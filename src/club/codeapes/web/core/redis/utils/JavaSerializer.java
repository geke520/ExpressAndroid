package club.codeapes.web.core.redis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	@Title:	 		使用标准Java API实现的序列化。
 * 	@Project:		shiro_spring_mybatis  
 * 	@ClassName:		JavaSerializer.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月18日 下午3:53:49 
 *	@version 		V1.0  
 */
public class JavaSerializer implements Serializer {

    private static Logger logger = LoggerFactory.getLogger(JavaSerializer.class);

    public static final String JAVA_SERIALIZER = "java";

    @Override
    public String name() {
        return JAVA_SERIALIZER;
    }

    @Override
    public byte[] serialize(Object obj) {
        if (null == obj) {
            return new byte[0];
        }
        else if (!(obj instanceof Serializable)) {
            throw new IllegalArgumentException("[" + obj.getClass().getName() + "] does not implement java.io.Serializable interface.");
        }

        byte[] result = null;
        ByteArrayOutputStream bytesOutputStream = new ByteArrayOutputStream(128);
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(bytesOutputStream);
            objectOutputStream.writeObject(obj);
            result = bytesOutputStream.toByteArray();
        }
        catch (IOException e) {
            logger.error("Failed to serialize.", e);
        }
        catch (Exception e) {
            logger.error("Failed to serialize.", e);
        }
        finally {
            if (null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                }
                catch (IOException e) {
                }
            }
        }

        return result;
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (null == bytes || 0 == bytes.length) {
            return null;
        }

        Object result = null;
        ObjectInputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            result = objectOutputStream.readObject();
        }
        catch (ClassNotFoundException e) {
            logger.error("Class of a serialized object cannot be found.", e);
        }
        catch (IOException e) {
            logger.error("Failed to deserialize.", e);
        }
        catch (Exception e) {
            logger.error("Failed to deserialize.", e);
        }
        finally {
            if (null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                }
                catch (IOException e) {
                }
            }
        }

        return result;
    }

}
