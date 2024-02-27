package org.southplast.calculation.shrinkage.core.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("unchecked")
public class ElementsViewerList extends Composite {
	private ListViewer viewer;
	private Button selectAllButton, deselectAllButton;
	
	private IBaseLabelProvider labelProvider;
	private IContentProvider contentProvider;
	
	@SuppressWarnings("rawtypes")
	private List items = new ArrayList();
	
	public ElementsViewerList(Composite parent, IContentProvider contentProvider, 
												ILabelProvider labelProvider) {
		super(parent, SWT.NONE);
		
		this.labelProvider = labelProvider;
		this.contentProvider = contentProvider;
		
		setLayout(new GridLayout(4, false));
		createContents();
	}
	
	private void createContents(){
		GridData listLayData = new GridData(GridData.FILL_BOTH);		
		
		listLayData.horizontalSpan = 4;
		viewer = new ListViewer(this, SWT.VIRTUAL | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setInput(items);
		viewer.getControl().setLayoutData(listLayData);			
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
				if(sel.size() == items.size()) {
					selectAllButton.setEnabled(false);
					deselectAllButton.setEnabled(true);
				} else if(sel.isEmpty()) {
					deselectAllButton.setEnabled(false);
					selectAllButton.setEnabled(true);
				} else {
					deselectAllButton.setEnabled(true);
					selectAllButton.setEnabled(true);
				}
				
			}
		});

		selectAllButton = new Button(this, SWT.PUSH);
		selectAllButton.setText("Выделить все");
		selectAllButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.getList().selectAll();
				selectAllButton.setEnabled(false);
				deselectAllButton.setEnabled(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		deselectAllButton = new Button(this, SWT.PUSH);
		deselectAllButton.setText("Снять выделение");
		deselectAllButton.setEnabled(false);
		deselectAllButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				viewer.getList().deselectAll();
				selectAllButton.setEnabled(true);
				deselectAllButton.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void setItems(List input){
		items.clear();
		items.addAll(input);
		
		viewer.refresh();
	}
	
	public Button getSelectAllButton() {
		return selectAllButton;
	}
	public Button getDeselectAllButton() {
		return deselectAllButton;
	}
	public ListViewer getViewer() {
		return viewer;
	}
	@SuppressWarnings("rawtypes")
	public void addItems(List items){
		for(Object obj:items){
			addItem(obj);
		}
		
		viewer.refresh();
	}
	public void addItem(Object item){
		if(items.contains(item)){
			return;
		}
		items.add(item);
		viewer.refresh();
	}
	
	@SuppressWarnings("rawtypes")
	public void removeItems(List items){
		for(Object obj:items){
			removeItem(obj);
		}
		
		
		viewer.refresh();
	}
	
	public void removeItem(Object item){
		items.remove(item);
		viewer.refresh();
	}
	
	@SuppressWarnings("rawtypes")
	public List getSelectedItems(){
		return ((IStructuredSelection)viewer.getSelection()).toList();
	}
	
	@SuppressWarnings("rawtypes")
	public List getItems() {
		return items;
	}
}
