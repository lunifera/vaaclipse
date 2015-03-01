/**
 * 
 */
package org.lunifera.vaaclipse.ui.preferences.addon.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.emf.common.util.EList;
import org.lunifera.vaaclipse.ui.preferences.addon.PreferencesAuthorization;
import org.lunifera.vaaclipse.ui.preferences.addon.PreferencesEvents;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.exception.ValidationFailedException;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.util.ModelHelper;
import org.lunifera.vaaclipse.ui.preferences.addon.internal.util.PrefHelper;
import org.lunifera.vaaclipse.ui.preferences.model.FieldEditor;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesCategory;
import org.lunifera.vaaclipse.ui.preferences.model.PreferencesPage;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;
import org.semanticsoft.vaaclipse.publicapi.resources.BundleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import e4modelextension.VaaclipseApplication;

/**
 * @author rushan
 *
 */
public class PreferencesDialog {

	static final String PAGE_HEADER_ERROR_TEXT = "page-header-error-text";

	@Inject
	IEventBroker eventBroker;

	@Inject
	VaaclipseApplication app;

	@Inject
	IEclipseContext context;

	@Inject
	@Optional
	PreferencesCategory selectedCategory;

	@Inject
	@Optional
	PreferencesAuthorization authService;

	Logger logger = LoggerFactory.getLogger(PreferencesDialog.class);

	@Inject
	UI ui;

	TextField filterField = new TextField();

	private Tree tree;

	private Window window;

	HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

	private Panel treePanel;

	private VerticalLayout rightSide;

	private CssLayout leftSide;

	private Button filterButton = new Button();
	private Button clearFilterButton = new Button();

	private CssLayout pageHeader;

	private CssLayout pageContent;

	private CssLayout pageBottom;

	private Button restoreDefaults;

	private Button apply;

	private VerticalLayout root;

	private CssLayout dlgButtonPanel;

	private Button cancelButton;

	private Button okButton;

	private Label pageHeaderText;

	private BundleContext bundleContext;

	private String errorMessage;

	List<PreferencesPage> visitedPages = new ArrayList<>();

	private HashSet<PreferencesCategory> visibleCategories;

	private List<PreferencesCategory> categoryList = new ArrayList<>(100);

	public Window getWindow() {
		return window;
	}

	public PreferencesCategory getSelectedCategory() {
		return selectedCategory;
	}

	public PreferencesPage getCurrentPage() {
		return selectedCategory.getPage();
	}

	@PostConstruct
	public void init() {
		if (selectedCategory == null
				&& !app.getPreferencesCategories().isEmpty()) {
			selectedCategory = app.getPreferencesCategories().get(0);
		}

		createWindow();
		createSearchField();
		createTree();

		addListeners();
	}

