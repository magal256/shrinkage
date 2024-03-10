package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;


public class DeleteGroupExecutor extends AbstractExecutor {
	private MatterGroup group;
	
	public DeleteGroupExecutor(MatterGroup group) {
		this.group = group;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(Messages.get("progress.delete.matter.group"), 
				  IProgressMonitor.UNKNOWN);

		service.deleteMatterGroup(group);
		
		final  List<MatterGroup> groups = service.getMatterGroups(); 
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				GroupsView view = (GroupsView)ViewUtils.getView(GroupsView.ID);	
				view.setGroups(groups);
			}
		});
		
		monitor.done();
		
	}
}
