package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForEighthExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 8";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual("D8","E8", "H8", "JS8", "J8", "N8", "K8", "F8"));
		list.add(service.getTolerancesManual("M8"));
		list.add(service.getTolerancesManual(  "C8","EF8",  "P8", "R8", "Z8"));
		list.add(service.getTolerancesManual("U8"));
		
		return list;
	}
}
