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
public final class Newton4 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Newton4() {
    }

    /**
    * Computes estimate of square root of x to within relative error 0.01%.
    * 
    * @param x
    *            positive number to compute square root of
    * @return estimate of square root
    */
    private static double sqrt(double x, double e) {
    	double r = x;
    	if(x!=0) {
        while (Math.abs(r * r - x) / x > (e * e)) {
            r = (r + x / r) / 2;
        	}
    	}
    	else {
    		return 0;
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
        out.print("Enter the value of relative error(e): ");
        double e = in.nextDouble();
        boolean yn = true;
        
        while (yn) {
            out.print("Enter a value to calculate the square root: ");
            double x = in.nextDouble();

            if (x>=0) 
            {
                yn = true;
                double r = sqrt(x, e);
                
                out.println("\nThe square root of " + x + " equals " + r
                        + ".");
            } 
            else if(x< 0) 
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
