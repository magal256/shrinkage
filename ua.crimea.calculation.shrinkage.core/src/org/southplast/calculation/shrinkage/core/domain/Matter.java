package org.southplast.calculation.shrinkage.core.domain;

import java.util.List;

public class Matter extends NamedEntity {
	private String commercialGrade;
	private String type;
	private String maker;
	private Shrinkage longitudinalShrinkage;
	private Shrinkage crossShrinkage;
	private List<Detail> details;
	private MatterGroup group;
	private String description;
	
	@Override
	public String toString() {		
		return getCommercialGrade();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Matter)){
			return false;
		}
		Matter m = (Matter)obj;
		if(m.getId() == null && getId() != null){
			return false;
		} else if(m.getId() != null && !m.getId().equals(getId())){
			return false;
		}
		
		return true;
	}
	
	@Override
	public void compile(BaseEntity entity) {
		Matter matter = (Matter)entity;
		setId(matter.getId());
		setName(matter.getName());
		setCommercialGrade(matter.getCommercialGrade());
		setType(matter.getType());
		setMaker(matter.getMaker());
		setLongitudinalShrinkage(matter.getLongitudinalShrinkage());
		setCrossShrinkage(matter.getCrossShrinkage());
		setDescription(matter.getDescription());
//		if(getGroup() != null ){
//			getGroup().compile(matter.getGroup());
//		} else {
			setGroup(matter.getGroup());
//		}		
	}
	
	@Override
	public Matter clone() throws CloneNotSupportedException {
		Matter matter = new Matter();
		matter.setId(getId());
		matter.setName(getName());
		matter.setCommercialGrade(getCommercialGrade());
		matter.setType(getType());
		matter.setMaker(getMaker());
		matter.setLongitudinalShrinkage(getLongitudinalShrinkage() != null?
										getLongitudinalShrinkage().clone():null);
		matter.setCrossShrinkage(getCrossShrinkage() != null? 
								 getCrossShrinkage().clone():null);
		matter.setDetails(getDetails());
		matter.setGroup(getGroup());
		matter.setDescription(getDescription());
		return matter;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public MatterGroup getGroup() {
		return group;
	}
	public void setGroup(MatterGroup group) {
		this.group = group;
	}
	
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
		if(this.details == null){
			return;
		}
		for(Detail det:this.details){
			det.setMatter(this);
		}
	}		
	
	public String getCommercialGrade() {
		return commercialGrade;
	}
	public void setCommercialGrade(String commercialGrade) {
		this.commercialGrade = commercialGrade;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public Shrinkage getLongitudinalShrinkage() {
		return longitudinalShrinkage;
	}
	public void setLongitudinalShrinkage(Shrinkage longitudinalShrinkage) {
		this.longitudinalShrinkage = longitudinalShrinkage;
	}
	public Shrinkage getCrossShrinkage() {
		return crossShrinkage;
	}
	public void setCrossShrinkage(Shrinkage crossShrinkage) {
		this.crossShrinkage = crossShrinkage;
	}
}
