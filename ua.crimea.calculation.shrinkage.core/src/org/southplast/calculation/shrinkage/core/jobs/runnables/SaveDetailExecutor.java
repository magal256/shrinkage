package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.CalculationView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;


public class SaveDetailExecutor extends AbstractExecutor {
	private Detail detail;
	public SaveDetailExecutor(Detail detail) {
		this.detail = detail;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(Messages.get("progress.add.detail"), IProgressMonitor.UNKNOWN);
		service.saveDetail(detail);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				
				ViewUtils.refreshMeasuringView(CalculationView.ID, detail, 
										   MeasuringType.CALCULATING);
				
				ViewUtils.refreshMeasuringView(MatchingView.ID, detail, 
										   MeasuringType.MATCHING);
			}
		});
		monitor.done();
		
	}
}
