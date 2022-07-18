package com.examples;

import com.elsealabs.xshot.lib.ComponentResizer;
import com.elsealabs.xshot.views.LShapeLine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

public class CommentDialogNewDesignPOC extends JDialog {

    private JTextArea textArea;
    private JPanel refreshPanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CommentDialogNewDesignPOC(null, null, null);
            }
        });
    }

    public CommentDialogNewDesignPOC(Frame parent, JPanel refreshPanel, LShapeLine lShapeLine) {

        this.refreshPanel = refreshPanel;

        textArea = new JTextArea("Hello World!");
        //textArea.setFont(new Font("Courier", Font.PLAIN, 22));

        final InputStream is = CommentDialogNewDesignPOC.class.getResourceAsStream("Lato-Regular.ttf");

        try {

            textArea.setFont(Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(18f));

        } catch (FontFormatException e2) {
            e2.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        //textArea.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        //textArea.setSize(new Dimension(300, 200));
        //textArea.setSize(new Dimension(260, 100));
        //textArea.setBorder(BorderFactory.createLineBorder(Color.cyan));
        //System.out.println("Text box at"+(int) lShapeLine.getSecondLine().getP2().getX()+" , "+ (int) lShapeLine.getSecondLine().getP2().getY());

        // create a new button
//        JButton okButton = new JButton("Okay");
//        okButton.setBorder(new RoundedBorder(10)); //10 is the radius
//        okButton.setForeground(Color.decode("#5452f7"));
//
//        JButton cancelButton = new JButton();
//        try{
//
//            Image okIcon = ImageIO.read(getClass().getResource("check_icon.png"));
//            okButton.setIcon(new ImageIcon(okIcon.getScaledInstance(30, 30,  Image.SCALE_SMOOTH)));
//            okButton.setOpaque(false);
//            Image cancelIcon = ImageIO.read(getClass().getResource("cancel_icon.png"));
//            cancelButton.setIcon(new ImageIcon(cancelIcon.getScaledInstance(30, 30,  Image.SCALE_SMOOTH)));
//            cancelButton.setOpaque(false);
//        }
//        catch (IOException e){
//            okButton.setText("okay");
//            cancelButton.setText("cancel");
//        }

        // add action listener
        //okButton.addActionListener(lShapeLine);

        // create a panel
        RoundedPane contentPane = new RoundedPane();
        contentPane.setBackground(Color.white);
        contentPane.setLayout(new BorderLayout());


        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BorderLayout());
        //mainPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#5452f7"), 3));

        // add contents to panel
        JScrollPane scrollPane = new JScrollPane(textArea); //place the JTextArea in a scroll pane
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        //scrollPane.getVerticalScrollBar().addAdjustmentListener(new MyScrollbarListener(textArea, this));
        JLabel title = new JLabel("Add Comment");
        title.setFont(getCustomFont().deriveFont(14f));
        title.setForeground(Color.GRAY);
        title.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        commentPanel.add(title, BorderLayout.NORTH);
        commentPanel.add(scrollPane, BorderLayout.CENTER);
        commentPanel.setBackground(Color.white);


        contentPane.add(commentPanel, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        FlowLayout layout = (FlowLayout)optionPanel.getLayout();
//        layout.setVgap(0);
//        layout.setAlignment(FlowLayout.LEFT);
//        optionPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
//        optionPanel.setOpaque(false);
//        optionPanel.setLayout(layout);
//

        RoundButton okButton = new RoundButton("Okay");
        okButton.setFocusPainted(false);
        okButton.setSize(80, 40);
        okButton.setForeground(Color.white);
        okButton.setBackground(Color.decode("#4240f5"));
        okButton.setColorWhenPressed(Color.decode("#1614f5"));
        okButton.setFont(getCustomFont().deriveFont(15f));
        optionPanel.add(okButton);

        JPanel emptySpace = new JPanel();
        emptySpace.setSize(40,40);
        emptySpace.setBackground(Color.WHITE);
        optionPanel.add(emptySpace);

        RoundButton cancelButton = new RoundButton("Cancel");
        cancelButton.setFocusPainted(false);
        cancelButton.setSize(90, 40);
        cancelButton.setForeground(Color.white);
        cancelButton.setBackground(Color.decode("#4240f5"));
        cancelButton.setColorWhenPressed(Color.decode("#1614f5"));
        cancelButton.setFont(getCustomFont().deriveFont(15f));
        optionPanel.add(cancelButton);
        
        optionPanel.setBackground(Color.white);
        contentPane.add(optionPanel, BorderLayout.SOUTH);

//        contentPane.setBorder(BorderFactory.createLineBorder(Color.black, 1));
//        optionPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
//        commentPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));


        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(300, 200));
        cr.setMaximumSize(new Dimension(800, 600));
        cr.registerComponent(contentPane);
        cr.setSnapSize(new Dimension(10, 10));
        JDialog dialog = this;

        /** Old key listener
//        textArea.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent ke) {
//                System.out.println("Key Code : " + ke.getKeyCode());
//
//                if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE) {
//
////                    System.out.println("text area " + textArea.getHeight());
////                    System.out.println("scroll pane " + scrollPane.getHeight());
//                    //scrollPane.getVerticalScrollBar().removeAdjustmentListener(this);
//                    int dialogHeight = dialog.getContentPane().getComponent(1).getHeight() + textArea.getHeight() + 23 - 50;
//                    dialog.setSize(new Dimension(dialog.getWidth(), dialogHeight));
//                }
//                else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
//
////                    System.out.println("text area " + textArea.getHeight());
////                    System.out.println("scroll pane " + scrollPane.getHeight());
//                    //scrollPane.getVerticalScrollBar().removeAdjustmentListener(this);
//                    int dialogHeight = dialog.getContentPane().getComponent(1).getHeight() + textArea.getHeight() + 23 + 35;
//                    dialog.setSize(new Dimension(dialog.getWidth(), dialogHeight));
//                }
//
//            }
//        });
*/

        textArea.setLineWrap(true);
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

    public Font getCustomFont(){

        Font customFont = null;

        try {
            final InputStream is = CommentDialogNewDesignPOC.class.getResourceAsStream("Lato-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e2) {
            e2.printStackTrace();
            customFont = new Font("Courier", Font.PLAIN, 22);
        }

        return customFont;
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

    class MyScrollbarListener implements AdjustmentListener {

        public JTextArea textArea;
        public JDialog dialog;

        public MyScrollbarListener(JTextArea textArea, JDialog dialog){
            this.textArea = textArea;
            this.dialog = dialog;
        }

        public void adjustmentValueChanged(AdjustmentEvent evt) {
            Adjustable source = evt.getAdjustable();
            if (evt.getValueIsAdjusting()) {
                return;
            }
            int orient = source.getOrientation();
            if (orient == Adjustable.HORIZONTAL) {
                System.out.println("from horizontal scrollbar "+textArea.getHeight() +" "+ textArea.getScrollableTracksViewportHeight());
            } else {

            }

            if(textArea.getHeight() > 30){

                JScrollPane scrollPane = (JScrollPane)((JPanel)dialog.getContentPane().getComponent(0)).getComponent(0);

//            System.out.println("text area "+textArea.getHeight());
//            System.out.println("scroll pane "+scrollPane.getHeight());
//            System.out.println("dialog " + dialog.getHeight());


                //scrollPane.getVerticalScrollBar().removeAdjustmentListener(this);
                int dialogHeight = dialog.getContentPane().getComponent(1).getHeight() + textArea.getHeight() + 23;
                dialog.setSize(new Dimension(dialog.getWidth(), dialogHeight));
                dialog.repaint();
                //scrollPane.getVerticalScrollBar().addAdjustmentListener(this);
            }
        }
    }

    class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
            g.fillRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}









