package org.southplast.calculation.shrinkage.core.handlers;

import org.southplast.calculation.shrinkage.core.jobs.runnables.AbstractExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForEighthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForFifthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForFourthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForFromFifteenthToSeventeenthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForFromTwelfthToFourteensExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForNinthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForSeventhExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForSixthExecutor;
import org.southplast.calculation.shrinkage.core.jobs.runnables.OpenShaftManualForTenthAndEleventhExecutor;

public class OpenShaftManualHandler extends AbstractOpenManualHandler {
	@Override
	protected AbstractExecutor getExecutorInstance() {
		switch(workmanship){
			case 4: return new OpenShaftManualForFourthExecutor();
			case 5: return new OpenShaftManualForFifthExecutor();
			case 6: return new OpenShaftManualForSixthExecutor();
			case 7: return new OpenShaftManualForSeventhExecutor();
			case 8: return new OpenShaftManualForEighthExecutor();
			case 9: return new OpenShaftManualForNinthExecutor();
			case 10: 
			case 11: return new OpenShaftManualForTenthAndEleventhExecutor();
			case 12: 
			case 13:
			case 14:return new OpenShaftManualForFromTwelfthToFourteensExecutor();
			case 15:
			case 16:
			case 17: return new OpenShaftManualForFromFifteenthToSeventeenthExecutor();
			
			default: return null;
		}
		
	}

}
