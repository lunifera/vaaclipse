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
package org.semanticsoft.vaaclipse.theme;

import org.semanticsoft.vaaclipse.publicapi.theme.ThemeContribution;

/**
 * @author rushan
 *
 */
public class ThemeContributionImpl extends ThemeEntryImpl implements
		ThemeContribution {
	private String insertPosition = "after=MAIN_CSS";

	public ThemeContributionImpl(String id) {
		super(id);
	}

	@Override
	public String getInsertPosition() {
		return insertPosition;
	}

	public void setInsertPosition(String insertPosition) {
		this.insertPosition = insertPosition;
	}
}
