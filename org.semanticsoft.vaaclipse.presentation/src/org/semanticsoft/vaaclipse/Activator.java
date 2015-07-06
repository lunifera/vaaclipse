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
package org.semanticsoft.vaaclipse;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author rushan
 *
 */
public class Activator implements BundleActivator {
	private static Activator instance;

	private BundleContext context;

	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
	}

	public BundleContext getContext() {
		return context;
	}

	public static Activator getInstance() {
		return instance;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}
}
