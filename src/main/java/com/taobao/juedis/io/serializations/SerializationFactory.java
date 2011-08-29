package com.taobao.juedis.io.serializations;

/**
 * 
 * @Description:
 * 
 * @author ����
 * 
 * @date 2011-8-29 ����10:19:56
 * 
 */
public class SerializationFactory {

	public static final String JAVA_SERIALIZATION = "java";

	public static final String HESSIAN_SERIALIZATION = "hessian";

	public static SerializationHandler get(String type) {
		if (HESSIAN_SERIALIZATION.equalsIgnoreCase(type)) {
			return new HessianSerialization();
		} else {
			return new JavaSerialization();
		}
	}
}
