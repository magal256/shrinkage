package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;


public class LoadingGroupsExecutor extends AbstractExecutor {

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Загрузка групп материалов", IProgressMonitor.UNKNOWN);											
//		doLongThing();	
		final  List<MatterGroup> groups = service.getMatterGroups(); 
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				GroupsView view = (GroupsView)ViewUtils.getView(GroupsView.ID);	
				view.setGroups(groups);
			}
		});
		monitor.done();
		
	}
	
//	private void doLongThing() {
//		for (int i = 0; i < 5; i++) {
//			try {
//				// We simulate a long running operation here
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			System.out.println("Doing something");
//		}
//	}
}
