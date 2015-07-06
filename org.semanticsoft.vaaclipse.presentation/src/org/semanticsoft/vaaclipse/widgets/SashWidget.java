/**
 * Copyright (c) 2011 - 2015, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *         Florian Pirchner - Initial implementation
 */
package org.semanticsoft.vaaclipse.widgets;

import java.util.EventObject;

import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.ui.splitpanel.AbstractSplitPanelState;

/**
 * @author rushan
 *
 */
public interface SashWidget {
	void addListener(SplitPositionChangedListener listener);

	void fireEvent(EventObject event);

	AbstractSplitPanelState getState();

	<T extends ServerRpc> void registerRpc(T implementation,
			Class<T> rpcInterfaceType);
}
