package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenDetailPreviewExecutor;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class OpenDetailPreviewHandler extends AbstractBaseHandler {
 
	public OpenDetailPreviewHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent e) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(e).getShell();
		MattersView view = (MattersView)ViewUtils.getView(MattersView .ID);	
		
		new OpenDetailPreviewExecutor(view.getDetail(), null).run(shell);
		
		return null;
	}

}
