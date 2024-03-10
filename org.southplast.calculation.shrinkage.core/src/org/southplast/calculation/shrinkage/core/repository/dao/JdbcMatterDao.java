package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.domain.Shrinkage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcMatterDao implements MatterDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public Long findCount() {
		return jdbcTemplate.queryForLong("select count(*) from matters");
	}
	
	@Override
	public List<String> findMakers() {
		String sql = "select DISTINCT maker from matters";
		jdbcTemplate.execute("CHECKPOINT");
		return jdbcTemplate.query(sql, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return new String(rs.getString("maker"));
			}
		});
	}
	
	@Override
	public List<String> findTypes() {
		String sql = "select DISTINCT m_type from matters";
		jdbcTemplate.execute("CHECKPOINT");
		return jdbcTemplate.query(sql, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("m_type") != null?
					   new String(rs.getString("m_type")):null;
			}
		});
	}
	
	@Override
	public List<Matter> findAll() {
		String sql = "select id, name, m_type, commercial_grade, maker, " +
				"long_min, long_max,cros_min, cros_max, " +
				"description, group_id from matters";
		jdbcTemplate.execute("CHECKPOINT");
		
		return jdbcTemplate.query(sql, new MaterMapper());
	}
	
	@Override
	public List<Matter> findByGroup(MatterGroup group) {
		String sql = "select id, name, m_type, commercial_grade, maker, " +
						"long_min, long_max,cros_min, cros_max, " +
						"description, group_id from matters where group_id = ?";
		jdbcTemplate.execute("CHECKPOINT");
		
		return jdbcTemplate.query(sql, new Object[]{group.getId()}, new MaterMapper());
	}

	@Override
	public void insert(Matter matter) {
		String sql = "insert into matters(id, name, m_type, maker, " +
						"long_min, long_max,cros_min, cros_max, " +
						"description, group_id) values(DEFAULT,?,?,?,?,?,?,?,?,?)";
		
		jdbcTemplate.update(sql, new Object[]{
								matter.getCommercialGrade(), 
								matter.getType(), 
//								matter.getCommercialGrade(), 
								matter.getMaker(),
								matter.getLongitudinalShrinkage().getMinimum(),
								matter.getLongitudinalShrinkage().getMaximum(),
								matter.getCrossShrinkage().getMinimum(),
								matter.getCrossShrinkage().getMaximum(),
								matter.getDescription(),
								matter.getGroup().getId()});
		
		matter.setId(findLastId());
	}

	@Override
	public void update(Matter matter) {
		String sql = "update matters SET  commercial_grade=?, m_type=?, name=?, maker=?, " +
						"long_min=?, long_max=?,cros_min=?, cros_max=?, " +
						"description=?, group_id=? WHERE id = ?";
		jdbcTemplate.update(sql, new Object[]{
								matter.getCommercialGrade(), 
								matter.getType(), 
								matter.getCommercialGrade(), 
								matter.getMaker(),
								matter.getLongitudinalShrinkage().getMinimum(),
								matter.getLongitudinalShrinkage().getMaximum(),
								matter.getCrossShrinkage().getMinimum(),
								matter.getCrossShrinkage().getMaximum(),
								matter.getDescription(),
								matter.getGroup().getId(), 
								matter.getId()});
		
	}

	@Override
	public void delete(Matter matter) {
		String sql = "delete from matters where id=?";
		jdbcTemplate.update(sql, new Object[]{matter.getId()});
	}
	
	private Long findLastId() {
		jdbcTemplate.execute("CHECKPOINT");
		return jdbcTemplate.queryForLong("select max(id) from matters");
	}	
	private static final class MaterMapper implements RowMapper<Matter>{

		@Override
		public Matter mapRow(ResultSet rs, int arg1) throws SQLException {
			Shrinkage longShr = new Shrinkage();
			longShr.setMinimum(rs.getBigDecimal("long_min"));
			longShr.setMaximum(rs.getBigDecimal("long_max"));
			
			Shrinkage crossShr = new Shrinkage();
			crossShr.setMinimum(rs.getBigDecimal("cros_min"));
			crossShr.setMaximum(rs.getBigDecimal("cros_max"));
			
			MatterGroup group = new MatterGroup();
			group.setId(rs.getLong("group_id"));
			
			Matter m = new Matter();
			m.setId(rs.getLong("id"));
			m.setName(rs.getString("name"));
			m.setCommercialGrade(rs.getString("name"));
			m.setDescription(rs.getString("description"));
			m.setMaker(rs.getString("maker"));
			m.setType(rs.getString("m_type"));
			m.setLongitudinalShrinkage(longShr);
			m.setCrossShrinkage(crossShr);
			m.setGroup(group);
			
			return m;
		}
		
	}

}
