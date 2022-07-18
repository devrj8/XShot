package com.examples;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomeJComponent extends JFrame {

    public static void main(String[] args) {

        CustomeJComponent frame = new CustomeJComponent();
        frame.setBackground(Color.BLUE);
        frame.setLayout(null);
//        JLabel textLabel = new JLabel("Yo man it's just a text label");
//        textLabel.setForeground(Color.BLUE);
//        textLabel.setBounds(100,100, 200, 100);
//        frame.add(textLabel);
//        frame.add(new Screenshot());
//        frame.add(textLabel);

        JLayeredPane layeredPan = new JDesktopPane();
        //layeredPan.setOpaque(false);
        layeredPan.add(new Highlighter(), JLayeredPane.POPUP_LAYER);
        layeredPan.add(new ImagePanel(), JLayeredPane.DEFAULT_LAYER);

        frame.getContentPane().add(layeredPan, BorderLayout.CENTER);

//        frame.setLayout(null);
//        frame.getContentPane().add(new ImagePanel());
//        frame.getContentPane().add(new Highlighter());
//        frame.getContentPane().validate();
//        frame.getContentPane().repaint();

//        JPanel panel = new JPanel();
//        panel.setBackground(Color.white);
//        panel.setBounds(0,0, 70,70);
//        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}



class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
        try {
            setBounds(0,0, 200, 200);
            image = ImageIO.read(new File("C:\\Users\\light\\Pictures\\lines.png"));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }

}

class Highlighter extends  JPanel {

    public Highlighter() {
        setOpaque(false);
        setBounds(0,0, 500, 500);
        setBorder(BorderFactory.createLineBorder(Color.white, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString("Yo man Transparent panel and draw", 100, 100);
        g.setColor(new Color(75, 248, 7, 200));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(20));
        g.drawLine(100, 150, 300, 150);
        g2.dispose();
    }
}