package com.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {
    private double theta = 0;
 
    public ProgressBar( int theta) {
        this.theta = theta;
    }

	protected void paintComponent(Graphics g) {
        int w = getWidth(), h = getHeight();
        double theta = Math.toRadians(this.theta);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHints(renderingHints);
        g2d.rotate(theta, w / 2, h / 2);
        super.paintComponent(g);
    }
}
