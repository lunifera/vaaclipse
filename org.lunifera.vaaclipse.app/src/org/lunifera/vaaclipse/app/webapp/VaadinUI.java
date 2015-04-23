/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *     Florian Pirchner - adjustings for lunifera implementation
 *******************************************************************************/

package org.lunifera.vaaclipse.app.webapp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.core.services.contributions.IContributionFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.translation.TranslationProviderFactory;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.internal.workbench.ActiveChildLookupFunction;
import org.eclipse.e4.ui.internal.workbench.ActivePartLookupFunction;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.E4XMIResourceFactory;
import org.eclipse.e4.ui.internal.workbench.ExceptionHandler;
import org.eclipse.e4.ui.internal.workbench.ModelServiceImpl;
import org.eclipse.e4.ui.internal.workbench.PlaceholderResolver;
import org.eclipse.e4.ui.internal.workbench.ReflectionContributionFactory;
import org.eclipse.e4.ui.internal.workbench.WorkbenchLogger;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.commands.impl.CommandsPackageImpl;
import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.AdvancedPackageImpl;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuPackageImpl;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.services.internal.events.EventBroker;
import org.eclipse.e4.ui.workbench.IExceptionHandler;
import org.eclipse.e4.ui.workbench.IModelResourceHandler;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPlaceholderResolver;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.service.datalocation.Location;
import org.lunifera.runtime.web.vaadin.databinding.VaadinObservables;
import org.lunifera.vaaclipse.addons.common.api.resource.ICustomizedModelHandler;
import org.lunifera.vaaclipse.addons.common.api.resource.ICustomizedModelResourceHandler;
import org.lunifera.vaaclipse.addons.common.resource.LayoutChangedObserver;
import org.lunifera.vaaclipse.app.VaadinE4Application;
import org.lunifera.vaaclipse.app.servlet.VaadinExecutorServiceImpl;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;
import org.semanticsoft.vaaclipse.publicapi.authentication.AuthenticationConstants;
import org.semanticsoft.vaaclipse.publicapi.theme.Theme;
import org.semanticsoft.vaaclipse.publicapi.theme.ThemeConstants;

import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;
import com.vaadin.ui.UIDetachedException;
import com.vaadin.ui.VerticalLayout;

// TODO Lunifera - Move me to Lunifera github repo.
@SuppressWarnings("restriction")
@Push
public class VaadinUI extends UI {
	private static final long serialVersionUID = 1L;

	public static final String THEME_ID = "cssTheme";

	/**
	 * This UI uses a different default resource handler.
	 */
	private static final String DEFAULT_RESOURCE_HANDLER = "bundleclass://org.lunifera.vaaclipse.addons.common/org.lunifera.vaaclipse.addons.common.resource.ResourceHandler";
	private static final String CUSTOMIZED_MODEL_SERVICE = "bundleclass://org.lunifera.vaaclipse.addons.common/org.lunifera.vaaclipse.addons.common.resource.CustomizedModelHandler";

	protected static String presentationEngineURI = "bundleclass://org.semanticsoft.vaaclipse.presentation/"
			+ "org.semanticsoft.vaaclipse.presentation.engine.VaadinPresentationEngine";

	protected Logger logger;

	private String[] args;
	private ICustomizedModelResourceHandler modelResourceHandler;

	private E4Workbench e4Workbench;

	private Location instanceLocation;

	private IApplicationContext context;

	private Map<String, MUIElement> id2element = new HashMap<String, MUIElement>();

	private Object lcManager;

	private IEclipseContext appContext;

	private IContributionFactory factory;

	private Object user;
	private Class<Object> userClass;

	private VaadinExecutorServiceImpl executorService;

	private LayoutChangedObserver layoutChangedObserver;

	private static Map<String, Object[]> tempUserStore = new HashMap<String, Object[]>();

	public VaadinUI() {
	}

	public IEclipseContext getRootContext() {
		return appContext;
	}

	private void setThemeInternal(String themeId) {
		throw new RuntimeException(
				"The changing theme in runtime is not supported by Vaadin 7");
	}

