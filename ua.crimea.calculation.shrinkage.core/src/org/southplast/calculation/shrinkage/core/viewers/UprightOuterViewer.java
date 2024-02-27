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
import org.southplast.calculation.shrinkage.core.utils.OuterUprightPredicate;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.converters.ShrinkageConverter;
import org.southplast.calculation.shrinkage.core.viewers.factories.UprightOuterFactory;
import org.southplast.calculation.shrinkage.core.viewers.factories.ViewerControlFactory;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.UprightOuterLabelProvider;
import org.southplast.calculation.shrinkage.core.views.CalculationView;


public class UprightOuterViewer extends AbstractShrinkageViewer {
	private CalculationView view;
	public UprightOuterViewer(CalculationView view, Composite parent, int style) {
		super(parent, style, new UprightOuterFactory(false));
		
		this.view = view;
		
		XViewerControlFactory cFac = new ViewerControlFactory(view);	      
		XViewerConverter converter = new ShrinkageConverter(this);	
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new UprightOuterLabelProvider(this));		    
		setXViewerEditAdapter(new XViewerEditAdapter(cFac, converter));
		
	}
	
	public UprightOuterViewer(Composite parent, int style) {
		super(parent, style, new UprightOuterFactory(true));
		super.preview = true;
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new UprightOuterLabelProvider(this));		    
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
		
		if(xCol.equals(UprightOuterFactory.toleranceName)) {			
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSize();
			
			new LoadingTolerancesExecutor(this, size, calc.getTolerance())
			.run(view.getSite().getShell());
			
			view.setEnabledMainToolBar(true);
			return true;
		} else if(xCol.equals(UprightOuterFactory.toleranceNameSign)){
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
		
		if(xCol.equals(UprightOuterFactory.toleranceName) && item != null||
				xCol.equals(UprightOuterFactory.toleranceNameSign)) {			
			getTree().setCursor(ViewUtils.CURSOR_HAND);			
		} else if((xCol.equals(UprightOuterFactory.hight) ||
				   xCol.equals(UprightOuterFactory.toleranceDown) ||
				   xCol.equals(UprightOuterFactory.toleranceUp) ||
				   xCol.equals(UprightOuterFactory.toleranceDownSign) ||
				   xCol.equals(UprightOuterFactory.toleranceUpSign)) 
				  && item != null ) {
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else {
			getTree().setCursor(null);
		}		
	}
	
	@Override
	public void setCalculations(List<ShrinkageCalculation> list) {
		
		super.setCalculations( filter(list, new OuterUprightPredicate()));
	}
}	
