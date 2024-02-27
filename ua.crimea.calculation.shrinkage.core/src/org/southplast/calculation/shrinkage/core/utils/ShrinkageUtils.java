package org.southplast.calculation.shrinkage.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;
import org.southplast.calculation.shrinkage.core.messages.Messages;


import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.innerDiameter;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.innerHeight;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.interaxialSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.maxShrinkageSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.maxSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minShrinkageSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.minSize;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.outerDiameter;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.outerHeight;
import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.tolerance;

public class ShrinkageUtils {
	public static boolean validateShrinkageMinSize(ShrinkageCalculation calc) {
		return minSize(calc).compareTo(minShrinkageSize(calc)) <= 0 &&
				maxSize(calc).compareTo(minShrinkageSize(calc)) >= 0;
	}
	
	public static boolean validateShrinkageMaxSize(ShrinkageCalculation calc) {
		System.out.println("Min Size" + minSize(calc));
		System.out.println("Max Size" + maxSize(calc));
		System.out.println("max Shri " + maxShrinkageSize(calc));
		
		return minSize(calc).compareTo(maxShrinkageSize(calc)) <= 0 &&
				maxSize(calc).compareTo(maxShrinkageSize(calc)) >= 0;
	}
	
	public static void defineTolerance(ShrinkageCalculation calc) {
		Tolerance tol = calc.getTolerance();
		
		if(null == tol || tol.getSizeList() == null) {
			return;
		}
		List<Tolerance> wkms = tol.getSizeList();
		
		for(Tolerance w:wkms) {
			if((calc.getSize().compareTo(BigDecimal.ONE) == 0 &&
				w.getDownSize().compareTo(BigDecimal.ONE) == 0) ||
			   (w.getDownSize().compareTo(calc.getSize()) < 0 &&
				w.getUpSize().compareTo(calc.getSize()) >= 0 )) {
				
				w.setSizeList(wkms);
				tol = w;
			}
		}
		calc.setTolerance(tol);
	}
	
	public static void defineSignTolerance(ShrinkageCalculation calc) {
		Tolerance signTol = calc.getSignTolerance();
		if(signTol == null || signTol.getSizeList() == null) {
			return;
		}
		List<Tolerance> wkms = signTol.getSizeList();		
		for(Tolerance w:wkms){
			if((calc.getSignSize().compareTo(BigDecimal.ONE) == 0 &&
				w.getUpSize().compareTo(BigDecimal.ONE) == 0) ||
			   (w.getUpSize().compareTo(calc.getSignSize()) < 0 &&
				w.getDownSize().compareTo(calc.getSignSize()) >= 0 )) {
				w.setSizeList(wkms);
				signTol = w;
			}
		}
		calc.setSignTolerance(signTol);
	}
	
