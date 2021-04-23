package club.codeapes.common.lang;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * 工具类
 * @author JunKs_7
 *
 */
public class ArrayAndMapUtil extends StringUtils {
	
	public static boolean isExistValByArray(String[] tempVals,String tempVal){
		if(tempVals != null && tempVals.length > 0){
			for(String temp : tempVals){
				if(temp.equals(tempVal)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static String ifNullReturnString(Map<String,Object> map, String paramName){
		if(map != null){
			if(map.get(paramName) != null)
				return map.get(paramName).toString();
			else
				return "";
		}else
			return "";
	}
	
	@SuppressWarnings("rawtypes")
	public static void sort(List<Map<String, Object>> data, String orderCloumn) {
		final String cloumnName = orderCloumn;
		Collections.sort(data, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {

				String a = (String) o1.get(cloumnName);
				String b = (String) o2.get(cloumnName);

				// 升序
				return a.compareTo(b);

				// 降序
				// return b.compareTo(a);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	public static void sort(List<Map<String, Object>> data, String orderCloumn,boolean isDesc) {
		final String cloumnName = orderCloumn;
		Collections.sort(data, new Comparator<Map>() {
			public int compare(Map o1, Map o2) {

				String a = (String) o1.get(cloumnName);
				String b = (String) o2.get(cloumnName);

				if(isDesc)
					return b.compareTo(a);	// 降序
				else
					return a.compareTo(b);	// 升序
			}
		});
	}

	public static boolean isNotNull(Object o) {
		if (o != null) {
			if (o.toString().length() > 0) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	public static boolean isNull(Object o) {
		if (o != null) {
			if (o.toString().length() > 0) {
				return false;
			} else
				return true;
		} else
			return true;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T copy(T source) throws Exception {
		T desc = null;
		if (desc instanceof List) {
			desc = (T) copyList((List) source);
		} else if (desc instanceof Set) {
			desc = (T) copySet((Set) source);
		} else if (desc instanceof Map) {
			desc = (T) copyMap((Map) source);
		} else {
			desc = (T) copyObject(source);
		}
		return desc;
	}

	@SuppressWarnings({ "rawtypes" })
	private static List<Object> copyList(List source) throws Exception {
		List<Object> desc = null;
		if (source instanceof ArrayList) {
			desc = new ArrayList<Object>(source.size());
		} else {
			desc = new LinkedList<Object>();
		}
		for (Object obj : source) {
			desc.add(copy(obj));
		}
		return desc;
	}

	@SuppressWarnings({ "rawtypes" })
	private static Set<Object> copySet(Set source) throws Exception {
		Set<Object> desc = null;
		if (source instanceof HashSet) {
			desc = new HashSet<Object>(source.size());
		} else {
			desc = new LinkedHashSet<Object>(source.size());
		}
		for (Object obj : source) {
			desc.add(copy(obj));
		}
		return desc;
	}

	@SuppressWarnings({ "rawtypes" })
	private static Map<Object, Object> copyMap(Map source) throws Exception {
		Map<Object, Object> desc = null;
		if (source instanceof LinkedHashMap) {
			desc = new LinkedHashMap<Object, Object>(source.size());
		} else {
			desc = new HashMap<Object, Object>(source.size());
		}
		for (Object entry : source.entrySet()) {
			Object key = ((Map.Entry) entry).getKey();
			Object value = ((Map.Entry) entry).getValue();
			if (value != null) {
				value = copyObject(value);
			}
			desc.put(key, value);
		}
		return desc;
	}

	@SuppressWarnings("rawtypes")
	private static Object copyObject(Object value) throws Exception {
		if (value instanceof Map) {
			value = copyMap((Map) value);
		} else if (value.getClass().isPrimitive()) {
		} else if (value instanceof Number) {
		} else if (value instanceof String) {
		} else if (value instanceof BigDecimal) {
		} else if (value instanceof Calendar) {
		} else if (value instanceof Character) {
			value = ((Calendar) value).clone();
		} else if (value instanceof java.util.Date) {
			value = ((java.util.Date) value).clone();
		} else if (value instanceof List) {
			value = copyList((List) value);
		} else {
			throw new Exception("不支持的类型" + value.getClass() + "的对象拷贝");
		}
		return value;
	}
	
	//去除数组中重复的记录  
		public static String[] array_unique(String[] a) {  
		    // array_unique  
		    List<String> list = new LinkedList<String>();  
		    for(int i = 0; i < a.length; i++) {  
		        if(!list.contains(a[i])) {  
		            list.add(a[i]);  
		        }  
		    }  
		    return (String[])list.toArray(new String[list.size()]);  
		} 

}