	@Override
	public void init(VaadinRequest request) {
		executorService = new VaadinExecutorServiceImpl();
		context = VaadinE4Application.getInstance().getAppContext();
		logger = VaadinE4Application.getInstance().getLogger();

		String sessionId = getSession().getSession().getId();
		Object[] prevUser = tempUserStore.remove(sessionId);
		if (prevUser != null) {
			this.user = prevUser[0];
			this.userClass = (Class<Object>) prevUser[1];
		}

		// -------------------------------------
		prepareEnvironment(context);

		IEventBroker eventBroker = appContext.get(EventBroker.class);
		eventBroker.subscribe(ThemeConstants.Events.setThemeEvent,
				new EventHandler() {
					@Override
					public void handleEvent(org.osgi.service.event.Event event) {
						Theme theme = (Theme) event
								.getProperty(IEventBroker.DATA);
						if (theme != null) {
							HttpSession httpSession = ((WrappedHttpSession) getSession()
									.getSession()).getHttpSession();
							httpSession.setAttribute(
									ThemeConstants.Attrubutes.themeid,
									theme.getId());
							setThemeInternal(theme.getWebId());
						}
					}
				});

		String authProvider = VaadinE4Application.getInstance()
				.getApplicationAuthenticationProvider();

		if (authProvider == null || authProvider.trim().isEmpty()) {
			// start workbench as usually
			e4Workbench = createE4Workbench(context);
			e4Workbench.createAndRunUI(e4Workbench.getApplication());
		} else {

			IContributionFactory contributionFactory = (IContributionFactory) appContext
					.get(IContributionFactory.class.getName());
			IEclipseContext authConext = appContext.createChild();

			VerticalLayout content = new VerticalLayout();
			content.setSizeFull();
			setContent(content);

			authConext.set(ComponentContainer.class, content);
			authConext.set(VerticalLayout.class, content);
			Object authProviderObj = contributionFactory.create(authProvider,
					authConext);
		}

		eventBroker.subscribe(
				AuthenticationConstants.Events.Authentication.name,
				new EventHandler() {
					@SuppressWarnings("unchecked")
					@Override
					public void handleEvent(org.osgi.service.event.Event event) {

						user = event.getProperty(EventUtils.DATA);
						userClass = (Class<Object>) event
								.getProperty(AuthenticationConstants.Events.Authentication.userClass);

						if (userClass == null && user != null) {
							userClass = (Class<Object>) user.getClass();
						}
						appContext.set(userClass, user);
						appContext.set("user", user);

						if (e4Workbench != null) {
							String sessionId = getSession().getSession()
									.getId();
							tempUserStore.put(sessionId, new Object[] { user,
									userClass });
							e4Workbench.close();
						} else {
							e4Workbench = createE4Workbench(context);
							e4Workbench.createAndRunUI(e4Workbench
									.getApplication());
						}

					}
				});

		executeLater();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.UI#push()
	 */
	@Override
	public void push() {
		VaadinSession session = getSession();

		if (session == null) {
			throw new UIDetachedException("Cannot push a detached UI");
		}
		assert session.hasLock();

		if (!getPushConfiguration().getPushMode().isEnabled()) {
			throw new IllegalStateException("Push not enabled");
		}
		assert getPushConnection() != null;

		/*
		 * Purge the pending access queue as it might mark a connector as dirty
		 * when the push would otherwise be ignored because there are no changes
		 * to push.
		 */
		session.getService().runPendingAccessTasks(session);

		// execute the runnables for e4 kernel
		executorService.exec();

		if (!getConnectorTracker().hasDirtyConnectors()) {
			// Do not push if there is nothing to push
			return;
		}

		getPushConnection().push();
	}

	/**
	 * Is used to execute the runnables from executor service.
	 */
	public void executeLater() {
		VaadinExecutorServiceImpl man = (VaadinExecutorServiceImpl) appContext
				.get(VaadinExecutorService.class);
		man.exec();
	}

	public void prepareEnvironment(IApplicationContext applicationContext) {
		args = (String[]) applicationContext.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);

		appContext = createDefaultContext(applicationContext);
		appContext.set("e4ApplicationInstanceId", UUID.randomUUID().toString());
		appContext.set("vaadinUI", this);
		appContext.set(UI.class, this);
		appContext.set(VaadinExecutorService.class, getExecutorService());
		appContext.set(UISynchronize.class, new UISynchronize() {

			public void syncExec(Runnable runnable) {
				VaadinObservables.activateRealm(VaadinUI.this);
				VaadinUI.this.accessSynchronously(runnable);
			}

			public void asyncExec(Runnable runnable) {
				VaadinObservables.activateRealm(VaadinUI.this);
				VaadinUI.this.access(runnable);
			}
		});

		factory = (IContributionFactory) appContext
				.get(IContributionFactory.class.getName());

		// Install the life-cycle manager for this session if there's one
		// defined
		String lifeCycleURI = getArgValue(E4Workbench.LIFE_CYCLE_URI_ARG,
				applicationContext, false);
		if (lifeCycleURI != null) {
			lcManager = factory.create(lifeCycleURI, appContext);
			if (lcManager != null) {
				// Let the manager manipulate the appContext if desired
				ContextInjectionFactory.invoke(lcManager,
						PostContextCreate.class, appContext, null);
			}
		}
	}

