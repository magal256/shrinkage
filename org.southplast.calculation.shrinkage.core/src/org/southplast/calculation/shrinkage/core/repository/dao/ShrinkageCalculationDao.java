package org.southplast.calculation.shrinkage.core.repository.dao;

import java.util.List;

import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;


public interface ShrinkageCalculationDao {
	public List<ShrinkageCalculation> findByDetailAndType(Long detId);
	public void insert(ShrinkageCalculation calc);
	public void deleteFromDetail(Long detId);
	public Long loadLastId();
}
