package com.rxtx;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class PortTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommPortIdentifier portIdentifier = null;
		
		Enumeration<?> allPorts = CommPortIdentifier.getPortIdentifiers();
		Vector<String> a = new Vector<String>();
		int i = 0;
		while (allPorts.hasMoreElements()) {
			portIdentifier = (CommPortIdentifier) allPorts.nextElement();
			a.add(portIdentifier.getName().toString());
			System.out.println("串口： " + portIdentifier.getName());
		}
		System.out.println(Arrays.toString(a.toArray()));
		CommPortIdentifier com1 = null;
		CommPortIdentifier com2 = null;
		SerialPort serialPort1 = null;
		SerialPort serialPort2 = null;
		try {
			
			com1 = CommPortIdentifier.getPortIdentifier("COM1");
			//com2 = CommPortIdentifier.getPortIdentifier("COM2");
			serialPort1 = (SerialPort) com1.open("COM1Writer", 1000);
			serialPort1.setSerialPortParams(9600, SerialPort.DATABITS_8,// 数据位数
					SerialPort.STOPBITS_1,// 停止位
					SerialPort.PARITY_NONE// 奇偶位
					);
			
			OutputStream outputStream = new BufferedOutputStream(serialPort1.getOutputStream());
			outputStream.write(new byte[] {'H','e','l','l','o',
                    ' ','W','o','r','l','d','!'});
			System.out.println("successful");
			outputStream.flush();
//			outputStream.close();
//			serialPort1.close();
			//serialPort2 = (SerialPort) com2.open("OpenerAndCloser", 1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
//		serialPort2.close();
	}

}
