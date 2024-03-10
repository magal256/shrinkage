package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.CalculationView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;


public class SaveMatterExecutor extends AbstractExecutor {
	private Matter matter;
	
	public SaveMatterExecutor(Matter matter) {
		this.matter = matter;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(Messages.get("progress.save.matter"), 
				IProgressMonitor.UNKNOWN);

		service.saveMatter(matter);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				List<Detail> details = matter.getDetails();
				if(details!= null){
					for(Detail det:details){
						//ViewUtils.closeMeasuringView(CalculationView.ID, det, 
						//							 MeasuringType.CALCULATING);
						//	
						//ViewUtils.closeMeasuringView(MatchingView.ID, det, 
						//						   MeasuringType.MATCHING);
						ViewUtils.refreshMeasuringView(CalculationView.ID, det, 
												   MeasuringType.CALCULATING);
						
						ViewUtils.refreshMeasuringView(MatchingView.ID, det, 
												   MeasuringType.MATCHING);
					}
				}
			}
		});
		monitor.done();
	}
}
