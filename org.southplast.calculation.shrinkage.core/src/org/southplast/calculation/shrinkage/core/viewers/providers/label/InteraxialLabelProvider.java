package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.interaxialSize;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.viewers.factories.InteraxialFactory;


public class InteraxialLabelProvider extends XViewerLabelProvider {
	public InteraxialLabelProvider(XViewer viewer) {
		super(viewer);
	}
	
	@Override
	public String getColumnText(Object element, XViewerColumn xCol,
			int columnIndex) throws Exception {
		ShrinkageCalculation calc = (ShrinkageCalculation) element;
		if(calc == null){
			return "";
		}
		
		if(xCol.equals(InteraxialFactory.number)){			
			return String.valueOf(calc.getNumber());
		}
		if(xCol.equals(InteraxialFactory.size)){
			return format(calc.getSize());
		}
		
		if(xCol.equals(InteraxialFactory.calcSize)){
			return format(interaxialSize(calc));
		}
		
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getColumnImage(Object element, XViewerColumn xCol,
			int columnIndex) throws Exception {
		return null;
	}
}
