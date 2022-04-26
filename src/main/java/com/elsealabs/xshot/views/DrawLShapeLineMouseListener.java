package com.elsealabs.xshot.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawLShapeLineMouseListener implements MouseListener, MouseMotionListener {

    private Point mousePressedPoint;
    private Point mouseDraggedPoint;
    private Point mouseReleasedPoint;
    private LShapeLine lShapeLine;


    @Override
    public void mousePressed(MouseEvent e) {

        mousePressedPoint = e.getPoint();
        lShapeLine = new LShapeLine(mousePressedPoint);

        // Add line to Parent component line list, so it will remember all lines and will draw them  when paint method called every time.
        if(e.getSource() instanceof PanelCapture)
            ((PanelCapture) e.getSource()).getlShapeLineList().add(lShapeLine);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        mouseDraggedPoint = e.getPoint();
        lShapeLine.setEndPoint(mouseDraggedPoint);

        e.getComponent().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        mouseReleasedPoint = e.getPoint();
        lShapeLine.setEndPoint(mouseReleasedPoint);

        addTextArea((PanelCapture) e.getSource());
        e.getComponent().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void addTextArea(PanelCapture panelCapture){

        // Add text area at the second line end
        if(lShapeLine.getSecondLineFlow() == LineFlow.HORIZONTAL && lShapeLine.getSecondLineDirection() == LineDirection.LEFT_TO_RIGHT) {
            JTextArea textArea = new JTextArea("Hello World!");
            textArea.setSize(200, 100);
            textArea.setOpaque(false);
            textArea.setBorder(BorderFactory.createLineBorder(Color.red));
            textArea.setBounds((int) lShapeLine.getSecondLine().getP2().getX(), (int) lShapeLine.getSecondLine().getP2().getY(), 200, 100);
            System.out.println("Text box at"+(int) lShapeLine.getSecondLine().getP2().getX()+" , "+ (int) lShapeLine.getSecondLine().getP2().getY());
            panelCapture.setLayout(null);
            panelCapture.add(textArea);
            panelCapture.repaint();
        }


    }

}
