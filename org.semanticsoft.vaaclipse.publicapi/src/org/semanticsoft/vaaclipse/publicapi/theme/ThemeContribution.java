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

/**
 * @author rushan
 *
 */
public interface ThemeContribution extends ThemeEntry {
	public static final String MAIN_CSS = "MAIN_CSS";
	public static final String INHERITED_IMPORTS = "INHERITED_IMPORTS";

	String getInsertPosition();
}
