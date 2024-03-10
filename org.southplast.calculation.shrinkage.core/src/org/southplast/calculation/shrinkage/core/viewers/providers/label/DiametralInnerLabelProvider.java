package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.formatTolerance;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.innerDiameter;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.tolerance;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.viewers.factories.DiametralInnerFactory;


public class DiametralInnerLabelProvider extends XViewerLabelProvider {

	public DiametralInnerLabelProvider(XViewer viewer) {
		super(viewer);
	}
	
	@Override
	public String getColumnText(Object element, XViewerColumn xCol,
											int columnIndex) throws Exception {
		ShrinkageCalculation calc = (ShrinkageCalculation) element;
		if(calc == null){
			return "";
		}
		
		if(xCol.equals(DiametralInnerFactory.number)){			
			return String.valueOf(calc.getNumber());
		}
		if(xCol.equals(DiametralInnerFactory.diameter)){
			return format(calc.getSize());
		}
		if(xCol.equals(DiametralInnerFactory.toleranceName)){
			return calc.getTolerance().getName();
		}
		if(xCol.equals(DiametralInnerFactory.toleranceValue)) {			
			return format(tolerance(calc.getTolerance()));
		} else if(DiametralInnerFactory.toleranceDown.equals(xCol)) {
			return formatTolerance(calc.getTolerance().getDown());
		} else if(xCol.equals(DiametralInnerFactory.toleranceUp)) {
			return formatTolerance(calc.getTolerance().getUp());
		}	
		if(DiametralInnerFactory.maxDiameter.equals(xCol)){
			return format(minSize(calc));
		}
		if(DiametralInnerFactory.calcDiametr.equals(xCol)) {
			return format(innerDiameter(calc));
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
