package club.codeapes.web.core.redis;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import club.codeapes.common.file.PropertyUtil;
import club.codeapes.common.web.RequestUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 	@Title:	 		RedisManager
 * 	@Project:		shiro_spring_mybatis  
 * 	@ClassName:		RedisManager.java   		
 *	@Description: 	TODO
 *	@author: 		JuNks.7 < 77923857@qq.com >
 * 	@date：			2016年3月18日 上午10:42:19 
 *	@version 		V1.0  
 */
@Component
public class RedisManager {
	@Value("#{configProperties['config.redis.host']}")
    private String host = "127.0.0.1";
	
	@Value("#{configProperties['config.redis.port']}")
    private int port = 6379;
	
	@Value("#{configProperties['config.redis.expire']}")
    private int expire = 0;

    @Value("#{configProperties['config.redis.timeout']}")
    private int timeout = 0;

    @Value("#{configProperties['config.redis.password']}")
    private String password = "";
    
    @Value("#{configProperties['config.redis.maxIdle']}")
    private int maxIdle = 8;
    
    @Value("#{configProperties['config.redis.maxTotal']}")
    private int maxTotal = 8;

	private JedisPoolConfig poolConfig = new JedisPoolConfig();

    private static JedisPool jedisPool = null;
    
    public RedisManager() {
    }

    /**
     * 初始化方法
     */
    public void init() {
    	PropertyUtil pu;
		try {
			pu = new PropertyUtil(RequestUtil.getWebRoot()+"WEB-INF/classes/redis.properties");
			host = pu.getValue("config.redis.host");
			port = Integer.parseInt(pu.getValue("config.redis.port"));
			timeout = Integer.parseInt(pu.getValue("config.redis.timeout"));
			password = pu.getValue("config.redis.password");
		} catch (IOException e) {
		}
        if (jedisPool == null) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
            }
            else if (timeout != 0) {
                jedisPool = new JedisPool(poolConfig, host, port, timeout);
            }
            else {
                jedisPool = new JedisPool(poolConfig, host, port);
            }
        }
    }

    /**
     * get value from redis
     * 
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = jedisPool.getResource();
//        jedis.lrange("", start, end)
        try {
            value = jedis.get(key);
        }
        finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     * 
     * @param key
     * @param value
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        }
        finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * set
     * 
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        }
        finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    /**
     * del
     * 
     * @param key
     */
    public void del(byte[] key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        }
        finally {
            jedisPool.returnResource(jedis);
        }
    }
    

    /**
     * flush
     */
    public void flushDB() {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.flushDB();
        }
        finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * size
     */
    public Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = jedisPool.getResource();
        try {
            dbSize = jedis.dbSize();
        }
        finally {
            jedisPool.returnResource(jedis);
        }
        return dbSize;
    }

    /**
     * keys
     * 
     * @param regex
     * @return
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = jedisPool.getResource();
        try {
            keys = jedis.keys(pattern.getBytes());
        }
        finally {
            jedisPool.returnResource(jedis);
        }
        return keys;
    }

    public String getHost() {
        return host;
    }

    @Value("#{configProperties['config.redis.host']}")
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    @Value("#{configProperties['config.redis.port']}")
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getTimeout() {
        return timeout;
    }

    @Value("#{configProperties['config.redis.timeout']}")
    public void setTimeout(String timeout) {
        this.timeout = Integer.parseInt(timeout);
    }

    public String getPassword() {
        return password;
    }

    @Value("#{configProperties['config.redis.password']}")
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getMaxIdle() {
		return maxIdle;
	}

    @Value("#{configProperties['config.redis.maxIdle']}")
	public void setMaxIdle(String maxIdle) {
		this.maxIdle = Integer.parseInt(maxIdle);
	}

	public int getMaxTotal() {
		return maxTotal;
	}
	
	@Value("#{configProperties['config.redis.maxTotal']}")
	public void setMaxTotal(String maxTotal) {
		this.maxTotal = Integer.parseInt(maxTotal);
	}

    /**
     * Returns the poolConfig.
     * 
     * @return Returns the poolConfig
     */
    public JedisPoolConfig getPoolConfig() {
        return poolConfig;
    }

    /**
     * Sets the pool configuration for this factory.
     * 
     * @param poolConfig
     *            The poolConfig to set.
     */
    public void setPoolConfig(JedisPoolConfig poolConfig) {
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(true);
         
        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        poolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
         
        //是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(true);
         
        //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
        poolConfig.setJmxNamePrefix("pool");
         
        //是否启用后进先出, 默认true
        poolConfig.setLifo(true);
         
        //最大空闲连接数, 默认8个
        poolConfig.setMaxIdle(maxIdle);
         
        //最大连接数, 默认8个
        poolConfig.setMaxTotal(maxTotal);
         
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        poolConfig.setMaxWaitMillis(-1);
         
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        poolConfig.setMinEvictableIdleTimeMillis(300000);
         
        //最小空闲连接数, 默认0
        poolConfig.setMinIdle(0);
         
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        poolConfig.setNumTestsPerEvictionRun(3);
         
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
        poolConfig.setSoftMinEvictableIdleTimeMillis(300000);
         
        //在获取连接的时候检查有效性, 默认false
        poolConfig.setTestOnBorrow(false);
         
        //在空闲时检查有效性, 默认false
        poolConfig.setTestWhileIdle(false);
         
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(-1);
        this.poolConfig = poolConfig;
    }
    
    
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
    /**
     * 
     * @Title: release
     * @Description: 释放连接
     * @param @param jedis
     * @return void
     * @throws
     */
    public static void release(Jedis jedis){
    	jedisPool.returnResource(jedis);
    }

}
