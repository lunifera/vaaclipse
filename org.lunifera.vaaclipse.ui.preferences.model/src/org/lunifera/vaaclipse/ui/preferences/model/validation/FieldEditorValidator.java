/**
 *
 * $Id$
 */
package org.lunifera.vaaclipse.ui.preferences.model.validation;

import org.osgi.service.prefs.Preferences;

/**
 * A sample validator interface for {@link org.lunifera.vaaclipse.ui.preferences.model.FieldEditor}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface FieldEditorValidator {
	boolean validate();

	boolean validateLabel(String value);
	boolean validatePreferenceName(String value);
	boolean validateDefaultValue(String value);
	boolean validateDefaultValueTyped(Object value);
	boolean validateBundle(String value);
	boolean validatePreferences(Preferences value);
	boolean validateEquinoxPath(String value);
}