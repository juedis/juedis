package com.taobao.juedis.io.serializations;


/**
 *
 * @Description: 
 *
 * @author ���� 
 *
 * @date 2011-8-24 ����04:45:11
 *
 */
public interface SerializationHandler {
	
	public byte[] encode(Object object);
	
	public Object decode(byte[] bytes);
}