	public void createWindow() {
		window = new Window();
		window.setCaption("Preferences");
		window.addStyleName("preferences-dialog");
		window.setWidth("800px");
		window.setHeight("600px");

		splitPanel.setSizeFull();

		dlgButtonPanel = new CssLayout();
		dlgButtonPanel.setWidth("100%");
		dlgButtonPanel.addStyleName("dlg-button-panel");

		// dlgButtonPanel.addComponent(new Label("<hr/>", ContentMode.HTML));

		cancelButton = new Button("Cancel");
		cancelButton.addStyleName("cancel-button");
		dlgButtonPanel.addComponent(cancelButton);
		okButton = new Button("OK");
		okButton.addStyleName("ok-button");
		dlgButtonPanel.addComponent(okButton);

		root = new VerticalLayout();
		root.setSizeFull();
		window.setContent(root);
		root.addComponent(splitPanel);
		root.addComponent(dlgButtonPanel);
		root.setExpandRatio(splitPanel, 10);
		root.setExpandRatio(dlgButtonPanel, 0);

		treePanel = new Panel();
		treePanel.addStyleName("categories-panel");
		treePanel.addStyleName("borderless");
		treePanel.setSizeFull();

		leftSide = new CssLayout();
		leftSide.addStyleName("categories");
		leftSide.setSizeFull();
		// filterField.setWidth("70%");
		filterField.addStyleName("categories-filter");
		leftSide.addComponent(filterField);

		filterButton
				.setIcon(BundleResource
						.valueOf("platform:/plugin/org.lunifera.vaaclipse.ui.preferences.addon/img/find.png"));
		filterButton.addStyleName("vaaclipsebutton");
		filterButton.addStyleName("icon-only");
		filterButton.addStyleName("filter-category-button");

		clearFilterButton
				.setIcon(BundleResource
						.valueOf("platform:/plugin/org.lunifera.vaaclipse.ui.preferences.addon/img/clear.png"));
		clearFilterButton.addStyleName("vaaclipsebutton");
		clearFilterButton.addStyleName("icon-only");
		clearFilterButton.addStyleName("clear-filter-button");

		leftSide.addComponent(clearFilterButton);
		leftSide.addComponent(filterButton);
		leftSide.addComponent(treePanel);

		rightSide = new VerticalLayout();
		rightSide.setSizeFull();
		// rightSide.setMargin(new MarginInfo(true, true, false, true));

		pageHeader = new CssLayout();
		pageHeader.addStyleName("page-header-panel");
		pageHeader.setWidth("100%");
		pageHeaderText = new Label("Page");
		pageHeaderText.addStyleName("page-header-text");
		pageHeader.addComponent(pageHeaderText);
		rightSide.addComponent(pageHeader);

		pageContent = new CssLayout();
		// pageContent.setSizeFull();
		Panel pageContentPanel = new Panel(pageContent);// wrap page content to
														// panel - if content is
														// too large, scrolling
														// needed
		pageContentPanel.setSizeFull();
		rightSide.addComponent(pageContentPanel);

		pageBottom = new CssLayout();
		pageBottom.addStyleName("page-bottom-panel");
		pageBottom.setWidth("100%");
		rightSide.addComponent(pageBottom);

		apply = new Button("Apply");
		apply.addStyleName("apply");
		pageBottom.addComponent(apply);

		restoreDefaults = new Button("RestoreDefaults");
		restoreDefaults.addStyleName("restore-defaults");
		pageBottom.addComponent(restoreDefaults);

		splitPanel.addComponent(leftSide);
		splitPanel.addComponent(rightSide);
		splitPanel.setSplitPosition(30, Unit.PERCENTAGE);

		rightSide.setExpandRatio(pageHeader, 0);
		rightSide.setExpandRatio(pageContentPanel, 1);
		rightSide.setExpandRatio(pageBottom, 0);

		clearFilterButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				filterField.setValue("");
				refreshTree();
			}
		});

		filterButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				refreshTree();
			}
		});
	}

	public void createSearchField() {
		filterField.addTextChangeListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				refreshTree();
			}
		});
	}

	private void createTree() {
		tree = new Tree();
		tree.setSizeFull();
		tree.setImmediate(true);
		treePanel.setContent(tree);

		tree.addContainerProperty("name", String.class, "NoName");
		tree.setItemCaptionPropertyId("name");

		ModelHelper.buildChildCategoryListIncludeThisList(
				app.getPreferencesCategories(), categoryList);

		refreshTree();

		tree.addItemClickListener(new ItemClickEvent.ItemClickListener() {

			public void itemClick(final ItemClickEvent event) {
				if (event.getButton() == MouseButton.LEFT) {
					PreferencesCategory selectedCat = (PreferencesCategory) event
							.getItemId();
					if (selectedCat != null) {
						openPreferencePageForCategory(selectedCat);
					}
				}
			}
		});

		if (selectedCategory != null) {
			tree.select(selectedCategory);
			openPreferencePageForCategory(selectedCategory);
		}
	}

	private void refreshTree() {
		tree.removeAllItems();

		EList<PreferencesCategory> catList = app.getPreferencesCategories();

		markVisibility();

		fillCategories(catList, null);

		for (Object id : tree.rootItemIds()) {
			tree.expandItemsRecursively(id);
		}
	}

	private void markVisibility() {
		visibleCategories = new HashSet<PreferencesCategory>();

		String filterValue = filterField.getValue();
		if (filterValue.isEmpty()) {
			return;
		}
		for (PreferencesCategory category : categoryList) {
			if (category != null && category.getName().contains(filterValue)) {
				do {
					visibleCategories.add(category);
					category = category.getParentCategory();
				} while (category != null);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void fillCategories(List<PreferencesCategory> list,
			PreferencesCategory parentCat) {
		String search = filterField.getValue().trim();
		for (PreferencesCategory c : list) {

			if (authService != null && c.getParentCategory() != null
					&& "user".equals(c.getParentCategory().getId())
					&& !authService.isAllowed(c)) {
				continue;
			}

			if (c.getName() == null)
				c.setName("No Name");
			if (filterField.getValue().isEmpty()
					|| visibleCategories.contains(c)) {

				Item catItem = tree.addItem(c);
				catItem.getItemProperty("name").setValue(c.getName());
				if (c.getChildCategories().isEmpty())
					tree.setChildrenAllowed(c, false);

				if (parentCat != null)
					tree.setParent(c, parentCat);

				fillCategories(c.getChildCategories(), c);
			}
		}
	}

	private void openPreferencePageForCategory(PreferencesCategory selectedCat) {
		selectedCategory = selectedCat;
		refreshHeaderMessage();

		pageContent.removeAllComponents();

		if (selectedCat.getPage() != null) {
			IEclipseContext pageContext = context.createChild();
			pageContext.set(CssLayout.class, pageContent);
			pageContext.set(PreferencesPage.class, selectedCat.getPage());
			PreferencesPageRenderer pageRenderer = ContextInjectionFactory
					.make(PreferencesPageRenderer.class, pageContext);
			pageRenderer.render();
			visitedPages.add(selectedCat.getPage());
		} else if (!selectedCat.getChildCategories().isEmpty()) {
			pageContent
					.addComponent(new Label(
							"Expand the tree to edit a preferences for a specific feature"));
		}
	}

	private void addListeners() {

		restoreDefaults.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				restoreDefaults();
			}
		});

		apply.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				applyPressed();
			}
		});

		okButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				okPressed();
			}
		});

		cancelButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				cancelPressed();
			}
		});
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		refreshHeaderMessage();
	}

	public void clearErrorMessage() {
		this.errorMessage = null;
		refreshHeaderMessage();
	}

	public void refreshHeaderMessage() {
		if (errorMessage != null) {
			pageHeaderText.addStyleName(PAGE_HEADER_ERROR_TEXT);
			pageHeaderText.setValue(errorMessage);
		} else {
			pageHeaderText.removeStyleName(PAGE_HEADER_ERROR_TEXT);
			pageHeaderText.setValue(selectedCategory.getName());
		}
	}

	private void restoreDefaults() {
		PreferencesPage currentPage = getCurrentPage();
		if (currentPage != null) {
			try {
				restoreDefaultsOnPage(currentPage);
			} catch (Exception e) {
				Notification.show("Error restoring defaults",
						"Restoring defaults on this page failed",
						Notification.Type.ERROR_MESSAGE);
				return;
			}
			fireEvent(PreferencesEvents.PREFERENCES_TO_DEFAULTS, currentPage);

			// Refresh the current page (it should be rerendered)
			openPreferencePageForCategory(getSelectedCategory());
		}
	}

	private void okPressed() {

		Exception exception = null;
		for (PreferencesPage page : visitedPages) {
			try {
				applyChangesOnPage(page);
			} catch (ValidationFailedException validationException) {
				setErrorMessage(validationException.getLocalizedMessage());
				Notification.show("Validation failed",
						validationException.getLocalizedMessage(),
						Notification.Type.WARNING_MESSAGE);
				return;
			} catch (Exception e) {
				exception = e;
			}
		}
		if (exception != null) {
			String errorMessage = "Saving changes was failed on some pages";
			setErrorMessage(errorMessage);
			Notification.show("Error apply changes", errorMessage,
					Notification.Type.ERROR_MESSAGE);
			return;
		} else {
			fireEvent(PreferencesEvents.PREFERENCES_APPLIED,
					(PreferencesPage[]) visitedPages
							.toArray(new PreferencesPage[visitedPages.size()]));
		}

		clearErrorMessage();

		ui.removeWindow(window);
	}

	private void cancelPressed() {
		ui.removeWindow(window);
	}

	private void applyPressed() {
		PreferencesPage currentPage = getCurrentPage();
		if (currentPage != null) {
			try {
				applyChangesOnPage(currentPage);
			} catch (ValidationFailedException validationException) {
				setErrorMessage(validationException.getLocalizedMessage());
				Notification.show("Validation failed",
						"The preferences is not correct",
						Notification.Type.WARNING_MESSAGE);
				return;
			} catch (Exception e) {
				setErrorMessage("Applying changes on this page failed");
				Notification.show("Error apply changes",
						"Applying changes on this page failed",
						Notification.Type.ERROR_MESSAGE);
				return;
			}
			clearErrorMessage();
			fireEvent(PreferencesEvents.PREFERENCES_APPLIED, currentPage);
		}
	}

	private void applyChangesOnPage(PreferencesPage page)
			throws BackingStoreException, ValidationFailedException {
		PreferencesPageRenderer pageRenderer = (PreferencesPageRenderer) page
				.getRenderer();

		pageRenderer.validate();

		try {
			pageRenderer.save();
		} catch (Exception e) {
			logger.error(
					"Error copy changes from UI to preferences for category {}",
					selectedCategory.getName(), e);
			throw e;
		}

		try {
			PrefHelper.flush(page);
		} catch (BackingStoreException e) {
			logger.error("Error flushing changes for category {}", page
					.getCategory().getName(), e);
			throw e;
		}
	}

	private void restoreDefaultsOnPage(PreferencesPage page)
			throws BackingStoreException {

		try {
			for (FieldEditor<?> fieldEditor : page.getChildren()) {
				String defaultValue = fieldEditor.getDefaultValue();
				if (defaultValue != null)
					((Preferences) fieldEditor.getPreferences()).put(
							fieldEditor.getPreferenceName(), defaultValue);
			}
		} catch (Exception e) {
			logger.error(
					"Error copy default values from model to preferences for category {}",
					page.getCategory().getName(), e);
			throw e;
		}

		try {
			PrefHelper.flush(page);
		} catch (BackingStoreException e) {
			logger.error("Error flushing changes for category {}", page
					.getCategory().getName(), e);
			throw e;
		}
	}

	private void fireEvent(String topic, PreferencesPage... pages) {
		eventBroker.send(topic, pages);
	}
}
