/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2016 All Rights Reserved.
 */
package com.zjzmjr.core.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import com.zjzmjr.core.base.util.SpringContextUtil;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: JedisUtil.java, v 0.1 2016-5-3 下午2:37:16 Administrator Exp $
 */
public class JedisUtil {

    private static final JedisUtil redis = new JedisUtil();
    
    /**
     * 缓存生存时间
     */
    private final int expire = 60000;
    
    
    private JedisPool jedisPool=SpringContextUtil.getBean("jedisPool");
    
    private JedisUtil() {
    }
    
    
    /**
     * 从jedis连接池获取jedis对象
     * 
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }
    
    /**
     * 获取jedis管道
     * 
     * @return
     */
    public Pipeline getJedisPipeline() {
        return jedisPool.getResource().pipelined();
    }
    
    /**
     * 获取JedisUtil实例
     * 
     * @return
     */
    public static JedisUtil getInstance() {
        return redis;
    }
    
    /**
     * 回收jedis
     * 
     * @param jedis
     */
    public void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }
    
   
    
    /**
     * 设置过期时间
     * 
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }
    
    /**
     * 设置默认过期时间
     * 
     * @param key
     */
    public void expire(String key) {
        expire(key, expire);
    }
    
    /**
     * 清空所有key
     * 
     * @return
     */
    public String flushAll() {
        Jedis jedis = getJedis();
        String status = jedis.flushAll();
        returnJedis(jedis);
        return status;
    }
    
    /**
     * 更改key
     * 
     * @param oldkey
     * @param newkey
     * @return  状态码
     */
    public String rename(String oldkey, String newkey) {
        return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
    }
    
    /**
     * 更改key，仅当新key不存在时才执行
     * 
     * @param oldkey
     * @param newkey
     * @return  状态码
     */
    public long renamenx(String oldkey, String newkey) {
        Jedis jedis = getJedis();
        long status = jedis.renamenx(oldkey, newkey);
        returnJedis(jedis);
        return status;
    }
    
    /**
     * 更改key
     * 
     * @param oldkey
     * @param newkey
     * @return
     */
    public String rename(byte[] oldkey, byte[] newkey) {
        Jedis jedis = getJedis();
        String status = jedis.rename(oldkey, newkey);
        returnJedis(jedis);
        return status;
    }
    
    /**
     * 设置key的过期时间，以秒为单位
     * 
     * @param key
     * @param seconds
     * @return
     */
    public long expired(String key, int seconds) {
        Jedis jedis = getJedis();
        long count = jedis.expire(key, seconds);
        returnJedis(jedis);
        return count;
    }
    
    /**
     * 设置key的过期时间，它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
     * 
     * @param key
     * @param timestamp
     * @return
     */
    public long expireAt(String key, long timestamp) {
        Jedis jedis = getJedis();
        long count = jedis.expireAt(key, timestamp);
        returnJedis(jedis);
        return count;
    }
    
    /**
     * 查询key的过期时间
     * 
     * @param key
     * @return
     */
    public long ttl(String key) {
        Jedis jedis = getJedis();
        long len = jedis.ttl(key);
        returnJedis(jedis);
        return len;
    }
    
    /**
     * 取消对key过期时间的设置
     * 
     * @param key
     * @return
     */
    public long persist(String key) {
        Jedis jedis = getJedis();
        long count = jedis.persist(key);
        returnJedis(jedis);
        return count;
    }
    
    /**
     * 删除keys对应的记录，可以是多个key
     * 
     * @param keys
     * @return
     */
    public long del(String... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }
    
    /**
     * 删除keys对应的记录，可以是多个key
     * 
     * @param keys
     * @return
     */
    public long del(byte[]... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }
    
    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = getJedis();
        boolean exists = jedis.exists(key);
        returnJedis(jedis);
        return exists;
    }
    
    /**
     * 对List, Set, SortSet进行排序，如果集合数据较大应避免使用这个方法
     * 
     * @param key
     * @return
     */
    public List<String> sort(String key) {
        Jedis jedis = getJedis();
        List<String> list = jedis.sort(key);
        returnJedis(jedis);
        return list;
    }
    
    /**
     * 对List, Set, SortSet进行排序或limit
     * 
     * @param key
     * @param parame
     * @return
     */
    public List<String> sort(String key, SortingParams parame) {
        Jedis sjedis=getJedis();
        List<String> list = sjedis.sort(key, parame);
        returnJedis(sjedis);
        return list;
    }
    
    /**
     * 返回指定key存储的类型
     * 
     * @param key
     * @return
     */
    public String type(String key) {
        Jedis sjedis=getJedis();
        String type = sjedis.type(key);
        returnJedis(sjedis);
        return type;
    }
    
    /**
     * 查找所有匹配给定的模式的键
     * 
     * @param pattern   *表示多个，？表示一个
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.keys(pattern);
        returnJedis(jedis);
        return set;
    }

    /**
     * 向Set添加一条记录，如果member已存在返回0,否则返回1
     * 
     * @param key
     * @param member
     * @return
     */
    public long sadd(String key, String member) {
        Jedis jedis = getJedis();
        long s = jedis.sadd(key, member);
        returnJedis(jedis);
        return s;
    }

    public long sadd(byte[] key, byte[] member) {
        Jedis jedis = getJedis();
        long s = jedis.sadd(key, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 获取给定key中元素个数
     * 
     * @param key
     * @return
     */
    public long scard(String key) {
        Jedis jedis = getJedis();
        long len = jedis.scard(key);
        returnJedis(jedis);
        return len;
    }

    /**
     * 返回从第一组和所有的给定集合之间的差异的成员
     * 
     * @param keys
     * @return
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.sdiff(keys);
        returnJedis(jedis);
        return set;
    }

    /**
     * 这个命令等于sdiff，但返回的不是结果集，而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * 
     * @param newkey
     * @param keys
     * @return
     */
    public long sdiffstore(String newkey, String... keys) {
        Jedis jedis = getJedis();
        long s = jedis.sdiffstore(newkey, keys);
        returnJedis(jedis);
        return s;
    }

    /**
     * 返回给定集合交集的成员，如果其中一个集合为不存在或为空，则返回空Set
     * 
     * @param keys
     * @return
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.sinter(keys);
        returnJedis(jedis);
        return set;
    }

    /**
     * 这个命令等于sinter，但返回的不是结果集，而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
     * 
     * @param newkey
     * @param keys
     * @return
     */
    public long sinterstore(String newkey, String ... keys) {
        Jedis jedis = getJedis();
        long s = jedis.sinterstore(newkey, keys);
        returnJedis(jedis);
        return s;
    }

    /**
     * 确定一个给定的值是否存在
     * 
     * @param key
     * @param member
     * @return
     */
    public boolean sismember(String key, String member) {
        Jedis jedis = getJedis();
        boolean s = jedis.sismember(key, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 返回集合中的所有成员
     * 
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.smembers(key);
        returnJedis(jedis);
        return set;
    }

    public Set<byte[]> smembers(byte[] key) {
        Jedis jedis = getJedis();
        Set<byte[]> set = jedis.smembers(key);
        returnJedis(jedis);
        return set;
    }

    /**
     * 将成员从源集合移出放入目标集合
     * 如果源集合不存在或不包含指定成员，不进行任何操作，返回0
     * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
     * 
     * @param srckey
     * @param dstkey
     * @param member
     * @return
     */
    public long smove(String srckey, String dstkey, String member) {
        Jedis jedis = getJedis();
        long s = jedis.smove(srckey, dstkey, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 从集合中删除成员
     * 
     * @param key
     * @return
     */
    public String spop(String key) {
        Jedis jedis = getJedis();
        String s = jedis.spop(key);
        returnJedis(jedis);
        return s;
    }

    /**
     * 从集合中删除指定成员
     * 
     * @param key
     * @param member
     * @return
     */
    public long srem(String key, String member) {
        Jedis jedis = getJedis();
        long s = jedis.srem(key, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存
     * 
     * @param keys
     * @return
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.sunion(keys);
        returnJedis(jedis);
        return set;
    }

    /**
     * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
     * 
     * @param newkey
     * @param keys
     * @return
     */
    public long sunionstore(String newkey, String... keys) {
        Jedis jedis = getJedis();
        long s = jedis.sunionstore(newkey, keys);
        returnJedis(jedis);
        return s;
    }

    /**
     * 向集合中增加一条记录，如果这个值已存在，这个值对应的权重将被置为新的权重
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public long zadd(String key, double score, String member) {
        Jedis jedis = getJedis();
        long s = jedis.zadd(key, score, member);
        returnJedis(jedis);
        return s;
    }

    public long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = getJedis();
        long s = jedis.zadd(key, scoreMembers);
        returnJedis(jedis);
        return s;
    }

    /**
     * 获取集合中元素的数量
     * 
     * @param key
     * @return
     */
    public long zcard(String key) {
        Jedis jedis = getJedis();
        long len = jedis.zcard(key);
        returnJedis(jedis);
        return len;
    }

    /**
     * 获取指定权重区间内集合的数量
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zcount(String key, double min, double max) {
        Jedis jedis = getJedis();
        long len = jedis.zcount(key, min, max);
        returnJedis(jedis);
        return len;
    }

    /**
     * 获取set的长度
     * 
     * @param key
     * @return
     */
    public long zlength(String key) {
        long len = 0;
        Set<String> set = zrange(key, 0, -1);
        len = set.size();
        return len;
    }

    /**
     * 权重增加给定值，如果给定的member已存在
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public double zincrby(String key, double score, String member) {
        Jedis jedis = getJedis();
        double s = jedis.zincrby(key, score, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 返回指定位置的集合元素，0为第一个元素，-1为最后一个元素
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.zrange(key, start, end);
        returnJedis(jedis);
        return set;
    }

    /**
     * 返回指定权重区间的元素集合
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.zrangeByScore(key, min, max);
        returnJedis(jedis);
        return set;
    }

    /**
     * 获取指定值在集合中的位置，集合排序从低到高
     * 
     * @param key
     * @param member
     * @return
     */
    public long zrank(String key, String member) {
        Jedis jedis = getJedis();
        long index = jedis.zrank(key, member);
        returnJedis(jedis);
        return index;
    }

    /**
     * 获取指定值在集合中的位置，集合排序从高到低
     * 
     * @param key
     * @param member
     * @return
     */
    public long zrevrank(String key, String member) {
        Jedis jedis = getJedis();
        long index = jedis.zrevrank(key, member);
        returnJedis(jedis);
        return index;
    }

    /**
     * 从集合中删除成员
     * 
     * @param key
     * @param member
     * @return
     */
    public long zrem(String key, String member) {
        Jedis jedis = getJedis();
        long s = jedis.zrem(key, member);
        returnJedis(jedis);
        return s;
    }

    /**
     * 删除
     * 
     * @param key
     * @return
     */
    public long zrem(String key) {
        Jedis jedis = getJedis();
        long s = jedis.del(key);
        returnJedis(jedis);
        return s;
    }

    /**
     * 删除给定位置区间的元素
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public long zremrangeByRank(String key, int start, int end) {
        Jedis jedis = getJedis();
        long s = jedis.zremrangeByRank(key, start, end);
        returnJedis(jedis);
        return s;
    }

    /**
     * 删除给定权重区间的元素
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zremrangeByScore(String key, double min, double max) {
        Jedis jedis = getJedis();
        long s = jedis.zremrangeByScore(key, min, max);
        returnJedis(jedis);
        return s;
    }

    /**
     * 获取给定区间的元素，原始按照权重由高到低排序
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.zrevrange(key, start, end);
        returnJedis(jedis);
        return set;
    }

    /**
     * 获取给定值在集合中的权重
     * 
     * @param key
     * @param member
     * @return
     */
    public double zscore(String key, String member) {
        Jedis jedis = getJedis();
        Double score = jedis.zscore(key, member);
        returnJedis(jedis);
        if (score != null)
            return score;
        return 0;
    }


    /**
     * 从hash中删除指定的存储
     * 
     * @param key
     * @param field
     * @return
     */
    public long hdel(String key, String field) {
        Jedis jedis = getJedis();
        long s = jedis.hdel(key, field);
        returnJedis(jedis);
        return s;
    }

    public long hdel(String key) {
        Jedis jedis = getJedis();
        long s = jedis.del(key);
        returnJedis(jedis);
        return s;
    }

    /**
     * 测试hash中指定的存储是否存在
     * 
     * @param key
     * @param field
     * @return
     */
    public boolean hexists(String key, String field) {
        Jedis jedis = getJedis();
        boolean s = jedis.hexists(key, field);
        returnJedis(jedis);
        return s;
    }

    /**
     * 返回hash中指定存储位置的值
     * 
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        Jedis jedis = getJedis();
        String s = jedis.hget(key, field);
        returnJedis(jedis);
        return s;
    }

    public byte[] hget(byte[] key, byte[] field) {
        Jedis jedis = getJedis();
        byte[] s = jedis.hget(key, field);
        returnJedis(jedis);
        return s;
    }

    /**
     * 以Map的形式返回hash中的存储和值
     * 
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = getJedis();
        Map<String, String> map = jedis.hgetAll(key);
        returnJedis(jedis);
        return map;
    }

    /**
     * 添加一个对应关系
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        long s = jedis.hset(key, field, value);
        returnJedis(jedis);
        return s;
    }

    public long hset(String key, String field, byte[] value) {
        Jedis jedis = getJedis();
        long s = jedis.hset(key.getBytes(), field.getBytes(), value);
        returnJedis(jedis);
        return s;
    }

    /**
     * 添加对应关系，只有在field不存在时才执行
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hsetnx(String key, String field, String value) {
        Jedis jedis = getJedis();
        long s = jedis.hsetnx(key, field, value);
        returnJedis(jedis);
        return s;
    }

    /**
     * 获取hash中value的集合
     * 
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        Jedis jedis = getJedis();
        List<String> list = jedis.hvals(key);
        returnJedis(jedis);
        return list;
    }

    /**
     * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hincrby(String key, String field, long value) {
        Jedis jedis = getJedis();
        long s = jedis.hincrBy(key, field, value);
        returnJedis(jedis);
        return s;
    }

    /**
     * 返回指定hash中的所有存储名字,类似Map中的keySet方法
     * 
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        Jedis jedis = getJedis();
        Set<String> set = jedis.hkeys(key);
        returnJedis(jedis);
        return set;
    }

    /**
     * 获取hash中存储的个数，类似Map中size方法
     * 
     * @param key
     * @return
     */
    public long hlen(String key) {
        Jedis jedis = getJedis();
        long len = jedis.hlen(key);
        returnJedis(jedis);
        return len;
    }

    /**
     * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
     * 
     * @param key
     * @param fields
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = getJedis();
        List<String> list = jedis.hmget(key, fields);
        returnJedis(jedis);
        return list;
    }

    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        Jedis jedis = getJedis();
        List<byte[]> list = jedis.hmget(key, fields);
        returnJedis(jedis);
        return list;
    }

    /**
     * 添加对应关系，如果对应关系已存在，则覆盖
     * 
     * @param key
     * @param map
     * @return
     */
    public String hmset(String key, Map<String, String> map) {
        Jedis jedis = getJedis();
        String s = jedis.hmset(key, map);
        returnJedis(jedis);
        return s;
    }

    public String hmset(byte[] key, Map<byte[], byte[]> map) {
        Jedis jedis = getJedis();
        String s = jedis.hmset(key, map);
        returnJedis(jedis);
        return s;
    }

    /**
     * 根据key获取记录
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        byte[] value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }

    /**
     * 添加有过期时间的记录
     * 
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public String setEx(String key, int seconds, String value) {
        Jedis jedis = getJedis();
        String str = jedis.setex(key, seconds, value);
        returnJedis(jedis);
        return str;
    }

    public String setEx(byte[] key, int seconds, byte[] value) {
        Jedis jedis = getJedis();
        String str = jedis.setex(key, seconds, value);
        returnJedis(jedis);
        return str;
    }

    /**
     * 添加一条记录，仅当给定的key不存在时才插入
     * 
     * @param key
     * @param value
     * @return
     */
    public long setnx(String key, String value) {
        Jedis jedis = getJedis();
        long str = jedis.setnx(key, value);
        returnJedis(jedis);
        return str;
    }

    /**
     * 添加记录,如果记录已存在将覆盖原有的value
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    public String set(String key, byte[] value) {
        return set(SafeEncoder.encode(key), value);
    }

    public String set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        String status = jedis.set(key, value);
        returnJedis(jedis);
        return status;
    }

    /**
     * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
     * 
     * @param key
     * @param offset
     * @param value
     * @return
     */
    public long setRange(String key, long offset, String value) {
        Jedis jedis = getJedis();
        long len = jedis.setrange(key, offset, value);
        returnJedis(jedis);
        return len;
    }

    /**
     * 在指定的key中追加value
     * 
     * @param key
     * @param value
     * @return
     */
    public long append(String key, String value) {
        Jedis jedis = getJedis();
        long len = jedis.append(key, value);
        returnJedis(jedis);
        return len;
    }

    /**
     * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
     * 
     * @param key
     * @param number
     * @return
     */
    public long decrBy(String key, long number) {
        Jedis jedis = getJedis();
        long len = jedis.decrBy(key, number);
        returnJedis(jedis);
        return len;
    }

    /**
     * 可以作为获取唯一id的方法
     * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
     * 
     * @param key
     * @param number
     * @return
     */
    public long incrBy(String key, long number) {
        Jedis jedis = getJedis();
        long len = jedis.incrBy(key, number);
        returnJedis(jedis);
        return len;
    }

    /**
     * 对指定key对应的value进行截取
     * 
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    public String getrange(String key, long startOffset, long endOffset) {
        Jedis jedis = getJedis();
        String value = jedis.getrange(key, startOffset, endOffset);

        returnJedis(jedis);
        return value;
    }

    /**
     * 获取并设置指定key对应的value
     * 如果key存在返回之前的value,否则返回null
     * 
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        Jedis jedis = getJedis();
        String str = jedis.getSet(key, value);
        returnJedis(jedis);
        return str;
    }

    /**
     * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
     * 
     * @param keys
     * @return
     */
    public List<String> mget(String... keys) {
        Jedis jedis = getJedis();
        List<String> str = jedis.mget(keys);
        returnJedis(jedis);
        return str;
    }

    /**
     * 批量存储记录
     * 
     * @param keysvalues
     * @return
     */
    public String mset(String... keysvalues) {
        Jedis jedis = getJedis();
        String str = jedis.mset(keysvalues);
        returnJedis(jedis);
        return str;
    }

    /**
     * 获取key对应的值的长度
     * 
     * @param key
     * @return
     */
    public long strlen(String key) {
        Jedis jedis = getJedis();
        long len = jedis.strlen(key);
        returnJedis(jedis);
        return len;
    }

    /**
     * List长度
     * 
     * @param key
     * @return
     */
    public long llen(String key) {
        return llen(SafeEncoder.encode(key));
    }

    public long llen(byte[] key) {
        Jedis jedis = getJedis();
        long count = jedis.llen(key);
        returnJedis(jedis);
        return count;
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     * 
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(byte[] key, int index, byte[] value) {
        Jedis jedis = getJedis();
        String status = jedis.lset(key, index, value);
        returnJedis(jedis);
        return status;
    }

    public String lset(String key, int index, String value) {
        return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
    }

    /**
     * 在value的相对位置插入记录
     * 
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return
     */
    public long linsert(String key, LIST_POSITION where, String pivot, String value) {
        return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
    }

    public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
        Jedis jedis = getJedis();
        long count = jedis.linsert(key, where, pivot, value);
        returnJedis(jedis);
        return count;
    }

    /**
     * 获取List中指定位置的值
     * 
     * @param key
     * @param index
     * @return
     */
    public String lindex(String key, int index) {
        return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
    }

    public byte[] lindex(byte[] key, int index) {
        Jedis jedis = getJedis();
        byte[] value = jedis.lindex(key, index);
        returnJedis(jedis);
        return value;
    }

    /**
     * 将List中的第一条记录移出List
     * 
     * @param key
     * @return
     */
    public String lpop(String key) {
        return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
    }

    public byte[] lpop(byte[] key) {
        Jedis jedis = getJedis();
        byte[] value = jedis.lpop(key);
        returnJedis(jedis);
        return value;
    }

    /**
     * 将List中最后第一条记录移出List
     * 
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = getJedis();
        String value = jedis.rpop(key);
        returnJedis(jedis);
        return value;
    }

    /**
     * 向List尾部追加记录
     * 
     * @param key
     * @param value
     * @return
     */
    public long lpush(String key, String value) {
        return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
    }

    /**
     * 向List头部追加记录
     * 
     * @param key
     * @param value
     * @return
     */
    public long rpush(String key, String value) {
        Jedis jedis = getJedis();
        long count = jedis.rpush(key, value);
        returnJedis(jedis);
        return count;
    }

    public long rpush(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        long count = jedis.rpush(key, value);
        returnJedis(jedis);
        return count;
    }

    /**
     * 向List中追加记录
     * 
     * @param key
     * @param value
     * @return
     */
    public long lpush(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        long count = jedis.lpush(key, value);
        returnJedis(jedis);
        return count;
    }

    /**
     * 获取指定范围的记录，可以做为分页使用
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = getJedis();
        List<String> list = jedis.lrange(key, start, end);
        returnJedis(jedis);
        return list;
    }

    public List<byte[]> lrange(byte[] key, int start, int end) {
        Jedis jedis = getJedis();
        List<byte[]> list = jedis.lrange(key, start, end);
        returnJedis(jedis);
        return list;
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     * 
     * @param key
     * @param c
     * @param value
     * @return
     */
    public long lrem(byte[] key, int c, byte[] value) {
        Jedis jedis = getJedis();
        long count = jedis.lrem(key, c, value);
        returnJedis(jedis);
        return count;
    }

    public long lrem(String key, int c, String value) {
        return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(byte[] key, int start, int end) {
        Jedis jedis = getJedis();
        String str = jedis.ltrim(key, start, end);
        returnJedis(jedis);
        return str;
    }

    public String ltrim(String key, int start, int end) {
        return ltrim(SafeEncoder.encode(key), start, end);
    }
    
    /**
     * 管道set方法
     * 
     * @param key
     * @param value
     * @param pipeline
     */
    public void set(String key, byte[] value, Pipeline pipeline) {
        pipeline.set(SafeEncoder.encode(key), value);
    }
    
    /**
     * 返回结果
     * 
     * @param pipeline
     * @return
     */
    public List<Object> syncAndReturnAll(Pipeline pipeline) {
        return pipeline.syncAndReturnAll();
    }
}
