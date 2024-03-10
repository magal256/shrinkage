package org.southplast.calculation.shrinkage.core.forms;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.controls.LabelListViewer;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.domain.ToleranceType;
import org.southplast.calculation.shrinkage.core.domain.WorkmanshipClass;
import org.southplast.calculation.shrinkage.core.jobs.ServiceJob;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingToleransesByNameExecutor;


public class TolerancesForm extends FilialWindow {	
	private Action openingAction;
	private Action shaftAction;
		
	private Label sizeLabel;
	private Label toleranceUp;
	private Label toleranceDown;
	private LabelListViewer preferList;
	private LabelListViewer mainList;
	private LabelListViewer additionalList;
	
	private List<Tolerance> workmanshipsForOpening;
	private List<Tolerance> workmanshipForShaft;
	private ToleranceType type;
	private Tolerance tolerance;
	private Tolerance targetTolerance;
	private BigDecimal size;
	
	/**
	 * Create the application window.
	 * @param wsForOpenning TODO
	 * @param wsForShaft TODO
	 * @param size TODO
	 * @param group TODO
	 */
	public TolerancesForm(Shell shell, List<Tolerance> wsForOpenning, 
									   	List<Tolerance> wsForShaft, 
									   	Tolerance current, 
									   	BigDecimal size) {
		super(shell);
		
		this.type = current.getType();
		this.size = size;
		this.tolerance = current;
		this.targetTolerance = current;
		this.workmanshipForShaft = wsForShaft;
		this.workmanshipsForOpening = wsForOpenning;
		  		
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();	
		setShellStyle(SWT.RESIZE | SWT.TITLE);					
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label label = new Label(container, SWT.NONE);
			label.setText("Предпочтительные");
		}
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}			
		{
			preferList = new LabelListViewer(this, container, SWT.BORDER, 30 );
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			preferList.setLayoutData(gridData);		
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.NONE);
			sepLbl.setLayoutData(gridData);
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label label = new Label(container, SWT.NONE);
			label.setText("Основные");
		}
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}				
		{
			mainList = new LabelListViewer(this, container, SWT.BORDER, 70);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			mainList.setLayoutData(gridData);	
		}			
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.NONE);
			sepLbl.setLayoutData(gridData);
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label label = new Label(container, SWT.NONE);
			label.setText("Дополнительные");
		}
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}				
		{
			additionalList = new LabelListViewer(this, container, SWT.BORDER, 55);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			additionalList.setLayoutData(gridData);	
		}		
		{
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 2;
			Label sepLbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
			sepLbl.setLayoutData(gridData);
		}
		{
			Composite comp = new Composite(container, SWT.NONE);
			comp.setLayout(new GridLayout(2, false));
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			comp.setLayoutData(gridData);
			
			Group group = new Group(comp, SWT.NONE);
			group.setText("Значение");
			group.setLayout(new GridLayout(1, false));
			group.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
			
			sizeLabel = new Label(group, SWT.FLAT);			
			sizeLabel.setText(format(size));
			
			Group group2 = new Group(comp, SWT.NONE);
			group2.setText("Отклонения");
			group2.setLayout(new GridLayout(2, false));
			group2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Label lbl1 = new Label(group2, SWT.NONE);
			lbl1.setText("Верхнее:");
			
			toleranceUp = new Label(group2, SWT.NONE);			
			toleranceUp.setText(format(tolerance.getUp()));
			
			Label lbl2 = new Label(group2, SWT.NONE);
			lbl2.setText("Нижнее:");
			
			toleranceDown = new Label(group2, SWT.NONE);
			toleranceDown.setText(format(tolerance.getDown()));
		}		
		{
			Button button = new Button(container, SWT.PUSH);
			button.setText("Выбрать");
			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			button.addSelectionListener(new SelectionListener() {				
				@Override
				public void widgetSelected(SelectionEvent e) {
					targetTolerance.setId(tolerance.getId());
					targetTolerance.setName(tolerance.getName());
					targetTolerance.setDown(tolerance.getDown());
					targetTolerance.setDownSize(tolerance.getDownSize());
					targetTolerance.setUp(tolerance.getUp());
					targetTolerance.setUpSize(tolerance.getUpSize());
					targetTolerance.setGroup(tolerance.getGroup());
					targetTolerance.setType(tolerance.getType());
					
					 new ServiceJob(getShell(), 
							 		new LoadingToleransesByNameExecutor(targetTolerance))
					 .schedule();
					
					
					close();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					
				}
			});
		}
		{
			Button button = new Button(container, SWT.PUSH);
			button.setText("Отменить");
			button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			button.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					close();	
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
									
				}
			});			
		}						
		
		defineWorkmanships(ToleranceType.OPENING.equals(type)?
									   workmanshipsForOpening:
									   workmanshipForShaft);
		additionalList.setSelection(tolerance.getName());
		preferList.setSelection(tolerance.getName());
		mainList.setSelection(tolerance.getName());
		
		getShell().pack();
		
		return container;
	}
	
	/**
	 * Create the actions.
	 */
	private void createActions() {
		shaftAction = new Action(){
			@Override
			public void run() {
				openingAction.setChecked(false);
				setChecked(true);
				defineWorkmanships(workmanshipForShaft);
			}
		};
		shaftAction.setText("Вал");		
		shaftAction.setChecked(ToleranceType.SHAFT.equals(type));
		
		openingAction = new Action(){
			@Override
			public void run() {
				shaftAction.setChecked(false);
				setChecked(true);
				defineWorkmanships(workmanshipsForOpening);
			}
		};
		openingAction.setText("Отверстие");		
		openingAction.setChecked(ToleranceType.OPENING.equals(type));	
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {					
		ActionContributionItem shaftItem = new ActionContributionItem(
														  shaftAction);
		shaftItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);		
		
		ActionContributionItem openningItem = new ActionContributionItem(
														  openingAction);
		openningItem.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		
		ToolBarManager toolBarManager = new ToolBarManager(style);
		toolBarManager.add(shaftAction);
		toolBarManager.add(openningItem);
				
		return toolBarManager;
	}	

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Выбор квалитета");		
		newShell.setMinimumSize(450, 600);
	}

	/**
	 * Return the initial size of the window.
	 */
