package com.commonsl.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Redis {
    private Log log = LogFactory.getLog(getClass());

    @Autowired
    @Qualifier("main")
    private StringRedisTemplate jedisTemplate;
//	@Autowired
//	private JedisPool jedisPool;

//	private Jedis jedis;

    private JsonResult jsonResult = new JsonResult();

    public JsonResult getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(JsonResult jsonResult) {
        this.jsonResult = jsonResult;
    }

    /**
     * 连接
     *
     * @return
     */
    public String getConnection() {
        try {
            Map<String, Object> resultMap = new TreeMap<String, Object>();
            String ping = jedisTemplate.getConnectionFactory().getConnection().ping();
            System.out.println("Connection to server sucessfully" + ping);

            //添加
            jedisTemplate.boundValueOps("test").set("this is one redis");
            System.out.println("新增后---" + jedisTemplate.boundValueOps("test").get());
            jedisTemplate.delete("test");
            System.out.println("删除后---" + jedisTemplate.boundValueOps("test").get());
            jedisTemplate.boundValueOps("test").set("this is two redis");
            System.out.println("修改后---" + jedisTemplate.boundValueOps("test").get());

            //检查服务器是否正在运行
            if (ping.equals("PONG")) {
                resultMap.put("msg", "Connection to server sucessfully");
                getRedis();
                System.out.print("Connection to server sucessfully");
            } else {
                resultMap.put("msg", "Connection to server failure");
                System.out.print("Connection to server failure");

            }
            jsonResult.setData(resultMap);
            System.out.println("==========" + resultMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            jsonResult.addErrorCode(ErrorCode.SYS_ERR);
        }
        return "success";
    }
    /**
     * 获取jedis实例
     * @return
     */
//	public synchronized  Jedis getJedis() {
//		        try {
//		            if (jedisPool != null) {
//		            	jedis= jedisPool.getResource();
//		               return jedis;
//	           } else {
//		                return null;
//		            }
//		       } catch (Exception e) {
//	            e.printStackTrace();
//		           return null;
//		       }
//	    }
//	 /**
//	  * 释放jedis资源
//	  * @param jedis
//	  */
//	 public  void returnResource(final Jedis jedis) {
//		       if (jedis != null) {
//		    	   jedisPool.returnResource(jedis);
//		         }
//		    }
//
//	  public  void testSet(){
//
//        //添加
//	        jedis.sadd("user1","liuling");
//	        jedis.sadd("user1","xinxin");
//	        jedis.sadd("user1","ling");
//	        jedis.sadd("user1","zhangxinxin");
//	        jedis.sadd("user1","who");
//	        //移除noname
//	        jedis.srem("user1","who");
//	        System.out.println(jedis.smembers("user1"));//获取所有加入的value
//	        System.out.println(jedis.sismember("user1", "who"));//判断 who 是否是user集合的元素
//	        System.out.println(jedis.srandmember("user1"));
//	        System.out.println(jedis.scard("user1"));//返回集合的元素个数
//	    }

    /**
     * 读取redis所有keys
     *
     * @return
     */
    public String getRedis() {
        try {
            Map<String, Object> resultMap = new TreeMap<String, Object>();
            if (openConnection()) {
                Set<String> keys = jedisTemplate.keys("*");
                System.out.println(keys);
                resultMap.put("msg", "get sucessfully");
                System.out.print("get sucessfully");

            } else {
                resultMap.put("msg", "Connection to server failure");
                System.out.print("Connection to server failure");
            }
            jsonResult.setData(resultMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            jsonResult.addErrorCode(ErrorCode.SYS_ERR);
        }
        return "success";
    }

    public Set<String> getKeys(String k) {
        try {
                Set<String> keys = jedisTemplate.keys(k);
                return keys;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 发布
     *
     * @param channel 频道
     * @param message 内容
     */
    public void convertAndSend(String channel, Object message) {
        jedisTemplate.convertAndSend(channel, message);
    }

    /**
     * 连接是否通过
     *
     * @return
     */
    public Boolean openConnection() {
        Boolean falg = false;
        String ping = jedisTemplate.getConnectionFactory().getConnection().ping();
        // 检查服务器是否正在运行
        if (ping.equals("PONG")) {
            System.out.println("redis在线...");
            falg = true;
        }
        if (!falg) {
            System.out.println("redis掉线...");
        }
        return falg;
    }

    /**
     * 定时器
     */
    public void timer1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (!openConnection()) {
                    openConnection();
                }
            }
        }, 1000);
    }


    /*********Key（键） 操作*********************************************************************/
    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 删除给定的一个 key 。不存在的 key 会被忽略。
     * @author chenwenhao
     * @date 2017-1-6 下午3:46:57
     */
    public void del(String key) {
        jedisTemplate.delete(key);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 删除给定的多个 key 。不存在的 key 会被忽略。
     * @author chenwenhao
     * @date 2017-1-6 下午3:48:01
     */
    public void del(Collection<String> keys) {
        jedisTemplate.delete(keys);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 检查给定 key 是否存在。若 key 存在，返回 true ，否则返回 false 。
     * @author chenwenhao
     * @date 2017-1-6 下午3:49:41
     */
    public Boolean exists(String key) {
        return jedisTemplate.hasKey(key);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 为给定 key 设置生存时间(秒)，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * 设置成功返回 true 。
     * 当 key 不存在或者不能为 key 设置生存时间时，返回 false 。
     * @author chenwenhao
     * @date 2017-1-6 下午3:52:30
     */
    public Boolean expire(String key, long timeout) {
        return jedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 设置key生存时间。key在date这个时间点过期（自动被删除）
     * @author chenwenhao
     * @date 2017-1-6 下午3:55:30
     */
    public Boolean expireAt(String key, Date date) {
        return jedisTemplate.expireAt(key, date);
    }


    /*********String（字符串） 操作****************************************************************/
    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将字符串值 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * @author chenwenhao
     * @date 2017-1-6 下午4:00:01
     */
    public void setString(String key, String value) {
        jedisTemplate.boundValueOps(key).set(value);
    }

    public void setInteger(String key, Integer value) {
        jedisTemplate.boundValueOps(key).set(value.toString());
    }

    public void setLong(String key, Long value) {
        jedisTemplate.boundValueOps(key).set(value.toString());
    }

    public void setDouble(String key, Double value) {
        jedisTemplate.boundValueOps(key).set(value.toString());
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将对象 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * @author chenwenhao
     * @date 2017-1-13 下午1:57:38
     */
    public void set(String key, Object value) {
        jedisTemplate.boundValueOps(key).set(JsonHelper.toJson(value));
    }


    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将字符串值 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
     * 有效期为timeout秒（即timeout秒后被自动删除）
     * @author chenwenhao
     * @date 2017-1-6 下午4:02:33
     */
    public void setex(String key, String value, long timeout) {
        jedisTemplate.boundValueOps(key).set(value, timeout, TimeUnit.SECONDS);
    }

    public void setex(String key, Object value, long timeout) {
        jedisTemplate.boundValueOps(key).set(JsonHelper.toJson(value),timeout, TimeUnit.SECONDS);
    }
//    public void setexToSerializer(String key, Object value, long timeout) {
//        jedisTemplate.setHashValueSerializer(new JacksonJsonRedisSerializer<>());
//        jedisTemplate.boundValueOps(key).set(JsonHelper.toJson(value),timeout, TimeUnit.SECONDS);
//    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * 设置成功，返回 true 。
     * 设置失败，返回 false 。
     * @author chenwenhao
     * @date 2017-1-12 下午2:12:52
     */
    public Boolean setnx(String key, String value) {
        return jedisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 返回 key 所关联的字符串值。如果 key 不存在那么返回null
     * @author chenwenhao
     * @date 2017-1-6 下午4:03:51
     */
    public String getString(String key) {
        return jedisTemplate.boundValueOps(key).get();
    }

    public Integer getInteger(String key) {
        try {
            return Integer.valueOf(getString(key));
        } catch (Exception e) {
            return 0;
        }
    }

    public Long getLong(String key) {
        return Long.valueOf(getString(key));
    }

    public Double getDouble(String key) {
        return Double.valueOf(getString(key));
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 返回 key 所关联的字符串值【解析为clazz类型对象实例返回】。如果 key 不存在那么返回null
     * @author chenwenhao
     * @date 2017-1-13 上午10:34:28
     */
    public <T> T get(String key, Class<T> clazz) {
        String result = jedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(result)) {
            return JsonHelper.fromJson(result, clazz);
        }
        return null;
    }


    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     * 返回给定 key 的旧值。
     * 当 key 没有旧值时，也即是， key 不存在时，返回 null 。
     * @author chenwenhao
     * @date 2017-1-6 下午4:11:45
     */
    public String getSet(String key, String value) {
        return jedisTemplate.boundValueOps(key).getAndSet(value);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     * @author chenwenhao
     * @date 2017-1-6 下午4:14:37
     */
    public String getSet(String key, long start, long end) {
        return jedisTemplate.boundValueOps(key).get(start, end);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将 key 所储存的值加上增量 increment 。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
     * 返回值：加上 increment 之后， key 的值。
     * @author chenwenhao
     * @date 2017-1-6 下午4:18:00
     */
    public Long incrBy(String key, long increment) {
        return jedisTemplate.boundValueOps(key).increment(increment);
    }


    /*********Hash（哈希表） 操作****************************************************************/
    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将哈希表 key 中的域 field 的值设为 value 。
     * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。
     * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
     * @author chenwenhao
     * @date 2017-1-6 下午4:21:51
     */
    public void hset(String key, String field, String value) {
        jedisTemplate.boundHashOps(key).put(key, value);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 返回哈希表 key 中给定域 field 的值。
     * 当给定域不存在或是给定 key 不存在时，返回 null 。
     * @author chenwenhao
     * @date 2017-1-6 下午4:23:57
     */
    public String hget(String key, String field) {
        Object valueObject = jedisTemplate.boundHashOps(key).get(field);
        return null == valueObject ? null : valueObject.toString();
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 删除哈希表 key 中的一个指定域，不存在的域将被忽略。
     * @author chenwenhao
     * @date 2017-1-6 下午4:27:32
     */
    public void hdel(String key, String field) {
        jedisTemplate.boundHashOps(key).delete(field);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 查看哈希表 key 中，给定域 field 是否存在。
     * 如果哈希表含有给定域，返回 true 。
     * 如果哈希表不含有给定域，或 key 不存在，返回 false。
     * @author chenwenhao
     * @date 2017-1-6 下午4:28:58
     */
    public Boolean hexists(String key, String field) {
        return jedisTemplate.boundHashOps(key).hasKey(field);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 此命令会覆盖哈希表中已存在的域。
     * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。
     * @author chenwenhao
     * @date 2017-1-6 下午4:37:07
     */
    public void hmset(String key, Map<String, String> values) {
        jedisTemplate.boundHashOps(key).putAll(values);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     * @author chenwenhao
     * @date 2017-1-6 下午4:41:56
     */
    public Long hincrby(String key, String fiel, long increment) {
        return jedisTemplate.boundHashOps(key).increment(key, increment);
    }


    /*********List（列表） 操作********************************************************************/
    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将一个或多个值 value 插入到列表 key 的表头,返回list的长度
     * @author chenwenhao
     * @date 2017-1-6 下午4:48:54
     */
    public Long rpushLeft(String key, String value) {
        return jedisTemplate.boundListOps(key).leftPush(value);
    }

    /**
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 将一个或多个值 value 插入到列表 key 的末尾,返回list的长度
     * @author chenwenhao
     * @date 2017-1-6 下午4:48:54
     */
    public Long rpushRight(String key, String value) {
        return jedisTemplate.boundListOps(key).rightPush(value);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * @author chenwenhao
     * @date 2017-1-6 下午5:23:15
     */
    public String lindex(String key, long index) {
        return jedisTemplate.boundListOps(key).index(index);
    }


    /*********Set（集合） 操作**********************************************************************/
    /**
     *
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
    假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
    返回值 ：被添加到集合中的新元素的数量，不包括被忽略的元素。
     * @author chenwenhao
     * @date 2017-1-6 下午5:25:50
     */
//	public Boolean sadd(String key,String value){
//		return jedisTemplate.boundSetOps(key).add(value);
//	}

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 判断 value 元素是否集合 key 的成员。
     * 如果 value 元素是集合的成员，返回 true 。
     * 如果 value 元素不是集合的成员，或 key 不存在，返回 false 。
     * @author chenwenhao
     * @date 2017-1-6 下午5:42:40
     */
    public Boolean sismember(String key, String value) {
        return jedisTemplate.boundSetOps(key).isMember(value);
    }

    /**
     *
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 移除集合 key 中的一个 value 元素，不存在的 value 元素会被忽略。
     * 				移除成功返回true，失败返回false
     * @author chenwenhao
     * @date 2017-1-6 下午5:43:57
     */
//	public Boolean srem(String key,String value){
//		return jedisTemplate.boundSetOps(key).remove(value);
//	}

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 返回集合 key 的基数(集合中元素的数量)。
     * @author chenwenhao
     * @date 2017-1-6 下午5:46:07
     */
    public Long scard(String key) {
        return jedisTemplate.boundSetOps(key).size();
    }


    /*********SortedSet（有序集合） 操作****************************************************************/
    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，
     * 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * @author chenwenhao
     * @date 2017-1-6 下午5:49:37
     */
    public Boolean zadd(String key, String member, double score) {
        return jedisTemplate.boundZSetOps(key).add(member, score);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 返回有序集 key 的基数。
     * @author chenwenhao
     * @date 2017-1-6 下午5:50:46
     */
    public Long zcard(String key) {
        return jedisTemplate.boundZSetOps(key).size();
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     * @author chenwenhao
     * @date 2017-1-6 下午5:52:19
     */
    public Long zcount(String key, double min, double max) {
        return jedisTemplate.boundZSetOps(key).count(min, max);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 为有序集 key 的成员 member 的 score 值加上增量 increment 。
     * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
     * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member 。
     * score 值可以是整数值或双精度浮点数。
     * 返回值：member 成员的新 score 值
     * @author chenwenhao
     * @date 2017-1-6 下午5:55:13
     */
    public Double zincrby(String key, String member, double score) {
        return jedisTemplate.boundZSetOps(key).incrementScore(member, score);
    }

    /**
     *
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * 				成功返回true,失败返回false
     * @author chenwenhao
     * @date 2017-1-6 下午5:57:01
     */
//	public Boolean zrem(String key , String member){
//		return jedisTemplate.boundZSetOps(key).remove(member);
//	}

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 返回有序集 key 中，成员 member 的 score 值。
     * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 null 。
     * @author chenwenhao
     * @date 2017-1-6 下午5:58:05
     */
    public Double zscore(String key, String member) {
        return jedisTemplate.boundZSetOps(key).score(member);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 注意点：返回的是成员。即member的集合值
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 有序集成员按 score 值递增(从小到大)次序排列。
     * @author chenwenhao
     * @date 2017-1-6 下午6:02:37
     */
    public Set<String> zrangebyscore(String key, double min, double max) {
        return jedisTemplate.boundZSetOps(key).rangeByScore(min, max);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * @author chenwenhao
     * @date 2017-1-6 下午6:07:38
     */
    public void zremrangebyscore(String key, double min, double max) {
        jedisTemplate.boundZSetOps(key).removeRangeByScore(min, max);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 注意点：获取对应下标区间内的成员（member）组合
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递增(从小到大)来排序。
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
     * @author chenwenhao
     * @date 2017-1-6 下午6:10:47
     */
    public Set<String> zrange(String key, long start, long end) {
        return jedisTemplate.boundZSetOps(key).range(start, end);
    }

    /**
     * @Title: Redis.java
     * @Package com.mahjong.util
     * @Description: 注意点：获取对应下标区间内的成员（member）组合
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * @author chenwenhao
     * @date 2017-1-6 下午6:14:02
     */
    public Set<String> zrevrange(String key, long start, long end) {
        return jedisTemplate.boundZSetOps(key).reverseRange(start, end);
    }


    /********************
     * Transaction（事务）
     *********************************************************/
    //字符串编码，数据以string存储
    private RedisSerializer<String> stringSerializer;

    public RedisSerializer<String> getStringSerializer() {
        if (stringSerializer == null) {
            stringSerializer = jedisTemplate.getStringSerializer();
        }
        return stringSerializer;
    }

    /***
     * @Title: Redis.java
     * @Package com.alliance.util
     * @Description: 使用redis的事务实现秒杀操作。
     * key :　product_[id值]
     * value : 商品数量
     * 返回值：true：成功，false：失败
     * <p>
     * 使用下面这种方式会报 ERR EXEC without MULTI 异常。
     * 原因：该库的redistemplate的multi和exec方法，都是新产生连接，而非使用本来的连接，这个异常，也是由于这个原因所以才导致的。
     * （因为新连接中，直接执行退出同步，系统肯定会去找是从哪儿开始同步的，这一找，发现没有开始同步的命令，就会抛出异常了）
     * <p>
     * //			jedisTemplate.watch(key);
     * //			String value = jedisTemplate.boundValueOps(key).get();
     * //			jedisTemplate.multi();
     * //			Long value = incrBy(key, -1);//数量减1
     * //			System.out.println("before : "+value+",after : "+value);
     * //			List<Object> result = jedisTemplate.exec();
     * //			System.out.println("=== exect : "+result.get(0).toString());
     * @author chenwenhao
     * @date 2017-1-18 下午4:04:08
     */
    public Boolean secKill(final String key) {
        return jedisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] rawKey = getStringSerializer().serialize(key);
                //监视 key
                connection.watch(rawKey);
                //获取key的内容
                byte[] rawReturn = connection.get(rawKey);
                Integer value = Integer.valueOf((String) getStringSerializer().deserialize(rawReturn));
                //开启事务
                connection.multi();
                if (value == null || value <= 0) {
                    return false;
                }
                connection.incrBy(rawKey, -1);   //库存减1

                /**
                 * 注意：【经过测试发现>>>】使用watch监视某个key开启事务后，不能再获取该key的值，即使是同一事务中，也无法获取。
                 * 下面的代码，获取出来的值是null。说明key在当前事务中已被锁定，不再给予读取的权限，故返回null值
                 */
//				rawReturn = connection.get(rawKey);
//				System.out.println(" get valur "+(String) getStringSerializer().deserialize(rawReturn));

                //只在这个key 没有被其他命令[事务]改动的情况下执行并生效，否则该事务被打断，事务被打断，则返回nul
                return null != connection.exec();
            }
        });
    }


}
