package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForTenthAndEleventhExecutor extends AbstractOpenToleranceManualExecutor {
	
	@Override
	public String getTitle() {
		return "���������� ���������� ����� ����������� �������� 1...500 �� (�� ���� 25347-82) ��������� 10 � 11";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(service.getTolerancesManual( "d10", "h10"));
		list.add(service.getTolerancesManual( "js10", "a11", "b11", "c11", "d11", "h11", "js11"));
		
		return list;
	}
	
	
}
