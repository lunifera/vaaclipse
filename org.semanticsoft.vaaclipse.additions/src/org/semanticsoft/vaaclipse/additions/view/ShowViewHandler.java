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
package org.semanticsoft.vaaclipse.additions.view;

import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.semanticsoft.vaadin.optiondialog.OptionDialog.OptionsAlign;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * @author rushan
 * 
 */
public class ShowViewHandler {
	public static final String VIEWS_SHOW_VIEW_PARM_ID = "org.eclipse.ui.views.showView.viewId"; //$NON-NLS-1$

	@Execute
	public void execute(UI ui, MApplication application,
			EPartService partService, IEclipseContext context,
			@Optional @Named(VIEWS_SHOW_VIEW_PARM_ID) String viewId) {
		if (viewId != null) {
			partService.showPart(viewId, PartState.ACTIVATE);
			return;
		}

		OptionDialog dlg = new OptionDialog();
		dlg.setOptionButtonsAlignment(OptionsAlign.RIGHT);
		dlg.setOptionButtonsWidth(80, Unit.PIXELS);
		dlg.setWidth("350px");
		dlg.setHeight("500px");
		dlg.setModal(true);
		dlg.setCaption("Show View");
		// static resource will process this, so we set application header icon
		// without using ResourceInfoProvider service as for Open Perspective
		// Dialog
		dlg.setIcon(new ThemeResource("../base/favicon.ico"));
		ShowViewDialogContent componentProvider = ContextInjectionFactory.make(
				ShowViewDialogContent.class, context);
		dlg.setComponentProvider(componentProvider);

		dlg.addOption(0, "OK");
		dlg.addOption(1, "Cancel");

		ui.addWindow(dlg);
	}
}
