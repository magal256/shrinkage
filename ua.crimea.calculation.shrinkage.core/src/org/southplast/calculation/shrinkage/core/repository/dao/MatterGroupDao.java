package org.southplast.calculation.shrinkage.core.repository.dao;

import java.util.List;

import org.southplast.calculation.shrinkage.core.domain.MatterGroup;


public interface MatterGroupDao {
	public List<MatterGroup> findAll();
	
	public List<MatterGroup> findActive();
	
	public void insert(MatterGroup group);
	
	public void update(MatterGroup group);
	
	public void delete(MatterGroup group);
}
