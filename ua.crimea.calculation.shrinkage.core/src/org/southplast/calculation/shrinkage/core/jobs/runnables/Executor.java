package org.southplast.calculation.shrinkage.core.jobs.runnables;

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.Activator;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.management.MatterService;


public abstract class Executor implements IRunnableWithProgress {
	protected MatterService service;
	
	public Executor() {
		@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
		ServiceTracker tracker = new ServiceTracker(
								 		 Activator.getDefault().getContext(), 
										 MatterService.class.getName(),
										 null);
         tracker.open();
         
         service = (MatterService) tracker.getService();
	}
	
	public void run(Shell shell) {
		new ServiceJob(shell, this).schedule();
	}
}
