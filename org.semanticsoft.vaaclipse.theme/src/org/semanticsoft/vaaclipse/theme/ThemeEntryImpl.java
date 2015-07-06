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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rushan
 *
 */
public class ThemeEntryImpl {
	protected String id;
	protected String webId;
	protected String cssUri;
	protected List<String> resourceUri = new ArrayList<String>();

	public ThemeEntryImpl(String id) {
		this.id = id;
		this.webId = this.id.replaceAll("\\.", "-");
	}

	public String getId() {
		return id;
	}

	public String getWebId() {
		return webId;
	}

	public String getCssUri() {
		return cssUri;
	}

	public void setCssUri(String cssUri) {
		this.cssUri = cssUri;
	}

	public List<String> getResourceLocationURIs() {
		return Collections.unmodifiableList(this.resourceUri);
	}

	public void addResourceUri(String resourceUri) {
		resourceUri = processUri(resourceUri);
		this.resourceUri.add(resourceUri);
	}

	private String processUri(String uri) {
		if (uri == null)
			return null;

		uri = uri.trim();
		if (uri.length() > 0) {
			char last = uri.charAt(uri.length() - 1);
			if (last != '/') {
				uri += "/";
			}
		}
		return uri;
	}
}