	private VaadinExecutorService getExecutorService() {
		return executorService;
	}

	public E4Workbench createE4Workbench(IApplicationContext applicationContext) {
		// Create the app model and its context
		MApplication appModel = loadApplicationModel(applicationContext,
				appContext);
		fixNullElementIds(appModel);
		appModel.setContext(appContext);
		appContext.set(MApplication.class.getName(), appModel);

		// ContextInjectionFactory.setDefault(appContext);
		if (lcManager != null) {
			ContextInjectionFactory.invoke(lcManager, ProcessAdditions.class,
					appContext, null);
			ContextInjectionFactory.invoke(lcManager, ProcessRemovals.class,
					appContext, null);
		}
		// Create the addons
		for (MContribution addon : appModel.getAddons()) {
			Object obj = factory.create(addon.getContributionURI(), appContext);
			addon.setObject(obj);
		}

		E4Workbench e4Workbench = new E4Workbench(appModel, appContext);

		return e4Workbench;
	}

	private void fixNullElementIds(MUIElement element) {
		if (!(element instanceof MApplication)) {
			if (element.getElementId() == null
					|| element.getElementId().trim().isEmpty()) {
				element.setElementId(UUID.randomUUID().toString());
			} else {
				if (element instanceof MPlaceholder) {
					// NOTHING TO DO - We must not touch the ID. Otherwise parts
					// can not be wired for the #reference after cloning.
					logger.debug("Skip fixing ID for " + element);
				} else if (isInSharedArea(element)) {
					// NOTHING TO DO - We must not touch the ID
					logger.debug("Skip fixing ID for " + element);
				} else {
					// check that there are not element in model with this id
					// MUIElement someElement =
					// modelService.find(element.getElementId(), app); //this
					// search
					// recursive - very long, so use map
					MUIElement someElement = id2element.get(element
							.getElementId());
					if (someElement != null && someElement != element) {
						final String randomUUID = UUID.randomUUID().toString();
						element.setElementId(element.getElementId() + "_"
								+ randomUUID);
					}
				}
			}

			id2element.put(element.getElementId(), element);
		}

		if (element instanceof MElementContainer<?>) {
			for (MUIElement child : ((MElementContainer<?>) element)
					.getChildren()) {
				fixNullElementIds(child);
			}
		}
	}

	private boolean isInSharedArea(MUIElement element) {
		EModelService modelService = appContext.get(EModelService.class);
		int location = modelService.getElementLocation(element);
		if ((location & EModelService.IN_SHARED_AREA) != 0) {
			return true;
		}
		return false;
	}

