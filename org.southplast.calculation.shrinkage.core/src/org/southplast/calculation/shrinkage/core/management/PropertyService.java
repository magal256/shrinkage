package org.southplast.calculation.shrinkage.core.management;

import org.southplast.calculation.shrinkage.core.domain.Property;

public interface PropertyService {
	public Property get(String name);
	public void addProperty(Property property);
	public void editProperty(Property property);
}
