package com.test;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.mavlink.ByteArrayTransfer;
import com.mavlink.MavlinkParser;
import com.mavlink.PX4Flow;
import com.mavlink.Test;
import com.rxtx.PortReadSerial;
/**
 * Set main windows for application
 * 
 * @author Yi Gan
 * 
 */

import gnu.io.SerialPort;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import com.mavlink.Test;
import com.rxtx.PortReadSerial;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

/**
 * Set main windows for application
 * 
 * @author Yi Gan
 * 
 */
public class TestFrame {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private static JPanel contePanel;
	private PortReadSerial prs = new PortReadSerial();
	private ComboBoxModel model;
	private JTextField textField_2;
	//private PX4Flow px = new PX4Flow();
	// TestFrame Constructor
	public TestFrame() {

		JFrame jf = new JFrame();
		jf.setResizable(false);
		jf.setSize(804, 429);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		contePanel = new JPanel();
		contePanel.setLayout(null);
		jf.setContentPane(contePanel);

		
		
		/**
		 * panel1
		 * 
		 **/
		final JPanel panel1 = new JPanel();
		panel1.setLayout(null);

		JLabel GrnDisLabel1 = new JLabel("5.0");
		GrnDisLabel1.setBounds(10, 10, 18, 15);
		panel1.add(GrnDisLabel1);

		JLabel GrnDisLabel2 = new JLabel("0.3");
		GrnDisLabel2.setBounds(10, 147, 18, 15);
		panel1.add(GrnDisLabel2);

		JLabel GrndDistLabel = new JLabel("Grnd dist (m)");
		GrndDistLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		GrndDistLabel.setBounds(42, 180, 88, 15);
		panel1.add(GrndDistLabel);

		final JProgressBar GroudDistanceProgressBar = new ProgressBar(180);
		GroudDistanceProgressBar.setValue(10);
		GroudDistanceProgressBar.setMaximum(10);
		GroudDistanceProgressBar.setBounds(32, 10, 98, 152);
		GroudDistanceProgressBar.setBackground(Color.BLUE);
		GroudDistanceProgressBar.setOrientation(1);

		// Create progress
		/*new Thread() {
			Random a = new Random();

			public void run() {
				for (int i = 0;; i = a.nextInt(100)) {
					try {
						// Set the process number
						GroudDistanceProgressBar.setValue(i);
						// sleep process 0.1ms
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}

			}
			// Start process
		}.start();*/
		//panel1.add(GroudDistanceProgressBar);

		// Change process bar direction
		JLabel QualityLabel1 = new JLabel("255");
		QualityLabel1.setBounds(158, 10, 31, 15);
		panel1.add(QualityLabel1);

		JLabel QualityLabel2 = new JLabel("0");
		QualityLabel2.setBounds(171, 147, 18, 15);
		panel1.add(QualityLabel2);

		JLabel QualityLabel = new JLabel("Quality");
		QualityLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		QualityLabel.setBounds(222, 180, 52, 15);
		panel1.add(QualityLabel);
		final ProgressBar QualityProgressBar = new ProgressBar(180);
		QualityProgressBar.setValue(100);
		QualityProgressBar.setOrientation(SwingConstants.VERTICAL);
		QualityProgressBar.setBounds(193, 10, 98, 152);
		QualityProgressBar.setBackground(Color.YELLOW);
		QualityProgressBar.setOrientation(1);
		panel1.add(GroudDistanceProgressBar);
		panel1.add(QualityProgressBar);
		textField_2 = new JTextField();
		textField_2.setBounds(506, 10, 165, 152);
		panel1.add(textField_2);
		textField_2.setColumns(10);

		
		/**
		 * panel2 Set combox for port parameters
		 **/
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);

		// Open Port
		JButton openPortButton = new JButton("Open Port");
		openPortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.openPortAndListen();
				System.out.println("Port: " + prs.getBaudRate()
						+ "\nDaud Rate: " + prs.getDataBites()
						+ "\nStop Bites: " + prs.getStopBites()
						+ "\nParity Check: " + prs.getParityCheck());
				
