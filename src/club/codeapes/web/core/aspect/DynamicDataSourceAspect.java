package club.codeapes.web.core.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import club.codeapes.web.core.annotation.DataSource;
import club.codeapes.web.core.datasource.DataSourceSwitch;

/**
 * 	@Title:	 
 * 	@Project:		network_security_service_platform  
 * 	@ClassName:		DynamicDataSourceAspect.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年2月17日 下午1:22:41 
 *	@version 		V1.0  
 */
public class DynamicDataSourceAspect {

	public DynamicDataSourceAspect() {
	}

	/**
	 * 拦截之后
	 * @param jp
	 * @throws Throwable
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public void afterHandler(JoinPoint jp) throws Throwable {
		/**
		 * 这里需要重置数据源为默认
		 */
		// 拦截的实体类
		Object target = jp.getTarget();
		// 拦截的方法名称
		String methodName = jp.getSignature().getName();
		// 拦截的方法参数
		Object[] args = jp.getArgs();

		// 拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature) jp.getSignature())
				.getMethod().getParameterTypes();

		Object object = null;
		// 获得被拦截的方法
		Method method = target.getClass().getMethod(methodName, parameterTypes);
//		System.out.println(methodName);
		// 获得方法上面的注解
		DataSource ds = method.getAnnotation(DataSource.class);
		/**
		 * 判断是否注解了数据源
		 */
		if(ds!=null){
			/**
			 * 判断数据源是否启用
			 */
			if(ds.enable() && ds.key().length()>0){
				//清空被注解的数据源
				DataSourceSwitch.clearDataSourceType();
			}
		}
	}
	/**
	 * 拦截之前
	 * @param jp
	 * @throws Throwable
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public void beforeHandler(JoinPoint jp) throws Throwable {
		// 拦截的实体类
		Object target = jp.getTarget();
		// 拦截的方法名称
		String methodName = jp.getSignature().getName();
		// 拦截的方法参数
		Object[] args = jp.getArgs();

		// 拦截的放参数类型
		Class[] parameterTypes = ((MethodSignature) jp.getSignature()).getMethod().getParameterTypes();

		Object object = null;
		// 获得被拦截的方法
		Method method = target.getClass().getMethod(methodName, parameterTypes);
		//获得方法上面的注解
		DataSource ds = method.getAnnotation(DataSource.class);
//		System.out.println(ds.name()+" "+ds.key());
		/**
		 * 判断是否注解了数据源
		 */
		if(ds!=null){
			/**
			 * 判断数据源是否启用
			 */
			if(ds.enable() && ds.key().length()>0){
				//切换为注解的数据源
				DataSourceSwitch.setDataSourceType(ds.key());
			}
		}
	}

}
