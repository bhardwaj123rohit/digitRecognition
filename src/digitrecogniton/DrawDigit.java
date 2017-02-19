/*
 * This is the J panel having the differnet canvas
 */

package digitrecogniton;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ANKITA
 */
public class DrawDigit extends JPanel {

    int xsize,ysize;
    int minx,miny;
    int maxx,maxy;
    Canvas canvas,c2;
    double input[];
    int m[][];

    public DrawDigit(int w,int h) {
        xsize=w;// the size of the canvas
        ysize=h;
        minx=w;
        miny=h;
        maxx=0;
        maxy=0;

        input =new double[784];

        m=new int[w][h];//matrix used to represent the canvas

        canvas=new Canvas();
        canvas.setBackground(Color.WHITE);
        c2=new Canvas();
        c2.setBackground(Color.WHITE);
       
        canvas.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
               
               int x= e.getX();
               int y=e.getY();
               /*
                * the followinf if conditions are to
                * find the real image coordinates
               */

              if(x>=0&&y>=0&&x<xsize&&y<ysize)
              {
               if(x<minx)
                   minx=x;
               if(x>maxx)
                   maxx=x;
               if(y<miny)
                   miny=y;
               if(y>maxy)
                   maxy=y;
/*
 * the following is to create
 * the image on the canvas
 */
               Graphics gc = canvas.getGraphics();
               gc.setColor(Color.BLACK);
               gc.fillOval(x, y, 6, 6);

            /*
             *the following is used to fill the matrix m representing
             * the original  image
             */

                 

                for(int i=-4;i<5;i++)
                for(int j=-4;j<5;j++)
                {

                 
                  if(x+i<xsize&&x+i>=0&&y+j<ysize&&y+j>=0)
                  m[x + i][y+j]  = 1;
                      
              }

               
              }
               
            }

            public void mouseMoved(MouseEvent e) {
              
              
                //convertCanvas();
            }
        });

       c2.setBounds(0,0,28,28);
        c2.setVisible(true);

        canvas.setBounds(0,0,xsize,ysize);
        canvas.setVisible(true);

      // c2=new Canvas();
       // c2.setBounds(xsize-28,ysize-28,28,28);
        //c2.setBackground(Color.WHITE);
        //c2.setVisible(true);
        //add(c2);


        add(canvas);
        add(c2);
        setSize(xsize, ysize);
        setVisible(true);

      



    }
