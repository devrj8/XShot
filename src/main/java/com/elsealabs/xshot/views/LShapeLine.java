package com.elsealabs.xshot.views;

import com.elsealabs.xshot.lib.ComponentResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class LShapeLine implements ActionListener {

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

    private boolean drawingFinished;

    private CommentDialog commentDialog;

    private CommentBox commentBox;

    private JPanel parent;

    public void setDrawingFinished(boolean drawingFinished) {
        this.drawingFinished = drawingFinished;
    }

    public boolean isDrawingFinished() {
        return drawingFinished;
    }

    public LShapeLine(){
        arrowHead = new ArrowHead();
        firstLine = new Line2D.Double();
        secondLine = new Line2D.Double();
        drawingFinished = false;
        commentBox = new CommentBox();
    }


    public LShapeLine(Point startPoint, JPanel parent){
        this.startPoint = startPoint;
        arrowHead = new ArrowHead();
        firstLine = new Line2D.Double();
        secondLine = new Line2D.Double();
        drawingFinished = false;
        commentBox = new CommentBox();
        this.parent = parent;
    }

    public void draw(Graphics g){

        generateLine();

//        Graphics2D g2d = (Graphics2D) g;
//        g.setColor(Color.cyan);
//        g2d.setStroke(new BasicStroke(3));

        g.setColor(Color.decode("#5452f7"));
        arrowHead.draw(g);
        //LShapeLineUtils.drawLine(firstLine, g);
        drawCurveBetweenTwoLines(g, firstLine, secondLine);
        //LShapeLineUtils.drawLine(secondLine, g);

        if(isDrawingFinished()) {

            //g.drawRect((int) secondLine.getX2(), (int) secondLine.getY2()-(commentBox.height/2), commentBox.width, commentBox.height);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setStroke(new BasicStroke(2));

            g2.setColor(Color.white);
            g2.fillRoundRect((int) secondLine.getX2(), (int) secondLine.getY2()-(commentBox.height/2), commentBox.width, commentBox.height, 20,20);
            g2.setColor(Color.decode("#5452f7"));
            g2.drawRoundRect((int) secondLine.getX2(), (int) secondLine.getY2()-(commentBox.height/2), commentBox.width, commentBox.height, 20,20);

            g.setFont(new Font("Courier", Font.PLAIN, 24));
            g.drawString(commentBox.text, (int) secondLine.getX2() + 15, (int) secondLine.getY2());
        }
//        g2d.dispose();
    }

    private void drawCurveBetweenTwoLines(Graphics g, Line2D firstLine, Line2D secondLine){
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2.0f));
        g2.setPaint(Color.green);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

        int arc = 30;
        Point2D curveP1;
        Point2D curveP2;
        Point2D curveP3;

        curveP1 = getCurveStartPoint(arc, firstLine, firstLineFlow, firstLineDirection, true);

        curveP2 = new Point2D.Double(firstLine.getX2(), firstLine.getY2());

        curveP3 = getCurveStartPoint(arc, secondLine , secondLineFlow, secondLineDirection, false);


        path.moveTo(firstLine.getX1(), firstLine.getY1());
        path.lineTo(curveP1.getX(), curveP1.getY());
        path.curveTo(curveP1.getX(), curveP1.getY(), curveP2.getX(), curveP2.getY(), curveP3.getX(), curveP3.getY());
        //path.lineTo(curveP3.getX(), curveP3.getY());
        path.lineTo(secondLine.getX2(), secondLine.getY2());
        g2.draw(path);
        g2.setPaint(Color.red);

