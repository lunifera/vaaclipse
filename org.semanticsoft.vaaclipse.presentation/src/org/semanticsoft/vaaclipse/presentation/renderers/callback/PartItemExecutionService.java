package org.semanticsoft.vaaclipse.presentation.renderers.callback;

import org.eclipse.e4.ui.model.application.ui.menu.MItem;
import org.semanticsoft.vaaclipse.presentation.renderers.ItemRenderer;
import org.semanticsoft.vaaclipse.publicapi.commands.IPartItemExecutionService;

public class PartItemExecutionService implements IPartItemExecutionService {

	public PartItemExecutionService() {
	}

	@Override
	public boolean canExecuteItem(MItem mItem) {
		Object renderer = mItem.getRenderer();
		if (renderer instanceof ItemRenderer) {
			ItemRenderer itemRenderer = (ItemRenderer) renderer;
			return itemRenderer.canExecute(mItem);
		}
		return true;
	}

	@Override
	public void executeItem(MItem mItem) {
		Object renderer = mItem.getRenderer();
		if (renderer instanceof ItemRenderer) {
			ItemRenderer itemRenderer = (ItemRenderer) renderer;
			itemRenderer.executeItem(mItem);
		}
	}

}
