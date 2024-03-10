package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenOpeningManualForFromTenthToEleventhExecutor extends AbstractOpenToleranceManualExecutor   {
	@Override
	public String getTitle() {
		return "Предельные отклонения отверстий номинальных размеров 1...500 мм (по ГОСТ 25347-82) Квалитеты 10 и 11";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
		list.add(service.getTolerancesManual( "E10", "D10", "H10", "JS10"));
		list.add(service.getTolerancesManual("A11", "B11", /*"C11",*/ "D11", "H11", "JS11", "C11"));
		
		return list;
	}
}
