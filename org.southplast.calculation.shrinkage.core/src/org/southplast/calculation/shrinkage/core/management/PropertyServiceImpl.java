package org.southplast.calculation.shrinkage.core.management;

import org.southplast.calculation.shrinkage.core.domain.Property;
import org.southplast.calculation.shrinkage.core.repository.dao.PropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;


public class PropertyServiceImpl implements PropertyService{
	@Autowired
	private PropertiesDao dao; 
	
	@Override
	public Property get(String name) {
		return dao.loadByName(name);
	}

	@Override
	public void addProperty(Property property) {
		dao.insert(property);
	}

	@Override
	public void editProperty(Property property) {
		dao.update(property);
	}

}
