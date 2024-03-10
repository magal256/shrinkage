package org.southplast.calculation.shrinkage.core.viewers;

import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerControlFactory;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerEditAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingTolerancesExecutor;
import org.southplast.calculation.shrinkage.core.utils.InnerUprightPredicate;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.converters.ShrinkageConverter;
import org.southplast.calculation.shrinkage.core.viewers.factories.UprightInnerFactory;
import org.southplast.calculation.shrinkage.core.viewers.factories.ViewerControlFactory;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.UprightInnerLabelProvider;
import org.southplast.calculation.shrinkage.core.views.CalculationView;


public class UprightInnerViewer extends AbstractShrinkageViewer {
	private CalculationView view;
	public UprightInnerViewer(CalculationView view, Composite parent, int style) {
		super(parent, style, new UprightInnerFactory(false));
		
		this.view = view;
		
		XViewerControlFactory cFac = new ViewerControlFactory(view);	      
		XViewerConverter converter = new ShrinkageConverter(this);	
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new UprightInnerLabelProvider(this));		    
		setXViewerEditAdapter(new XViewerEditAdapter(cFac, converter));
	}
	
	public UprightInnerViewer(Composite parent, int style) {
		super(parent, style, new UprightInnerFactory(true));
		super.preview = true;
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new UprightInnerLabelProvider(this));		    
	}
	
	@Override
	public boolean handleLeftClick(TreeColumn treeColumn, TreeItem treeItem) {
		if(super.preview){
			return false;
		}
		if(treeColumn == null){
			return false;
		}
		if(!(treeColumn.getData() instanceof XViewerColumn)) {
			return false;
		}
		XViewerColumn xCol = (XViewerColumn) treeColumn.getData();
		
		if(xCol.equals(UprightInnerFactory.toleranceName)) {			
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSize();
			new LoadingTolerancesExecutor(this, size, calc.getTolerance())
			.run(view.getSite().getShell());
			view.setEnabledMainToolBar(true);
			return true;
		} else if(xCol.equals(UprightInnerFactory.toleranceNameSign)){
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSize();
			new LoadingTolerancesExecutor(this, size, calc.getSignTolerance())
			.run(view.getSite().getShell());
			view.setEnabledMainToolBar(true);
			return true;
		}
		
		return super.handleLeftClick(treeColumn, treeItem);
	}	
	
	@Override
	public boolean handleLeftClickInIconArea(TreeColumn treeColumn, TreeItem treeItem) {
		return  handleLeftClick(treeColumn, treeItem);
	}
	
	@Override
	public void handleMouseMove(TreeColumn col, TreeItem item) {
		if(super.preview){
			return;
		}
		if(col == null){
			return;
		}
		if(!(col.getData() instanceof XViewerColumn)){
			return;
		}
		XViewerColumn xCol = (XViewerColumn) col.getData();
		
		if((xCol.equals(UprightInnerFactory.toleranceName)  ||
			xCol.equals(UprightInnerFactory.toleranceNameSign)) 
		   && item != null) {			
			getTree().setCursor(ViewUtils.CURSOR_HAND);			
		} else if((xCol.equals(UprightInnerFactory.hight) ||
				   xCol.equals(UprightInnerFactory.toleranceDown) ||
				   xCol.equals(UprightInnerFactory.toleranceUp) ||
				   xCol.equals(UprightInnerFactory.toleranceDownSign) ||
				   xCol.equals(UprightInnerFactory.toleranceUpSign)) 
				  && item != null ) {
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else {
			getTree().setCursor(null);
		}		
	}
	
	@Override
	public void setCalculations(List<ShrinkageCalculation> list) {
		
		
		super.setCalculations( filter(list, new InnerUprightPredicate()));
	}
}
