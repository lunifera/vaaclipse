package org.semanticsoft.vaaclipse.publicapi.events;

import org.eclipse.emf.ecore.EObject;

/**
 * A session singleton service, which returns the EObject for a given component.
 * <p>
 * It also allows different EMF-based UIModel-To-Widget-Frameworks like ECView
 * to participate by {@link #addThirdParty(IWidgetModelAssociations)}.
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
	EObject getElement(Object component);

	/**
	 * Returns the UI Widget for the given model element.
	 * 
	 * @param element
	 * @return
	 */
	Object getWidget(EObject element);

	/**
	 * Adds a thirdparty {@link IWidgetModelAssociations} to this service.
	 * 
	 * @param e
	 * @return
	 */
	boolean addThirdParty(IWidgetModelAssociations e);

	/**
	 * Removes a thirdparty {@link IWidgetModelAssociations} from this service.
	 * 
	 * @param o
	 * @return
	 */
	boolean removeThirdParty(IWidgetModelAssociations o);

}
