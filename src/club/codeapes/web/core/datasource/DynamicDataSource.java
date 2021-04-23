package club.codeapes.web.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 *	@Title:			数据源路由
 *	@File:			DynamicDataSource.java 
 *	@Package 		club.codeapes.web.base.datasource 
 *	@Description: 	
 *	@author 		JuNks.7 < 77923857@qq.com >
 *	@date 			2020年12月4日 下午4:23:23
 *	@version 		V1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceSwitch.getDataSourceType();
	}

}
