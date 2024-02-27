package org.southplast.calculation.shrinkage.core.controls;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.forms.TolerancesForm;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;


public class LabelListViewer  extends Composite {
	private int size;
	
	private TolerancesForm form;
	
	private CLabel selectedLabel;
	private CLabel[] labels;
	
	public LabelListViewer(TolerancesForm form, Composite parent, int style, int size) {
		super(parent, style);
		
		this.size = size;
		this.form = form;
		labels = new CLabel[size];
		
		setBackground(ViewUtils.WHITE);
		setLayout(new GridLayout(12, false));	
		buildControls();
	}
	
	public void buildControls(){
		for(int i = 0; i < size; i++){
			labels[i] = addWorkmanship(this, null);
		}
	}
	
	private CLabel addWorkmanship(Composite parent, Tolerance w) {
		CLabel lbl = new CLabel(parent, SWT.FLAT | SWT.CENTER);				
		lbl.setText(w != null?w.getName():"");
		lbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		lbl.setBackground(ViewUtils.WHITE);
		lbl.setData(w);
		lbl.addListener(SWT.MouseMove, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				CLabel label = (CLabel)event.widget;
				if(!label.getText().isEmpty()){
					getShell().setCursor(ViewUtils.CURSOR_HAND);
				}										
			}
		});
		lbl.addListener(SWT.MouseExit, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				getShell().setCursor(null);						
			}
		});
		lbl.addListener(SWT.MouseDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {				
				CLabel lbl  = (CLabel)event.widget;				
				if(lbl.getText().isEmpty()){
					return;
				}
				
				form.handleSelectWorkmanship((Tolerance)lbl.getData());				
				deselectItem();				
				selectItem(lbl);										
			}
		});			
		
		return lbl;
	}
	
	public void setSelection(String name){
		for(CLabel l:labels){
			if(l != null && l.getText().equals(name)){
				selectItem(l);
			}
		}
	}
	
	public void setInput(List<Tolerance> wks) {
		for(int i = 0; i < size; i++){
			if(labels[i] == null){
				continue;
			}
			labels[i].setText("");
			labels[i].setData(null);
		}
		int index = 0;
		for(Tolerance w:wks){
			if(index == size){
				break;
			}
			labels[index].setText(w.getName());
			labels[index].setData(w);
			labels[index].redraw();
			index++;
		}
	}
	
	public Tolerance getSelectedElement() {
		return selectedLabel != null?(Tolerance)selectedLabel.getData():null;
	}
	
	private void selectItem(CLabel lbl){
		lbl.setBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_LIST_SELECTION));
		lbl.setForeground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_SELECTION_TEXT));				
		selectedLabel = lbl;	
	}
	
	public void deselectItem() {
		if(selectedLabel != null){
			selectedLabel.setBackground(ViewUtils.WHITE);
			selectedLabel.setForeground(Display.getCurrent()
									.getSystemColor(
									SWT.COLOR_LIST_FOREGROUND));
		}
	}
}
