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
import org.southplast.calculation.shrinkage.core.utils.OuterDiametralPredicate;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.converters.ShrinkageConverter;
import org.southplast.calculation.shrinkage.core.viewers.factories.DiametralOuterFactory;
import org.southplast.calculation.shrinkage.core.viewers.factories.ViewerControlFactory;
import org.southplast.calculation.shrinkage.core.viewers.listeners.ShrinkageViewerFocusListener;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.DiametralOuterLabelProvider;
import org.southplast.calculation.shrinkage.core.views.CalculationView;


public class DiametralOuterViewer extends AbstractShrinkageViewer {
	private CalculationView view;
	
	public DiametralOuterViewer(CalculationView view, Composite parent, int style) {
		super(parent, style, new DiametralOuterFactory(false));
		
		this.view = view;
		
		XViewerControlFactory cFac = new ViewerControlFactory(view);	      
		XViewerConverter converter = new ShrinkageConverter(this);	
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new DiametralOuterLabelProvider(this));		    
		setXViewerEditAdapter(new XViewerEditAdapter(cFac, converter));
	}
	
	public DiametralOuterViewer(Composite parent, int style) {
		super(parent, style, new DiametralOuterFactory(true));
		super.preview = true;
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new DiametralOuterLabelProvider(this));		    
	}
	
	@Override
	public boolean handleLeftClick(TreeColumn treeColumn, TreeItem treeItem) {
		if(super.preview){
			return false;
		}
		if(treeColumn == null){
			return false;
		}
		if(!(treeColumn.getData() instanceof XViewerColumn)){
			return false;
		}
		XViewerColumn xCol = (XViewerColumn) treeColumn.getData();
		
		if(xCol.equals(DiametralOuterFactory.toleranceName)){			
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSize();
			new LoadingTolerancesExecutor(this, size, calc.getTolerance())
			.run(view.getSite().getShell());
			view.setEnabledMainToolBar(true);
			return true;
		} 
		
		return super.handleLeftClick(treeColumn, treeItem);
	}	
	
	@Override
	public boolean handleLeftClickInIconArea(TreeColumn treeColumn,
											 TreeItem treeItem) {
		if(super.preview){
			return false;
		}
		if(treeColumn == null){
			return false;
		}
		if(!(treeColumn.getData() instanceof XViewerColumn)){
			return false;
		}
		XViewerColumn xCol = (XViewerColumn) treeColumn.getData();
		
		if(xCol.equals(DiametralOuterFactory.toleranceName)) {									
			return handleLeftClick(treeColumn, treeItem);
		}
		
		return super.handleLeftClick(treeColumn, treeItem);
	}
	
	@Override
	public void handleMouseMove(TreeColumn col, TreeItem item) {
		if(super.preview){
			return;
		}
		if(col == null){
			return;
		}
		if(!(col.getData() instanceof XViewerColumn)) {
			return;
		}
		XViewerColumn xCol = (XViewerColumn) col.getData();
		
		if(xCol.equals(DiametralOuterFactory.toleranceName) && item != null) {			
			getTree().setCursor(ViewUtils.CURSOR_HAND);			
		}else if( (xCol.equals(DiametralOuterFactory.diameter) || 
					xCol.equals(DiametralOuterFactory.toleranceUp) ||
					xCol.equals(DiametralOuterFactory.toleranceDown)) 
				&& item != null) {
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else {
			getTree().setCursor(null);
		}
	}
	
	@Override
	protected void createSupportWidgets(Composite parent) {
		super.createSupportWidgets(parent);
		
		getTree().addFocusListener(new ShrinkageViewerFocusListener(this));
	}
	
	@Override
	public void setCalculations(List<ShrinkageCalculation> list) {
		
		super.setCalculations( filter(list, new OuterDiametralPredicate()));
	}
	
	public void handleFocusLost(){
		getTree().deselectAll();
	}
}
