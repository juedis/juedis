package com.taobao.juedis.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.util.SafeEncoder;

import com.taobao.juedis.io.JuedisClient;
import com.taobao.juedis.packet.JudisRequest;

/**
 * 
 * @Description:
 * 
 * @author ÇïÄê
 * 
 * @date 2011-8-25 ÏÂÎç06:26:11
 * 
 */
public class BinaryJudis implements BinaryJudisCommands {

	protected JuedisClient client;

	public BinaryJudis(JuedisClient client) {
		this.client = client;
	}

	public Long append(byte[] key, byte[] value) {
		checkIsInMulti();
		JudisRequest request = new JudisRequest(Command.APPEND);
		request.addObj(key);
		request.addObj(value);
		return (Long) client.invoke(request);
	}

	public Long decr(byte[] key) {
		return null;
	}

	public Long decrBy(byte[] key, long integer) {
		return null;
	}

	public Boolean exists(byte[] key) {
		return null;
	}

	public Long expire(byte[] key, int seconds) {
		return null;
	}

	public Long expireAt(byte[] key, long unixTime) {
		return null;
	}

	public Object get(byte[] key) {
		JudisRequest request = new JudisRequest(Command.GET);
		request.addObj(key);
		return  client.invoke(request);
	}

	public byte[] getSet(byte[] key, byte[] value) {
		return null;
	}

	public Long hdel(byte[] key, byte[] field) {
		return null;
	}

	public Boolean hexists(byte[] key, byte[] field) {
		return null;
	}

	public byte[] hget(byte[] key, byte[] field) {
		return null;
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		return null;
	}

	public Long hincrBy(byte[] key, byte[] field, long value) {
		return null;
	}

	public Set<byte[]> hkeys(byte[] key) {
		return null;
	}

	public Long hlen(byte[] key) {
		return null;
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		return null;
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		return null;
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		return null;
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		return null;
	}

	public Collection<byte[]> hvals(byte[] key) {
		return null;
	}

	public Long incr(byte[] key) {
		return null;
	}

	public Long incrBy(byte[] key, long integer) {
		return null;
	}

