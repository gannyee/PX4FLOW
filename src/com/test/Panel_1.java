package com.test;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Panel_1 extends JPanel {
	private JTextField textField_2;
	private static JPanel panel1;
	private final ProgressBar QualityProgressBar = new ProgressBar(180);
	public Panel_1() {
		/**
		 * panel1
		 * 
		 **/
		panel1 = new JPanel();
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
		GroudDistanceProgressBar.setBounds(32, 10, 98, 152);
		GroudDistanceProgressBar.setBackground(Color.BLUE);
		GroudDistanceProgressBar.setOrientation(1);

		// Create progress
		/*new Thread() {
			

			public void run() {
				
					try {
						// sleep process 0.1ms
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					// Set the process number
					GroudDistanceProgressBar.setValue(12);
				}
			
			// Start process
		}.start();*/
		panel1.add(GroudDistanceProgressBar);

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
		
		QualityProgressBar.setOrientation(SwingConstants.VERTICAL);
		QualityProgressBar.setBounds(193, 10, 98, 152);
		QualityProgressBar.setBackground(Color.YELLOW);
		QualityProgressBar.setOrientation(1);

		// Create Process
		new Thread() {
			Random a = new Random();

			public void run() {
				for (int i = 0;; i = a.nextInt(100)) {
					try {
						// Sleep process 0.1ms
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					QualityProgressBar.setValue(i); // Set process number
				}
			}
			// Start process
		}.start();
		panel1.add(QualityProgressBar);

		textField_2 = new JTextField();
		textField_2.setBounds(506, 10, 165, 152);
		panel1.add(textField_2);
		textField_2.setColumns(10);
		
		//Draw coordinate axis
		
	}

	public JPanel panel1() {
		return this.panel1;
	}

	public void setValue(int i){
		QualityProgressBar.setValue(i);
	}
}
