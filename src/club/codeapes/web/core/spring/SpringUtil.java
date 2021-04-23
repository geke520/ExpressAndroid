package club.codeapes.web.core.spring;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 *	@Title:			获取spring application context工具类
 *	@File:			SpringUtil.java 
 *	@Package 		club.codeapes.web.base.spring 
 *	@Description: 	
 *	@author 		LinCH <312329768@qq.com> 
 *	@date 			2014年11月4日 下午4:30:01 
 *	@version 		V1.0
 */
public class SpringUtil implements ApplicationContextAware{

	@SuppressWarnings("unused")
	private static ServletContext servletContext = null;
	private static ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}
	public static Object getBean(String beanId) {
		return appContext.getBean(beanId);
	}
	public static void init(ServletContext in_servletContext) {
		servletContext = in_servletContext;		
	}	
	
	public static ApplicationContext getApplicationContext(){
		return appContext;
	}
	/**
	 * 获得springmvc要访问的类及方法
	 * @Title: getHandler 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @Type:Method
	 * @param request
	 * @param uri
	 * @param method
	 * @return
	 * Map<String,Object> key = [class,method]
	 */
	public static Map<String,Object> getHandler(HttpServletRequest request, final String uri,String method) {
		Map<String,Object> handlerMap = null;
		String[] beanNames = appContext.getBeanNamesForType(RequestMappingHandlerMapping.class);
        if(beanNames != null) {
            for(String beanName : beanNames) {
				RequestMappingHandlerMapping mapping = appContext.getBean(
						beanName, RequestMappingHandlerMapping.class);
				try {
					HandlerExecutionChain hec = mapping.getHandler(request);
					if (hec != null) {
						Object handler = hec.getHandler();
						HandlerMethod hm = (HandlerMethod) handler;
						handlerMap = new HashMap<String, Object>();
						handlerMap.put("class", hm.getBean().getClass());
						handlerMap.put("method", hm.getMethod());
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                
//                try {
//					System.out.println(mapping.getHandler(request));
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//                
//                for (Entry<RequestMappingInfo, HandlerMethod> entry : mapping.getHandlerMethods().entrySet()) {
//                	 
//                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                 
//                }
//                
//                try {
//                    HandlerExecutionChain chain = mapping.getHandler(httpServletRequestWrapper);
//                    
//                    
//                    if(chain != null) {
//                        Object handler = chain.getHandler();
//                        
//                        System.out.println(handler);
//                        
//                        if(handler instanceof HandlerMethod) {
//                        	HandlerMethod hm = (HandlerMethod)handler;
//                            handlerMap = new HashMap<String,Object>();
//                            handlerMap.put("class", hm.getBean().getClass());
//                            handlerMap.put("method", hm.getMethod());
//                        } else 
//                        	if(handler instanceof org.springframework.web.servlet.mvc.Controller) {
//                            org.springframework.web.servlet.mvc.Controller hm = (org.springframework.web.servlet.mvc.Controller)handler;
//                            Class<? extends org.springframework.web.servlet.mvc.Controller> hmClass = hm.getClass();
////                            uriMapMethod.append(hmClass.getDeclaredMethod("handleRequest",HttpServletRequest.class, HttpServletResponse.class));
//                            handlerMap = new HashMap<String,Object>();
//                            handlerMap.put("class", hmClass);
//                            handlerMap.put("method", hmClass.getDeclaredMethod("handleRequest",HttpServletRequest.class, HttpServletResponse.class));
//                        } 
//                        break;
//                    }
//                } catch (HttpRequestMethodNotSupportedException e) {
//                     return getHandler(httpServletRequestWrapper, uri, "POST");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
//        System.out.println(uriMapMethod);
        /*  */
        return handlerMap;
    }
}
