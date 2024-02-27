package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.Property;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcPropertiesDao implements PropertiesDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void insert(Property property) {
		jdbcTemplate.update("insert into properties(name, num_value, date_value) values(?,?,?)", 
				new Object[]{property.getName(), property.getValue(), property.getDate()});
	}
	@Override
	public Property loadByName(String name) {
		try{
			return jdbcTemplate.queryForObject("select name, date_value, num_value from properties where name = ?", 
					   new PropertyMapper(),
					   new Object[]{name});
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	@Override
	public void update(Property property) {
		jdbcTemplate.update("update properties set num_value=?, date_value=? where name=?", 
				new Object[]{property.getValue(), property.getDate(), property.getName()});
	}
	
	private class PropertyMapper implements RowMapper<Property> {
		@Override
		public Property mapRow(ResultSet rs, int index) throws SQLException {
			Property property = new Property();
			property.setName(rs.getString("name"));
			property.setDate(rs.getDate("date_value"));
			property.setValue(rs.getInt("num_value"));
			
			return property;
		}
	}
}
