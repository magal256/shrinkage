package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import java.util.Map;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class PreviewLabelProvider  extends XViewerLabelProvider  {
	private int itemIndex = 1;
	
	public PreviewLabelProvider(XViewer viewer) {
		super(viewer);
	}

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object arg0, XViewerColumn arg1, int arg2)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getColumnText(Object obj, XViewerColumn col, int i)
													throws Exception {
		Map<Integer, String> map = (Map<Integer, String>) obj;
		if(i == 0 && map.get(0) == null && map.get(1) != null){
			return  String.valueOf(itemIndex++);
		}
		return map.get(i);
	}
	
	@Override
	public Color getBackground(Object element, int columnIndex) {
		@SuppressWarnings("unchecked")
		Map<Integer, String> map = (Map<Integer, String>) element;
		
		if(map.get(0) != null && map.get(columnIndex) != null){
			return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		}
		return super.getBackground(element, columnIndex);
	}
	
	@Override
	public Color getForeground(Object element, int columnIndex) {
		@SuppressWarnings("unchecked")
		Map<Integer, String> map = (Map<Integer, String>) element;
		
		if(map.get(0) != null && map.get(columnIndex) != null) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
		}
		
		return super.getForeground(element, columnIndex);
	}

}
