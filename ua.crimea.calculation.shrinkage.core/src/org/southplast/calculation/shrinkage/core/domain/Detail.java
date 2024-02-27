package org.southplast.calculation.shrinkage.core.domain;

import java.util.ArrayList;
import java.util.List;


public class Detail extends NamedEntity {
	private Matter matter;
	private List<ShrinkageCalculation> shrinkages;
	private MeasuringType defaultMeasuring;
	private String description;	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MeasuringType getDefaultMeasuring() {
		return defaultMeasuring;
	}
	public void setDefaultMeasuring(MeasuringType defaultMeasuring) {
		this.defaultMeasuring = defaultMeasuring;
	}
	
	public Matter getMatter() {
		return matter;
	}
	
	public void setMatter(Matter matter) {
		this.matter = matter;
		if(shrinkages == null){
			return;
		}
		for(ShrinkageCalculation sc:shrinkages){				
			sc.setMatter(matter);
		}
	}
	
	public void addTolerance(ShrinkageCalculation calc){
		calc.setMatter(matter);
		
		if(shrinkages == null){
			shrinkages = new ArrayList<ShrinkageCalculation>();
		}
		calc.setDetail(this);
		shrinkages.add(calc);
	}
	
	public List<ShrinkageCalculation> getTolerances() {
		return shrinkages;
	}
	
	public void setTolerances(List<ShrinkageCalculation> shrinkages) {
		this.shrinkages = shrinkages;
		if(shrinkages == null){
			return;
		}
		for(ShrinkageCalculation sc:this.shrinkages){	
			if(sc == null){
				continue;
			}
			sc.setMatter(matter);
			sc.setDetail(this);
		}
	}
	
	@Override
	public void compile(BaseEntity entity) {
		Detail detail = (Detail)entity;
		setId(detail.getId());
		setName(detail.getName());
		setMatter(detail.getMatter());
		setTolerances(detail.getTolerances());
		setDefaultMeasuring(detail.getDefaultMeasuring());		
	}
	
	@Override
	public Detail clone() throws CloneNotSupportedException {
		Detail detail = new Detail();
		detail.setId(getId());
		detail.setName(getName());
		detail.setMatter(getMatter().clone());
		detail.setTolerances(getTolerances());
		detail.setDefaultMeasuring(getDefaultMeasuring());
		
		return detail;
	}
}
