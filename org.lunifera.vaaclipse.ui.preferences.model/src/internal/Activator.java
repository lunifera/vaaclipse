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
package internal;

import org.lunifera.vaaclipse.ui.preferences.model.impl.PreferencesFactoryImpl;
import org.lunifera.vaaclipse.ui.preferences.model.metadata.PreferencesFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author rushan
 *
 */
public class Activator implements BundleActivator {

	private ServiceRegistration<PreferencesFactory> factoryRegistraction;

	@Override
	public void start(BundleContext context) throws Exception {
		PreferencesFactory factory = new PreferencesFactoryImpl();
		factoryRegistraction = context.registerService(
				PreferencesFactory.class, factory, null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		factoryRegistraction.unregister();
	}
}
