package org.southplast.calculation.shrinkage.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListUtils {
	public static <T> List<T> filter(Collection<T> l, Predicate<T> predicate) {
		List<T> list = new ArrayList<T>();
		if(l != null){
			Iterator<T> it= l.iterator();
		    while(it.hasNext()) {
		    	T item = it.next();
		      if(predicate.apply(item)) {
		        list.add(item);   
		      }	      
		    }	
		}
	      
	    return list;
	}
	
	
}
