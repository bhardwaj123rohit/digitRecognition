/*
 * This class corresponds to the Matrix class which is responsible  for all the
 * Calculations in the WEIGHT matrix
 */

package digitrecogniton;

/**
 *
 * @author ANKITA
 */
public class Matrix {

   double [][] a;// holds the value of the matrix
    int rowSize, columnSize;

    Matrix(int r,int c)// constructor for initializing the matrix
    {
    rowSize =r;
    columnSize=c;

    a= new double[rowSize][columnSize];

    
    }
void equal(Matrix m)
        {

for(int i=0;i< m.rowSize;i++)
{
    for(int j=0;j<m.columnSize;j++)
        a[i][j]=m.a[i][j];


}

        }
    public double [][] getA() {
        return a;
    }

    public void setA(double [][] a) {
        this.a = a;
    }

    @Override
  public   String toString()
    {
    String s=new String();
    s=" ";
    for(int i=0;i<rowSize;i++)
    {
        for (int j = 0; j < columnSize; j++)
        {
        s+=a[i][j]+" ";


        }
        s+="\n";
        }
    return s;
    }

  Matrix Transpose()
    {
    Matrix c= new Matrix(columnSize,rowSize);
    for(int i=0;i<rowSize;i++)
        for(int j=0;j<columnSize;j++)
            c.a[j][i]=a[i][j];

    return c;

    }



double[] getRow(int i) // gets the ith row of the matrix
    {
        return a[i];

    }

  double[] getColumn(int i)// gets the ith column of the matrix
    {
        double b[]=new double[rowSize];

        for(int j=0;j<rowSize;j++)// loop for converting the column into a array
            b[j]=a[j][i];

        return b;

    }


    static   Matrix Multiply(Matrix x,Matrix y ) throws Exception// static function
            // to multiply to matrices and return the result
    {
         Matrix  c= new Matrix(x.rowSize, y.columnSize);

       if(x.columnSize!=y.rowSize)// condition for the multiplication to be possible
       {
                throw new Exception("Multiplication not possible");

//                return null;

       }


       for(int i=0;i<x.rowSize;i++)
       {
           for(int j=0;j<y.columnSize;j++)
           {     c.a[i][j]=0;
               for(int k=0;k<y.rowSize;k++)
               {
                   c.a[i][j]+=x.a[i][k]*y.a[k][j];

               }
           }
       }

        return c;

    }


    static Matrix dotMultiply(Matrix x, Matrix y) throws Exception
    {
    if(x.columnSize!=y.columnSize||x.rowSize!=y.rowSize)// checking for dot Multiplication compartibility
        throw new Exception("Dot Multiplication not possible ");

    Matrix c= new Matrix(x.rowSize, x.columnSize);
    for(int i=0;i<x.rowSize;i++)//logic for dot multiplication
        for(int j=0;j<x.columnSize;j++)
            c.a[i][j]=x.a[i][j]*y.a[i][j];

    return c;

    }

    static Matrix insertRow(Matrix m )
    {
    Matrix n= new Matrix(m.rowSize+1, m.columnSize);

    for (int i=0;i<n.rowSize;i++)
        for(int j=0;j<n.columnSize;j++)
        {
            if(i==0)
                n.a[i][j]=1.0;
            else
                n.a[i][j]=m.a[i-1][j];


        }

    return n;
    }


    static Matrix scalarMultiply(double  k, Matrix x)throws Exception
    {
    int r=x.rowSize;
    int c= x.columnSize;

    Matrix m =new Matrix(r, c);

    for(int i=0;i<r;i++)
        for(int j=0;j<c;j++)
            m.a[i][j]=k;

    return dotMultiply(x, m);

    }
    static Matrix loggify(Matrix x)
    {
        int r=x.rowSize;
        int c=x.columnSize;
        Matrix logged = new Matrix(r,c);
        
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
              logged.a[i][j]= Math.log(x.a[i][j]);
            }
        }   

        return logged;
    }

static Matrix makeOne(Matrix x)
{
        int r=x.rowSize;
        int c=x.columnSize;
        Matrix ones = new Matrix(r,c);
        
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                ones.a[i][j]=1;
            }
        }
        return ones;
    
}
static Matrix subtraction(Matrix x, Matrix y) throws Exception
{
        int r=x.rowSize;
        int c=x.columnSize;
    
        Matrix sub = new Matrix(r,c);
        int r2=y.rowSize;
        int c2=y.columnSize;
    
     if(x.columnSize!=y.columnSize||x.rowSize!=y.rowSize)
        throw new Exception("Subtraction Not Possible:- Dimension Mismatch  ");
     
     for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                sub.a[i][j]= x.a[i][j] -y.a[i][j];
            }
        }
     
     return sub;
    
    
}
static Matrix addition(Matrix x, Matrix y) throws Exception
{
        int r=x.rowSize;
        int c=x.columnSize;
    
        Matrix add = new Matrix(r,c);
        int r2=y.rowSize;
        int c2=y.columnSize;
    
     if(x.columnSize!=y.columnSize||x.rowSize!=y.rowSize)
        throw new Exception("Addition Not Possible:- Dimension Mismatch  ");
     
     for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                add.a[i][j]= x.a[i][j] + y.a[i][j];
            }
        }
     
     return add;
    
    
}
static double sum(Matrix x)
{
    int r=x.rowSize;
    int c=x.columnSize;
    double sum=0;
    
    for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                sum = sum + x.a[i][j];
                
            }
        }   
    
    return sum;
    
}
static Matrix makeFirstColZero(Matrix x){
    
    int r=x.rowSize;
    int c=x.columnSize;
    
    Matrix colZero = new Matrix(r, c);
    
    for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                if(j!=0)
                    colZero.a[i][j]=x.a[i][j];
                else
                    colZero.a[i][j]=0.0;
                
            }
        }
    return colZero;
    
}
static Matrix removeFirstRow(Matrix x)
{
    
    int r=x.rowSize;
    int c=x.columnSize;
  //  System.out.println("Initial Size "+r+" "+c);
    
    Matrix firstRowRemoved = new Matrix(r-1, c);
    
    for(int i=0;i<r-1;i++)
        {
            for(int j=0;j<c;j++)
            {
                firstRowRemoved.a[i][j]=x.a[i+1][j];
                
            }
            
        }
    //System.out.println("After Size "+firstRowRemoved.rowSize+" "+firstRowRemoved.columnSize);
    return firstRowRemoved;
    
}
static Matrix addZeroCol(Matrix x)
{
    int r=x.rowSize;
    int c=x.columnSize;
  //  System.out.println("Initial Size "+r+" "+c);
    
    Matrix zerocol = new Matrix(r, c+1);
    
    for(int i=0;i<r;i++)
        {
            for(int j=0;j<c+1;j++)
            {
               if(j!=0)
                zerocol.a[i][j]=x.a[i][j-1];
               else
                 zerocol.a[i][j]=1.0;
            }
        }
    return zerocol;
    
}
static Matrix doubleToMatrix(double[][] x) {
    
    Matrix m = new Matrix(x.length,x[0].length);
    
    for(int i=0;i<m.rowSize;i++)
    {
        for(int j=0;j<m.columnSize;j++)
        {
            m.a[i][j]= x[i][j];
        }
    }
    return m;
}



}
