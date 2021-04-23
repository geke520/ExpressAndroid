package club.codeapes.common.api.ipip;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import club.codeapes.common.lang.Array;
import club.codeapes.common.web.RequestUtil;

public class IPUtil {

	/**
	 * 文件路径
	 */
	private String filepath;
	
	private int mode = 1;
	
	public IPUtil(){
		this.filepath = getWebRoot()+"WEB-INF"+File.separator+"plugins"+File.separator+"ipip"+File.separator+"17monipdb.datx";
		load();
	}
	/**
	 * 0 dat
	 * 1 datx
	 * @param mode
	 */
	public IPUtil(int mode){
		this.mode = mode;
		load();
	}
	
	public IPUtil(String filepath){
		this.filepath = filepath;
		load();
	}
	
	public IPUtil(String filepath,int mode){
		this.filepath = filepath;
		this.mode = (mode > 0 ? mode >1?1:mode :0);
		load();
	}
	
	public void load(){
		if(mode == 1){
			IPIPnetExt.load(filepath);
		}else{
			IPIPnet.load(filepath);
		}
	}
	
	public String[] find(String ip){
		if(mode == 1){
			return IPIPnetExt.find(ip);
		}else{
			return IPIPnet.find(ip);
		}
	}
	/**
	 * 获得原始IPIP.net的数组数据，若有异常返回空
	 * @param ip
	 * @return
	 */
	public String[] getIpArray(String ip){
		String[] arr = null;
		try{
			arr = find(ip);
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr;
	}
	
	/**
	 * 获得信息 xxx,xxx
	 * @param ip
	 * @return
	 */
	public String getIpString(String ip){
		return getIpString(ip, ",");
	}
	
	/**
	 * 
	 * @param ip
	 * @param separator 分隔符
	 * @return
	 */
	public String getIpString(String ip,String separator){
		Array array = new Array();
		String[] arr = getIpArray(ip);
		if(arr!=null){
			for(String a : arr){
				if(a.length()>0){
					array.push(a);
				}
			}
		}
		return array.join("").toString().length()>0?array.join(separator==null?",":separator):null;
		
	}
	/**
	 * 获得ip信息map对象
	 * "country",     				国家
		"province",					省会或直辖市
		"area,city",				地区或城市（逗号表示两个key都可以取）
		"school,organization",		学校或单位
		"operators",				运营商字段
		"latitude",					纬度
		"longitude",				经度
		"timezone",					时区一
		"timezone2",				时区二
		"region_code",				中国行政区划代码
		"telephone_code",			国际电话代码
		"country_code",				国家二位代码
		"continent"					世界大洲代码
	 * 
	 * @param ip
	 * @return
	 */
	public Map<String,Object> getIpMap(String ip){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ip", ip);
		String[] arr = getIpArray(ip);
		/**
		 * 国家，省会或直辖市，地区或城市 ，学校或单位 ，运营商字段，纬度，经度，时区一，时区二，中国行政区划代码，国际电话代码，国家二位代码，世界大洲代码
		 */
		String[] mapper = {
				"country",
				"province",
				"area,city",
				"school,organization",
				"operators",
				"latitude",
				"longitude",
				"timezone",
				"timezone2",
				"region_code",
				"telephone_code",
				"country_code",
				"continent"};
		
		if(arr!=null){
			for(int i=0,ln=arr.length;i<ln;i++){
				String[] _mapper = mapper[i].split(",");
				if(arr[i].length()>0){
					for(String m : _mapper){
						map.put(m, arr[i]);
					}
				}
			}
		}
		return map;
	}
	
	public String getIpJSONString(String ip){
		return JSON.toJSONString(getIpMap(ip));
	}
	
	public static void main(String[] args) {
		IPUtil ip = new IPUtil();
		for(String s : ip.getIpArray("218.5.2.179")){
			System.out.println(s);
		}
		System.out.println(ip.getIpString("61.154.39.225"));
		System.out.println(ip.getIpMap("61.154.39.225"));
		System.out.println(ip.getIpJSONString("61.154.39.225"));
	}
	
	
	public String getWebRoot() {
		URL url = RequestUtil.class.getResource(RequestUtil.class.getSimpleName()
				+ ".class");
		String str = url.getPath();
		/* 这个判断是用来若是打成jar包的情况下需要截取 */
		if(str.indexOf("file:") == 0){
			str = str.substring(5);
		}
		if (str.indexOf(":") != -1) {
			str = str.substring(1, str.lastIndexOf("WEB-INF"));// 在windows下面为/F:/MyPrj/WORK/post/
		} else {
			str = str.substring(0, str.lastIndexOf("WEB-INF"));// 在Linux下面为/usr/local/apache.../
		}
		return str;
	}
}
