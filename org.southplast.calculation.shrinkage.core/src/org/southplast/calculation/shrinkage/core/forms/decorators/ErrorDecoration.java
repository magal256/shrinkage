package org.southplast.calculation.shrinkage.core.forms.decorators;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;


public class ErrorDecoration {
	private Control input;
	private ControlDecoration decoration;
	
	public ErrorDecoration(Control text) {
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault()
						.getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);		
		input = text;		
		decoration =  new ControlDecoration(text, SWT.LEFT | SWT.TOP);				
		decoration.setImage(fieldDecoration.getImage());
	}

	public void show(String description) {		
		try{
			decoration.setDescriptionText(description);
			decoration.show();
			input.setBackground(ViewUtils.PINK);
		}catch(Exception e){e.printStackTrace();}
		
	}	
	
	public void hide() {		
		decoration.hide();
		input.setBackground(ViewUtils.WHITE);
	}
	
	public void dispose(){
		decoration.dispose();
	}
}
