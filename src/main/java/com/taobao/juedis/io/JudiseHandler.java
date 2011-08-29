package com.taobao.juedis.io;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.juedis.exceptions.JudisException;
import com.taobao.juedis.packet.JudisResponse;

/**
 * 
 * @Description:
 * 
 * @author ÇïÄê
 * 
 * @date 2011-8-24 ÏÂÎç03:21:50
 * 
 */
public class JudiseHandler extends IoHandlerAdapter {

	public static Logger logger = LoggerFactory.getLogger(JudiseHandler.class);

	private JuedisClient client;

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof JudisResponse) {
			JudisResponse response = (JudisResponse)message;
			processResult(response);
		}

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {

		logger.error(cause.getMessage());
		cause.printStackTrace();
		if (!(cause instanceof IOException)) {
			session.close(true);
		}
	}

	public void sessionClosed(IoSession session) throws Exception {
	}

	private void processResult(JudisResponse result) {
			try {
				ArrayBlockingQueue<JudisResponse> queue = client.getResponses().poll();
				if (queue != null) {
					queue.put(result);
					logger.debug("put response in request queue ,time is:"
							+ System.currentTimeMillis());
				} 
			} catch (InterruptedException e) {
				throw new JudisException("put response error", e);
			}
	}

	public JuedisClient getClient() {
		return client;
	}

	public void setClient(JuedisClient client) {
		this.client = client;
	}

}
