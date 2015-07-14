package com.test;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;

public class Panel_3 extends Canvas{
	private static int failcount = 0;
	private static final long serialVersionUID = 1L;
	// Distance
	private final int FRAME_X = 40;
	private final int FRAME_Y = 27;
	private final int FRAME_WIDTH = 150;
	private final int FRAME_HEIGHT = 150;
	private final int PARALLAR_MOVE = 250;
	private final int MOVE_INSTANT = 75;
	// X Axis Y Axis origin coordinate point
	private final int Origin_X = FRAME_X ;
	private final int Origin_Y = FRAME_Y + FRAME_HEIGHT - MOVE_INSTANT;

	// X Axis Y Axis final coordinate
	private final int XAxis_X = FRAME_X + FRAME_WIDTH;
	private final int XAxis_Y = Origin_Y;
	private final int YAxis_X = Origin_X;
	private final int YAxis_Y = FRAME_Y;
	
	private long originTime;
	private int v_x = 0;
	private int v_y = 0;
	private Image iBuffer;
	private int count = -1;
	private long timeSecPrev;
	private double elapsedSec;
	private int X_accum = 0;
	private int Y_accum = 0;
	private Vector<Point> points = new Vector<Point>();

	public Panel_3(){
		points.add(new Point((int) (XAxis_X -75 + PARALLAR_MOVE), Origin_Y + 5));
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		// Draw frame
		g.setColor(Color.BLACK);
		g.draw3DRect(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT, false);

		// Draw Axes
		g.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(Float.parseFloat("2.0f")));
		// X Axis and direction arrows 
		g.drawLine(Origin_X, Origin_Y, XAxis_X, XAxis_Y);
		g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y - 5);
		g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y + 5);
		// Y Axis and direction arrows
		g.drawLine(Origin_X + MOVE_INSTANT, Origin_Y + MOVE_INSTANT, YAxis_X + MOVE_INSTANT, YAxis_Y);
		g.drawLine(YAxis_X + MOVE_INSTANT, YAxis_Y , YAxis_X - 5 + MOVE_INSTANT , YAxis_Y + 5 );
		g.drawLine(YAxis_X + MOVE_INSTANT, YAxis_Y, YAxis_X + 5 + MOVE_INSTANT , YAxis_Y + 5);

		g.drawString("X m/sec", XAxis_X + 5, XAxis_Y + 5);
		g.drawString("Y m/sec", XAxis_X + 5 - 95, XAxis_Y + 5 - 85);
		
		g.setColor(Color.BLACK);
		g.draw3DRect(FRAME_X + PARALLAR_MOVE, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT, false);

		
		g.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(Float.parseFloat("2.0f")));
		
		g.drawLine(Origin_X + PARALLAR_MOVE , Origin_Y + MOVE_INSTANT, XAxis_X + PARALLAR_MOVE, XAxis_Y + MOVE_INSTANT);
		g.drawLine(XAxis_X + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + MOVE_INSTANT, XAxis_X + 5 + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + 5 + MOVE_INSTANT);
		g.drawLine(XAxis_X + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y + MOVE_INSTANT , XAxis_X + 5 + PARALLAR_MOVE - FRAME_WIDTH, XAxis_Y - 5 + MOVE_INSTANT);
		
		g.drawLine(Origin_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, Origin_Y + MOVE_INSTANT, YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y);
		g.drawLine(YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y , YAxis_X - 5 + MOVE_INSTANT * 2+ PARALLAR_MOVE , YAxis_Y + 5 );
		g.drawLine(YAxis_X + MOVE_INSTANT * 2 + PARALLAR_MOVE, YAxis_Y, YAxis_X + 5 + MOVE_INSTANT * 2 + PARALLAR_MOVE , YAxis_Y + 5);
		
		g.drawString("+10", XAxis_X + 5 + PARALLAR_MOVE, XAxis_Y + 5 - MOVE_INSTANT);
		g.drawString("-10", XAxis_X + MOVE_INSTANT, XAxis_Y + 5 + 85);
		g.drawString("Y(m)", XAxis_X + 5 + PARALLAR_MOVE, XAxis_Y + 5);
		g.drawString("X(m)", XAxis_X + 5 - 95 + PARALLAR_MOVE, XAxis_Y + 5 + 85);
		
		
		g.setColor(new Color(255, 0, 0));
		//g.drawOval(XAxis_X -80 + PARALLAR_MOVE, Origin_Y, 10, 10);
		
		//g.drawOval((int) (XAxis_X -80 + (this.X_accum / 10) * 100+ PARALLAR_MOVE), Origin_Y, 10, 10);
		Point p1, p2;
		for (int i = 1; i < points.size(); i++) {
			p1 = points.get(i - 1);
			p2 = points.get(i);
			//g.drawOval(p2.x, p2.y, 10, 10);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		
		
		if(this.v_x < 0)
			g.setColor(new Color(255, 0, 0));
		else 
			g.setColor(new Color(0, 255, 0));
		g.drawLine(Origin_X + 75, Origin_Y,Origin_X + 75 + this.v_x, Origin_Y);
		if(this.v_y < 0)
			g.setColor(new Color(255, 0, 0));
		else 
			g.setColor(new Color(0, 255, 0));
		g.drawLine(Origin_X + 75, Origin_Y,Origin_X + 75,Origin_Y + this.v_y );
		/*Point p1, p2;
		for (int i = 1; i < points.size(); i++) {
			p1 = points.get(i - 1);
			p2 = points.get(i);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}*/
		
		count ++;
		originTime = System.currentTimeMillis();
		
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(count);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public void setX(Object x, Object distance,Object time) {
		// Display params (Width, Height, X, Y, Radius, Distance)
		
		//time = ((Long.valueOf(time.toString()) / 1000000) - originTime) ;
		if(Float.valueOf(distance.toString()) > 0){
			
			this.v_x = (int) (Integer.valueOf(x.toString().substring(0,1)) / 5.0 * 100);
			System.out.println("---------------------------------");
			System.out.println("X: " + this.v_x);
			System.out.println("---------------------------------");
		}else{
            this.failcount += 1;
            System.out.println("Fail: " + this.failcount);
		}
		repaint();
	}
		
	public void setY(Object y, Object distance,Object time) {
		// Display params (Width, Height, X, Y, Radius, Distance)
		//time = Long.valueOf(time.toString()) / 1000000 - originTime;
		if(Float.valueOf(distance.toString()) > 0){
			this.v_y = (int) (Integer.valueOf(y.toString().substring(0,1)) / 5.0 * 100);
			System.out.println("---------------------------------");
			System.out.println("Y: " + this.v_y);
			System.out.println("---------------------------------");
		}else{
            this.failcount += 1;
            System.out.println("Fail: " + this.failcount);
		}
		repaint();
	}
	
	 // Double Buffer deal with figure shown  
    public void update(Graphics g) {  
        if (iBuffer == null) {  
            iBuffer = createImage(this.getSize().width,  
                    this.getSize().height);  

        }  
        Graphics gBuffer = iBuffer.getGraphics();  
        gBuffer.setColor(getBackground());  
        gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);  
        paint(gBuffer);  
        gBuffer.dispose();  
        g.drawImage(iBuffer, 0, 0, this);  
    }
    
    public void setXY(Object time,Object compX,Object compY){
    	
    	long timeSec = (long) (Long.valueOf(time.toString()) / 1e6);
    	
    	if(this.count > 0){
    		if(this.timeSecPrev > 0){
    			this.elapsedSec = (timeSec - this.timeSecPrev) / 1000000000000.0;
    			
    			if(this.elapsedSec < 0.1){
    				this.X_accum += (Float.valueOf(compX.toString()) * this.elapsedSec) / 100000000;
    				this.Y_accum += (Float.valueOf(compY.toString()) * this.elapsedSec) / 100000000;
    				this.timeSecPrev = timeSec;
    				
    				System.out.println("******************************");
    				System.out.println("X_accum: " + this.X_accum);
    				System.out.println("Y_accum: " + this.Y_accum);
    				System.out.println("timeSecPrev: " + this.timeSecPrev);
    				System.out.println("******************************");
    			}
    		}
    		this.timeSecPrev = timeSec;
    	}
    	Point prev = points.get(points.size() - 1);
		Point point = new Point(new Point((int) (XAxis_X -75 + PARALLAR_MOVE) + this.X_accum, Origin_Y + 5 + + this.X_accum));
		points.add(point);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println("X_accum: " + this.X_accum);
		System.out.println("Y_accum: " + this.Y_accum);
		System.out.println("compX: " + Float.valueOf(compX.toString()));
		System.out.println("compY: " + Float.valueOf(compY.toString()));
		System.out.println( "elapsedSec: " +  this.elapsedSec);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    	repaint();
    }
}
