package org.semanticsoft.vaaclipse.publicapi.preferences;

import org.osgi.service.prefs.Preferences;

/**
 * A service that provides preferences.
 */
public interface IPreferenceProvider {
	
	/**
	 * Returns the system preferences.
	 * 
	 * @return
	 */
	Preferences getSystemPreferences();

	/**
	 * Returns the preferences for the current user.
	 * 
	 * @return
	 */
	Preferences getUserPreferences();

	/**
	 * Returns the preferences for the given user.
	 * 
	 * @return
	 */
	Preferences getUserPreferences(String userId);

}
