package com.elsealabs.xshot.views;

import com.elsealabs.xshot.lib.ComponentResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CommentDialog_Backup extends JDialog {

    private JTextArea textArea;
    private JPanel refreshPanel;

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new CommentDialog(null, null, null);
//            }
//        });
//
//
//    }

    public CommentDialog_Backup(Frame parent, JPanel refreshPanel, LShapeLine lShapeLine) {

        this.refreshPanel = refreshPanel;

        textArea = new JTextArea("Hello World!");


//            InputStream is = CommentDialog.class.getResourceAsStream("Lato-Regular.ttf");
//            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            textArea.setFont(new Font("Courier", Font.PLAIN, 16));



        //textArea.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        textArea.setSize(new Dimension(300, 200));
        //textArea.setBorder(BorderFactory.createLineBorder(Color.cyan));
        //System.out.println("Text box at"+(int) lShapeLine.getSecondLine().getP2().getX()+" , "+ (int) lShapeLine.getSecondLine().getP2().getY());

        // create a new button
        JButton okButton = new JButton();
        JButton cancelButton = new JButton();
        try{

            Image okIcon = ImageIO.read(getClass().getResource("check_icon.png"));
            okButton.setIcon(new ImageIcon(okIcon.getScaledInstance(30, 30,  Image.SCALE_SMOOTH)));
            okButton.setOpaque(false);
            Image cancelIcon = ImageIO.read(getClass().getResource("cancel_icon.png"));
            cancelButton.setIcon(new ImageIcon(cancelIcon.getScaledInstance(30, 30,  Image.SCALE_SMOOTH)));
            cancelButton.setOpaque(false);
        }
        catch (IOException e){

        }

        // add action listener
        okButton.addActionListener(lShapeLine);

        // create a panel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        RoundedPane mainPanel = new RoundedPane();
        mainPanel.setLayout(new BorderLayout());
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#5452f7"), 3));

        // add contents to panel
        mainPanel.add(textArea, BorderLayout.CENTER);
        mainPanel.setBackground(Color.white);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.setOpaque(false);
        optionPanel.add(okButton);
        optionPanel.add(cancelButton);
        optionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add(optionPanel, BorderLayout.SOUTH);

        this.setContentPane(contentPane);
        this.setUndecorated(true);
        this.setSize(new Dimension(300, 200));
        this.setVisible(false);

        this.setBackground(new Color(0, 0, 0, 0));
        //this.setModal(true);
        //this.setSize(640, 480);
        //this.setLocationRelativeTo(null);

        ComponentResizer cr = new ComponentResizer();
//        cr.setMinimumSize(new Dimension(300, 300));
//        cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(mainPanel);
        cr.setSnapSize(new Dimension(10, 10));

        setUndecorated(true);
        setVisible(false);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JPanel getRefreshPanel() {
        return refreshPanel;
    }

    public void setRefreshPanel(JPanel refreshPanel) {
        this.refreshPanel = refreshPanel;
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
            g2.setStroke(new BasicStroke(2));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() -1, getRadius(), getRadius());
            g2.setColor(Color.decode("#5452f7"));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() -1, getRadius(), getRadius());

            super.paintComponent(g);
        }
    }

}




