package com.mavlink;

import java.util.Arrays;

public class ByteArrayTransfer {

	// Byte to int, float, Long
	public static Object byteToBaseType(String type, int from, int to, byte[] array) {
		Object result = null;
		if (type.equals("int")) {
			byte[] b = new byte[] { 0, 0, 0, 0 };
			for (int i = from,j = 0; i < to; i++,j ++) {
				b[j] = array[i];
			}
			System.out.println("int / float: " + Arrays.toString(b));
			int s = 0;
			int s0 = b[0] & 0xff;
			int s1 = b[1] & 0xff;
			int s2 = b[2] & 0xff;
			int s3 = b[3] & 0xff;
			s3 <<= 24;
			s2 <<= 16;
			s1 <<= 8;
			s = s0 | s1 | s2 | s3;
			result = s;
		} else if (type.equals("long")) {
			byte[] b = new byte[] {0,0,0,0,0,0,0,0};
			for (int i = from,j = 0; i < to; i++,j ++) {
				b[j] = array[i];
			}
			System.out.println("long: " + Arrays.toString(b));
			long s = 0;
			long s0 = b[0] & 0xff;
			long s1 = b[1] & 0xff;
			long s2 = b[2] & 0xff;
			long s3 = b[3] & 0xff;
			long s4 = b[4] & 0xff;
			long s5 = b[5] & 0xff;
			long s6 = b[6] & 0xff;
			long s7 = b[7] & 0xff;
			s1 <<= 8;
			s2 <<= 8 * 2;
			s3 <<= 8 * 3;
			s4 <<= 8 * 4;
			s5 <<= 8 * 5;
			s6 <<= 8 * 6;
			s7 <<= 8 * 7;
			s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
			result = s;
		} else if (type.equals("float")) {
			result = Float.parseFloat(byteToBaseType("int",from, to, array).toString());
		}
		return result;
	}
}
