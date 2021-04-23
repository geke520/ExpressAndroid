package club.codeapes.common.usermap;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import club.codeapes.common.lang.StringUtil;
import club.codeapes.common.web.LoginUtil;

/**
 * 	@Title:	 
 * 	@Project:		network_security_service_platform  
 * 	@ClassName:		UserMap.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月1日 下午8:29:38 
 *	@version 		V1.0  
 */
public class UserMap extends HashMap<String, Object>{
	public UserMap() {
		super();
		this.put("user_account", getUser_account());
	}
	
	public static String getUser_account() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		Map<String,String> config = LoginUtil.getConfig();
		Map<String, Object> map =  (Map<String, Object>) session.getAttribute(config.get("session_key"));
		Map<String, Object> user = (Map<String, Object>)map.get(config.get("user_key"));
		return user.get("user_account").toString();
	}
	
	public static String getUser_id() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		Map<String,String> config = LoginUtil.getConfig();
		Map<String, Object> map =  (Map<String, Object>) session.getAttribute(config.get("session_key"));
		Map<String, Object> user = (Map<String, Object>)map.get(config.get("user_key"));
		return user.get("user_id").toString();
	}
	
	public static int getUser_operate_self_only() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		Map<String,String> config = LoginUtil.getConfig();
		Map<String, Object> map =  (Map<String, Object>) session.getAttribute(config.get("session_key"));
		Map<String, Object> user = (Map<String, Object>)map.get(config.get("user_key"));
		return Integer.parseInt(user.get("operate_self_only").toString());
	}
	
	public static Map<String, Object> getUserMap() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession(false);
		Map<String,String> config = LoginUtil.getConfig();
		Map<String, Object> map =  (Map<String, Object>) session.getAttribute(config.get("session_key"));
		Map<String, Object> user = (Map<String, Object>)map.get(config.get("user_key"));
		return user;
	}
	
}
