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
package org.semanticsoft.vaaclipse.publicapi.model;

/**
 * @author rushan
 *
 */
public class Tags {
	/**
	 * Vaadin specific tag to inhibit resizing of sashes if needed
	 */
	public static final String NO_RESIZE = "NoResize";

	public static final String MAIN_WINDOW = "mainWindow";

	/**
	 * Perspective's tag to show perspective switch button in perspective switch
	 * panel
	 */
	public static final String HAS_SWITCH_BUTTON = "HasSwitchButton";

	/**
	 * Tag to show text of perspective switch buttons in perspective switcher
	 * panel
	 */
	public static final String ICONS_ONLY = "IconsOnly";
}
