package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenGroupFilterExecutor;


public class OpenFilterHandler extends AbstractBaseHandler {

	public OpenFilterHandler() {
		setBaseEnabled(true);
	}
	
	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(e).getShell();
		
		new OpenGroupFilterExecutor().run(shell);
		
		return null;
	}
	

}
