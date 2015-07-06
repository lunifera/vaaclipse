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

import java.util.List;

/**
 * @author rushan
 *
 */
public interface ThemeEntry {
	/**
	 * Theme id
	 */
	String getId();

	/**
	 * Web id - this id is in uri (replace all dot's, etc)
	 */
	String getWebId();

	String getCssUri();

	List<String> getResourceLocationURIs();
}
