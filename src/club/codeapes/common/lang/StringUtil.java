package club.codeapes.common.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ObjectUtils;

import com.alibaba.fastjson.JSON;

public class StringUtil {
	
	public static String ifNullReturnString(Map<String,Object> map, String paramName){
		if(map != null){
			if(map.get(paramName) != null)
				return map.get(paramName).toString();
			else
				return "";
		}else
			return "";
	}

	/**
	 * 首字母变小写
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) ? true : false;
	}

	/**
	 * 字符串不为 null 而且不为 "" 时返回 true
	 */
	public static boolean notBlank(String str) {
		return str == null || "".equals(str.trim()) ? false : true;
	}

	public static boolean notBlank(String... strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if (str == null || "".equals(str.trim()))
				return false;
		return true;
	}

	public static boolean notNull(Object... paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
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

	/**
	 * 去除左右两边空格
	 * 
	 * @param o
	 * @return
	 */
	public static String trims(Object o) {
		String res = ObjectUtils.toString(o);
		if ("".equals(res)) {
			return res;
		}

		return org.apache.commons.lang.StringUtils.trimToEmpty(res);
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

	/**
	 * @Title: 截取括号内的内容
	 * @MethodName: getMarksByBrackets
	 * @Description:
	 * @param @param source
	 * @param @param type big[{],middle[\\[],small[\\(]
	 * @param @return
	 * @return List<String>
	 * @throws
	 */
	public static List<String> getMarksByBrackets(String source, String type) {
		if (type == null)
			type = "big";
		String[] arr = null;
		String endMark = "[}]";
		if (source != null) {
			if ("big".equals(type)) {
				arr = source.split("[{]");
			} else if ("middle".equals(type)) {
				arr = source.split("[\\[]");
				endMark = "[\\]]";
			} else if ("small".equals(type)) {
				arr = source.split("[\\(]");
				endMark = "[\\)]";
			}
			if (arr != null) {
				List<String> rs = new ArrayList<String>();
				for (int i = 0, ln = arr.length; i < ln; i++) {
					String[] _arr = arr[i].split(endMark);
					if (_arr.length == 2) {
						rs.add(_arr[0]);
					} else {
						String mark = "}";
						if ("middle".equals(type)) {
							mark = "]";
						} else if ("small".equals(type)) {
							mark = ")";
						}
						if (arr[i].indexOf(mark) != -1)
							rs.add(_arr[0]);
					}
				}
				return rs;
			}
		}
		return null;
	}

	/**
	 * 把指定字符串转换为map，适用的解析字符串模板如下： dvc=192.168.118.93
	 * deviceMacAddress=00:E0:66:DC:44:74 dvchost=localhost
	 * deviceExternalId=9987D329A467-4749943D-C29F-3FB6-6BE3 rt=Jun 08 2015
	 * 16:28:28 GMT+08:00 app=DNS Response deviceDirection=0 dhost=dd-pc
	 * dst=192.168.119.94 dpt=50384 dmac=14:DA:E9:40:23:D4 shost=fj-dns.fz.fj.cn
	 * src=218.85.157.99 spt=53 smac=28:51:32:07:F1:10 cs3Label=HostName_Ext
	 * cs3=s9.cngba.com fileType=0 fsize=0 requestClientApplication=7?01 act=not
	 * blocked cn3Label=Threat Type cn3=2
	 * destinationTranslatedAddress=218.85.157.99
	 * fileHash=0000000000000000000000000000000000000000
	 * sourceTranslatedAddress=192.168.119.94 cnt=2
	 * cs5Label=CCCA_DetectionSource cs5=VIRTUAL_ANALYZER
	 * cn1Label=CCCA_Detection cn1=1
	 * 
	 * @param map
	 * @param source
	 */
	public static void resolver2map(Map<String, String> map, String source) {
		/* 获得最后一个等号的位置 */
		int dh_index = source.lastIndexOf("=");
		/* 截取字符串，从开始到等号的位置 */
		String temp = source.substring(0, dh_index);
		/* 获取截取后的字符串最后一个空格的位置 */
		int kg_index = temp.lastIndexOf(" ");
		/* 索引位置判断 */
		if (kg_index != -1) {
			String t = source.substring(kg_index + 1);
			String[] array = t.split("=");
			map.put(array[0], array.length == 2 ? array[1] : null);
			resolver2map(map, source.substring(0, kg_index));
		} else {
			String[] array = source.split("=");
			map.put(array[0], array.length == 2 ? array[1] : null);
		}
	}

	/**
	 * 把指定字符串转换为map，适用的解析字符串模板如下： dvc=192.168.118.93
	 * deviceMacAddress=00:E0:66:DC:44:74 dvchost=localhost
	 * deviceExternalId=9987D329A467-4749943D-C29F-3FB6-6BE3 rt=Jun 08 2015
	 * 16:28:28 GMT+08:00 app=DNS Response deviceDirection=0 dhost=dd-pc
	 * dst=192.168.119.94 dpt=50384 dmac=14:DA:E9:40:23:D4 shost=fj-dns.fz.fj.cn
	 * src=218.85.157.99 spt=53 smac=28:51:32:07:F1:10 cs3Label=HostName_Ext
	 * cs3=s9.cngba.com fileType=0 fsize=0 requestClientApplication=7?01 act=not
	 * blocked cn3Label=Threat Type cn3=2
	 * destinationTranslatedAddress=218.85.157.99
	 * fileHash=0000000000000000000000000000000000000000
	 * sourceTranslatedAddress=192.168.119.94 cnt=2
	 * cs5Label=CCCA_DetectionSource cs5=VIRTUAL_ANALYZER
	 * cn1Label=CCCA_Detection cn1=1
	 * 
	 * @param map
	 * @param source
	 */
	public static void resolver2mapObject(Map<String, Object> map, String source) {
		/* 获得最后一个等号的位置 */
		int dh_index = source.lastIndexOf("=");
		/* 截取字符串，从开始到等号的位置 */
		String temp = source.substring(0, dh_index);
		/* 获取截取后的字符串最后一个空格的位置 */
		int kg_index = temp.lastIndexOf(" ");
		/* 索引位置判断 */
		if (kg_index != -1) {
			String t = source.substring(kg_index + 1);
			String[] array = t.split("=");
			map.put(array[0], array.length == 2 ? array[1] : null);
			resolver2mapObject(map, source.substring(0, kg_index));
		} else {
			String[] array = source.split("=");
			map.put(array[0], array.length == 2 ? array[1] : null);
		}
	}

	/**
	 * 把指定字符串转换为list(倒序)，适用的解析字符串模板如下： dvc=192.168.118.93
	 * deviceMacAddress=00:E0:66:DC:44:74 dvchost=localhost
	 * deviceExternalId=9987D329A467-4749943D-C29F-3FB6-6BE3 rt=Jun 08 2015
	 * 16:28:28 GMT+08:00 app=DNS Response deviceDirection=0 dhost=dd-pc
	 * dst=192.168.119.94 dpt=50384 dmac=14:DA:E9:40:23:D4 shost=fj-dns.fz.fj.cn
	 * src=218.85.157.99 spt=53 smac=28:51:32:07:F1:10 cs3Label=HostName_Ext
	 * cs3=s9.cngba.com fileType=0 fsize=0 requestClientApplication=7?01 act=not
	 * blocked cn3Label=Threat Type cn3=2
	 * destinationTranslatedAddress=218.85.157.99
	 * fileHash=0000000000000000000000000000000000000000
	 * sourceTranslatedAddress=192.168.119.94 cnt=2
	 * cs5Label=CCCA_DetectionSource cs5=VIRTUAL_ANALYZER
	 * cn1Label=CCCA_Detection cn1=1
	 * 
	 * @param list
	 * @param source
	 */
	public static void resolver2list(List<String> list, String source) {
		/* 获得最后一个等号的位置 */
		int dh_index = source.lastIndexOf("=");
		/* 截取字符串，从开始到等号的位置 */
		String temp = source.substring(0, dh_index);
		/* 获取截取后的字符串最后一个空格的位置 */
		int kg_index = temp.lastIndexOf(" ");
		/* 索引位置判断 */
		if (kg_index != -1) {
			String t = source.substring(kg_index + 1);
			list.add(t);
			resolver2list(list, source.substring(0, kg_index));
		} else {
			list.add(source);
		}
	}

	public static void main(String[] args) {
		String str = "rt=Jul 01 2015 11:08:57 GMT+08:00 dvc=192.168.118.98 dvchost=NDASEC deviceMacAddress=00:0A:F7:79:9C:D4 deviceExternalId=DAE39EFC915B-4D9C9BAF-7210-F1EB-A623 fname=VPN.exe fileHash=EBD85105A47B7F3DFCB98E2CEAD1EC9B55A20E05 deviceProcessHash=AD673C680DE2FAA4303AA2383892139CB6BC6D5C fileType=UPX EXE fsize=1541632 cs1Label=SandboxImageType cs1=WindowsXP cn1Label=GRIDIsKnownGood cn1=-1 cn2Label=ROZRating cn2=2 cs2Label=MalwareName cs2=VAN_MALWARE.UMXX cn3Label=PcapReady cn3=1";
		Map<String, Object> map = new HashMap<String, Object>();
		resolver2mapObject(map, str);
		System.out.println(JSON.toJSONString(map));
		System.out.println("EBD85105A47B7F3DFCB98E2CEAD1EC9B55A20E05".length());
	}

	public static boolean isIP(String addr) {
		if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();
		return ipAddress;
	}

	public static int getLength(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	public static int getLengthByRegex(String s) {
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

}
