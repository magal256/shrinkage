package org.southplast.calculation.shrinkage.core.viewers.providers.content;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;


public class ShrinkageContentProvider  implements ITreeContentProvider {

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		return ((List<ShrinkageCalculation>)inputElement).toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return   new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
