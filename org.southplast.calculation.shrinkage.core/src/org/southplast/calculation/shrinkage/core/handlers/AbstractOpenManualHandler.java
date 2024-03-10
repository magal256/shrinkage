package org.southplast.calculation.shrinkage.core.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.jobs.runnables.AbstractExecutor;


public abstract class AbstractOpenManualHandler extends AbstractBaseHandler {
	protected AbstractExecutor executor;
	protected int workmanship;
	private  Map<Integer, AbstractExecutor> executorsMap = new HashMap<Integer, AbstractExecutor>();
	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(e).getShell();
		
		workmanship = Integer.parseInt(e.getParameter("org.southplast.calculation.shrinkage.core.workmanship"));
		
		if(executorsMap.get(workmanship) == null) {
			executorsMap.put(workmanship, getExecutorInstance());
		}
		
		executorsMap.get(workmanship).run(shell);

		return null;
	}
	
	protected abstract AbstractExecutor getExecutorInstance();
}
