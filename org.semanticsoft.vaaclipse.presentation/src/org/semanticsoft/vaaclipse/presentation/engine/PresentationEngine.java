/*******************************************************************************
 * Copyright (c) 2011 Kai Toedter and others.
 * 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Kai Toedter - initial API and implementation
 ******************************************************************************/

package org.semanticsoft.vaaclipse.presentation.engine;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.MElementContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;

/**
 * This engine was adopted from Kai Toedter's generic renderer project. I place
 * it in vaaclipse packages temproraly - until the generic renderer will be the
 * part of eclipse project.
 * 
 * @author Kai Toedter
 */
@SuppressWarnings("restriction")
public interface PresentationEngine extends IPresentationEngine {
	public Object createGui(MUIElement element,
			MElementContainer<MUIElement> parent, IEclipseContext parentContext);
}
