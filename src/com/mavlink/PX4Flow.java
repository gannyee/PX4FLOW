package com.mavlink;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import com.rxtx.PortReadSerial;

public class PX4Flow extends MavlinkParser{
	PortReadSerial prs = new PortReadSerial();
	int count = 0;
	MavlinkParser mp = new MavlinkParser();
	byte[] buffer = new byte[2048];
	int numBytes;
	byte[] c;
	ByteArrayTransfer bat = new ByteArrayTransfer();
	Float distance;
	int quality;
	InputStream is;
	private boolean refreshed;
	public PX4Flow(){
		new MavlinkParser();
	}
	
	public void refresh() {
		is = getInputStream();
		try {
			while(is.read(buffer) > 0){
			process(buffer);
			if(getTargetMassager().size()>0){
			System.out.println("Messager: " + getTargetMassager());
			for(int i = 0;i < getTargetMassager().size();i ++){
				System.out.println("1: " + byteToBaseType("long",0,8, getTargetMassager().get(i)));
				System.out.println("2: " + byteToBaseType("int",24,24, getTargetMassager().get(i)));
				System.out.println("3: " + byteToBaseType("int",20,22,getTargetMassager().get(i)));
				System.out.println("4: " + byteToBaseType("int",22,24,getTargetMassager().get(i)));
				System.out.println("5: " + byteToBaseType("float",8,12,getTargetMassager().get(i)));
				System.out.println("6: " + byteToBaseType("float",12,16,getTargetMassager().get(i)));
				System.out.println("7: " + byteToBaseType("int",25,25,getTargetMassager().get(i)));
				System.out.println("8: " + byteToBaseType("float",16,20,getTargetMassager().get(i)));
			}
			}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
