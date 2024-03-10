package org.southplast.calculation.shrinkage.core.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.forms.EntityFormBuilder;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingMattersExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveDetailExecutor;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class EditDetailHandler  extends AbstractBaseHandler {
	public EditDetailHandler() {
		setEnabled(false);
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {		
		EntityFormBuilder builder;
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		MattersView view = (MattersView)ViewUtils.getView(MattersView .ID);		
		
		List<MeasuringType> types = new ArrayList<MeasuringType>();
		types.add(MeasuringType.CALCULATING);
		types.add(MeasuringType.MATCHING);
		
		try {
			Detail detail = view.getDetail();						
			
			builder = new EntityFormBuilder(shell, detail);		
			builder.setFormTitle(Messages.get("shell.title.detail"));
			builder.createForm();
			builder.buildTextField("name");				
			builder.buildObjectList("matter", view.getMatters());
			builder.buildObjectList("defaultMeasuring", types);
			builder.buildMultiText("description");
			builder.buildControlBar(new SaveDetailExecutor(detail), true);
			builder.run();
			
			new ServiceJob(shell, new LoadingMattersExecutor()).schedule();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
