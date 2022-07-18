package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.Capture.AREA;
import com.elsealabs.xshot.math.Scale;


public class PanelCapture extends JLayeredPane
{
	// Referenced outside objects

	private JComponent  parent;
	public JFrame      frame;
	private Capture     capture;
	private JScrollPane scrollPane;

	// State-related attributes

	private boolean debug = false;
	private boolean saved = false;

	// Size-related attributes

	private int width;
	private int height;

	private Dimension frameSize;
	private Point viewportPoint;
	
	private Scale scale = Scale.getInstance();

	// Information regarding collision

    private int padWidth;
    private int colWidth;
    private int colHeight;

    private Rectangle imageWhole;
    private Rectangle imageNorth;
    private Rectangle imageSouth;
    private Rectangle imageEast;
    private Rectangle imageWest;

    private AREA    currentArea;
    private Point   initial;
    private boolean pressListening;

    // Image hover hints

    private AREA hoverArea;

	private JPanel container;

	private Screenshot screenshot;

	/**
	 * Creates a new PanelCapture
	 *
	 * @param parent  The component the capture is inside of
	 * @param frame   The frame the parent and the panel are inside of
	 * @param capture The capture to display in the panel
	 */
	public PanelCapture(JComponent parent, JFrame frame, Capture capture, JPanel container)
	{
		this.parent  = parent;
		this.frame   = frame;
		this.capture = capture;

		if (parent instanceof JScrollPane) scrollPane = ((JScrollPane) parent);

		width  = capture.getTotalBounds().width;
		height = capture.getTotalBounds().height;

        imageWhole = new Rectangle();
        imageNorth = new Rectangle();
        imageSouth = new Rectangle();
        imageEast  = new Rectangle();
        imageWest  = new Rectangle();

        padWidth  = 10;
        colWidth  = 20;
        colHeight = 20;

		frameSize = new Dimension(0, 0);

		this.container = container;
		//_addListeners();


//		addSomeLines();

	}



	/**
	 * Paints the components and handles when information regarding
	 * positioning should be updated
	 */
	public void paintx()
	{
		
		/**
		 * Scale all used bounds
		 */
		
		Rectangle boundsUpdatedScaled = capture.getUpdatedBounds();
		
		//System.out.println("SCALED BOUNDS: " + boundsUpdatedScaled);

		/**
		 * This ensures that the viewport will only be repositioned when the user
		 * is re-sizing the frame.
		 */


//        g.drawImage(
//        	capture.getUpdatedImage(),
//        	boundsUpdatedScaled.x,
//        	boundsUpdatedScaled.y,
//        	boundsUpdatedScaled.width,
//        	boundsUpdatedScaled.height,
//        	null
//        );

		//PanelCapture size calculation

		// margins or padding around the image
		int margins = 25 * 2;
		int availableWidth = (int) getParent().getWidth() - margins;
		int availableHeight = (int)getParent().getHeight() - margins;

		float scalingRatio = 1f;
	 	float scalingWidthRatio = (float) availableWidth / capture.getUpdatedBounds().width;
		float scalingHeightRatio = (float) availableHeight /capture.getUpdatedBounds().height;

		scalingRatio = scalingWidthRatio < scalingHeightRatio ? scalingWidthRatio : scalingHeightRatio;

		// Set size the same as the size of the full original image
		Dimension panelCaptureSize = new Dimension(
				(int) (capture.getUpdatedBounds().width * scalingRatio),
				(int) (capture.getUpdatedBounds().height * scalingRatio)
		);

		setSize(panelCaptureSize);
		setPreferredSize(panelCaptureSize);
		setLocation(
				(getParent().getWidth() - panelCaptureSize.width) / 2,
				(getParent().getHeight() - panelCaptureSize.height) / 2
		);

		screenshot = new Screenshot(capture.getUpdatedImage(),
				boundsUpdatedScaled.x,
				boundsUpdatedScaled.y,
				getWidth(),
				getHeight());

		add(screenshot, new Integer(100));
		add(new Highlighter(), new Integer(101));

		Graphics imgGraphics = capture.getUpdatedImage().createGraphics();

		//addSomeDummyLines(imgGraphics);



		if (
				frameSize.getWidth()  != frame.getSize().getWidth() ||
						frameSize.getHeight() != frame.getSize().getHeight()
		)
		{
			//repositionViewport();
			//updateCollisionBounds();

			frameSize = frame.getSize();
		}
	}


	/**
	 * This takes the width of the viewport and the width of the image
	 * current being displayed and centers the image in the viewport.
	 */
	public void repositionViewport()
	{
		if (parent instanceof JScrollPane)
		{
			Rectangle boundsUpdatedScaled = scale.scaleRectangle(capture.getUpdatedBounds());
			
			JViewport viewport = scrollPane.getViewport();

			int proper_width  = (viewport.getWidth()  / 2) - (boundsUpdatedScaled.width  / 2);
			int proper_height = (viewport.getHeight() / 2) - (boundsUpdatedScaled.height / 2);

			viewportPoint = new Point(
					(500 / 2) + boundsUpdatedScaled.x - proper_width,
					(500 / 2) + boundsUpdatedScaled.y - proper_height
			);

			viewport.setViewPosition(viewportPoint);
		}
	}

