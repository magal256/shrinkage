package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForNinthExecutor extends AbstractOpenToleranceManualExecutor  {
	@Override
	public String getTitle() {
		return "Предельные отклонения валов номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 9";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual( "a9", "b9",  "c9", "js9", "d9"));
		list.add(service.getTolerancesManual( "e9", "f9", "h9"));
		
		
		return list;
	}
}
