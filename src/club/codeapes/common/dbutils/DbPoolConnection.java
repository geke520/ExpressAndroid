package club.codeapes.common.dbutils;

import club.codeapes.common.file.PropertyUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DbPoolConnection {

	/**
	 * 定义静态变量
	 */
	private static DbPoolConnection databasePool = null;
	private static DruidDataSource dds = null;
	/**
	 * 只执行一次的代码区块
	 */
	static {
		Properties properties = loadPropertyFile("jdbc.properties");
		try {
			dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 默认构造函数
	 */
	private DbPoolConnection() {}

	/**
	 * 
	 * @return
	 */
	public static synchronized DbPoolConnection getInstance() {
		if (null == databasePool) {
			databasePool = new DbPoolConnection();
		}
		return databasePool;
	}

	public DruidDataSource getDataSource() throws SQLException {
		return dds;
	}

	public DruidPooledConnection getConnection() throws SQLException {
		return dds.getConnection();
	}

	/**
	 * 加载数据库连接池配置信息
	 * @param fileName
	 * @return
	 */
	public static Properties loadPropertyFile(String fileName) {
		String path = null;
		/* 配置文件名 */
		if (null == fileName || fileName.equals(""))
			throw new IllegalArgumentException("Properties file path can not be null : " + fileName);
		path = DbPoolConnection.class.getClassLoader().getResource("\\").getPath();
		path = new File(path).getParent();
		Properties p = null;
		try {
			/* 全路径 */
			String fullPath = path + File.separator + fileName;
			PropertyUtil pu = new PropertyUtil(fullPath);

			p = pu.getProperties();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found: "+ fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Properties file can not be loading: " + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
}