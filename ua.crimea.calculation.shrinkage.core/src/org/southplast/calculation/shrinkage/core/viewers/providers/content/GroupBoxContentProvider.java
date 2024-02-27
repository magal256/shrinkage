package org.southplast.calculation.shrinkage.core.viewers.providers.content;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;


public class GroupBoxContentProvider implements ITreeContentProvider {
	private final static Object[] EMPTY_ARRAY = new Object[0];
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {		 	
		return ((List<MatterGroup>)inputElement).toArray();	     
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}
}
