package org.southplast.calculation.shrinkage.core.repository.dao;

import java.math.BigDecimal;
import java.util.List;

import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.domain.ToleranceType;


public interface WorkmanshipDao {
	public List<Tolerance> findByName(String name);
	public List<Tolerance> findByType(ToleranceType wType, BigDecimal size);
	public Tolerance findByValues(BigDecimal up, BigDecimal down);
	public Long insert(Tolerance tolerance);
	public Tolerance findById(Long id);
	public List<Tolerance> findByNameForManual(String name);
}
