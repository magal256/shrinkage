package org.southplast.calculation.shrinkage.core.management;

import static org.southplast.calculation.shrinkage.core.utils.CsvUtils.writeDetail;
import static org.southplast.calculation.shrinkage.core.utils.CsvUtils.writeMaps;
import static org.southplast.calculation.shrinkage.core.utils.ListUtils.filter;
import static org.southplast.calculation.shrinkage.core.utils.ShrinkageUtils.convertCalculationsToMaps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.southplast.calculation.shrinkage.core.csv.Csv;
import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;
import org.southplast.calculation.shrinkage.core.domain.MatterGroup;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.domain.ToleranceType;
import org.southplast.calculation.shrinkage.core.messages.Messages;
import org.southplast.calculation.shrinkage.core.repository.dao.DetailDao;
import org.southplast.calculation.shrinkage.core.repository.dao.MatterDao;
import org.southplast.calculation.shrinkage.core.repository.dao.MatterGroupDao;
import org.southplast.calculation.shrinkage.core.repository.dao.ShrinkageCalculationDao;
import org.southplast.calculation.shrinkage.core.repository.dao.WorkmanshipDao;
import org.southplast.calculation.shrinkage.core.utils.InnerDiametralPredicate;
import org.southplast.calculation.shrinkage.core.utils.InnerUprightPredicate;
import org.southplast.calculation.shrinkage.core.utils.InteraxialPredicate;
import org.southplast.calculation.shrinkage.core.utils.MatchingPredicate;
import org.southplast.calculation.shrinkage.core.utils.OuterDiametralPredicate;
import org.southplast.calculation.shrinkage.core.utils.OuterUprightPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Transactional(propagation=Propagation.REQUIRED)
public class MatterServiceImpl implements MatterService {
	private long index = 0;
	
	@Autowired
	private MatterGroupDao groupDao;
	
	@Autowired
	private WorkmanshipDao toleranceDao;
	
	@Autowired
	private MatterDao matterDao;
	
	@Autowired
	private DetailDao detailDao;
	
	@Autowired
	private ShrinkageCalculationDao calculationDao;
		
	@Override
	public List<Matter> getMatters() {		
		return matterDao.findAll();
	}	
	
	@Override
	public List<MatterGroup> getAllMatterGroups() {
		return groupDao.findAll();
	}
	
	@Override
	public void exportCalculations(Detail detail, 
								   List<ShrinkageCalculation> calcs, 
								   String filePath) {
			  
		List<ShrinkageCalculation> diCalcs = filter(calcs, new InnerDiametralPredicate());
	    List<ShrinkageCalculation> doCalcs = filter(calcs, new OuterDiametralPredicate());
	    List<ShrinkageCalculation> iCalcs = filter(calcs, new InteraxialPredicate());
	    List<ShrinkageCalculation> uiCalcs = filter(calcs, new InnerUprightPredicate());
	    List<ShrinkageCalculation> uoCalcs = filter(calcs, new OuterUprightPredicate());
	    List<ShrinkageCalculation> mCalcs = filter(calcs, new MatchingPredicate()); 
		
		Csv.Writer writer = new Csv.Writer(filePath).delimiter(';');
		
		writeDetail(detail, writer);
		writeMaps(convertCalculationsToMaps(mCalcs), writer);
		writeMaps(convertCalculationsToMaps(doCalcs), writer);
		writeMaps(convertCalculationsToMaps(uoCalcs), writer);  
		writeMaps(convertCalculationsToMaps(diCalcs), writer);      
		writeMaps(convertCalculationsToMaps(iCalcs), writer);
		writeMaps(convertCalculationsToMaps(uiCalcs), writer);
		  
		writer.close();
	}
	
	@Override
	public List<Matter> getMattersByGroup(MatterGroup group) {
		List<Matter> matters =  matterDao.findByGroup(group);
		for(Matter matter:matters){
			matter.setDetails(detailDao.findByMatter(matter));
			matter.setGroup(group);
		}
		return matters;
	}

	@Override
	public List<String> getMattersTypes() {
		return matterDao.findTypes();
	}

