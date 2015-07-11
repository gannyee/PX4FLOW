package com.test;

import gnu.io.SerialPort;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mavlink.PX4Flow;
import com.rxtx.PortReadSerial;

public class Panel_2 extends JPanel {
	private PortReadSerial prs = new PortReadSerial();
	private ComboBoxModel model;
	private JPanel panel2;
	private PX4Flow px = new PX4Flow();
	
	public Panel_2() {
		/**
		 * panel2 Set combox for port parameters
		 **/
		panel2 = new JPanel();
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
				//px.refresh();
				// Create Process
				new Thread() {

					public void run() {
						
							px.refresh();
							
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
				prs.closePort();
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
	}

	public JPanel panel2() {
		return this.panel2;
	}
}
