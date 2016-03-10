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

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.semanticsoft.vaaclipse.presentation.renderers.GenericRenderer;

@SuppressWarnings("restriction")
public interface RendererFactory {
	public GenericRenderer getRenderer(MUIElement uiElement);
}
