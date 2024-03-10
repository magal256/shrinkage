package org.southplast.calculation.shrinkage.core.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerControlFactory;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerConverter;
import org.eclipse.nebula.widgets.xviewer.edit.XViewerEditAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.util.tracker.ServiceTracker;
import org.southplast.calculation.shrinkage.core.Activator;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.SaveCalculationsExecutor;
import org.southplast.calculation.shrinkage.core.management.MatterService;
import org.southplast.calculation.shrinkage.core.viewers.AbstractShrinkageViewer;
import org.southplast.calculation.shrinkage.core.viewers.MatchingShrinkageViewer;
import org.southplast.calculation.shrinkage.core.viewers.converters.ShrinkageConverter;
import org.southplast.calculation.shrinkage.core.viewers.factories.ViewerControlFactory;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.ShrinkageContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.MatchingLabelProvider;


public class MatchingView extends AbstratctMeasuringView {	
	public static final String ID = "org.southplast.calculation.shrinkage.core.views.MatchingView"; //$NON-NLS-1$
	
		
	private MatchingShrinkageViewer viewer;		

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
//		set layout and head controls
		super.createPartControl(parent);
		
//		set special controls				
		GridData viewerLayData = new GridData(GridData.FILL_BOTH);
		viewerLayData.horizontalSpan = MAIN_GRID_COLUMNS_COUNT;
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayoutData(viewerLayData);
		comp.setLayout(new GridLayout(1, true));
		viewer = new MatchingShrinkageViewer(this, comp, SWT.FULL_SELECTION | 
													SWT.VIRTUAL | SWT.SINGLE );
		
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setLabelProvider(new MatchingLabelProvider(viewer));
		viewer.setContentProvider(new ShrinkageContentProvider());		
		viewer.getControl().setFocus(); 
		XViewerControlFactory cFactory = new ViewerControlFactory(this);	      
		XViewerConverter converter = new ShrinkageConverter(viewer);	     
		viewer.setXViewerEditAdapter(new XViewerEditAdapter(cFactory, converter));
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {					
				removeAction.setEnabled(!event.getSelection().isEmpty());
			}
		});
	}
	
	@Override
	protected void createActions(){
		super.createActions();
		
		removeAction = new Action(){
			@Override
			public void run() {
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
		cancelAction.setToolTipText("Отменить изменения");
		cancelAction.setEnabled(false);
		
		addAction = new Action(){
			@Override
			public void run() {
				@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
				ServiceTracker tracker = new ServiceTracker(
										 		 Activator.getDefault().getContext(), 
												 MatterService.class.getName(),
												 null);
		         tracker.open();
		         MatterService matterService = (MatterService) tracker.getService();
				ShrinkageCalculation calc = matterService.getNewMachingCalculation();
				if(detail.getTolerances() == null){
					detail.setTolerances(new ArrayList<ShrinkageCalculation>());
				}
				calc.setMatter(detail.getMatter());
				calc.setDetail(detail);
				detail.getTolerances().add(calc);
				viewer.setCalculations(detail.getTolerances());
				viewer.refresh();	
				setEnabledMainToolBar(true);
			}
		};
		addAction.setImageDescriptor(ImageFactory.getDescriptor(Images.ADD));
		addAction.setToolTipText("Добавить расчет");		
	}
	

	@Override
	public void setFocus() {
		// Set the focus
	}
	
	public void setEnabledToolbars(boolean enabled){
		viewerToolBarManager.getControl().setEnabled(enabled);
		setEnabledMainToolBar(enabled);
	}
	
	
	@Override
	public void setDetail(Detail det) {		
		super.setDetail(det);		
		viewer.setCalculations(detail != null? detail.getTolerances():null);				
	}
	@Override
	protected List<ShrinkageCalculation> getCalculationsPreview() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AbstractShrinkageViewer getViewer() {
		
		return viewer;
	}
	
	AbstratctMeasuringView getInstance(){
		return this;
	}
}
