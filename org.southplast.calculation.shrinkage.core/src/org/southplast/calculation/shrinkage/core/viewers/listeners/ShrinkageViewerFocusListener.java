package org.southplast.calculation.shrinkage.core.viewers.listeners;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.southplast.calculation.shrinkage.core.viewers.DiametralOuterViewer;


public class ShrinkageViewerFocusListener implements FocusListener {
	private DiametralOuterViewer viewer;
	
	public ShrinkageViewerFocusListener(DiametralOuterViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		viewer.handleFocusLost();
	}
	
}
