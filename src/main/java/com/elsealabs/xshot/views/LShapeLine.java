package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.geom.Line2D;

public class LShapeLine {

    // startPoint is first line start point
    private Point startPoint;

    // endPoint is second line end point
    private Point endPoint;

    private Line2D firstLine;
    private Line2D secondLine;

    private LineFlow firstLineFlow;
    private LineDirection firstLineDirection;

    private LineFlow secondLineFlow;
    private LineDirection secondLineDirection;

    private ArrowHead arrowHead;

    public LShapeLine(){
        arrowHead = new ArrowHead();
        firstLine = new Line2D.Double();
        secondLine = new Line2D.Double();
    }


    public LShapeLine(Point startPoint){
        this.startPoint = startPoint;
        arrowHead = new ArrowHead();
        firstLine = new Line2D.Double();
        secondLine = new Line2D.Double();
    }

    public void draw(Graphics g){

        generateLine();

//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.cyan);
//        g2d.setStroke(new BasicStroke(3));

        g.setColor(Color.cyan);
        arrowHead.draw(g);
        LShapeLineUtils.drawLine(firstLine, g);
        LShapeLineUtils.drawLine(secondLine, g);

//        g2d.dispose();
    }

    public void generateLine(){

        if (startPoint != null && endPoint != null) {

            // Find the user intuition whether it is horizontal or vertical. Also find the Direction
            determineMouseFlowAndDirection();

            // Convert the line into L shape line, it is nothing but two perpendicular lines
            createLShapeLineFromTwoPoints();

            // set ArrowHead parameters
            arrowHead.setArrowStartPoint(startPoint);
            arrowHead.setArrowDirection(firstLineDirection);

            // The above steps calculation for first line, now find the second line flow and direction
            determineSecondLineFlowAndDirection();

            //


        }

    }

    private void determineMouseFlowAndDirection(){

        double distance = LShapeLineUtils.calculateDistanceBetweenTwoPoints(startPoint, endPoint);

        // It will consider first 10 pixels to find the flow and direction
        if(distance < 10 && distance > 0) {
            firstLineFlow = LShapeLineUtils.findLineFlow(startPoint, endPoint);
            firstLineDirection = LShapeLineUtils.findLineDirection(startPoint, endPoint);
        }
    }

    /**
     * L shape line is nothing but perpendicular sides of a right angle triangle
     * startPoint and endPoint are the diagonal end points
     */
    private void createLShapeLineFromTwoPoints(){

        if (firstLineFlow == LineFlow.HORIZONTAL) {
            // These perpendicular lines are upper side of diagonal
            firstLine.setLine(startPoint.x, startPoint.y, endPoint.x, startPoint.y);
            secondLine.setLine(endPoint.x, startPoint.y, endPoint.x, endPoint.y);
        } else {
            // These perpendicular lines are bottom side of diagonal
            firstLine.setLine(startPoint.x, startPoint.y, startPoint.x, endPoint.y);
            secondLine.setLine(startPoint.x, endPoint.y, endPoint.x, endPoint.y);
        }
    }

    private void determineSecondLineFlowAndDirection(){

        Point p1 = LShapeLineUtils.convertToPoint(secondLine.getP1());
        Point p2 = LShapeLineUtils.convertToPoint(secondLine.getP2());

        secondLineFlow = LShapeLineUtils.findLineFlow(p1, p2);
        secondLineDirection = LShapeLineUtils.findLineDirection(p1, p2);
    }



    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Line2D getSecondLine() {
        return secondLine;
    }

    public void setSecondLine(Line2D secondLine) {
        this.secondLine = secondLine;
    }

    public LineFlow getFirstLineFlow() {
        return firstLineFlow;
    }

    public void setFirstLineFlow(LineFlow firstLineFlow) {
        this.firstLineFlow = firstLineFlow;
    }

    public LineFlow getSecondLineFlow() {
        return secondLineFlow;
    }

    public void setSecondLineFlow(LineFlow secondLineFlow) {
        this.secondLineFlow = secondLineFlow;
    }

    public LineDirection getFirstLineDirection() {
        return firstLineDirection;
    }

    public void setFirstLineDirection(LineDirection firstLineDirection) {
        this.firstLineDirection = firstLineDirection;
    }

    public LineDirection getSecondLineDirection() {
        return secondLineDirection;
    }

    public void setSecondLineDirection(LineDirection secondLineDirection) {
        this.secondLineDirection = secondLineDirection;
    }

    public Line2D getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(Line2D firstLine) {
        this.firstLine = firstLine;
    }

    @Override
    public String toString() {
        return "LShapeLine{" +
                "startPoint=" + startPoint.x +","+startPoint.y+"\n"+
                ", endPoint=" + endPoint.x +", "+endPoint.y+"\n"+
                ", firstLine=" + firstLine.getX1() +", "+firstLine.getY1() +", "+firstLine.getX2() +", "+firstLine.getY2() +", "+"\n"+
                ", secondLine=" + secondLine.getX1() +", "+secondLine.getY1() +", "+secondLine.getX2() +", "+secondLine.getY2() +", "+"\n"+
                ", firstLineFlow=" + firstLineFlow +"\n"+
                ", firstLineDirection=" + firstLineDirection +"\n"+
                ", secondLineFlow=" + secondLineFlow +"\n"+
                ", secondLineDirection=" + secondLineDirection +"\n"+
                ", arrowHead=" + arrowHead +
                '}';
    }


    //        else if(getInitialLineFlow() == LineFlow.HORIZONTAL) {
//
//            if(mouseDraggedPoint.x < mousePressedPoint.x) {
//                mouseDraggedPoint = mousePressedPoint;
//            }
//        }
//        else if(getInitialLineFlow() == LineFlow.VERTICAL) {
//
//            if(mouseDraggedPoint.y < mousePressedPoint.y) {
//                mouseDraggedPoint = mousePressedPoint;
//            }
//        }
}
