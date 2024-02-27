package org.southplast.calculation.shrinkage.core.viewers.providers.content;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PreviewContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		
	}

	@Override
	public Object[] getChildren(Object arg0) {
		return  new Object[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object obj) {
		return ((List<Map<Integer, String>>) obj).toArray();
	}

	@Override
	public Object getParent(Object arg0) {
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		return false;
	}

}
