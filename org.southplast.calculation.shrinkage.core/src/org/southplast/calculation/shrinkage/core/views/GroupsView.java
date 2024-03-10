package org.southplast.calculation.shrinkage.core.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.jobs.runnables.LoadingMattersExecutor;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.GroupBoxContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.GroupsTreeLabelProvider;


public class GroupsView extends ViewPart {
	public static final String ID = "org.southplast.calculation.shrinkage.core.views.GroupsView";
	private TreeViewer groupsViewer;

	private List<MatterGroup> groups = new ArrayList<MatterGroup>();
	
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));		
		
		groupsViewer = new TreeViewer(parent);
		groupsViewer.setContentProvider(new GroupBoxContentProvider());
		groupsViewer.setLabelProvider(new GroupsTreeLabelProvider());
		groupsViewer.setInput(groups);
		groupsViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				boolean groupSelected = !groupsViewer.getSelection().isEmpty();
				ViewUtils.setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.AddMaterialCommand", groupSelected);
				ViewUtils.setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.EditGroupCommand", groupSelected);
				ViewUtils.setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.DeleteGroupCommand", groupSelected);
				ViewUtils.setCommandEnabled("org.southplast.calculation.shrinkage.core.commands.LoadMaterialsForGroupCommand", groupSelected);
			}
		});
		groupsViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		groupsViewer.getTree().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				MatterGroup group = getSelectedGroup();
				if(group == null){
					return;
				}
				deselectGroups();
				
				group.setSelected(true);
				
				groupsViewer.refresh();
				
				 new LoadingMattersExecutor().run(getSite().getShell());
				
				
			}
		});
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(groupsViewer.getTree());
		groupsViewer.getTree().setMenu(menu);
		
		getSite().setSelectionProvider(groupsViewer);
		getSite().registerContextMenu(menuManager, groupsViewer);	
		
		
	}
	
	@Override
	public void setFocus() {

	}
	
	public MatterGroup getSelectedGroup(){
		IStructuredSelection sel = (IStructuredSelection) groupsViewer
														  .getSelection();
		return (MatterGroup)sel.getFirstElement();
	}
	
	public void setGroups(List<MatterGroup> groups){
		this.groups.clear();
		
		if(groups != null){
			this.groups.addAll(groups);
		}
		
		groupsViewer.refresh(true);
	}		
	
	private void deselectGroups(){
		for(MatterGroup g:groups){
			g.setSelected(false);
		}
	}
	
	public List<MatterGroup> getGroups() {
		return groups;
	}
}
