package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForFromFifteenthToSeventeenthExecutor 
								extends AbstractOpenToleranceManualExecutor  {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитеты 15, 16 и 17";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
		list.add(service.getTolerancesManual("H15", "JS15",  "H16", "JS16", "H17", "JS17"));
		
		return list;
	}
}
