package org.southplast.calculation.shrinkage.core;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.domain.Property;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingGroupsExecutor;
import org.southplast.calculation.shrinkage.core.management.PropertyService;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(400, 300));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("Справочник по литьевым усадкам");		 
	}
	
	@Override
	public void postWindowOpen() {
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		
		Shell shell = configurer.getWindow().getShell(); 
		new LoadingGroupsExecutor().run(shell);
		/*
		@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
		ServiceTracker tracker = new ServiceTracker(
		 		 Activator.getDefault().getContext(), 
				 PropertyService.class.getName(),
				 null);
		tracker.open();

		PropertyService service = (PropertyService) tracker.getService();
		Property startDate = service.get("start_date");
		Property count = service.get("count");
		Property maxCount = service.get("max_count");
		if(startDate == null){
			service.addProperty(new Property("start_date", new Date()));
		} else if(count.getValue() <= maxCount.getValue()) {
			service.editProperty(new Property("count", count.getValue() + 1));
			Calendar currentDay = Calendar.getInstance();
			currentDay.setTime(new Date());
			
			Calendar lastDay = Calendar.getInstance();
			lastDay.setTime(startDate.getDate());
			lastDay.add(Calendar.DAY_OF_YEAR, 60);
			
			if(currentDay.after(lastDay)) {
				configurer.getWindow().getWorkbench().close();
			}
		} else {
			configurer.getWindow().getWorkbench().close();
		}*/
	}
	@Override
	public void postWindowCreate() {				 
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		
		if(configurer.getWindow().getShell() != null){
			configurer.getWindow().getShell().setMaximized(true);
			configurer.getWindow().getShell().setImage(ImageFactory.getImage(
																Images.MAIN));
		}	

		IWorkbenchPage page = configurer.getWindow().getActivePage();

		page.addPartListener(new IPartListener2() {
			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
			
			}

			@Override
			public void partOpened(IWorkbenchPartReference partRef) {
				
			}

			@Override
			public void partInputChanged(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				
			}

			@Override
			public void partDeactivated(IWorkbenchPartReference partRef) {
			
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				
			}

			@Override
			public void partBroughtToTop(IWorkbenchPartReference partRef) {
			
			}

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {	
//				if(partRef.getPart(true) instanceof GroupsView){
//					Shell shell = partRef.getPart(true).getSite().getShell();
//					new ServiceJob(shell, new LoadingGroupsExecutor()).schedule();
//					
//				}
			}
		});
	}		
}
