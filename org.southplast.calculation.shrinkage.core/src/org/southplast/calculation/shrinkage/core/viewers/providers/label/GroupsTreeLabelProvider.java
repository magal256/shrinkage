package org.southplast.calculation.shrinkage.core.viewers.providers.label;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.images.ImageFactory;
import org.southplast.calculation.shrinkage.core.images.Images;


public class GroupsTreeLabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object element) {
		MatterGroup group = (MatterGroup) element;
		if(group.isSelected()){
			return ImageFactory.getImage(Images.SELECTED_GROUP);
		}
		else{
			return ImageFactory.getImage(Images.GROUP);
		}		
	}

	@Override
	public String getText(Object element) {
		MatterGroup group = (MatterGroup) element;
		return group.getName() + " "+ (group.getMattersCount() != null 
									   && group.getMattersCount() != 0?
									   "(" + group.getMattersCount() + ")":"");
	}
	
}
