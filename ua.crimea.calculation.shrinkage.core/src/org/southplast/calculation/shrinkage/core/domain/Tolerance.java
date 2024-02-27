package org.southplast.calculation.shrinkage.core.domain;

import java.math.BigDecimal;
import java.util.List;

public class Tolerance extends NamedEntity implements Cloneable {	
	private BigDecimal value;	
	private BigDecimal up;
	private BigDecimal down;
	private BigDecimal upSize;
	private BigDecimal downSize;
	private ToleranceType type;
	private WorkmanshipClass group;	
	private List<Tolerance> sizeList;
	
	public Tolerance() {
	}
	
	public Tolerance(ToleranceType type) {
		this.type = type;
	}
	
	public List<Tolerance> getSizeList() {
		return sizeList;
	}
	public void setSizeList(List<Tolerance> sizeList) {
		this.sizeList = sizeList;
	}
	
	public BigDecimal getUpSize() {
		return upSize;
	}
	public void setUpSize(BigDecimal upSize) {
		this.upSize = upSize;
	}
	
	public BigDecimal getDownSize() {
		return downSize;
	}
	public void setDownSize(BigDecimal downSize) {
		this.downSize = downSize;
	}
	
	public WorkmanshipClass getGroup() {
		return group;
	}
	public void setGroup(WorkmanshipClass group) {
		this.group = group;
	}
	
	public ToleranceType getType() {
		return type;
	}
	public void setType(ToleranceType type) {
		this.type = type;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public BigDecimal getUp() {
		return up;
	}
	public void setUp(BigDecimal up) {
		this.up = up;
	}
	
	public BigDecimal getDown() {
		return down;
	}
	public void setDown(BigDecimal down) {
		this.down = down;
	}
	
	@Override
	public void compile(BaseEntity entity) {
		
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	@Override
	public Tolerance clone() {
		Tolerance w = new Tolerance();
		w.setId(getId());
		w.setValue(getValue());
		w.setName(getName());
		w.setDown(getDown());
		w.setDownSize(getDownSize());
		w.setUp(getUp());
		w.setUpSize(getUpSize());
		w.setGroup(getGroup());
		w.setType(getType());
		w.setSizeList(getSizeList());
		
		return w;
	}
}
