/*
 * Created on Dec 8, 2010
 *
 * PLACE_YOUR_DISTRIBUTION_STATEMENT_RIGHT_HERE
 */
package org.southplast.calculation.shrinkage.core.viewers.customization;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.customize.CustomizeData;
import org.southplast.calculation.shrinkage.core.utils.MyLib;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatterViewerFactory;



public class MatterDefaultCustomizations {

   public static CustomizeData getCompletionCustomization() {
      CustomizeData data = new CustomizeData();
      data.setName("Name Status");
      // Each customization must have it's own guid
      data.setGuid(MyLib.generateGuidStr());
      data.setNameSpace(MatterViewerFactory.NAMESPACE);

      // Columns must be copied cause they each store their own manipulation data and can be used
      // across multiple customizations.
      XViewerColumn nameColumn = MatterViewerFactory.Name.copy();
      nameColumn.setSortForward(true);
      nameColumn.setWidth(175);
      nameColumn.setShow(true);
      
      data.getColumnData().getColumns().add(nameColumn);

      return data;
   }
   
   public static CustomizeData getDescriptionCustomization() {
      CustomizeData data = new CustomizeData();
      data.setName("Name Description");
      data.setGuid(MyLib.generateGuidStr());
      data.setNameSpace(MatterViewerFactory.NAMESPACE);

//      XViewerColumn descColumn = MatterViewerFactory.Description.copy();
//      descColumn.setShow(true);
//      descColumn.setWidth(250);
//      data.getColumnData().getColumns().add(descColumn);

      XViewerColumn nameColumn = MatterViewerFactory.Name.copy();
      nameColumn.setSortForward(true);
      nameColumn.setWidth(175);
      nameColumn.setShow(true);
      data.getColumnData().getColumns().add(nameColumn);

      return data;
   }
}
