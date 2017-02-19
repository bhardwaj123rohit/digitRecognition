/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digitrecogniton;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;



/**
 *
 * @author ANKITA
 */
public class NeuralNetwork {

    int neuronInput;
    int neuronOutput ,neuronHidden;

    Matrix[] theta;

    
    NeuralNetworkLayer n[];

    public NeuralNetwork(int i,int h,int o) {

        neuronInput=i;
        neuronHidden=h;
        neuronOutput=o;

        n=new NeuralNetworkLayer[3];


        n[0]= new NeuralNetworkLayer(i+1, "i");
        n[1]= new NeuralNetworkLayer(h+1, "h");
        n[2]= new NeuralNetworkLayer(o, "o");

        theta =new Matrix[2];

        theta[0]=new Matrix(h, i+1);
        theta[0]=InitializeWeights(theta[0]);
        theta[1]=new Matrix(o,h+1);
        //InitializeWeights2();
        theta[1]=InitializeWeights(theta[1]);
  //System.out.print(theta[0]);
  //System.out.print(theta[1]);



    }


// EnterInput function is used to enter an input to the neural network
void EnterInput(double  i[])
    {
    
for(int k=0;k<n[0].no_neurons;k++)
                    {
                       // System.out.print(k+" ");
                    if(k==0)
                        n[0].n[k].value=1.0;
                    else
                        n[0].n[k].value=i[k-1];
                    
                    
                }

//System.out.println("Input to Neural Network completed...");


    }

    

 int  getOutput()
    {
 /*       
    try
    {
    Matrix hidden=Matrix.Multiply(n[0].matrixForm(),theta[0].Transpose());
    
    n[1].EqualMatrix(hidden);
    n[1].activate();
    
    hidden=n[1].matrixForm();
    //System.out.println("Activated Hidden layer: "+hidden);
    Matrix output=Matrix.Multiply(hidden, theta[1].Transpose());
   
    n[2].EqualMatrix(output);
  //  System.out.println("Output Layer: "+n[2].n);
    n[2].activate();
   // System.out.println(n[2]);
   // System.out.println(n[2]);
    
    //System.out.println("Output " + predictOutput());
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
*/

    try
    {
        // Size of hidden [ 25 1]
    Matrix hidden=Matrix.Multiply(theta[0],n[0].matrixForm().Transpose());
    
    n[1].EqualMatrix(hidden.Transpose());
    
    n[1].activate();
    
    hidden=n[1].matrixForm();
    //System.out.println("Activated Hidden layer: "+hidden);
    Matrix output=Matrix.Multiply(theta[1],hidden.Transpose());
   
    n[2].EqualMatrix(output.Transpose());
  //  System.out.println("Output Layer: "+n[2].n);
    n[2].activate();
   // System.out.println(n[2]);
   // System.out.println(n[2]);
    
    //System.out.println("Output " + predictOutput());
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

  return predictOutput();
  }

int  predictOutput()
    {
int k=0,h=0;
double max=n[2].n[0].value;
double min=n[2].n[0].value;

    for(int i=0;i<neuronOutput;i++)
    {
       // System.out.print(n[2].n[i].value+" ");
        if(n[2].n[i].value>max)
            {
          max=n[2].n[i].value;
          k=i;
          
        }
         if(n[2].n[i].value<min)
         {
             min=n[2].n[i].value;
              h=(i+1)%neuronOutput;

         }
        
    }
    
// System.out.println(h);
return k;
}
int  predictOutput2()
    {
int k=0,h=0;
double max=n[2].n[0].value;
double min=n[2].n[0].value;

    for(int i=0;i<neuronOutput;i++)
    {
       // System.out.print(n[2].n[i].value+" ");
        if(n[2].n[i].value>max)
            {
          max=n[2].n[i].value;
          k=(i+1)%neuronOutput;
          
        }
         if(n[2].n[i].value<min)
         {
             min=n[2].n[i].value;
              h=(i+1)%neuronOutput;

         }
        
    }
    
// System.out.println(h);
return k;
}

  void InitializeWeights2(){
      Matrix m3=new Matrix(25, 401);
 Matrix m2=new Matrix(10, 26);

        try{

Path p = Paths.get(System.getProperty("user.home"),"NetBeansProjects","digitRecogniton","src","Weights","InitialWeights1.txt");
Path p2 = Paths.get(System.getProperty("user.home"),"NetBeansProjects","digitRecogniton","src","Weights","InitialWeights12.txt");
        if(!Files.exists(p)||!Files.exists(p2))
        {
           System.out.println("File doesnot exists") ;

        }
        String content2 = new String(Files.readAllBytes(p2));
        String content = new String(Files.readAllBytes(p));
 StringTokenizer t=new StringTokenizer(content, ",");
 StringTokenizer t2=new StringTokenizer(content2, ",");
 int i=0;

//System.out.println(t.countTokens());
 while(t.hasMoreTokens())
 {


 m3.a[i/401][i%401]=Double.parseDouble(t.nextToken().toString());

 i++;


 }
i=0;
//System.out.println(t2.countTokens());
while(t2.hasMoreTokens())
 {


 m2.a[i/26][i%26]=Double.parseDouble(t2.nextToken().toString());

 i++;


 }

       //  System.out.println(m);
         //        System.out.println(content);
        } catch (Exception ex) {
        //System.out.println(ex.);
        ex.printStackTrace();
        }

  theta[0]=m3;
  theta[1]=m2;

  }
static Matrix InitializeWeights(Matrix m)
{
    /*    double min = - 1/Math.sqrt(m.columnSize);
        double max =  - min;
        
        for(int i=0;i<m.rowSize;i++)
            for(int j=0;j<m.columnSize;j++)
            {
                m.a[i][j]= (Math.random()*2*(max - min) - (max - min));
            }
        System.out.print("Weights Initialized");
        return m;
  }
      */
 
      double epsilon = Math.sqrt(6)/Math.sqrt(m.columnSize+m.rowSize);
      
       for(int i=0;i<m.rowSize;i++)
            for(int j=0;j<m.columnSize;j++)
            {
                m.a[i][j]= (Math.random()*2*epsilon - epsilon);
            }
   
        
      return m;

      

}
}
         



