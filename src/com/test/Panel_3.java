package com.test;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Panel_3 extends JPanel{

	private static final long serialVersionUID = 1L;
	// 框架起点坐标、宽高
	private final int FRAME_X = 10;
	private final int FRAME_Y = 27;
	private final int FRAME_WIDTH = 150;
	private final int FRAME_HEIGHT = 150;
	private final int PARALLAR_MOVE = 250;
	private final int MOVE_INSTANT = 75;
	// 原点坐标
	private final int Origin_X = FRAME_X ;
	private final int Origin_Y = FRAME_Y + FRAME_HEIGHT - MOVE_INSTANT;

	// X轴、Y轴终点坐标
	private final int XAxis_X = FRAME_X + FRAME_WIDTH;
	private final int XAxis_Y = Origin_Y;
	private final int YAxis_X = Origin_X;
	private final int YAxis_Y = FRAME_Y;
	

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		// 画边框
		g.setColor(Color.BLACK);
		g.draw3DRect(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT, false);

		// 画坐标轴
		g.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(Float.parseFloat("2.0f")));
		// X轴及方向箭头
		g.drawLine(Origin_X, Origin_Y, XAxis_X, XAxis_Y);
		g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y - 5);
		g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y + 5);
		// Y轴及方向箭头
		g.drawLine(Origin_X + MOVE_INSTANT, Origin_Y + MOVE_INSTANT, YAxis_X + MOVE_INSTANT, YAxis_Y);
		g.drawLine(YAxis_X + MOVE_INSTANT, YAxis_Y , YAxis_X - 5 + MOVE_INSTANT , YAxis_Y + 5 );
		g.drawLine(YAxis_X + MOVE_INSTANT, YAxis_Y, YAxis_X + 5 + MOVE_INSTANT , YAxis_Y + 5);

		g.drawString("X m/sec", XAxis_X + 5, XAxis_Y + 5);
		g.drawString("Y m/sec", XAxis_X + 5 - 95, XAxis_Y + 5 - 85);
		
		g.setColor(Color.BLACK);
		g.draw3DRect(FRAME_X + PARALLAR_MOVE, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT, false);

		// 画坐标轴
		g.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(Float.parseFloat("2.0f")));
		// X轴及方向箭头
		g.drawLine(Origin_X + PARALLAR_MOVE , Origin_Y + MOVE_INSTANT, XAxis_X + PARALLAR_MOVE, XAxis_Y + MOVE_INSTANT);
		g.drawLine(XAxis_X + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + MOVE_INSTANT, XAxis_X + 5 + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + 5 + MOVE_INSTANT);
		g.drawLine(XAxis_X + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + MOVE_INSTANT , XAxis_X + 5 + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y - 5 + MOVE_INSTANT);
		// Y轴及方向箭头
		g.drawLine(Origin_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, Origin_Y + MOVE_INSTANT, YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y);
		g.drawLine(YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y , YAxis_X - 5 + MOVE_INSTANT * 2+ PARALLAR_MOVE , YAxis_Y + 5 );
		g.drawLine(YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y, YAxis_X + 5 + MOVE_INSTANT * 2 + PARALLAR_MOVE , YAxis_Y + 5);
		
		g.drawString("+10", XAxis_X + 5 + PARALLAR_MOVE, XAxis_Y + 5 - MOVE_INSTANT);
		g.drawString("-10", XAxis_X + MOVE_INSTANT, XAxis_Y + 5 + 85);
		g.drawString("Y(m)", XAxis_X + 5 + PARALLAR_MOVE, XAxis_Y + 5);
		g.drawString("X(m)", XAxis_X + 5 - 95 + PARALLAR_MOVE, XAxis_Y + 5 + 85);
		
	}
	
}
