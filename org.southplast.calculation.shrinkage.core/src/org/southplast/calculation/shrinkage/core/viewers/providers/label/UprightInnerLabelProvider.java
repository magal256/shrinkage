package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.formatTolerance;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.innerHeight;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.tolerance;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.viewers.factories.UprightInnerFactory;


public class UprightInnerLabelProvider extends XViewerLabelProvider {
	public UprightInnerLabelProvider(XViewer viewer) {
		super(viewer);
	}
	
	@Override
	public String getColumnText(Object element, XViewerColumn xCol,
			int columnIndex) throws Exception {
		ShrinkageCalculation calc = (ShrinkageCalculation) element;
		if(calc == null){
			return "";
		}
		
		if(xCol.equals(UprightInnerFactory.number)){			
			return String.valueOf(calc.getNumber());
		} else if(xCol.equals(UprightInnerFactory.hight)){
			return format(calc.getSize());
		} else if(xCol.equals(UprightInnerFactory.toleranceName)){
			return calc.getTolerance().getName();
		} else if(xCol.equals(UprightInnerFactory.toleranceValue)){			
			return format(tolerance(calc.getTolerance()));
		} else if(UprightInnerFactory.toleranceDown.equals(xCol)){
			return formatTolerance(calc.getTolerance().getDown());
		}else if(UprightInnerFactory.toleranceUp.equals(xCol)){
			return formatTolerance(calc.getTolerance().getUp());
		} else if(xCol.equals(UprightInnerFactory.toleranceNameSign)){
			return calc.getSignTolerance().getName();
		} else if(xCol.equals(UprightInnerFactory.toleranceValueSign)){			
			return format(tolerance(calc.getSignTolerance()));
		} else if (UprightInnerFactory.toleranceDownSign.equals(xCol)){
			return formatTolerance(calc.getSignTolerance().getDown());
		} else if (UprightInnerFactory.toleranceUpSign.equals(xCol)){
			return formatTolerance(calc.getSignTolerance().getUp());
		} else if(xCol.equals(UprightInnerFactory.minHight)){
			return format(minSize(calc));
		} else if(xCol.equals(UprightInnerFactory.calcHight)){
			return format(innerHeight(calc));
		}
		
		return null;
	}
	
	@Override
	public Color getBackground(Object element, XViewerColumn xCol, int index) {
		if(element == null || (!xCol.equals(UprightInnerFactory.space))) {
			return super.getBackground(element, xCol, index);
		}

		return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	}		
	
	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object element, XViewerColumn xCol,
			int columnIndex) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
}
