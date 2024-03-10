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
package org.southplast.calculation.shrinkage.core.viewers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ISomeTask;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatterViewerFactory;
import org.southplast.calculation.shrinkage.core.views.AbstratctMeasuringView;
import org.southplast.calculation.shrinkage.core.views.CalculationView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;


public class MatterViewer extends XViewer {
	   
   private final Set<ISomeTask> runList = new HashSet<ISomeTask>();
   public MatterViewer(Composite comp, int style){
	   
	   super(comp, style, new MatterViewerFactory());
	  
   }
   
   public boolean isScheduled(ISomeTask autoRunTask) {
      return true;
   }

   @Override
	protected void createSupportWidgets(Composite parent) {
	   super.createSupportWidgets(parent);
//	   this.filterDataUI = new FilterDataUI(this);
//	      this.searchDataUI = new SearchDataUI(this);
//	  searchColor = Display.getDefault().getSystemColor(SWT.COLOR_YELLOW);
//
//      Composite comp = new Composite(parent, SWT.NONE);
//      comp.setLayout(XViewerLib.getZeroMarginLayout(11, false));
//      comp.setLayoutData(new GridData(SWT.FILL, SWT.None, true, false));
//
//      filterDataUI.createWidgets(comp);
//      Label sep1 = new Label(comp, SWT.SEPARATOR);

//      GridData gd = new GridData(SWT.RIGHT, SWT.NONE, false, false);
//      gd.heightHint = 16;
//      sep1.setLayoutData(gd);
//      searchDataUI.createWidgets(comp);
//      Label sep2 = new Label(comp, SWT.SEPARATOR);
//      gd = new GridData(SWT.RIGHT, SWT.NONE, false, false);
//      gd.heightHint = 16;
//      sep2.setLayoutData(gd);
//
//      statusLabel = new Label(comp, SWT.NONE);
//      statusLabel.setText(" ");
//      statusLabel.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
//      
////      getTree().addListener(SWT.MouseDown, new Listener() {
////		
////          public void handleEvent(Event event) {
////             if (event.button == 3) {
////                rightClickSelectedColumn = null;
////                rightClickSelectedColumnNum = null;
////                rightClickSelectedItem = null;
////                Point pt = new Point(event.x, event.y);
////                rightClickSelectedItem = getTree().getItem(pt);
////                if (rightClickSelectedItem == null) return;
////                for (int colNum = 0; colNum < getTree().getColumnCount(); colNum++) {
////                   Rectangle rect = rightClickSelectedItem.getBounds(colNum);
////                   if (rect.contains(pt)) {
////                      rightClickSelectedColumnNum = colNum;
////                      rightClickSelectedColumn = getTree().getColumn(colNum);
////                      break;
////                   }
////                }
////             }
////          }
////       });
	}
   
   public boolean isRun(ISomeTask autoRunTask) {
      return runList.contains(autoRunTask);
   }

   public void setRun(ISomeTask autoRunTask, boolean run) {
      if (run) {
         runList.add(autoRunTask);
      } else {
         runList.remove(autoRunTask);
      }
   }

   @Override
   public boolean handleLeftClickInIconArea(TreeColumn treeColumn, TreeItem treeItem) {
         return super.handleLeftClickInIconArea(treeColumn, treeItem);
   }
   
   @Override	
   public void handleDoubleClick(TreeColumn col, TreeItem item) {
	   if(item.getData() instanceof Detail) {		
		   Detail det =(Detail)item.getData();
		   MeasuringType type = det.getDefaultMeasuring();		   		  
		   String id = MeasuringType.MATCHING.equals(type)?MatchingView.ID:CalculationView.ID;
		   AbstratctMeasuringView view = ViewUtils.showMeasuringView(id, det, type);
		   new LoadingCalculationsExecutor(view).run(col.getParent().getShell());
	   }	   
	}
}
