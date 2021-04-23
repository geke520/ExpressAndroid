package club.codeapes.common.dbutils;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import club.codeapes.common.date.DateUtil;
import club.codeapes.common.lang.Array;

public class DbUtilsTool {

	private static Logger logger = Logger.getLogger(DbUtilsTool.class);
	
	/**
	 * 
	 * @param runner
	 * @param tableName
	 * @param column
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void insert(QueryRunner runner,String tableName,Map<String,Object> column)throws Exception{
		if(column == null || column.size() == 0){
			logger.error("[ "+DateUtil.getNow()+" ] 'colums' is null :(");
			throw new NullPointerException();
		}
		/* 定义变量 */
		Object[] params = new Object[column.size()];
		int count = 0;
		Array columns = new Array(),placeholder = new Array();
		/* 遍历map */
		for (Iterator ite = column.entrySet().iterator(); ite.hasNext();) {
			Map.Entry entry = (Map.Entry) ite.next();
			params[count] = entry.getValue();
			columns.push(entry.getKey());
			placeholder.push("?");
			count += 1;
		}
		/*  */
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ").append(tableName).append("(")
		.append(columns.join()).append(")values(").append(placeholder.join()).append(")");
		logger.debug("[ "+DateUtil.getNow()+" ] SQL:"+sql.toString());
		runner.update(sql.toString(), params);
	}
	
	@SuppressWarnings("rawtypes")
	public static void update(QueryRunner runner,String tableName,Map<String,Object> column,Map<String,Object> key){
		if((column == null || column.size() == 0) || (key == null || key.size() == 0)){
			logger.error("[ "+DateUtil.getNow()+" ] 'colums' or 'key' is null :(");
			throw new NullPointerException();
		}
		/* 定义变量 */
		Object[] params = new Object[column.size()+key.size()];
		int count = 0;
		Array columns = new Array(),keys = new Array();
		/* 遍历map */
		for (Iterator ite = column.entrySet().iterator(); ite.hasNext();) {
			Map.Entry entry = (Map.Entry) ite.next();
			params[count] = entry.getValue();
			columns.push(entry.getKey()," = ?");
			count += 1;
		}
		for (Iterator ite = key.entrySet().iterator(); ite.hasNext();) {
			Map.Entry entry = (Map.Entry) ite.next();
			params[count] = entry.getValue();
			keys.push(entry.getKey()," = ?");
			count += 1;
		}
		/*  */
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(tableName).append(" set ")
		.append(columns.join()).append(" where ").append(keys.join(" and "));
		logger.debug("[ "+DateUtil.getNow()+" ] SQL:"+sql.toString());
		
	}
	
	
	public static void main(String[] args) {
		System.out.println("0003fd76-0a60-11e5-b3c3-28513207492f".length());
	}
}
