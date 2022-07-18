package com.examples;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {

    private Shape shape;
    private Color colorWhenPressed;

    public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false);
    }

    public RoundButton(String label, Icon icon) {
        super(label, icon);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    public RoundButton(Icon icon) {
        super(icon);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    public RoundButton(Action action) {
        super(action);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setPreferredSize(new Dimension(width, height));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            g2.setColor(getColorWhenPressed());
        } else {
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
        super.paintComponent(g2);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

    public Color getColorWhenPressed() {
        return colorWhenPressed;
    }

    public void setColorWhenPressed(Color colorWhenPressed) {
        this.colorWhenPressed = colorWhenPressed;
    }

}

