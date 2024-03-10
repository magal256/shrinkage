package org.southplast.calculation.shrinkage.core.utils;

import org.southplast.calculation.shrinkage.core.domain.CalculationType;
import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;

public class InteraxialPredicate  implements Predicate<ShrinkageCalculation> {
	@Override
	public boolean apply(ShrinkageCalculation calc) {
        return CalculationType.INTERAXIAL.equals(calc.getType());
    }
}
