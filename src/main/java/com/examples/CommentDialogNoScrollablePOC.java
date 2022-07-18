package com.examples;

import com.elsealabs.xshot.lib.ComponentResizer;
import com.elsealabs.xshot.views.LShapeLine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CommentDialogNoScrollablePOC extends JDialog {

    private JTextArea textArea;
    private JPanel refreshPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CommentDialogNoScrollablePOC(null, null, null);
            }
        });
    }

    public CommentDialogNoScrollablePOC(Frame parent, JPanel refreshPanel, LShapeLine lShapeLine) {

        this.refreshPanel = refreshPanel;

        textArea = new JTextArea("Hello World!");
        textArea.setFont(new Font("Courier", Font.PLAIN, 30));
        //textArea.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //textArea.setSize(new Dimension(300, 200));
        //textArea.setSize(new Dimension(260, 100));
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
            okButton.setText("okay");
            cancelButton.setText("cancel");
        }

        // add action listener
        okButton.addActionListener(lShapeLine);

        // create a panel
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        RoundedPane commentPanel = new RoundedPane();
        commentPanel.setLayout(new BorderLayout());
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#5452f7"), 3));

        // add contents to panel
        JScrollPane scrollPane = new JScrollPane(textArea); //place the JTextArea in a scroll pane
        //scrollPane.getVerticalScrollBar().addAdjustmentListener(new MyScrollbarListener(textArea, this));
        commentPanel.add(textArea, BorderLayout.CENTER);
        commentPanel.setBackground(Color.white);

        contentPane.add(commentPanel, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();

        optionPanel.add(okButton);
        optionPanel.add(cancelButton);
        optionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        optionPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        contentPane.add(optionPanel, BorderLayout.SOUTH);

        ComponentResizer cr = new ComponentResizer();
//        cr.setMinimumSize(new Dimension(300, 300));
//        cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(commentPanel);
        cr.setSnapSize(new Dimension(10, 10));
        optionPanel.setOpaque(false);

        JDialog dialog = this;

        textArea.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {

                if(textArea.getCaretPosition() > 0) {

                    int charBeforeCursor = textArea.getText().toCharArray()[textArea.getCaretPosition() - 1];
                    System.out.println("Caret Position : " + charBeforeCursor);

                    if (charBeforeCursor == KeyEvent.VK_ENTER
                            && (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE)) {

                        int dialogHeight = dialog.getContentPane().getComponent(1).getHeight() + textArea.getHeight() + 23 - 30;
                        dialog.setSize(new Dimension(dialog.getWidth(), dialogHeight));
                    } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                        int dialogHeight = dialog.getContentPane().getComponent(1).getHeight() + textArea.getHeight() + 23 + 30;
                        dialog.setSize(new Dimension(dialog.getWidth(), dialogHeight));
                    }
                }
            }
        });

        this.setContentPane(contentPane);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setSize(new Dimension(300, 200));
        this.setVisible(true);

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

    class RoundedPane extends JPanel {

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









