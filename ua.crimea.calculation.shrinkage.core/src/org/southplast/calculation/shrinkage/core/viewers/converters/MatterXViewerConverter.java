package org.southplast.calculation.shrinkage.core.viewers.converters;

import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.swt.widgets.Control;

public class MatterXViewerConverter implements XViewerConverter {

   @Override
   public void setInput(Control c, CellEditDescriptor ced, Object selObject) {

//      if (c instanceof Text) {
//         Text text = (Text) c;
//         if (selObject instanceof SomeTask) {
//            SomeTask someTask = (SomeTask) selObject;
//            if (ced.getInputField().equals("completed")) { //$NON-NLS-1$
//               text.setText(String.valueOf(someTask.getPercentComplete()));
//            }
//         }
//      }

   }

   @Override
   public void getInput(Control c, CellEditDescriptor ced, Object selObject) {

//      if (c instanceof Text) {
//         Text text = (Text) c;
//         if (selObject instanceof SomeTask) {
//            SomeTask someTask = (SomeTask) selObject;
//            if (ced.getInputField().equals("completed")) { //$NON-NLS-1$
//               someTask.setPercentComplete(Integer.valueOf(text.getText()));
//            }
//         }
//      }

   }

}
