import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Computing roots using Newton Iteration
 *
 * @author Viraj Patel
 *
 */
public final class Newton1 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton1() {
    }

    /**
    * Computes estimate of square root of x to within relative error 0.01%.
    * 
    * @param x
    *            positive number to compute square root of
    * @return estimate of square root
    */
    private static double sqrt(double x) {
    	double r = x;
        while (Math.abs(r * r - x) / x > (.01 * .01)) {
            r = (r + x / r) / 2;
        }
        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        boolean yn = true;
        
        while (yn) {
            out.print("Enter a value to find its square root: ");
            double x = in.nextDouble();
            
            double r = sqrt(x);
            
            out.println("\nThe square root of " + x + " equals " + r
                    + ".");
            
            out.print("\nY to continue or N to cancel: ");
            String c = in.nextLine();

            if (c.equals("Y")) 
            {
                yn = true;
            } 
            else if(c.equals("N")) 
			{
                yn = false;
            }
        }
        /*
         * Close input and output streams
         */
        out.println("Cancelled");
        in.close();
        out.close();
    }

}
