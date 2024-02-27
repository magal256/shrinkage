package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.forms.TolerancesForm;


public class LoadingTolerancesExecutor extends AbstractExecutor {
	private Shell shell;
	private XViewer viewer;
	
	private BigDecimal size;
	private Tolerance tolerance;
	
	public LoadingTolerancesExecutor(XViewer xviewer, BigDecimal size, Tolerance tolerance) {
		this.shell = xviewer.getTree().getShell();
		this.viewer = xviewer;
		this.size = size;
		this.tolerance = tolerance;
		this.fork = false;
	}
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Загрузка списка допусков", IProgressMonitor.UNKNOWN);
		final List<Tolerance> wsForOpenning = service.getTolerancesForOpening(size);
		final List<Tolerance> wsForShaft = service.getTolerancesForShaft(size);		
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				TolerancesForm form = new TolerancesForm(shell, wsForOpenning, 
				       	wsForShaft,
				        tolerance, 
				        size);
				form.run();				
				
				viewer.refresh(true);
			}
		});
		
		monitor.done();
		
	}
}
