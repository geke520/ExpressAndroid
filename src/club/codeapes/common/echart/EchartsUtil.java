package club.codeapes.common.echart;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.codeapes.common.date.DateUtil;
import club.codeapes.common.lang.ArrayAndMapUtil;

/**
 * Echarts 构造时间轴
 * @author JuNks7
 *
 */
public class EchartsUtil {
	
	/**
	 * 构造时间数组
	 * @param result
	 * @param 心跳：Heartbeat
	 * @param 年报表：DynamicForYear	（1月至12月）
	 * @param 月报表：DynamicForMonth	（1日至月末）
	 * @param 周报表：DynamicForWeek 	（周一至周日）
	 * @param 日报表：DynamicForDay 	（24小时）
	 * @return
	 */ 
	public static String[] handleTimes(Map<String, Object> result) {
		String[] times =  null;
		String handleForDateType = result.get("handleForDateType").toString();
		if("Heartbeat".equals(handleForDateType)){
			times =  (String[])result.get("timesGroup[]");
		    result.put("startTime", DateUtil.format(new Date(), DateUtil.FORMAT_YMD) + " " + times[0]);
		    result.put("endTime", DateUtil.format(new Date(), DateUtil.FORMAT_YMD) + " " + times[times.length-1]);
		}else if("DynamicForYesterday".equals(handleForDateType)){
			String[] returnTimes = new String[24];
			for(int i = 0 ; i < 24 ; i ++){
				if(i < 10)
					returnTimes[i] = "0" + i + ":00";
				else
					returnTimes[i] = i + ":00";
			}
			times = new String[25];
			for(int i = 0 ; i < 25 ; i ++){
				if(i < 10)
					times[i] = "0" + i + ":00:00";
				else if(i == 24)
					times[i] = "23:59:00";
				else
					times[i] = i + ":00:00";
			}
			result.put("returnTimes", returnTimes);
			result.put("startTime", DateUtil.getYesterday(DateUtil.FORMAT_YMD) + " " + times[0]);
		    result.put("endTime", DateUtil.getYesterday(DateUtil.FORMAT_YMD) + " 23:59:59");
		}else if("DynamicForDay".equals(handleForDateType)){
			String[] returnTimes = new String[24];
			for(int i = 0 ; i < 24 ; i ++){
				if(i < 10)
					returnTimes[i] = "0" + i + ":00";
				else
					returnTimes[i] = i + ":00";
			}
			times = new String[25];
			for(int i = 0 ; i < 25 ; i ++){
				if(i < 10)
					times[i] = "0" + i + ":00:00";
				else if(i == 24)
					times[i] = "23:59:00";
				else
					times[i] = i + ":00:00";
			}
			result.put("returnTimes", returnTimes);
			result.put("startTime", DateUtil.format(new Date(), DateUtil.FORMAT_YMD) + " " + times[0]);
		    result.put("endTime", DateUtil.format(new Date(), DateUtil.FORMAT_YMD) + " 23:59:59");
		}else if("DynamicForWeek".equals(handleForDateType)){
			String[] returnTimes = new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
			List<String> list = DateUtil.getAllDaysForWeek(DateUtil.FORMAT_YMD);
			times = new String[list.size()];
			for(int i = 0 ; i < list.size() ; i ++){
//				if(i < list.size() -1)
					times[i] = list.get(i) + " 00:00:00";
//				else if(i == list.size() -1)
//					times[i] = list.get(i) + " 23:59:59";
			}
			result.put("returnTimes", returnTimes);
			result.put("startTime", times[0]);
		    result.put("endTime", times[list.size()-1]);
		}else if("DynamicForMonth".equals(handleForDateType)){
			List<String> list = DateUtil.getAllDaysForMonth(DateUtil.FORMAT_YMD);
			String returnTimes[] = (String[]) list.toArray(new String[list.size()]);
			times = new String[list.size()];
			for(int i = 0 ; i < list.size() ; i ++){
//				if(i < list.size() -1)
					times[i] = list.get(i) + " 00:00:00";
//				else if(i == list.size() -1)
//					times[i] = list.get(i) + " 23:59:59";
			}
			result.put("returnTimes", returnTimes);
			result.put("startTime", times[0]);
		    result.put("endTime", times[list.size()-1]);
		}else if("DynamicForYear".equals(handleForDateType)){
			int year = DateUtil.getYear(new Date());
			String[] returnTimes = new String[]{"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
			times = new String[13];
			for(int i = 0 ; i <= returnTimes.length ; i ++){
				if(i < 9)
					times[i] = year + "-" + "0" + (i+1) + "-01 00:00:00";
				else if(i == returnTimes.length)
					times[i] = year + "-" + (i) + "-31 23:59:59";
				else
					times[i] = year + "-" + (i+1) + "-01 00:00:00";
			}
			result.put("returnTimes", returnTimes);
			result.put("startTime", times[0]);
		    result.put("endTime", times[returnTimes.length]);
		}else if("Custom".equals(handleForDateType)){
			if(result.get("start_time") != null && result.get("end_time") != null){
				List<String> list = DateUtil.getBetweenDate(result.get("start_time").toString(), result.get("end_time").toString(), "yyyy-MM-dd");
				String returnTimes[] = (String[]) list.toArray(new String[list.size()]);
				times = new String[list.size()];
				if(list.size() == 1){
					times = new String[2];
					times[0] = list.get(0) + " 00:00:00";
					times[1] = list.get(0) + " 23:59:59";
					result.put("startTime", times[0]);
				    result.put("endTime", times[1]);
				}else{
					for(int i = 0 ; i < list.size() ; i ++){
//						if(i < list.size() -1)
							times[i] = list.get(i) + " 00:00:00";
//						else if(i == list.size() -1)
//							times[i] = list.get(i) + " 00:00:00";
					}
					result.put("startTime", times[0]);
				    result.put("endTime", times[list.size()-1]);
				}
			    result.put("endTime", times[list.size()-1]);
			    result.put("returnTimes", returnTimes);
			}
		}
        return times;
	}
	
	
	/**
     * 通过时间数据构造相应的数据
     * 2016-01-27 新加入vals[j] += Integer.parseInt(temp2Map.get("count_num").toString())，可以从现有的统计字段里面去叠加值
     * @param result
     * @param dataList
     */
    public static void structureDataForHeartbeat(Map<String, Object> result, List<Map<String,Object>> dataList, String[] times, String countNumForCloumnName) {
    	String handleForDateType = result.get("handleForDateType").toString();
    	String cloumn_type_name = result.get("cloumn_type_name").toString();					//必须条件
    	String cloumn_createtime_name = result.get("cloumn_createtime_name").toString();		//必须条件
		//初始化时间数组
    	Long[] newTimes = new Long[times.length];
        int i = 0;
        if("Heartbeat".equals(handleForDateType) || "DynamicForDay".equals(handleForDateType) || "DynamicForYesterday".equals(handleForDateType)){
	        for(String time : times){
	        	newTimes[i] = Long.parseLong(time.replaceAll(":","")); i ++;
	        }
        }else if("DynamicForWeek".equals(handleForDateType) || "DynamicForMonth".equals(handleForDateType) || "DynamicForYear".equals(handleForDateType) || "Custom".equals(handleForDateType)){
        	for(String time : times){
        		//正常 < 11 的条件是不会成立的
        		if(time.length() < 11)
        			newTimes[i] = Long.parseLong(time.replace(" ", "").replaceAll(":","").replaceAll("-", "") + "000000");
        		else
        			newTimes[i] = Long.parseLong(time.replace(" ", "").replaceAll(":","").replaceAll("-", ""));
        		 i ++;
        	}
        }
        List<Map<String,Object>> list = null;
        List<Map<String,Object>> listVal = new ArrayList<Map<String,Object>>();
        if(dataList != null && dataList.size() > 0){
        	list = dataList;
	        ArrayAndMapUtil.sort(list, cloumn_type_name);
	        String tempName = "";
	        String tempNames = "";
	        for(Map<String,Object> tempMap : list){
	        	List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
	        	if(!tempName.equals(tempMap.get(cloumn_type_name).toString())){
	        		tempName = tempMap.get(cloumn_type_name).toString();
	        		tempNames += tempName + ",";
		        	for(Map<String,Object> tempMap2 : list){
		        		if(tempMap2.get(cloumn_type_name).toString().equals(tempMap.get(cloumn_type_name).toString())){
		        			tempList.add(tempMap2);
		        		}
		        	}
		        	
		        	Map<String,Object> map = new HashMap<String, Object>();
		            map.put("title_name", tempList.get(0).get(cloumn_type_name));
		            Double[] vals = new Double[times.length]; 
		            //初始化为数组赋值
		            for(int ii = 0 ; ii < vals.length; ii++){
		            	vals[ii] = 0.0 ;
		            }
		            
		        	for(Map<String,Object> temp2Map : tempList){
		        		Long vt = 0l;
		        		if("Heartbeat".equals(handleForDateType) || "DynamicForDay".equals(handleForDateType)|| "DynamicForYesterday".equals(handleForDateType)){
		        			vt = Long.parseLong(DateUtil.format(DateUtil.parse(temp2Map.get(cloumn_createtime_name).toString()), "HHmmss"));
		        	    }else if("DynamicForWeek".equals(handleForDateType) || "DynamicForMonth".equals(handleForDateType) || "DynamicForYear".equals(handleForDateType) || "Custom".equals(handleForDateType)){
		        	    	vt = Long.parseLong(DateUtil.format(DateUtil.parse(temp2Map.get(cloumn_createtime_name).toString()), "yyyyMMddHHmmss"));
		        	    }
		        		//这边数据越界的原因是因为 比如说 vt=1006  newTimes[j] = 1000   newTimes[j+1] = 1006, 这时候 vt < newTimes[j+1] 条件不成立， 所以j就会+1
		        		for(int j = 0 ; j < newTimes.length ; j ++){
		        			//[20160411000000, 20160412000000, 20160413235959]   因为最后一位是 235959， 所以 13 号的数据也会跑到 中间那个数组去
		        			if(vt >= newTimes[j] && j == (newTimes.length - 1)){
		        				if("".equals(countNumForCloumnName))
		        					vals[j] ++;
				        		else
				        			vals[j] += Double.parseDouble(temp2Map.get(countNumForCloumnName).toString());
		        				break;
		        			}else if(vt >= newTimes[j] && vt <= newTimes[j+1] ){
		        				if("".equals(countNumForCloumnName))
		        					vals[j] ++;
				        		else
				        			vals[j] += Double.parseDouble(temp2Map.get(countNumForCloumnName).toString());
		        				break;
			        		}
		        		}
		        	}
		        	map.put("vals", vals);
		        	listVal.add(map);
	        	}
	        }
	        //有查到值需要判断，是不是所有列都有值， 没有的话， 需要把没值的列也给补上一个值
        	String[] cloumnTypeNames = null;
            if(result.get("cloumnTypeNames") != null){
            	cloumnTypeNames = result.get("cloumnTypeNames").toString().split(",");
            }
        	tempNames = tempNames.substring(0, tempNames.length()-1);
	        if(tempNames.split(",").length != cloumnTypeNames.length){
	            List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
	            for(String cloumnTypeName : cloumnTypeNames){
	            	Map<String,Object> valMap = null;
	            	for(Map<String,Object> tempMap : listVal){ 
	            		if(cloumnTypeName.equals(tempMap.get("title_name").toString())){
	            			valMap = tempMap;
	            			break;
	            		}
	            	}
	            	if(valMap == null){
	            		Map<String,Object> map = new HashMap<String, Object>();
	        	        map.put("title_name", cloumnTypeName);
	            		Integer[] vals = new Integer[times.length];
	        	        for(int ii = 0 ; ii < vals.length; ii++){
	        	            vals[ii] = 0 ;
	        	        }
	        	        map.put("vals", vals);
	        	        tempList.add(map);
	            	}else{
	            		tempList.add(valMap);
	            	}
	            }
	            listVal.clear();
	            listVal.addAll(tempList);
	        }
        }else{
        	String tempStr = "";
        	if(result.get("cloumnTypeNames") != null){
        		tempStr = result.get("cloumnTypeNames").toString();
        	}
//        	else{
//	        	Map<String,Object> param = new HashMap<String, Object>();
//	        	param.put("cloumn_type_name", cloumn_type_name);
//	        	param.put("table_name", result.get("table_name").toString());
//	        	List<Map<String,Object>> cloumnNamesList = this.queryCloumnName(param);
//	        	tempStr = cloumnNamesList.get(0).get("cloumnTypeNames").toString();
//        	}
        	String[] tempStrs = tempStr.split(",");
        	for(String str : tempStrs){
        		Map<String,Object> map = new HashMap<String, Object>();
	            map.put("title_name", str);
	            Integer[] vals = new Integer[times.length];
	            for(int ii = 0 ; ii < vals.length; ii++){
	            	vals[ii] = 0 ;
	            }
	            map.put("vals", vals);
	            listVal.add(map);
        	}
        }
         
        
        result.put("data", listVal);
	}
	
//	/**
//     * 通过时间数据构造相应的数据
//     * @param result
//     * @param dataList
//     */
//    public static void structureDataForHeartbeat(Map<String, Object> result, List<Map<String,Object>> dataList, String[] times) {
//    	String handleForDateType = result.get("handleForDateType").toString();
//    	String cloumn_type_name = result.get("cloumn_type_name").toString();					//必须条件
//    	String cloumn_createtime_name = result.get("cloumn_createtime_name").toString();		//必须条件
//    	if(dataList != null){
//    		
//    	}
//		//初始化时间数组
//    	Long[] newTimes = new Long[times.length];
//        int i = 0;
//        if("Heartbeat".equals(handleForDateType) || "DynamicForDay".equals(handleForDateType) || "DynamicForYesterday".equals(handleForDateType)){
//	        for(String time : times){
//	        	newTimes[i] = Long.parseLong(time.replaceAll(":","")); i ++;
//	        }
//        }else if("DynamicForWeek".equals(handleForDateType) || "DynamicForMonth".equals(handleForDateType) || "DynamicForYear".equals(handleForDateType) || "Custom".equals(handleForDateType)){
//        	for(String time : times){
//        		//正常 < 11 的条件是不会成立的
//        		if(time.length() < 11)
//        			newTimes[i] = Long.parseLong(time.replace(" ", "").replaceAll(":","").replaceAll("-", "") + "000000");
//        		else
//        			newTimes[i] = Long.parseLong(time.replace(" ", "").replaceAll(":","").replaceAll("-", ""));
//        		 i ++;
//        	}
//        }
//        List<Map<String,Object>> list = null;
//        if(dataList != null)
//        	list = dataList;
////        else
////        	list = this.queryAllList(result);
//        
//        ArrayAndMapUtil.sort(list, cloumn_type_name);
//        String tempName = "";
//        List<Map<String,Object>> listVal = new ArrayList<Map<String,Object>>();
//        String tempNames = "";
//        for(Map<String,Object> tempMap : list){
//        	List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
//        	if(!tempName.equals(tempMap.get(cloumn_type_name).toString())){
//        		tempName = tempMap.get(cloumn_type_name).toString();
//        		tempNames += tempName + ",";
//	        	for(Map<String,Object> tempMap2 : list){
//	        		if(tempMap2.get(cloumn_type_name).toString().equals(tempMap.get(cloumn_type_name).toString())){
//	        			tempList.add(tempMap2);
//	        		}
//	        	}
//	        	
//	        	Map<String,Object> map = new HashMap<String, Object>();
//	            map.put("title_name", tempList.get(0).get(cloumn_type_name));
//	            Integer[] vals = new Integer[times.length-1]; 
//	            //初始化为数组赋值
//	            for(int ii = 0 ; ii < vals.length; ii++){
//	            	vals[ii] = 0 ;
//	            }
//	            
//	        	for(Map<String,Object> temp2Map : tempList){
//	        		Long vt = 0l;
//	        		if("Heartbeat".equals(handleForDateType) || "DynamicForDay".equals(handleForDateType)|| "DynamicForYesterday".equals(handleForDateType)){
//	        			vt = Long.parseLong(DateUtil.format(DateUtil.parse(temp2Map.get(cloumn_createtime_name).toString()), "HHmmss"));
//	        	    }else if("DynamicForWeek".equals(handleForDateType) || "DynamicForMonth".equals(handleForDateType) || "DynamicForYear".equals(handleForDateType) || "Custom".equals(handleForDateType)){
//	        	    	vt = Long.parseLong(DateUtil.format(DateUtil.parse(temp2Map.get(cloumn_createtime_name).toString()), "yyyyMMddHHmmss"));
//	        	    }
//	        		//这边数据越界的原因是因为 比如说 vt=1006  newTimes[j] = 1000   newTimes[j+1] = 1006, 这时候 vt < newTimes[j+1] 条件不成立， 所以j就会+1
//	        		for(int j = 0 ; j < newTimes.length ; j ++){
//	        			if(vt >= newTimes[j] && vt <= newTimes[j+1] ){
//	        				vals[j] ++;
//	        				break;
//		        		}
//	        		}
//	        	}
//	        	map.put("vals", vals);
//	        	listVal.add(map);
//        	}
//        	
//        }
//        //if("addData".equals(ArrayAndMapUtil.ifNullReturnString(result, "doType"))){//}
//        //有查到值需要判断，是不是所有列都有值， 没有的话， 需要把没值的列也给补上一个值
//        if(list.size() > 0){
//        	String[] cloumnTypeNames = null;
//            if(result.get("cloumnTypeNames") != null){
//            	cloumnTypeNames = result.get("cloumnTypeNames").toString().split(",");
//            }
//        	tempNames = tempNames.substring(0, tempNames.length()-1);
//	        if(tempNames.split(",").length != cloumnTypeNames.length){
//	            List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
//	            for(String cloumnTypeName : cloumnTypeNames){
//	            	Map<String,Object> valMap = null;
//	            	for(Map<String,Object> tempMap : listVal){ 
//	            		if(cloumnTypeName.equals(tempMap.get("title_name").toString())){
//	            			valMap = tempMap;
//	            			break;
//	            		}
//	            	}
//	            	if(valMap == null){
//	            		Map<String,Object> map = new HashMap<String, Object>();
//	        	        map.put("title_name", cloumnTypeName);
//	            		Integer[] vals = new Integer[times.length-1];
//	        	        for(int ii = 0 ; ii < vals.length; ii++){
//	        	            vals[ii] = 0 ;
//	        	        }
//	        	        map.put("vals", vals);
//	        	        tempList.add(map);
//	            	}else{
//	            		tempList.add(valMap);
//	            	}
//	            }
//	            listVal.clear();
//	            listVal.addAll(tempList);
//	        }
//        }else if(list.size() == 0){
//        	String tempStr = "";
//        	if(result.get("cloumnTypeNames") != null){
//        		tempStr = result.get("cloumnTypeNames").toString();
//        	}
////        	else{
////	        	Map<String,Object> param = new HashMap<String, Object>();
////	        	param.put("cloumn_type_name", cloumn_type_name);
////	        	param.put("table_name", result.get("table_name").toString());
////	        	List<Map<String,Object>> cloumnNamesList = this.queryCloumnName(param);
////	        	tempStr = cloumnNamesList.get(0).get("cloumnTypeNames").toString();
////        	}
//        	String[] tempStrs = tempStr.split(",");
//        	for(String str : tempStrs){
//        		Map<String,Object> map = new HashMap<String, Object>();
//	            map.put("title_name", str);
//	            Integer[] vals = new Integer[times.length-1];
//	            for(int ii = 0 ; ii < vals.length; ii++){
//	            	vals[ii] = 0 ;
//	            }
//	            map.put("vals", vals);
//	            listVal.add(map);
//        	}
//        }
//         
//        
//        result.put("data", listVal);
//	}
	
}
