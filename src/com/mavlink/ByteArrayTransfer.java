package com.mavlink;

import java.util.Arrays;
import java.util.Vector;

import com.rxtx.PortReadSerial;

public class ByteArrayTransfer extends PortReadSerial{

	// Byte to int, float, Long
	public static Object byteToBaseType(String type, int from, int to, Vector<Byte> array) {
		Object result = null;
		Vector<Byte> b = new Vector<Byte>();
		
		if (type.equals("int")) {
			if(to - from == 0){
				b.add(array.get(from));
				b.add((byte)0);
				b.add((byte)0);
				b.add((byte)0);
			}else if(to - from == 2){
				b.add(array.get(from));
				b.add(array.get(from + 1));
				b.add((byte)0);
				b.add((byte)0);
			}else if(to - from == 4){
				b.add(array.get(from));
				b.add(array.get(from + 1));
				b.add(array.get(from + 2));
				b.add(array.get(from + 3));
			}
			//System.out.println("int / float: " + Arrays.toString(b));
			int s = 0;
			int s0 = b.get(0) & 0xff;
			int s1 = b.get(1) & 0xff;
			int s2 = b.get(2) & 0xff;
			int s3 = b.get(3) & 0xff;
			s1 <<= 8 * 1;
			s2 <<= 8 * 2;
			s3 <<= 8 * 3;
			s = s0 | s1 | s2 | s3;
			result = s;
		} else if (type.equals("long")) {
			//System.out.println("long: " + Arrays.toString(b));
			for (int i = from; i < to; i++) {
				b.add(array.get(i));
			}
			long s = 0;
			long s0 = b.get(0) & 0xff;
			long s1 = b.get(1) & 0xff;
			long s2 = b.get(2) & 0xff;
			long s3 = b.get(3) & 0xff;
			long s4 = b.get(4) & 0xff;
			long s5 = b.get(5) & 0xff;
			long s6 = b.get(6) & 0xff;
			long s7 = b.get(7) & 0xff;
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
			result = Float.intBitsToFloat(Integer.valueOf(byteToBaseType("int",from, to, array).toString()));
		}
		return result;
	}
	
	public int getDistance(Object distance){
		float grn_dis = (float) ((5 - (Float.valueOf(distance.toString()))) * 20.5);
		return (int) grn_dis ;
	}
	
	public int getQuality(Object quality){
		return (int) ((255 - Integer.valueOf(quality.toString())) * 0.392);
		
	}
}
