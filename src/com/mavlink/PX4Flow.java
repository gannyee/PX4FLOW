package com.mavlink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.rxtx.PortReadSerial;

public class PX4Flow extends MavlinkParser {
	PortReadSerial prs = new PortReadSerial();
	
	MavlinkParser mp = new MavlinkParser();
	byte[] buffer = new byte[2048];
	int numBytes;
	byte[] c;
	ByteArrayTransfer bat = new ByteArrayTransfer();
	Float distance;
	int quality;
	InputStream is;
	public PX4Flow(){
		prs.openPortAndListen();
		
	}
	public void refresh() {
		
		is = prs.getInputStream();
			try {
				while (is.available() > 0) {
					numBytes = is.read(buffer);
					// System.out.println("true");
					mp.process(buffer);
					// if(mp.getTargetMassager().length() != 0)
					System.out.println(Arrays.toString(mp.getTargetMassager()
							.getBytes()));
					c = mp.getTargetMassager().getBytes();
					if(c.length > 0){
					System.out.println(bat.byteToBaseType("long",0, 8, c));
					System.out.println(bat.byteToBaseType("int", 8, 9, c));
					System.out.println(bat.byteToBaseType("int", 9, 11, c));
					System.out.println(bat.byteToBaseType("int", 11, 13, c));
					System.out.println(bat.byteToBaseType("float",13, 17, c));
					System.out.println(bat.byteToBaseType("float",17, 21, c));
					System.out.println(bat.byteToBaseType("int",21, 22, c));
					System.out.println(bat.byteToBaseType("float",22, 26, c));
					
					}
				}
				// System.out.println(Arrays.toString(buffer));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public float getDistance(){
		distance = Float.valueOf(bat.byteToBaseType("float",21, 22, c).toString());
		return distance;
	}
	
	public int getQuality(){
		quality = Integer.valueOf(bat.byteToBaseType("int",21, 22, c).toString());
		return quality;
	}
}
