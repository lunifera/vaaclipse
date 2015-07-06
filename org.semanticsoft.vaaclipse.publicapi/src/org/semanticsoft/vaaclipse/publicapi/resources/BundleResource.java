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
package org.semanticsoft.vaaclipse.publicapi.resources;

import org.eclipse.emf.common.util.URI;

import com.vaadin.server.ThemeResource;

/**
 * @author rushan
 *
 */
public class BundleResource extends ThemeResource {
	private String resourceBundlePath;

	public static BundleResource valueOf(String resourceBundlePath) {
		String themePath = convertPath(resourceBundlePath);
		BundleResource bundleResource = new BundleResource(themePath);
		bundleResource.resourceBundlePath = resourceBundlePath;
		return bundleResource;
	}

	private BundleResource(String resourceUri) {
		super(resourceUri);
	}

	private static String convertPath(String uriString) {
		if (!uriString.startsWith("platform:/plugin/"))
			throw new IllegalArgumentException(
					"Wrong bundle resource uri: "
							+ uriString
							+ ". Bundle resource uri should start with platform:/plugin/");

		URI uri = URI.createURI(uriString);

		if (uri.segmentCount() < 2)
			throw new IllegalArgumentException();

		StringBuilder bundlePath = new StringBuilder("plugin/");

		for (int i = 1; i < uri.segmentCount(); i++) {
			bundlePath.append(uri.segment(i));
			bundlePath.append("/");
		}

		if (bundlePath.charAt(bundlePath.length() - 1) == '/')
			return bundlePath.substring(0, bundlePath.length() - 1);
		else
			return bundlePath.toString();
	}
}
