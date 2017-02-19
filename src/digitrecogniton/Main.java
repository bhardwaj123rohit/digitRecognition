/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package digitrecogniton;

/**
 *
 * @author ANKITA
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
               // TODO code application logic here
        
        DisplayImages.createAndShowGUI();
            
 javax.swing.SwingUtilities.invokeLater (
         new Runnable() {
     @Override
 public void run() 
     {
         new Interface();
     }
 }
 );


      
    }

}
