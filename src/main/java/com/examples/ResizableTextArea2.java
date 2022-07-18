package com.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class ResizableTextArea2 extends  JFrame{

    public ResizableTextArea2(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());  //give your JPanel a BorderLayout

        JTextArea text = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(text); //place the JTextArea in a scroll pane
        panel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new MyAdjustmentListener(text, this));
        this.setContentPane(panel);
        this.setVisible(true);
        this.setSize(200, 100);
    }

    public static void main(String[] args) {
        new ResizableTextArea2();
    }
}

class MyAdjustmentListener implements AdjustmentListener {

    public JTextArea textArea;
    public JFrame frame;

    public MyAdjustmentListener(JTextArea textArea, JFrame frame){
        this.textArea = textArea;
        this.frame = frame;
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
            System.out.println("from vertical scrollbar "+textArea.getHeight() +" "+ textArea.getScrollableTracksViewportHeight());

            //scrollPane.setPreferredSize(new Dimension((int)scrollPane.getPreferredSize().getWidth(), textArea.getHeight()));
            if(textArea.getHeight() > 30)
                frame.setSize(new Dimension(frame.getWidth(), textArea.getHeight()+50));
        }
        int type = evt.getAdjustmentType();
        switch (type) {
            case AdjustmentEvent.UNIT_INCREMENT:
                System.out.println("Scrollbar was increased by one unit");
                break;
            case AdjustmentEvent.UNIT_DECREMENT:
                System.out.println("Scrollbar was decreased by one unit");
                break;
            case AdjustmentEvent.BLOCK_INCREMENT:
                System.out.println("Scrollbar was increased by one block");
                break;
            case AdjustmentEvent.BLOCK_DECREMENT:
                System.out.println("Scrollbar was decreased by one block");
                break;
            case AdjustmentEvent.TRACK:
                System.out.println("The knob on the scrollbar was dragged");
                break;
        }
        int value = evt.getValue();
    }
}
