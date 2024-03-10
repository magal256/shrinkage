package org.southplast.calculation.shrinkage.core.domain;

import java.util.ArrayList;
import java.util.List;

public class MatterGroup extends NamedEntity implements Cloneable {
	private String description;
	private List<Matter> matters;
	private boolean selected;
	private Long mattersCount;
	private boolean hidden;
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public Long getMattersCount() {
		return mattersCount;
	}
	public void setMattersCount(Long mattersCount) {
		this.mattersCount = mattersCount;
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Matter> getMatters() {
		return matters;
	}
	public void setMatters(List<Matter> matters) {		
		this.matters = matters;
		if(this.matters == null){
			return;
		}
		for(Matter m:this.matters){
			m.setGroup(this);
		}	
	}		
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MatterGroup){
			if(!super.equals(obj)){
				return false;
			}
			
			MatterGroup group = (MatterGroup) obj;
			if(group.getDescription() != null && getDescription() == null  ||
			   group.getDescription() == null && getDescription() != null  ){
				return false;
			} else if(group.getDescription() != null && 
					  !group.getDescription().equals(getDescription())){
				return false;
			}
			
		} else {
			return false;
		}
		
		return true;
	}
	
	@Override
	public void compile(BaseEntity entity) {
		MatterGroup group = (MatterGroup) entity;
		
		setId(group.getId());
		setName(group.getName());
		setDescription(group.getDescription());
		setSelected(group.isSelected());
		setMatters(group.getMatters());
	}
	
	@Override
	public MatterGroup clone() throws CloneNotSupportedException {
		List<Matter> matters = null;
		if(this.matters != null){
			matters = new ArrayList<Matter>();
			matters.addAll(this.matters);
		}
				
		MatterGroup group = new MatterGroup();
		group.setId(getId());
		group.setName(getName());
		group.setDescription(description);
		group.setSelected(selected);
		group.setMatters(matters);
		
		return group;
	}
	
	@Override
	public String toString() {
		
		return getName();
	}
}
