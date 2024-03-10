package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.southplast.calculation.shrinkage.core.domain.Detail;


public class SaveCalculationsExecutor extends AbstractExecutor {
	private Detail detail;
	
	public SaveCalculationsExecutor(Detail detail) {
		this.detail = detail;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Сохранение расчетов в базе", IProgressMonitor.UNKNOWN);
		
		service.saveShrinkageCalculations(detail.getTolerances(), detail.getId());
		
		monitor.done();
		
	}
	
}
