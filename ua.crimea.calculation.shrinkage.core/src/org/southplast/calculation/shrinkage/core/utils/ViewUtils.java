package org.southplast.calculation.shrinkage.core.utils;


import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.handlers.HandlerProxy;
import org.eclipse.ui.part.ViewPart;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.handlers.AbstractBaseHandler;
import org.southplast.calculation.shrinkage.core.views.AbstratctMeasuringView;


@SuppressWarnings("restriction")
public class ViewUtils {	
	public static final Color WHITE = new Color(Display.getCurrent(), 255, 255, 
																		   255);
	public static final Color RED = new Color(Display.getCurrent(), 255, 30, 0);
	public static final Color PINK = new Color(Display.getCurrent(), 255, 224, 
																		  207);
	public static final Cursor CURSOR_WAIT = new Cursor(Display.getCurrent(), 
															SWT.CURSOR_WAIT);
	public static final Cursor CURSOR_IBEAM = new Cursor(Display.getCurrent(), 
															SWT.CURSOR_IBEAM);
	public static final Cursor CURSOR_HAND = new Cursor(Display.getCurrent(), 
															SWT.CURSOR_HAND);
	
	public static void setCommandEnabled(String commandId, boolean value){
		ICommandService commandService = (ICommandService) PlatformUI
											.getWorkbench()
											.getService(ICommandService.class);

		Command command = commandService.getCommand(commandId);
		IHandler handler = command.getHandler();
		try {
			Field field = HandlerProxy.class.getDeclaredField("handler");
			field.setAccessible(true);
			((AbstractBaseHandler)field.get(handler)).setEnabled(value);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ViewPart getView(String id) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				  												.getActivePage();
		return (ViewPart) page.findViewReference(id).getView(true);
	}
	
	public static void closeMeasuringView(String id,  Detail detail, 
			MeasuringType type){
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				  .getActiveWorkbenchWindow()
				  .getActivePage();
		IViewReference vRef =  activePage.findViewReference(id, detail.getId() + " " + type);
		if(vRef == null){
				return;
		}

		AbstratctMeasuringView mView = (AbstratctMeasuringView)vRef.getView(true);
		
		activePage.hideView(mView);
		mView.dispose();
	}
	
	public static AbstratctMeasuringView refreshMeasuringView(String id,  Detail detail, 
														MeasuringType type){
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
											  .getActiveWorkbenchWindow()
											  .getActivePage();
		String name = detail.getName();
		IViewReference vRef =  activePage.findViewReference(id, detail.getId() + " " + type);
		if(vRef == null){
			return null;
		}
		
		AbstratctMeasuringView mView = (AbstratctMeasuringView)vRef.getView(true);
		
		List<ShrinkageCalculation> calcs = mView.getDetail().getTolerances();
		detail.setTolerances(calcs);
		mView.setPartName(name);
		mView.setTitleToolTip(name + " ("+ type + ")");
		mView.setDetail(detail);
		
		return mView; 
	}
	
	public static AbstratctMeasuringView showMeasuringView(String id, Detail detail, 
												MeasuringType type) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
											  .getActiveWorkbenchWindow()
											  .getActivePage();
		String name = detail.getName();
							
		try {
			
			activePage.showView(id, detail.getId() + " " + type, IWorkbenchPage.VIEW_ACTIVATE);
			AbstratctMeasuringView mView = ((AbstratctMeasuringView) activePage.findViewReference(id, 
					detail.getId() + " " + type)
				   												.getView(true));
			mView.setPartName(name);
			mView.setTitleToolTip(name + " ("+ type + ")");
			mView.setDetail(detail);
			return mView;
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
