package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForFromTwelfthToFourteenthExecutor 
								extends AbstractOpenToleranceManualExecutor  {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитеты 12, 13 и 14";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
		list.add(service.getTolerancesManual("H12"));
		list.add(service.getTolerancesManual("B12", "JS12", "H13", "JS13", "H14", "JS14"));
		
		return list;
	}
}
