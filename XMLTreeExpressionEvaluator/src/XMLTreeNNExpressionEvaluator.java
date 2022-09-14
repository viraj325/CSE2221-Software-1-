import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     * 
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        //declaring all of the necessary NaturalNumber variables
        NaturalNumber value = new NaturalNumber1L(), first = new NaturalNumber1L(), second = new NaturalNumber1L();
        
        //checking if XMLTree exp has 0 or more than 0 children
        if(exp.numberOfChildren() == 0)
        	//value is set to the value of the operand if there wasn't any operator
            value.setFromInt(Integer.parseInt((exp.attributeValue("value"))));
        else{
        	//getting the operator
        	String operator = exp.label();
        	
        	//recursion to calculate from the last child and work back up and copy the values to first and second
        	first.copyFrom(evaluate(exp.child(0)));
        	second.copyFrom(evaluate(exp.child(1)));
        	
        	if(operator.equals("plus")) {
        		first.add(second);
        		value.transferFrom(first);
        	}
        	else if(operator.equals("minus")){
        		first.subtract(second);
        		value.transferFrom(first);
        	}
        	else if(operator.equals("divide")) {
        		if(second.toInt() == 0)
        			//divide by 0 error using reporter
        			Reporter.fatalErrorToConsole("error: divide by 0");
        		
        		first.divide(second);
        		value.transferFrom(first);
        	}
        	else if(operator.equals("times")) {
        		first.multiply(second);
        		value.transferFrom(first);
        	}
        }
        
        /*
         * This line added just to make the program compilable. Should be
         * replaced with appropriate return statement.
         */
        //returning the calculated value of the expression
        return value;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
