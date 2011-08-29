package com.taobao.juedis.io.serializations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-29 ÉÏÎç09:34:50
 *
 */
public class HessianSerialization implements SerializationHandler {

	public static Logger logger = LoggerFactory.getLogger(HessianSerialization.class);
	
	private static final SerializerFactory _serializerFactory = new SerializerFactory();   

	public Object decode(byte[] bytes){
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);   
			Hessian2Input h2in = new Hessian2Input(in);   
			h2in.setSerializerFactory(_serializerFactory); 
			return h2in.readObject();
		} catch (IOException e) {
			logger.error("HessianSerialization decode exception ", e);
			throw new RuntimeException(e);
		}   
	}

	public byte[] encode(Object object){
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();   
			Hessian2Output h2out = new Hessian2Output(bout);   
			h2out.setSerializerFactory(_serializerFactory); 
			h2out.writeObject(object);   
			h2out.flush();   
			return bout.toByteArray();
		} catch (IOException e) {
			logger.error("HessianSerialization encode exception", e);
			throw new RuntimeException(e);
		}  
	}

}
