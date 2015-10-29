package org.semanticsoft.vaaclipse.publicapi.resources;

import com.vaadin.server.ThemeResource;

/**
 * @author rushan
 */
public class ResourceHelper {
	public static ThemeResource createResource(String path) {
		if (path == null || path.trim().equals("")) {
			return null;
		}
		if (path.startsWith("platform:/plugin/"))
			return BundleResource.valueOf(path);
		else
			return new ThemeResource(path);
	}
}
