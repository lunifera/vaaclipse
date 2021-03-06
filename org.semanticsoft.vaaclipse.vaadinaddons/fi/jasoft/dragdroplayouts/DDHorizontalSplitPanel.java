/*
 * Copyright 2011 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts;

import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

@SuppressWarnings("serial")
@ClientWidget(VDDHorizontalSplitPanel.class)
public class DDHorizontalSplitPanel extends HorizontalSplitPanel implements
        LayoutDragSource, DropTarget, ShimSupport {

    /**
     * The drop handler which handles dropped components in the layout.
     */
    private DropHandler dropHandler;

    /**
     * Specifies if dragging components is allowed and if so how it should be
     * visualized
     */
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Contains the location and other information about the drop.
     */
    public class HorizontalSplitPanelTargetDetails extends TargetDetailsImpl {

        private Component over;

        protected HorizontalSplitPanelTargetDetails(
                Map<String, Object> rawDropData) {
            super(rawDropData, DDHorizontalSplitPanel.this);

            if (getDropLocation() == HorizontalDropLocation.LEFT) {
                over = getFirstComponent();
            } else if (getDropLocation() == HorizontalDropLocation.RIGHT) {
                over = getSecondComponent();
            } else {
                over = DDHorizontalSplitPanel.this;
            }
        }

        /**
         * The component over which the drop was made.
         * 
         * @return Null if the drop was not over a component, else the component
         */
        public Component getOverComponent() {
            return over;
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize((String) getData(Constants.DROP_DETAIL_MOUSE_EVENT));
        }

        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public HorizontalDropLocation getDropLocation() {
            return HorizontalDropLocation.valueOf((String) getData(Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.event.dd.DropTarget#translateDropTargetDetails(java.util.Map)
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new HorizontalSplitPanelTargetDetails(clientVariables);
    }

    /**
     * Get the transferable created by a drag event.
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new LayoutBoundTransferable(this, rawVariables);
    }

    /**
     * Returns the drop handler which handles drop events from dropping
     * components on the layout. Returns Null if dropping is disabled.
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * Sets the current handler which handles dropped components on the layout.
     * By setting a drop handler dropping components on the layout is enabled.
     * By setting the dropHandler to null dropping is disabled.
     * 
     * @param dropHandler
     *            The drop handler to handle drop events or null to disable
     *            dropping
     */
    public void setDropHandler(DropHandler dropHandler) {
    	if(this.dropHandler != dropHandler){
            this.dropHandler = dropHandler;
            requestRepaint();
    	}
    }

    /**
     * Returns the mode of which dragging is visualized.
     * 
     * @return
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    /**
     * Enables dragging components from the layout.
     * 
     * @param mode
     *            The mode of which how the dragging should be visualized.
     */
    public void setDragMode(LayoutDragMode mode) {
    	if(dragMode != mode){
    		 dragMode = mode;
    		 requestRepaint();
    	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.ui.AbstractOrderedLayout#translateDropTargetDetails(java.util
     * .Map)
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        // Add drop handler
        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Drag mode
        target.addAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE, dragMode.ordinal());

        // Shims
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
    }

    /**
     * {@inheritDoc}
     */
    public void setShim(boolean shim) {
    	if(iframeShims != shim){
    		iframeShims = shim;
            requestRepaint();
    	}
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShimmed() {
        return iframeShims;
    }
    
    /**
     * {@inheritDoc}
     */
	public DragFilter getDragFilter() {
		return dragFilter;
	}

	/**
     * {@inheritDoc}
     */
	public void setDragFilter(DragFilter dragFilter) {
		if(this.dragFilter != dragFilter){
			this.dragFilter = dragFilter;
			requestRepaint();
		}
	}
}
