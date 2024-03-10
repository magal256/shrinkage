package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.jobs.runnables.DeleteGroupExecutor;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;


public class DeleteGroupHandler  extends AbstractBaseHandler {
	public DeleteGroupHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		GroupsView view = (GroupsView)ViewUtils.getView(GroupsView.ID);
		MatterGroup group = view.getSelectedGroup();
		if(group.getMattersCount() == 0){
			boolean delete = MessageDialog.openConfirm(shell, 
								Messages.get("shell.title.confirm.delete"), 
								Messages.get("message.dialog.confirm.delete"));
			if(!delete){
				return null;
			}
			
			new DeleteGroupExecutor(group).run(shell);
		} else {
			MessageDialog.openWarning(shell, 
							Messages.get("shell.title.cant.delete"), 
							Messages.get("message.dialog.cant.delete.group"));
		}						
		return null;
	}
}
