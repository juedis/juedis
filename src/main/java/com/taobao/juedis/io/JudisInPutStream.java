package com.taobao.juedis.io;

import org.apache.mina.core.buffer.IoBuffer;

import com.taobao.juedis.exceptions.HalfPackException;

/**
 * 
 * @Description:
 * 
 * @author ����
 * 
 * @date 2011-8-24 ����06:26:11
 * 
 */
public class JudisInPutStream {
	private IoBuffer buffer;

	public JudisInPutStream(IoBuffer buffer) {
		this.buffer = buffer;
	}
	

    public String readLine() {
        int b;
        byte c;
        StringBuilder sb = new StringBuilder();
            while (buffer.hasRemaining()) {
                b = buffer.get();
                if (b == '\r') {
                	 sb.append((char) b);
                    if (buffer.hasRemaining()) {
                    	 c = buffer.get();
                    	 sb.append((char) c);
                    	 if (c == '\n') {
                             break;
                         }
                    }
                } else {
                    sb.append((char) b);
                }
            }
        String reply = sb.toString();
        //�����ǰ�ַ�������'\r\n'��β֤��buffer���Ѿ�û�����ˣ��׳�����쳣
        if(!reply.endsWith("\r\n") || reply.length() == 0){
        	throw new HalfPackException();
        }
        return reply;
    }

	

	public IoBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(IoBuffer buffer) {
		this.buffer = buffer;
	}
	
}
