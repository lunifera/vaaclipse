package org.semanticsoft.vaaclipse.publicapi.events;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

/**
 * A session singleton service, which returns the EObject for a given component.
 */
public interface IWidgetModelAssociations {

	/**
	 * Returns the EObject for a given component. If no model element could be
	 * found for the given component, the component hierarchies parent will be
	 * used. This method is internally called with the parent as long as no
	 * model element could be found, or until the UI instance is reached.
	 * 
	 * @param component
	 * @return
	 */
	MUIElement getElement(Object component);

	/**
	 * Returns the UI Widget for the given model element.
	 * 
	 * @param element
	 * @return
	 */
	Object getWidget(MUIElement element);

}
