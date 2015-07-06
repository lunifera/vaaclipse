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
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.PreferencesDialog;

import com.vaadin.ui.UI;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class OpenPreferencesDialogHandler {

	@Execute
	public void execute(IEclipseContext context, MApplication app, UI ui) {

		context.set(VaaclipseApplication.class, (VaaclipseApplication) app);
		PreferencesDialog prefDlg = ContextInjectionFactory.make(
				PreferencesDialog.class, context);
		prefDlg.getWindow().center();
		ui.addWindow(prefDlg.getWindow());
	}

}
