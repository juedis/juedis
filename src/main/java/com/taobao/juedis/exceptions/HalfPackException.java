package com.taobao.juedis.exceptions;
/**
 *
 * @Description: 半包异常，主要用于控制IO时的流程走向
 *
 * @author 秋年 
 *
 * @date 2011-8-25 下午04:01:01
 *
 */
public class HalfPackException extends RuntimeException {
	
	private static final long serialVersionUID = -7324360350643453864L;
	
	public HalfPackException(){
		super();
	}

}
