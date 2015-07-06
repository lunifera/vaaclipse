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
package org.semanticsoft.vaaclipse.publicapi.app;

import org.eclipse.e4.core.contexts.IEclipseContext;

/**
 * @author rushan
 *
 */
public class ThreadLocals {
	private static final ThreadLocal<IEclipseContext> eclipseContext = new ThreadLocal<IEclipseContext>();

	public static IEclipseContext getRootContext() {
		return eclipseContext.get();
	}

	public static void setRootContext(IEclipseContext context) {
		eclipseContext.set(context);
	}

}
