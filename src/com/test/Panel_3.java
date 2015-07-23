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
	private double timeSecPrev = 0;
	private double elapsedSec;
	private double X_accum = 0.0;
	private double Y_accum = 0.0;
	private Vector<Point> points = new Vector<Point>();
	private int updates = 0;
	private long  elapsed_sec;
	private float lineWidth = 0.2f;
	private float lineWidth1 = 2.2f;
	public Panel_3(){
		points.add(new Point((int) (XAxis_X -75 + PARALLAR_MOVE), Origin_Y + 5));
		originTime = System.currentTimeMillis() / 100;
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
		
	    ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
		g.setColor(new Color(255, 0, 0));
		Point p1, p2;
		for (int i = 1; i < points.size(); i++) {
			p1 = points.get(i - 1);
			p2 = points.get(i);
			//g.drawOval(p2.x, p2.y, 10, 10);
			g.drawLine(p1.x , p1.y, p2.x, p2.y);
		}
		
		((Graphics2D)g).setStroke(new BasicStroke(lineWidth1));
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
	}
	
	//Set X Y direction flow velocity
	public void setX_Y(Object x,Object y, Object distance) {
		float comp_x = (float) 0.0;
		float comp_y = (float) 0.0;
		if(Float.valueOf(distance.toString()) > 0){
			comp_x =  (Float.valueOf(x.toString()) * 30 ) ;  //'35' mean the length of axis
			comp_y =  (Float.valueOf(y.toString()) * 30) ;
			this.v_x = (int) comp_x;
			this.v_y = (int) comp_y;
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
    
    //Set device movements situation
    public void setXY(Object time,Object compX,Object compY){
    	
    	double timeSec =  (Long.valueOf(time.toString()) / 1e6);
    	if(this.count != 0){
    		if(this.timeSecPrev != 0){
    			this.elapsedSec = (timeSec - this.timeSecPrev);
    			
    			if(this.elapsedSec < 0.1){
    				this.X_accum += (Float.valueOf(compX.toString()) * this.elapsedSec) ;
    				this.Y_accum += (Float.valueOf(compY.toString()) * this.elapsedSec) ;
    			}
    			this.timeSecPrev = timeSec;
    		}
    	}
    	
    	this.count += 1;
		this.timeSecPrev = timeSec;
		Point pre = new Point();
		Point point = new Point();
		if(pre.x != (int) (XAxis_X -75 + PARALLAR_MOVE + (this.X_accum / 10  * 100)) && pre.y != (int) (Origin_Y + 5 +  (this.Y_accum / 10  * 100)))
			point = new Point((int) (XAxis_X -75 + PARALLAR_MOVE + (this.X_accum / 30  * 100)), (int) (Origin_Y + 5 +  (this.Y_accum / 30  * 100)));
		points.add(point);
    	repaint();
    }
}
