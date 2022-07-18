package com.examples;

import java.awt.*;

import javax.swing.*;

public class JLayerExample {
    public static void main(String args[]) {
        JFrame f = new JFrame("JDesktopPane Sample");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        JLayeredPane desktop = new JLayeredPane();
        desktop.setOpaque(false);
        desktop.add(createHighlighter(), JLayeredPane.POPUP_LAYER);
        desktop.add(createPanel(), JLayeredPane.POPUP_LAYER);

        desktop.add(createLayer("Iconified"), JLayeredPane.DEFAULT_LAYER);
        desktop.add(createLayer("Open 2"), JLayeredPane.PALETTE_LAYER);
        content.add(desktop, BorderLayout.CENTER);
        f.setSize(500, 500);
        f.setVisible(true);
    }

    public static JInternalFrame createLayer(String label) {
        return new SelfInternalFrame(label);
    }

    public static JPanel createPanel(){
        JPanel panel = new ImagePanel();
//        panel.setBackground(Color.blue);
        panel.setSize(400, 400);
        return panel;
    }

    public static JPanel createHighlighter(){
        JPanel panel = new Highlighter();
//        panel.setBackground(Color.blue);
        panel.setSize(300, 300);
        return panel;
    }


    static class SelfInternalFrame extends JInternalFrame {
        public SelfInternalFrame(String s) {
            getContentPane().add(new JLabel(s), BorderLayout.CENTER);
            setBounds(50, 50, 100, 100);
            setResizable(true);
            //setClosable(true);
            //setMaximizable(true);
            //setIconifiable(true);
            setTitle(s);
            setVisible(true);
        }
    }
}