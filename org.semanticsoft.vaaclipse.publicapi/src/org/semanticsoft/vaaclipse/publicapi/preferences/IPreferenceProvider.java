/**
 * Copyright (c) 2011 - 2014, Lunifera GmbH (Gross Enzersdorf), Loetz KG (Heidelberg)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * 		Florian Pirchner - Initial implementation
 */
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
