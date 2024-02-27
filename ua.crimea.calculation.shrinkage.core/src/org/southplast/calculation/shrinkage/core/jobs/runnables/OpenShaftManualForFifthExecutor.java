package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForFifthExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения валов номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 5";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(service.getTolerancesManual("g5", "h5", "js5", "k5", "m5", "n5"));
		list.add(service.getTolerancesManual( "t5"));		
		list.add(service.getTolerancesManual( "s5", "e5", "ef5", "f5", "fg5", "j5", "u5"));
		list.add(service.getTolerancesManual( "p5", "r5"));
		
		return list;
	}
}