	public byte[] lindex(byte[] key, int index) {
		return null;
	}

	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot,
			byte[] value) {
		return null;
	}

	public Long llen(byte[] key) {
		return null;
	}

	public byte[] lpop(byte[] key) {
		return null;
	}

	public Long lpush(byte[] key, byte[] string) {
		return null;
	}

	public List<byte[]> lrange(byte[] key, int start, int end) {
		return null;
	}

	public Long lrem(byte[] key, int count, byte[] value) {
		return null;
	}

	public String lset(byte[] key, int index, byte[] value) {
		return null;
	}

	public String ltrim(byte[] key, int start, int end) {
		return null;
	}

	public byte[] rpop(byte[] key) {
		return null;
	}

	public Long rpush(byte[] key, byte[] string) {
		return null;
	}

	public Long sadd(byte[] key, byte[] member) {
		return null;
	}

	public Long scard(byte[] key) {
		return null;
	}

	public String set(byte[] key, byte[] value) {
		checkIsInMulti();
		JudisRequest request = new JudisRequest(Command.SET);
		request.addObj(key);
		request.addObj(value);
		return (String) client.invoke(request);
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		return null;
	}

	public Long setnx(byte[] key, byte[] value) {
		return null;
	}

	public Boolean sismember(byte[] key, byte[] member) {
		return null;
	}

	public Set<byte[]> smembers(byte[] key) {
		return null;
	}

	public List<byte[]> sort(byte[] key) {
		return null;
	}

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		return null;
	}

	public byte[] spop(byte[] key) {
		return null;
	}

	public byte[] srandmember(byte[] key) {
		return null;
	}

	public Long srem(byte[] key, byte[] member) {
		return null;
	}

	public byte[] substr(byte[] key, int start, int end) {
		return null;
	}

	public Long ttl(byte[] key) {
		return null;
	}

	public String type(byte[] key) {
		return null;
	}

	public Long zadd(byte[] key, double score, byte[] member) {
		return null;
	}

	public Long zcard(byte[] key) {
		return null;
	}

	public Long zcount(byte[] key, double min, double max) {
		return null;
	}

	public Double zincrby(byte[] key, double score, byte[] member) {
		return null;
	}

	public Set<byte[]> zrange(byte[] key, int start, int end) {
		return null;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		return null;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max,
			int offset, int count) {
		return null;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		return null;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min,
			double max, int offset, int count) {
		return null;
	}

	public Set<Tuple> zrangeWithScores(byte[] key, int start, int end) {
		return null;
	}

	public Long zrank(byte[] key, byte[] member) {
		return null;
	}

	public Long zrem(byte[] key, byte[] member) {
		return null;
	}

	public Long zremrangeByRank(byte[] key, int start, int end) {
		return null;
	}

	public Long zremrangeByScore(byte[] key, double start, double end) {
		return null;
	}

	public Set<byte[]> zrevrange(byte[] key, int start, int end) {
		return null;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		return null;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min,
			int offset, int count) {
		return null;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min) {
		return null;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
			double min, int offset, int count) {
		return null;
	}

	public Set<Tuple> zrevrangeWithScores(byte[] key, int start, int end) {
		return null;
	}

	public Long zrevrank(byte[] key, byte[] member) {
		return null;
	}

	public Double zscore(byte[] key, byte[] member) {
		return null;
	}

	public void multi() {
		JudisRequest req = new JudisRequest(Command.MULTI);
		req.addObj(new byte[0]);
		client.ISINMULTI.set(true);
	}

	public void discard() {
		JudisRequest req = new JudisRequest(Command.DISCARD);
		req.addObj(new byte[0]);
		client.ISINMULTI.set(false);
	}

	public void exec() {
		JudisRequest req = new JudisRequest(Command.EXEC);
		req.addObj(new byte[0]);
		client.ISINMULTI.set(false);
	}

	protected void checkIsInMulti() {
		client.checkCurrentThreadIsInMulti();
	}

	public static enum Command {
		PING, SET, GET, QUIT, EXISTS, DEL, TYPE, FLUSHDB, KEYS, RANDOMKEY, RENAME, RENAMENX, RENAMEX, DBSIZE, EXPIRE, EXPIREAT, TTL, SELECT, MOVE, FLUSHALL, GETSET, MGET, SETNX, SETEX, MSET, MSETNX, DECRBY, DECR, INCRBY, INCR, APPEND, SUBSTR, HSET, HGET, HSETNX, HMSET, HMGET, HINCRBY, HEXISTS, HDEL, HLEN, HKEYS, HVALS, HGETALL, RPUSH, LPUSH, LLEN, LRANGE, LTRIM, LINDEX, LSET, LREM, LPOP, RPOP, RPOPLPUSH, SADD, SMEMBERS, SREM, SPOP, SMOVE, SCARD, SISMEMBER, SINTER, SINTERSTORE, SUNION, SUNIONSTORE, SDIFF, SDIFFSTORE, SRANDMEMBER, ZADD, ZRANGE, ZREM, ZINCRBY, ZRANK, ZREVRANK, ZREVRANGE, ZCARD, ZSCORE, MULTI, DISCARD, EXEC, WATCH, UNWATCH, SORT, BLPOP, BRPOP, AUTH, SUBSCRIBE, PUBLISH, UNSUBSCRIBE, PSUBSCRIBE, PUNSUBSCRIBE, ZCOUNT, ZRANGEBYSCORE, ZREVRANGEBYSCORE, ZREMRANGEBYRANK, ZREMRANGEBYSCORE, ZUNIONSTORE, ZINTERSTORE, SAVE, BGSAVE, BGREWRITEAOF, LASTSAVE, SHUTDOWN, INFO, MONITOR, SLAVEOF, CONFIG, STRLEN, SYNC, LPUSHX, PERSIST, RPUSHX, ECHO, LINSERT, DEBUG, BRPOPLPUSH, SETBIT, GETBIT, SETRANGE, GETRANGE;

		public final byte[] raw;

		Command() {
			raw = SafeEncoder.encode(this.name());
		}
	}

	public static enum Keyword {
		AGGREGATE, ALPHA, ASC, BY, DESC, GET, LIMIT, MESSAGE, NO, NOSORT, PMESSAGE, PSUBSCRIBE, PUNSUBSCRIBE, OK, ONE, QUEUED, SET, STORE, SUBSCRIBE, UNSUBSCRIBE, WEIGHTS, WITHSCORES, RESETSTAT;
		public final byte[] raw;

		Keyword() {
			raw = SafeEncoder.encode(this.name().toLowerCase());
		}

	}

}
