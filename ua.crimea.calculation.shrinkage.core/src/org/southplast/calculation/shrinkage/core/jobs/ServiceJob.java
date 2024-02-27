package org.southplast.calculation.shrinkage.core.jobs;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.Activator;
import org.southplast.calculation.shrinkage.core.management.MatterService;


public class ServiceJob extends Job{
	protected MatterService service;
	private boolean fork = true;
	private IRunnableWithProgress runnable;
	private Shell shell;
	
	public ServiceJob(Shell shell, IRunnableWithProgress runnable) {
		super("");
		
		@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
		ServiceTracker tracker = new ServiceTracker(
								 		 Activator.getDefault().getContext(), 
										 MatterService.class.getName(),
										 null);
         tracker.open();
         
         this.service = (MatterService) tracker.getService();
         this.runnable = runnable;
         this.shell = shell;
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					new ProgressMonitorDialog(shell).run(fork, false, runnable);
				} catch (InvocationTargetException e) {
					MessageDialog.openError(shell, "Ошибка", 
							"Во время операции возникла ошибка. \n" +
							(e.getLocalizedMessage() != null? 
									e.getLocalizedMessage():e.fillInStackTrace()));
					e.printStackTrace();
				} catch (InterruptedException e) {
					
					MessageDialog.openError(shell, "Ошибка", 
							"Во время операции возникла ошибка. \n" +
							(e.getLocalizedMessage() != null? 
									e.getLocalizedMessage():e.getMessage()));
					e.printStackTrace();
				}
			}
		});
		return Status.OK_STATUS;
	}
	
	public void setFork(boolean fork) {
		this.fork = fork;
	}
}
