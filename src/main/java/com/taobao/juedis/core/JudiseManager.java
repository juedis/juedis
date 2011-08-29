//package com.taobao.juedis.core;
//
//import java.net.InetSocketAddress;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.FutureTask;
//
//import org.apache.mina.core.future.ConnectFuture;
//import org.apache.mina.core.session.IoSession;
//import org.apache.mina.filter.logging.LoggingFilter;
//import org.apache.mina.filter.logging.MdcInjectionFilter;
//import org.apache.mina.transport.socket.nio.NioSocketConnector;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import redis.clients.jedis.exceptions.JedisException;
//
//import com.taobao.juedis.exceptions.JudisException;
//import com.taobao.juedis.io.JudisProtocolCodecFilter;
//import com.taobao.juedis.io.JudisProtocolDecoder;
//import com.taobao.juedis.io.JudisProtocolEncoder;
//import com.taobao.juedis.io.JudiseClientFactory;
//import com.taobao.juedis.io.JudiseHandler;
//import com.taobao.juedis.io.JuedisClient;
//import com.taobao.juedis.io.serializations.HessianSerialization;
//import com.taobao.juedis.io.serializations.JavaSerialization;
//
///**
// * 
// * @Description:
// * 
// * @author 秋年
// * 
// * @date 2011-8-29 上午10:11:05
// * 
// */
//public class JudiseManager {
//	private static final Logger logger = LoggerFactory
//			.getLogger(JudiseClientFactory.class);
//
//	private static final int processorCount = Runtime.getRuntime()
//			.availableProcessors() + 1;
//
//	private static final JudiseClientFactory factory = new JudiseClientFactory();
//
//	private final ConcurrentHashMap<String, FutureTask<JuedisClient>> clients = new ConcurrentHashMap<String, FutureTask<JuedisClient>>();
//
//	public static JudiseClientFactory getInstance() {
//		return factory;
//	}
//
//	public JuedisClient get(final String ip, final String port) {
//		if (clients.containsKey(ip)) {
//			try {
//				return clients.get(ip).get();
//			} catch (Exception e) {
//				clients.remove(ip);
//				throw new JudisException(
//						"get redis connection error,targetAddress is " + ip, e);
//			}
//		} else {
//			FutureTask<JuedisClient> task = new FutureTask<JuedisClient>(
//					new Callable<JuedisClient>() {
//						public JuedisClient call() throws Exception {
//							return create(ip, port);
//						}
//					});
//			FutureTask<JuedisClient> existTask = clients.putIfAbsent(ip, task);
//			if (existTask == null) {
//				existTask = task;
//				task.run();
//			}
//			try {
//				return existTask.get();
//			} catch (Exception e) {
//				clients.remove(ip);
//				throw new JedisException(
//						"get redis connection error,targetAddress is " + ip, e);
//			}
//		}
//	}
//
//	public void remove(String key) {
//		clients.remove(key);
//	}
//
//	private JuedisClient create(String ip, String port) {
//		IoSession session = null;
//		NioSocketConnector connector = new NioSocketConnector(processorCount);
//		connector.getFilterChain().addLast("logger", new LoggingFilter());
//		connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
//		// 协议解析（粘包 半包处理）
//		JudisProtocolDecoder decoder = new JudisProtocolDecoder();
//		decoder.setTemplate(new HessianSerialization());
//		JudisProtocolCodecFilter protocolFilter = new JudisProtocolCodecFilter(
//				new JudisProtocolEncoder(new JavaSerialization()), decoder);
//		connector.getFilterChain().addLast("codec", protocolFilter);
//		connector.getSessionConfig().setTcpNoDelay(true);
//		JudiseHandler ioHandler = new JudiseHandler(factory);
//		connector.setHandler(ioHandler);
//		ConnectFuture future = connector.connect(new InetSocketAddress(ip,
//				Integer.valueOf(port)));
//		future.awaitUninterruptibly();
//		session = future.getSession();
//		if (session == null || !session.isConnected()) {
//			logger.error("create socket client falied!");
//			return null;
//		}
//		JuedisClient client = new JuedisClient(session, ip, port);
//		ioHandler.setClient(client);
//		return client;
//	}
//}
