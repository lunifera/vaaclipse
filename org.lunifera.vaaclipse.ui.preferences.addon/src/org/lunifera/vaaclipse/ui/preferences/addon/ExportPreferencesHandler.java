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
package org.lunifera.vaaclipse.ui.preferences.addon;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.impexp.ExportPreferences;

/**
 * @author rushan
 *
 */
public class ExportPreferencesHandler {

	@CanExecute
	public boolean canExecute(@Optional PreferencesAuthorization prefAuthService) {
		boolean isAllowed = true;
		if (prefAuthService != null) {
			isAllowed = prefAuthService.exportAllowed();
		}
		return isAllowed;
	}

	@Execute
	public void execute(@Optional PreferencesAuthorization prefAuthService,
			IEclipseContext context) {
		if (!canExecute(prefAuthService))
			return;

		ContextInjectionFactory.make(ExportPreferences.class, context);
	}

}
