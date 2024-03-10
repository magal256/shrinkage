package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.AbstratctMeasuringView;
import org.southplast.calculation.shrinkage.core.views.MatchingView;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class MatchingHandler extends AbstractBaseHandler {
	
	public MatchingHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
			     							  .getActiveWorkbenchWindow()
			     							  .getActivePage();
		MattersView view = ((MattersView) activePage.findViewReference(MattersView.ID)
															    .getView(true));
		Detail det = view.getDetail();
		
		if(det != null) {
			AbstratctMeasuringView cView =  ViewUtils.showMeasuringView(MatchingView.ID, 
												det, MeasuringType.MATCHING);
			new LoadingCalculationsExecutor(cView).run(shell);
		}
		
		return null;
	}

}
