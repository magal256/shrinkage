package org.southplast.calculation.shrinkage.core.forms;


import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EntityForm extends ApplicationWindow {
	private String title;	
	
	public EntityForm(Shell shell) {
		super(shell);
	}

	@Override
	public Control createContents(Composite parent) {
		GridLayout layout = new GridLayout(1, false);
		parent.setLayout(layout);				
		
		return parent;
	}
	
	@Override
	protected void configureShell(Shell shell) {				
		super.configureShell(shell);
		
		Point size = getParentShell().getSize();
		shell.setSize(size.x/2, size.y-200);
		shell.setText(title);
	}
	
	public void setFormTitle(String title) {
		this.title = title;
	}
}
