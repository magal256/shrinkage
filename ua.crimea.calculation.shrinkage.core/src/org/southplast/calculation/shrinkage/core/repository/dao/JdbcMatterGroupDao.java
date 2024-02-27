package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcMatterGroupDao implements MatterGroupDao {		
	private JdbcTemplate jdbcTemplate;
	
	public JdbcMatterGroupDao() {
	}
	
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public List<MatterGroup> findAll() {
    	String sql = "select id, name, description, hidden, (select count(*) from matters where group_id = mg.id) as items_count from matter_groups mg ";
		
		jdbcTemplate.execute("CHECKPOINT");		
		return jdbcTemplate.query(sql, new GroupMapper());
    }
	
    @Override
    public void update(MatterGroup group) {
    	String sql = "update matter_groups set name=?, description=?, hidden=? WHERE id=?";
    	jdbcTemplate.update(sql, new Object[]{group.getName(), 
    										  group.getDescription(),
    										  group.isHidden(),
    										  group.getId()});
    }
    
    @Override
    public void delete(MatterGroup group) {
    	String sql = "delete from matter_groups where id = ?";
    	jdbcTemplate.update(sql, new Object[]{group.getId()});
    }
    
    @Override
    public void insert(MatterGroup group) {
    	Long id = findLastId();
    	String sql = "insert into matter_groups (id, name, description, hidden) values (?, ?, ?, ?)";
    	
    	group.setId(id != null? id + 1:1L);
    	
    	jdbcTemplate.update(sql, new Object[] { group.getId(), 
    											group.getName(), 
    											group.getDescription() ,
    											group.isHidden()});
    }
    
    
	@Override
	public List<MatterGroup> findActive() {
		String sql = "select id, name, description, hidden, (select count(*) from matters where group_id = mg.id) as items_count from matter_groups mg where mg.hidden = 'false'";
		
		jdbcTemplate.execute("CHECKPOINT");		
		return jdbcTemplate.query(sql, new GroupMapper());
	}
	
	private Long findLastId() {
		return jdbcTemplate.queryForLong("select max(id) from matter_groups");
	}		
	
	private static final class GroupMapper implements RowMapper<MatterGroup> {

		@Override
		public MatterGroup mapRow(ResultSet rs, int arg1) throws SQLException {
			MatterGroup group = new MatterGroup();
			group.setId(rs.getLong("id"));
			group.setName(rs.getString("name"));
			group.setDescription(rs.getString("description"));
			group.setMattersCount(rs.getLong("items_count"));
			group.setHidden(rs.getBoolean("hidden"));
			return group;
		}
		
	}
}
