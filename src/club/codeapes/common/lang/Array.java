package club.codeapes.common.lang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 模拟JS的array功能
 * @Description 
 * @version 1.0 
 * @date 2015年5月9日 上午9:07:19
 */
public class Array {
	
	/**
	 * 定义变量
	 */
	/*
	 * 定义存储的变量
	 */
	private List<String> array = new ArrayList<String>();
	/*
	 * 定义默认的分隔符
	 */
	private String separator = ",";
	/*
	 * 设置默认日期格式化
	 */
	private String datePattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 设置需要转换的日期格式
	 * @param datePattern
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	/**
	 * 构造函数
	 */
	
	public Array(){}
	
	public Array(Object obj){
		push(obj);
	}
	
	public Array(Object... objs){
		if(objs != null){
			for(Object obj : objs){
				array.add(instanceOf(obj));
			}
		}
	}
	
	public String toString(String separator){
		if(separator!=null){
			this.separator = separator;
		}
		return join(separator);
	}
	
	public String toString(){
		this.separator = "";
		return join(separator);
	}
	
	/**
	 * 添加
	 */
	public void push(Object obj){
		array.add(instanceOf(obj));
	}
	/**
	 * 添加
	 */
	public void push(String... strs){
		StringBuffer sb = new StringBuffer();
		if(strs != null){
			for(String str : strs){
				sb.append(str);
			}
		}
		array.add(sb.toString());
	}
	/**
	 * 添加
	 */
	public void push(Object... objs){
		StringBuffer sb = new StringBuffer();
		if(objs != null){
			for(Object obj : objs){
				sb.append(instanceOf(obj));
			}
		}
		array.add(sb.toString());
	}
	/**
	 * 返回字符串
	 * @param separator	自定义分隔符
	 * @return
	 */
	public String join(String separator){
		this.separator = separator;
		return join();
	}
	
	public void clear(){
		array.clear();
	}
	/**
	 * 返回字符串
	 * @return
	 */
	public String join(){
		StringBuffer sb = new StringBuffer();
		for(String str : array){
			sb.append(this.separator);
			sb.append(str);
		}
		/* 设置截取索引 */
		int index = 0;
		if(this.separator != null && this.separator.length() > 0)index = this.separator.length();
		return sb.toString().length()>0?sb.toString().substring(index):"";
	}
	
	
	/**
	 * 实例化比较
	 * @param obj
	 * @return
	 */
	public String instanceOf(Object obj){
		if(obj != null){
			if(obj instanceof String){
				return obj.toString();
			}else if(obj instanceof Long){
				return obj.toString();
			}else if(obj instanceof Integer){
				return obj.toString();
			}else if(obj instanceof Float){
				return obj.toString();
			}else if(obj instanceof Double){
				return obj.toString();
			}else if(obj instanceof Boolean){
				return obj.toString();
			}else if(obj instanceof Date){
				/* 若为日期类型就默认转换 */
				return format((Date) obj);
			}else{
				System.out.println("Array Log : \""+obj.getClass().getName() +"\" not supported.");
				return "";
			}
		}else return "";
	}
	
	private String format(Date date){
		if(this.datePattern != null){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
			return simpleDateFormat.format(date);
		}
		return date.toString();
	}
	
	public static void main(String[] args) {
		Array array = new Array(333);
//		array.push("asdfasdf");
//		array.push(1);
//		array.push(new Date());
//		array.push(1.325);
//		array.push("<div class=\"class\">");
//		array.push("sfsdfsf");
//		array.push("</div>");
//		array.push(new ArrayList());
//		array.push("</div>");
		array.push("asdf","222","333","444");
		array.push("asdf",22,33,"444",new Date());
		System.out.println(array.join("/-/"));
	}
}
