package org.southplast.calculation.shrinkage.core.viewers.factories;

import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.SIGN_SIZE;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.SIZE;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_DOWN;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_SIGN_DOWN;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_SIGN_UP;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_UP;

import java.util.Locale;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.NumberFormatter;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.DefaultXViewerControlFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Control;
import org.southplast.calculation.shrinkage.core.controls.NumberText;
import org.southplast.calculation.shrinkage.core.views.AbstratctMeasuringView;


public class ViewerControlFactory extends DefaultXViewerControlFactory {
	private AbstratctMeasuringView view;
	
	public ViewerControlFactory(AbstratctMeasuringView view) {
		this.view = view;
	}
	
	@Override
	public Control createControl(CellEditDescriptor ced, XViewer xv) {
		try{
			if(ced.getControl().equals(FormattedText.class)) {
				NumberFormatter formatter = null;
				if(ced.getInputField().equals(TOLERANCE_UP) || 
				   ced.getInputField().equals(TOLERANCE_DOWN) ||
				   ced.getInputField().equals(TOLERANCE_SIGN_DOWN) ||
				   ced.getInputField().equals(TOLERANCE_SIGN_UP)) {
					
					formatter = new NumberFormatter("-#####0.000", Locale.US);
				} else if(ced.getInputField().equals(SIGN_SIZE) || 
						  ced.getInputField().equals(SIZE)) {
					
					formatter = new NumberFormatter("#####0.00", Locale.US);
				}
				
				NumberText text = new NumberText(xv.getTree(), SWT.NONE, formatter);
				text.addFocusListener(new FocusListener() {
					
					@Override
					public void focusLost(FocusEvent e) {
						 view.setEnabledToolbars(true);
					}
					
					@Override
					public void focusGained(FocusEvent e) {
						
						view.setEnabledToolbars(false);
					}
				});
				text.addTraverseListener(new TraverseListener() {
					@Override
					public void keyTraversed(TraverseEvent e) {
						if(e.detail == SWT.TRAVERSE_ESCAPE ||
							e.detail == SWT.TRAVERSE_RETURN) {
							view.setEnabledToolbars(true);
						}
					}
				});								
				
				return text;
			}else{
				return super.createControl(ced, xv);
			}		
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
