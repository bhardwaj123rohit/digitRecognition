/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digitrecogniton;

/**
 *
 * @author ANKITA
 */
public class neuron {

   double value;
   double siGrad;

  //  double activation;

   void tanh()
    {
   value = (Math.pow(Math.E, 2*value)-1)/(Math.pow(Math.E, 2*value)+1);

    }

    void activate()
    {
    value=1/(1+Math.pow(Math.E, -value));

    }
 neuron()
    {
        
    }
    neuron(double i){
        value =i;
    }
    void SigmoidGradient()
    {
        siGrad= value * (1-value);
        
    }

}
