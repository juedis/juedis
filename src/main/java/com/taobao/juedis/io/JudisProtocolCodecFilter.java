package com.taobao.juedis.io;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-24 ÏÂÎç04:06:55
 *
 */
public class JudisProtocolCodecFilter extends ProtocolCodecFilter {


	public JudisProtocolCodecFilter(ProtocolEncoder encoder,
			ProtocolDecoder decoder) {
		super(encoder, decoder);
	}

}
