package org.southplast.calculation.shrinkage.core.forms;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.middle;
import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Shrinkage;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;
import org.southplast.calculation.shrinkage.core.jobs.runnables.ExportToCsvExecutor;
import org.southplast.calculation.shrinkage.core.utils.InnerDiametralPredicate;
import org.southplast.calculation.shrinkage.core.utils.InnerUprightPredicate;
import org.southplast.calculation.shrinkage.core.utils.InteraxialPredicate;
import org.southplast.calculation.shrinkage.core.utils.MatchingPredicate;
import org.southplast.calculation.shrinkage.core.utils.OuterDiametralPredicate;
import org.southplast.calculation.shrinkage.core.utils.OuterUprightPredicate;
import org.southplast.calculation.shrinkage.core.viewers.DiametralInnerViewer;
import org.southplast.calculation.shrinkage.core.viewers.DiametralOuterViewer;
import org.southplast.calculation.shrinkage.core.viewers.InteraxialViewer;
import org.southplast.calculation.shrinkage.core.viewers.MatchingShrinkageViewer;
import org.southplast.calculation.shrinkage.core.viewers.UprightInnerViewer;
import org.southplast.calculation.shrinkage.core.viewers.UprightOuterViewer;


public class CalculationPreviewForm extends FilialWindow {
	private List<ShrinkageCalculation> previews;
	private Detail detail;
	
	public CalculationPreviewForm(Shell parentShell, Detail detail, 
								  List<ShrinkageCalculation> previews) {
		super(parentShell);
		
		this.previews = previews;
		this.detail = detail;
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
	    GridLayout layout = new GridLayout(1, false);
	    child.setLayout(layout);
	    child.setLayoutData(new GridData(GridData.FILL_BOTH));
	    
	    Shrinkage shr = detail.getMatter().getLongitudinalShrinkage();
	    String str = new String(detail.getMatter() != null? 
	    						detail.getMatter().getCommercialGrade().trim():
	    						"");
	    
	    Composite detailComp = new Composite(child, SWT.NONE);
	    detailComp.setLayout(new GridLayout(6, false));
	    detailComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    
	    new Label(detailComp, SWT.NONE).setText("Материал:");
	    Label matterLbl = new Label(detailComp, SWT.NONE);
	    matterLbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    matterLbl.setText(str + " ");	
	    
	    new Label(detailComp, SWT.NONE).setText("Усадка:");
	    Label rangeLabel = new Label(detailComp, SWT.NONE); 
	    rangeLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));						
		rangeLabel.setText(format(shr.getMinimum()) + " - " + 
						   format(shr.getMaximum()) + " (среднее- " + 
						   format(middle(shr.getMinimum(), shr.getMaximum())) + 
						   ")");
		
		new Label(detailComp, SWT.NONE).setText("Деталь:");
		Label detailLabel = new Label(detailComp, SWT.NONE);
		detailLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailLabel.setText((detail!=null?detail.getName():""));		
	    
	    List<ShrinkageCalculation> diCalcs = filter(previews, new InnerDiametralPredicate());
	    List<ShrinkageCalculation> doCalcs = filter(previews, new OuterDiametralPredicate());
	    List<ShrinkageCalculation> iCalcs = filter(previews, new InteraxialPredicate());
	    List<ShrinkageCalculation> uiCalcs = filter(previews, new InnerUprightPredicate());
	    List<ShrinkageCalculation> uoCalcs = filter(previews, new OuterUprightPredicate());
	    List<ShrinkageCalculation> mCalcs = filter(previews, new MatchingPredicate());
	    
	    if(mCalcs != null && !mCalcs.isEmpty()){
	    	new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Расчеты методом подбора:");
	    	MatchingShrinkageViewer viewer = new MatchingShrinkageViewer(child, 
												SWT.VIRTUAL| SWT.NO_SCROLL);
			viewer.setCalculations(mCalcs);
			viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    }
	    
	    if(doCalcs != null && !doCalcs.isEmpty()) {
	    	new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Диаметральный (внешний) расчет:");
			DiametralOuterViewer viewer = new DiametralOuterViewer(child, 
												SWT.VIRTUAL| SWT.NO_SCROLL);
			viewer.setCalculations(doCalcs);
			viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
	    if(uoCalcs != null && !uoCalcs.isEmpty()) {
	    	new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Высотный расчет:");
			UprightOuterViewer viewer = new UprightOuterViewer(child, SWT.VIRTUAL| 
																	  SWT.NO_SCROLL);
			viewer.setCalculations(uoCalcs);
			viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		if(diCalcs != null && !diCalcs.isEmpty()) {
			new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Диаметральный (внутренний) расчет:");
			DiametralInnerViewer viewer = new DiametralInnerViewer(child, 
											SWT.VIRTUAL | SWT.SCROLL_LOCK);
		    viewer.setCalculations(diCalcs);
		    viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		
		if(iCalcs != null && !iCalcs.isEmpty()) {
			new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Межосевой расчет:");
			InteraxialViewer viewer = new InteraxialViewer(child, SWT.VIRTUAL| 
																  SWT.NO_SCROLL);
			viewer.setCalculations(iCalcs);
			viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}
		if(uiCalcs != null && !uiCalcs.isEmpty()) {
			new Label(child, SWT.NONE);
	    	new Label(child, SWT.NONE).setText("Смешанный расчет:");
			UprightInnerViewer viewer = new UprightInnerViewer(child, SWT.VIRTUAL| 
																	  SWT.NO_SCROLL);
			viewer.setCalculations(uiCalcs);
			viewer.getTree().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
		
	    Button button = new Button(parent, SWT.PUSH);
	    button.setText("Сохранить в CSV файл");
	    button.setImage(ImageFactory.getImage(Images.EXPORT));
	    button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END | 
	    								  GridData.FILL_HORIZONTAL));
	    button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ExportToCsvExecutor(getShell(), detail, previews)
				.run(getShell());				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		return parent;
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		
		Point size = getParentShell().getSize();
		
		shell.setSize(size.x - 100, size.y-200);		
		shell.setText(detail!=null?detail.getName():"Расчеты по детали");		
	}		
}
