package org.southplast.calculation.shrinkage.core.domain;

import java.math.BigDecimal;


public class ShrinkageCalculation  extends BaseEntity {
	private Integer number;
	private Matter matter;
	private BigDecimal size;
	private BigDecimal signSize;
	private Tolerance tolerance;
	private Tolerance signTolerance;
	private CalculationType type;
	private Detail detail;
	
	public Detail getDetail() {
		return detail;
	}
	public void setDetail(Detail detail) {
		this.detail = detail;
	}
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public BigDecimal getSignSize() {
		return signSize;
	}
	public void setSignSize(BigDecimal backSize) {
		this.signSize = backSize;
	}
	
	public BigDecimal getSize() {
		return size;
	}
	public void setSize(BigDecimal size) {
		this.size = size;				
	}
	
	public Tolerance getTolerance() {
		return tolerance;
	}
	public void setTolerance(Tolerance firstTolerance) {
		this.tolerance = firstTolerance;
	}
	
	public Tolerance getSignTolerance() {
		return signTolerance;
	}
	public void setSignTolerance(Tolerance secondTolerance) {
		this.signTolerance = secondTolerance;
	}
	
	public Matter getMatter() {
		return matter;
	}
	public void setMatter(Matter matter) {
		this.matter = matter;
	}
	
	public CalculationType getType() {
		return type;
	}
	public void setType(CalculationType type) {
		this.type = type;		
	}
	
	@Override
	public void compile(BaseEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
