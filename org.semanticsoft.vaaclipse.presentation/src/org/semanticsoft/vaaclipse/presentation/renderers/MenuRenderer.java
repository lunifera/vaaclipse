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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.ContributionsAnalyzer;
import org.eclipse.e4.ui.model.application.ui.MCoreExpression;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.semanticsoft.vaaclipse.api.MenuContributionService;
import org.semanticsoft.vaaclipse.api.VaadinExecutorService;
import org.semanticsoft.vaaclipse.publicapi.resources.ResourceHelper;

import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("restriction")
public class MenuRenderer extends BasicMenuToolbarTrimbarRenderer {
	@Inject
	MenuContributionService contributionService;

	@Inject
	VaadinExecutorService execService;

	@Inject
	IEventBroker eventBroker;

	@Override
	public void createWidget(MUIElement element,
			MElementContainer<MUIElement> parent) {
		if (!(element instanceof MMenu) || !element.isToBeRendered())
			return;

		if (MWindow.class.isAssignableFrom(parent.getClass())) {
			MenuBar menuBar = new MenuBar();
			element.setWidget(menuBar);
		} else {
			String text = ((MUILabel) element).getLocalizedLabel();
			if (text != null)
				text = text.trim();
			else
				text = "NoName";
			text = text.replaceAll("&", "");

			MUIElement nextRenderableAndVisible = findNextRendarableAndVisible(
					element, parent);
			MenuItem item = null;
			if (parent.getWidget() instanceof MenuBar) {
				if (nextRenderableAndVisible == null)
					item = ((MenuBar) parent.getWidget()).addItem(text, null,
							null);
				else
					item = ((MenuBar) parent.getWidget()).addItemBefore(text,
							null, null,
							(MenuItem) nextRenderableAndVisible.getWidget());
			} else if (parent.getWidget() instanceof MenuItem) {
				if (nextRenderableAndVisible == null)
					item = ((MenuItem) parent.getWidget()).addItem(text, null,
							null);
				else
					item = ((MenuItem) parent.getWidget()).addItemBefore(text,
							null, null,
							(MenuItem) nextRenderableAndVisible.getWidget());
			}

			element.setWidget(item);
		}
	}

	@Override
	public void processContents(MElementContainer<MUIElement> element) {
		final MMenu menu = (MMenu) (MElementContainer<?>) element;

		final IEclipseContext ctx = getContext(menu);
		final ExpressionContext eContext = new ExpressionContext(ctx);

		// Before contribution added:
		// visible when support for original trimbar elements (without
		// contributed)
		for (final MMenuElement child : menu.getChildren()) {
			if (child.getVisibleWhen() != null) {
				ctx.runAndTrack(new RunAndTrack() {
					@Override
					public boolean changed(IEclipseContext context) {

						if (!menu.isToBeRendered() || !menu.isVisible()
								|| menu.getWidget() == null) {
							System.err.println("remove context tracker");
							return false;
						}

						final boolean rc = ContributionsAnalyzer.isVisible(
								(MCoreExpression) child.getVisibleWhen(),
								eContext);
						Runnable runnable = new Runnable() {

							@Override
							public void run() {
								child.setToBeRendered(rc);
							}
						};
						execService.invokeLater(runnable);

						return true;
					}
				});
			}
		}
		// Then add contributions using contribution service:
		contributionService
				.addContributions((MMenu) (MElementContainer<?>) element);
	}

	@Override
	public void disposeWidget(MUIElement element) {
		contributionService
				.removeContributions((MMenu) (MElementContainer<?>) element);
	}

	@Override
	public void addChildGui(MUIElement child,
			MElementContainer<MUIElement> parent) {
		createWidget(child, parent);
	}

	@Override
	public void removeChildGui(MUIElement element,
			MElementContainer<MUIElement> parent) {
		MenuItem childItem = (MenuItem) element.getWidget();

		if (parent.getWidget() instanceof MenuBar) {
			MenuBar bar = (MenuBar) parent.getWidget();
			bar.removeItem(childItem);
		} else if (parent.getWidget() instanceof MenuItem) {
			MenuItem parentItem = (MenuItem) parent.getWidget();
			parentItem.removeChild(childItem);
		}
	}

	@Override
	public void setVisible(MUIElement changedElement, boolean visible) {
		if (changedElement.getWidget() instanceof MenuBar)
			((MenuBar) changedElement.getWidget()).setVisible(visible);
		else if (changedElement.getWidget() instanceof MenuItem)
			((MenuItem) changedElement.getWidget()).setVisible(visible);
	}

	@PostConstruct
	public void postConstruct() {
		eventBroker.subscribe(UIEvents.UILabel.TOPIC_ALL, itemUpdater);
		eventBroker.subscribe(UIEvents.Menu.TOPIC_ENABLED, itemUpdater);
		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN,
				childrenMoveUpdater);
	}

	@PreDestroy
	public void preDestroy() {
		eventBroker.unsubscribe(itemUpdater);
		eventBroker.unsubscribe(childrenMoveUpdater);
	}

	private EventHandler childrenMoveUpdater = new EventHandler() {
		@SuppressWarnings("unchecked")
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu))
				return;

			MElementContainer<MUIElement> menu = (MElementContainer<MUIElement>) event
					.getProperty(UIEvents.EventTags.ELEMENT);

			String type = (String) event.getProperty(UIEvents.EventTags.TYPE);

			// on move, we unrender an render the UI again
			//
			if (UIEvents.EventTypes.MOVE.equals(type)) {
				for (MUIElement item : menu.getChildren()) {
					removeChildGui(item, menu);
				}

				for (MUIElement item : menu.getChildren()) {
					createWidget(item, menu);
				}
			}
		}
	};

	private EventHandler itemUpdater = new EventHandler() {
		public void handleEvent(Event event) {
			// Ensure that this event is for a MMenuItem
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu))
				return;

			MMenu model = (MMenu) event.getProperty(UIEvents.EventTags.ELEMENT);
			if (model.getWidget() instanceof MenuItem) {
				MenuItem ici = (MenuItem) model.getWidget();
				if (ici == null) {
					return;
				}

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (UIEvents.UILabel.LABEL.equals(attName)) {
					ici.setText((String) newValue);
				} else if (UIEvents.UILabel.ICONURI.equals(attName)) {
					Resource icon = ResourceHelper
							.createResource((String) newValue);
					ici.setIcon(icon);
				} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
					ici.setDescription((String) newValue);
				} else if (UIEvents.Item.ENABLED.equals(attName)) {
					ici.setEnabled((boolean) newValue);
				}
			} else if (model.getWidget() instanceof MenuBar) {
				MenuBar ici = (MenuBar) model.getWidget();
				if (ici == null) {
					return;
				}

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (UIEvents.UILabel.ICONURI.equals(attName)) {
					Resource icon = ResourceHelper
							.createResource((String) newValue);
					ici.setIcon(icon);
				} else if (UIEvents.UILabel.TOOLTIP.equals(attName)) {
					ici.setDescription((String) newValue);
				} else if (UIEvents.Item.ENABLED.equals(attName)) {
					ici.setEnabled((boolean) newValue);
				} else if (UIEvents.Menu.ENABLED.equals(attName)) {
					ici.setEnabled((boolean) newValue);
				}
			}
		}
	};
}
