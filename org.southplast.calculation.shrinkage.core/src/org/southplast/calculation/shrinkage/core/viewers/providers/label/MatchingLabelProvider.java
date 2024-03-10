package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.MatchingShrinkageViewer;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatchingViewerFactory;



import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.formatTolerance;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.maxShrinkageSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.maxSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minShrinkageSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minSize;
import static org.southplast.calculation.shrinkage.core.utils.ShrinkageUtils.validateShrinkageMaxSize;
import static org.southplast.calculation.shrinkage.core.utils.ShrinkageUtils.validateShrinkageMinSize;

public class MatchingLabelProvider  extends XViewerLabelProvider {
	public MatchingLabelProvider(MatchingShrinkageViewer viewer) {
		super(viewer);
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
	public Color getBackground(Object element, XViewerColumn xCol,
													int columnIndex) {
		
		if(element == null || (!xCol.equals(MatchingViewerFactory.space1) && 
							!xCol.equals(MatchingViewerFactory.space2))) {
			return super.getBackground(element, xCol, columnIndex);
		}
		
		return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
	}
	
	@Override
	public Image getColumnImage(Object element, XViewerColumn xCol,
			int columnIndex) throws Exception {
				
		if(element == null || (!xCol.equals(MatchingViewerFactory.shrinkageSizeMin) 
				  && !xCol.equals(MatchingViewerFactory.shrinkageSizeMax))) {
			return null;
		}

		ShrinkageCalculation calc = (ShrinkageCalculation) element;							
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMin) &&
				validateShrinkageMinSize(calc)) {
			return null;			
		}
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMax) && 
				validateShrinkageMaxSize(calc)) {
			return null;
		}

		
		
		return ImageFactory.getImage(Images.DOT_RED);
	}	
	
	@Override
	public Color getForeground(Object element, XViewerColumn xCol, int index) {						
		
		if(element == null || (!xCol.equals(MatchingViewerFactory.shrinkageSizeMin) 
						  && !xCol.equals(MatchingViewerFactory.shrinkageSizeMax))) {
			return super.getForeground(element, xCol, index);
		}
		
		ShrinkageCalculation calc = (ShrinkageCalculation) element;			
		
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMin) &&
							validateShrinkageMinSize(calc)) {
			return super.getForeground(element, xCol, index);			
		}
		
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMax) && 
							validateShrinkageMaxSize(calc)) {
			return super.getForeground(element, xCol, index);
		}
		
		return ViewUtils.RED;
	}
	
	int index = 0;
	
	@Override
	public String getColumnText(Object element, XViewerColumn xCol, int columnIndex) 
															throws Exception {
		ShrinkageCalculation calc = (ShrinkageCalculation)element;
		
		if(calc == null){
			return "";
		}
		if(xCol.equals(MatchingViewerFactory.number)){
			
			return String.valueOf(calc.getNumber());
		}
		if(xCol.equals(MatchingViewerFactory.size)){
			return format(calc.getSize());
		}
		if(xCol.equals(MatchingViewerFactory.toleranceName)){
			return calc.getTolerance().getName();
		}
		if(xCol.equals(MatchingViewerFactory.toleranceValue)){
			return calc.getTolerance().getValue() != null?
					formatTolerance(calc.getTolerance().getValue()):"0";
		}
		if(xCol.equals(MatchingViewerFactory.toleranceDown)){
			return formatTolerance(calc.getTolerance().getDown()); 
		}
		if(xCol.equals(MatchingViewerFactory.toleranceUp)){
			return formatTolerance(calc.getTolerance().getUp());
		}
		if(xCol.equals(MatchingViewerFactory.sizeMin)){
			return format(minSize(calc));
		}
		if(xCol.equals(MatchingViewerFactory.sizeMax)){
			return format(maxSize(calc));
		}
		if(xCol.equals(MatchingViewerFactory.sizeSign)){
			return format(calc.getSignSize());
		}
		if(xCol.equals(MatchingViewerFactory.toleranceNameSign)){
			return calc.getSignTolerance().getName();
		}
		if(xCol.equals(MatchingViewerFactory.toleranceValueSign)){
			return formatTolerance(calc.getSignTolerance().getValue());
		}
		if(xCol.equals(MatchingViewerFactory.toleranceDownSign)){
			return formatTolerance(calc.getSignTolerance().getDown());
		}
		if(xCol.equals(MatchingViewerFactory.toleranceUpSign)){
			return formatTolerance(calc.getSignTolerance().getUp());
		}
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMax)){
			return format(maxShrinkageSize(calc));			
		}
		if(xCol.equals(MatchingViewerFactory.shrinkageSizeMin)){
			return format(minShrinkageSize(calc));
		}
						
		return "";
	}
	
	
}