	private MApplication loadApplicationModel(IApplicationContext appContext,
			IEclipseContext eclipseContext) {
		logger.debug("VaadinE4Application.loadApplicationModel()");
		MApplication theApp = null;

		instanceLocation = VaadinE4Application.getInstance()
				.getInstanceLocation();

		String appModelPath = getArgValue(E4Workbench.XMI_URI_ARG, appContext,
				false);
		Assert.isNotNull(appModelPath, E4Workbench.XMI_URI_ARG
				+ " argument missing"); //$NON-NLS-1$
		final URI initialWorkbenchDefinitionInstance = URI
				.createPlatformPluginURI(appModelPath, true);

		eclipseContext.set(E4Workbench.INITIAL_WORKBENCH_MODEL_URI,
				initialWorkbenchDefinitionInstance);
		eclipseContext.set(E4Workbench.INSTANCE_LOCATION, instanceLocation);

		// Save and restore
		boolean saveAndRestore;
		String value = getArgValue(E4Workbench.PERSIST_STATE, appContext, false);

		saveAndRestore = value == null || Boolean.parseBoolean(value);

		eclipseContext.set(E4Workbench.PERSIST_STATE, true);

		// Persisted state
		boolean clearPersistedState;
		value = getArgValue(E4Workbench.CLEAR_PERSISTED_STATE, appContext, true);
		clearPersistedState = value != null && Boolean.parseBoolean(value);
		eclipseContext.set(E4Workbench.CLEAR_PERSISTED_STATE, false);

		// Delta save and restore
		boolean deltaRestore;
		value = getArgValue(E4Workbench.DELTA_RESTORE, appContext, false);
		deltaRestore = value == null || Boolean.parseBoolean(value);
		eclipseContext.set(E4Workbench.DELTA_RESTORE,
				Boolean.valueOf(deltaRestore));

		registerResourceSet(eclipseContext);

		IContributionFactory factory = eclipseContext
				.get(IContributionFactory.class);

		// create the customized model service
		ICustomizedModelHandler service = (ICustomizedModelHandler) factory
				.create(CUSTOMIZED_MODEL_SERVICE, eclipseContext);
		eclipseContext.set(ICustomizedModelHandler.class, service);

		String resourceHandler = getArgValue(
				E4Workbench.MODEL_RESOURCE_HANDLER, appContext, false);

		if (resourceHandler == null) {
			resourceHandler = DEFAULT_RESOURCE_HANDLER;
		}

		modelResourceHandler = (ICustomizedModelResourceHandler) factory.create(
				resourceHandler, eclipseContext);
		eclipseContext.set(IModelResourceHandler.class, modelResourceHandler);
		eclipseContext.set(ICustomizedModelResourceHandler.class, modelResourceHandler);

		Resource resource = modelResourceHandler.loadMostRecentModel();
		theApp = (MApplication) resource.getContents().get(0);

		// register the layout changed observer
		layoutChangedObserver = ContextInjectionFactory.make(
				LayoutChangedObserver.class, eclipseContext);
		eclipseContext.set(LayoutChangedObserver.class, layoutChangedObserver);

		return theApp;
	}

	/**
	 * Registers the model resource set at the eclipse context.
	 * 
	 * @param eclipseContext
	 */
	private void registerResourceSet(IEclipseContext eclipseContext) {
		ResourceSet resourceSetImpl = new ResourceSetImpl();
		resourceSetImpl
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new E4XMIResourceFactory());

		resourceSetImpl.getPackageRegistry().put(
				ApplicationPackageImpl.eNS_URI,
				ApplicationPackageImpl.eINSTANCE);
		resourceSetImpl.getPackageRegistry().put(CommandsPackageImpl.eNS_URI,
				CommandsPackageImpl.eINSTANCE);
		resourceSetImpl.getPackageRegistry().put(UiPackageImpl.eNS_URI,
				UiPackageImpl.eINSTANCE);
		resourceSetImpl.getPackageRegistry().put(MenuPackageImpl.eNS_URI,
				MenuPackageImpl.eINSTANCE);
		resourceSetImpl.getPackageRegistry().put(BasicPackageImpl.eNS_URI,
				BasicPackageImpl.eINSTANCE);
		resourceSetImpl.getPackageRegistry().put(AdvancedPackageImpl.eNS_URI,
				AdvancedPackageImpl.eINSTANCE);
		resourceSetImpl
				.getPackageRegistry()
				.put(org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eNS_URI,
						org.eclipse.e4.ui.model.application.descriptor.basic.impl.BasicPackageImpl.eINSTANCE);

