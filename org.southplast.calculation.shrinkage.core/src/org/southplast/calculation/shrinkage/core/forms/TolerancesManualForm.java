package org.southplast.calculation.shrinkage.core.forms;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.formatInt;

import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridColumnGroup;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.domain.WorkmanshipClass;


public class TolerancesManualForm extends FilialWindow {
	private List<Map<String, Object>> manuals;
	private Font preferableFont, additionalFont;
	private String title;
	
	public static TolerancesManualForm getInstance(Shell parentShell, List<Map<String, Object>> manuals, String title) {
	    return new TolerancesManualForm(parentShell, manuals, title);
	  }
	
	private TolerancesManualForm(Shell parentShell, List<Map<String, Object>> manuals, String title) {
		super(parentShell);
		
		this.manuals = manuals;
		this.title = title;
		this.preferableFont = new Font(Display.getCurrent(), "Times New Roman", 10, SWT.BOLD);
		this.additionalFont = new Font(Display.getCurrent(), "Times New Roman", 10, SWT.ITALIC);
		
	}
	
	@Override
	protected Control createContents(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		final ScrolledComposite sc = new ScrolledComposite(parent, SWT.V_SCROLL | 
															 SWT.H_SCROLL);		
		sc.setLayoutData(new GridData(GridData.FILL_BOTH));	
		sc.setLayout(new GridLayout(1, false));
		// Create a child composite to hold the controls
	    final Composite child = new Composite(sc, SWT.NONE);
	    GridLayout layout = new GridLayout(2, false);
	    child.setLayout(layout);
	    child.setLayoutData(new GridData(GridData.FILL_BOTH));
	    
	    for(Map<String, Object> manual:manuals){
	    	createTable(child, manual);
	    }
	    
	    child.addListener(SWT.Resize, new Listener() {
		    int width = -1;
		    public void handleEvent(Event e) {
		      int newWidth = child.getSize().x;
		      if (newWidth != width) {
		    	  sc.setMinHeight(child.computeSize(newWidth, SWT.DEFAULT).y);
		        width = newWidth;
		      }
		    }
		  });
	    
	    sc.setContent(child);
	    sc.setMinHeight(child.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y);
	    sc.setExpandHorizontal(true);
	    sc.setExpandVertical(true);		
		return parent;
	}
	
	@SuppressWarnings("unchecked")
	public void createTable(Composite parent, Map<String, Object> map){
		Map<String, List<Tolerance>> sizes = (Map<String, List<Tolerance>>) map.get("sizes");
		String[] names = (String[])map.get("names");
		Grid grid = new Grid(parent, SWT.BORDER | SWT.H_SCROLL | SWT.VIRTUAL);
		grid.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
	    grid.setHeaderVisible(true);
//	    grid.setFooterVisible(true);
	    grid.setLinesVisible(true);
	    grid.setAutoWidth(true);
	    grid.setAutoHeight(true);
	    
	    GridColumn column = new GridColumn(grid,SWT.LEFT);
	    column.setText("Номинальные \n размеры, мм");
	    column.setWidth(150);
	    column.setWordWrap(true);
	    GridColumnGroup columnGroup = new GridColumnGroup(grid, SWT.RIGHT);
	    columnGroup.setText("Поле допуска");
	    
	    GridColumn[] columns = new GridColumn[500];	 
	    GridItem[] items = null;
	    int i = 0;
	   
	    for(String name:names){	    	
	    	columns[i] = new GridColumn(columnGroup,SWT.CENTER);	    
	    	columns[i].setText(name);
	    	columns[i].setWidth(80);
//	    	columns[i].setWordWrap(true);
	    	
	    	if(items == null){
	    		items = new GridItem[500];
	    	}
	    	int j = 0;
	    	
	    	for(Tolerance tol:sizes.get(name)) {
	    		if(j == 0){
	    			columns[i].setHeaderFont(getFont(tol.getGroup()));
	    		}
	    		
	    		if(items[j] == null){
	    			items[j] = new GridItem(grid, SWT.CENTER);
	    		}
	    		if(i == 0){
	    			items[j].setText((tol.getDownSize().intValue() == 1?"От":"Св. ") + 
	    							 formatInt(tol.getDownSize()) + " до " + 
	    							 formatInt(tol.getUpSize()));
	    		}
    			String down =(tol.getDown().intValue() == tol.getDown().floatValue()? formatInt(tol.getDown()):format(tol.getDown()));
    			String up = (tol.getUp().intValue() == tol.getUp().floatValue()? formatInt(tol.getUp()):format(tol.getUp()));
    			String fields =  (up.contains("-") || up.equals("0")?up:"+"+up)
    			+ "\n "+ (down.contains("-")|| down.equals("0")?down:"+"+down);
    			
    			items[j].setText(i+1,  fields.trim().isEmpty()?"-":fields);
	    		j++;
	    	}
	    	i++;
	    }	
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		
		Point size = getParentShell().getSize();
		
		shell.setSize(size.x - 100, size.y-200);	
		shell.setText(title);
	}			
	
	@Override
	public void run() {
//		getParentShell().setCursor(ViewUtils.CURSOR_WAIT);
//		setBlockOnOpen(true);
		open();
//		getParentShell().setEnabled(true);
//		getParentShell().setCursor(null);
	}
	
	@Override
	public boolean close() {
		preferableFont.dispose();
		additionalFont.dispose();
		return super.close();
	}
	
	private Font getFont(WorkmanshipClass wClass){
		if(WorkmanshipClass.ADDITIONAL.equals(wClass)){
			return additionalFont;
		} else if(WorkmanshipClass.PREFERABLE.equals(wClass)){
			return preferableFont;
		} else {
			return null;
		}
	}
}
