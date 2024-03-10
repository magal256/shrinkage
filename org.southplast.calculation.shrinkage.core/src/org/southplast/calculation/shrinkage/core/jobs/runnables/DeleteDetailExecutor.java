package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.CalculationView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class DeleteDetailExecutor extends AbstractExecutor {
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(Messages.get("progress.delete.detail"), 
				  		  IProgressMonitor.UNKNOWN);

		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				MattersView view = (MattersView)ViewUtils.getView(MattersView.ID);
								
				service.deleteDetail(view.getDetail());
				
				ViewUtils.closeMeasuringView(CalculationView.ID, view.getDetail(), 
						   							MeasuringType.CALCULATING);
				ViewUtils.closeMeasuringView(MatchingView.ID, view.getDetail(), 
						   								MeasuringType.MATCHING);
			}
		});
		
		monitor.done();
	}
}
