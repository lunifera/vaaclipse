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

package org.lunifera.vaaclipse.ui.preferences.addon;

import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;

public interface PreferencesAuthorization {

	/**
	 * Returns true if the preference page may be showed for the current user
	 * 
	 * @param pageId
	 * @param user
	 * @return
	 **/
	boolean isAllowed(PreferencesCategory category);

	boolean exportAllowed();

	boolean importAllowed();
}
