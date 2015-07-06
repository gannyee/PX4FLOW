package com.test;

import javax.swing.DefaultComboBoxModel;
/**
 * Amodel for combox choicess
 * @author Yi Gan
 *
 */
public class Amodel extends DefaultComboBoxModel{
	
	Amodel(String[] s) {
		// TODO Auto-generated constructor stub
		for(int i = 0;i < s.length;i ++)
		addElement(s[i]);
	}
}
