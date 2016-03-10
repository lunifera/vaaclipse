/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Florian Pirchner - moved to lunifera namespace for Vaaclipse
 *******************************************************************************/
package org.semanticsoft.vaaclipse.publicapi.perspective;

import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;

/**
 * The workbench's user based registry of perspectives.
 * <p>
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IPerspectiveHandler {

	/**
	 * The preference key to access the default perspective.
	 */
	public static final String PREF_DEFAULT_PERSPECTIVE = "org.lunifera.vaaclipse.addons.default.perspective";

	/**
	 * Is used to save the original perspective in the persistedState of a
	 * perspective.
	 */
	public static final String PROP_ORIGINAL_PERSPECTIVE = "org.lunifera.vaaclipse.addons.original.perspective";

	/**
	 * Only perspectives tagged with this tag can be deleted by an user.
	 */
	public static final String TAG_CREATED_BY_USER = "org.lunifera.vaaclipse.addons.perspective.createdByUser";

	/**
	 * Clones an existing perspective.
	 * 
	 * @param label
	 *            the label assigned to the cloned perspective
	 * @param desc
	 *            the perspective to clone
	 * @return the cloned perspective descriptor
	 * @throws IllegalArgumentException
	 *             if there is already a perspective with the given id
	 */
	public MPerspective clonePerspective(String userId, String label,
			MPerspective perspective) throws IllegalArgumentException;

	/**
	 * Returns true, if the user can delete the perspective.
	 * 
	 * @param userId
	 *            the userId
	 * @param persp
	 *            the perspective to delete
	 */
	public boolean canDeletePerspective(String userId, MPerspective perspective);

	/**
	 * Deletes a perspective.
	 * 
	 * @param persp
	 *            the perspective to delete
	 * @return true if the perspective could be deleted
	 */
	public boolean deletePerspective(String userId, MPerspective perspective);

	/**
	 * Finds and returns the registered perspective with the given perspective
	 * id.
	 *
	 * @param perspectiveId
	 *            the perspective id
	 * @return the perspective, or <code>null</code> if none
	 * @see MPerspective#getId
	 */
	public MPerspective findPerspectiveWithId(String perspectiveId);

	// /**
	// * Finds and returns the registered perspective with the given label.
	// *
	// * @param label
	// * the label
	// * @return the perspective, or <code>null</code> if none
	// * @see MPerspective#getLabel
	// */
	// public MPerspective findPerspectiveWithLabel(String label);

	public String getDefaultPerspective();

	public String getDefaultPerspective(String userId);

	// /**
	// * Returns a list of the perspectives known to the workbench.
	// *
	// * @return a list of perspectives
	// */
	// public MPerspective[] getPerspectives();

	/**
	 * Sets the default perspective for the system to the given perspective id.
	 * If non-<code>null</code>, the id must correspond to a perspective
	 * extension within the workbench's perspective registry.
	 * <p>
	 * A <code>null</code> id indicates no default perspective.
	 * </p>
	 *
	 * @param id
	 *            a perspective id, or <code>null</code>
	 */
	public void setSystemDefaultPerspective(String id);

	/**
	 * Sets the default perspective for the workbench for the current User to
	 * the given perspective id. If non-<code>null</code>, the id must
	 * correspond to a perspective extension within the workbench's perspective
	 * registry.
	 * <p>
	 * A <code>null</code> id indicates no default perspective.
	 * </p>
	 *
	 * @param id
	 *            a perspective id, or <code>null</code>
	 */
	public void setUserDefaultPerspective(String userId, String id);

	/**
	 * Returns true, if the perspective can be reverted.
	 * 
	 * @param persp
	 *            the perspective to delete
	 */
	public boolean canRevertPerspective(MPerspective perspective);

	/**
	 * Reverts a perspective back to its original definition as specified in the
	 * plug-in manifest.
	 * 
	 * @param perspToRevert
	 *            the perspective to revert
	 * 
	 * @since 3.0
	 */
	public void revertPerspective(MPerspective perspectiveToRevert);

}