	public static Map<Integer, String> getDiametralInnerHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, Messages.get("table.header.shrinkage.diameter"));
		map.put(2, Messages.get("table.header.tolerance.name"));
		map.put(3, Messages.get("table.header.tolerance.value"));
		map.put(4, Messages.get("table.header.tolerance.down"));
		map.put(5, "D, min");
		map.put(6, "Dסע, mm");
		
		return map;
	}
	
	public static Map<Integer, String> convertDiametralInnerToMap(
													ShrinkageCalculation calc){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, calc.getTolerance().getName());
		map.put(3, format(tolerance(calc.getTolerance())));
		map.put(4, format(calc.getTolerance().getDown()));
		map.put(5, format(minSize(calc)));
		map.put(6, format(innerDiameter(calc)));
		return map;
	}
	
	public static Map<Integer, String> getInteraxialHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, "A, mm");
		map.put(2, "Am, mm");
		
		return map;		
	}
	
	public static Map<Integer, String> convertInteraxialOuterToMap(
												ShrinkageCalculation calc){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, format(interaxialSize(calc)));
		
		return map;		
	}
	
	public static Map<Integer, String> getDiametralOuterHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, Messages.get("table.header.shrinkage.diameter"));
		map.put(2, Messages.get("table.header.tolerance.name"));
		map.put(3, Messages.get("table.header.tolerance.value"));
		map.put(4, Messages.get("table.header.tolerance.up"));
		map.put(5, "D, max");
		map.put(6, "Dm, mm");
		
		return map;
	}
	
	public static Map<Integer, String> convertDiametralOuterToMap(
													ShrinkageCalculation calc) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, calc.getTolerance().getName());
		map.put(3, format(tolerance(calc.getTolerance())));
		map.put(4, format(calc.getTolerance().getUp()));
		map.put(5, format(maxSize(calc)));
		map.put(6, format(outerDiameter(calc)));
		
		return map;
	}
	
	public static Map<Integer, String> getUprightInnerHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, "H, mm");
		map.put(2, Messages.get("table.header.tolerance.name"));
		map.put(3, Messages.get("table.header.tolerance.value"));
		map.put(4,  Messages.get("table.header.tolerance.down"));
		map.put(5,  Messages.get("table.header.tolerance.up"));
		map.put(6,  Messages.get("table.header.tolerance.sign"));
		map.put(7, Messages.get("table.header.tolerance.sign.value"));
		map.put(8,  Messages.get("table.header.tolerance.sign.down"));
		map.put(9, Messages.get("table.header.tolerance.sign.up"));
		map.put(10, "H min, לל");
		map.put(11, "Hm, mm");
		
		return map;
	}
	
	public static Map<Integer, String> convertUprightInnerToMap(
													ShrinkageCalculation calc) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, calc.getTolerance().getName());
		map.put(3, format(tolerance(calc.getTolerance())));
		map.put(4, format(calc.getTolerance().getDown()));
		map.put(5,  format(calc.getTolerance().getUp()));
		map.put(6, calc.getSignTolerance().getName());
		map.put(7, format(tolerance(calc.getSignTolerance())));
		map.put(8, format(calc.getSignTolerance().getDown()));
		map.put(9, format(calc.getSignTolerance().getUp()));
		map.put(10, format(minSize(calc)));
		map.put(11, format(innerHeight(calc)));
		
		return map;
	}
	
	public static Map<Integer, String> getUprightOuterHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, "H, mm");
		map.put(2, Messages.get("table.header.tolerance.name"));
		map.put(3, Messages.get("table.header.tolerance.value"));
		map.put(4,  Messages.get("table.header.tolerance.down"));
		map.put(5,  Messages.get("table.header.tolerance.up"));
		map.put(6,  Messages.get("table.header.tolerance.sign"));
		map.put(7, Messages.get("table.header.tolerance.sign.value"));
		map.put(8,  Messages.get("table.header.tolerance.sign.down"));
		map.put(9, Messages.get("table.header.tolerance.sign.up"));
		map.put(10, "H max, לל");
		map.put(11, "Hm, mm");
		
		return map;
	}
	
	public static Map<Integer, String> convertUprightOuterToMap(
													ShrinkageCalculation calc) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, calc.getTolerance().getName());
		map.put(3, format(tolerance(calc.getTolerance())));
		map.put(4, format(calc.getTolerance().getDown()));
		map.put(5, format(calc.getTolerance().getUp()));
		map.put(6, calc.getSignTolerance().getName());
		map.put(7, format(tolerance(calc.getSignTolerance())));
		map.put(8, format(calc.getSignTolerance().getDown()));
		map.put(9, format(calc.getSignTolerance().getUp()));
		map.put(10, format(maxSize(calc)));
		map.put(11, format(outerHeight(calc)));
		
		return map;
	}
	
	public static Map<Integer, String> getMatchingHeader(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, Messages.get("table.header.number"));
		map.put(1, Messages.get("table.header.size"));
		map.put(2, Messages.get("table.header.tolerance.name"));
		map.put(3,  Messages.get("table.header.tolerance.down"));
		map.put(4,  Messages.get("table.header.tolerance.up"));
		map.put(5,  Messages.get("table.header.size.max"));
		map.put(6,  Messages.get("table.header.size.min"));
		map.put(7,  Messages.get("table.header.size.sign"));
		map.put(8,  Messages.get("table.header.tolerance.sign"));
		map.put(9,  Messages.get("table.header.tolerance.sign.down"));
		map.put(10, Messages.get("table.header.tolerance.sign.up"));
		map.put(11, Messages.get("table.header.shrinkage.size.min"));
		map.put(12, Messages.get("table.header.shrinkage.size.max"));
		
		return map;
	}
	
	public static Map<Integer, String> convertMatchingToMap(
													ShrinkageCalculation calc){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, calc.getNumber().toString());
		map.put(1, format(calc.getSize()));
		map.put(2, calc.getTolerance().getName());
		map.put(3, format(calc.getTolerance().getDown()));
		map.put(4, format(calc.getTolerance().getUp()));
		map.put(5, format(maxSize(calc)));
		map.put(6, format(minSize(calc)));
		map.put(7, format(calc.getSignSize()));
		map.put(8, calc.getSignTolerance().getName());	
		map.put(9, format(calc.getSignTolerance().getDown()));
		map.put(10, format(calc.getSignTolerance().getUp()));
		map.put(11, format(minShrinkageSize(calc)));
		map.put(12, format(maxShrinkageSize(calc)));
		
		return map;
	}
	
	public static List<Map<Integer, String>> convertCalculationsToMaps(
											List<ShrinkageCalculation> calcs) {
		if(calcs == null || calcs.isEmpty()){
			return new ArrayList<Map<Integer,String>>();
		}
		
		List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
		list.add(new HashMap<Integer, String>());
		
		if(CalculationType.INNER_DIAMETRAL.equals(calcs.get(0).getType())) {
			list.add(getDiametralInnerHeader());			
			for(ShrinkageCalculation calc:calcs){
				list.add(convertDiametralInnerToMap(calc));
			}
		} else if(CalculationType.OUTER_DIAMETRAL.equals(calcs.get(0).getType())) {
			list.add(getDiametralOuterHeader());			
			for(ShrinkageCalculation calc:calcs){
				list.add(convertDiametralOuterToMap(calc));
			}
		} else if(CalculationType.INTERAXIAL.equals(calcs.get(0).getType())) {
			list.add(getInteraxialHeader());			
			for(ShrinkageCalculation calc:calcs){
				list.add(convertInteraxialOuterToMap(calc));
			}
		} else if(CalculationType.INNER_UPRIGHT.equals(calcs.get(0).getType())){
			list.add(getUprightInnerHeader());			
			for(ShrinkageCalculation calc:calcs){
				list.add(convertUprightInnerToMap(calc));
			}
		} else if(CalculationType.OUTER_UPRIGHT.equals(calcs.get(0).getType())){
			list.add(getUprightOuterHeader());			
			for(ShrinkageCalculation calc:calcs){
				list.add(convertUprightOuterToMap(calc));
			}
		} else if(CalculationType.MATCHING.equals(calcs.get(0).getType())){
			list.add(getMatchingHeader());
			for(ShrinkageCalculation calc:calcs){
				list.add(convertMatchingToMap(calc));
			}
		}
		
		return list;
	}
	
	
}
