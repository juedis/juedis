package com.taobao.juedis.io.serializations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description:
 * 
 * @author ÇïÄê
 * 
 * @date 2011-8-24 ÏÂÎç04:46:10
 * 
 */
public class JavaSerialization implements SerializationHandler {

	public static Logger logger = LoggerFactory
			.getLogger(JavaSerialization.class);

	public Object decode(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			logger.error("decode exception", e);
			throw new RuntimeException(e);
		} finally {
			try {
				bis.close();
				ois.close();
			} catch (IOException e) {
			}
		}

	}

	public byte[] encode(Object object) {
		ByteArrayOutputStream bos = null;

		ObjectOutputStream ops = null;

		try {
			bos = new ByteArrayOutputStream();

			ops = new ObjectOutputStream(bos);

			ops.writeObject(object);

			return bos.toByteArray();
		} catch (Exception e) {
			logger.error("encode exception", e);
			throw new RuntimeException(e);
		} finally {
			try {
				bos.close();
				ops.close();
			} catch (IOException e) {
			}
		}
	}

}
