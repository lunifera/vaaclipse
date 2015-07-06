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

package org.semanticsoft.e4.injector.objectsuppliers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.internal.extensions.EventObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;

public class CustomEventObjectSupplier extends EventObjectSupplier {

	@Inject
	@Named("e4ApplicationInstanceId")
	private String eAI;

	@Override
	protected String getTopic(IObjectDescriptor descriptor) {
		String topic = super.getTopic(descriptor);
		return eAI == null ? topic : eAI + "/" + topic;
	}

}
