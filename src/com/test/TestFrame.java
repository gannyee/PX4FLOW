package com.test;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import com.mavlink.ByteArrayTransfer;
import com.mavlink.MavlinkParser;
import com.mavlink.PX4Flow;
import com.rxtx.PortReadSerial;
import gnu.io.SerialPort;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
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
public class TestFrame extends PX4Flow{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private static JPanel contePanel;
	private PortReadSerial prs = new PortReadSerial();
	private ComboBoxModel model;
	private JTextField textField_2;
	public TestFrame() {

		JFrame jf = new JFrame();
		jf.setResizable(false);
		jf.setSize(804, 429);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		contePanel = new JPanel();
		contePanel.setLayout(null);
		jf.setContentPane(contePanel);

		//panel3
		final Panel_3 panel3 = new Panel_3();
		
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
		GroudDistanceProgressBar.setValue(100);
		GroudDistanceProgressBar.setBounds(32, 10, 98, 152);
		GroudDistanceProgressBar.setBackground(Color.BLUE);
		GroudDistanceProgressBar.setOrientation(1);

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
				
				new Thread() {
					InputStream is;
					byte[] buffer = new byte[2048];
					int numBytes;
					MavlinkParser mp = new MavlinkParser();
					ByteArrayTransfer bat = new ByteArrayTransfer();
					public void run() {
						System.out.println("Port: " + prs.getBaudRate()
								+ "\nDaud Rate: " + prs.getDataBites()
								+ "\nStop Bites: " + prs.getStopBites()
								+ "\nParity Check: " + prs.getParityCheck());
						openPortAndListen();
						while(true){
						try {
							while(getInputStream().read(buffer) > 0){
								process(buffer);
							//	System.out.println("Messager: " + getTargetMassager());
							for(int i = 0;i < getTargetMassager().size();i ++){
//							System.out.println("Messager: " + getTargetMassager().get(i));
//							System.out.println("1: " + byteToBaseType("long",0,8, getTargetMassager().get(i)));
//							System.out.println("2: " + byteToBaseType("int",24,24, getTargetMassager().get(i)));
//							System.out.println("3: " + byteToBaseType("int",20,22,getTargetMassager().get(i)));
//							System.out.println("4: " + byteToBaseType("int",22,24,getTargetMassager().get(i)));
//							System.out.println("5: " + byteToBaseType("float",8,12,getTargetMassager().get(i)));
//							System.out.println("6: " + byteToBaseType("float",12,16,getTargetMassager().get(i)));
//							System.out.println("7: " + byteToBaseType("int",25,25,getTargetMassager().get(i)));
//							System.out.println("8: " + byteToBaseType("float",16,20,getTargetMassager().get(i)));
							GroudDistanceProgressBar.setValue(getDistance(byteToBaseType("float",16,20,getTargetMassager().get(i))));
							QualityProgressBar.setValue(getQuality(byteToBaseType("int",25,25,getTargetMassager().get(i)))); 
							panel1.add(QualityProgressBar);
							panel1.add(GroudDistanceProgressBar);
							panel1.repaint();
							panel3.setXY(byteToBaseType("long",0, 8, getTargetMassager().get(i)), byteToBaseType("float",8, 12, getTargetMassager().get(i)),byteToBaseType("float",12, 16, getTargetMassager().get(i)));
							panel3.setX_Y(byteToBaseType("float", 8, 12, getTargetMassager().get(i)), byteToBaseType("float",12, 16, getTargetMassager().get(i)),  byteToBaseType("float",16,20,getTargetMassager().get(i)));
							Thread.sleep(10);
							}
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (InterruptedException e) {
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
						closePort();
						QualityProgressBar.setValue(100); // Set process number
						GroudDistanceProgressBar.setValue(100);
						panel1.add(QualityProgressBar);
						panel1.add(GroudDistanceProgressBar);
						panel1.repaint();
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
