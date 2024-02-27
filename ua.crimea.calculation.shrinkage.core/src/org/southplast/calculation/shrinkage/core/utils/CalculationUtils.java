package org.southplast.calculation.shrinkage.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.southplast.calculation.shrinkage.core.domain.ShrinkageCalculation;
import org.southplast.calculation.shrinkage.core.domain.Tolerance;


public class CalculationUtils {
	private static final BigDecimal ONE_HUNDREDTH = new BigDecimal("0.01");
	private static final BigDecimal TWO = new BigDecimal("2");
	
	private static DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.US);
	private static DecimalFormat numberFormat = new DecimalFormat("0.00", unusualSymbols);
	private static DecimalFormat toleranceFormat = new DecimalFormat("0.000", unusualSymbols);
	private static DecimalFormat intFormat = new DecimalFormat("0", unusualSymbols);
	
	static{
		numberFormat.setRoundingMode(RoundingMode.HALF_UP);
	}
	
	public static BigDecimal maxShrinkageSize(ShrinkageCalculation calc) {
		BigDecimal maxSh = calc.getMatter().getLongitudinalShrinkage().getMaximum();
		BigDecimal sum =  BigDecimal.ONE.add((maxSh.multiply(ONE_HUNDREDTH)));;
		
		return minSignSize(calc).divide(sum, 7, BigDecimal.ROUND_HALF_UP); 
	}
	
	public static BigDecimal minShrinkageSize(ShrinkageCalculation calc) {
		BigDecimal minSh = calc.getMatter().getLongitudinalShrinkage().getMinimum();
		BigDecimal sum = BigDecimal.ONE.add((minSh.multiply(ONE_HUNDREDTH)));
		
		return maxSignSize(calc).divide(sum, 7, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal interaxialSize(ShrinkageCalculation calc){
		BigDecimal minSh = calc.getMatter().getLongitudinalShrinkage().getMinimum();
		BigDecimal maxSh = calc.getMatter().getLongitudinalShrinkage().getMaximum();
		BigDecimal middle = middle(minSh, maxSh);
		BigDecimal mult = calc.getSize().multiply(ONE_HUNDREDTH).multiply(middle);
				
		return calc.getSize().add(mult);
	}
	
	public static BigDecimal middle(BigDecimal f, BigDecimal s) {
		return (f.add(s)).divide(TWO, 7, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal innerHeight(ShrinkageCalculation calc){
		BigDecimal minH = minSize(calc);
		BigDecimal minSh = calc.getMatter().getLongitudinalShrinkage().getMinimum();
		BigDecimal maxSh = calc.getMatter().getLongitudinalShrinkage().getMaximum();
		BigDecimal multF = minH.multiply(ONE_HUNDREDTH).multiply(middle(maxSh, minSh));
		BigDecimal multS = middle(tolerance(calc.getTolerance()), 
								  tolerance(calc.getSignTolerance()));		
		return minH.add(multF).add(multS);
	}
	
	public static BigDecimal outerHeight(ShrinkageCalculation calc){
		BigDecimal maxH = maxSize(calc);
		BigDecimal minSh = calc.getMatter().getLongitudinalShrinkage().getMinimum();
		BigDecimal maxSh = calc.getMatter().getLongitudinalShrinkage().getMaximum();
		BigDecimal multF = maxH.multiply(ONE_HUNDREDTH).multiply(middle(maxSh, minSh));
		BigDecimal multS = middle(tolerance(calc.getTolerance()), 
								  tolerance(calc.getSignTolerance()));		
		return maxH.add(multF).subtract(multS);
	}
	
	public static BigDecimal innerDiameter(ShrinkageCalculation calc){
		BigDecimal minD = minSize(calc);
		BigDecimal minSh = calc.getMatter().getLongitudinalShrinkage().getMinimum();
		BigDecimal mult = minSh.multiply(minD).multiply(ONE_HUNDREDTH);
		
		return minD.add(mult).add(tolerance(calc.getTolerance()));
	}
	
	public static BigDecimal minSize(ShrinkageCalculation calc){
		return calc.getSize().add(calc.getTolerance().getDown());
	}
	
	public static BigDecimal tolerance(Tolerance w){
		return w.getUp().subtract(w.getDown());
	}		
	
	public static BigDecimal minSizeBySignTolerance(ShrinkageCalculation calc){
		return calc.getSize().add(calc.getSignTolerance().getDown());
	}
	
	public static BigDecimal minSignSize(ShrinkageCalculation calc){
		return calc.getSignTolerance().getDown().add(calc.getSignSize());
	}
	
	public static BigDecimal maxSignSize(ShrinkageCalculation calc){
		return calc.getSignTolerance().getUp().add(calc.getSignSize());
	}
	
	public static BigDecimal maxSize(ShrinkageCalculation calc){
		
		return  calc.getSize().add(calc.getTolerance().getUp());				
	}
	
	public static BigDecimal outerDiameter(ShrinkageCalculation calc){
		BigDecimal maxD = maxSize(calc);
		BigDecimal maxSh = calc.getMatter().getLongitudinalShrinkage().getMaximum();
		BigDecimal mult = maxSh.multiply(maxD).multiply(ONE_HUNDREDTH);
		
		return maxD.add(mult).subtract(tolerance(calc.getTolerance()));
	}
	
	public static String formatInt(BigDecimal number){
		return intFormat.format(number);
	}
	
	public static String format(BigDecimal number){
		return numberFormat.format(number);
	}
	
	public static String formatTolerance(BigDecimal number){
		return toleranceFormat.format(number);
	}
}