// this function is used to convert the input to 20*20 pixel image
   
   void convertCanvas2()
   {

        if(minx>=10)
       minx=minx-10;
        else
            minx=0;
       if(miny>=10)
       miny=miny-10;
        else
            miny=0;
        if(maxx<xsize)
       maxx=maxx+10;
        else
            maxx=0;

       if(maxy<ysize)
       maxy=maxy+10;
        else
            maxy=0;




       int imgwidth=maxx-minx+1;// real image width excluding the whitespace
                          //and including a margin
int imgheight=maxy-miny+1;// real image height excluding the whitespace
                          //and including a margin

if(imgwidth-(imgheight/2)<40||(imgheight/2)-imgwidth<40)
    imgwidth=imgheight;
if(imgheight-(imgwidth/2)<40||(imgwidth/2)-imgheight<40)
    imgheight=imgwidth;



int m2[][]=new int[imgwidth][imgheight];//matrix to represent the clipped image

for(int i=0;i<imgwidth;i++)
    for(int j=0;j<imgheight;j++)
    {           if(minx+i>=0&&minx+i<xsize&&miny+j>=0&&miny+j<ysize)
               m2[i][j] = m[minx + i][miny + j];
    }




       int m3[][]=new int[28][28];
   int thumbwidth = 20;
int thumbheight = 20;
double xscale = (thumbwidth+0.0) / imgwidth;
double yscale = (thumbheight+0.0) / imgheight;
double threshold = 0.5 / (xscale * yscale);
double yend = 0.0;
for (int f = 0; f < thumbheight; f++) // y on output
{
    double ystart = yend;
    yend = (f + 1) / yscale;
    if (yend >= imgheight) yend = imgheight - 0.000001;
    double xend = 0.0;
    for (int g = 0; g < thumbwidth; g++) // x on output
    {
        double xstart = xend;
        xend = (g + 1) / xscale;
        if (xend >= imgwidth) xend = imgwidth - 0.000001;
        double sum = 0.0;
        for (int y = (int)ystart; y <= (int)yend; ++y)
        {
            double yportion = 1.0;
            if (y == (int)ystart) yportion -= ystart - y;
            if (y == (int)yend) yportion -= y+1 - yend;
            for (int x = (int)xstart; x <= (int)xend; ++x)
            {
                double xportion = 1.0;
                if (x == (int)xstart) xportion -= xstart - x;
                if (x == (int)xend) xportion -= x+1 - xend;
                if(y<imgwidth&&x<imgheight)
                sum += m2[y][x] * yportion * xportion;
            }
        }
     m3[f][g] = (sum > threshold) ? 1 : 0;
    }
}


/*

for(int i=1;i<27;i++)
    for(int j=1;j<27;j++)
    {
        if(m3[j+1][i]>0||(m3[j][i]>0&&m3[j+1][i]==0))
        {
        int k= m3[j][i]+m3[j][i-1]+m3[j][i+1]+m3[j-1][i]+m3[j+1][i];
        k=k/5;
        m3[j][i]=k;

        }


    }
*/
               Graphics gc = c2.getGraphics();
               gc.setColor(Color.BLACK);
//               gc.fillOval(x, y, 6, 6);

               int lMinx=28;
    int lMaxx=0;
               int lMiny=28;
    int lMaxy=0;
int m5[][]=new int[28][28];


               for(int i=2;i<26;i++)
{
    for (int j = 2; j < 26; j++)
    {
    if(m3[j][i]>0)
    {

m5[j][i]=1;

///*System.out.println(j+" "+i);

        if(i>lMaxy)
            lMaxy=i;

        if(i<lMiny)
            lMiny=i;
if(j>lMaxx)
            lMaxx=j;

        if(j<lMinx)
            lMinx=j;


       if (m3[j+1][i]==1||m3[j-1][i]==1)
        {
      //  if(i+2>lMaxy)
            if(i+1>lMaxy)
            lMaxy=i+1;

        if(i-1<lMiny)
            lMiny=i-1;
        
        m5[j][i+1]=1;
         m5[j][i-1]=1;
       // m5[j][i+2]=1;
        // m5[j][i-2]=1;

        }

  if(m3[j][i+1]==1|| m3[j][i-1]==1)
       {
       if(j+1>lMaxx)
            lMaxx=j+1;

        if(j-1<lMinx)
            lMinx=j-1;

 m5[j+1][i]=1;
 m5[j-1][i]=1;
 //m5[j+2][i]=1;
 //m5[j-2][i]=1;
 }
  }

    }}


for(int i=0;i<28;i++)
{
    for (int j = 0; j < 28; j++)
    {

m3[i][j]=m5[i][j];
            }

        }

for(int i=0;i<28;i++)
{
    for (int j = 0; j < 28; j++)
    {


System.out.print((m3[j][i]>0?1:0)+" ");

            }
    System.out.println();
        }


 System.out.println(lMaxx+" "+ lMinx+" "+lMaxy+" "+lMiny);

   
    {
    int m4[][]=new int[28][28];

    int xMargin=(28-(lMaxx-lMinx+1))/2;
    int yMargin=(28-(lMaxy-lMiny+1))/2;

    for(int i=yMargin+1;i<lMaxy-lMiny+1+yMargin+1;i++)
        for(int j=xMargin+1;j<lMaxx-lMinx+1+xMargin+1;j++)
            m4[j][i]=m3[j-xMargin][i-yMargin];

    m3=m4;


    }



for(int i=0;i<28;i++)
{
    for (int j = 0; j < 28; j++)
    {
        
        
        input[i*28+j]=m3[j][i];

        if(m3[j][i]>0)
        {
            gc.fillOval(j, i, 1, 1);
        }
       
System.out.print((m3[j][i]>0?1:0)+" ");

            }
    System.out.println();
        }

System.out.println(imgwidth+" "+imgheight);

   }
   
void refresh()
    {
    minx=xsize-1;
    maxx=0;
    miny=ysize-1;
    maxy=0;
    for(int i=0;i<xsize;i++)
        for(int j=0;j<ysize;j++)
            m[i][j]=0;


    canvas.repaint();
    c2.repaint();
        }

}
