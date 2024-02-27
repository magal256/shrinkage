package org.southplast.calculation.shrinkage.core.forms;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;


public class FilialWindow extends ApplicationWindow {

	public FilialWindow(Shell parentShell) {
		super(parentShell);
	}
	
	public void run(){
		getParentShell().setEnabled(false);
		getParentShell().setCursor(ViewUtils.CURSOR_WAIT);
		setBlockOnOpen(true);
		
		open();
		
		getParentShell().setEnabled(true);
		getParentShell().setCursor(null);
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		
		shell.setImage(ImageFactory.getImage(Images.MAIN));
	}
}
