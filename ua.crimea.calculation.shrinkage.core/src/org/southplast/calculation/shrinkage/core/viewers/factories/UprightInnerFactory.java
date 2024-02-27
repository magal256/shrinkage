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


public class UprightInnerFactory extends XViewerFactory {
	public final static String NAMESPACE = "shrinkage.diametral.upright.inner.xviewer";
	public final static String TOLERANCE_NAME =  NAMESPACE + ".tolerance.name";		
	public final static String TOLERANCE_VAL =  NAMESPACE + ".tolerance.value";
	public final static String TOLERANCE_UP =  NAMESPACE + ".tolerance.max";
	public final static String TOLERANCE_DOWN =  NAMESPACE + ".tolerance.min";
	public final static String TOLERANCE_SIGN_NAME = NAMESPACE + ".tolrence.sign.name";
	public static final String TOLERANCE_SIGN_VALUE = NAMESPACE + ".tolerance.sign.value";
	public final static String TOLERANCE_SIGN_DOWN = NAMESPACE + ".tolrenace.sign.min";
	public static final String TOLERANCE_SIGN_UP = NAMESPACE + ".tolerance.sign.max";
	
	public static XViewerColumn number = new XViewerColumn(NAMESPACE + 
														".number", 
														"¹", 
														50, 
														SWT.LEFT, 
														true, 
														SortDataType.Integer, 
														false, 
														null);
	public static ExtendedViewerColumn hight = new ExtendedViewerColumn(
															NAMESPACE + 
															".diametr", 
															"H, mm", 
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
								 	70, 
								 	SWT.LEFT, 
								 	false, 
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
	public static ExtendedViewerColumn toleranceUp = new ExtendedViewerColumn(
									 TOLERANCE_UP, 
									 Messages.get("table.header.tolerance.up"), 
									 100, 
									 SWT.LEFT, 
									 true, 
									 SortDataType.Float, 
									 false, 
									 null);
	
	 public static XViewerColumn toleranceNameSign = new XViewerColumn(
									 TOLERANCE_SIGN_NAME, 
									 Messages.get("table.header.tolerance.sign"), 
									 50, 
									 SWT.LEFT, 
									 true, 
									 SortDataType.String, 
									 false, 
									 null);
	 public static ExtendedViewerColumn toleranceValueSign = new ExtendedViewerColumn(
							 TOLERANCE_SIGN_VALUE, 
							 Messages.get("table.header.tolerance.sign.value"), 
							 70, 
							 SWT.LEFT, 
							 false, 
							 SortDataType.Float, 
							 false, 
							 null);
	 public static ExtendedViewerColumn toleranceDownSign = new ExtendedViewerColumn(
							 TOLERANCE_SIGN_DOWN, 
							 Messages.get("table.header.tolerance.sign.down"), 
							 70, 
							 SWT.LEFT, 
							 true, 
							 SortDataType.Float, 
							 false, 
							 null);
	 public static ExtendedViewerColumn toleranceUpSign = new ExtendedViewerColumn(
								   TOLERANCE_SIGN_UP, 
								   Messages.get("table.header.tolerance.sign.up"), 
								   100, 
								   SWT.LEFT, 
								   true, 
								   SortDataType.Float, 
								   false, 
								   null);

	public static XViewerColumn minHight = new XViewerColumn(NAMESPACE + 
															".minHight", 
															"H min, ìì", 
															70, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	
	public static ExtendedViewerColumn space = new ExtendedViewerColumn(
										NAMESPACE + ".space1", "", 20, SWT.LEFT, 
										true, SortDataType.String, false, null);
	
	
	public static XViewerColumn calcHight = new XViewerColumn(NAMESPACE + 
															".calcDiametr", 
															"Hm, mm", 
															70, 
															SWT.LEFT, 
															true, 
															SortDataType.Float, 
															false, 
															null);
	
	public UprightInnerFactory(boolean preview) {
		super(NAMESPACE);
		
		registerColumns(number, hight, toleranceName, toleranceValue,
														toleranceDown,
														toleranceUp,
														toleranceNameSign,  
														toleranceValueSign,
														toleranceDownSign,
														toleranceUpSign,
														minHight,
														space,
														calcHight);
		if(preview){
			return;
		}
		hight.addMapEntry(ShrinkageCalculation.class, new CellEditDescriptor(
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
								new CellEditDescriptor(FormattedText.class, 
										  			   SWT.NONE, 
										  			   FieldConstants.TOLERANCE_UP, 
										  			   BigDecimal.class));

		toleranceDownSign.addMapEntry(ShrinkageCalculation.class, 
									  new CellEditDescriptor( 
											FormattedText.class, 
							  				SWT.NONE, 
							  				FieldConstants.TOLERANCE_SIGN_DOWN, 
							  				BigDecimal.class));
		toleranceUpSign.addMapEntry(ShrinkageCalculation.class, 
									new CellEditDescriptor( 
											FormattedText.class, 
							  				SWT.NONE, 
							  				FieldConstants.TOLERANCE_SIGN_UP, 
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
