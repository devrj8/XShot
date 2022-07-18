package com.elsealabs.xshot.views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class ArrowCommentPanel extends JPanel {

    private List<LShapeLine> lShapeLineList;
    private LayerPaintListener layerPaintListener;

    public ArrowCommentPanel(int width, int height){
        setSize(width, height);
        DrawLShapeLineMouseListener drawLShapeLineMouseListener= new DrawLShapeLineMouseListener(this);
        addMouseListener(drawLShapeLineMouseListener);
        addMouseMotionListener(drawLShapeLineMouseListener);
        lShapeLineList = new ArrayList<LShapeLine>();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawString("Yo man Transparent panel and draw", 100, 100);
        g.setColor(new Color(250, 8, 116, 100));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(20));
        g.drawLine(500, 150, 300, 150);

        for(LShapeLine line : getlShapeLineList()){
            line.draw(g);
        }

        //getlShapeLineList().stream().filter(line -> line.isDrawingFinished()).forEach(finishedLine -> finishedLine.draw(imgGraphics));



        g2.dispose();
    }

    public void clearGraphics(){
        getlShapeLineList().clear();
        repaint();
    }

    public void addLayerPaintListener(LayerPaintListener layerPaintListener){
        this.layerPaintListener = layerPaintListener;
    }

    public LayerPaintListener getLayerPaintListener(){
        return layerPaintListener;
    }

    public java.util.List<LShapeLine> getlShapeLineList() {
        return lShapeLineList;
    }

    public void setlShapeLineList(List<LShapeLine> lShapeLineList) {
        this.lShapeLineList = lShapeLineList;
    }

    //TODO : remove this on production
    private void addSomeDummyLines(Graphics g){

        LShapeLine line1 = new LShapeLine();
        line1.setStartPoint(new Point(734, 291));
        line1.setEndPoint(new Point(755, 291));
        line1.setFirstLine(new Line2D.Double(734.0, 291.0, 755.0, 291.0));
        line1.setSecondLine(new Line2D.Double(755.0, 291.0, 755.0, 291.0));
        line1.setFirstLineFlow(LineFlow.HORIZONTAL);
        line1.setFirstLineDirection(LineDirection.LEFT_TO_RIGHT);

        lShapeLineList.add(line1);

        LShapeLine line2 = new LShapeLine();
        line2.setStartPoint(new Point(234, 700));
        line2.setEndPoint(new Point(555, 700));
        line2.setFirstLine(new Line2D.Double(734.0, 291.0, 755.0, 291.0));
        line2.setSecondLine(new Line2D.Double(755.0, 291.0, 755.0, 291.0));
        line2.setFirstLineFlow(LineFlow.HORIZONTAL);
        line2.setFirstLineDirection(LineDirection.LEFT_TO_RIGHT);

        lShapeLineList.add(line2);

        if(lShapeLineList.size() > 1) {
            for (LShapeLine lShapeLine : lShapeLineList) {
                lShapeLine.draw(g);
                System.out.println(lShapeLine);
            }
        }
    }
}
