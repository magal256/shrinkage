package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.messages.Messages;


public class SaveGroupExecutor extends AbstractExecutor {
	private MatterGroup group;
	
	public SaveGroupExecutor(MatterGroup group) {
		this.group = group;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(Messages.get("progress.save.matter.group"), 
				IProgressMonitor.UNKNOWN);

		service.saveMatterGroup(group);
		
		monitor.done();
		
	}
}
