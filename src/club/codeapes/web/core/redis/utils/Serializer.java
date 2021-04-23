package club.codeapes.web.core.redis.utils;

/**
 * 	@Title:	 		对象序列化接口。
 * 	@Project:		shiro_spring_mybatis  
 * 	@ClassName:		Serializer.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月18日 下午3:54:35 
 *	@version 		V1.0  
 */
public interface Serializer {

    /**
     * 序列化程序的名称。
     * 
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public String name();

    /**
     * 序列化给定的对象。
     * 
     * @param obj
     *            给定的对象
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public byte[] serialize(Object obj);

    /**
     * 反序列化。
     * 
     * @param bytes
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public Object deserialize(byte[] bytes);

}
