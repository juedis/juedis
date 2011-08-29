package com.taobao.juedis.io;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;

/**
 *
 * @Description: 
 *
 * @author ÇïÄê 
 *
 * @date 2011-8-24 ÏÂÎç05:07:11
 *
 */
public class JudisOutPutStream {

	private IoBuffer buffer ;
	
	public JudisOutPutStream(IoBuffer buffer) {
		this.buffer = buffer;
	}
	

    public void write(final byte b) throws IOException {
    	buffer.put(b);
    }
    
    public void write(final byte[] b) throws IOException {
    	buffer.put(b);
    }


    public void writeAsciiCrLf(final String in) throws IOException {
        final int size = in.length();

        for (int i = 0; i != size; ++i) {
            buffer.put((byte) in.charAt(i));
        }
        writeCrLf();
    }

    public static boolean isSurrogate(final char ch) {
        return ch >= Character.MIN_SURROGATE && ch <= Character.MAX_SURROGATE;
    }

    public static int utf8Length (final String str) {
        int strLen = str.length(), utfLen = 0;
        for(int i = 0; i != strLen; ++i) {
            char c = str.charAt(i);
            if (c < 0x80) {
                utfLen++;
            } else if (c < 0x800) {
                utfLen += 2;
            } else if (isSurrogate(c)) {
                i++;
                utfLen += 4;
            } else {
                utfLen += 3;
            }
        }
        return utfLen;
    }

	public void writeCrLf() throws IOException {
       buffer.put((byte)'\r');
       buffer.put((byte)'\n');
    }

    public void writeUtf8CrLf(final String str) throws IOException {
        int strLen = str.length();

        int i;
        for (i = 0; i < strLen; i++) {
            char c = str.charAt(i);
            if (!(c < 0x80)) break;
            buffer.put((byte) c);
        }

        for (; i < strLen; i++) {
            char c = str.charAt(i);
            if (c < 0x80) {
            	buffer.put((byte) c);
            } else if (c < 0x800) {
            	buffer.put((byte)(0xc0 | (c >> 6)));
            	buffer.put((byte)(0x80 | (c & 0x3f)));
            } else if (isSurrogate(c)) {
                int uc = Character.toCodePoint(c, str.charAt(i++));
                buffer.put(((byte)(0xf0 | ((uc >> 18)))));
                buffer.put(((byte)(0x80 | ((uc >> 12) & 0x3f))));
                buffer.put(((byte)(0x80 | ((uc >> 6) & 0x3f))));
                buffer.put(((byte)(0x80 | (uc & 0x3f))));
            } else {
            	buffer.put(((byte)(0xe0 | ((c >> 12)))));
            	buffer.put(((byte)(0x80 | ((c >> 6) & 0x3f))));
            	buffer.put(((byte)(0x80 | (c & 0x3f))));
            }
        }

        writeCrLf();
    }

    private final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    private final static byte[] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    };

    private final static byte[] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };

    private final static byte[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    public void writeIntCrLf(int value) throws IOException {
    	int count = buffer.position();
        if (value < 0) {
            write((byte)'-');
            value = -value;
        }

        int size = 0;
        while (value > sizeTable[size])
            size++;

        size++;

        int q, r;
        int charPos = count + size;

        int start =  charPos;
        
        while (value >= 65536) {
            q = value / 100;
            r = value - ((q << 6) + (q << 5) + (q << 2));
            value = q;
            buffer.position(--charPos);
            
            buffer.put(DigitOnes[r]);
            buffer.position(--charPos);
            buffer.put(DigitTens[r]);
        }

        for (; ;) {
            q = (value * 52429) >>> (16 + 3);
            r = value - ((q << 3) + (q << 1));
            buffer.position(--charPos);
            buffer.put(digits[r]);
            value = q;
            if (value == 0) break;
        }
        count += size;

        buffer.position(start);
        
        writeCrLf();
    }

}
