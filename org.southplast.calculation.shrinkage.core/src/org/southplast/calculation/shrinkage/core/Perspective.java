package org.southplast.calculation.shrinkage.core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
	    layout.setEditorAreaVisible(false);
	    layout.setFixed(true);
	    
	    layout.createFolder("Calculation",IPageLayout.BOTTOM,0.8f, editorArea);
	   
	}
	
}
