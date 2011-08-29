package com.taobao.juedis.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.taobao.juedis.exceptions.HalfPackException;
import com.taobao.juedis.exceptions.JudisException;
import com.taobao.juedis.io.serializations.SerializationHandler;
import com.taobao.juedis.packet.JudisResponse;

/**
 * 
 * @Description:
 * 
 * @author 秋年
 * 
 * @date 2011-8-24 下午06:10:20
 * 
 */
public class JudisProtocolDecoder extends CumulativeProtocolDecoder {

	public static final byte DOLLAR_BYTE = '$';
	public static final byte ASTERISK_BYTE = '*';
	public static final byte PLUS_BYTE = '+';
	public static final byte MINUS_BYTE = '-';
	public static final byte COLON_BYTE = ':';

	private SerializationHandler template;

	public JudisProtocolDecoder(SerializationHandler template) {
		this.template = template;
	}

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {

		boolean finish = true;
		int start = buffer.position();

		JudisInPutStream is = new JudisInPutStream(buffer);

		try {
			if (buffer.hasRemaining()) {
				byte b = buffer.get();
				if (b == MINUS_BYTE) {
					Object result = processError(is);
					decode(result, MINUS_BYTE, out);
				} else if (b == ASTERISK_BYTE) {
					Object result = processMultiBulkReply(is);
					decode(result, ASTERISK_BYTE, out);
				} else if (b == COLON_BYTE) {
					Object result = processInteger(is);
					decode(result, COLON_BYTE, out);
				} else if (b == DOLLAR_BYTE) {
					Object result = processBulkReply(is);
					decode(result, DOLLAR_BYTE, out);
				} else if (b == PLUS_BYTE) {
					Object result = processStatusCodeReply(is);
					decode(result, PLUS_BYTE, out);
				} else {
					throw new JudisException("Unknown reply: "
							+ (char) b);
				}
			} else {
				finish = false;
			}
		} catch (HalfPackException e) {
			// 如果捕获到半包异常则返回false，让mina重新组包进行操作
			buffer.position(start);
			return false;
		}

		return finish;
	}

	private Object process(final JudisInPutStream is) {

		try {
			if (is.getBuffer().hasRemaining()) {
				byte b = is.getBuffer().get();
				if (b == MINUS_BYTE) {
					return processError(is);
				} else if (b == ASTERISK_BYTE) {
					return processMultiBulkReply(is);
				} else if (b == COLON_BYTE) {
					return processInteger(is);
				} else if (b == DOLLAR_BYTE) {
					return processBulkReply(is);
				} else if (b == PLUS_BYTE) {
					return processStatusCodeReply(is);
				} else {
					throw new JudisException("Unknown reply: "
							+ (char) b);
				}
			} else {
				throw new HalfPackException();
			}
		} catch (IOException e) {
			throw new JudisException("",e);
		}
	}

	private String processError(final JudisInPutStream is) {
		String message = is.readLine();
		return message;
	}

	private String processStatusCodeReply(final JudisInPutStream is) {
		return is.readLine();
	}

	private byte[] processBulkReply(final JudisInPutStream is)
			throws IOException {
		String line = is.readLine();
		byte[] lineByte = line.getBytes();
		byte[] newByte = new byte[lineByte.length - 2];

		for (int i = 0; i < lineByte.length - 2; i++) {
			newByte[i] = lineByte[i];
		}

		String str = new String(newByte, "utf-8");

		int len = Integer.parseInt(str);
		if (len == -1) {
			return null;
		}
		if (is.getBuffer().remaining() < len + 2) {
			throw new HalfPackException();
		}

		int i = 0;
		byte[] read = new byte[len];
		while (is.getBuffer().hasRemaining()) {
			if (i == len) {
				break;
			}
			read[i] = is.getBuffer().get();
			i++;
		}

		is.getBuffer().get();
		is.getBuffer().get();

		return read;
	}

	private Long processInteger(final JudisInPutStream is) {
		String num = is.readLine();
		return Long.valueOf(num);
	}

	private List<Object> processMultiBulkReply(final JudisInPutStream is) {
		int num = Integer.parseInt(is.readLine());
		if (num == -1) {
			return null;
		}
		List<Object> ret = new ArrayList<Object>(num);
		for (int i = 0; i < num; i++) {
			ret.add(process(is));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private void decode(Object obj, byte type, ProtocolDecoderOutput out)
			throws IOException, ClassNotFoundException {
		JudisResponse response = new JudisResponse();
		if (obj == null) {
			response.setResult(null);
		}
		if (type == MINUS_BYTE) {
			response.setResult(obj);
		} else if (type == ASTERISK_BYTE) {
			List<Object> list = (List<Object>) obj;
			List<Object> newList = new ArrayList<Object>(list.size());
			for (Object object : list) {
				if (object instanceof byte[]) {
					byte[] bytes = (byte[]) object;
					newList.add(template.decode(bytes));
				}
				newList.add(object);
			}
			response.setResult(newList);
		} else if (type == COLON_BYTE) {
			response.setResult(obj);
		} else if (type == DOLLAR_BYTE) {
			if (obj instanceof byte[]) {
				byte[] bytes = (byte[]) obj;
				response.setResult(template.decode(bytes));
			}
		} else if (type == PLUS_BYTE) {
			response.setResult(obj);
		}

		out.write(response);
	}

	public SerializationHandler getTemplate() {
		return template;
	}

	public void setTemplate(SerializationHandler template) {
		this.template = template;
	}

}
