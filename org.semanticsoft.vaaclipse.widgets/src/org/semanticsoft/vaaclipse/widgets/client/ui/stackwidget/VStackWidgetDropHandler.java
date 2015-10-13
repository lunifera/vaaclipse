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

package org.semanticsoft.vaaclipse.widgets.client.ui.stackwidget;

import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.VDDTabsheetDropHandler;

public class VStackWidgetDropHandler extends VDDTabsheetDropHandler {

	private final VStackWidget stackWidget;

	public VStackWidgetDropHandler(VStackWidget stackWidget,
			ComponentConnector connector) {
		super(connector);
		this.stackWidget = stackWidget;
	}

	@Override
	public void dragOver(VDragEvent drag) {

		// VConsole.log("Drag Over");

		stackWidget.deEmphasis();

		stackWidget.updateDragDetails(drag);

		stackWidget.postOverHook(drag);

		// Check if we are dropping on our self
		if (stackWidget.equals(drag.getTransferable().getData(
				Constants.TRANSFERABLE_DETAIL_COMPONENT))) {
			return;
		}

		// Validate the drop
		validate(new VAcceptCallback() {
			public void accepted(VDragEvent event) {
				stackWidget.emphasis(event.getElementOver(), event);
			}
		}, drag);
	};

	@Override
	public void dragLeave(VDragEvent drag) {
		// VConsole.log("Drag Leave");
		stackWidget.deEmphasis();
		stackWidget.updateDragDetails(drag);
		stackWidget.postLeaveHook(drag);

		stackWidget.removeDockZone();
	};

	@Override
	public boolean drop(VDragEvent event) {
		if (!stackWidget.updateRegion(event))
			return false;

		Object sourceWidget = event.getTransferable().getDragSource();
		if (sourceWidget instanceof StackWidgetConnector) {
			sourceWidget = ((StackWidgetConnector) sourceWidget).getWidget();
			VStackWidget stackWidget = (VStackWidget) sourceWidget;
			stackWidget.restoreLocationOfPartToolbar();
		}

		return super.drop(event);
	}
}
