package org.southplast.calculation.shrinkage.core.repository.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.domain.ToleranceType;
import org.southplast.calculation.shrinkage.core.domain.WorkmanshipClass;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcWorkmanshipDao implements WorkmanshipDao {
	private JdbcTemplate jdbcTemplate;
	
	 public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }
	
	 @Override
	 public Tolerance findById(Long id) {
		 String sql = "select id, name, up, down, up_size, down_size, w_type, " +
		 		"w_class from tolerances where id = ?";
		 List<Tolerance> result = jdbcTemplate.query(sql, new Object[]{id}, 
				 									 new BaseMapper());
		 
		 
		 return result != null && !result.isEmpty()?result.get(0):null;
	 }
	 
	 @Override
	 public Long insert(Tolerance tol) {
		 
		 String sql = "insert into tolerances(id, up, down, name) values(DEFAULT,?,?,?)";		
		 jdbcTemplate.update(sql, new Object[]{
								 tol.getUp().multiply(new BigDecimal("1000")), 
								 tol.getDown().multiply(new BigDecimal("1000")), 
								 tol.getName()});
		 
		 jdbcTemplate.execute("CHECKPOINT");
		 return jdbcTemplate.queryForLong("select max(id) from tolerances");
	 }
	 
	 @Override
	 public Tolerance findByValues(BigDecimal up, BigDecimal down) {
		 String sql = "select id, name, up, down, up_size, down_size, w_type, w_class from tolerances where up = ? and down = ?";
		 List<Tolerance> result = jdbcTemplate.query(sql, new Object[]{
													   		up.multiply(
													 	new BigDecimal("1000")), 
													   down.multiply(
													    new BigDecimal("1000"))}, 
				 									 new BaseMapper());
		 
		 
		 return result != null && !result.isEmpty()?result.get(0):null;
	 }
	@Override
	public List<Tolerance> findByName(String name) {
		
		return jdbcTemplate.query("select id, name, up, down, up_size, " +
				"down_size, w_type, w_class from tolerances where name = ? order by down_size", 
				new Object[]{name}, new BaseMapper());
	}
	
	@Override
	public List<Tolerance> findByNameForManual(String name) {
		return jdbcTemplate.query("select id, name, up, down, up_size, " +
				"down_size, w_type, w_class from tolerances where name = ? order by down_size", 
				new Object[]{name}, new ManualMapper());
	}
	
	@Override
	public List<Tolerance> findByType(ToleranceType wType, BigDecimal size) {
//		 
		return jdbcTemplate.query("select id, name, up, down, up_size, down_size, " +
				"w_type, w_class from tolerances " +
				"where w_type = ? and ((up_size >= ? and down_size < ?) or" +
				"(1=? and down_size = 1)) order by workmanship", 
				new Object[]{ wType.ordinal(), size, size, size}, 
							  new BaseMapper());
	}
	
	private static final class BaseMapper implements RowMapper<Tolerance> {
		@Override
		public Tolerance mapRow(ResultSet rs, int arg1) throws SQLException {
			Tolerance w = new Tolerance();
			w.setId(rs.getLong("id"));
			w.setName(rs.getString("name"));
			w.setUp((new BigDecimal("0.001") ).multiply(rs.getBigDecimal("up")!=null?rs.getBigDecimal("up"):BigDecimal.ZERO));
			w.setDown((new BigDecimal("0.001")).multiply( rs.getBigDecimal("down")!=null?rs.getBigDecimal("down"):BigDecimal.ZERO));
			w.setUpSize(( rs.getBigDecimal("up_size")!=null?rs.getBigDecimal("up_size"):BigDecimal.ZERO));
			w.setDownSize(( rs.getBigDecimal("down_size")!=null?rs.getBigDecimal("down_size"):BigDecimal.ZERO));
			w.setType(ToleranceType.values()[rs.getInt("w_type")]);
			w.setGroup(WorkmanshipClass.values()[rs.getInt("w_class")]);
			
			return w;
		}
	}
	
	private static final class ManualMapper implements RowMapper<Tolerance> {
		
		@Override
		public Tolerance mapRow(ResultSet rs, int arg1) throws SQLException {
			Tolerance w = new Tolerance();
			w.setId(rs.getLong("id"));
			w.setName(rs.getString("name"));
			w.setUp((rs.getBigDecimal("up")!=null?rs.getBigDecimal("up"):BigDecimal.ZERO));
			w.setDown((rs.getBigDecimal("down")!=null?rs.getBigDecimal("down"):BigDecimal.ZERO));
			w.setUpSize(( rs.getBigDecimal("up_size")!=null?rs.getBigDecimal("up_size"):BigDecimal.ZERO));
			w.setDownSize(( rs.getBigDecimal("down_size")!=null?rs.getBigDecimal("down_size"):BigDecimal.ZERO));
			w.setType(ToleranceType.values()[rs.getInt("w_type")]);
			w.setGroup(WorkmanshipClass.values()[rs.getInt("w_class")]);
			
			return w;
		}
		
	}

}
