package club.codeapes.web.core.el;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class CodeapesTag {

	/**
	 * 判断list string集合中是否存在需要判断的字符串对象（忽略大小写）
	 * @param keys
	 * @param key
	 * @return
	 */
	public static boolean containsIgnoreCase(String[] keys,String key){
		boolean bl = false;
		if(keys != null && keys.length > 0 && key != null && key.length() >0){
			key = key.toLowerCase().trim();
			if(key.length()>0)
				for(String k : keys){
					if(k.toLowerCase().trim().equals(key)){
						bl = true;
						break;
					}
				}
		}
		return bl;
	}
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateFormat(Date date,String pattern){
		String rs = "";
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat(pattern);
            rs = df.format(date);
		}
		return rs;
	}
	/**
	 * 依赖 Apache commons-lang
	 * @param html
	 * @return
	 */
	public static String escapeHtml(String html){
		return StringEscapeUtils.escapeHtml(html);
	}
	
	public static String toString(Object obj){
		return obj!=null?obj.toString():"";
	}
}
