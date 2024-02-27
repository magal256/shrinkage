package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.forms.CalculationPreviewForm;


public class OpenDetailPreviewExecutor extends AbstractExecutor {
	private Detail detail;
	private List<ShrinkageCalculation> previews;
	
	public OpenDetailPreviewExecutor(Detail detail, List<ShrinkageCalculation> prews) {
		this.detail = detail;
		this.previews = prews;	
		this.fork = false;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
	
			monitor.beginTask("Подготовка к просмотру", IProgressMonitor.UNKNOWN);
			if(previews == null || previews.isEmpty()){
				previews = service.getCalculations(detail.getId());
				detail.setTolerances(previews);
			}
			
			Display.getDefault().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					new CalculationPreviewForm(shell, detail, previews).run();
				}
			});
			monitor.done();
			
	}
}
