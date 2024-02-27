package org.southplast.calculation.shrinkage.core.domain;

public enum CalculationType {
	MATCHING(0), 
	OUTER_DIAMETRAL(1), 
	INNER_DIAMETRAL(2), 
	OUTER_UPRIGHT(3), 
	INNER_UPRIGHT(4),
	INTERAXIAL(5);
	
	private int id;
	
	private CalculationType(int index) {
		id = index;
	}
	public int getId() {
		return id;
	}
}
