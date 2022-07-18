package com.elsealabs.xshot.views;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.ClipboardCapture;
import com.elsealabs.xshot.file.FileUtil;
import com.elsealabs.xshot.math.Scale;

/**
 * PictureView.java
 *
 */
public class ViewPicture extends JFrame
{

	private JFrame instance;

	private String title;
	private int barHeight;

	private JPanel bar;
	private JPanel container;
	private PanelCapture panelCapture;

//	private JScrollPane scrollPane;

	private WindowListener windowListener;
	private JButton buttonSave;
	private JButton buttonNew;
	private JButton buttonCopy;
	private JButton buttonComment;

	private ActionListener actionSave;
	private ActionListener actionNew;
	private ActionListener actionZoom;
	private ActionListener actionCopy;
	private ActionListener actionQuit;
	private ActionListener actionComment;

	private ClipboardCapture clipCapture;
	private Capture capture;

	private String[] options;
	
	private Scale scale = Scale.getInstance();
	
	public ViewPicture(Capture capture)
	{
		this.capture = capture;

		instance = this;
		title = "Capture";
		barHeight = 40;

		options = new String[] { "Save", "Back", "Close" };

		addListeners();
	}

	public void build()
	{
		// Set size of window
//		setSize(capture.getUpdatedBounds().width + 200,
//				capture.getUpdatedBounds().height + barHeight + 200);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLookAndFeel();

		getContentPane().setLayout(new BorderLayout());

		addButtonBar();

		// Scroll pane with always-on scroll bars
		//createScrollPane();
		//add(scrollPane, BorderLayout.CENTER);

		// Container for easier manipulation of the scroll pane.
		createContainerPanel();
		//scrollPane.getViewport().add(container);
		add(container, BorderLayout.CENTER);

		// Customized image panel
		createPanelCapture();
		container.add(panelCapture);// Add panelCapture at static position


		//addPanelnDrawLineMouseListener();

		setTitle(title);
		setLocationRelativeTo(null);
		setVisible(true);
		panelCapture.paintx();
	}

	private void addButtonBar(){

		// Top bar of the program. Has feature buttons.
		bar = new JPanel();
		bar.setLayout(new GridLayout(0, 5));
		bar.setPreferredSize(new Dimension(barHeight, barHeight));
		add(bar, BorderLayout.NORTH);

		// Adding elements to top bar

		actionSave = x ->
		{

			ViewSave viewSave = new ViewSave();
			viewSave.supplyCapture(capture);
			viewSave.supplyViewPicture(this);
			viewSave.init();
			viewSave.build();
			viewSave.setVisible(true);

			panelCapture.setSaved(true);
		};

		buttonSave = new JButton("Save");
		buttonSave.addActionListener(actionSave);
		bar.add(buttonSave);

		actionNew = x ->
		{
			this.setVisible(false);
			this.dispose();
			EventQueue.invokeLater(ViewMainModern::new);
		};

		buttonNew = new JButton("New");
		buttonNew.addActionListener(actionNew);
		bar.add(buttonNew);

		actionComment = x ->
		{
			panelCapture.addArrowCommentPanel();
		};

		buttonComment = new JButton("Arrow Comment");
		buttonComment.addActionListener(actionComment);
		bar.add(buttonComment);


//		JButton buttonZoom = new JButton("Zoom");
//		buttonZoom.addActionListener(actionZoom);
//		bar.add(buttonZoom);

//		actionZoom = x ->
//		{
//			scale.setScale(scale.getScale() + .1f);
//
//			Rectangle totalBounds = scale.scaleRectangle(capture.getTotalBounds());
//
//			// Set size and position of container
//
//			Dimension containerSize = new Dimension(
//					totalBounds.width  + 500,
//					totalBounds.height + 500
//			);
//
//			container.setSize(containerSize);
//			container.setPreferredSize(containerSize);
//
//			container.repaint();
//
//			// Set size and position of panel capture
//
//			Dimension panelCaptureSize = new Dimension(
//					totalBounds.width,
//					totalBounds.height
//			);
//
//			panelCapture.setSize(panelCaptureSize);
//			panelCapture.setPreferredSize(panelCaptureSize);
//
//			panelCapture.setLocation(
//					((totalBounds.width + 500)  / 2) - (totalBounds.width  / 2),
//					((totalBounds.height + 500) / 2) - (totalBounds.height / 2)
//			);
//
//			container.repaint();
//			panelCapture.repaint();
//		};

		// Copy image action and button

		actionCopy = x -> {
			clipCapture = new ClipboardCapture(capture);
			clipCapture.moveToClipboard();
		};

		buttonCopy = new JButton("Copy");
		buttonCopy.addActionListener(actionCopy);
		bar.add(buttonCopy);

		// Force quit no save action and button

		actionQuit = x -> {
			this.setVisible(false);
			this.dispose();
			System.exit(0);
		};

		JButton buttonQuit = new JButton("Quit, no save");
		buttonQuit.addActionListener(actionQuit);
		bar.add(buttonQuit);
	}

	private void createScrollPane(){

//		scrollPane = new JScrollPane();
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
//		scrollPane.getViewport().setLayout(null);
	}

	private void createContainerPanel(){
		container = new JPanel();
		container.setLayout(null);
		container.setBackground(Color.white);

//		Dimension containerSize = new Dimension(
//				capture.getTotalBounds().width  + 50,
//				capture.getTotalBounds().height + 50
//		);
//
//		container.setSize(containerSize);
//		container.setPreferredSize(containerSize);
//		container.setLocation(0, 0);
	}

	private void createPanelCapture(){

		panelCapture = new PanelCapture(null, (JFrame) this, capture, container);

	}

	/**
	 * TODO : remove it on production
	 */
	private void addSomeLines(List<LShapeLine> lShapeLineList) {
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

	}

	private void addListeners()
	{
		windowListener = new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent e)
			{
				int n = JOptionPane
						.showOptionDialog(
								instance,
								"Your image is unsaved and will be lost. Are you sure you want to close?",
								"Warning: Capture Not Saved",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE, null, options,
								options[0]);

				if (n == JOptionPane.YES_OPTION)
				{
					actionSave.actionPerformed(null);
				} else if (n == JOptionPane.NO_OPTION)
				{
					panelCapture.setSaved(false);
				} else
				{
					super.windowClosing(e);
					instance.dispose();
				}
			}

		};
		
		addWindowListener(windowListener);
	}

	private void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public ActionListener getActionSave()
	{
		return actionSave;
	}
	
	public ActionListener getActionNew()
	{
		return actionNew;
	}
	
	public WindowListener getWindowListener()
	{
		return windowListener;
	}

}