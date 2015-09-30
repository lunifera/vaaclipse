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
package org.lunifera.vaaclipse.ui.preferences.addon.internal.service;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.osgi.service.prefs.Preferences;
import org.osgi.service.prefs.PreferencesService;
import org.semanticsoft.vaaclipse.publicapi.preferences.IPreferenceProvider;

public class PreferenceProvider implements IPreferenceProvider {

	@Inject
	@Named("userId")
	@Optional
	String userId;

	@Inject
	private PreferencesService delegate;

	@PostConstruct
	public void init() {
		if (userId == null) {
			userId = UUID.randomUUID().toString();
		}
	}

	@Override
	public Preferences getSystemPreferences() {
		return delegate.getSystemPreferences();
	}

	@Override
	public Preferences getUserPreferences() {
		return getUserPreferences(userId);
	}

	@Override
	public Preferences getUserPreferences(String userId) {
		return delegate.getUserPreferences(userId);
	}

}
