package com.taobao.juedis.exceptions;

/**
 * 
 * @Description:
 * 
 * @author ����
 * 
 * @date 2011-8-24 ����02:44:02
 * 
 */
public class JudisException extends RuntimeException {

	private static final long serialVersionUID = 8765642168054472730L;

	public JudisException(String msg) {
		super(msg);
	}
	
	public JudisException(String msg , Throwable e) {
		super(msg ,e);
	}

}
