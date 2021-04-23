package club.codeapes.common.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import club.codeapes.common.lang.Array;

import com.alibaba.fastjson.JSON;

public class IPUtil {


	/**
	 * 通过淘宝接口获得ip所属的地址
	 * 
	 * @param ip
	 * @return {data={region=福建省, area_id=300000, country_id=CN, isp=电信,
	 *         region_id=350000, country=中国, city=福州市, isp_id=100017,
	 *         ip=61.154.39.225, city_id=350100, area=华东, county=台江区,
	 *         county_id=350103}, mess=通信成功, code=0, responeCode=200}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> getIpByTaobao(String ip) {
		Map<String, Object> result = new HashMap<String, Object>();
		String urlLink = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		URL url;
		try {
			url = new URL(urlLink);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String sCurrentLine = "";
				String sTotalString = "";
				while ((sCurrentLine = bufferedReader.readLine()) != null) {
					sTotalString += sCurrentLine;
				}
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("data", Map.class);
				result = JSON.parseObject(sTotalString, Map.class);
			} else {
				System.out.println("通信失败");
			}
			result.put("responeCode", responseCode);
			// result.put("mess", "通信成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 1);
			result.put("data", "通信异常");
		}
		return result;
	}
	/**
	 * 通过IP获得经纬度
	 * @Title: getPointByBaidu 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param ip
	 * @param key
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getCoordinateByBaidu(String ip,String key){
		//http://api.map.baidu.com/location/ip?ak=MvS3HjerDs8bCCx8850oBax7&ip=202.198.16.3&coor=bd09ll
		Map<String, Object> result = new HashMap<String, Object>();
		Array urlLink = new Array("http://api.map.baidu.com/location/ip?","ak=",key,"&ip=",ip,"&coor=bd09ll");
		URL url;
		try {
			url = new URL(urlLink.join(""));
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String sCurrentLine = "";
				String sTotalString = "";
				while ((sCurrentLine = bufferedReader.readLine()) != null) {
					sTotalString += sCurrentLine;
				}
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("data", Map.class);
				result = JSON.parseObject(sTotalString, Map.class);
				if(result.get("status").toString().equals("0")){
					Array point = new Array();
					Map<String,Object> content = (Map<String, Object>) result.get("content");
					content = (Map<String, Object>) content.get("point");
					point.push(content.get("x"));
					point.push(content.get("y"));
					return point.join();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String,Object> getIpByBaidu(String ip,String key){
		//http://api.map.baidu.com/location/ip?ak=MvS3HjerDs8bCCx8850oBax7&ip=202.198.16.3&coor=bd09ll
		Map<String, Object> result = new HashMap<String, Object>();
		Array urlLink = new Array("http://api.map.baidu.com/location/ip?","ak=",key,"&ip=",ip,"&coor=bd09ll");
		URL url;
		try {
			url = new URL(urlLink.join(""));
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream urlStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(urlStream));
				String sCurrentLine = "";
				String sTotalString = "";
				while ((sCurrentLine = bufferedReader.readLine()) != null) {
					sTotalString += sCurrentLine;
				}
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("data", Map.class);
				result = JSON.parseObject(sTotalString, Map.class);
			} else {
				System.out.println("通信失败");
			}
			result.put("responeCode", responseCode);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", 1);
			result.put("message", "通信异常");
		}
		return result;
	}
	
	public static void main(String[] args) {
		//{"address":"CN|\u798f\u5efa|\u798f\u5dde|None|CHINANET|0|0","content":{"address":"\u798f\u5efa\u7701\u798f\u5dde\u5e02","address_detail":{"city":"\u798f\u5dde\u5e02","city_code":300,"district":"","province":"\u798f\u5efa\u7701","street":"","street_number":""},"point":{"x":"119.33022111","y":"26.04712550"}},"status":0}
		//{"status":2,"message":"Request Parameter Error:ip illegal"}
		//{"status":200,"message":"APP不存在，AK有误请检查再重试"}
		//{"status":1,"message":"Internal Service Error:ip[127.0.0.1] loc failed"}
		//getIpByBaidu("218.5.2.179","MvS3HjerDs8bCCx8850oBax7");
//		System.out.println(getPointByBaidu("218.5.2.179","MvS3HjerDs8bCCx8850oBax7"));
	}
}
