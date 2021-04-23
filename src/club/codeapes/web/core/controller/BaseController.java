package club.codeapes.web.core.controller;

import club.codeapes.common.web.LoginUtil;
import club.codeapes.common.web.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 	@Title:	 
 * 	@Project:		network_security_service_platform  
 * 	@ClassName:		BaseController.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2020年12月15日 下午2:18:07
 *	@version 		V1.0  
 */
@Controller
public class BaseController {

	/**
	 * 
	 * @Title: getParams 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param request
	 * @param userSessionParams 数组格式：[{'用户key,参数key','用户key,参数key'}]
	 * @return
	 */
	public Map<String,Object> getParams(HttpServletRequest request,String[] userSessionParams){
		Map<String,Object> params = new HashMap<String,Object>();
		Set<String> key = request.getParameterMap().keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String name = it.next();
			Object[] value = (Object[]) request.getParameterMap().get(name);
			if(value.length==1){
				if(value[0]!=null&&value[0].toString().length()>0)
					params.put(name, value[0]);
			}else if(value.length>1){
				params.put(name, value);
			}
		}
		/**
		 * 分页构造
		 */
		if(params.get("start")==null){
			params.put("start", 0);
		}else{
			params.put("start", params.get("start").toString().length()>0?Integer.parseInt(params.get("start").toString()):0);
		}
		if(params.get("limit")==null){
			params.put("limit", 10);
		}else{
			params.put("limit", params.get("limit").toString().length()>0?Integer.parseInt(params.get("limit").toString()):10);
		}
		/* 从会话中获取指定key参数并设置进去 */
		if(userSessionParams!=null && userSessionParams.length>0){
			Map<String,Object> user = LoginUtil.getUserInfo(request);
			for(String t:userSessionParams){
				String[] temp = t.split(",");
				if(user.get(temp[0])!=null){
					if(temp.length == 1){
						params.put(temp[0], user.get(temp[0]));
					}else if(temp.length ==2){
						params.put(temp[1], user.get(temp[0]));
					}
				}
			}
		}
		return params;
	}
	
	/**
	 * 构造传递的参数
	 * @param request
	 * @return
	 */
	public Map<String,Object> getParams(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Set<String> key = request.getParameterMap().keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String name = it.next();
			Object[] value = (Object[]) request.getParameterMap().get(name);
			if(value.length==1){
				if(value[0]!=null&&value[0].toString().length()>0)
					params.put(name, value[0]);
			}else if(value.length>1){
				params.put(name, value);
			}
		}
		/**
		 * 分页构造
		 */
		if(params.get("start")==null){
			params.put("start", 0);
		}else{
			params.put("start", params.get("start").toString().length()>0?Integer.parseInt(params.get("start").toString()):0);
		}
		if(params.get("limit")==null){
			params.put("limit", 10);
		}else{
			params.put("limit", params.get("limit").toString().length()>0?Integer.parseInt(params.get("limit").toString()):10);
		}
		/**
		 * 设置其他参数
		 */
		params.put("session_user_ip", RequestUtil.getIpAddr(request));
		return params;
	}
	/**
	 *	@Title: 		
	 *	@MethodName:	resetModel 
	 *	@Description:	
	 *	@param params
	 *	@param request 		void
	 *	@throws
	 */
	@SuppressWarnings("rawtypes")
	public void resetModel(ModelMap params,HttpServletRequest request){
		Set<String> key = request.getParameterMap().keySet();
		for (Iterator it = key.iterator(); it.hasNext();) {
			String name = (String) it.next();
			Object[] value = (Object[]) request.getParameterMap().get(name);
			if(value.length==1){
				if(value[0]!=null&&value[0].toString().length()>0)
					params.put(name,value[0]);
			}else if(value.length>1){
				params.put(name,value[0]);
			}
		}
	}
	
}
