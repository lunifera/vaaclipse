/**
 *
 * $Id$
 */
package org.lunifera.vaaclipse.ui.preferences.model.validation;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;

/**
 * A sample validator interface for {@link org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface PreferencesPageValidator {
	boolean validate();

	boolean validateCategory(PreferencesCategory value);
	boolean validateDescription(String value);
}
