/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.renderers;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBar;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.semanticsoft.vaaclipse.presentation.engine.GenericRendererFactory;

@SuppressWarnings("restriction")
public class VaadinRendererFactory extends GenericRendererFactory {

	private WorkbenchWindowRenderer workbenchWindowRenderer;
	private TrimBarRenderer trimRenderer;
	private ToolControlRenderer toolControlRenderer;
	private SashRenderer sashRenderer;
	private StackRenderer partStackRenderer;
	private PartRenderer partRenderer;
	private ToolBarRenderer toolBarRenderer;
	private ToolItemRenderer toolItemRenderer;
	private MenuRenderer menuBarRenderer;
	private MenuItemRenderer menuItemRenderer;
	private MenuSeparatorRenderer menuSeparatorRenderer;
	private PerspectiveStackRenderer perspectiveStackRenderer;
	private PerspectiveRenderer perspectiveRenderer;
	private PlaceholderRenderer placeholderRenderer;
	private AreaRenderer areaRenderer;

	private final IEclipseContext context;

	@Inject
	public VaadinRendererFactory(IEclipseContext context) {
		this.context = context;
	}

	@Override
	public GenericRenderer getRenderer(MUIElement uiElement) {
		if (uiElement instanceof MWindow) {
			if (workbenchWindowRenderer == null) {
				workbenchWindowRenderer = ContextInjectionFactory.make(
						WorkbenchWindowRenderer.class, context);
			}
			return workbenchWindowRenderer;
		} else if (uiElement instanceof MTrimBar) {
			if (trimRenderer == null) {
				trimRenderer = ContextInjectionFactory.make(
						TrimBarRenderer.class, context);
			}
			return trimRenderer;
		} else if (uiElement instanceof MToolControl) {
			if (toolControlRenderer == null) {
				toolControlRenderer = ContextInjectionFactory.make(
						ToolControlRenderer.class, context);
			}
			return toolControlRenderer;
		} else if (uiElement instanceof MPartStack) {
			if (partStackRenderer == null) {
				partStackRenderer = ContextInjectionFactory.make(
						StackRenderer.class, context);
			}
			return partStackRenderer;
		} else if (uiElement instanceof MPart) {
			if (partRenderer == null) {
				partRenderer = ContextInjectionFactory.make(PartRenderer.class,
						context);
			}
			return partRenderer;
		} else if (uiElement instanceof MToolBar) {
			if (toolBarRenderer == null) {
				toolBarRenderer = ContextInjectionFactory.make(
						ToolBarRenderer.class, context);
			}
			return toolBarRenderer;
		} else if (uiElement instanceof MToolItem) {
			if (toolItemRenderer == null) {
				toolItemRenderer = ContextInjectionFactory.make(
						ToolItemRenderer.class, context);
			}
			return toolItemRenderer;
		} else if (uiElement instanceof MMenu) {
			if (menuBarRenderer == null) {
				menuBarRenderer = ContextInjectionFactory.make(
						MenuRenderer.class, context);
			}
			return menuBarRenderer;
		} else if (uiElement instanceof MMenuItem) {
			if (menuItemRenderer == null) {
				menuItemRenderer = ContextInjectionFactory.make(
						MenuItemRenderer.class, context);
			}
			return menuItemRenderer;
		} else if (uiElement instanceof MMenuSeparator) {
			if (menuSeparatorRenderer == null) {
				menuSeparatorRenderer = ContextInjectionFactory.make(
						MenuSeparatorRenderer.class, context);
			}
			return menuSeparatorRenderer;
		} else if (uiElement instanceof MPerspectiveStack) {
			if (perspectiveStackRenderer == null) {
				perspectiveStackRenderer = ContextInjectionFactory.make(
						PerspectiveStackRenderer.class, context);
			}
			return perspectiveStackRenderer;
		} else if (uiElement instanceof MPerspective) {
			if (perspectiveRenderer == null) {
				perspectiveRenderer = ContextInjectionFactory.make(
						PerspectiveRenderer.class, context);
			}
			return perspectiveRenderer;
		} else if (uiElement instanceof MPlaceholder) {
			if (placeholderRenderer == null) {
				placeholderRenderer = ContextInjectionFactory.make(
						PlaceholderRenderer.class, context);
			}
			return placeholderRenderer;
		} else if (uiElement instanceof MArea) // must be before
												// MPartSashContainer becouse
												// more special type
		{
			if (areaRenderer == null) {
				areaRenderer = ContextInjectionFactory.make(AreaRenderer.class,
						context);
			}
			return areaRenderer;
		} else if (uiElement instanceof MPartSashContainer) {
			if (sashRenderer == null) {
				sashRenderer = ContextInjectionFactory.make(SashRenderer.class,
						context);
			}
			return sashRenderer;
		}
		return super.getRenderer(uiElement);
	}
}
