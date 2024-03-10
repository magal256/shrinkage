package org.southplast.calculation.shrinkage.core.utils;

import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;

public class MatchingPredicate  implements Predicate<ShrinkageCalculation>{
	@Override
	public boolean apply(ShrinkageCalculation calc) {
		
    	if(calc == null){
    		return false;
    	}
        return CalculationType.MATCHING.equals(calc.getType());
    }
}
