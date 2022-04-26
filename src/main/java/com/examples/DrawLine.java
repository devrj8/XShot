package com.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DrawLine {

    public static void main(String args[]) throws Exception {
        JFrame f = new JFrame("Draw a Red Line");
        f.setSize(300, 300);
        f.setLocation(300, 300);
        f.setResizable(false);
        JPanel p = new JPanel() {
            Point pointStart = null;
            Point pointEnd   = null;
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        pointStart = e.getPoint();
                    }

                    public void mouseReleased(MouseEvent e) {
                        pointStart = null;
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseMoved(MouseEvent e) {
                        pointEnd = e.getPoint();
                    }

                    public void mouseDragged(MouseEvent e) {
                        pointEnd = e.getPoint();
                        repaint();
                    }
                });
            }

            public void paint(Graphics g) {
                super.paint(g);
                if (pointStart != null) {

                    Graphics2D g2d = (Graphics2D) g;
                    g.setColor(Color.black);
                    Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
                    g2d.setStroke(dashed);

                    g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointStart.y);
                    g.drawLine(pointEnd.x, pointStart.y, pointEnd.x, pointEnd.y);
                    g2d.dispose();
                }
            }
        };
        f.add(p);
        f.setVisible(true);
    }
}