				new Thread() {
					InputStream is = prs.getInputStream();
					byte[] buffer = new byte[2048];
					int numBytes;
					MavlinkParser mp = new MavlinkParser();
					byte[] c;
					ByteArrayTransfer bat = new ByteArrayTransfer();
					public void run() {
						//for (int i = 0,j = 0;; /*i = (int) px.getDistance(),j = px.getQuality()*/i ++) {
						while(true){
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
									GroudDistanceProgressBar.setValue(Integer.parseInt(bat.byteToBaseType("float",22, 26, c).toString().substring(0,1)));
									QualityProgressBar.setValue(Integer.valueOf(bat.byteToBaseType("int",21, 22, c).toString())); 
									panel1.add(QualityProgressBar);
									panel1.add(GroudDistanceProgressBar);
									Thread.sleep(15);
									QualityProgressBar.setValue(100); // Set process number
									GroudDistanceProgressBar.setValue(10);
									panel1.add(QualityProgressBar);
									panel1.add(GroudDistanceProgressBar);
									
									}
								}
								
								
							} catch (InterruptedException e) {
								// TODO: handle exception
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					// Start process
				}.start();
				
			}
		});
		openPortButton.setBounds(189, 130, 93, 23);
		panel2.add(openPortButton);
		// Close Port
		JButton closePortButton = new JButton("Close Port");
		closePortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						prs.closePort();
						QualityProgressBar.setValue(100); // Set process number
						GroudDistanceProgressBar.setValue(10);
						panel1.add(QualityProgressBar);
						panel1.add(GroudDistanceProgressBar);
					}

				}.start();
			}

		});
		closePortButton.setBounds(348, 130, 93, 23);
		panel2.add(closePortButton);

		// Port Name
		JLabel portNameLabel = new JLabel("Port Name: ");
		portNameLabel.setBounds(29, 25, 94, 25);
		panel2.add(portNameLabel);

		// To get all available ports array
		model = new Amodel(prs.getAllAvailablePorts());
		final JComboBox portNameComboBox = new JComboBox(model);
		portNameComboBox.setBounds(110, 29, 137, 21);
		panel2.add(portNameComboBox);
		// Add listener for choosing port number
		portNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.setCom((String) portNameComboBox.getSelectedItem());
				System.out.println("You chose port:" + prs.getCom());
			}
		});

		// Baud Rate
		JLabel baudRateLabel = new JLabel("Baud Rate: ");
		baudRateLabel.setBounds(29, 60, 94, 25);
		panel2.add(baudRateLabel);

		// To get all available Baud Rate choices
		model = new Amodel(prs.getAllBaudRateChoices());
		final JComboBox baudRateComboBox = new JComboBox(model);
		baudRateComboBox.setBounds(110, 64, 137, 21);
		panel2.add(baudRateComboBox);
		// Add listener for choosing baud rate
		baudRateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.setBaudRate(Integer.parseInt(((String) baudRateComboBox
						.getSelectedItem())));
				System.out.println("You chose baud rate:" + prs.getBaudRate());
			}
		});
		// Data Bites
		JLabel dataBitesLabel = new JLabel("Data Bites: ");
		dataBitesLabel.setBounds(29, 95, 94, 25);
		panel2.add(dataBitesLabel);

		// To get all available Data Bites choices
		model = new Amodel(prs.getAllDateBitesChoices());
		final JComboBox dataBitesComboBox = new JComboBox(model);
		dataBitesComboBox.setBounds(110, 99, 137, 21);
		panel2.add(dataBitesComboBox);
		// Add listener for choosing data bites
		dataBitesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String) dataBitesComboBox.getSelectedItem();
				if (key.equals("DATABITS_5")) {
					prs.setDataBites(SerialPort.DATABITS_5);
				} else if (key.equals("DATABITS_6")) {
					prs.setDataBites(SerialPort.DATABITS_6);
				} else if (key.equals("DATABITS_7")) {
					prs.setDataBites(SerialPort.DATABITS_7);
				} else if (key.equals("DATABITS_8")) {
					prs.setDataBites(SerialPort.DATABITS_8);
				}
				System.out
						.println("You chose data bites:" + prs.getDataBites());
			}
		});

		// Stop Bites
		JLabel stopBitesLabel = new JLabel("Stop Bites: ");
		stopBitesLabel.setBounds(349, 25, 94, 25);
		panel2.add(stopBitesLabel);

		// To get all available Stop bites choices
		model = new Amodel(prs.getAllStopBitesChoices());
		final JComboBox stopBitesComboBox = new JComboBox(model);
		stopBitesComboBox.setBounds(452, 27, 137, 21);
		panel2.add(stopBitesComboBox);
		// Add listener for choosing stop bites
		stopBitesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String) stopBitesComboBox.getSelectedItem();
				if (key.equals("STOPBITS_1")) {
					prs.setStopBites(SerialPort.STOPBITS_1);
				} else if (key.equals("STOPBITS_1_5")) {
					prs.setStopBites(SerialPort.STOPBITS_1_5);
				} else if (key.equals("STOPBITS_2")) {
					prs.setStopBites(SerialPort.STOPBITS_2);
				}
				System.out
						.println("You chose stop bites:" + prs.getStopBites());
			}
		});

		// Parity Check
		JLabel parityChecLabel = new JLabel("Parity Check: ");
		parityChecLabel.setBounds(349, 60, 93, 25);
		panel2.add(parityChecLabel);

		// To get all available Parity Check choices
		model = new Amodel(prs.getAllParityCheckChoices());
		final JComboBox parityCheckComboBox = new JComboBox(model);
		parityCheckComboBox.setBounds(452, 64, 137, 21);
		panel2.add(parityCheckComboBox);
		// Add listener for choosing parity check
		parityCheckComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String) parityCheckComboBox.getSelectedItem();
				if (key.equals("PARITY_EVEN")) {
					prs.setParityCheck(SerialPort.PARITY_EVEN);
				} else if (key.equals("PARITY_MARK")) {
					prs.setParityCheck(SerialPort.PARITY_MARK);
				} else if (key.equals("PARITY_NONE")) {
					prs.setParityCheck(SerialPort.PARITY_NONE);
				} else if (key.equals("PARITY_ODD")) {
					prs.setParityCheck(SerialPort.PARITY_ODD);
				} else if (key.equals("PARITY_SPACE")) {
					prs.setParityCheck(SerialPort.PARITY_SPACE);
				}
				System.out.println("You chose paraty check:"
						+ prs.getParityCheck());
			}
		});

		
		//panel3
		Panel_3 panel3 = new Panel_3();
		
		
		//Split panel panel1 and panel3
		JSplitPane splitPanel2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, panel1,panel3 );
		splitPanel2.setBounds(10, 10, 697, 381);
		// Set location and size of split line
		splitPanel2.setDividerLocation(0.43);
		splitPanel2.setDividerSize(1);
		
		// To Spite two panels: panel1 and panel2, by vertical method
		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				false, splitPanel2, panel2);
		splitPanel.setEnabled(false);
		splitPanel.setBounds(10, 10, 778, 381);
		// Set location and size of split line
		splitPanel.setDividerLocation(0.55);
		splitPanel.setDividerSize(10);
		contePanel.add(splitPanel);
		jf.show();

	}

	
	public static void main(String[] args) {
		new TestFrame();
	}
}
