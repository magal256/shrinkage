package org.southplast.calculation.shrinkage.core.utils;

import static org.southplast.calculation.shrinkage.core.utils.CalculationUtils.format;

import java.util.List;
import java.util.Map;

import org.southplast.calculation.shrinkage.core.csv.Csv;
import org.southplast.calculation.shrinkage.core.csv.Csv.Writer;
import org.southplast.calculation.shrinkage.core.domain.Detail;
import org.southplast.calculation.shrinkage.core.domain.Matter;


public class CsvUtils {
	public static void writeDetail(Detail detail, Writer writer){
		Matter matter = detail.getMatter();	
		writer.comment("Расчет усадки")
	      .value(" ").value(" ").value("мин.").value("макс.")
	      				.value("Деталь:").value(detail.getName()).newLine()
	      .value("").value("Усадка:")
	      	  .value(format(matter.getLongitudinalShrinkage().getMinimum()))
	      	  .value(format(matter.getLongitudinalShrinkage().getMaximum()))
	      	  .value("Материал:").value(matter.getCommercialGrade()).newLine();
	}
	
	public static void writeMapLine(Map<Integer, String> map, Csv.Writer writer){
		for(String str:map.values()){
			writer.value(str);
		}
		
		writer.newLine();
	}
	
	public static void writeMaps(List<Map<Integer, String>> maps, Writer writer){
		for(Map<Integer, String> map:maps){
			writeMapLine(map, writer);
		}
	}
	
}
