package org.southplast.calculation.shrinkage.core.handlers.states;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.southplast.calculation.shrinkage.core.utils.ViewUtils;
import org.southplast.calculation.shrinkage.core.views.MattersView;


public class EditMatterCommandState extends AbstractSourceProvider {
	public final static String MY_STATE = "org.southplast.calculation.shrinkage.core.handlers.states.acive";
	public final static String ENABLED = "ENABLED";
	public final static String DISENABLED = "DISENABLED";
	@Override
	public void dispose() {
	}

	// We could return several values but for this example one value is sufficient
	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { MY_STATE };
	}
	
	@Override
	public Map<String, String> getCurrentState() {
		
		Map<String, String> map = new HashMap<String, String>();
		try{
			MattersView view = (MattersView) ViewUtils.getView(MattersView.ID);
			String value = view.getMatter() != null ? ENABLED : DISENABLED;
			map.put(MY_STATE, value);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return map;
	}

	

	// This method can be used from other commands to change the state
	// Most likely you would use a setter to define directly the state and not use this toogle method
	// But hey, this works well for my example
//	public void toogleEnabled() {
//		enabled = !enabled ;
//		String value = enabled ? ENABLED : DISENABLED;
//		fireSourceChanged(ISources.WORKBENCH, MY_STATE, value);
//	}

}