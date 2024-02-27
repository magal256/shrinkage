package org.southplast.calculation.shrinkage.core.domain;

public enum MeasuringType {
	MATCHING(0), CALCULATING(1);
	private int id;
	
	private MeasuringType(int index) {
		this.id = index;
	}
	public int getId() {
		return id;
	}
	
	@Override
	public  String toString() {
		switch(this.id){
			case 0: return "����� �������";
			case 1: return "����� �������";
			default: return "����� �������";
		}
	}
}
