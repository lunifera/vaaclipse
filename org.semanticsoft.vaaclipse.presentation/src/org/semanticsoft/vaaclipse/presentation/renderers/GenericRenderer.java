package org.semanticsoft.vaaclipse.presentation.renderers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;

/**
 * The begginning point of this renderer interface is a Kai Toedter's
 * GenericRenderer class. I place it in vaaclipse packages temproraly - until
 * the generic renderer will be the part of eclipse project.
 * 
 * @author Kai Toedter
 */
public interface GenericRenderer {
	void createWidget(MUIElement element, MElementContainer<MUIElement> parent);

	void disposeWidget(MUIElement element);

	void processContents(MElementContainer<MUIElement> element);

	void addChildGui(MUIElement child, MElementContainer<MUIElement> element);

	void removeChildGui(MUIElement element, MElementContainer<MUIElement> parent);

	boolean isLazy();

	void hookControllerLogic(MUIElement element);

	IEclipseContext getContext(MUIElement part);

	void setVisible(MUIElement changedElement, boolean visible);

	void bindWidget(MUIElement element);

	void unbindWidget(MUIElement element);
}
