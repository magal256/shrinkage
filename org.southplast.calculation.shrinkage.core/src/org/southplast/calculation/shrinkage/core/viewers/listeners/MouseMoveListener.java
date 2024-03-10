package org.southplast.calculation.shrinkage.core.viewers.listeners;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.viewers.AbstractShrinkageViewer;


public class MouseMoveListener implements Listener{
	private AbstractShrinkageViewer viewer;
	
	public MouseMoveListener(AbstractShrinkageViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void handleEvent(Event event) {
		Point point = new Point(event.x, event.y);      
		TreeColumn column = viewer.getColumnUnderMouseClick(point);      
		TreeItem itemToReturn = viewer.getItemUnderMouseClick(point);
      
		viewer.handleMouseMove(column, itemToReturn);		
	}
}
