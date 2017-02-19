/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitrecogniton;



import java.io.IOException;
import static java.lang.System.exit;
import java.nio.file.Paths;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

        /**
 *
 * @author rohit
 */
public class Reading_Images {

    static int noImage;
    static int output[];
    static int noRows;
    static int noColumns;
    static int bufSize;
    static int labBufSize;
    static Matrix input;
    static int noIteration;
    static int noImageBuff;
    static double learningRate;
    static int predection;
    static int countMatch;
    static int sizeInput;
    
   

    public static NeuralNetwork read() throws Exception {
       
        
         // Fetch System time
          long start = System.nanoTime();
              
        // Fetch and Display Training and Test Data.
        Path pTrainLabel = Paths.get(System.getProperty("user.home"),"Desktop","Ankita","Neural Networks","Data","train-labels.idx1-ubyte");
        Path pTrain = Paths.get(System.getProperty("user.home"),"Desktop","Ankita","Neural Networks","Data","train-images.idx3-ubyte");
        Path pTest  = Paths.get(System.getProperty("user.home"),"Desktop","Ankita","Neural Networks","Data","t10k-images.idx3-ubyte");
        Path pTestLabel  = Paths.get(System.getProperty("user.home"),"Desktop","Ankita","Neural Networks","Data","t10k-labels.idx1-ubyte");
        if(Files.exists(pTrain)&& Files.exists(pTrainLabel)&& Files.exists(pTest)&& Files.exists(pTestLabel))
        {
            System.out.println("Path of Training Images is :" +pTrain+"/n");
            System.out.println("Path of Training Images Labels is :" +pTrainLabel+"/n");
            System.out.println("Path of Testing Images is :" +pTest+"/n");
            System.out.println("Path of Testing Images Labels is :" +pTestLabel+"/n");
        }
        else
        {
            System.out.println("Path is not correct.");
            exit(1);
        }
        
        System.out.println("\nDecoding the Top Few Bytes...\n ");
        
        // Reading the binary file using Low Level IO
        try
        {
            SeekableByteChannel testChannel = Files.newByteChannel(pTest);
            SeekableByteChannel testLabelChannel = Files.newByteChannel(pTestLabel);
            SeekableByteChannel trainChannel = Files.newByteChannel(pTrain);
            SeekableByteChannel trainLabelChannel = Files.newByteChannel(pTrainLabel);

            bufSize= (28*28*60000+16);
            labBufSize=(60000+8);
             //Buffer to binary images         
             ByteBuffer buf= ByteBuffer.allocate(bufSize);
             ByteBuffer labBuf= ByteBuffer.allocate(bufSize);
             
             // Display the order of Bytes in buffer LowEndian or BigEndian
             ByteOrder byteOrder= buf.order();
             System.out.println("The order is :"+byteOrder);
            
            // Reading data from trainChannel and putting it in buffer.
             trainChannel.read(buf);
             trainLabelChannel.read(labBuf);
            
             // Reseting in buffer pointer to the begning of buffer
             buf.rewind();
             labBuf.rewind();
             
             //buf.flip();
             // Fetching the bytes of magic number.
             System.out.println("First Two Bytes of Magic Number are always 0 ");
             labBuf.getInt();
             output = new int[labBuf.getInt()];
             System.out.println("First Byte: "+buf.get());
             System.out.println("Second Byte: "+buf.get());
             System.out.println("Third Byte:(Tells the type of Data): "+buf.get()+ " (8 means unsined byte)");
             System.out.println("Forth Byte:(Tells the No. of Dimesnsion of vector/ matrix) "+buf.get());
             
             // Fetching the Integer that store the number of images
              noImage=  buf.getInt();
              
             //Fetching number of rows
              noRows=buf.getInt();
              
             //Fetching number of columns
              noColumns=buf.getInt();
              
              System.out.println("\nNo. of Imgages: "+noImage+"\nNo. of Rows: "+noRows+"\nNo. of Columns: "+noColumns);
                          
              int sizeInput=10000;// noImageBuff+ 24*((bufSize)/(28*28));
              
              // Input to store fetched Images.
              input = new Matrix(sizeInput,noRows*noColumns);
              
              
              
            // Output from the labels
              output = new int[sizeInput];
             int im=0;
            for( im=0;im<sizeInput;im++)
             {  
                for(int Img=0;Img<(noRows*noColumns);Img++)
                  {  int k=( buf.get() & 0xFF );
                     if(k>0)
                  input.a[im][Img] =1;
                  }
               // input.a[im]=((input.a[im]));
                output[im]=( labBuf.get() & 0xFF );
                 
                               
              }


            
           /* 
            for(int j=(labBufSize- 8-noImageBuff);j<sizeInput ;j++)
            {
                 output[im+j]=( labBuf.get() & 0xFF );
                 sizeInput++;
            }
            */
            System.out.println("Bytes in buffer."+buf.remaining());
            /*noImageBuff = (bufSize)/(28*28);          
            for(int i=0;i<24;i++)
            {
                trainChannel.read(buf);
                 buf.rewind();
              
            for(int k=0;k<noImageBuff;k++)
             {  
                for(int Img=0;Img<(noRows*noColumns);Img++)
                  { 
                  input.a[i*noImageBuff+im+k][Img] = ( buf.get() & 0xFF );
                  }
                   input.a[i*noImageBuff+im+k]=(input.a[im]);
                   output[im+k]=( labBuf.get() & 0xFF );
             }
            }
            */
            
            //Testing Images Read...
          /*
             for(int p=0;p<5;p++)
            {
            for(int i=0;i<28;i++)
            {
                for(int j=0;j<28;j++)
                {
                    System.out.print((input.a[p][28*i+j])>0?1:0);
                
                }
                System.out.println();
            }
            System.out.println("Output"+output[p]);
            }
            
            System.out.println("Calculated Input: "+sizeInput+" Real Input: "+input.a.length+" Output Count "+output.length);
           */
           noIteration = 70;
           Backpropogation.BackInit(input, output);
            learningRate=0.11;
          //  System.out.print("Initial Weights");
          // System.out.println("Thet  a 1 \n"+Backpropogation.di_rec.theta[0]);
           // System.out.println("Theta 2 \n"+Backpropogation.di_rec.theta[1]);
              
            for(int i=0;i<noIteration;i++)
            {
                 Backpropogation.Backpropogate(input,output);
                 
                 System.out.println("Interation: "+ (i+1)+" Cost: "+Backpropogation.Cost());
                 Matrix temp = Matrix.subtraction(Backpropogation.di_rec.theta[0],Matrix.scalarMultiply(learningRate,Backpropogation.theta1Grad));
                 
                 Matrix temp2 = Matrix.subtraction(Backpropogation.di_rec.theta[1],Matrix.scalarMultiply(learningRate,Backpropogation.theta2Grad));
                  Backpropogation.di_rec.theta[0]=temp;
                  Backpropogation.di_rec.theta[1]=temp2;
              //  System.out.print("Updating Weights.");
            }   
            
              System.out.println("\n Backpropogation is over. \n");
            /*  System.out.println("Testing on Training DataSet");
               countMatch =0;
              for(int i=0;i<100;i++)
              {
               Backpropogation.di_rec.EnterInput(input.a[i]);
               predection= Backpropogation.di_rec.getOutput();
               System.out.println("Predected: "+predection+" Output: "+output[i]);
               //System.out.println("Activation Values...");
                 //  System.out.print(Backpropogation.di_rec.n[2].matrixForm());
              
               if(output[i]==predection)
                   countMatch++;
               
              }
            //System.out.println("Theta 1 \n"+Backpropogation.di_rec.theta[0]);
            //System.out.println("Theta 2 \n"+Backpropogation.di_rec.theta[1]);
              System.out.println("Accuracy: "+(countMatch));
            */
              long mid = System.nanoTime();
              System.out.println("Total Time "+(float)(mid-start)/1000000000);



        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return Backpropogation.di_rec;
    }
    
}
