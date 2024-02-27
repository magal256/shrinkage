package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForSeventhExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения валов номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 7";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual(  "e7","f7", "h7", "js7", "k7", "n7", "ef7"));
		list.add(service.getTolerancesManual(  "m7"));
		list.add(service.getTolerancesManual(  "d7", "g7", "s7", "u7"));
		list.add(service.getTolerancesManual(  "v7"));
		list.add(service.getTolerancesManual(  "j7", "p7", "r7", "x7", "z7"));
		
		list.add(service.getTolerancesManual(  "t7"));
		
		
		return list;
	}
}
