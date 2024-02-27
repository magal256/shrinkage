package org.southplast.calculation.shrinkage.core.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class JdbcShrinkageCalculationDao implements ShrinkageCalculationDao {
	
	private JdbcTemplate jdbcTemplate;
	
	 public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	 }
	
	@Override
	public Long loadLastId() {
		return jdbcTemplate.queryForLong("select max(id) from calculation");
	} 
	 
	@Override
	public List<ShrinkageCalculation> findByDetailAndType(Long detId) {
		String sql = "select id, c_size, sign_size, calc_type, " +
					 "tolerance_id, sign_tolerance_id " +
					 "from calculation where detail_id = ?";
		jdbcTemplate.execute("CHECKPOINT");
		
		return  jdbcTemplate.query(sql, new Object[]{detId}, 
								   new ShrinkageCalculationMapper());
	}
	
	@Override
	public void deleteFromDetail(Long detId) {
		String sql = "delete from calculation " +
					 "where detail_id = ?";
		jdbcTemplate.update(sql, new Object[]{detId});
	}
	
	@Override
	public void insert(ShrinkageCalculation calc) {
		String sql = "insert into calculation (id, detail_id, c_size," +
				" sign_size, tolerance_id, sign_tolerance_id, calc_type)" +
				"values(DEFAULT,?,?,?,?,?,?)";		
		
		 jdbcTemplate.update(sql, new Object[]{calc.getDetail().getId(),
											  calc.getSize(), 
											  calc.getSignSize(), 
											  null != calc.getTolerance()?
													calc.getTolerance().getId():
														null,
											  null != calc.getSignTolerance()?
												calc.getSignTolerance().getId():
													null,
											  calc.getType().getId()});
		 
		 calc.setId(jdbcTemplate.queryForLong("select max(id) from calculation"));
	}
	
	
	private static final class ShrinkageCalculationMapper 
								implements RowMapper<ShrinkageCalculation> {
		@Override
		public ShrinkageCalculation mapRow(ResultSet rs, int arg1)
				throws SQLException {
			Tolerance tol = new Tolerance();
			tol.setId(rs.getLong("tolerance_id"));
			
			Tolerance signTol = new Tolerance();
			signTol.setId(rs.getLong("sign_tolerance_id"));
			
			ShrinkageCalculation calc = new ShrinkageCalculation();
			calc.setId(rs.getLong("id"));
			calc.setSize(rs.getBigDecimal("c_size"));
			calc.setSignSize(rs.getBigDecimal("sign_size"));
			calc.setSignTolerance(signTol);
			calc.setTolerance(tol);
			calc.setType(CalculationType.values()[rs.getInt("calc_type")]);
			
			return calc;
		}
	}
}
