package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerStyledTextLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.viewers.MatterViewer;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatterViewerFactory;



public class MatterStyledTextLabelProvider extends XViewerStyledTextLabelProvider {

//	private final MatterViewer xViewerTest;
	
	public MatterStyledTextLabelProvider(MatterViewer viewer) {
		super(viewer);
//		this.xViewerTest = viewer;
	}

	@Override
	public Image getColumnImage(Object element, XViewerColumn xCol, int column) throws Exception {
//		 if (xCol.equals(MatterViewerFactory.Name_Col)) {
//	         return xViewerTest.isRun((ISomeTask) element) ? MyImageCache.getImage("chkbox_enabled.gif") : MyImageCache.getImage("chkbox_disabled.gif");
//	      }
//	      if (xCol.equals(MatterViewerFactory.Name_Col) && xViewerTest.isScheduled((ISomeTask) element)) {
//	         return MyImageCache.getImage("clock.gif");
//	      }
	      return null;
	}

	@Override
	public StyledString getStyledText(Object element, XViewerColumn xCol, int column) throws Exception {
		 if (element instanceof String) {
	         if (column == 1) {
	            return (StyledString) element;
	         } else {
	            return new StyledString("");
	         }
	      }
	      Matter matter = null;
	      MatterGroup group = null;
	      if(element instanceof MatterGroup){
	    	  group = (MatterGroup) element;
	      }
	      if(element instanceof Matter){
	    	  matter =(Matter) element;
	      }
	      if(matter == null && group == null){
	    	  return new StyledString("");
	      }
	   
	      if (xCol.equals(MatterViewerFactory.Name)) {
	    	  if(matter != null){
	    		  new StyledString(matter.getCommercialGrade());
	    	  }
	    	  if( group != null){
	    		  new StyledString(group.getName());
	    	  }
	    	  
	    	  return new StyledString("");	         
	      }
	      
	      if (xCol.equals(MatterViewerFactory.Type)) {
	         return matter != null? new StyledString(matter.getType()):new StyledString("");
	      }
	     
	      if (xCol.equals(MatterViewerFactory.longitudinalShrinkage)) {
	         return matter != null && matter.getLongitudinalShrinkage() != null? 
	        		 new StyledString(
	        		 matter.getLongitudinalShrinkage().getMaximum().toString() + 
	        		 " - " + matter.getLongitudinalShrinkage().getMaximum()):new StyledString("");
	      }
	     
	      if (xCol.equals(MatterViewerFactory.Maker)) {
		         return matter != null?new StyledString(matter.getMaker()):new StyledString("");
		      }
	      return new StyledString("");
	}

	@Override
	public Color getBackground(Object element, XViewerColumn viewerColumn, int columnIndex) throws Exception {
		return null;
	}

	@Override
	public Color getForeground(Object element, XViewerColumn viewerColumn, int columnIndex) throws Exception {
		return null;
	}

	@Override
	public Font getFont(Object element, XViewerColumn viewerColumn, int columnIndex) throws Exception {
		return null;
	}


}
