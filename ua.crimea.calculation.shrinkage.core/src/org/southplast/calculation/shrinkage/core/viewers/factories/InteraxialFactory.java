package org.southplast.calculation.shrinkage.core.viewers.factories;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.ExtendedViewerColumn;
import org.eclipse.swt.SWT;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;


public class InteraxialFactory  extends XViewerFactory {
	public final static String NAMESPACE = "shrinkage.diametral.interaxial.xviewer";
	public static XViewerColumn number = new XViewerColumn(NAMESPACE + 
															".number", 
															"¹", 
															50, 
															SWT.LEFT, 
															true, 
															SortDataType.Integer, 
															false, 
															null);
	public static ExtendedViewerColumn size = new ExtendedViewerColumn(
															NAMESPACE + 
															".size", 
															"A, mm", 
															150, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	public static XViewerColumn calcSize = new XViewerColumn(
															NAMESPACE + 
															".sizeCalc", 
															"Am, mm", 
															150, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	
	public InteraxialFactory(boolean preview) {
		super(NAMESPACE);
		registerColumns(number, size, calcSize);
		
		if(preview){
			return;
		}
		
		size.addMapEntry(ShrinkageCalculation.class, new CellEditDescriptor(
												FormattedText.class, 
												SWT.NONE, 
												"size", 
												ShrinkageCalculation.class));
	}

	@Override
	public boolean isAdmin() {
		return true;
	}
	@Override
	public boolean isCellGradientOn() {
      return true;
	}
	@Override
	public boolean isHeaderBarAvailable() {	
	   return true;
	}
	@Override
	public boolean isLoadedStatusLabelAvailable() {	
	   return false;
	}
	@Override	
	public boolean isFilterUiAvailable() {
	   return false;	
	}
   
	@Override	   
	public boolean isSearchUiAvailable() {
	   return false;	
	}

}
