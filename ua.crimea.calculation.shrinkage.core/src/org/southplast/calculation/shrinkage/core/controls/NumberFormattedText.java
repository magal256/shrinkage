package org.southplast.calculation.shrinkage.core.controls;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.swt.widgets.Text;

public class NumberFormattedText extends FormattedText {
	
	public NumberFormattedText(Text t) {
		super(t);
		this.caretPos = 1;		
	}
	
}
