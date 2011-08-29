package com.taobao.juedis.io;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.taobao.juedis.core.BinaryJudis.Command;
import com.taobao.juedis.io.serializations.SerializationHandler;
import com.taobao.juedis.packet.JudisRequest;

/**
 * 
 * @Description:
 * 
 * @author ÇïÄê
 * 
 * @date 2011-8-24 ÏÂÎç04:08:10
 * 
 */
public class JudisProtocolEncoder implements ProtocolEncoder {

	public static final int DEFAULT_PORT = 6379;
	public static final int DEFAULT_TIMEOUT = 2000;

	public static final String CHARSET = "UTF-8";

	public static final byte DOLLAR_BYTE = '$';
	public static final byte ASTERISK_BYTE = '*';
	public static final byte PLUS_BYTE = '+';
	public static final byte MINUS_BYTE = '-';
	public static final byte COLON_BYTE = ':';

	private SerializationHandler template;

	public JudisProtocolEncoder(SerializationHandler template) {
		this.template = template;
	}

	public void dispose(IoSession arg0) throws Exception {
	}

	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out)
			throws Exception {
		
		if (obj instanceof JudisRequest) {
			JudisRequest request = (JudisRequest) obj;
			Command command = request.getCmd();
			byte[] combyte = command.raw;
			
			IoBuffer buffer =  IoBuffer.allocate(1000).setAutoExpand(true);
			
			JudisOutPutStream jos =  new JudisOutPutStream(buffer);
			
			jos.write(ASTERISK_BYTE);
			jos.writeIntCrLf((request.getObjs().size() + 1));
			jos.write(DOLLAR_BYTE);
            jos.writeIntCrLf(combyte.length);
            jos.write(combyte);
            jos.writeCrLf();

            for (final byte[] arg : request.getObjs()) {
            	jos.write(DOLLAR_BYTE);
            	jos.writeIntCrLf(arg.length);
            	jos.write(arg);
                jos.writeCrLf();
            }
            buffer.flip();
			out.write(buffer);
		}
	}

	public SerializationHandler getTemplate() {
		return template;
	}

	public void setTemplate(SerializationHandler template) {
		this.template = template;
	}

}