	@Override
	public List<String> getMattersMakers() {
		return matterDao.findMakers();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void hideGroups(List<MatterGroup> groups) {
		for(MatterGroup group:groups){
			group.setHidden(true);
			groupDao.update(group);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void showGroups(List<MatterGroup> groups) {
		for(MatterGroup group:groups){
			group.setHidden(false);
			groupDao.update(group);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void saveMatter(Matter matter) {
		if(matter.getId() == null){
			matterDao.insert(matter);
		} else {
			matterDao.update(matter);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void deleteMatter(Matter matter) {
		if(matter.getDetails() != null){
			for(Detail det:matter.getDetails()){
				detailDao.delete(det);
			}
		}
		
		matterDao.delete(matter);
	}

	@Override
	public List<MatterGroup> getMatterGroups() {
		List<MatterGroup> groups = groupDao.findActive();
		
		return  groups;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void saveMatterGroup(MatterGroup group) {
		if(group.getId() == null){
			groupDao.insert(group);
			
		} else {
			groupDao.update(group);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void deleteMatterGroup(MatterGroup group) {
		List<Matter> matters = matterDao.findByGroup(group);
		for(Matter m:matters){
			deleteMatter(m);
		}
		groupDao.delete(group);
	}

	@Override
	public List<Detail> getDetailsByMetter(Matter metter) {
		List<Detail> details = new ArrayList<Detail>();
		for(Detail det:details){
			det.setMatter(metter);
		}
		return details;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void saveDetail(Detail detail) {
		if(detail.getId() != null){
			detailDao.update(detail);
		} else {
			detailDao.insert(detail);
		}
		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void deleteDetail(Detail detail) {
		calculationDao.deleteFromDetail(detail.getId());
		detailDao.delete(detail);
	}

	@Override
	public List<Tolerance> getTolerancesByName(String name) {
		return toleranceDao.findByName(name);
	}

	@Override
	public Map<String, Object> getTolerancesManual(String... names) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, List<Tolerance>> sizes = new HashMap<String, List<Tolerance>>();
		for(int i = 0; i < names.length; i++){
			List<Tolerance> tols = toleranceDao.findByNameForManual(names[i]);
			sizes.put(names[i], tols);
		}
		map.put("sizes", sizes);
		map.put("names", names);
		return map;
	}
	
	@Override
	public List<Tolerance> getTolerancesForShaft(BigDecimal size) {
		return toleranceDao.findByType(ToleranceType.SHAFT, size);
	}

	@Override
	public List<Tolerance> getTolerancesForOpening(BigDecimal size) {
		return toleranceDao.findByType(ToleranceType.OPENING, size);
	}
	
	@Override
	public Tolerance getToleranceByValues(BigDecimal up, BigDecimal down) {
		Tolerance tol = toleranceDao.findByValues(up, down);
		
		if(tol == null){
			tol = new Tolerance();
			tol.setName(Messages.get("custom.tolerance"));
			tol.setDown(down);
			tol.setUp(up);
		} else {
			tol.setSizeList(toleranceDao.findByName(tol.getName()));
		}
		
		return tol;
	}
	
	@Override
	public ShrinkageCalculation getNewInteraxial(){
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSize(BigDecimal.ONE);
	   	sc.setType(CalculationType.INTERAXIAL) ;	   
		
	   	return sc;
	}
	
	@Override
	public ShrinkageCalculation getNewInnerUprightCalculation() {
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSignSize(BigDecimal.ONE);
	    sc.setSize(BigDecimal.ONE);
	   	sc.setType(CalculationType.INNER_UPRIGHT) ;	   
	   	List<Tolerance> ws1 = toleranceDao.findByName("H7");
	   	Tolerance w1 = ws1.get(0);
	   	w1.setSizeList(ws1);
		sc.setSignTolerance(w1);
		
		List<Tolerance> ws2 = toleranceDao.findByName("js7");
	   	Tolerance w2 = ws2.get(0);
	   	w2.setSizeList(ws2);
		sc.setTolerance(w2);
		return sc;
	}
	
	@Override
	public ShrinkageCalculation getNewOuterUprightCalculation() {
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSignSize(BigDecimal.ONE);
	    sc.setSize(BigDecimal.ONE);
	   	sc.setType(CalculationType.OUTER_UPRIGHT) ;	   
	   	List<Tolerance> ws1 = toleranceDao.findByName("H7");
	   	Tolerance w1 = ws1.get(0);
	   	w1.setSizeList(ws1);
		sc.setSignTolerance(w1);
		
		List<Tolerance> ws2 = toleranceDao.findByName("js7");
	   	Tolerance w2 = ws2.get(0);
	   	w2.setSizeList(ws2);
		sc.setTolerance(w2);
		return sc;
	}
	
	@Override
	public ShrinkageCalculation getNewInnerDiametralCalculation(){
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSignSize(BigDecimal.ONE);
	    sc.setSize(BigDecimal.ONE);
	   	sc.setType(CalculationType.INNER_DIAMETRAL) ;	   
	   	List<Tolerance> ws1 = toleranceDao.findByName("H7");
	   	Tolerance w1 = ws1.get(0);
	   	w1.setSizeList(ws1);
		sc.setSignTolerance(w1);
		
		List<Tolerance> ws2 = toleranceDao.findByName("js7");
	   	Tolerance w2 = ws2.get(0);
	   	w2.setSizeList(ws2);
		sc.setTolerance(w2);
		return sc;
	}
	
	@Override
	public ShrinkageCalculation getNewOuterDiametralCalculation() {		
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSignSize(BigDecimal.ONE);
	    sc.setSize(BigDecimal.ONE);
	   	sc.setType(CalculationType.OUTER_DIAMETRAL) ;	   
	   	List<Tolerance> ws1 = toleranceDao.findByName("H7");
	   	Tolerance w1 = ws1.get(0);
	   	w1.setSizeList(ws1);
		sc.setSignTolerance(w1);
		
		List<Tolerance> ws2 = toleranceDao.findByName("js7");
	   	Tolerance w2 = ws2.get(0);
	   	w2.setSizeList(ws2);
		sc.setTolerance(w2);
		return sc;
	}
	
	@Override
	public ShrinkageCalculation getNewMachingCalculation() {		 
		ShrinkageCalculation sc = new ShrinkageCalculation();
		sc.setId(-(calculationDao.loadLastId() + index++));
	    sc.setSignSize(BigDecimal.ONE);
	    sc.setSize(BigDecimal.ONE);
	    sc.setType(CalculationType.MATCHING) ;	  		   
	    List<Tolerance> ws1 = toleranceDao.findByName("H7");
	   	Tolerance w1 = ws1.get(0);
	   	w1.setSizeList(ws1);
		sc.setTolerance(w1);
		
		List<Tolerance> ws2 = toleranceDao.findByName("js7");
	   	Tolerance w2 = ws2.get(0);
	   	w2.setSizeList(ws2);
		sc.setSignTolerance(w2);
		return sc;
	}

	@Override	
	public List<ShrinkageCalculation> getCalculations(Long detailId) {
		List<ShrinkageCalculation> calcs = calculationDao.findByDetailAndType(detailId);
		for(ShrinkageCalculation calc:calcs){
			calc.setTolerance(toleranceDao.findById(calc.getTolerance().getId()));
			calc.setSignTolerance(toleranceDao.findById(calc.getSignTolerance().getId()));
		}
		return calcs;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false, rollbackFor=Throwable.class)
	public void saveShrinkageCalculations(List<ShrinkageCalculation> calcs, 
										  Long detailId) {
		calculationDao.deleteFromDetail(detailId);			
		
		for(ShrinkageCalculation calc:calcs){
			if(null != calc.getTolerance() && 
			   null == calc.getTolerance().getId()) {
				calc.getTolerance().setId(toleranceDao.insert(
										  calc.getTolerance()));
			}
			if(null != calc.getSignTolerance() &&
			   null == calc.getSignTolerance().getId()){
				calc.getSignTolerance().setId(toleranceDao.insert(
											  calc.getSignTolerance()));
			}
			calculationDao.insert(calc);	
		} 
		
	}
}