	/**
	 * Updates relevant boundaries generated from dimension information that
	 * will be used to calculate collision and image re-sizing.
	 */
    public void updateCollisionBounds()
    {
    	Rectangle boundsUpdatedScaled = scale.scaleRectangle(capture.getUpdatedBounds());

        // Bounds of entire image, plus padding
        imageWhole.setBounds(
        		boundsUpdatedScaled.x - scale.scale(padWidth),
        		boundsUpdatedScaled.y - scale.scale(padWidth),
        		boundsUpdatedScaled.width  + (scale.scale(padWidth) * 2),
        		boundsUpdatedScaled.height + (scale.scale(padWidth) * 2)
        );

        // Image's Northern Collision Bounds
        imageNorth.setBounds(
        		boundsUpdatedScaled.x - (scale.scale(colWidth)  / 2),
        		boundsUpdatedScaled.y - (scale.scale(colHeight) / 2),
        		boundsUpdatedScaled.width + scale.scale(colWidth),
        		scale.scale(colHeight)
        );

        // Image's Southern Collision Bounds
        imageSouth.setBounds(
        		boundsUpdatedScaled.x - (scale.scale(colWidth) / 2),
        		boundsUpdatedScaled.y + boundsUpdatedScaled.height - (scale.scale(colHeight) / 2),
        		boundsUpdatedScaled.width + scale.scale(colWidth),
        		scale.scale(colHeight)
        );

        // Image's Eastern Collision Bounds
        imageEast.setBounds(
        		boundsUpdatedScaled.x + boundsUpdatedScaled.width - (scale.scale(colWidth) / 2),
        		boundsUpdatedScaled.y - (scale.scale(colHeight) / 2),
        		scale.scale(colWidth),
               boundsUpdatedScaled.height + scale.scale(colHeight)
        );

        // Image's Western Collision Bounds
        imageWest.setBounds(
        		boundsUpdatedScaled.x - (scale.scale(colWidth)  / 2),
        		boundsUpdatedScaled.y - (scale.scale(colHeight) / 2),
        		scale.scale(colWidth),
                boundsUpdatedScaled.height + scale.scale(colHeight)
        );
    }

    /**
     * Quickly locate the area in which the point is contained by.
     *
     * @param p The point whose area is to be located.
     * @return  The area in which the point is located.
     */
    public Capture.AREA locateArea(Point p)
    {
        if      (imageNorth.contains(p) && imageEast .contains(p)) return Capture.AREA.NORTHEAST;
        else if (imageEast .contains(p) && imageSouth.contains(p)) return Capture.AREA.SOUTHEAST;
        else if (imageSouth.contains(p) && imageWest .contains(p)) return Capture.AREA.SOUTHWEST;
        else if (imageWest .contains(p) && imageNorth.contains(p)) return Capture.AREA.NORTHWEST;

        else if (imageNorth.contains(p)) return Capture.AREA.NORTH;
        else if (imageEast .contains(p)) return Capture.AREA.EAST;
        else if (imageSouth.contains(p)) return Capture.AREA.SOUTH;
        else if (imageWest .contains(p)) return Capture.AREA.WEST;

        else if (imageWhole.contains(p)) return Capture.AREA.WHOLE;

        else    return null;
    }

    public void setSaved(boolean saved)
    {
    	this.saved = saved;
    }

    public boolean isSaved()
    {
    	return saved;
    }

	public void addArrowCommentPanel(){

		ArrowCommentPanel arrowCommentPanel = new ArrowCommentPanel(getWidth(), getHeight());
		arrowCommentPanel.addLayerPaintListener(screenshot);
		//TODO : Integer should not be static - work on it
		add(arrowCommentPanel, new Integer(105));

	}



}

class Screenshot extends JPanel implements LayerPaintListener {

	private Stack<BufferedImage> previousImageStack;
	private BufferedImage image;

	public Screenshot(BufferedImage bufferedImage, int x, int y, int width, int height){
		setBounds(x, y, width, height);
		image = bufferedImage;
		previousImageStack = new Stack<BufferedImage>();
		previousImageStack.add(image);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2.setRenderingHints(rh);
		g2.drawImage(image,0,0, getWidth(), getHeight(), this );
		g2.dispose();
	}

	@Override
	public void layerPainted(List<Object> paintList) {
		Graphics g = image.createGraphics();

		for(Object obj : paintList){
			if(obj instanceof LShapeLine){
				((LShapeLine)obj).draw(g);
			}
		}
	}


}

class Highlighter extends  JPanel {

	public Highlighter() {
		setOpaque(false);
		setBounds(0,0, 500, 500);
		setBorder(BorderFactory.createLineBorder(Color.white, 5));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g.drawString("Yo man Transparent panel and draw", 100, 100);
		g.setColor(new Color(75, 248, 7, 100));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(20));
		g.drawLine(100, 150, 300, 150);
		g2.dispose();
	}
}