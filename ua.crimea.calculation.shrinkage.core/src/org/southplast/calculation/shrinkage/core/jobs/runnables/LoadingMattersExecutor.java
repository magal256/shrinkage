package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class LoadingMattersExecutor extends AbstractExecutor {
	private MatterGroup group = null;
	private List<Matter> matters = null;
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
														InterruptedException {
		monitor.beginTask("Загрузка материалов", IProgressMonitor.UNKNOWN);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {		
				group = ((GroupsView)ViewUtils.getView(GroupsView.ID))
											  .getSelectedGroup();
				if(null == group){
					return;
				}
			}
		});
		matters = service.getMattersByGroup(group);
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MattersView mView = (MattersView)ViewUtils.getView(MattersView.ID);
				mView.setMatters(matters);
				mView.setGroup(group);
			}
		});
		monitor.done();
	}
}
