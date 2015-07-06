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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Observable;
import java.util.TooManyListenersException;
import java.util.Vector;

/**
 * Find usable port Open serial port Read data via opened port
 * 
 * @author Yi Gan
 * 
 */
public class PortReadSerial extends Observable implements Runnable,
		SerialPortEventListener {
	// All available ports of computer
	private Enumeration<?> allPorts = null;
	// Port ID identifier
	private static CommPortIdentifier portId = null;
	// Port ID
	private static String com;
	// Serial port
	private static SerialPort serialPort = null;
	// Baud rate
	private int baudRate;
	// Data bites
	private int dataBites;
	// Stop bites
	private int stopBites;
	// Parity check
	private int parityCheck;

	// Input stream
	private static InputStream inputStream;
	// Output stream
	private static OutputStream outputStream;
	// Data buffer
	private static byte[] readBuffer = new byte[1024];
	//Actual bytes of readBuffer
	int numBytes;
	// Whether port is opened
	private static boolean isOpen = false;

	// Override constructor for initialing
	// baudRate,dataBites,stopBites,parityChck
	public PortReadSerial() {
		this.com = "COM9";
		this.baudRate = 9600;
		this.dataBites = SerialPort.DATABITS_5;
		this.stopBites = SerialPort.STOPBITS_1;
		this.parityCheck = SerialPort.PARITY_EVEN;
	}

	
	// Get all available ports
	public String[] getAllAvailablePorts() {
		Enumeration<?> allPorts = CommPortIdentifier.getPortIdentifiers();
		Vector<String> a = new Vector<String>();
		int i = 0;
		while (allPorts.hasMoreElements()) {
			a.add(((CommPortIdentifier) allPorts.nextElement()).getName()
					.toString());
		}
		return (Arrays.toString(a.toArray()).substring(1,
				Arrays.toString(a.toArray()).length() - 1).split(", "));
	}

	//Get port number
	public static String getCom() {
		return com;
	}

	//Set port number
	public static void setCom(String com) {
		PortReadSerial.com = com;
	}

	// Get baud rate
	public int getBaudRate() {
		return baudRate;
	}

	// Set baud rate
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	// Get all indicates parameters for baud rate
	public String[] getAllBaudRateChoices() {
		String[] allBaudRatechoices = { "9600", "4800", "12500" };
		return allBaudRatechoices;
	}

	// Get data bits
	public int getDataBites() {
		return dataBites;
	}

	// Set data bits
	public void setDataBites(int dataBites) {
		this.dataBites = dataBites;
	}

	// Get all indicates parameters for data bites
	public String[] getAllDateBitesChoices() {
		String[] allDateBiteschoices = { "DATABITS_5",
				"DATABITS_6", "DATABITS_7",
				"DATABITS_8" };
		return allDateBiteschoices;
	}

	// Get stop bites
	public int getStopBites() {
		return stopBites;
	}

	// Set stop bites
	public void setStopBites(int stopBites) {
		this.stopBites = stopBites;
	}

	// Get all indicates parameters for stop bites
	public String[] getAllStopBitesChoices() {
		String[] allStopBiteschoices = {"STOPBITS_1","STOPBITS_1_5","STOPBITS_2"};
		return allStopBiteschoices;
	}
	// Get parity check
	public int getParityCheck() {
		return parityCheck;
	}

	public void setParityCheck(int parityCheck) {
		this.parityCheck = parityCheck;
	}

	// Get all indicates parameters for parity check
	public String[] getAllParityCheckChoices() {
		String[] allParityCheckchoices = {"PARITY_EVEN","PARITY_MARK","PARITY_NONE",
				"PARITY_ODD","PARITY_SPACE"};
		return allParityCheckchoices;
	}
	
	/*// Get all indicates parameters for flow control
	public String[] getAllFlowControlChoices() {
		String[] allFlowControlchoices = {"FLOWCONTROL_NONE","FLOWCONTROL_RTSCTS_IN","FLOWCONTROL_RTSCTS_OUT","FLOWCONTROL_XONXOFF_IN","FLOWCONTROL_XONXOFF_OUT"};
		return allFlowControlchoices;
	}*/
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
			//outputStream = serialPort.getOutputStream();
			// Add listener
			serialPort.addEventListener(this);
			// Listeners for available even
			serialPort.notifyOnDataAvailable(true);
			// Configure parameters
			serialPort.setSerialPortParams(getBaudRate(), getDataBites(),
					getStopBites(), getParityCheck());
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
			System.out.println("Open port: " + portId.getName() + "failly!");
		}
		Thread readThread = new Thread(this);
		readThread.start();
		// closePort();
	}

	public static void closePort() {
		if (isOpen) {
			try {
				serialPort.notifyOnDataAvailable(false);
				serialPort.removeEventListener();
				inputStream.close();
				serialPort.close();
				isOpen = false;
				System.out.println("Port: " + portId.getName()
						+ ": clossed successfully!");
			} catch (IOException ex) {
				System.out.println("Port: " + portId.getName()
						+ ": clossed fiallly!");
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
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:// Output buffer is empty
			break;
		case SerialPortEvent.DATA_AVAILABLE:// Data available at the serial port

			/*byte[] readBuffer = new byte[1024];

			try {
				while (inputStream.available() > 0) {
					int numBytes = inputStream.read(readBuffer);
				}
				System.out.print(new String(readBuffer));
			} catch (IOException e) {
			}*/
			
			try {
				// Read data
				while (inputStream.available() > 0) {
					numBytes = inputStream.read(readBuffer);
				}

				// print the information of got data
				for (int i = 0; i < numBytes; i++) {
					System.out.println("msg[" + numBytes + "]: ["
							+ readBuffer[i] + "]:" + (char) readBuffer[i]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	/*
	 * public static void main(String[] args) { PortReadSerial prs = new
	 * PortReadSerial(); prs.openPortAndListen();
	 * System.out.println(prs.getAllAvailablePorts()); String[] strings =
	 * prs.getAllAvailablePorts(); for(int i = 0;i < strings.length;i ++) {
	 * System.out.println(strings[i]); } }
	 */
}
