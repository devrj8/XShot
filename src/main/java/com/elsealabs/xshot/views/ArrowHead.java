package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Drawing is Arrow is combination of two line perpendicularly
 */
public class ArrowHead {

    private Point arrowStartPoint;
    private LineDirection arrowDirection;

    public void draw(Graphics g){
        int arrowSize = 7;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2.0f));

        if(arrowDirection == LineDirection.TOP_TO_BOTTOM) {
            g.drawLine(arrowStartPoint.x - arrowSize, arrowStartPoint.y + arrowSize, arrowStartPoint.x, arrowStartPoint.y);
            g.drawLine(arrowStartPoint.x, arrowStartPoint.y, arrowStartPoint.x + arrowSize + 1, arrowStartPoint.y + arrowSize + 1);
        }
        else if(arrowDirection == LineDirection.BOTTOM_TO_TOP){
            g.drawLine(arrowStartPoint.x-arrowSize, arrowStartPoint.y-arrowSize, arrowStartPoint.x, arrowStartPoint.y);
            g.drawLine(arrowStartPoint.x, arrowStartPoint.y, arrowStartPoint.x+arrowSize+1,arrowStartPoint.y-arrowSize+1);
        }
        else if(arrowDirection == LineDirection.RIGHT_TO_LEFT){
            g.drawLine(arrowStartPoint.x-arrowSize, arrowStartPoint.y-arrowSize, arrowStartPoint.x, arrowStartPoint.y);
            g.drawLine(arrowStartPoint.x, arrowStartPoint.y, arrowStartPoint.x-arrowSize+1,arrowStartPoint.y+arrowSize+1);
        }
        else {
            g.drawLine(arrowStartPoint.x+arrowSize, arrowStartPoint.y-arrowSize, arrowStartPoint.x, arrowStartPoint.y);
            g.drawLine(arrowStartPoint.x, arrowStartPoint.y, arrowStartPoint.x+arrowSize+1,arrowStartPoint.y+arrowSize+1);
        }
    }

    public Point getArrowStartPoint() {
        return arrowStartPoint;
    }

    public void setArrowStartPoint(Point arrowStartPoint) {
        this.arrowStartPoint = arrowStartPoint;
    }

    public LineDirection getArrowDirection() {
        return arrowDirection;
    }

    public void setArrowDirection(LineDirection arrowDirection) {
        this.arrowDirection = arrowDirection;
    }



}
