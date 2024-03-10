package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;


public class LoadingToleransesByNameExecutor extends AbstractExecutor {
	private Tolerance tolerance;
	public LoadingToleransesByNameExecutor(Tolerance tolerance) {
		this.tolerance = tolerance;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
														InterruptedException {
		List<Tolerance> sizeList = service.getTolerancesByName(tolerance.getName());
		tolerance.setSizeList(sizeList);
	}
}
