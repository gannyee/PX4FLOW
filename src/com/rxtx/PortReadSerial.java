package com.rxtx;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.TooManyListenersException;

/**
 * Find usable port Open serial port Read data via opened port
 * 
 * @author gannyee
 * 
 */
public class PortReadSerial extends Observable implements Runnable,
		SerialPortEventListener {

	// Port ID identifier
	private static CommPortIdentifier portId = null;
	// Port ID
	private static final String com = "COM9";
	// Serial port
	private static SerialPort serialPort = null;
	// Baud rate
	private static int baudRate = 9600;
	// Data bites
	private static int dataBites = SerialPort.DATABITS_8;
	// Stop bites
	private static int stopBites = SerialPort.STOPBITS_1;
	// Parity check
	private static int parityChck = SerialPort.PARITY_NONE;

	// Input stream
	private static InputStream inputStream;
	// Output stream
	private static OutputStream outputStream;
	// Data buffer
	StringBuffer stringBuffer = new StringBuffer(128);
	// Whether port is opened
	private static boolean isOpen = false;

	// Open port
	public void openPortAndListen() {
		if (isOpen) {
			closePort();
		}
		try {
			// Get specify port ID number
			portId = CommPortIdentifier.getPortIdentifier(com);
			// Open specify port
			serialPort = (SerialPort) portId.open(com, 1000);
			// get data stream
			inputStream = serialPort.getInputStream();
			outputStream = serialPort.getOutputStream();
			// Add listener
			serialPort.addEventListener(this);
			// Listeners for available even
			serialPort.notifyOnDataAvailable(true);
			// Configure parameters
			serialPort.setSerialPortParams(baudRate, dataBites, stopBites,
					parityChck);
			System.out.println("Port: " + portId.getName()
					+ ": opened successfully!");
			isOpen = true;
		} catch (NoSuchPortException e) {
			// No such port exception
			e.printStackTrace();
			System.out.println("Port: " + portId.getName()
					+ ": can't be fount!");
		} catch (PortInUseException e) {
			// port in used exception
			e.printStackTrace();
			System.out.println("Port: " + portId.getName()
					+ ": be used by other device!");
		} catch (UnsupportedCommOperationException e) {
			// Not allowed operate
			System.out.println("Port operat command not allowed!");
		} catch (TooManyListenersException e) {
			// Too many listeners exception
			e.printStackTrace();
			System.out.println("Port: " + portId.getName()
					+ ": too many listeners!");
		} catch (IOException e) {
			// IO exception
			e.printStackTrace();
			System.out.println("Open port: " + portId.getName() + "failed!");
		}
		Thread readThread = new Thread(this);
		readThread.start();
		//closePort();
	}

	public static void closePort() {
		if (isOpen) {
			try {
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				serialPort.close();
				isOpen = false;
			} catch (IOException ex) {
				System.out.println("Close port failed!");
			}
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEventType()) {
		case SerialPortEvent.BI:// Break interrupt
		case SerialPortEvent.OE:// Overrun error
		case SerialPortEvent.FE:// Framing error
		case SerialPortEvent.PE:// Parity error
		case SerialPortEvent.CD:// Carrier detect
		case SerialPortEvent.CTS:// Clear to send
		case SerialPortEvent.DSR:// Data set ready
		case SerialPortEvent.RI:// Ring indicator 
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY://Output buffer is empty								 
			break;
		case SerialPortEvent.DATA_AVAILABLE://Data available at the serial port
											 
			byte[] readBuffer = new byte[1024];
			
			try {
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
				}
				System.out.print(new String(readBuffer));
			} catch (IOException e) {
			}
			break;
		}
	}

	public static void main(String[] args) {
		PortReadSerial prs = new PortReadSerial();
		prs.openPortAndListen();
	}
}
