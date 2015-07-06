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
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import javax.inject.Inject;

import org.lunifera.vaaclipse.ui.preferences.model.Entry;
import org.lunifera.vaaclipse.ui.preferences.model.ListFieldEditor;

import com.vaadin.data.Item;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 * @author rushan
 *
 */
public abstract class ListFieldEditorRenderer extends
		FieldEditorRenderer<String> {
	@Inject
	ListFieldEditor editor;

	AbstractSelect select;

	public abstract AbstractSelect createSelect();

	@Override
	public void render() {
		select = createSelect();

		CssLayout layout = new CssLayout();
		layout.addComponent(new Label(editor.getLabel()));
		layout.addComponent(select);

		refreshSelect();
		component = layout;
	}

	protected void refreshSelect() {
		select.removeAllItems();
		for (Entry entry : editor.getEntries()) {
			Item item = select.addItem(entry.getValue());
			select.setItemCaption(entry.getValue(), entry.getName());
		}
		String value = getValue();
		if (value != null)
			select.select(value);
	}

	@Override
	public String getValue() {
		return getPreferences().get(editor.getPreferenceName(),
				editor.getDefaultValue());
	}

	@Override
	public void setValue(String value) {
		getPreferences().put(editor.getPreferenceName(), value);
	}

	@Override
	public void save() {
		if (select.getValue() != null)
			setValue(select.getValue().toString());
	}
}
