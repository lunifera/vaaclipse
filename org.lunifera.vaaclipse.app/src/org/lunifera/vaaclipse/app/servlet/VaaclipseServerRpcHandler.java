/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin, Florian Prichner, Sopot Cela and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.lunifera.vaaclipse.app.servlet;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;

import com.vaadin.server.ClientConnector;
import com.vaadin.server.JsonCodec;
import com.vaadin.server.LegacyCommunicationManager;
import com.vaadin.server.LegacyCommunicationManager.InvalidUIDLSecurityKeyException;
import com.vaadin.server.ServerRpcManager;
import com.vaadin.server.ServerRpcManager.RpcInvocationException;
import com.vaadin.server.ServerRpcMethodInvocation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VariableOwner;
import com.vaadin.server.communication.ServerRpcHandler;
import com.vaadin.shared.ApplicationConstants;
import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.LegacyChangeVariablesInvocation;
import com.vaadin.shared.communication.MethodInvocation;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.communication.UidlValue;
import com.vaadin.ui.Component;
import com.vaadin.ui.ConnectorTracker;
import com.vaadin.ui.UI;

/**
 * This changes are required for the eclipse "UI invoke later" support.
 * 
 * @author rushan
 *
 */
@SuppressWarnings({ "deprecation", "serial" })
public class VaaclipseServerRpcHandler extends ServerRpcHandler {
	private VaadinExecutorServiceImpl executorService = new VaadinExecutorServiceImpl();

	public VaadinExecutorService getExecutorService() {
		return executorService;
	}
 
	public void handleRpc(UI ui, Reader reader, VaadinRequest request)
			throws IOException, InvalidUIDLSecurityKeyException, JSONException {
		ui.getSession().setLastRequestTimestamp(System.currentTimeMillis());

		String changeMessage = getMessage(reader);

		if (changeMessage == null || changeMessage.equals("")) {
			// The client sometimes sends empty messages, this is probably a bug
			return;
		}

		RpcRequest rpcRequest = new RpcRequest(changeMessage, request);
		// Security: double cookie submission pattern unless disabled by
		// property
		if (!VaadinService.isCsrfTokenValid(ui.getSession(),
				rpcRequest.getCsrfToken())) {
			throw new InvalidUIDLSecurityKeyException("");
		}
		handleInvocations(ui, rpcRequest.getSyncId(),
				rpcRequest.getRpcInvocationsData());

		ui.getConnectorTracker().cleanConcurrentlyRemovedConnectorIds(
				rpcRequest.getSyncId());
	}

	/**
	 * Parse JSON from the client into a list of MethodInvocation instances.
	 *
	 * @param connectorTracker
	 *            The ConnectorTracker used to lookup connectors
	 * @param invocationsJson
	 *            JSON containing all information needed to execute all
	 *            requested RPC calls.
	 * @param lastSyncIdSeenByClient
	 *            the most recent sync id the client has seen at the time the
	 *            request was sent
	 * @return list of MethodInvocation to perform
	 * @throws JSONException
	 */
	private List<MethodInvocation> parseInvocations(
			ConnectorTracker connectorTracker, JSONArray invocationsJson,
			int lastSyncIdSeenByClient) throws JSONException {
		int invocationCount = invocationsJson.length();
		ArrayList<MethodInvocation> invocations = new ArrayList<MethodInvocation>(
				invocationCount);

		MethodInvocation previousInvocation = null;
		// parse JSON to MethodInvocations
		for (int i = 0; i < invocationCount; ++i) {

			JSONArray invocationJson = invocationsJson.getJSONArray(i);

			MethodInvocation invocation = parseInvocation(invocationJson,
					previousInvocation, connectorTracker,
					lastSyncIdSeenByClient);
			if (invocation != null) {
				// Can be null if the invocation was a legacy invocation and it
				// was merged with the previous one or if the invocation was
				// rejected because of an error.
				invocations.add(invocation);
				previousInvocation = invocation;
			}
		}
		return invocations;
	}

