package org.southplast.calculation.shrinkage.core.repository.dao;

import java.util.List;

import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;


public interface DetailDao {
	public List<Detail> findByMatter(Matter matter);
	public void insert(Detail detail);
	public void update(Detail detail);
	public void delete(Detail detail);
}
