package club.codeapes.web.core.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import club.codeapes.web.core.redis.utils.SerializationUtils;

/**
 * 	@Title:	 		RedisSessionDAO
 * 	@Project:		shiro_spring_mybatis  
 * 	@ClassName:		RedisSessionDAO.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月18日 下午3:53:29 
 *	@version 		V1.0  
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    /**
     * shiro-redis的session对象前缀
     */
    private RedisManager redisManager;
    

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }
    
    

    /**
     * save session
     * 
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }

        byte[] key = getByteKey(session.getId());
        // byte[] value = SerializeUtils.serialize(session);
        byte[] value = SerializationUtils.serialize(session);
        session.setTimeout(redisManager.getExpire() * 1000);
        this.redisManager.set(key, value, redisManager.getExpire());
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }

        redisManager.del(this.getByteKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();

        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if (keys != null && keys.size() > 0) {
            for (byte[] key : keys) {
                // Session s = (Session) SerializeUtils.deserialize(redisManager.get(key));
                Session s = (Session) SerializationUtils.deserialize(redisManager.get(key));
                sessions.add(s);
            }
        }

        return sessions;
    }
    
    public List<Map<String,Object>> getActiveSessions(int pageNumber, int pageSize){
		Set<Session> sessions = new HashSet<Session>();

		Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
		if (keys != null && keys.size() > 0) {
			for (byte[] key : keys) {
				Session s = (Session) SerializationUtils.deserialize(redisManager.get(key));
				sessions.add(s);
			}
		}
//		int start = (pageNumber - 1) * pageSize;
//		int toIndex = (start + pageSize) > sessions.size() ? sessions.size() : start + pageSize;

		if (sessions.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Session session : sessions) {
				Map<String, Object> map = (Map<String, Object>) session.getAttribute("LOGINUSER");
				if(map == null){
					map = new HashMap<String,Object>();
					map.put("user_account", "游客");
				}else{
					Map<String,Object> temp = (Map<String, Object>) map.get("user");
					map.put("user_account", temp.get("user_account"));
				}
				map.put("lastAccessTime", session.getLastAccessTime());
				map.put("host", session.getHost());
				map.put("id", session.getId());
				map.put("startTime", session.getStartTimestamp());
				map.put("timeOut", session.getTimeout());
				// 做到这里，明天要把map弄成list放回到前台。 另外， 考虑一下登录用户的信息，是否也放到session里面，
				// 还有session里面好像有个时间，看下能不能用起来
				list.add(map);
				return list;
			}
//			List<Map<String, Object>> relist = list.subList(start, toIndex);
//			return relist;
		}
		return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }

        // Session s = (Session) SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
        Session s = (Session) SerializationUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));

        return s;
    }

    /**
     * 获得byte[]型的key
     * 
     * @param key
     * @return
     */
    private byte[] getByteKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }


    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;

        // 初始化redisManager
        this.redisManager.init();
    }

    /**
     * Returns the Redis session keys prefix.
     * 
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key prefix.
     * 
     * @param keyPrefix
     *            The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

}
