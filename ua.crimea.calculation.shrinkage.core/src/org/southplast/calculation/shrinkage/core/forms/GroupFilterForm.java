package org.southplast.calculation.shrinkage.core.forms;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.controls.ElementsViewerList;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveFilterExecutor;
import org.southplast.calculation.shrinkage.core.utils.Predicate;


import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;

public class GroupFilterForm extends FilialWindow {
	
	private List<MatterGroup> groups;
	
	
	public GroupFilterForm(Shell parentShell, List<MatterGroup> groups) {
		super(parentShell);
		
		this.groups = groups;
		
		setShellStyle(SWT.SHELL_TRIM);
	}

	@Override
	protected Control createContents(Composite parent) {
		ILabelProvider lblProv = new ILabelProvider() {
			
			@Override
			public void removeListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isLabelProperty(Object arg0, String arg1) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addListener(ILabelProviderListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getText(Object obj) {
				MatterGroup group = ((MatterGroup)obj);
				return group.getName() + (group.getMattersCount()!=0?
						(" (" +((MatterGroup)obj).getMattersCount()+ ")"):"");
			}
			
			@Override
			public Image getImage(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		IStructuredContentProvider cntProv = new IStructuredContentProvider() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
				// TODO Auto-generated method stub
				
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object[] getElements(Object obj) {
				
				return ((List<MatterGroup>)obj).toArray();
			}
			
			
		};
		
		parent.setLayout(new GridLayout(1, false));
		
		Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout(3, false));
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label showLbl = new Label(content, SWT.NONE);
		showLbl.setText("Активные группы");
		
		new Label(content, SWT.NONE);
		
		Label  hideLbl = new Label(content, SWT.NONE);
		hideLbl.setText("Спрятанные группы");
		
		final ElementsViewerList showList = new ElementsViewerList(content, cntProv, lblProv);		
		showList.setLayoutData(new GridData(GridData.FILL_BOTH));
		showList.setItems(filter(groups, new Predicate<MatterGroup>() {
			@Override
			public boolean apply(MatterGroup type) {
				
				return !type.isHidden();
			}
		}));
		
		Composite moveComp = new Composite(content, SWT.NONE);
		moveComp.setLayout(new GridLayout(1, false));
		moveComp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		
		final Button toHideBtn = new Button(moveComp, SWT.PUSH);
		toHideBtn.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		toHideBtn.setText(">>");
		toHideBtn.setEnabled(false);		
		
		final Button toShowBtn = new Button(moveComp, SWT.PUSH);
		toShowBtn.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		toShowBtn.setText("<<");
		toShowBtn.setEnabled(false);
		
		final ElementsViewerList hideList = new ElementsViewerList(content, cntProv, lblProv);
		hideList.setLayoutData(new GridData(GridData.FILL_BOTH));
		hideList.setItems(filter(groups, new Predicate<MatterGroup>() {
			@Override
			public boolean apply(MatterGroup type) {
				
				return type.isHidden();
			}
		}));
		GridData toolLayData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		toolLayData.horizontalSpan = 3;				
		
		Composite toolComp = new Composite(content, SWT.NONE);
		toolComp.setLayout(new GridLayout(2, false));		
		toolComp.setLayoutData(toolLayData);
		
		Button saveBtn = new Button(toolComp, SWT.PUSH);
		saveBtn.setText("Сохранить");
		saveBtn.setImage(ImageFactory.getImage(Images.SAVE));
		saveBtn.addSelectionListener(new SelectionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new SaveFilterExecutor(hideList.getItems(), showList.getItems())
				.run(getShell());
				close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		Button cancelBtn = new Button(toolComp, SWT.PUSH);
		cancelBtn.setText("Отменить");
		cancelBtn.setImage(ImageFactory.getImage(Images.CANCEL));
		cancelBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		hideList.getDeselectAllButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toShowBtn.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		hideList.getViewer().getList().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				IStructuredSelection sel = (IStructuredSelection) hideList.getViewer().getSelection();
				if(sel.isEmpty()){
					toShowBtn.setEnabled(false);
				} else {
					toShowBtn.setEnabled(true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		hideList.getSelectAllButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toShowBtn.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		showList.getDeselectAllButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toHideBtn.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		showList.getSelectAllButton().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toHideBtn.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		showList.getViewer().getList().addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) showList.getViewer().getSelection();
				if(sel.isEmpty()){
					toHideBtn.setEnabled(false);
				} else {
					toHideBtn.setEnabled(true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		toHideBtn.addSelectionListener(new SelectionListener() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List items = showList.getSelectedItems();
				showList.removeItems(items);
				
				hideList.addItems(items);
				toHideBtn.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		toShowBtn.addSelectionListener(new SelectionListener() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				List items = hideList.getSelectedItems();
				hideList.removeItems(items);
				
				showList.addItems(items);
				toShowBtn.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		return parent;
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		Point size = getParentShell().getSize();
		shell.setSize(size.x - 100, size.y-200);
		shell.setText("Параметры фильтра по группам");
	}	
}
