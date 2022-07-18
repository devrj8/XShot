package com.examples;

import com.elsealabs.xshot.lib.ComponentResizer;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class UndecoratedExample {

    private JDialog frame = new JDialog();

    class MainPanel extends JPanel {

        public MainPanel() {
            setBackground(Color.black);
            setBorder(new LineBorder(Color.CYAN, 5));
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
    }

    private void createAnsShowGui() {

        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(300, 300));
        cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(frame);
        cr.setSnapSize(new Dimension(10, 10));
        frame.setUndecorated(true);
        frame.add(new MainPanel());
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UndecoratedExample().createAnsShowGui();
            }
        });
    }
}