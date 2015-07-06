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
package e4modelextension;

import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Editor Part Descriptor</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link e4modelextension.EditorPartDescriptor#getUriFilter <em>Uri Filter
 * </em>}</li>
 * <li>{@link e4modelextension.EditorPartDescriptor#getPartAddingLogicUri <em>
 * Part Adding Logic Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @see e4modelextension.E4modelextensionPackage#getEditorPartDescriptor()
 * @model
 * @generated
 */
public interface EditorPartDescriptor extends EObject, MPartDescriptor {
	/**
	 * Returns the value of the '<em><b>Uri Filter</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uri Filter</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Uri Filter</em>' attribute.
	 * @see #setUriFilter(String)
	 * @see e4modelextension.E4modelextensionPackage#getEditorPartDescriptor_UriFilter()
	 * @model
	 * @generated
	 */
	String getUriFilter();

	/**
	 * Sets the value of the '
	 * {@link e4modelextension.EditorPartDescriptor#getUriFilter
	 * <em>Uri Filter</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Uri Filter</em>' attribute.
	 * @see #getUriFilter()
	 * @generated
	 */
	void setUriFilter(String value);

	/**
	 * Returns the value of the '<em><b>Part Adding Logic Uri</b></em>'
	 * attribute. The default value is
	 * <code>"bundleclass://org.semanticsoft.e4extension/org.semanticsoft.e4extension.shared.DefaultPartAddingLogic"</code>
	 * . <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Part Adding Logic Uri</em>' attribute isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Part Adding Logic Uri</em>' attribute.
	 * @see #setPartAddingLogicUri(String)
	 * @see e4modelextension.E4modelextensionPackage#getEditorPartDescriptor_PartAddingLogicUri()
	 * @model default=
	 *        "bundleclass://org.semanticsoft.e4extension/org.semanticsoft.e4extension.shared.DefaultPartAddingLogic"
	 * @generated
	 */
	String getPartAddingLogicUri();

	/**
	 * Sets the value of the '
	 * {@link e4modelextension.EditorPartDescriptor#getPartAddingLogicUri
	 * <em>Part Adding Logic Uri</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Part Adding Logic Uri</em>'
	 *            attribute.
	 * @see #getPartAddingLogicUri()
	 * @generated
	 */
	void setPartAddingLogicUri(String value);

} // EditorPartDescriptor
