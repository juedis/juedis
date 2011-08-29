package com.taobao.juedis.io;

import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.juedis.exceptions.JudisException;
import com.taobao.juedis.io.serializations.SerializationFactory;
import com.taobao.juedis.io.serializations.SerializationHandler;
import com.taobao.juedis.packet.JudisRequest;
import com.taobao.juedis.packet.JudisResponse;

/**
 * 
 * @Description:
 * 
 * @author 秋年
 * 
 * @date 2011-8-24 下午02:34:07
 * 
 */
public class JuedisClient {

	public static Logger logger = LoggerFactory.getLogger(JuedisClient.class);

	private ArrayBlockingQueue<ArrayBlockingQueue<JudisResponse>> responses = new ArrayBlockingQueue<ArrayBlockingQueue<JudisResponse>>(
			3000);

	private SerializationHandler serialization;

	private ArrayBlockingQueue<JudisRequest> requests = new ArrayBlockingQueue<JudisRequest>(
			2000);

	public ThreadLocal<Boolean> ISINMULTI = new ThreadLocal<Boolean>();

	private IoSession session;

	private String url;

	private String port;

	private String serType; // 序列化类型

	public void sendRequest(JudisRequest request) throws InterruptedException {
		requests.put(request);
	}

	public Object invoke(JudisRequest request) {
		long defaultTimeOut = 5000;
		return invoke(request, defaultTimeOut);
	}

	public Object invoke(JudisRequest request, long timeOut) {
		ArrayBlockingQueue<JudisResponse> queue = new ArrayBlockingQueue<JudisResponse>(
				1);
		request.setQueue(queue);

		JudisResponse response = null;
		try {
			sendRequest(request);
			response = queue.poll(timeOut, TimeUnit.MILLISECONDS);
			if (response == null) {
				throw new JudisException(
						"redis client invoke timeout,timeout is: " + timeOut);
			} else if (response.getResult() instanceof JudisException) {
				throw (JudisException) response.getResult();
			}
		} catch (InterruptedException e) {
			throw new JudisException("redis client invoke error", e);
		}

		logger.debug("current responses size: " + responses.size());

		return response.getResult();
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public ArrayBlockingQueue<ArrayBlockingQueue<JudisResponse>> getResponses() {
		return responses;
	}

	public void setResponses(
			ArrayBlockingQueue<ArrayBlockingQueue<JudisResponse>> responses) {
		this.responses = responses;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public IoSession getSession() {
		return session;
	}

	public ArrayBlockingQueue<JudisRequest> getRequests() {
		return requests;
	}

	public void setRequests(ArrayBlockingQueue<JudisRequest> requests) {
		this.requests = requests;
	}

	public SerializationHandler getSerialization() {
		return serialization;
	}

	public void setSerialization(SerializationHandler serialization) {
		this.serialization = serialization;
	}

	public String getSerType() {
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public void checkCurrentThreadIsInMulti() {
		if (ISINMULTI.get() != null && ISINMULTI.get()) {
			throw new JudisException(
					"Cannot use Jedis when in Multi. Please use JudisTransaction instead.");
		}
	}

	public void connection() {
		int processorCount = Runtime.getRuntime().availableProcessors() + 1;
		NioSocketConnector connector = new NioSocketConnector(processorCount);
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("mdc", new MdcInjectionFilter());
		// 协议解析（粘包 半包处理）
		serialization = SerializationFactory.get(serType);
		JudisProtocolDecoder decoder = new JudisProtocolDecoder(serialization);
		JudisProtocolEncoder encoder =  new JudisProtocolEncoder(serialization);
		
		JudisProtocolCodecFilter protocolFilter = new JudisProtocolCodecFilter(encoder,decoder);
		connector.getFilterChain().addLast("codec", protocolFilter);
		connector.getSessionConfig().setTcpNoDelay(true);
		JudiseHandler ioHandler = new JudiseHandler();
		connector.setHandler(ioHandler);
		ConnectFuture future = connector.connect(new InetSocketAddress(url,Integer.valueOf(port)));
		future.awaitUninterruptibly();
		session = future.getSession();
		if (session == null || !session.isConnected()) {
			throw new JudisException("create socket client falied!");
		}
		ioHandler.setClient(this);
		new Thread(new ProcessRequestThread(this)).start();
	}

	public static class ProcessRequestThread implements Runnable {

		public static Logger logger = LoggerFactory
				.getLogger(ProcessRequestThread.class);

		JuedisClient client;

		public ProcessRequestThread(JuedisClient client) {
			this.client = client;
		}

		public void run() {
			while (true) {
				try {
					JudisRequest request = client.getRequests().take();
					if (request != null) {
						client.getResponses().add(request.getQueue());
						client.getSession().write(request);
					}
				} catch (InterruptedException e) {
					logger.error("process request thread is Interrupted", e);
				}
			}
		}
	}

}
