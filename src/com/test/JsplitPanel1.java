package com.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JsplitPanel1 {

	public static void main(String[] args) {
		JFrame f = new JFrame("JSplitPaneDemo");
		Container contentPane = f.getContentPane();
		JLabel label1 = new JLabel("Label 1", JLabel.CENTER);
		label1.setBackground(Color.green);
		label1.setOpaque(true);// setOpaqueIture)方法的目的是让组件变成不透明，这样我们在JLabel上所设置的颜色
		// 才能显示出来。
		JLabel label2 = new JLabel("Label 2", JLabel.CENTER);
		label2.setBackground(Color.pink);
		label2.setOpaque(true);
		JLabel label3 = new JLabel("Label 3", JLabel.CENTER);
		label3.setBackground(Color.yellow);
		label3.setOpaque(true);
		/*
		 * 加入label1,label2到splitPane1中，并设置此splitPane1为水平分割且具有Continuous Layout的
		 * 功能。
		 */
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				false, label1, label2);
		/*
		 * 设置splitPane1的分隔线位置，0.3是相对于splitPane1的大小而定，因此这个值的范围在0.0～1.0
		 * 中。若你使用整数值来设置splitPane的分隔线位置，如第34行所示，则所定义的值以pixel为计算单位
		 */
		splitPane1.setDividerLocation(0.3);
		/*
		 * 设置JSplitPane是否可以展开或收起(如同文件总管一般)，设为true表示打开此功能。
		 */
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setDividerSize(10);// 设置分隔线宽度的大小，以pixel为计算单位。

		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				false, splitPane1, label3);
		splitPane2.setDividerLocation(35);
		// 设置JSplitPane是否可以展开或收起(如同文件总管一般),设为true表示打开此功能.
		splitPane2.setOneTouchExpandable(true);
		splitPane2.setDividerSize(5);

		contentPane.add(splitPane2);

		f.setSize(250, 200);
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
