package org.southplast.calculation.shrinkage.core.viewers;

import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingTolerancesExecutor;
import org.southplast.calculation.shrinkage.core.utils.MatchingPredicate;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.factories.MatchingViewerFactory;
import org.southplast.calculation.shrinkage.core.viewers.listeners.MouseMoveListener;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.MatchingLabelProvider;
import org.southplast.calculation.shrinkage.core.views.MatchingView;


public class MatchingShrinkageViewer extends AbstractShrinkageViewer {
	public MatchingView view;
	
	public MatchingShrinkageViewer(MatchingView view, Composite parent, int style) {
		super(parent, style, new MatchingViewerFactory(false));		
		this.view = view;
	}		
	
	public MatchingShrinkageViewer(Composite parent, int style) {
		super(parent, style, new MatchingViewerFactory(true));	
		super.preview = true;
		
		setLabelProvider(new MatchingLabelProvider(this));
		setContentProvider(new ShrinkageContentProvider());	
	}	
	
	@Override
	protected void createSupportWidgets(Composite parent) {
		
		super.createSupportWidgets(parent);
		
		getTree().addListener(SWT.MouseMove, new MouseMoveListener(this));			
	}
	
	public void handleMouseMove(TreeColumn col, TreeItem item){
		if(super.preview){
			return;
		}
		if(col == null){
			return;
		}
		if(item != null &&
			col.getText().equals(MatchingViewerFactory.toleranceName.getName())) {			
			getTree().setCursor(ViewUtils.CURSOR_HAND);			
		}else if( col.getText().equals(MatchingViewerFactory.size.getName()) && 
				item != null){
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else if(col.getText().equals(MatchingViewerFactory.sizeSign.getName()) &&
				item != null){
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else if(col.getText().equals(MatchingViewerFactory.toleranceNameSign.getName()) &&
				item != null){
			getTree().setCursor(ViewUtils.CURSOR_HAND);
		}else if ((col.getText().equals(MatchingViewerFactory.toleranceUp.getName()) || 
				   col.getText().equals(MatchingViewerFactory.toleranceDown.getName()) || 
				   col.getText().equals(MatchingViewerFactory.toleranceDownSign.getName()) ||
				   col.getText().equals(MatchingViewerFactory.toleranceUpSign.getName())) &&
				  item != null){
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else {
			getTree().setCursor(null);
		}
	}
	
	@Override
	public boolean handleLeftClick(TreeColumn treeColumn, TreeItem treeItem) {
		if(super.preview){
			return false;
		}
		
		if(treeColumn.getText().equals(MatchingViewerFactory.toleranceName.getName())){			
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSize();
			new LoadingTolerancesExecutor(this, size, calc.getTolerance())
			.run(view.getSite().getShell());
			view.setEnabledMainToolBar(true);
			return true;
		}else if(treeColumn.getText().equals(MatchingViewerFactory.toleranceNameSign.getName())){
			ShrinkageCalculation calc = getSelectedCalculation();
			BigDecimal size = calc.getSignSize();			
			
			new LoadingTolerancesExecutor(this, size, calc.getSignTolerance())
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
		if(treeColumn.getText().equals(MatchingViewerFactory.toleranceName.getName())){									
			return handleLeftClick(treeColumn, treeItem);
		}else if(treeColumn.getText().equals(MatchingViewerFactory.toleranceNameSign.getName())) {
			return handleLeftClick(treeColumn, treeItem);
		}
		return super.handleLeftClick(treeColumn, treeItem);
	}	
	
	@Override
	public void setCalculations(List<ShrinkageCalculation> list) {
		if(list == null){
			return;
		}		
		
		super.setCalculations(filter(list, new MatchingPredicate()));
	}
}
