package com.elsealabs.xshot.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawLShapeLineMouseListener implements MouseListener, MouseMotionListener {

    private Point mousePressedPoint;
    private Point mouseDraggedPoint;
    private Point mouseReleasedPoint;
    private LShapeLine lShapeLine;

    ArrowCommentPanel arrowCommentPanel;

    public DrawLShapeLineMouseListener(ArrowCommentPanel arrowCommentPanel){
        this.arrowCommentPanel = arrowCommentPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        mousePressedPoint = e.getPoint();
        lShapeLine = new LShapeLine(mousePressedPoint, (JPanel) e.getSource());

        // Add line to Parent component line list, so it will remember all lines and will draw them  when paint method called every time.
        if(e.getSource() instanceof ArrowCommentPanel)
            ((ArrowCommentPanel) e.getSource()).getlShapeLineList().add(lShapeLine);
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
        arrowCommentPanel.getLayerPaintListener().layerPainted(Arrays.asList(lShapeLine));
        arrowCommentPanel.clearGraphics();
        //lShapeLine.setDrawingFinished(true);
        lShapeLine.showCommentDialog((JFrame)(((ArrowCommentPanel) e.getSource()).getParent()).getParent().getParent().getParent().getParent().getParent(), (JPanel) e.getSource());
        //addTextArea((PanelCapture) e.getSource());

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

}
