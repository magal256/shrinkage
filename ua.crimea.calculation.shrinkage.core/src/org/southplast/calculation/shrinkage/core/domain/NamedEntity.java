package org.southplast.calculation.shrinkage.core.domain;

public abstract class NamedEntity extends BaseEntity {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof NamedEntity) {
			if(!super.equals(obj)){
				return false;
			} 
			NamedEntity entity = (NamedEntity) obj;
			if(entity.getName() != null && getName() == null  ||
			   entity.getName() == null && getName() != null  ){
				return false;
			} else if(entity.getName() != null && 
					  !entity.getName().equals(getName())){
				return false;
			}
			
		} else{
			return false;
		} 
			
		return true;
	}
}
