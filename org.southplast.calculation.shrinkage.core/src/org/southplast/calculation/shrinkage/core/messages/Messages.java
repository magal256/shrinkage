package org.southplast.calculation.shrinkage.core.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "org.southplast.calculation.shrinkage.core.messages.messages"; //$NON-NLS-1$
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	public static String get(String key){
		try{
			return BUNDLE.getString(key);
		}catch(MissingResourceException e){
			return key;
		}		
	}
	
}
