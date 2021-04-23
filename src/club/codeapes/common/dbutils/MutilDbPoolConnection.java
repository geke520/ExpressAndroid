package club.codeapes.common.dbutils;

import club.codeapes.common.file.PropertyUtil;
import club.codeapes.common.path.PathUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源连接池
 *
 */
public class MutilDbPoolConnection {

	private static Logger logger = Logger.getLogger(MutilDbPoolConnection.class); 
	/**
	 * 
	 */
	private static MutilDbPoolConnection mdc = null;
	
	private static Map<String,DruidDataSource> ddss = null;
	
	static{
		/* 获得路径 */
		String path = new File(PathUtil.getPath()).getParent();
		/* 获得jdbc配置文件列表 */
		File[] jdbcs = new File(path+File.separator+"jdbc").listFiles();
		/* 判断是否存在配置文件 */
		if(jdbcs == null){
			logger.error("Not found 'jdbc' config files :(");
			logger.info("Automatic program exit :)");
			System.exit(0);
		}
		/* 加载配置文件 */
		for(File jdbc : jdbcs){
			try {
				PropertyUtil pu = new PropertyUtil(jdbc);
				if(pu.getValue("key") == null){
					logger.error("Load jdbc file '"+jdbc.getName()+"' success , but missing 'key' attribute :(");
					logger.info("Automatic program exit :)");
					System.exit(0);
				}
				if(ddss == null) ddss = new HashMap<String,DruidDataSource>();
				try {
					/* 判断密码是否有加密 */

					/* 创建数据源 */
					ddss.put(pu.getValue("key"), (DruidDataSource) DruidDataSourceFactory.createDataSource(pu.getProperties()));
				} catch (Exception e) {
					logger.error("Create '"+pu.getValue("key")+"' druid datasource fail :(");
					logger.info("Automatic program exit :)");
					System.exit(0);
				}
			} catch (IOException e) {
				logger.error("Load jdbc file '"+jdbc.getName()+"' fail :(");
				logger.info("Automatic program exit :)");
				System.exit(0);
			}
		}
	}
	/**
	 * 获得数据源map对象
	 * @return
	 */
	public Map<String,DruidDataSource> getDataSourceMap(){
		return ddss;
	}
	
	/**
	 * 获得默认数据源，关键字为 default
	 * @return
	 * @throws SQLException
	 */
	public DruidDataSource getDataSource() throws SQLException {
		return ddss.get("default");
	}
	/**
	 * 获取指定关键字的数据源
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public DruidDataSource getDataSource(String key) throws SQLException {
		return ddss.get(key);
	}
	/**
	 * 获得默认的 Connection
	 * @return
	 * @throws SQLException
	 */
	public DruidPooledConnection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
	/**
	 * 获得指定key的 Connection
	 * @return
	 * @throws SQLException
	 */
	public DruidPooledConnection getConnection(String key) throws SQLException {
		return getDataSource(key).getConnection();
	}
	
	private MutilDbPoolConnection() {}
	/**
	 * 
	 * @return
	 */
	public static synchronized MutilDbPoolConnection getInstance() {
		if (null == mdc) {
			mdc = new MutilDbPoolConnection();
		}
		return mdc;
	}
}
