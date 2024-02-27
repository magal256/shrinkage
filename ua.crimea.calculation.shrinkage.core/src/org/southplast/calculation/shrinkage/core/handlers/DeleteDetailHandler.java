package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.jobs.runnables.DeleteDetailExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingGroupsExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingMattersExecutor;
import org.southplast.calculation.shrinkage.core.messages.Messages;


public class DeleteDetailHandler extends AbstractBaseHandler {
	
	public DeleteDetailHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		boolean delete = MessageDialog.openConfirm(shell, 
								Messages.get("shell.title.confirm.delete"), 
								Messages.get("message.dialog.confirm.delete"));
		
		if(!delete){
			return null;
		}
		
		new DeleteDetailExecutor().run(shell);
		new LoadingMattersExecutor().run(shell);
		new LoadingGroupsExecutor().run(shell);
		
		return null;
	}
}
