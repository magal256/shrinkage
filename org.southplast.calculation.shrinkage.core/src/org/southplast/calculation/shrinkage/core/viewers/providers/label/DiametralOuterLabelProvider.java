package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.formatTolerance;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.maxSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.outerDiameter;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.tolerance;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.viewers.factories.DiametralOuterFactory;


public class DiametralOuterLabelProvider extends XViewerLabelProvider {

	public DiametralOuterLabelProvider(XViewer viewer) {
		super(viewer);
	}

	@Override
	public String getColumnText(Object element, XViewerColumn xCol, 
											int columnIndex) throws Exception {
		ShrinkageCalculation calc = (ShrinkageCalculation) element;
		if(calc == null){
			return "";
		}else if(xCol.equals(DiametralOuterFactory.number)){			
			return String.valueOf(calc.getNumber());
		} else if(xCol.equals(DiametralOuterFactory.diameter)){
			return format(calc.getSize());
		} else if(xCol.equals(DiametralOuterFactory.toleranceName)){
			return calc.getTolerance().getName();
		} else if(xCol.equals(DiametralOuterFactory.toleranceValue)){			
			return format(tolerance(calc.getTolerance()));
		} else if(DiametralOuterFactory.toleranceUp.equals(xCol)){
			return formatTolerance(calc.getTolerance().getUp());
		} else if (DiametralOuterFactory.toleranceDown.equals(xCol)){
			return formatTolerance(calc.getTolerance().getDown());
		}else if(DiametralOuterFactory.maxDiameter.equals(xCol)){
			return format(maxSize(calc));
		} else if(DiametralOuterFactory.calcDiametr.equals(xCol)) {
			return format(outerDiameter(calc));
		}
		
		return "";
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
