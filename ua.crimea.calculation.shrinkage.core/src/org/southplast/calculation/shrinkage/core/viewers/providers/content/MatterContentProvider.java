/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package org.southplast.calculation.shrinkage.core.viewers.providers.content;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;


/**
 * Content provider for example XViewer implementation
 * 
 * @author Donald G. Dunne
 */
public class MatterContentProvider implements ITreeContentProvider {

   protected Collection<Matter> rootSet = new HashSet<Matter>();
   private final static Object[] EMPTY_ARRAY = new Object[0];

   public MatterContentProvider() {
      super();
   }

   @Override
   @SuppressWarnings("rawtypes")
   public Object[] getChildren(Object parentElement) {
      if (parentElement instanceof Object[]) {
         return (Object[]) parentElement;
      }
      if (parentElement instanceof Collection) {
         return ((Collection) parentElement).toArray();
      }
      if (parentElement instanceof MatterGroup) {
         return ((MatterGroup) parentElement).getMatters().toArray();
      }
      if(parentElement instanceof Matter){
    	  Matter matter = ((Matter)parentElement);
    	  return matter != null?matter.getDetails().toArray():EMPTY_ARRAY;
      }
      
      return EMPTY_ARRAY;
   }

   @Override
   public Object getParent(Object element) {
      return null;
   }

   @Override
   public boolean hasChildren(Object element) {
      if (element instanceof MatterGroup) {    	  
    	  MatterGroup mg = (MatterGroup) element;         
    	  return mg.getMatters() != null?mg.getMatters().size() > 0:false;
      }
      if(element instanceof Matter){
    	  Matter matter = ((Matter)element);
    	  return matter.getDetails()!=null?matter.getDetails().size() > 0:false;
      }
      return false;
   }

   @Override
   public Object[] getElements(Object inputElement) {
      if (inputElement instanceof String) {
         return new Object[] {inputElement};
      }
      return getChildren(inputElement);
   }

   @Override
   public void dispose() {
      // do nothing
   }

   @Override
   public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
      // do nothing
   }

   public Collection<Matter> getRootSet() {
      return rootSet;
   }

}
