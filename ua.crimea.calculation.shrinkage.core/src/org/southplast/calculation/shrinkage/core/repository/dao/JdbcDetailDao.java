package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MeasuringType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcDetailDao implements DetailDao {
	
private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public List<Detail> findByMatter(Matter matter) {
		String sql = "select id, name, matter_id, measuring, description from details where matter_id=?";
//		jdbcTemplate.execute("CHECKPOINT");
		
		return jdbcTemplate.query(sql, new Object[]{matter.getId()}, new DetailMapper());
	}

	@Override
	public void insert(Detail detail) {
		String sql = "insert into details(id, name, matter_id, measuring, description) " +
				"values(?,?,?,?,?)";
				
		detail.setId(findLastId() + 1);
		jdbcTemplate.update(sql, new Object[]{detail.getId(),detail.getName(),
				detail.getMatter().getId(),
				detail.getDefaultMeasuring().getId(),
				detail.getDescription()});
	}

	@Override
	public void update(Detail detail) {
		String sql = "update details set name=?, matter_id=?, " +
										"measuring=?, description=? where id=?";
		jdbcTemplate.update(sql, new Object[]{detail.getName(),
											detail.getMatter().getId(),
											detail.getDefaultMeasuring().getId(),
											detail.getDescription(),
											detail.getId()});
	}

	@Override
	public void delete(Detail detail) {
		String sql = "delete from details where id=?";
		jdbcTemplate.update(sql, new Object[]{detail.getId()});
	}
	
	private Long findLastId(){
		return jdbcTemplate.queryForLong("select max(id) from details");
	}
	
	private static final class DetailMapper implements RowMapper<Detail>{
		@Override
		public Detail mapRow(ResultSet rs, int arg1) throws SQLException {
			Matter matter = new Matter();
			matter.setId(rs.getLong("matter_id"));
			
			Detail d = new Detail();
			d.setId(rs.getLong("id"));
			d.setName(rs.getString("name"));
			d.setDescription(rs.getString("description"));
			d.setMatter(matter);
			d.setDefaultMeasuring(MeasuringType.values()[rs.getInt("measuring")]);
			return d;
		}
	}
	
}
