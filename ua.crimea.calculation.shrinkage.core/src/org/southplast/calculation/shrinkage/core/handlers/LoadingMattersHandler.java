package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingMattersExecutor;


public class LoadingMattersHandler extends AbstractBaseHandler {
	public LoadingMattersHandler() {
		setEnabled(false);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		 new LoadingMattersExecutor().run(shell);
		
		return null;
	}

}