		eclipseContext.set(ResourceSet.class, resourceSetImpl);
		eclipseContext.set("e4ModelResourceset", resourceSetImpl);
	}

	private String getArgValue(String argName, IApplicationContext appContext,
			boolean singledCmdArgValue) {
		// Is it in the arg list ?
		if (argName == null || argName.length() == 0) {
			return null;
		}

		if (singledCmdArgValue) {
			for (int i = 0; i < args.length; i++) {
				if (("-" + argName).equals(args[i])) {
					return "true";
				}
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				if (("-" + argName).equals(args[i]) && i + 1 < args.length) {
					return args[i + 1];
				}
			}
		}

		final String brandingProperty = appContext.getBrandingProperty(argName);
		return brandingProperty == null ? System.getProperty(argName)
				: brandingProperty;
	}

	public IEclipseContext createDefaultContext(
			IApplicationContext applicationContext) {
		IEclipseContext serviceContext = E4Workbench.getServiceContext();
		final IEclipseContext eclipseContext = serviceContext
				.createChild("WorkbenchContext"); //$NON-NLS-1$

		IExtensionRegistry registry = RegistryFactory.getRegistry();
		ReflectionContributionFactory contributionFactory = new ReflectionContributionFactory(
				registry);
		eclipseContext.set(IContributionFactory.class.getName(),
				contributionFactory);

		eclipseContext.set(Logger.class.getName(), ContextInjectionFactory
				.make(WorkbenchLogger.class, eclipseContext));

		String presentationURI = getArgValue(E4Workbench.PRESENTATION_URI_ARG,
				applicationContext, false);
		if (presentationURI == null) {
			presentationURI = presentationEngineURI;
		}
		eclipseContext.set(E4Workbench.PRESENTATION_URI_ARG, presentationURI);
		eclipseContext.set(UI.class, this);

		eclipseContext.set(EPlaceholderResolver.class,
				new PlaceholderResolver());

		if (user != null) {
			if (userClass == null)
				userClass = (Class<Object>) user.getClass();
			eclipseContext.set(userClass, user);
			eclipseContext.set("user", user);
		}

		String themeId = getArgValue(THEME_ID, applicationContext, false);
		eclipseContext.set(THEME_ID, themeId);

		// if (themeId != null && !themeId.equals("")) {
		// setTheme(themeId);
		// } else {
		// setTheme(ValoTheme.THEME_NAME);
		// }

		String cssURI = getArgValue(E4Workbench.CSS_URI_ARG,
				applicationContext, false);
		if (cssURI != null) {
			eclipseContext.set(E4Workbench.CSS_URI_ARG, cssURI);
		}

		// Temporary to support old property as well
		if (cssURI != null && !cssURI.startsWith("platform:")) {
			logger.warn("Warning "
					+ cssURI
					+ " changed its meaning it is used now to run without theme support");
			eclipseContext.set(THEME_ID, cssURI);
		}

		String cssResourcesURI = getArgValue(E4Workbench.CSS_RESOURCE_URI_ARG,
				applicationContext, false);
		eclipseContext.set(E4Workbench.CSS_RESOURCE_URI_ARG, cssResourcesURI);

		eclipseContext.set(EModelService.class, new ModelServiceImpl(
				eclipseContext));

		// translation
		String locale = getLocale().toString();
		serviceContext.set(TranslationService.LOCALE, locale);
		logger.debug("Setting locale to " + locale);
		TranslationService bundleTranslationProvider = TranslationProviderFactory
				.bundleTranslationService(serviceContext);
		serviceContext.set(TranslationService.class, bundleTranslationProvider);

		ExceptionHandler exceptionHandler = new ExceptionHandler();
		eclipseContext.set(IExceptionHandler.class.getName(), exceptionHandler);
		eclipseContext.set(IExtensionRegistry.class.getName(), registry);

		// setup for commands and handlers
		eclipseContext.set(IServiceConstants.ACTIVE_PART,
				new ActivePartLookupFunction());

		eclipseContext.set(IServiceConstants.ACTIVE_SHELL,
				new ActiveChildLookupFunction(IServiceConstants.ACTIVE_SHELL,
						E4Workbench.LOCAL_ACTIVE_SHELL));

		return eclipseContext;
	}

	@Override
	public void detach() {
		if (e4Workbench != null) {
			e4Workbench.close();
		}

		if (layoutChangedObserver != null) {
			layoutChangedObserver.dispose();
		}
		super.detach();
	}
}
