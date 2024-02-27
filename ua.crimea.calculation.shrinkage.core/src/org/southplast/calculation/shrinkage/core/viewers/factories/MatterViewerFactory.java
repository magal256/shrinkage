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
package org.southplast.calculation.shrinkage.core.viewers.factories;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.nebula.widgets.xviewer.customize.IXViewerCustomizations;
import org.eclipse.swt.SWT;
import org.southplast.calculation.shrinkage.core.viewers.customization.MyXViewerCustomizations;


/**
 * Columns for example XViewer
 * 
 * @author Donald G. Dunne
 */
public class MatterViewerFactory extends XViewerFactory {

   public final static String NAMESPACE = "matter.xviewer";

   public static XViewerColumn Name = new XViewerColumn(
						   NAMESPACE + ".name", "Наименование", 270, 
						   SWT.LEFT, true, SortDataType.String, false, null);
  
   public static XViewerColumn Type = new XViewerColumn(NAMESPACE + ".type", 
				   "Тип Материала", 150, SWT.CENTER, true, SortDataType.String, 
				   false, null);
   
   public static XViewerColumn Maker = new XViewerColumn(NAMESPACE + ".Maker", 
						   			"Фирма изготовитель", 200, SWT.LEFT, true,
						   			SortDataType.String, false, null);
   public static XViewerColumn longitudinalShrinkage = new XViewerColumn(
					   NAMESPACE + ".longitudinalShrinkage", "Продольная усадка", 
					   150, SWT.LEFT,true, SortDataType.String, false, null);
   
  
   public static XViewerColumn measuringType = new XViewerColumn(
						   NAMESPACE + ".measuring", "Метод расчета по умолчанию", 150, 
						   SWT.LEFT, true, SortDataType.String, false, null);

   public MatterViewerFactory() {
      super(NAMESPACE);
      registerColumns(Name, Type, Maker, longitudinalShrinkage, measuringType);
   }

   @Override
   public IXViewerCustomizations getXViewerCustomizations() {
      return new MyXViewerCustomizations();
   }

   @Override
   public boolean isAdmin() {
      return true;
   }

   @Override
   public boolean isCellGradientOn() {
      return true;
   }
   
//   @Override	
//   public boolean isFilterUiAvailable() {
//	   return false;	
//   }
//   
//   @Override	   
//   public boolean isSearchUiAvailable() {
//	   return false;	
//   }

}
