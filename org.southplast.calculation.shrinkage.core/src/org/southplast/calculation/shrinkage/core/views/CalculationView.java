package org.southplast.calculation.shrinkage.core.views;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.Activator;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenDetailPreviewExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.management.MatterService;
import org.southplast.calculation.shrinkage.core.viewers.AbstractShrinkageViewer;
import org.southplast.calculation.shrinkage.core.viewers.DiametralInnerViewer;
import org.southplast.calculation.shrinkage.core.viewers.DiametralOuterViewer;
import org.southplast.calculation.shrinkage.core.viewers.InteraxialViewer;
import org.southplast.calculation.shrinkage.core.viewers.UprightInnerViewer;
import org.southplast.calculation.shrinkage.core.viewers.UprightOuterViewer;


public class CalculationView extends AbstratctMeasuringView {

	public static final String ID = "org.southplast.calculation.shrinkage.core.views.CalculationView";
	
	private PShelf shelf;
	private PShelfItem outerDiameterShelfItem;
	private PShelfItem innerDiameterShelfItem;
	private PShelfItem uprightOuterShelfItem;
	private PShelfItem uprightInnerShelfItem;
	private PShelfItem interaxialShelfItem;
	private DiametralOuterViewer diametralOuterViewer;
	private DiametralInnerViewer diametralInnerViewer;
	private UprightOuterViewer uprightOuterViewer;
	private UprightInnerViewer uprightInnerViewer;
	private InteraxialViewer interaxialViewer;
	
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
//		set layout and head controls
		super.createPartControl(parent);
		
//		set special controls		
		{
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = MAIN_GRID_COLUMNS_COUNT;
			
			shelf = new PShelf(parent, SWT.NONE);
			shelf.setLayoutData(gridData);	
			shelf.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {					
					if(getViewer() == null || 
						getViewer().getSelection() == null || 
						getViewer().getSelection().isEmpty()) {
						removeAction.setEnabled(false);
					} else {
						removeAction.setEnabled(true);
					}
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {					
					
				}
			});
		}
		{
			outerDiameterShelfItem = new PShelfItem(shelf, SWT.NONE);
			outerDiameterShelfItem.setText("Диаметральный внешний (охватываемый)");	    
			outerDiameterShelfItem.getBody().setLayout(new FillLayout());			
		    diametralOuterViewer = new DiametralOuterViewer(this,
		    									outerDiameterShelfItem.getBody(), 
		    									SWT.VIRTUAL | SWT.SINGLE | 
		    									SWT.FULL_SELECTION);	
		    diametralOuterViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {					
					removeAction.setEnabled(!event.getSelection().isEmpty());
				}
			});
		}
		{
			uprightOuterShelfItem = new PShelfItem(shelf,SWT.NONE);
			uprightOuterShelfItem.setText("Высотный расчет" );
			uprightOuterShelfItem.getBody().setLayout(new FillLayout());
			
			uprightOuterViewer = new UprightOuterViewer(this, uprightOuterShelfItem.getBody(), 
													SWT.VIRTUAL | SWT.SINGLE | 
													SWT.FULL_SELECTION);
			uprightOuterViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {					
					removeAction.setEnabled(!event.getSelection().isEmpty());
				}
			});
		}
		{
			innerDiameterShelfItem = new PShelfItem(shelf,SWT.NONE);
			innerDiameterShelfItem.setText("Диаметральный внутрений (охватываемый)");
		    
			innerDiameterShelfItem.getBody().setLayout(new FillLayout());
			
			diametralInnerViewer = new DiametralInnerViewer(this, 
												innerDiameterShelfItem.getBody(), 
												SWT.VIRTUAL | SWT.SINGLE | 
												SWT.FULL_SELECTION);
			diametralInnerViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {					
					removeAction.setEnabled(!event.getSelection().isEmpty());
				}
			});						
		}
		{
			interaxialShelfItem = new PShelfItem(shelf,SWT.NONE);
			interaxialShelfItem.setText("Межосевой расчет");		   
			interaxialShelfItem.getBody().setLayout(new FillLayout());
			
			interaxialViewer = new InteraxialViewer(this, 
													interaxialShelfItem.getBody(), 
													SWT.VIRTUAL | SWT.SINGLE | 
													SWT.FULL_SELECTION);
			interaxialViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {					
					removeAction.setEnabled(!event.getSelection().isEmpty());
				}
			});	
		}
		{
			uprightInnerShelfItem = new PShelfItem(shelf,SWT.NONE);
			uprightInnerShelfItem.setText("Смешанный расчет");
		    
			uprightInnerShelfItem.getBody().setLayout(new FillLayout());
			
			uprightInnerViewer = new UprightInnerViewer(this, 
												uprightInnerShelfItem.getBody(), 
												SWT.VIRTUAL | SWT.SINGLE | 
												SWT.FULL_SELECTION);
			
			uprightInnerViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {					
					removeAction.setEnabled(!event.getSelection().isEmpty());
				}
			});
		}
	}

	@Override
	protected void createActions() {
		super.createActions();
		
		previewAction = new Action(){
			@Override
			public void run() {
				new OpenDetailPreviewExecutor(getDetail(), getCalculationsPreview())
				.run(getViewSite().getShell());
			}
		};
		previewAction.setImageDescriptor(ImageFactory.getDescriptor(
														Images.PREVIEW));
		
		addAction = new Action(){
			@Override
			public void run() {
				AbstractShrinkageViewer viewer = getViewer();
				ShrinkageCalculation calc = null;
				@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
				ServiceTracker tracker = new ServiceTracker(
										 		 Activator.getDefault().getContext(), 
												 MatterService.class.getName(),
												 null);
		         tracker.open();
		         MatterService  matterService = (MatterService) tracker.getService();
				
				if(isDiametralInnerActive()){
					calc = matterService.getNewInnerDiametralCalculation();
				} else if(isDiametralOuterActive()){
					calc = matterService.getNewOuterDiametralCalculation();
				} else if(isUprightOuterActive()){
					calc = matterService.getNewOuterUprightCalculation();
				} else if(isUprightInnerActive()){
					calc = matterService.getNewInnerUprightCalculation();
				} else if(isInteraxialViewer()){
					calc = matterService.getNewInteraxial();
				}
				
				detail.addTolerance(calc);
				calc.setMatter(detail.getMatter());
				calc.setDetail(detail);
				
				viewer.setCalculations(detail.getTolerances());
				viewer.refresh();	
				setEnabledMainToolBar(true);
			}
		};
		addAction.setImageDescriptor(ImageFactory.getDescriptor(Images.ADD));
		addAction.setToolTipText("Добавить расчет");
		
		removeAction = new Action(){
			@Override
			public void run() {
				AbstractShrinkageViewer viewer = getViewer();
				IStructuredSelection sel = (IStructuredSelection)viewer.getSelection();
				if(sel.isEmpty()){
					return;
				}
				List<ShrinkageCalculation> calcs = new ArrayList<ShrinkageCalculation>();
				for(Object o:sel.toList()){
					ShrinkageCalculation sct = (ShrinkageCalculation)o;
					for(ShrinkageCalculation sc:detail.getTolerances()){
						if(sct.getId().equals(sc.getId())){
							calcs.add(sc);
						}
					}
				}
				detail.getTolerances().removeAll(calcs);
				viewer.setCalculations(detail.getTolerances());
				viewer.refresh();
				setEnabledMainToolBar(true);
			}
		};
		removeAction.setImageDescriptor(ImageFactory.getDescriptor(Images.REMOVE));
		removeAction.setEnabled(false);
		removeAction.setToolTipText("Удалить расчет");
		
		saveAction = new Action(){
			@Override
			public void run() {
				new SaveCalculationsExecutor(detail).run(getViewSite().getShell());
				
				setEnabledMainToolBar(false);
			}
		};   
		saveAction.setImageDescriptor(ImageFactory.getDescriptor(Images.SAVE));
		saveAction.setToolTipText("Сохранить изменения");
		saveAction.setEnabled(false);
		
		cancelAction = new Action(){
			@Override
			public void run() {				
				new LoadingCalculationsExecutor(getInstance()).run(getSite().getShell());				
				
				setEnabledMainToolBar(false);
			}
		};
		cancelAction.setImageDescriptor(ImageFactory.getDescriptor(Images.CANCEL));
		cancelAction.setToolTipText("Отменить все изменения");
		cancelAction.setEnabled(false);
	}
	
	@Override
	public AbstractShrinkageViewer getViewer() {
		if(isDiametralInnerActive()){
			return diametralInnerViewer;
		} else if(isDiametralOuterActive()){
			return diametralOuterViewer;
		} else if(isUprightOuterActive()){
			return uprightOuterViewer;
		}else if(isUprightInnerActive()){
			return uprightInnerViewer;
		}else if(isInteraxialViewer()){
			return interaxialViewer;
		}
		return null;
	}
	
	@Override
	protected List<ShrinkageCalculation> getCalculationsPreview() {
		List<ShrinkageCalculation> list = new ArrayList<ShrinkageCalculation>();
		
		list.addAll(diametralOuterViewer.getCalculations());		
		list.addAll(diametralInnerViewer.getCalculations());
		list.addAll(interaxialViewer.getCalculations());
		list.addAll(uprightInnerViewer.getCalculations());
		list.addAll(uprightOuterViewer.getCalculations());
		
		return list;
	}
	
	@Override
	public void setEnabledToolbars(boolean enabled) {
		viewerToolBarManager.getControl().setEnabled(enabled);
		setEnabledMainToolBar(enabled);		
	}
	
	@Override
	public void setDetail(Detail det) {
		super.setDetail(det);
		diametralOuterViewer.setCalculations(det.getTolerances());
		diametralInnerViewer.setCalculations(det.getTolerances());
		uprightOuterViewer.setCalculations(det.getTolerances());
		uprightInnerViewer.setCalculations(det.getTolerances());
		interaxialViewer.setCalculations(det.getTolerances());
	}
	
	@Override
	public void setFocus() {
		// Set the focus
	}
	
	boolean isInteraxialViewer(){
		return shelf.getSelection().equals(interaxialShelfItem);
	}
	
	boolean isUprightInnerActive(){
		return shelf.getSelection().equals(uprightInnerShelfItem);
	}
	
	boolean isUprightOuterActive(){
		return shelf.getSelection().equals(uprightOuterShelfItem);
	}
	
	boolean isDiametralInnerActive(){
		return shelf.getSelection().equals(innerDiameterShelfItem);
	}
	
	boolean isDiametralOuterActive(){
		return shelf.getSelection().equals(outerDiameterShelfItem);
	}
	
	AbstratctMeasuringView getInstance(){
		return this;
	}
}
