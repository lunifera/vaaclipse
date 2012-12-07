/**
 * 
 */
package org.semanticsoft.vaaclipsedemo.mediaplayer.handlers.medialib;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.semanticsoft.e4extension.service.EPartServiceExt;
import org.semanticsoft.vaaclipsedemo.mediaplayer.constants.MediaConstants;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.Media;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaCategory;
import org.semanticsoft.vaaclipsedemo.mediaplayer.model.MediaLibrary;
import org.semanticsoft.vaaclipsedemo.mediaplayer.service.MediaService;
import org.semanticsoft.vaadin.optiondialog.OptionDialog;
import org.semanticsoft.vaadin.optiondialog.OptionDialog.ComponentProvider;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * @author rushan
 *
 */
public class AddMedia extends AddMediaEntryBasic
{
	@Inject
	IEventBroker eventBroker;
	
	@Inject
	EPartServiceExt partServiceExt;
	
	@Inject
	MediaService mediaService;
	
	@CanExecute
	public boolean canExecute(MediaLibrary medialib)
	{
		return medialib.getSelectedMediaEntry() != null;
	}
	
	@Execute
	public void addMedia(@Optional final String mediaUri, MediaLibrary mediaLibrary, final MWindow window)
	{
		init(mediaLibrary, window);
		
		if (mediaUri != null)
		{
			addMediaToLibrary(window, parentCategory, mediaUri);
		}
		else
		{
			//----
			createAndShowDlg(window, "New media", "Media url:");
		}
	}
	
	@Override
	public void optionSelected(int optionId)
	{
		if (optionId == 0)
		{
			String uri = componentProvider.getTextField().getValue().toString();
			addMediaToLibrary(window, parentCategory, uri);
		}
	}
	
	private void addMediaToLibrary(MWindow window, MediaCategory category, String uri)
	{
		Media media = mediaService.findMedia(uri);
		if (media != null)
		{
			((Window)window.getWidget()).showNotification("Media with this uri exists in media library", Notification.TYPE_WARNING_MESSAGE);
			return;
		}
		else
		{
			media = new Media();
			media.setName("No name");
			media.setUri(uri);
			media.setDescription("");
			category.addMedia(media);
			eventBroker.send(MediaConstants.mediaSelected, media);
			
			eventBroker.send(MediaConstants.mediaEntryAdded, media);
			MInputPart part = partServiceExt.openUri(media.getUri());
			part.setLabel(media.getName());
		}
	}
}