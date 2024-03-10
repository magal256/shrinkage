package org.southplast.calculation.shrinkage.core.viewers.factories;

import java.math.BigDecimal;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.ExtendedViewerColumn;
import org.eclipse.swt.SWT;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.FieldConstants;


public class DiametralInnerFactory extends XViewerFactory {
	public final static String NAMESPACE = "shrinkage.diametral.inner.xviewer";
	
	public final static String TOLERANCE_NAME =  NAMESPACE + ".tolerance.name";		
	public final static String TOLERANCE_VAL =  NAMESPACE + ".tolerance.value";
	public final static String TOLERANCE_UP =  NAMESPACE + ".tolerance.max";
	public final static String TOLERANCE_DOWN =  NAMESPACE + ".tolerance.min";
	
	public static XViewerColumn number = new XViewerColumn(NAMESPACE + ".number", 
															"№", 
															50, 
															SWT.LEFT, 
															true, 
															SortDataType.Integer, 
															false, 
															null);
	public static ExtendedViewerColumn diameter = new ExtendedViewerColumn(
															NAMESPACE + 
															".diametr", 
															"D, mm", 
															150, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	
	 public static XViewerColumn toleranceName = new XViewerColumn(
									TOLERANCE_NAME, 
									Messages.get("table.header.tolerance.name"), 
									70, 
									SWT.LEFT, 
									true, 
									SortDataType.String, 
									false, 
									null);
	 public static ExtendedViewerColumn toleranceValue = new ExtendedViewerColumn(
								 	TOLERANCE_VAL, 
								 	Messages.get("table.header.tolerance.value"), 
								 	100, 
								 	SWT.LEFT, 
								 	false, 
								 	SortDataType.Float, 
								 	false, 
								 	null);
	 public static ExtendedViewerColumn toleranceUp = new ExtendedViewerColumn(
												 TOLERANCE_UP, 
												 Messages.get("table.header.tolerance.up"), 
												 100, 
												 SWT.LEFT, 
												 true, 
												 SortDataType.Float, 
												 false, 
												 null);
	 public static ExtendedViewerColumn toleranceDown = new ExtendedViewerColumn(
									 TOLERANCE_DOWN, 
									 Messages.get("table.header.tolerance.down"), 
									 100, 
									 SWT.LEFT, 
									 true, 
									 SortDataType.Float, 
									 false, 
									 null);
	public static XViewerColumn maxDiameter = new XViewerColumn(NAMESPACE + 
															".maxDiametr", 
															"D, min", 
															70, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	public static XViewerColumn calcDiametr = new XViewerColumn(NAMESPACE + 
															".calcDiametr", 
															"Dст, mm", 
															70, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	public DiametralInnerFactory(boolean preview) {
		super(NAMESPACE);
		
		registerColumns(number, diameter, toleranceName, toleranceValue, 
														 toleranceDown,
														 toleranceUp,
														 maxDiameter, 
														 calcDiametr);
		if(preview){
			return;
		}
		diameter.addMapEntry(ShrinkageCalculation.class, new CellEditDescriptor(
													FormattedText.class, 
													SWT.NONE, 
													"size", 
													ShrinkageCalculation.class));
		toleranceDown.addMapEntry(ShrinkageCalculation.class, 
								  new CellEditDescriptor(
										  FormattedText.class, 
										  SWT.NONE, 
										  FieldConstants.TOLERANCE_DOWN, 
										  BigDecimal.class));
		toleranceUp.addMapEntry(ShrinkageCalculation.class, 
				  new CellEditDescriptor(
						  FormattedText.class, 
						  SWT.NONE, 
						  FieldConstants.TOLERANCE_UP, 
						  BigDecimal.class));			
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
