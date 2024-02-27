package org.southplast.calculation.shrinkage.core.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.Activator;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.Shrinkage;
import org.southplast.calculation.shrinkage.core.forms.EntityFormBuilder;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingMattersExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveMatterExecutor;
import org.southplast.calculation.shrinkage.core.management.MatterService;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.GroupsView;


public class AddMaterialHandler extends AbstractBaseHandler {
	
//	private List<String> types = null;
	private List<String> makers = null;
	
	public AddMaterialHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		setEnabled(false);
		EntityFormBuilder builder;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		
		@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
		ServiceTracker tracker = new ServiceTracker(
								 		 Activator.getDefault().getContext(), 
										 MatterService.class.getName(),
										 null);
         tracker.open();
         MatterService matterService = (MatterService) tracker.getService();
//         types = matterService.getMattersTypes();
         makers = matterService.getMattersMakers();         
		
         try {
			GroupsView gView = (GroupsView)ViewUtils.getView(GroupsView.ID);	
//			MattersView mView = (MattersView)ViewUtils.getView(MattersView.ID);					
			Matter matter = new Matter();
			matter.setLongitudinalShrinkage(new Shrinkage());
			matter.setCrossShrinkage(new Shrinkage());
			matter.setGroup(gView.getSelectedGroup());
			
			builder = new EntityFormBuilder(shell, matter);		
			builder.setFormTitle(Messages.get("shell.title.matter"));
			builder.createForm();
			builder.buildEditableList("maker", makers);
			builder.buildTextField("commercialGrade");	
			builder.buildObjectList("group", gView.getGroups());
			builder.buildTextField("type");
			builder.buildNumberInterval("longitudinalShrinkage");
			builder.buildNumberInterval("crossShrinkage");		
			builder.buildMultiText("description");
			builder.buildControlBar( new SaveMatterExecutor(matter), false);
			builder.run();
			
			new ServiceJob(shell, new LoadingMattersExecutor()).schedule();						
//			new ServiceJob(shell,  new LoadingGroupsExecutor()).schedule();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}		
}
