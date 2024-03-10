package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForEighthExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения валов номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 8";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(service.getTolerancesManual( "z8", "ef8",  "x8", "za8", "zb8", "zc8"));
		list.add(service.getTolerancesManual("js8", "u8", "c8"));
		list.add(service.getTolerancesManual(  "d8", "e8", "f8", "h8"));
		
		
		
		return list;
	}
}
