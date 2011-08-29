package com.taobao.juedis.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

import com.taobao.juedis.io.JuedisClient;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-29 ÉÏÎç09:58:27
 *
 */
public class Judis extends BinaryJudis implements JudisCommands {
	
	public Judis(JuedisClient client) {
		super(client);
	}

	public Long append(String key, String value) {
		return null;
	}

	public Long decr(String key) {
		return null;
	}

	public Long decrBy(String key, long integer) {
		return null;
	}

	public Boolean exists(String key) {
		return null;
	}

	public Long expire(String key, int seconds) {
		return null;
	}

	public Long expireAt(String key, long unixTime) {
		return null;
	}

	public Object get(String key) {
		return get(encode(key));
	}

	public String getSet(String key, String value) {
		return null;
	}

	public boolean getbit(String key, long offset) {
		return false;
	}

	public String getrange(String key, long startOffset, long endOffset) {
		return null;
	}

	public Long hdel(String key, String field) {
		return null;
	}

	public Boolean hexists(String key, String field) {
		return null;
	}

	public String hget(String key, String field) {
		return null;
	}

	public Map<String, String> hgetAll(String key) {
		return null;
	}

	public Long hincrBy(String key, String field, long value) {
		return null;
	}

	public Set<String> hkeys(String key) {
		return null;
	}

	public Long hlen(String key) {
		return null;
	}

	public List<String> hmget(String key, String... fields) {
		return null;
	}

	public String hmset(String key, Map<String, String> hash) {
		return null;
	}

	public Long hset(String key, String field, String value) {
		return null;
	}

	public Long hsetnx(String key, String field, String value) {
		return null;
	}

	public List<String> hvals(String key) {
		return null;
	}

	public Long incr(String key) {
		return null;
	}

	public Long incrBy(String key, long integer) {
		return null;
	}

	public String lindex(String key, long index) {
		return null;
	}

	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		return null;
	}

	public Long llen(String key) {
		return null;
	}

	public String lpop(String key) {
		return null;
	}

	public Long lpush(String key, String string) {
		return null;
	}

	public List<String> lrange(String key, long start, long end) {
		return null;
	}

	public Long lrem(String key, long count, String value) {
		return null;
	}

	public String lset(String key, long index, String value) {
		return null;
	}

	public String ltrim(String key, long start, long end) {
		return null;
	}

	public String rpop(String key) {
		return null;
	}

	public Long rpush(String key, String string) {
		return null;
	}

	public Long sadd(String key, String member) {
		return null;
	}

	public Long scard(String key) {
		return null;
	}

	public Object set(String key, Object value) {
		return set(encode(key), encode(value));
	}

	public boolean setbit(String key, long offset, boolean value) {
		return false;
	}

	public String setex(String key, int seconds, String value) {
		return null;
	}

	public Long setnx(String key, String value) {
		return null;
	}

	public long setrange(String key, long offset, String value) {
		return 0;
	}

	public Boolean sismember(String key, String member) {
		return null;
	}

	public Set<String> smembers(String key) {
		return null;
	}

	public List<String> sort(String key) {
		return null;
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		return null;
	}

	public String spop(String key) {
		return null;
	}

	public String srandmember(String key) {
		return null;
	}

	public Long srem(String key, String member) {
		return null;
	}

	public String substr(String key, int start, int end) {
		return null;
	}

	public Long ttl(String key) {
		return null;
	}

	public String type(String key) {
		return null;
	}

	public Long zadd(String key, double score, String member) {
		return null;
	}

	public Long zcard(String key) {
		return null;
	}

	public Long zcount(String key, double min, double max) {
		return null;
	}

	public Double zincrby(String key, double score, String member) {
		return null;
	}

	public Set<String> zrange(String key, int start, int end) {
		return null;
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		return null;
	}

	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		return null;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return null;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		return null;
	}

	public Set<Tuple> zrangeWithScores(String key, int start, int end) {
		return null;
	}

	public Long zrank(String key, String member) {
		return null;
	}

	public Long zrem(String key, String member) {
		return null;
	}

	public Long zremrangeByRank(String key, int start, int end) {
		return null;
	}

	public Long zremrangeByScore(String key, double start, double end) {
		return null;
	}

	public Set<String> zrevrange(String key, int start, int end) {
		return null;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return null;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		return null;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		return null;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		return null;
	}

	public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
		return null;
	}

	public Long zrevrank(String key, String member) {
		return null;
	}

	public Double zscore(String key, String member) {
		return null;
	}

	private byte[] encode(Object obj){
		return client.getSerialization().encode(obj);
	}
}
