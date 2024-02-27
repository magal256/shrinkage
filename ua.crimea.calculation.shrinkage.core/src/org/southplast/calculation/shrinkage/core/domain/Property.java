package org.southplast.calculation.shrinkage.core.domain;

import java.util.Date;

public class Property {
	private String name;
	private Date date;
	private Integer value;
	
	public Property() {
	}
	
	public Property(String name, Date date) {
		this.name = name;
		this.date = date;				
	}
	
	public Property(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
}
