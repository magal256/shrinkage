package org.southplast.calculation.shrinkage.core.views;

import static org.southplast.calculation.shrinkage.core.utils.ViewUtils.setCommandEnabled;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.viewers.MatterViewer;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.MatterContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.MatterXViewerLabelProvider;


public class MattersView extends ViewPart {
	public static final String ID = "org.southplast.calculation.shrinkage.core.views.MattersView";
	private MatterViewer viewer;
	
	private List<Matter> matters = new ArrayList<Matter>();
	private MatterGroup group = new MatterGroup();
	
	public MattersView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer = new MatterViewer(comp, SWT.MULTI |  SWT.FULL_SELECTION |
																	SWT.BORDER | 
																	SWT.SINGLE);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));	      
		viewer.setContentProvider(new MatterContentProvider());	      
		viewer.setLabelProvider(new MatterXViewerLabelProvider(viewer));  
		viewer.setInput(matters);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
				boolean matterSelected = !sel.isEmpty() && 
									   sel.getFirstElement() instanceof Matter;
				boolean detailSelected = !sel.isEmpty() &&
									  sel.getFirstElement() instanceof Detail;
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.EditMatterCommand", matterSelected);
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.DeleteMatterCommand", matterSelected);
				
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.AddDetailCommand", matterSelected);
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.EditDetailCommand", detailSelected);
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.DeleteDetailCommand", detailSelected);
				setCommandEnabled("org.southplast.calculation.shrinkage.core.command.OpenPreviewDetailCommand", detailSelected);
				
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.CalculationShrinkage", detailSelected);
				setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.MachingShrinkage", detailSelected);
			}
		});
		
		MenuManager menuManager = new MenuManager();		
		Menu menu = menuManager.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		
		getSite().registerContextMenu(menuManager, viewer);	
		
		getSite().setSelectionProvider(viewer);
		getSite().registerContextMenu(menuManager, viewer);	
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public Matter getMatter(){
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		
		if(!sel.isEmpty() && sel.getFirstElement() instanceof Matter) {
			return (Matter)sel.getFirstElement();
		}
		
		return null;
	}
	
	public Detail getDetail(){
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		
		if(!sel.isEmpty() && sel.getFirstElement() instanceof Detail) {
			return (Detail)sel.getFirstElement();
		}
		
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Matter> getMatters() {
		return (List<Matter>)viewer.getInput();
	}
	
	public void setMatters(List<Matter> matters){
		this.matters.clear();
		
		if(matters != null){
			viewer.setInput(matters);
		}		
		
		viewer.refresh(true);
	}
	
	public void setGroup(MatterGroup group) {
		this.group = group;
	}
	public MatterGroup getGroup() {
		return group;
	}
}
