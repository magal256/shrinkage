package org.southplast.calculation.shrinkage.core.management;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;


public interface MatterService {
	public void exportCalculations(Detail detail, List<ShrinkageCalculation> calcs, String filePath);
	
	public List<Matter> getMatters();
	public List<Matter> getMattersByGroup(MatterGroup group);	
	public List<String> getMattersTypes();
	public List<String> getMattersMakers();	
	public void saveMatter(Matter matter);
	public void deleteMatter(Matter matter);
	
	public void hideGroups(List<MatterGroup> groups);
	public void showGroups(List<MatterGroup> groups);
	public List<MatterGroup> getMatterGroups();
	public List<MatterGroup> getAllMatterGroups();
	public void saveMatterGroup(MatterGroup group);
	public void deleteMatterGroup(MatterGroup group);
	
	public List<Detail> getDetailsByMetter(Matter metter);
	public void saveDetail(Detail detail);
	public void deleteDetail(Detail detail);
	
	
	public List<Tolerance> getTolerancesByName(String name);
	public List<Tolerance> getTolerancesForShaft(BigDecimal size);
	public List<Tolerance> getTolerancesForOpening(BigDecimal size);
	public Tolerance getToleranceByValues(BigDecimal up, BigDecimal down);
	public Map<String, Object> getTolerancesManual(String... names);
	
	public ShrinkageCalculation getNewMachingCalculation();
	public ShrinkageCalculation getNewOuterDiametralCalculation();
	public ShrinkageCalculation getNewInnerDiametralCalculation();
	public ShrinkageCalculation getNewOuterUprightCalculation();
	public ShrinkageCalculation getNewInnerUprightCalculation();
	public ShrinkageCalculation getNewInteraxial();
	public List<ShrinkageCalculation> getCalculations(Long detailId);
	public void saveShrinkageCalculations(List<ShrinkageCalculation> calcs, Long detailId);
	
}
