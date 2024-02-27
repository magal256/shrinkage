/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.viewers.MatterViewer;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatterViewerFactory;


import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
/**
 * Example implementation for XViewerTest XViewer
 * 
 * @author Donald G. Dunne
 */
public class MatterXViewerLabelProvider extends XViewerLabelProvider {
//   private final MatterViewer xViewerTest;

   public MatterXViewerLabelProvider(MatterViewer xViewerTest) {
      super(xViewerTest);
//      this.xViewerTest = xViewerTest;
   }

   @Override
   public String getColumnText(Object element, XViewerColumn xCol, int columnIndex) {
      if (element instanceof String) {
         if (columnIndex == 1) {
            return (String) element;
         } else {
            return "";
         }
      }
      Matter matter = null;
      MatterGroup group = null;
      Detail detail = null;
      
      if(element instanceof MatterGroup){
    	  group = (MatterGroup) element;
      }
      if(element instanceof Matter){
    	  matter =(Matter) element;
      }
      if(element instanceof Detail){
    	  detail = (Detail)element;
      }
      
      if(matter == null && group == null && detail == null){
    	  return "";
      }
   
      if (xCol.equals(MatterViewerFactory.Name)) {
    	  if(group != null){    		
    		  return group.getName();
    	  }else if(detail != null) {
    		  return detail.getName();
    	  }else if(matter != null){
    		  return matter.getCommercialGrade();
    	  }
      }
      
      if (xCol.equals(MatterViewerFactory.Type)) {
         return matter != null? matter.getType():"";
      }
     
      if (xCol.equals(MatterViewerFactory.longitudinalShrinkage)) {
         return matter != null && matter.getLongitudinalShrinkage() != null? 
        		format(matter.getLongitudinalShrinkage().getMinimum()) + 
        		 " - " +format( matter.getLongitudinalShrinkage().getMaximum()):"";
      }
     
      if (xCol.equals(MatterViewerFactory.measuringType)) {
         return detail != null?detail.getDefaultMeasuring().toString():"";
      }
      
      if (xCol.equals(MatterViewerFactory.Maker)) {
          return matter != null?matter.getMaker():"";
       }
      return "";
   }

   @Override
   public void dispose() {
      // do nothing
   }

   @Override
   public boolean isLabelProperty(Object element, String property) {
      return false;
   }

   @Override
   public void addListener(ILabelProviderListener listener) {
      // do nothing
   }

   @Override
   public void removeListener(ILabelProviderListener listener) {
      // do nothing
   }

   @Override
   public Image getColumnImage(Object element, XViewerColumn xCol, int columnIndex) {
	   if (xCol.equals(MatterViewerFactory.measuringType)) {
		   if(element instanceof Detail){
			   Detail det = (Detail)element;
				  return ImageFactory.getImage(det.getDefaultMeasuring()
						  						.equals(MeasuringType.MATCHING)?
						  						  Images.RULER1:Images.RULER2);
	    	  }  
		   
		  }
	   if (xCol.equals(MatterViewerFactory.Name)) {
    	  if(element instanceof Detail){
    		  
    		  return ImageFactory.getImage(Images.DETAIL);
    	  }
    	  if(element instanceof MatterGroup){
    		  return ImageFactory.getImage(Images.GROUP);
    	  }
    	  if(element instanceof Matter){
    		  return ImageFactory.getImage(Images.MATERIAL);
    	  }
      }
      return null;
   }

   @Override
   public Color getBackground(Object element, int columnIndex) {
      return super.getBackground(element, columnIndex);
   }

   @Override
   public int getColumnGradient(Object element, XViewerColumn xCol, int columnIndex) throws Exception {
//      if (!(element instanceof ISomeTask)) {
//         return 0;
//      }
//      ISomeTask task = ((ISomeTask) element);
//      if (xCol.equals(MatterViewerFactory.Grade_Col)) {
//         return task.getPercentComplete();
//      }
      return 0;
   }

}
