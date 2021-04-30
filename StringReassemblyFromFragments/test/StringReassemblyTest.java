import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

public class StringReassemblyTest {
	//Name: Viraj Patel
	
	/**
	 * Test of combination.
	 */
	@Test
    public void addToSetAvoidingSubstrings1() {
        Set<String> str = new Set1L<String>();
        Set<String> check = new Set1L<String>();
        String s1 = "Viraj~";
        String s2 = "o Patel -- P";
        String sub = "iraj~";

        str.add(s1);
        str.add(s2);
        check.add(s1);
        check.add(s2);

        StringReassembly.addToSetAvoidingSubstrings
        (str, sub);

        assertEquals(check, str);

    }

    /**
	 * Test of combination.
	 */
    @Test
    public void inputTest() {
        Set<String> lines = new Set1L<String>();
        Set<String> inputCkeck = new Set1L<String>();
        SimpleReader input = new SimpleReader1L("cheer-8-2.txt");
        String one = "Bucks -- Beat";
        String two = "Go Bucks";
        String three = "o Bucks -- B";
        String four = "Beat Mich";
        String five = "ichigan~";
        String six = "Bucks";
        String seven = "Michigan~";
        inputCkeck.add(one);
        inputCkeck.add(two);
        inputCkeck.add(three);
        inputCkeck.add(four);
        inputCkeck.add(seven);
        
        lines = StringReassembly.linesFromInput(input);

        assertEquals(inputCkeck, lines);
    }

	/**
	 * Test of combination.
	 */
	@Test
	public void combinationTest_Name() {
		String t = "Hi, Viraj";
		String t2 = "Viraj Patel";
		
		int overlap = StringReassembly.overlap(t, t2);
        String combination = StringReassembly.combination
        		(t, t2, overlap);

        assertEquals("Hi, Viraj Patel", combination);
	}

}