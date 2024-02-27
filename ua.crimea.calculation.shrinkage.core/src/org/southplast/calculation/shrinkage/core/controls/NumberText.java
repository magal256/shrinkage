package org.southplast.calculation.shrinkage.core.controls;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.NumberFormatter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class NumberText extends Text {

	private FormattedText formattedText;
	
	public NumberText(Composite parent, int style, NumberFormatter formatter) {
		super(parent, style);
		
		this.formattedText = new NumberFormattedText(this);
		this.formattedText.setFormatter(formatter);
	}	
	
	public void setFormattedText(FormattedText formattedText) {
		this.formattedText = formattedText;
	}
	public FormattedText getFormattedText() {
		return formattedText;
	}
	
	@Override
	protected void checkSubclass() {
		
	}
}
