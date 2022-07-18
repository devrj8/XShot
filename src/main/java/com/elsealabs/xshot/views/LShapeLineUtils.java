package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LShapeLineUtils {


    public static void drawLine(Line2D line, Graphics g){

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
    }

    public static LineDirection findLineDirection(Point startPoint, Point endPoint){

        if(findLineFlow(startPoint, endPoint) == LineFlow.HORIZONTAL){
            if((endPoint.x - startPoint.x)  < 0)
                return LineDirection.RIGHT_TO_LEFT;
            else
                return LineDirection.LEFT_TO_RIGHT;
        }
        else {
            if((endPoint.y - startPoint.y)  < 0)
                return LineDirection.BOTTOM_TO_TOP;
            else
                return LineDirection.TOP_TO_BOTTOM;
        }
    }

    // is line drawn more than given length either horizontally or vertically
    public static boolean isLineLengthExceeded(Point startPoint, Point endPoint, int length) {

        double xDiff = Math.abs(startPoint.x - endPoint.x);
        double yDiff = Math.abs(startPoint.y - endPoint.y);

        return length < xDiff || length < yDiff;
    }

    // is line drawn more than given length either horizontally or vertically
    public static double calculateDistanceBetweenTwoPoints(Point startPoint, Point endPoint) {

        double sideX = startPoint.x - endPoint.x;
        double sideY = startPoint.y - endPoint.y;

        // Pythagorean theorem
        double distance = Math.sqrt(Math.pow(sideX, 2)+ Math.pow(sideY, 2));

        return distance;
    }

    /**
     If line occupy the x-axis then it will consider as Horizontal else it is Vertical
     If both occupy same i.e. diagonal, it will return default as Horizontal
     Line need not be exact horizontal or vertical, even if the line has slope, it will consider it.
     */
    public static LineFlow findLineFlow(Point startPoint, Point endPoint){

        double xDiff = Math.abs(startPoint.x - endPoint.x);
        double yDiff = Math.abs(startPoint.y - endPoint.y);

        return xDiff < yDiff ? LineFlow.VERTICAL : LineFlow.HORIZONTAL;
    }

    public static Point convertToPoint(Point2D point2d){
        return new Point((int) point2d.getX(), (int) point2d.getY());
    }

    public static double GetAngleOfLineBetweenTwoPoints(Point p1, Point p2)
    {
        double xDiff = p2.x - p1.x;
        double yDiff = p2.y - p1.y;

        System.out.println("Degree : "+Math.toDegrees(Math.atan2(yDiff, xDiff)));
        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }
}

enum LineFlow {
    VERTICAL, HORIZONTAL
}

enum LineDirection {
    LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
}
