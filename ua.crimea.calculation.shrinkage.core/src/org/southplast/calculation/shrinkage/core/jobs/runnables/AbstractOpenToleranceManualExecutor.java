package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.forms.TolerancesManualForm;


public abstract class AbstractOpenToleranceManualExecutor extends AbstractExecutor {
	private TolerancesManualForm form;
	
	public AbstractOpenToleranceManualExecutor() {
		this.fork = false;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		final boolean isNewForm = form == null || form.getShell() == null || 
								  				form.getShell().isDisposed();
		if(isNewForm){
			form = TolerancesManualForm.getInstance(shell, getTolerancesManual(), 
																	getTitle());
		}
		
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if(isNewForm){
					form.run();
				} else {
					form.getShell().setActive();
				}
			}
		});
	}
	
	public abstract List<Map<String, Object>> getTolerancesManual();
	
	public abstract String getTitle();
}
