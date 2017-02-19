/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitrecogniton;

/**
 *
 * @author rohit
 */
import java.awt.Canvas;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DisplayImages {
    
   

    static void createAndShowGUI() {
      
        JFrame f = new JFrame("Display Images");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setSize(1000, 1000);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
    } 
}

class MyPanel extends JPanel {

      int noImage;
    int noRows;
    int noColumns;
    int bufSize;
    int i,j;
    int dataX[],dataY[];
     int[][] image;
    //static Matrix ImgMat;
    double input[];
    int predicted[] ; // Values Predicted by our Neural Network
    //static NeuralNetwork di_rec;// Neural Network
    MyCanvas c[];
   
    public MyPanel() {
          
        setBorder(BorderFactory.createLineBorder(Color.black));
        
        Path pTrain = Paths.get(System.getProperty("user.home"),"Desktop","Ankita","Neural Networks","Data","train-images.idx3-ubyte");
      
                
        System.out.println("\n Displaying random images. \n ");
        
        // Reading the binary file using Low Level IO
        try
        {
            SeekableByteChannel trainChannel = Files.newByteChannel(pTrain);
            bufSize= 256*1024;
             //Buffer to binary images         
             ByteBuffer buf= ByteBuffer.allocate(bufSize);
             
             // Display the order of Bytes in buffer LowEndian or BigEndian
             ByteOrder byteOrder= buf.order();
           //  System.out.println("The order is :"+byteOrder);
            
            // Reading data from trainChannel and putting it in buffer.
             trainChannel.read(buf);
            
             // Reseting in buffer pointer to the begning of buffer
             buf.rewind();
             
             //buf.flip();
             // Fetching the bytes of magic number.
             // System.out.println("First Two Bytes of Magic Number are always 0 ");
             
             System.out.println("First Byte: "+buf.get());
             System.out.println("Second Byte: "+buf.get());
             System.out.println("Third Byte:(Tells the type of Data): "+buf.get()+ " (8 means unsined byte)");
             System.out.println("Forth Byte:(Tells the No. of Dimesnsion of vector/ matrix) "+buf.get());
             
             // Fetching the Integer that store the number of images
              noImage=  buf.getInt();
              
              // Matrix to store Images.
//              ImgMat = new  Matrix( noRows,noColumns );
              
              //Fetching number of rows
              noRows=buf.getInt();
              
              //Fetching number of columns
              noColumns=buf.getInt();
              
             // System.out.println("\nNo. of Imgages: "+noImage+"\nNo. of Rows: "+noRows+"\nNo. of Columns: "+noColumns);
             
              
              // Getting the 1st Image
                       
              input = new double[noRows*noColumns];
             dataX = new int[noRows*noColumns];
             dataY = new int[noRows*noColumns];
              int noImageBuff=(bufSize-16)/(28*28);
              c=new MyCanvas[noImageBuff];
              long start = System.nanoTime();
              predicted = new int[noImageBuff];
            
            for(int im=0;im<noImageBuff;im++)
             {  
                 for(int Img=0;Img<(noRows*noColumns);Img++)
                  { 
                  input[Img] = ( buf.get() & 0xFF );
                  }
              // To Display the images...           
            //  System.out.println(im+1+" Image");
              int k=0;
              for( i=0;i<noRows;i++) {
                      for(j=0;j<noColumns;j++) {
                          if((int)input[28*i + j]>0)
                          {
                              dataX[k]=j;
                              dataY[k]=i;
                              k++;
                            //  System.out.print(1+" ");
                             // System.out.print(p[k]+" ");
                              // System.out.print(j+" --");
                          }
                         // System.out.print(28*i+j+" ");
                         // else
                           //   System.out.print(0+" ");
                              
                      }
                     // System.out.println();
              }
                c[im]= new MyCanvas(dataX,dataY);

                c[im].setBackground (Color.WHITE);

                add(c[im]);
                
              //  c[im].setVisible(true);

               // repaint();

                     
              
             }    
              
              System.out.println("Image Display done \n");
             // long mid = System.nanoTime();
              //System.out.println("Total Time to read all Images"+(float)(mid-start)/1000000000);
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
   
    
        
    }
   
      @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,1000);
    }
    
      @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
       
    }  
}

class MyCanvas extends Canvas {
      int p[];
      int q[];
   

      public MyCanvas (int r[],int s[]) {
         setBackground (Color.GRAY);
         setSize(38, 38);
         p= new int[r.length];
         q= new int[s.length];
         for(int i=0;i<784;i++)
         {
             p[i]=r[i];
             q[i]=s[i];
         }
       
      }

      @Override
      public void paint (Graphics g) {
         Graphics2D g2;
         g2 = (Graphics2D) g;
        
         if(p!= null)
         for(int i=0;i<784;i++)
         {
              g2.drawOval(p[i],q[i], 1, 1);
            // g2.fillOval(p[i],q[i], 1, 1);
         }
         else
             System.out.print("It is null");
      }
      @Override
      public Dimension getPreferredSize(){
              return new Dimension(38,38);
      }
   }
