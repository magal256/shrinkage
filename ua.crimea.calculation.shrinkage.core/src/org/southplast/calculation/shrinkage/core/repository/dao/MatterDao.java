package org.southplast.calculation.shrinkage.core.repository.dao;

import java.util.List;

import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;


public interface MatterDao {
	public List<String> findMakers();
	public List<String> findTypes();
	public List<Matter> findByGroup(MatterGroup group);
	public List<Matter> findAll();
	public void insert(Matter matter);
	public void update(Matter matter);
	public void delete(Matter matter);
	public Long findCount();
}