//	@Override
//	protected Point getInitialSize() {
//		Point parentSize = getParentShell().getSize();
//		return new Point(parentSize.x/3, parentSize.y/2);
//	}				
	
	private void defineWorkmanships(List<Tolerance> ws){
		List<Tolerance> wsp = new ArrayList<Tolerance>();
		List<Tolerance> wsm = new ArrayList<Tolerance>();
		List<Tolerance> wsa = new ArrayList<Tolerance>();
		
		for(Tolerance w: ws){
			if(WorkmanshipClass.MAIN.equals(w.getGroup())){
				wsm.add(w);
			}
			if(WorkmanshipClass.PREFERABLE.equals(w.getGroup())){
				wsp.add(w);
			}
			if(WorkmanshipClass.ADDITIONAL.equals(w.getGroup())){
				wsa.add(w);
			}
		}
		
		preferList.setInput(wsp);
		additionalList.setInput(wsa);
		mainList.setInput(wsm);
	}
	
	public void handleSelectWorkmanship(Tolerance element) {		
		tolerance = element;
		
		mainList.deselectItem();
		additionalList.deselectItem();
		preferList.deselectItem();
		toleranceUp.setText(format(element.getUp()));
		toleranceUp.redraw();
		toleranceUp.pack(true);
		toleranceDown.setText(format(element.getDown()));
		toleranceDown.redraw();
		toleranceDown.pack(true);
	}
	
	public Tolerance getWorkmanship() {
		return tolerance;
	}
}
