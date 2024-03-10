package org.southplast.calculation.shrinkage.core.utils;

import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;

public class OuterUprightPredicate  implements Predicate<ShrinkageCalculation>{
	@Override
	public boolean apply(ShrinkageCalculation calc) {
        return CalculationType.OUTER_UPRIGHT.equals(calc.getType());
    }
}