	private MethodInvocation parseInvocation(JSONArray invocationJson,
			MethodInvocation previousInvocation,
			ConnectorTracker connectorTracker, long lastSyncIdSeenByClient)
			throws JSONException {
		String connectorId = invocationJson.getString(0);
		String interfaceName = invocationJson.getString(1);
		String methodName = invocationJson.getString(2);

		if (connectorTracker.getConnector(connectorId) == null
				&& !connectorId
						.equals(ApplicationConstants.DRAG_AND_DROP_CONNECTOR_ID)) {

			if (!connectorTracker.connectorWasPresentAsRequestWasSent(
					connectorId, lastSyncIdSeenByClient)) {
				getLogger()
						.log(Level.WARNING,
								"RPC call to "
										+ interfaceName
										+ "."
										+ methodName
										+ " received for connector "
										+ connectorId
										+ " but no such connector could be found. Resynchronizing client.");
				// This is likely an out of sync issue (client tries to update a
				// connector which is not present). Force resync.
				connectorTracker.markAllConnectorsDirty();
			}
			return null;
		}

		JSONArray parametersJson = invocationJson.getJSONArray(3);

		if (LegacyChangeVariablesInvocation.isLegacyVariableChange(
				interfaceName, methodName)) {
			if (!(previousInvocation instanceof LegacyChangeVariablesInvocation)) {
				previousInvocation = null;
			}

			return parseLegacyChangeVariablesInvocation(connectorId,
					interfaceName, methodName,
					(LegacyChangeVariablesInvocation) previousInvocation,
					parametersJson, connectorTracker);
		} else {
			return parseServerRpcInvocation(connectorId, interfaceName,
					methodName, parametersJson, connectorTracker);
		}

	}

	private LegacyChangeVariablesInvocation parseLegacyChangeVariablesInvocation(
			String connectorId, String interfaceName, String methodName,
			LegacyChangeVariablesInvocation previousInvocation,
			JSONArray parametersJson, ConnectorTracker connectorTracker)
			throws JSONException {
		if (parametersJson.length() != 2) {
			throw new JSONException(
					"Invalid parameters in legacy change variables call. Expected 2, was "
							+ parametersJson.length());
		}
		String variableName = parametersJson.getString(0);
		UidlValue uidlValue = (UidlValue) JsonCodec.decodeInternalType(
				UidlValue.class, true, parametersJson.get(1), connectorTracker);

		Object value = uidlValue.getValue();

		if (previousInvocation != null
				&& previousInvocation.getConnectorId().equals(connectorId)) {
			previousInvocation.setVariableChange(variableName, value);
			return null;
		} else {
			return new LegacyChangeVariablesInvocation(connectorId,
					variableName, value);
		}
	}

	private ServerRpcMethodInvocation parseServerRpcInvocation(
			String connectorId, String interfaceName, String methodName,
			JSONArray parametersJson, ConnectorTracker connectorTracker)
			throws JSONException {
		ClientConnector connector = connectorTracker.getConnector(connectorId);

		ServerRpcManager<?> rpcManager = connector.getRpcManager(interfaceName);
		if (rpcManager == null) {
			/*
			 * Security: Don't even decode the json parameters if no RpcManager
			 * corresponding to the received method invocation has been
			 * registered.
			 */
			getLogger().warning(
					"Ignoring RPC call to " + interfaceName + "." + methodName
							+ " in connector " + connector.getClass().getName()
							+ "(" + connectorId
							+ ") as no RPC implementation is registered");
			return null;
		}

		// Use interface from RpcManager instead of loading the class based on
		// the string name to avoid problems with OSGi
		Class<? extends ServerRpc> rpcInterface = rpcManager.getRpcInterface();

		ServerRpcMethodInvocation invocation = new ServerRpcMethodInvocation(
				connectorId, rpcInterface, methodName, parametersJson.length());

		Object[] parameters = new Object[parametersJson.length()];
		Type[] declaredRpcMethodParameterTypes = invocation.getMethod()
				.getGenericParameterTypes();

		for (int j = 0; j < parametersJson.length(); ++j) {
			Object parameterValue = parametersJson.get(j);
			Type parameterType = declaredRpcMethodParameterTypes[j];
			parameters[j] = JsonCodec.decodeInternalOrCustomType(parameterType,
					parameterValue, connectorTracker);
		}
		invocation.setParameters(parameters);
		return invocation;
	}

