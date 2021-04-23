package club.codeapes.web.core.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 	@Title:	 
 * 	@Project:		network_security_service_platform  
 * 	@ClassName:		DataSource.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年2月17日 下午1:22:20 
 *	@version 		V1.0  
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
	/**
	 * 【必须】数据源关键字，默认为空串
	 * @return
	 */
	String key() default "";
	/**
	 * 是否启用，默认为启用
	 * @return
	 */
	boolean enable() default true;
	/**
	 * 数据源名称，默认为空串
	 * @return
	 */
	String name() default "";
}
