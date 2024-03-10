package org.southplast.calculation.shrinkage.core.handlers;

import org.southplast.calculation.shrinkage.core.jobs.runnables.AbstractExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForEighthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForFromFifteenthToSeventeenthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForFromFourthToFifthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForFromTenthToEleventhExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForFromTwelfthToFourteenthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForNinthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForSeventhExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenOpeningManualForSixthExecutor;

public class OpenOpeningManualHandler extends AbstractOpenManualHandler {
	@Override
	protected AbstractExecutor getExecutorInstance() {
		
		switch(workmanship){
			case 4: 
			case 5: return new OpenOpeningManualForFromFourthToFifthExecutor();
			case 6: return new OpenOpeningManualForSixthExecutor();
			case 7: return new OpenOpeningManualForSeventhExecutor();
			case 8: return new OpenOpeningManualForEighthExecutor();
			case 9: return new OpenOpeningManualForNinthExecutor();
			case 10: 
			case 11: return new OpenOpeningManualForFromTenthToEleventhExecutor();
			case 12: 
			case 13:
			case 14:return new OpenOpeningManualForFromTwelfthToFourteenthExecutor();
			case 15:
			case 16:
			case 17: return new OpenOpeningManualForFromFifteenthToSeventeenthExecutor();
			
			default: return null;
		}
	}
}
