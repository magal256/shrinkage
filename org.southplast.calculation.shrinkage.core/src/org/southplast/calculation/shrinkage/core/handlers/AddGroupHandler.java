package org.southplast.calculation.shrinkage.core.handlers;

import org.eclipse.core.commands.AbstractHandler;
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


public class AddGroupHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		try {
			MatterGroup group = new MatterGroup();
			
			EntityFormBuilder builder = new EntityFormBuilder(shell, group);		
			builder.setFormTitle(Messages.get("shell.title.matter.group"));
			builder.createForm();
			builder.buildTextField("name");
			builder.buildMultiText("description");
			builder.buildControlBar(new SaveGroupExecutor(group), false);
			builder.run();		
			
			new ServiceJob(shell,  new LoadingGroupsExecutor()).schedule();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
