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

package org.semanticsoft.vaaclipse.publicapi.theme;

import java.io.InputStream;
import java.util.List;

public interface Theme extends ThemeEntry {
	/**
	 * Theme label displayed to user
	 */
	String getLabel();

	/**
	 * Theme description displayed to user
	 */
	String getDescription();

	/**
	 * List of inherited themes
	 */
	List<Theme> getInheritedThemes();

	/**
	 * List of contributions to this theme
	 */
	List<ThemeContribution> getContributions();

	/**
	 * Get list of resource location URI including resource URI of this theme,
	 * contribution URIs and inherited theme's URI
	 */
	List<String> getAllResourceLocationURIs();

	/**
	 * Get CSS list including CSS of this theme, contribution CSS and inherited
	 * theme's CSS
	 */
	List<String> getAllCssURIs();

	/**
	 * Get CSS of this theme as stream
	 */
	InputStream getCssAsStream();

	/**
	 * Get theme resource as stream
	 * 
	 * @param themeResourceUri
	 *            resource uri relative to this theme
	 */
	InputStream getThemeResourceAsStream(String themeResourceUri);
}
