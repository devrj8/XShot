package com.examples;

import com.elsealabs.xshot.lib.ComponentResizer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RoundCornerDialog extends JDialog{

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoundCornerDialog();
            }
        });
    }

    public RoundCornerDialog() {

        JTextArea textArea = new JTextArea("Hello World!");
        textArea.setSize(new Dimension(300, 200));


        // create a new button
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        // create a panel
        RoundedPane mainPanel = new RoundedPane();
        mainPanel.setLayout(new BorderLayout());

        // add contents to panel
        mainPanel.add(textArea, BorderLayout.CENTER);
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BorderLayout());
        optionPanel.add(okButton, BorderLayout.WEST);
        optionPanel.add(cancelButton, BorderLayout.EAST);
        mainPanel.add(optionPanel, BorderLayout.SOUTH);

        this.setUndecorated(true);
        this.setSize(new Dimension(300, 200));
        this.setVisible(false);

        this.setBackground(new Color(0, 0, 0, 0));
        this.setModal(true);
        this.setContentPane(mainPanel);
        this.setSize(640, 480);
        this.setLocationRelativeTo(null);

        ComponentResizer cr = new ComponentResizer();
        cr.registerComponent(this);
        cr.setSnapSize(new Dimension(10, 10));
        this.setVisible(true);

    }

    public class RoundedPane extends JPanel {

        private int radius = 20;

        public RoundedPane() {

            setOpaque(false);
            setBorder(new EmptyBorder(10, 10, 10, 10));
        }

        public void setRadius(int radius) {
            this.radius = radius;
            setBorder(new EmptyBorder(radius / 2, radius / 2, radius / 2, radius / 2));
            revalidate();
            repaint();
        }

        public int getRadius() {
            return radius;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
            super.paintComponent(g);
        }
    }
}
