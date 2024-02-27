package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.views.AbstratctMeasuringView;


public class LoadingCalculationsExecutor extends AbstractExecutor {
	
	private AbstratctMeasuringView view;
	
	public LoadingCalculationsExecutor(AbstratctMeasuringView view) {

		this.view = view;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Загрузка расчетов", IProgressMonitor.UNKNOWN);
		
		final Detail detail = view.getDetail();
		final List<ShrinkageCalculation> calcs = service.getCalculations(detail.getId());
		Display.getDefault().syncExec(new Runnable(){
			@Override
			public void run() {
				detail.setTolerances(calcs);
				view.setDetail(detail);	
				view.getViewer().refresh();
			}
		});
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		monitor.done();
		
	}
}
