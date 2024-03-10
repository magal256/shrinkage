package org.southplast.calculation.shrinkage.core.viewers;

import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;

import java.util.List;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerControlFactory;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerEditAdapter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.utils.InteraxialPredicate;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.converters.ShrinkageConverter;
import org.southplast.calculation.shrinkage.core.viewers.factories.InteraxialFactory;
import org.southplast.calculation.shrinkage.core.viewers.factories.ViewerControlFactory;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.InteraxialLabelProvider;
import org.southplast.calculation.shrinkage.core.views.CalculationView;


public class InteraxialViewer extends AbstractShrinkageViewer{
	public InteraxialViewer(CalculationView view, Composite parent, int style) {
		super(parent, style, new InteraxialFactory(false));
			
		XViewerControlFactory cFac = new ViewerControlFactory(view);	      
		XViewerConverter converter = new ShrinkageConverter(this);	
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new InteraxialLabelProvider(this));		    
		setXViewerEditAdapter(new XViewerEditAdapter(cFac, converter));
	}

	public InteraxialViewer(Composite parent, int style) {
		super(parent, style, new InteraxialFactory(true));			
		super.preview = true;
		
		setContentProvider(new ShrinkageContentProvider());
		setLabelProvider(new InteraxialLabelProvider(this));		    
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
		
		if(xCol.equals(InteraxialFactory.size) && item != null ) {
			getTree().setCursor(ViewUtils.CURSOR_IBEAM);
		} else {
			getTree().setCursor(null);
		}		
	}
	
	@Override
	public void setCalculations(List<ShrinkageCalculation> list) {
		
		super.setCalculations( filter(list, new InteraxialPredicate()));
	}
}
