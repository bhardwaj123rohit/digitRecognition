/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digitrecogniton;

/**
 *
 * @author ANKITA
 */
public class NeuralNetworkLayer {
int no_neurons;//no of neurons
neuron n[];
String Layer;// i=input layer h =hidden layer o=output layer
    public NeuralNetworkLayer(int m,String k) {

        Layer=k;

        no_neurons=m;

       n=new neuron[m];

        for(int i =0;i<m;i++)
            n[i]=new neuron();
     


    }

 void tanh()
    {

        for(int i=0;i<no_neurons;i++)
            n[i].tanh();
    }
    void activate()
    {

        for(int i=0;i<no_neurons;i++)
            n[i].activate();
    }


    void EqualMatrix(Matrix m)throws Exception
    {
    if(m.rowSize!=1&&m.columnSize!=no_neurons)
        throw  new Exception("Error: Can't Equate Matrix(Dimension Mismatch! ");
    if(!Layer.equals("o"))
    for(int i=0;i<no_neurons;i++)
    {
        if(i==0)
        n[i].value = 1.0;
        else
        n[i].value = m.a[0][i-1];

        }
    else
        for(int i=0;i<no_neurons;i++)
    {

        n[i].value = m.a[0][i];

        }



    }

Matrix matrixForm()
    {

        Matrix m=new Matrix(1,no_neurons);

        for(int i=0;i<no_neurons;i++)
            m.a[0][i]=n[i].value;
        return m;
        
    }

    @Override
    public String toString()
    {
    String k=new String();
    k="";

    for(int  i=0;i<no_neurons;i++)
        k+=n[i].value+" ";

    return k;
    }



}
