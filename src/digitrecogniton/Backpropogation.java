/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digitrecogniton;

/**
 *
 * @author ANKITA
 */

public class Backpropogation {
     static NeuralNetwork di_rec;
     static int predicted[] ; // Values Predicted by our Neural Network
     static double cost;
     static Matrix exOut;
     static Matrix preOut;
     static Matrix actiOut;
     static Matrix actiHidden;
     static double lambda;
     static double regSum; // Sum of regularised weights
     static double noTrain;
     static Matrix erroOut;
     static Matrix errHidden;
     static Matrix theta1Grad;
     static Matrix theta2Grad;
     
     static void BackInit(Matrix input,int output[]) throws Exception
    {
        
        di_rec=new NeuralNetwork(784, 30, 10);
        
        // Predicted Values are stored in this
        predicted = new int[input.a.length];
        
        // Activation of Hidden Layer [5000 26]
        actiHidden = new Matrix(output.length,di_rec.n[1].no_neurons);
        
        // Activation of Output Layer [5000 10]
        actiOut = new Matrix(output.length, di_rec.n[2].no_neurons);
        
        // Output Matrix [5000 10] 5000 Rows 10 Columns
        exOut = ExpandOutput(output);
        
        // Assigning value to Lambda
        lambda =0;
        
        //
        noTrain = output.length;
    }
       static void Backpropogate(Matrix input,int output[]) throws Exception
    { 
        System.out.println("Size of Input: "+input.a[0].length);
        for(int i=0;i<noTrain;i++)
         {
               di_rec.EnterInput(Normalize(input.a[i]));
               
               
               
             predicted[i]= di_rec.getOutput(); 
             //System.out.println("Predicted No: "+predicted[i]+" Correct No: "+output[i]);
               
             // Fetch Indivisual Activations from Hidden Layer
               for(int k=0;k<di_rec.n[1].no_neurons;k++)
                    actiHidden.a[i][k] = di_rec.n[1].n[k].value; 
        
             // Fetch Indivisual Activations from Output Layer
               for(int k=0;k<di_rec.n[2].no_neurons;k++)
                    actiOut.a[i][k] = di_rec.n[2].n[k].value; 
                         
         }
        preOut =ExpandOutput(predicted);
        
        // Error in Output Layer
        //D3 = a3' - Y ;
        erroOut = Matrix.subtraction(actiOut, exOut);
        
        //Error in Hidden Layer
        //D2 = (Theta2'*D3').*(a2'.*(1-a2'))';
        errHidden = Matrix.dotMultiply(Matrix.Multiply(di_rec.theta[1].Transpose(), erroOut.Transpose()) , (Matrix.dotMultiply(actiHidden,Matrix.subtraction(Matrix.makeOne(actiHidden),actiHidden))).Transpose());
        
        // Changes that are made in Theta[2](elment wise);
        //% a2*D3  a2=[26 5000] D3=[5000 10]
        //Theta2_grad = (1/m)*((( a2*D3 )')  +lambda*[zeros(size(Theta2,1),1) Theta2(:,2:end)]);
        //theta2Grad= Matrix.addition(Matrix.scalarMultiply( (1/noTrain),(Matrix.Multiply(actiHidden.Transpose(),erroOut)).Transpose()),Matrix.scalarMultiply(lambda, Matrix.makeFirstColZero(di_rec.theta[1])));
         theta2Grad=Matrix.scalarMultiply( (1/noTrain),(Matrix.Multiply(actiHidden.Transpose(),erroOut)).Transpose());
        
        // Changes that are made in Theta[1](elment wise);
                                                         //Theta1_grad = (1/m)*(((X'*D2')')(2:end,:) + lambda*[zeros(size(Theta1,1),1) Theta1(:,2:end)]);
      
        //theta1Grad=Matrix.addition(Matrix.scalarMultiply(1/noTrain,Matrix.removeFirstRow((Matrix.Multiply(Matrix.addZeroCol(input).Transpose(),errHidden.Transpose())).Transpose())),Matrix.scalarMultiply(lambda,Matrix.makeFirstColZero(di_rec.theta[0])));
        theta1Grad=Matrix.scalarMultiply(1/noTrain,Matrix.removeFirstRow((Matrix.Multiply(Matrix.addZeroCol(input).Transpose(),errHidden.Transpose())).Transpose()));
        
        
    }
     // Create a matrix of [5000 10]
     static Matrix ExpandOutput(int output[]){
         Matrix out = new Matrix(output.length,di_rec.n[2].no_neurons);

         for(int i=0;i<output.length;i++)
         {
             out.a[i][output[i]]=1;
         }
         
         return out;
     }
     static Matrix ExpandOutput2(int output[]){
         Matrix out = new Matrix(output.length,di_rec.n[2].no_neurons);

         for(int i=0;i<output.length;i++)
         {
             out.a[i][output[i]-1]=1;
         }
         
         return out;
     }
        
    static double[] Normalize(double inp[]) {
        double sum=0,std=0,diff=0;
    
        // Calculating the Mean
        for(int i=0;i<inp.length;i++)
        {
            sum+=inp[i];
        }
        sum/=inp.length;
              
        // Calculating the Standard Deviation
      
        for(int j=0;j<inp.length;j++){
       
            diff = inp[j]-sum;
            std = std +Math.pow(diff, 2);
            inp[j]=diff;
          
        }
        std =std/inp.length;
        
        std = Math.sqrt(std);
        for(int k=0;k<inp.length;k++){
            inp[k]=inp[k]/std;
        }
        
        return inp;

       }
    static double Cost() throws Exception
    {
        
       // Calculate the error correponding wrongly calculated ones. 
        //  h1 = -log(a3).*Y
        
        Matrix h1 = Matrix.scalarMultiply(-1,Matrix.dotMultiply(Matrix.loggify(actiOut),exOut));
        
        // Matrix of ones
        Matrix onesActi = Matrix.makeOne(actiOut);
        Matrix onesOut = Matrix.makeOne(preOut);
        
        // Calculate the error corresponding wrongly calculated zeros 
       // h2 =  -(myone -Y ).*log ( myone - a3' )
        
        Matrix h2 = Matrix.scalarMultiply(-1,(Matrix.dotMultiply(Matrix.subtraction(onesOut, exOut),Matrix.loggify(Matrix.subtraction(onesOut, actiOut)))));
        
        Matrix totalCost = Matrix.addition(h1, h2);
        
        // Calculating regularised cost corresponding theta 1
        regSum=0;
        for(int i=0;i<di_rec.theta[0].a.length;i++)
            for(int j=0;j<di_rec.theta[0].a[i].length;j++)
            {
                if(j!=0)
                {
                    regSum += Math.pow(di_rec.theta[0].a[i][j],2);
                }
            }
                
        // Calculating regularised cost corresponding theta 2
        for(int i=0;i<di_rec.theta[1].a.length;i++)
            for(int j=0;j<di_rec.theta[1].a[i].length;j++)
            {
                if(j!=0)
                {
                    regSum += Math.pow(di_rec.theta[1].a[i][j],2);
                }
            }
        
        // Returing total cost + regularised cost 
        return ( Matrix.sum(totalCost) + (lambda/(2*(noTrain)))*regSum) ;
                
        //return h1+h2;
    }
       
        
   
}
