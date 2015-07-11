package com.mavlink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.rxtx.PortReadSerial;

public class Test {

	private static  byte[] buffer = null;
	
	public void set(byte[] newBuffer){
		buffer = newBuffer;
	}
	
	public void get(){
		System.out.println(Arrays.toString(buffer));
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		/*PortReadSerial prs = new PortReadSerial();
		//MavlinkParser map = new MavlinkParser();
		prs.openPortAndListen();
		prs.getInputStream();
		
		//System.out.println(Arrays.toString(prs.));
		//System.out.println(map.getMassager());
		StringBuffer sb = new StringBuffer();
		sb.append(0);
		sb.append(1);
		sb.append(2);
		System.out.println(sb);
		
		Test test  = new Test();
		ByteArrayTransfer bat = new ByteArrayTransfer();
		String a = "-14-54127-4300000000000039";
		byte[] b = a.getBytes();
		System.out.println(Arrays.toString(b));	
		byte[] c = new byte[]{45, 49, 52, 45, 53, 52, 49, 50, 55, 45, 52, 51, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 51, 57};
		
		System.out.println(bat.byteToBaseType("long",0, 8, c));
		System.out.println(bat.byteToBaseType("int", 8, 9, c));
		System.out.println(bat.byteToBaseType("int", 9, 11, c));
		System.out.println(bat.byteToBaseType("int", 11, 13, c));
		System.out.println(bat.byteToBaseType("float",13, 17, c));
		System.out.println(bat.byteToBaseType("float",17, 21, c));
		System.out.println(bat.byteToBaseType("float",21, 22, c));
		System.out.println(bat.byteToBaseType("float",22, 26, c));*/
		
		PX4Flow px = new PX4Flow();
		px.refresh();
	}
	
}
