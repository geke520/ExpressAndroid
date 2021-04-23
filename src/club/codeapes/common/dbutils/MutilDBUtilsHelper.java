package club.codeapes.common.dbutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.alibaba.druid.pool.DruidDataSource;

public class MutilDBUtilsHelper {

	private Map<String,DruidDataSource> ddss = null;
	
	private Map<String,QueryRunner> qrs = new HashMap<String,QueryRunner>();
	
	public MutilDBUtilsHelper() {
		this.ddss = MutilDbPoolConnection.getInstance().getDataSourceMap();
	}
	
	public QueryRunner getRunner(){
		return getRunner("default");
	}
	
	public QueryRunner getRunner(String key){
		if(qrs.get(key) == null){
			qrs.put(key, new QueryRunner(this.ddss.get(key)));
		}
		return qrs.get(key);
	}
}
