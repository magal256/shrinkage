package org.southplast.calculation.shrinkage.core.domain;

import java.math.BigDecimal;

public class Shrinkage implements Cloneable {
	private BigDecimal minimum;
	private BigDecimal maximum;
	
	public Shrinkage() {
		minimum = BigDecimal.ZERO;
		maximum = BigDecimal.ZERO;
	}
	
	public Shrinkage(BigDecimal min, BigDecimal max){
		minimum = min;
		maximum = max;
	}
	
	@Override
	protected Shrinkage clone() throws CloneNotSupportedException {
		return new Shrinkage(getMinimum(), getMaximum());
	}
	
	public BigDecimal getMinimum() {
		return minimum;
	}
	public void setMinimum(BigDecimal minimum) {
		this.minimum = minimum;
	}
	
	public BigDecimal getMaximum() {
		return maximum;
	}
	public void setMaximum(BigDecimal maximum) {
		this.maximum = maximum;
	}
}
