package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForNinthExecutor extends AbstractOpenToleranceManualExecutor  {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 9";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	
		list.add(service.getTolerancesManual("N9", "P9", "CD9"));
		list.add(service.getTolerancesManual("D9", "E9", "F9", "H9"));
		list.add(service.getTolerancesManual("A9","B9",  "C9","JS9"));
		
		return list;
	}
}
