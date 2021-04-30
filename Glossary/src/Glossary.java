import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Making HTML files for terms and definitions.
 *
 * @author Viraj Patel
 *
 */
public final class Glossary {

    private Glossary() {
    }

    /**
     *
     * class to compare strings
     *
     */
    private static class compareString implements Comparator<String> {
    	@Override
        public int compare(String s1, String s2) {
            if (s1.compareTo(s2) > 0)
                return 1;

            else if (s1.compareTo(s2) < 0)
                return -1;

            else
                return 0;
        }
    }

    /**
     *
     * @param map
     * @param q
     * @updates map
     */
    private static void replace(Map<String, String> map,
            Queue<String> q) {

    	String ss = " \t:;,.-";
        Set<Character> s = new Set2<>();
        generate(ss, s);

        Map<String, String> t = map.newInstance();
        t.transferFrom(map);

        while (t.size() != 0) {
            String ans = "";
            Map.Pair<String, String> mapPair = t.removeAny();

            String key = mapPair.key();
            String value = mapPair.value();
            int p = 0;
            String temp = "";

            while (p < value.length()) {
                String result = wordSeperator(value, p,s);

                temp = result;

                if (!s.contains(result.charAt(0))) {
                    Queue<String> tq = q.newInstance();
                    tq.transferFrom(q);

                    while (tq.length() != 0) {
                        String pair = tq.dequeue();

                        if (result.equals(pair))
                            temp = "<a href=\"" + pair + ".html\">" + pair
                                    + "</a>";
                        q.enqueue(pair);
                    }
                }

                p += result.length();
                ans += temp;
            }
            map.add(key, ans);
        }

    }
    
    /**
     * @param in
     * @requires input file is not empty
     * @return The words in file.
     */
    private static String getTerm(SimpleReader in) {
        assert in != null : "Violation of: input is not null";

        String w = in.nextLine();
        String a = "";

        while (!w.equals("") && !in.atEOS()) {
            a += w;
            w = in.nextLine();
            String nextWord = w;

            if (!w.equals(""))
                a += "|";
            w = nextWord;
        }
        return a + w;
    }

    /**
     *
     * @param s
     * @param ss
     *
     * @updates ss
     */
    private static void generate(String s, Set<Character> ss) {
        assert s != null : "Violation of: str is not null";
        assert ss != null : "Violation of: strSet is not null";

        if (s.length() != 0)
            for (int i = 0; i < s.length(); i++) {
                ss.add(s.charAt(i));
            }
    }
    
    /**
    *
    * @param w
    *            Words for map
    * @param map
    *            Includes all of the terms and definitions
    * @updates map
    * @requires w to be not empty
    *
    */
    private static void map(String w, Map<String, String> map) {
    	assert w != null : "Violation of: fileName is not null";
    	String t = w.substring(0, w.indexOf("|"));
    	String d = w.substring(w.indexOf("|") + 1,
	               w.length());
	
    	if (!map.hasKey(t))
    		map.add(t, d);
	   }   

    /**
     *
     * @param t
     * @param p
     * @param s
     *
     * @return Next Word
     */
    private static String wordSeperator(String t, int p,Set<Character> s) {
        assert t != null : "Violation of: text is not null";
        assert s != null : "Violation of: separators is not null";
        assert 0 <= p : "Violation of: 0 <= position";
        assert p < t.length() : "Violation of: position < |text|";

        int a = 0;

        String ts = t.substring(p, t.length());
        String ans = "";

        if (s.contains(t.charAt(p))) {
            while (a < ts.length()) {
                if (s.contains(t.charAt(p + a))) {
                    ans += t.charAt(p + a);
                    a++;
                }

                else {
                    a = ts.length();
                }
            }
        }

        else {
            while (a < ts.length()) {
                if (!s.contains(t.charAt(p + a))) {
                    ans += t.charAt(p + a);
                    a++;
                }

                else {
                    a = ts.length();
                }
            }
        }
        return ans;
    }

    /**
     *
     * @param map
     * @param out
     * @param folder
     *
     */
    private static void header(Map<String, String> map,
            SimpleWriter out, String folder) {

        Map<String, String> t = map.newInstance();
        Queue<String> q = inQueue(map);
        replace(map, q);
        t.transferFrom(map);

        while (t.size() != 0) {
            Map.Pair<String, String> mapPair = t.removeAny();
            String keyPair = mapPair.key();
            String valuePair = mapPair.value();

            out = new SimpleWriter1L(folder + "/" + keyPair + ".html");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + keyPair + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2><b><i><font color=\"red\">" + keyPair + "</font></i></b></h2>");
            out.println("<blockquote>" + valuePair + "</blockquote>");
            out.println("<hr/>");
            out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
    *
    * @param map
    *
    * @return Map key and value Queue
    */
   private static Queue<String> inQueue(Map<String, String> map) {

       Map<String, String> temp = map.newInstance();
       Queue<String> queue = new Queue1L<>();
       temp.transferFrom(map);

       while (temp.size() != 0) {

           Map.Pair<String, String> pair = temp.removeAny();

           String keyPair = pair.key();
           String valuePair = pair.value();
           queue.enqueue(keyPair);
           map.add(keyPair, valuePair);
       }

       return queue;
   }

    /**
     *
     * @param q
     * @param out
     */
    private static void index(Queue<String> q, SimpleWriter out) {

        Queue<String> tempQueue = q.newInstance();
        tempQueue.transferFrom(q);

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Glossary</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        out.println("<hr />");
        out.println("<h3>Index</h3>");
        out.println("<ul>");

        while (tempQueue.length() != 0) {
            String queueHold = tempQueue.dequeue();

            out.println("<li><a href=\"" + queueHold + ".html\">" + queueHold
                    + "</a></li>");

            q.enqueue(queueHold);
        }

        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method
     *
     * @param args
     *            Command-Line Arguments
     */
    public static void main(String[] args) {
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();
        Comparator<String> o = new compareString();

        output.println("Input file name: ");
        String fileName = input.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);
        output.println("Folder name: ");

        String folder = input.nextLine();

        Map<String, String> map = new Map1L<>();

        while (!file.atEOS()) {
            String s = getTerm(file);
            String t = s.substring(0, s.indexOf("|") + 1);
            String d = s.substring(s.indexOf("|") + 1,
                    s.length());

            while (d.contains("|")) {
                d = d.substring(0, d.indexOf("|")) + " " + d.substring(d.indexOf("|") + 1, d.length());
            }

            String ans = t + d;
            map(ans, map);

        }

        Queue<String> queue = inQueue(map);
        queue.sort(o);
        SimpleWriter index = new SimpleWriter1L(folder + "/index.html");
        index(queue, index);

        SimpleWriter terms = new SimpleWriter1L();
        header(map, terms, folder);
        file.close();
        index.close();
        output.close();
        input.close();
    }
}