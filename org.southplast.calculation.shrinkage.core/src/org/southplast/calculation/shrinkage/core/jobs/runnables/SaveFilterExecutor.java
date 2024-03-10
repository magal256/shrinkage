package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;


public class SaveFilterExecutor extends AbstractExecutor {
	private List<MatterGroup> groupsToHide;
	private List<MatterGroup> groupsToShow;
	
	public SaveFilterExecutor(List<MatterGroup> hidden, List<MatterGroup> shown) {
		groupsToHide = hidden;
		groupsToShow = shown;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Сохранение изменений в фильтре", IProgressMonitor.UNKNOWN);
		
		service.hideGroups(groupsToHide);
		service.showGroups(groupsToShow);
		
		new LoadingGroupsExecutor().run(shell);
		
		monitor.done();
	}
}
