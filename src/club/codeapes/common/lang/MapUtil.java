package club.codeapes.common.lang;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {

	private Map<String,Object> map = null;
	
	public MapUtil(Map<String,Object> map){
		this.map = map;
	}
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key){
		if(map == null)return null;
		if(map.get(key) == null)return null;
		try{
			return Long.parseLong(map.get(key).toString());
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public long getlong(String key){
		if(map == null)return 0;
		if(map.get(key) == null)return 0;
		try{
			return Long.parseLong(map.get(key).toString());
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key){
		if(map == null)return null;
		if(map.get(key) == null)return null;
		try{
			return Integer.parseInt(map.get(key).toString());
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key){
		if(map == null)return 0;
		if(map.get(key) == null)return 0;
		try{
			return Integer.parseInt(map.get(key).toString());
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key){
		if(map == null)return null;
		if(map.get(key) == null)return null;
		try{
			return map.get(key).toString();
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key){
		if(map == null)return null;
		if(map.get(key) == null)return null;
		try{
			return Boolean.parseBoolean(map.get(key).toString());
		}catch(Exception e){
			return null;
		}
	}
	
	public static Map<String,Object> getSingle(String key,Object value){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(key, value);
		return map;
	}
	
	public static Map<String,String> getSingle(String key,String value){
		Map<String,String> map = new HashMap<String,String>();
		map.put(key, value);
		return map;
	}
	
	public static Map<Integer,Object> getSingle(Integer key,Object value){
		Map<Integer,Object> map = new HashMap<Integer,Object>();
		map.put(key, value);
		return map;
	}
	
	public static Map<Integer,String> getSingle(Integer key,String value){
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(key, value);
		return map;
	}
	
}
