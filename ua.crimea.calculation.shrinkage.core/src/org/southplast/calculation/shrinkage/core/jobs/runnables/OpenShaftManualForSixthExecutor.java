package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForSixthExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения валов номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 6";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual( "f6", "g6", "h6", "js6", "k6", "m6", "n6"));
		list.add(service.getTolerancesManual(  "j6","ef6", "fg6"));
		list.add(service.getTolerancesManual( "t6"));
		list.add(service.getTolerancesManual( "v6"));
		list.add(service.getTolerancesManual( "r6", "s6", "p6", "d6"));			
		list.add(service.getTolerancesManual( "e6","u6"));
		
		
		
		return list;
	}
}
