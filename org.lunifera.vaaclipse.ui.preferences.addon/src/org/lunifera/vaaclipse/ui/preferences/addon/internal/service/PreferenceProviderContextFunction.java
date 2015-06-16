/**
 * Copyright (c) 2011 - 2014, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 		Florian Pirchner - Initial implementation
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal.service;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.service.component.annotations.Component;
import org.semanticsoft.vaaclipse.publicapi.preferences.IPreferenceProvider;

/**
 * Use this class to obtain an instance of {@link IPreferenceProvider}.
 */
@Component(service = IContextFunction.class, property = { "service.context.key=org.semanticsoft.vaaclipse.publicapi.preferences.IPreferenceProvider" })
public class PreferenceProviderContextFunction extends ContextFunction {
	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		PreferenceProvider broker = ContextInjectionFactory.make(
				PreferenceProvider.class, context);
		context.set(IPreferenceProvider.class, broker);
		context.set(PreferenceProvider.class, broker);
		return broker;
	}
}
