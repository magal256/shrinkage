package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForSeventhExecutor extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитет 7";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(service.getTolerancesManual(  "F7","G7", "H7", "JS7", "K7",/* "M7", */"N7"));
		list.add(service.getTolerancesManual("J7"));
		
		list.add(service.getTolerancesManual(  "D7","E7", "EF7", "R7", "S7", "U7", "P7"));
		list.add(service.getTolerancesManual( "T7"));
		return list;
	}
}
