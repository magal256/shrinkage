package org.southplast.calculation.shrinkage.core.viewers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.viewers.listeners.MouseMoveListener;


public abstract class AbstractShrinkageViewer extends XViewer {
	protected boolean preview;
	
	public AbstractShrinkageViewer(Composite parent, int style, XViewerFactory fac) {
		super(parent, style, fac);
	}
	
	public abstract void handleMouseMove(TreeColumn col, TreeItem item);
	
	@Override
	protected void createSupportWidgets(Composite parent) {
		
		super.createSupportWidgets(parent);
		
		getTree().addListener(SWT.MouseMove, new MouseMoveListener(this));			
	}
	
	@Override
	public MenuManager getMenuManager() {		
		return new MenuManager();
	}
	
	public void setCalculations(List<ShrinkageCalculation> list) {
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++) {
				list.get(i).setNumber(i + 1);
			}
		}
		
		super.setInput(list);
	}	
	
	public ShrinkageCalculation getSelectedCalculation(){
		IStructuredSelection sel = (IStructuredSelection) getSelection();
		
		return (ShrinkageCalculation)sel.getFirstElement();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShrinkageCalculation> getCalculations(){
		return getInput() != null?(List<ShrinkageCalculation>)getInput():
								  new ArrayList<ShrinkageCalculation>();
	}
}
