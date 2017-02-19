/*
 *This is the main interface 
 */

package digitrecogniton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author ANKITA
 */
public class Interface extends JFrame {

   DrawDigit d;
   JButton predictB,trainB,clearB;
   JLabel predicttext;
   JTextField predictdisplay;
      NeuralNetwork di_rec;

    public Interface() {


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
      //  setMaximumSize(null);
        //setMaximumSize(null);


        setSize(500, 500);
        d= new DrawDigit(200, 200);

       d.setBounds(0,0,300,300);
       d.setVisible(true);
       add(d);

       predicttext=new  JLabel("The predicted output :  ");
       predicttext.setBounds(0, 350, 150, 50);
       add(predicttext);

     predictdisplay= new JTextField();
     predictdisplay.setBounds(155, 350, 50, 50);
     predictdisplay.setFocusable(false);
     add(predictdisplay);


       predictB=new JButton("Predict");
       predictB.setSize(100, 50);
       predictB.setFont(new Font(Font.SANS_SERIF, 20, 10));
       predictB.setBounds(200, 400, 100, 50);

       predictB.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            d.convertCanvas2();
                          
         di_rec.EnterInput(Backpropogation.Normalize(d.input));
            
          int h= di_rec.getOutput();
System.out.println("Output = "+h);
    String l= new String();
    l=" "+ h;
          predictdisplay.setText(l);
            }
        });
        add(predictB);
          trainB=new JButton("TRAIN");
       trainB.setSize(100, 50);
       trainB.setFont(new Font(Font.SANS_SERIF, 20, 10));
       trainB.setBounds(50, 400, 100, 50);

       trainB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    //System.out.println("Button Clicked!");
                       di_rec=Reading_Images.read();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
       });
              
       add(trainB);

        clearB=new JButton("CLEAR");
       clearB.setSize(100, 50);
       clearB.setFont(new Font(Font.SANS_SERIF, 20, 10));
       clearB.setBounds(300, 400, 100, 50);

       clearB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                predictdisplay.setText("");
                d.refresh();
            }
       });

       add(clearB);


       setVisible(true);

/*
   di_rec=new NeuralNetwork(400, 25, 10);
      Matrix m=new Matrix(25, 401);
     Matrix m2=new Matrix(10, 26);


        try{

Path p = Paths.get(System.getProperty("user.home"),"NetBeansProjects","digitRecogniton","src","Weights","Weight.txt");
Path p2 = Paths.get(System.getProperty("user.home"),"NetBeansProjects","digitRecogniton","src","Weights","Weight2.txt");
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


 m.a[i/401][i%401]=Double.parseDouble(t.nextToken().toString());

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

  di_rec.theta[0]=m;
  di_rec.theta[1]=m2;
//  System.out.println(di_rec.theta[0]);


*/
    }





}
