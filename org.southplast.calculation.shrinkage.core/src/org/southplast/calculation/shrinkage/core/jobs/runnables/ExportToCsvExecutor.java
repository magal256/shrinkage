package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;


public class ExportToCsvExecutor extends AbstractExecutor {
	private Shell shell;
	private List<ShrinkageCalculation> calculations;
	private Detail detail;
	private String filePath;
	
	public ExportToCsvExecutor(Shell shell, Detail detail, List<ShrinkageCalculation> calcs) {
		this.shell = shell;
		this.calculations = calcs;
		this.detail = detail;
		this.fork = false;
	}
	
	@Override
	public void run(IProgressMonitor arg0) throws InvocationTargetException,
			InterruptedException {
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				boolean selectFile = false;
				String filterPath = "${user.home}";
				while(!selectFile){
					shell.setEnabled(false);
					selectFile = true;
					FileDialog dialog = new FileDialog(shell, SWT.SAVE);
					dialog.setText("Сохранить расчеты");
				    dialog.setFilterNames(new String[] { "Csv Files", "All Files (*.*)" });
				    dialog.setFilterExtensions(new String[] { "*.csv", "*.*" }); // Windows
				    dialog.setFilterPath(filterPath); 
				    dialog.setFileName("shrinkage.csv");
				    filePath = dialog.open();
				    if(filePath != null){
				    	if(new File(filePath).exists()){
				    		MessageDialog confirm = new MessageDialog(shell, 
				    				"Подтвердить сохранение",
			            			null, filePath + " уже существует.\n Заменить?",
			            			MessageDialog.CONFIRM,
			            			new String[] { "Да", "Отменить"}, 2);
							int answer = confirm.open();		        
							switch (answer) {
							  case -1: return;		            
							  case 0 : break;
							  case 1 : selectFile = false; break;		          
							}
				    		
				    	}
				    }
				    shell.setEnabled(true);
				}
				
			}
		});
	    if(filePath != null){
	    	service.exportCalculations(detail, calculations, filePath);
	    }
	    Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if(filePath == null){
					return;
				}
				MessageDialog confirm = new MessageDialog(shell, 
	    				"Операция завершена",
            			null, "Экспорт в файл " + filePath + " завершен",
            			MessageDialog.INFORMATION,
            			new String[] { "Просмотреть CSV файл", "Ок"}, 2);
				int answer = confirm.open();		        
				switch (answer) {
				  case -1: return;		            
				  case 0 : Program.launch(filePath); break;
				  case 1 : break;		          
				}
			}
		});
		
	}
}
