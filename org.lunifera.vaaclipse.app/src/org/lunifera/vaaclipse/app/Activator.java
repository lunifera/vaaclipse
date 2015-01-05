/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.lunifera.vaaclipse.app;

import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator
{
	private BundleContext context;
	private ServiceTracker<Location, Location> locationTracker;
	private static Activator activator;

	public static Activator getDefault()
	{
		return activator;
	}

	public BundleContext getContext()
	{
		return context;
	}

	@Override
	public void start(BundleContext context) throws Exception
	{
		activator = this;
		this.context = context;
	}

	@Override
	public void stop(BundleContext context) throws Exception
	{
		VaadinE4Application.getInstance().shutdown();
	}

	public Bundle getBundle()
	{
		if (context == null)
		{
			return null;
		}
		return context.getBundle();
	}

	/**
	 * @return the instance Location service
	 */
	public Location getInstanceLocation()
	{
		if (locationTracker == null)
		{
			Filter filter = null;
			try
			{
				filter = context.createFilter(Location.INSTANCE_FILTER);
			}
			catch (InvalidSyntaxException e)
			{
				// ignore this. It should never happen as we have tested the
				// above format.
			}
			locationTracker = new ServiceTracker<Location, Location>(context, filter, null);
			locationTracker.open();
		}
		return locationTracker.getService();
	}
}
