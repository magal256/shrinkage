package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForSixthExecutor extends AbstractOpenToleranceManualExecutor  {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 6";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(service.getTolerancesManual(  "D6", "E6", "K6", "M6"));
		list.add(service.getTolerancesManual( "G6", "H6", "JS6",/* "K6", "M6",*/ "N6"));
		list.add(service.getTolerancesManual("P6"));
		list.add(service.getTolerancesManual(  "F6","EF6", "FG6", "R6", "S6"));
		list.add(service.getTolerancesManual("J6"));
		list.add(service.getTolerancesManual("T6"));
		
		return list;
	}
}
