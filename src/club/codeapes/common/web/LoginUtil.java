package club.codeapes.common.web;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import club.codeapes.common.file.PropertyUtil;
/**
 * 登陆信息工具类
 * @Description
 * @version 1.0 
 * @date 2015年5月4日 下午4:40:55
 */
public class LoginUtil {

	/**
	 * user = map
	 * uri 	= map
	 */
	/**
	 * 获得配置信息
	 * @return
	 */
	public static Map<String,String> getConfig(){
		Map<String,String> config = new HashMap<String,String>();
		config.put("session_key", "LOGINUSER");
		config.put("user_key", "user");
		config.put("uri_key", "uri");
		config.put("productcodes_key", "product_codes");
		/**
		 * 读取配置文件
		 */
		try {
			PropertyUtil pu = new PropertyUtil(LoginUtil.getWebRoot()+"WEB-INF/classes/codeapes.properties");
			if(pu.getValue("util.login.session_key")!=null){
				config.put("session_key",pu.getValue("util.login.session_key"));
			}
			if(pu.getValue("util.login.user_key")!=null){
				config.put("user_key",pu.getValue("util.login.user_key"));
			}
			if(pu.getValue("util.login.uri_key")!=null){
				config.put("uri_key",pu.getValue("util.login.uri_key"));
			}
			if(pu.getValue("util.login.productcodes_key")!=null){
				config.put("productcodes_key",pu.getValue("util.login.productcodes_key"));
			}
		} catch (IOException e) {
//			System.out.println("not found “codeapes.properties” in “"+LoginUtil.getWebRoot()+"” folder. must be create it. properties [util.login.session_key,util.login.user_key,util.login.uri_key]");
		}
		return config;
	}
	
	
	/**
	 * 获得登陆信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getLoginInfo(HttpServletRequest request){
		Map<String,String> config = LoginUtil.getConfig();
		return (Map<String, Object>) request.getSession().getAttribute(config.get("session_key"));
	}
	/**
	 * 获得用户信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getUserInfo(HttpServletRequest request){
		Map<String,String> config = LoginUtil.getConfig();
		return (Map<String, Object>) getLoginInfo(request).get(config.get("user_key"));
	}
	/**
	 * 获得用户信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[] getUserOrderProductCodes(HttpServletRequest request){
		Map<String,String> config = LoginUtil.getConfig();
		return (String[]) getLoginInfo(request).get(config.get("productcodes_key"));
	}
	
	
	/**
	 * 获得web根目录
	 * @return
	 */
	private static String getWebRoot() {
		URL url = RequestUtil.class.getResource(RequestUtil.class.getSimpleName()
				+ ".class");
		String str = url.getPath();
		if (str.indexOf(":") != -1) {
			str = str.substring(1, str.lastIndexOf("WEB-INF"));// 在windows下面为/F:/MyPrj/WORK/post/
		} else {
			str = str.substring(0, str.lastIndexOf("WEB-INF"));// 在Linux下面为/usr/local/apache.../
		}
		return str;
	}
}
