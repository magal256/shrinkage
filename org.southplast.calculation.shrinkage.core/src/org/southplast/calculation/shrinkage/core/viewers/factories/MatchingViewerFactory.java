package org.southplast.calculation.shrinkage.core.viewers.factories;

import java.math.BigDecimal;

import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.nebula.widgets.xviewer.customize.IXViewerCustomizations;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.ExtendedViewerColumn;
import org.eclipse.swt.SWT;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.FieldConstants;
import org.southplast.calculation.shrinkage.core.viewers.customization.MyXViewerCustomizations;


public class MatchingViewerFactory extends XViewerFactory  {
	 public final static String NAMESPACE = "shrinkage.xviewer";
	 public final static String SIZE = NAMESPACE + ".size";
	 public final static String NUMBER = NAMESPACE + ".number";
	 public final static String TOLERANCE_NAME =  NAMESPACE + ".tolerance.name";
	 public final static String TOLERANCE_VAL =  NAMESPACE + ".tolerance.value";
	 public final static String TOLERANCE_DOWN =  NAMESPACE + ".tolerance.min";
	 public final static String TOLERANCE_UP =  NAMESPACE + ".tolerance.max";
	 public final static String SIZE_MAX = NAMESPACE + ".size.max";
	 public final static String SIZE_MIN = NAMESPACE + ".size.min";
	 public final static String SIZE_SIGN = NAMESPACE + ".size.sign";
	 public final static String TOLERANCE_SIGN_NAME = NAMESPACE + ".tolrence.sign.name";
	 public static final String TOLERANCE_SIGN_VALUE = NAMESPACE + ".tolerance.sign.value";
	 public final static String TOLERANCE_SIGN_DOWN = NAMESPACE + ".tolrenace.sign.min";
	 public static final String TOLERANCE_SIGN_UP = NAMESPACE + ".tolerance.sign.max";
	 public static final String SHRINKAGE_SIZE_MIN = NAMESPACE + "shrinkage.size.min";
	 public static final String SHRINKAGE_SIZE_MAX = NAMESPACE + "shrinkage.size.max";
	 public static final String SPACE1 = NAMESPACE + ".space1";
	 public static final String SPACE2 = NAMESPACE + ".space2";
	 	 	 
	 public static XViewerColumn number = new XViewerColumn(
			 								NUMBER, 
			 								Messages.get("table.header.number"), 
											50, 
											SWT.LEFT, 
											true, 
											SortDataType.Integer, 
											false, 
											null);
	 public static ExtendedViewerColumn size = new ExtendedViewerColumn(
			 								SIZE, 
			   								Messages.get("table.header.size"), 
	   										100, 
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
	 public static XViewerColumn sizeMax = new XViewerColumn(		 						
					 					 SIZE_MAX, 
										 Messages.get("table.header.size.max"), 
										 100, 
										 SWT.LEFT, 
										 true, 
										 SortDataType.Float, 
										 false, 
										 null);
	 public static XViewerColumn sizeMin = new XViewerColumn(
					 					SIZE_MIN, 
					 					Messages.get("table.header.size.min"), 
					 					100, 
					 					SWT.LEFT, 
					 					true, 
					 					SortDataType.Float, 
					 					false, 
					 					null);
	 
	 public static ExtendedViewerColumn space1 = new ExtendedViewerColumn(
														  SPACE1, 
														  "", 
														  20, 
														  SWT.LEFT, 
														  true, 
														  SortDataType.String, 
														  false, 
														  null);
	 
	 public static ExtendedViewerColumn sizeSign = new ExtendedViewerColumn(
										 SIZE_SIGN, 
										 Messages.get("table.header.size.sign"), 
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
	 
	 public static XViewerColumn space2 = new XViewerColumn(
														   SPACE2, 
														   "", 
														   20, 
														   SWT.LEFT, 
														   true, 
														   SortDataType.String, 
														   false, 
														   null);
	 
	 public static XViewerColumn shrinkageSizeMin = new XViewerColumn(
								SHRINKAGE_SIZE_MIN, 
								Messages.get("table.header.shrinkage.size.min"), 
								100, 
								SWT.LEFT, 
								true, 
								SortDataType.Float, 
								false, 
								null);
	 public static XViewerColumn shrinkageSizeMax = new XViewerColumn(
								 SHRINKAGE_SIZE_MAX, 
								 Messages.get("table.header.shrinkage.size.max"), 
								 100, 
								 SWT.LEFT, 
								 true, 
								 SortDataType.Float, 
								 false, 
								 null);
	 
	public MatchingViewerFactory(boolean preview) {
		super(NAMESPACE);
		
		registerColumns(number, size,toleranceName, toleranceValue, 
						toleranceDown, toleranceUp, sizeMax, sizeMin, space1, 
						sizeSign, toleranceNameSign, toleranceValueSign, 
						toleranceDownSign, toleranceUpSign,  space2, 
						shrinkageSizeMin, shrinkageSizeMax);		
		
		if(preview){
			return;
		}
		
		size.addMapEntry(ShrinkageCalculation.class, new CellEditDescriptor(
												  FormattedText.class, 
												  SWT.NONE, 
												  FieldConstants.SIZE, 
												  ShrinkageCalculation.class));		
		
		sizeSign.addMapEntry(ShrinkageCalculation.class, new CellEditDescriptor(
												  FormattedText.class, 
												  SWT.NONE, 
												  FieldConstants.SIGN_SIZE, 
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
	public IXViewerCustomizations getXViewerCustomizations() {      
		return new MyXViewerCustomizations();   
	}	
	
	@Override
	public boolean isFilterUiAvailable() {
		return false;
	}
	
	@Override
	public boolean isSearchUiAvailable() {
		return false;
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
}
