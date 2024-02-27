package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.CalculationView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class DeleteMatterExecutor extends AbstractExecutor {
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
														InterruptedException {
		monitor.beginTask(Messages.get("progress.delete.matter"), 
				  IProgressMonitor.UNKNOWN);

		Display.getDefault().syncExec(new Runnable() {			
			@Override
			public void run() {
				MattersView view = (MattersView) ViewUtils.getView(MattersView.ID);
				service.deleteMatter(view.getMatter());
				List<Detail> details = view.getMatter().getDetails();
				for(Detail det:details){
					ViewUtils.closeMeasuringView(CalculationView.ID, det, 
											   MeasuringType.CALCULATING);
					
					ViewUtils.closeMeasuringView(MatchingView.ID, det, 
											   MeasuringType.MATCHING);
				}
			}
		});
		
		monitor.done();
	}
}