	private void handleInvocations(UI uI, int lastSyncIdSeenByClient,
			JSONArray invocationsData) {
		// TODO PUSH Refactor so that this is not needed
		LegacyCommunicationManager manager = uI.getSession()
				.getCommunicationManager();

		try {
			ConnectorTracker connectorTracker = uI.getConnectorTracker();

			Set<Connector> enabledConnectors = new HashSet<Connector>();

			List<MethodInvocation> invocations = parseInvocations(
					uI.getConnectorTracker(), invocationsData,
					lastSyncIdSeenByClient);
			for (MethodInvocation invocation : invocations) {
				final ClientConnector connector = connectorTracker
						.getConnector(invocation.getConnectorId());

				if (connector != null && connector.isConnectorEnabled()) {
					enabledConnectors.add(connector);
				}
			}

			for (int i = 0; i < invocations.size(); i++) {
				MethodInvocation invocation = invocations.get(i);

				final ClientConnector connector = connectorTracker
						.getConnector(invocation.getConnectorId());
				if (connector == null) {
					getLogger()
							.log(Level.WARNING,
									"Received RPC call for unknown connector with id {0} (tried to invoke {1}.{2})",
									new Object[] { invocation.getConnectorId(),
											invocation.getInterfaceName(),
											invocation.getMethodName() });
					continue;
				}

				if (!enabledConnectors.contains(connector)) {

					if (invocation instanceof LegacyChangeVariablesInvocation) {
						LegacyChangeVariablesInvocation legacyInvocation = (LegacyChangeVariablesInvocation) invocation;
						// TODO convert window close to a separate RPC call and
						// handle above - not a variable change

						// Handle special case where window-close is called
						// after the window has been removed from the
						// application or the application has closed
						Map<String, Object> changes = legacyInvocation
								.getVariableChanges();
						if (changes.size() == 1 && changes.containsKey("close")
								&& Boolean.TRUE.equals(changes.get("close"))) {
							// Silently ignore this
							continue;
						}
					}

					// Connector is disabled, log a warning and move to the next
					getLogger().warning(
							getIgnoredDisabledError("RPC call", connector));
					continue;
				}
				// DragAndDropService has null UI
				if (connector.getUI() != null && connector.getUI().isClosing()) {
					String msg = "Ignoring RPC call for connector "
							+ connector.getClass().getName();
					if (connector instanceof Component) {
						String caption = ((Component) connector).getCaption();
						if (caption != null) {
							msg += ", caption=" + caption;
						}
					}
					msg += " in closed UI";
					getLogger().warning(msg);
					continue;

				}

				if (invocation instanceof ServerRpcMethodInvocation) {
					try {
						ServerRpcManager.applyInvocation(connector,
								(ServerRpcMethodInvocation) invocation);
					} catch (RpcInvocationException e) {
						manager.handleConnectorRelatedException(connector, e);
					}
				} else {
					// All code below is for legacy variable changes
					LegacyChangeVariablesInvocation legacyInvocation = (LegacyChangeVariablesInvocation) invocation;
					Map<String, Object> changes = legacyInvocation
							.getVariableChanges();
					try {
						if (connector instanceof VariableOwner) {
							// The source parameter is never used anywhere
							changeVariables(null, (VariableOwner) connector,
									changes);
							executorService.exec();
						} else {
							throw new IllegalStateException(
									"Received legacy variable change for "
											+ connector.getClass().getName()
											+ " ("
											+ connector.getConnectorId()
											+ ") which is not a VariableOwner. The client-side connector sent these legacy varaibles: "
											+ changes.keySet());
						}
					} catch (Exception e) {
						manager.handleConnectorRelatedException(connector, e);
					}
				}
			}
		} catch (JSONException e) {
			getLogger().warning(
					"Unable to parse RPC call from the client: "
							+ e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private static final Logger getLogger() {
		return Logger.getLogger(ServerRpcHandler.class.getName());
	}
}
