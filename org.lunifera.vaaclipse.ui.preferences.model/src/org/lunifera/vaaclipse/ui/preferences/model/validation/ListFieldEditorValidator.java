/**
 *
 * $Id$
 */
package org.lunifera.vaaclipse.ui.preferences.model.validation;

import org.eclipse.emf.common.util.EList;

import org.lunifera.vaaclipse.ui.preferences.model.Entry;

/**
 * A sample validator interface for
 * {@link org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor}. This
 * doesn't really do anything, and it's not a real EMF artifact. It was
 * generated by the org.eclipse.emf.examples.generator.validator plug-in to
 * illustrate how EMF's code generator can be extended. This can be disabled
 * with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ListFieldEditorValidator {
	boolean validate();

	boolean validateEntries(EList<Entry> value);
}
