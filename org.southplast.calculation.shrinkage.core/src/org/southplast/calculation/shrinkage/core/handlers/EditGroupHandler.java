package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.forms.EntityFormBuilder;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingGroupsExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveGroupExecutor;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;


public class EditGroupHandler  extends AbstractBaseHandler {
	public EditGroupHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		GroupsView view = (GroupsView)ViewUtils.getView(GroupsView.ID);
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		EntityFormBuilder builder;
		try {
			MatterGroup group = view.getSelectedGroup();
			
			builder = new EntityFormBuilder(shell, group);		
			builder.setFormTitle(Messages.get("shell.title.matter.group"));
			builder.createForm();
			builder.buildTextField("name");
			builder.buildMultiText("description");
			builder.buildControlBar(new SaveGroupExecutor(group), true);
			builder.run();
					
			new ServiceJob(shell,  new LoadingGroupsExecutor()).schedule();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
