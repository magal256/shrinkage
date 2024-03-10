package org.southplast.calculation.shrinkage.core.viewers.converters;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.SIGN_SIZE;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.SIZE;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_DOWN;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_SIGN_DOWN;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_SIGN_UP;
import static org.southplast.calculation.shrinkage.core.utils.FieldConstants.TOLERANCE_UP;
import static org.southplast.calculation.shrinkage.core.utils.ShrinkageUtils.defineSignTolerance;
import static org.southplast.calculation.shrinkage.core.utils.ShrinkageUtils.defineTolerance;

import java.math.BigDecimal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.controls.NumberText;
import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.messages.Messages;


public class ShrinkageConverter implements XViewerConverter {
	private static final BigDecimal MAX_SIZE = new BigDecimal("500");
	
	private XViewer viewer;
	
	public ShrinkageConverter(XViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void getInput(Control c, CellEditDescriptor ced, Object obj) {
		if (c instanceof NumberText) {
			try{
				NumberText text = (NumberText) c;
		         
				if (obj instanceof ShrinkageCalculation) {	        	 	        	 
		        	final  ShrinkageCalculation calc = (ShrinkageCalculation) obj;
		        	 final BigDecimal value = new BigDecimal(text.getText().isEmpty()?
		 					 							"0.00":text.getText());	        	 
		        	 if(!validateSize(value) && 
		    			 (ced.getInputField().equals(SIZE) || 
		    			  ced.getInputField().equals(SIGN_SIZE))) {
		        		 showErrorMessage(text.getShell());
		        		 
		        		 return;
		        	 }
		        		 
		        	 if (ced.getInputField().equals(SIZE)) {	  	        		 
		        		 CalculationType type = calc.getType();
		        		 calc.setSize(value);
		        		 defineTolerance(calc);
		        		 
		        		 if(CalculationType.OUTER_UPRIGHT.equals(type) ||
		        			CalculationType.INNER_UPRIGHT.equals(type) ||
		        			CalculationType.MATCHING.equals(type)) {
		        					calc.setSignSize(value);
		        					defineSignTolerance(calc);
		        				}
		        	 } else if(ced.getInputField().equals(SIGN_SIZE)){
		        		 calc.setSignSize(value);
		        		 defineSignTolerance(calc);
		        	 } else if(ced.getInputField().equals(TOLERANCE_DOWN)) {
		        		 Job job = new ServiceJob(null, null) {
							@Override
							protected IStatus run(IProgressMonitor mon) {
								Tolerance tol = service.getToleranceByValues(
													calc.getTolerance().getUp(), 
													value);
								calc.setTolerance(tol);						
								
								Display.getDefault().syncExec(new Runnable() {
									@Override
									public void run() {
										viewer.refresh();
									}
								});
								return Status.OK_STATUS;
							}
						};		        		 		        						
		        		job.schedule();
		        	 } else if(ced.getInputField().equals(TOLERANCE_UP)) {
		        		 
		        		 Job job = new ServiceJob(null, null) {
								
								@Override
								protected IStatus run(IProgressMonitor mon) {									
									Tolerance tol = service.getToleranceByValues(
												value, 
												calc.getTolerance().getDown());
				        	        calc.setTolerance(tol);
									Display.getDefault().syncExec(new Runnable() {
										
										@Override
										public void run() {
											viewer.refresh();
										}
									});
				        	        
									
									return Status.OK_STATUS;
								}
							};	
							
							job.schedule();
		        	 } else if (ced.getInputField().equals(TOLERANCE_SIGN_DOWN)){
		        		 Job job = new ServiceJob(null, null) {
								
								@Override
								protected IStatus run(IProgressMonitor mon) {									
									Tolerance tol = service.getToleranceByValues(
											calc.getSignTolerance().getUp(), 
											value);
				        	        calc.setSignTolerance(tol);
									Display.getDefault().syncExec(new Runnable() {
										@Override
										public void run() {
											viewer.refresh();
										}
									});
									
									return Status.OK_STATUS;
								}
							};	
							
							job.schedule();
		        	 } else if (ced.getInputField().equals(TOLERANCE_SIGN_UP)){
		        		 Job job = new ServiceJob(null, null) {
								
								@Override
								protected IStatus run(IProgressMonitor mon) {									
									Tolerance tol = service.getToleranceByValues(
												value, 
												calc.getSignTolerance().getDown());
				        	        calc.setSignTolerance(tol);
									Display.getDefault().syncExec(new Runnable() {
										
										@Override
										public void run() {
											viewer.refresh();
										}
									});
				        	        
									
									return Status.OK_STATUS;
								}
							};	
							
							job.schedule();
		        	 }
				}	      
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void setInput(Control c, CellEditDescriptor ced, Object selObject) {	
		
		if (c instanceof NumberText) {
			NumberText text = (NumberText) c;
			if(selObject instanceof ShrinkageCalculation) {
				ShrinkageCalculation calc = (ShrinkageCalculation) selObject;
				FormattedText fText = text.getFormattedText();
				try{
					if (SIZE.equals(ced.getInputField())) { 		               
						text.setText(format(calc.getSize()));
					} else if(SIGN_SIZE.equals(ced.getInputField())) {
						text.setText(format(calc.getSignSize()));
					} else if(TOLERANCE_DOWN.equals(ced.getInputField())) {
						fText.setValue(calc.getTolerance().getDown());
					} else if(TOLERANCE_UP.equals(ced.getInputField())) {
						fText.setValue(calc.getTolerance().getUp());
					} else if(TOLERANCE_SIGN_DOWN.equals(ced.getInputField())){
						fText.setValue(calc.getSignTolerance().getDown());
					} else if(TOLERANCE_SIGN_UP.equals(ced.getInputField())){
						fText.setValue(calc.getSignTolerance().getUp());
					}
				} catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}	
	}
	
	private void showErrorMessage(Shell shell){
		MessageDialog.openWarning(shell, Messages.get("shell.title.wrong.size"), 
								  Messages.get("message.error.wrong.size"));
	}
	
	private boolean validateSize(BigDecimal size) {		
		return BigDecimal.ONE.compareTo(size) <= 0 && 
			   MAX_SIZE.compareTo(size) >= 0;
	}
}
