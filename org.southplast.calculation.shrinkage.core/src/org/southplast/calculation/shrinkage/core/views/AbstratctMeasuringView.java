package org.southplast.calculation.shrinkage.core.views;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.middle;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.part.ViewPart;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Shrinkage;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.ExportToCsvExecutor;
import org.southplast.calculation.shrinkage.core.viewers.AbstractShrinkageViewer;


public abstract class AbstratctMeasuringView  extends ViewPart {
	protected static final int MAIN_GRID_COLUMNS_COUNT = 7;
	protected Composite parent;
	protected Action saveAction;
	protected Action cancelAction;
	protected Action addAction;
	protected Action removeAction;	
	protected Action previewAction;
	protected Action exportAction;
	
	private Label matterLabel, detailLabel, rangeLabel;
	
	protected ToolBarManager mainToolBarManager;
	protected ToolBarManager viewerToolBarManager;
	
	protected Detail detail = null;
	
	public AbstratctMeasuringView() {
		createActions();
	}
	
	protected void createActions(){
		exportAction = new Action() {
			@Override
			public void run() {
				Shell shell = getViewSite().getShell();
				new ExportToCsvExecutor(shell, detail, getViewer().getCalculations()).run(shell);
				
			}
		};
		exportAction.setImageDescriptor(ImageFactory.getDescriptor(Images.EXPORT));
		exportAction.setText("Экспортировать расчеты в csv");
	}
	
	protected abstract List<ShrinkageCalculation> getCalculationsPreview();
	
	public abstract void setEnabledToolbars(boolean enabled);
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		parent.setLayout(new GridLayout(MAIN_GRID_COLUMNS_COUNT, false));
		
		{
			Label lbl = new Label(parent, SWT.NONE);
			lbl.setText("Материал:");		
			lbl.setLayoutData(new GridData(GridData.BEGINNING));
			
			matterLabel = new Label(parent, SWT.NONE);
			matterLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL |
									  GridData.BEGINNING));
		}
		{
			Label lbl = new Label(parent, SWT.NONE);
			lbl.setText("Усадка:");
			lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			
			rangeLabel = new Label(parent, SWT.NONE);
			rangeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL |
										GridData.HORIZONTAL_ALIGN_BEGINNING));
		}
		{
			Label lbl = new Label(parent, SWT.NONE);
			lbl.setText("Деталь:");		
			lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			detailLabel = new Label(parent, SWT.NONE);
			detailLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL |
										GridData.HORIZONTAL_ALIGN_BEGINNING));	
		}		
		{
			GridData layData = new GridData(GridData.HORIZONTAL_ALIGN_END |
											GridData.FILL_HORIZONTAL);
			ToolBar toolBar = new ToolBar(parent, SWT.NONE);
			toolBar.setLayoutData(layData);
			mainToolBarManager = new ToolBarManager(toolBar);
			if(null != previewAction){
				mainToolBarManager.add(previewAction);
			}	
			mainToolBarManager.add(saveAction);	
			mainToolBarManager.add(cancelAction);
			mainToolBarManager.update(true);
		}		//new Label(parent, SWT.NONE);
		{
			Label sepLbl = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
			GridData layData = new GridData(GridData.FILL_HORIZONTAL);
			layData.horizontalSpan = MAIN_GRID_COLUMNS_COUNT;
			sepLbl.setLayoutData(layData);
		}
		{
			GridData layData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING |
											GridData.FILL_HORIZONTAL);
			layData.horizontalSpan = MAIN_GRID_COLUMNS_COUNT;
			ToolBar toolBar = new ToolBar(parent, SWT.NONE);
			toolBar.setLayoutData(layData);
			viewerToolBarManager = new ToolBarManager(toolBar);
			viewerToolBarManager.add(addAction);
			viewerToolBarManager.add(removeAction);
			viewerToolBarManager.add(exportAction);			
			viewerToolBarManager.add(new Separator());
			viewerToolBarManager.update(true);
		}
	}
	
	public void setEnabledMainToolBar(boolean enabled){
		saveAction.setEnabled(enabled);
		cancelAction.setEnabled(enabled);
	}
	
	public void setDetail(Detail det) {
		detail = det;
		Shrinkage shr = detail.getMatter().getLongitudinalShrinkage();
		String str = new String(det.getMatter() != null? new String(det.getMatter().getCommercialGrade().trim()):"");
		matterLabel.setText(str + " ");	
		
		rangeLabel.setText(format(shr.getMinimum()) + " - " + format(shr.getMaximum()) + 
						   " (среднее- " + format(middle(shr.getMinimum(), 
								   				shr.getMaximum())) + ")");
		detailLabel.setText((det!=null?det.getName():""));		
	}
	
	public abstract AbstractShrinkageViewer getViewer();
	
	@Override
	public void setTitleToolTip(String toolTip) {
		super.setTitleToolTip(toolTip);
	}
	
	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}	
	
	public Detail getDetail() {
		return detail;
	}
}
