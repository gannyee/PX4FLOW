package com.test;

import gnu.io.SerialPort;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import com.rxtx.PortReadSerial;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;

/**
 * Set main windows for application
 * @author Yi Gan
 *
 */
public class TestFrame {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 200;
	private static JPanel contePanel;
	private PortReadSerial prs = new PortReadSerial();
	private ComboBoxModel model;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	//TestFrame Constructor
	public TestFrame(){
		
		JFrame jf = new JFrame();
		jf.setResizable(false);
		jf.setSize(722, 429);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		contePanel = new JPanel();
		contePanel.setLayout(null);
		jf.setContentPane(contePanel);
		
		/**
		 * panel1 
		 * 
		**/
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		
		
		/**
		 * panel2 
		 * Set combox for port parameters
		**/
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		
		//Open Port
		JButton openPortButton = new JButton("Open Port");
		openPortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.openPortAndListen();
				System.out.println("Port: " + prs.getBaudRate() +
						"\nDaud Rate: " +  prs.getDataBites() +
						"\nStop Bites: " +  prs.getStopBites() + 
						"\nParity Check: " +  prs.getParityCheck());
			}
		});
		openPortButton.setBounds(189, 149, 93, 23);
		panel2.add(openPortButton);
		
		//Close Port
		JButton closePortButton = new JButton("Close Port");
		closePortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.closePort();
			}
		});
		closePortButton.setBounds(348, 149, 93, 23);
		panel2.add(closePortButton);
		
		//Port Name
		JLabel portNameLabel = new JLabel("Port Name: ");
		portNameLabel.setBounds(28, 18, 94, 25);
		panel2.add(portNameLabel);
		
		//To get all available ports array
		model = new Amodel(prs.getAllAvailablePorts());
		final JComboBox portNameComboBox = new JComboBox(model);
		portNameComboBox.setBounds(109, 22, 137, 21);
		panel2.add(portNameComboBox);
		//Add listener for choosing port number
		portNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.setCom((String)portNameComboBox.getSelectedItem());
				System.out.println("You chose port:" + prs.getCom());
			}
		});
		
		//Baud Rate
		JLabel baudRateLabel = new JLabel("Baud Rate: ");
		baudRateLabel.setBounds(28, 53, 94, 25);
		panel2.add(baudRateLabel);
		
		//To get all available Baud Rate choices
		model = new Amodel(prs.getAllBaudRateChoices());
		final JComboBox baudRateComboBox = new JComboBox(model);
		baudRateComboBox.setBounds(109, 57, 137, 21);
		panel2.add(baudRateComboBox);
		//Add listener for choosing baud rate
		baudRateComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prs.setBaudRate(Integer.parseInt(((String)baudRateComboBox.getSelectedItem())));
				System.out.println("You chose baud rate:" + prs.getBaudRate());
			}
		});
		//Data Bites
		JLabel dataBitesLabel = new JLabel("Data Bites: ");
		dataBitesLabel.setBounds(28, 88, 94, 25);
		panel2.add(dataBitesLabel);
		
		//To get all available Data Bites choices
		model = new Amodel(prs.getAllDateBitesChoices());
		final JComboBox dataBitesComboBox = new JComboBox(model);
		dataBitesComboBox.setBounds(109, 92, 137, 21);
		panel2.add(dataBitesComboBox);
		//Add listener for choosing data bites
		dataBitesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String)dataBitesComboBox.getSelectedItem();
				if (key.equals("DATABITS_5")) {
					prs.setDataBites(SerialPort.DATABITS_5);
				}
				else if (key.equals("DATABITS_6")) {
					prs.setDataBites(SerialPort.DATABITS_6);
				}else if (key.equals("DATABITS_7")) {
					prs.setDataBites(SerialPort.DATABITS_7);
				}else if (key.equals("DATABITS_8")) {
					prs.setDataBites(SerialPort.DATABITS_8);
				}
				System.out.println("You chose data bites:" + prs.getDataBites());
			}
		});
		
		//Stop Bites
		JLabel stopBitesLabel = new JLabel("Stop Bites: ");
		stopBitesLabel.setBounds(348, 18, 94, 25);
		panel2.add(stopBitesLabel);
		
		//To get all available Stop bites choices
		model = new Amodel(prs.getAllStopBitesChoices());
		final JComboBox stopBitesComboBox = new JComboBox(model);
		stopBitesComboBox.setBounds(451, 20, 137, 21);
		panel2.add(stopBitesComboBox);
		//Add listener for choosing stop bites
		stopBitesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String)stopBitesComboBox.getSelectedItem();
				if (key.equals("STOPBITS_1")) {
					prs.setStopBites(SerialPort.STOPBITS_1);
				}
				else if (key.equals("STOPBITS_1_5")) {
					prs.setStopBites(SerialPort.STOPBITS_1_5);
				}else if (key.equals("STOPBITS_2")) {
					prs.setStopBites(SerialPort.STOPBITS_2);
				}
				System.out.println("You chose stop bites:" + prs.getStopBites());
			}
		});
		
		//Parity Check
		JLabel parityChecLabel = new JLabel("Parity Check: ");
		parityChecLabel.setBounds(348, 53, 93, 25);
		panel2.add(parityChecLabel);

		//To get all available Parity Check choices
		model = new Amodel(prs.getAllParityCheckChoices());
		final JComboBox parityCheckComboBox = new JComboBox(model);
		parityCheckComboBox.setBounds(451, 57, 137, 21);
		panel2.add(parityCheckComboBox);
		//Add listener for choosing parity check
		parityCheckComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String)parityCheckComboBox.getSelectedItem();
				if (key.equals("PARITY_EVEN")) {
					prs.setParityCheck(SerialPort.PARITY_EVEN);
				}
				else if (key.equals("PARITY_MARK")) {
					prs.setParityCheck(SerialPort.PARITY_MARK);
				}else if (key.equals("PARITY_NONE")) {
					prs.setParityCheck(SerialPort.PARITY_NONE);
				}else if (key.equals("PARITY_ODD")) {
					prs.setParityCheck(SerialPort.PARITY_ODD);
				}else if (key.equals("PARITY_SPACE")) {
					prs.setParityCheck(SerialPort.PARITY_SPACE);
				}
				System.out.println("You chose paraty check:" + prs.getParityCheck());
			}
		});
		/*JLabel label = new JLabel("Flow Control: ");
		label.setBounds(348, 90, 93, 25);
		panel2.add(label);
		
		JComboBox comboBox = new JComboBox((ComboBoxModel) null);
		comboBox.setBounds(451, 94, 137, 21);
		panel2.add(comboBox);*/
		
		//To Spite two panels: panel1 and panel2, by vertical method
		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, panel1, panel2);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 107, 152);
		panel1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(186, 10, 107, 152);
		panel1.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(506, 10, 165, 152);
		panel1.add(textField_2);
		textField_2.setColumns(10);
		splitPanel.setBounds(10, 10, 697, 381);
		//Set location and size of split line
		splitPanel.setDividerLocation(0.5);
		splitPanel.setDividerSize(10);
		//Disable to move split line
		splitPanel.setEnabled(false);
		contePanel.add(splitPanel);
		jf.show();
		
	}
	
	public static void main(String[] args) {
		new TestFrame();
	}

}
