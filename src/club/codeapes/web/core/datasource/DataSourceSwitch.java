package club.codeapes.web.core.datasource;
/**
 *	@Title:			数据源选择器
 *	@File:			DataSourceSwitch.java 
 *	@Package 		club.codeapes.web.base.datasource 
 *	@Description: 	
 *	@author 		JuNks.7 < 77923857@qq.com >
 *	@date 			2020年12月4日 下午4:21:34
 *	@version 		V1.0
 */
public class DataSourceSwitch {
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal();
	@SuppressWarnings("unchecked")
	public static void setDataSourceType(String dataSourceType) {
		contextHolder.set(dataSourceType);
	}
	public static String getDataSourceType() {
		return (String) contextHolder.get();
	}
	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}
