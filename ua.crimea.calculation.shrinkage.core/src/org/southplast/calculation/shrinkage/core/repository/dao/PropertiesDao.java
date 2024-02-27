package org.southplast.calculation.shrinkage.core.repository.dao;

import org.southplast.calculation.shrinkage.core.domain.Property;

public interface PropertiesDao {
	public Property loadByName(String name);
	public void insert(Property property);
	public void update(Property property);
}
