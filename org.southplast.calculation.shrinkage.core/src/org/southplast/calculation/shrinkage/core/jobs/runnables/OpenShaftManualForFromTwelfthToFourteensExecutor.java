package org.southplast.calculation.shrinkage.core.jobs.runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenShaftManualForFromTwelfthToFourteensExecutor 
								  extends AbstractOpenToleranceManualExecutor {
	@Override
	public String getTitle() {
		return "���������� ���������� ����� ����������� �������� 1...500 �� (�� ���� 25347-82) ��������� 12, 13 � 14";
	}
	
	@Override
	public List<Map<String, Object>> getTolerancesManual() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list.add(service.getTolerancesManual(  "b12", "h12", "js12", "h13", "js13", "h14", "js14"));
		
		
		return list;
	}
}
