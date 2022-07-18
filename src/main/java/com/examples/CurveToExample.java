package com.examples;

import com.sun.javafx.geom.RoundRectangle2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class CurveToExample extends JComponent {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw GeneralPath Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CurveToExample());
        frame.pack();
        frame.setSize(new Dimension(420, 400));
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;



        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawRoundRect(10, 50, 150, 150, 50, 50);

        g2.setStroke(new BasicStroke(1.0f));
        g2.setPaint(Color.black);

        GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        path.moveTo(50, 650);
        path.lineTo(50, 100);
        path.curveTo(50, 100, 50, 50, 100, 50);
//        path.curveTo(50, 100, 50, 50, 100, 50);
        path.lineTo(650, 50);
        g2.draw(path);



    }
}
