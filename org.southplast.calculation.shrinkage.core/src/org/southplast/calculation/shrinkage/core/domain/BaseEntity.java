package org.southplast.calculation.shrinkage.core.domain;

public abstract class BaseEntity implements Cloneable {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract void compile(BaseEntity entity);
	
	@Override
	public abstract Object clone() throws CloneNotSupportedException;
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BaseEntity){
			BaseEntity entity = (BaseEntity) obj;
			if(entity.getId() != null && getId() == null  ||
			   entity.getId() == null && getId() != null  ){
				return false;
			} else if(entity.getId() != null && 
					  !entity.getId().equals(getId())){
				return false;
			}
			
		} else {
			return false;
		}
		
		return true;
	}
}
