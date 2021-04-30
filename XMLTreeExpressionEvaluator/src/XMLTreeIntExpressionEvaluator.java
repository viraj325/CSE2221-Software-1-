import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 * 
 * @author Viraj Patel
 * 
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        
        //declaring all of the necessary int variables
        int value = 0, first = 0, second = 0;
        
        //checking if XMLTree exp has 0 or more than 0 children
        if(exp.numberOfChildren() == 0)
        	//value is set to the value of the operand if there wasn't any operator
        	value = Integer.parseInt(exp.attributeValue("value"));
        else {
        	//getting the operator
        	String operator = exp.label();
        	
        	//first and second will do recursion and start calculation from the last child and work back up
        	first = evaluate(exp.child(0));
        	second = evaluate(exp.child(1));
        	
        	if(operator.equals("plus"))
        		value = first + second;
        	
        	else if(operator.equals("minus"))
        		value = first - second;
        	
        	else if(operator.equals("divide")) 
        		value = first / second;
        	
        	else if(operator.equals("times"))
        		value = first * second;
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