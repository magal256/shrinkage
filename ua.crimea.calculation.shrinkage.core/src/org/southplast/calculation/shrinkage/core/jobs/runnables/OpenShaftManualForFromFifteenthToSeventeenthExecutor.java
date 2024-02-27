package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForFromFifteenthToSeventeenthExecutor 
								  extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "���������� ���������� ����� ����������� �������� 1...500 �� (�� ���� 25347-82) ��������� 15, 16 � 17";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual( "h15", "js15", "h16", "js16", "h17", "js17"));
		
		return list;
	}
}
