package club.codeapes.web.core.redis.utils;

/**
 * 	@Title:	 		序列化工具类。
 * 	@Project:		shiro_spring_mybatis  
 * 	@ClassName:		SerializationUtils.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月18日 下午3:54:09 
 *	@version 		V1.0  
 */
public final class SerializationUtils {

    // private static final Logger logger = LoggerFactory.getLogger(SerializationUtils.class);

    private SerializationUtils() {
        throw new Error("Do not allow instantiation!");
    }

    private static Serializer serializer;

    static {
        // String ser = FST_SERIALIZER;
        // if (null == ser || 0 == ser.trim().length()) {
        // serializer = new JavaSerializer();
        // }
        // else if (JAVA_SERIALIZER.equals(ser)) {
        // serializer = new JavaSerializer();
        // }
        // else if (FST_SERIALIZER.equals(ser)) {
        // serializer = new FSTSerializer();
        // }
        // else {
        // try {
        // serializer = (Serializer) Class.forName(ser).newInstance();
        // }
        // catch (Exception e) {
        // }
        // }

        serializer = new FSTSerializer();
    }

    /**
     * 序列化。
     * 
     * @param obj
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static byte[] serialize(Object obj) {
        return serializer.serialize(obj);
    }

    /**
     * 反序列化。
     * 
     * @param bytes
     * @return
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static Object deserialize(byte[] bytes) {
        return serializer.deserialize(bytes);
    }

}
