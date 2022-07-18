package com.examples;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.Font;

public class Photoshop {
    private JFrame frame;

    public Photoshop() {
        frame = new JFrame("Photoshop Filter");
        frame.setSize(620, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new PhotoDraw(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        new Photoshop();
    }

    public static class PhotoDraw extends JPanel  implements MouseListener {

        
        int[][] red;
        int[][] green ;
        int[][] blue ;
        int width = 620;
        int height = 400;
        int effect = 0;
        BufferedImage img;
        BufferedImage img2;

        public PhotoDraw(Dimension dimension) {//constructor

            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);

            try {
                img = ImageIO.read(this.getClass().getResource("img.jpg"));
                img2 = ImageIO.read(this.getClass().getResource("img.jpg"));
            } catch (IOException e) {
                System.out.println("Image could not be read");
                System.exit(1);
            }

            int width  = img.getWidth();
            int height  = img.getHeight();
            setArrays(width, height);

        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            Dimension d = getSize();
            g2.drawImage(img,0,0,300,150 ,null);
            g2.drawImage(img2,310,0,300,150 ,null);
            g.drawRect(0, height-width/3, width/3, width/3);
            g.drawRect(width/3, height-width/3, width/3, width/3);
            g.drawRect(2*width/3, height-width/3, width/3, width/3);
            g2.setFont (new Font("TimesRoman", Font.PLAIN, 20));
            g2.drawString("Effect One",  70, height - width/6);
            g2.drawString("Inverse", 270, height - width/6);
            g2.drawString("Effect Three", 480, height - width/6);

        }

        public void setArrays(int width, int height){

            red = new int[width][height];
            green = new int[width][height];
            blue = new int[width][height];

            for(int x=0; x< width; x++){
                for (int y=0;y< height;y++){

                    Color mycolor = new Color(img.getRGB(x, y));

                    int r = mycolor.getRed();
                    int g = mycolor.getGreen();
                    int b = mycolor.getBlue();
                    /* */
                    red[x][y] = r;
                    green[x][y] = g;
                    blue[x][y] = b;

                }} 
        }

        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();
            if(y >= height - width/2){
                effect = x/(width/2);
            }

            imageChange(effect);

            repaint();
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

       

        public void imageChange(int effect)
        {

           

            for(int x=0; x< red.length-1; x++){
                for (int y=0;y < red[0].length-1;y++){

                   
                    double lum =(0.2126*red[x][y]) + (0.7152*green[x][y]) + (0.0722 * blue[x][y]);
                    
                    if(effect == 0){
                        if(lum <= 255/2){
                            red[x][y] = 0;
                            green[x][y] = 0;
                            blue[x][y] = 0;
                        }
                        else{
                            red[x][y] = 255;
                            green[x][y] = 255;
                            blue[x][y] = 255;
                        }
                    }
                    if(effect == 1){
                        red[x][y] = 255 - red[x][y];
                        green[x][y] = 255 - red[x][y];
                        blue[x][y] = 255 - red[x][y];
                    }
                    if(effect == 2){
                        red[x][y] = green[x][y]/2;
                        green[x][y] = blue[x][y]/2;
                        blue[x][y] = blue[x][y]/2;
                        
                    }

                }}

         
            BufferedImage img3 = new BufferedImage(red.length, red[0].length, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x< red.length; x++){
                for (int y = 0; y< red[0].length; y++){

                    int r = red[x][y];
                    int g = green[x][y];
                    int b = blue[x][y];
                    int col = (r << 16) | (g << 8) | b;
             
                    img3.setRGB(x, y, col);

                }}
            String fullName = "inverted";

            File f = new File(fullName + ".jpg");

            try{
                ImageIO.write(img3, "JPEG", f);
                img2 = img3;
                repaint();
                //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
                // iImgName = "inverted.jpg";
                //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
            } catch(Exception e){

            }

        }
    }
}