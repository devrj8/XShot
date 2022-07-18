package com.examples;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.Action;
import javax.swing.Icon;

public class RoundButtonTest {

    public RoundButtonTest() {
        createAndShowGui();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoundButtonTest::new);
    }

    private void createAndShowGui() {
        JFrame frame = new JFrame("TestApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);


        RoundButton button2 = new RoundButton("Okay");
        button2.setFocusPainted(false);
        button2.setSize(100, 40);
        button2.setForeground(Color.white);
        button2.setBackground(Color.decode("#4240f5"));
        button2.setColorWhenPressed(Color.decode("#1614f5"));
        panel.add(button2);

        RoundButton button3 = new RoundButton("Cancel");
        button3.setFocusPainted(false);
        button3.setSize(100, 40);
        button3.setForeground(Color.white);
        button3.setBackground(Color.decode("#4240f5"));
        button3.setColorWhenPressed(Color.decode("#1614f5"));
        panel.add(button3);


        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }




    public class RoundButton extends JButton {

        private Shape shape;
        private Color colorWhenPressed;

        public RoundButton(String label) {
            super(label);
            setContentAreaFilled(false);
        }

        public RoundButton(String label, Icon icon) {
            super(label, icon);
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setContentAreaFilled(false);
        }

        public RoundButton(Icon icon) {
            super(icon);
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setContentAreaFilled(false);
        }

        public RoundButton(Action action) {
            super(action);
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setContentAreaFilled(false);
        }

        @Override
        public void setSize(int width, int height) {
            super.setSize(width, height);
            setPreferredSize(new Dimension(width, height));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isArmed()) {
                g2.setColor(getColorWhenPressed());
            } else {
                g2.setColor(getBackground());
            }
            g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
            super.paintComponent(g2);
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            }
            return shape.contains(x, y);
        }

        public Color getColorWhenPressed() {
            return colorWhenPressed;
        }

        public void setColorWhenPressed(Color colorWhenPressed) {
            this.colorWhenPressed = colorWhenPressed;
        }

    }


}
