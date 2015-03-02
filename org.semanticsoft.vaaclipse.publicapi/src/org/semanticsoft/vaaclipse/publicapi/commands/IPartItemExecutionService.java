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
package org.semanticsoft.vaaclipse.publicapi.commands;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.menu.MItem;

/**
 * {@link MPart parts} provide this service by their eclipse context. It can be
 * used to execute {@link MItem} directly.
 */
public interface IPartItemExecutionService {

	/**
	 * Executes the given item in the context of the container part.
	 * 
	 * @param mItem
	 * @return true if the item can be executed. False otherwise.
	 */
	boolean canExecuteItem(MItem mItem);
	
	/**
	 * Executes the given item in the context of the container part.
	 * 
	 * @param mItem
	 */
	void executeItem(MItem mItem);

}
