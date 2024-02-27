package org.southplast.calculation.shrinkage.core.viewers;

import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.swt.widgets.Composite;
import org.southplast.calculation.shrinkage.core.viewers.factories.PreviewViewerFactory;
import org.southplast.calculation.shrinkage.core.viewers.providers.content.PreviewContentProvider;
import org.southplast.calculation.shrinkage.core.viewers.providers.label.PreviewLabelProvider;


public class PreviewViewer extends XViewer {

	public PreviewViewer(Composite parent, int style) {
		super(parent, style, new PreviewViewerFactory());
		
		setContentProvider(new PreviewContentProvider());
		setLabelProvider(new PreviewLabelProvider(this));
	}

}
