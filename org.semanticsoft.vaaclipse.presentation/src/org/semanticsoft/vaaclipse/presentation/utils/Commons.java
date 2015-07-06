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
package org.semanticsoft.vaaclipse.presentation.utils;

/**
 * @author rushan
 *
 */
public class Commons {
	public static String trim(String value) {
		if (value != null) {
			value = value.trim();
			if (value.isEmpty())
				value = null;
		}
		return value;
	}
}
