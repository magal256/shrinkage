package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.forms.GroupFilterForm;


public class OpenGroupFilterExecutor extends AbstractExecutor {
	public OpenGroupFilterExecutor() {
		this.fork = false;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Загрузка списка групп", IProgressMonitor.UNKNOWN);
		
		final List<MatterGroup> groups = service.getAllMatterGroups(); 
		
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				new GroupFilterForm(shell, groups).run();
			}
		});
		
		monitor.done();
	}
	
}