//        Curve Debugging points -- don't remove it
//        g2.drawLine((int) curveP1.getX(), (int) curveP1.getY(),(int) curveP1.getX(), (int) curveP1.getY());
//        g2.drawLine((int) curveP2.getX(), (int) curveP2.getY(),(int) curveP2.getX(), (int) curveP2.getY());
//        g2.drawLine((int) curveP3.getX(), (int) curveP3.getY(),(int) curveP3.getX(), (int) curveP3.getY());

    }

    private Point2D getCurveStartPoint(int arc, Line2D line, LineFlow flow, LineDirection direction, boolean isCurveStartFromEndPoint){

        Point2D curvePoint = null;

        Point2D curveBeginPoint = line.getP2();
        int curveDirection = 1;

        // These configurations are for second line, second line curve starting point would be from first point
        if(!isCurveStartFromEndPoint) {
            curveBeginPoint = line.getP1();
            curveDirection = -1;
        }

        if(flow == LineFlow.HORIZONTAL && direction == LineDirection.LEFT_TO_RIGHT){
            curvePoint = new Point2D.Double(curveBeginPoint.getX() - (arc * curveDirection), curveBeginPoint.getY());
        }
        else if(flow == LineFlow.HORIZONTAL && direction == LineDirection.RIGHT_TO_LEFT){
            curvePoint = new Point2D.Double(curveBeginPoint.getX() + (arc * curveDirection), curveBeginPoint.getY());
        }
        else  if(flow == LineFlow.VERTICAL && direction == LineDirection.TOP_TO_BOTTOM){
            curvePoint = new Point2D.Double(curveBeginPoint.getX(), curveBeginPoint.getY() - (arc * curveDirection));
        }
        else  if(flow == LineFlow.VERTICAL && direction == LineDirection.BOTTOM_TO_TOP){
            curvePoint = new Point2D.Double(curveBeginPoint.getX(), curveBeginPoint.getY() + (arc * curveDirection));
        }

        return curvePoint;
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

    private void addTextArea(PanelCapture panelCapture){

        // Add text area at the second line end
        //if(lShapeLine.getSecondLineFlow() == LineFlow.HORIZONTAL && lShapeLine.getSecondLineDirection() == LineDirection.LEFT_TO_RIGHT) {
        JTextArea textArea = new JTextArea("Hello World!");
        textArea.setSize(200, 100);
        textArea.setBorder(BorderFactory.createLineBorder(Color.cyan));
        //System.out.println("Text box at"+(int) lShapeLine.getSecondLine().getP2().getX()+" , "+ (int) lShapeLine.getSecondLine().getP2().getY());
        panelCapture.setLayout(null);
        panelCapture.add(textArea);
        textArea.repaint();

        JDialog d = new JDialog(panelCapture.frame, "dialog Box");
        JLabel l = new JLabel("this is a dialog box");
        d.add(textArea);
        d.setSize(100, 300);
        //d.setBounds((int) lShapeLine.getSecondLine().getP2().getX(), (int) lShapeLine.getSecondLine().getP2().getY(), 200, 100);
        d.setUndecorated(true);
        d.setVisible(true);
    }

    public void showCommentDialog(Frame frame, JPanel refreshPanel){

        commentDialog = new CommentDialog(frame, refreshPanel,this);

        if(secondLineFlow == LineFlow.HORIZONTAL && secondLineDirection == LineDirection.LEFT_TO_RIGHT) {
            System.out.println("x : " + getSecondLine().getP2().getX());
            System.out.println("y : " + getSecondLine().getP2().getY());
            System.out.println("Dialog widht : "+ commentBox.width);
            System.out.println("Dialog height : "+ commentBox.height);
            System.out.println("Modified Y value"+ (getSecondLine().getP2().getY() - (commentDialog.getHeight()/2)));
            commentDialog.setBounds((int) getSecondLine().getP2().getX(), (int) getSecondLine().getP2().getY() + (commentBox.height / 2),
                    commentDialog.getWidth(), commentDialog.getHeight());
        }
        else if(secondLineFlow == LineFlow.HORIZONTAL && secondLineDirection == LineDirection.RIGHT_TO_LEFT) {
            commentDialog.setBounds((int) getSecondLine().getP2().getX() - commentDialog.getWidth(),
                    (int) getSecondLine().getP2().getY() - (commentBox.height / 2),
                    commentDialog.getWidth(), commentDialog.getHeight());
        }
        else if(secondLineFlow == LineFlow.VERTICAL && secondLineDirection == LineDirection.TOP_TO_BOTTOM)
            commentDialog.setBounds((int) getSecondLine().getP2().getX()-50, (int) getSecondLine().getP2().getY(), commentDialog.getWidth(), commentDialog.getHeight());

        else if(secondLineFlow == LineFlow.VERTICAL && secondLineDirection == LineDirection.BOTTOM_TO_TOP)
            commentDialog.setBounds((int) getSecondLine().getP2().getX() - (commentDialog.getWidth()/2), (int) (getSecondLine().getP2().getY() - commentDialog.getHeight()), commentDialog.getWidth(), commentDialog.getHeight());

        commentDialog.setLocationRelativeTo(refreshPanel);
        commentDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        commentBox.text =commentDialog.getTextArea().getText();
        commentBox.width = commentDialog.getWidth();
        commentBox.height = commentDialog.getHeight();
        setDrawingFinished(true);
        commentDialog.setVisible(false);
        commentDialog.getRefreshPanel().repaint();
    }
}

class CommentBox{
    public String text = "";
    public int width = 200;
    public int height = 100;
}
