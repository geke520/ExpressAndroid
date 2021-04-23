package club.codeapes.common.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.codeapes.common.date.DateUtil;

/**
 * 月表构造工具类
 * 
 * @author JuNks7
 *
 */
public class MonthTableUtil {
	/**
	 * 构造union参数
	 * 
	 * @param params
	 */
	public static void buildUnionParams(Map<String, Object> params,
			String start_date_mask, String end_date_mask) {
		buildUnionParams(params,start_date_mask,end_date_mask,"2015-01-01");
	}
	
	
	public static void buildUnionParams(Map<String, Object> params,String min_date) {
		buildUnionParams(params,"start_date","end_date",min_date);
	}
	
	/**
	 * 
	 * @Title: buildUnionParams 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param params			参数
	 * @param start_date_mask	开始日期标识
	 * @param end_date_mask		结束日期标识
	 * @param min_date			最小日期
	 */
	public static void buildUnionParams(Map<String, Object> params,
			String start_date_mask, String end_date_mask,String min_date) {
		/* 获得开始时间 */
		String start_date = params.get(start_date_mask) == null ? min_date
				: params.get(start_date_mask).toString();
		String end_date = params.get(end_date_mask) == null ? DateUtil
				.getNow("yyyy-MM-dd") : params.get(end_date_mask).toString();
		/* 判断开始和结束时间是否超出限制 */
		if (Integer.parseInt(start_date.replace("-", "")) < Integer
				.parseInt(min_date.replace("-", ""))) {
			start_date = min_date;
		}
		if (Integer.parseInt(end_date.replace("-", "")) < Integer
				.parseInt(min_date.replace("-", ""))) {
			end_date = min_date;
		}
		if (Integer.parseInt(end_date.replace("-", "")) > Integer
				.parseInt(DateUtil.getNow("yyyyMMdd"))) {
			end_date = DateUtil.getNow("yyyy-MM-dd");
		}
		/* 获得两个日期的月份间隔 */
		int between = DateUtil.getBetweenMonthsCount(
				DateUtil.parse(start_date, "yyyy-MM-dd"),
				DateUtil.parse(end_date, "yyyy-MM-dd"));

		List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();
		Map<String, Object> table = null;
		for (int i = 0; i <= between; i++) {
			table = new HashMap<String, Object>();
			String td = DateUtil.format(DateUtil.addMonths(
					DateUtil.parse(start_date, "yyyy-MM-dd"), i), "yyyy-MM");
			/* 设置表名 */
			table.put("date", td.replace("-", ""));
			/* 添加开始时间和结束时间的查询 */
			if (i == 0 && start_date.substring(0, 7).equals(td)) {
				table.put(start_date_mask, start_date + " 00:00:00");
			}
			if (i == between && end_date.substring(0, 7).equals(td)) {
				table.put(end_date_mask, end_date + " 23:59:59");
			}
			tables.add(table);
		}
		params.put("tables", tables);
	}

	public static void buildUnionParams(Map<String, Object> params) {
		MonthTableUtil.buildUnionParams(params, "start_date", "end_date");
	}

}
