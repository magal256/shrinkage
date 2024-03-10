package org.southplast.calculation.shrinkage.core.viewers.factories;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.swt.SWT;

public class PreviewViewerFactory extends XViewerFactory  {
	public final static String NAMESPACE = "calculations.preview.xviewer";
	public final static String ONE = NAMESPACE + ".one";
	public final static String TWO = NAMESPACE + ".two";
	public final static String THREE =  NAMESPACE + ".three";
	public final static String FOUR =  NAMESPACE + ".fore";
	public final static String FIVE =  NAMESPACE + ".five";
	public final static String SIX =  NAMESPACE + ".six";
	public final static String SEVEN = NAMESPACE + ".seven";
	public final static String EIGHT = NAMESPACE + ".eight";
	public final static String NINE = NAMESPACE + ".nine";
	public final static String TEN = NAMESPACE + ".ten";
	public static final String ELEVEN = NAMESPACE + ".eleven";
	public final static String TEWLVE = NAMESPACE + ".twelve";
	public static final String THIRTEEN = NAMESPACE + ".thirteen";
	public static final String FOURTEEN= NAMESPACE + ".fourteen";
	public static final String FIFTEEN= NAMESPACE + "fifteen";
	
	public static XViewerColumn one = new XViewerColumn(ONE, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);	
	public static XViewerColumn two = new XViewerColumn(TWO, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn three = new XViewerColumn(THREE, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn four = new XViewerColumn(FOUR, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn five = new XViewerColumn(FIVE, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn six = new XViewerColumn(SIX, "", 100, SWT.LEFT, 
									true, SortDataType.Integer, false, null);
	public static XViewerColumn seven = new XViewerColumn(SEVEN, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn eight = new XViewerColumn(EIGHT, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn nine = new XViewerColumn(NINE, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn ten = new XViewerColumn(TEN, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn eleven = new XViewerColumn(ELEVEN, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn twelve = new XViewerColumn(TEWLVE, "", 100, SWT.LEFT, 
										true, SortDataType.Integer, false, null);
	public static XViewerColumn thirteen = new XViewerColumn(THIRTEEN, "", 100, 
							SWT.LEFT, true, SortDataType.Integer, false, null);
	public static XViewerColumn fourteen = new XViewerColumn(FOURTEEN, "", 100, 
							SWT.LEFT, true, SortDataType.Integer, false, null);
	public static XViewerColumn fifteen = new XViewerColumn(FIFTEEN, "", 100, 
							SWT.LEFT, true, SortDataType.Integer, false, null);
	
	public PreviewViewerFactory() {
		super(NAMESPACE);
		
		registerColumns(one, two,three, four, five, six, seven, eight, nine, 
						ten, eleven, twelve, thirteen, fourteen,  fifteen);	
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
