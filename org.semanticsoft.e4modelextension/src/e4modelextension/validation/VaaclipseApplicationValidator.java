/**
 *
 * $Id$
 */
package e4modelextension.validation;

import e4modelextension.EditorPartDescriptor;

import org.eclipse.emf.common.util.EList;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;

/**
 * A sample validator interface for
 * {@link e4modelextension.VaaclipseApplication}. This doesn't really do
 * anything, and it's not a real EMF artifact. It was generated by the
 * org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's
 * code generator can be extended. This can be disabled with -vmargs
 * -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface VaaclipseApplicationValidator {
	boolean validate();

	boolean validateEditorDescriptors(EList<EditorPartDescriptor> value);

	boolean validatePreferencesCategories(EList<PreferencesCategory> value);

	boolean validatePreferencesPages(EList<PreferencesPage> value);
}
