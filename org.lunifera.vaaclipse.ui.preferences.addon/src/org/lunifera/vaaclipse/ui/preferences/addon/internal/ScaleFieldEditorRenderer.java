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

import org.lunifera.vaaclipse.ui.preferences.model.ScaleFieldEditor;

import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Slider.ValueOutOfBoundsException;

/**
 * @author rushan
 *
 */
public class ScaleFieldEditorRenderer extends FieldEditorRenderer<Integer> {

	@Inject
	ScaleFieldEditor editor;

	Slider slider;

	@Override
	public void render() {
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		layout.addComponent(new Label(editor.getLabel()));
		slider = new Slider();
		slider.setWidth("100%");
		if (editor.getMaxValue() != null)
			slider.setMax(editor.getMaxValue().doubleValue());
		if (editor.getMinValue() != null)
			slider.setMin(editor.getMinValue().doubleValue());

		try {
			slider.setValue(getValue().doubleValue());
		} catch (ValueOutOfBoundsException vo) {

		}

		slider.setOrientation(SliderOrientation.HORIZONTAL);
		layout.addComponent(slider);
		this.component = layout;
	}

	@Override
	public Integer getValue() {
		return getPreferences().getInt(editor.getPreferenceName(),
				editor.getDefaultValueTyped());
	}

	@Override
	public void setValue(Integer value) {
		this.getPreferences().putInt(editor.getPreferenceName(), value);
	}

	@Override
	public void save() {
		if (slider.getValue() != null) {
			setValue(slider.getValue().intValue());
		}
	}

}
