///////////////////////////////////////////////////////////////////////////////
//
// Title:			WordCloudGenerator
// Files:			BSTDictionary, BSTDictionaryIterator, KeyWord, ArrayHeap
//					BSTnode, DictionaryADT, DuplicateException, Prioritizable, PriorityQueueADT
// Semester:        CS367 Fall 2016
//
// Author1:         Justin High (jshigh@wisc.edu)
// CS Login:        high
// Author2:			Aaron Gordner (agordner@wisc.edu)
// CS Login:		gordner
// Lecturer's Name: Charles Fischer
//
///////////////////////////////////////////////////////////////////////////////

import java.util.*;
import java.io.*;

public class WordCloudGenerator {
    /**
     * The main method generates a word cloud as described in the program 
     * write-up.  You will need to add to the code given here.
     * 
     * @param args the command-line arguments that determine where input and 
     * output is done:
     * <ul>
     *   <li>args[0] is the name of the input file</li>
     *   <li>args[1] is the name of the output file</li>
     *   <li>args[2] is the name of the file containing the words to ignore 
     *       when generating the word cloud</li>
     *   <li>args[3] is the maximum number of words to include in the word 
     *       cloud</li> 
     * </ul>
     */
    public static void main(String[] args) {
    	boolean debug = true;

        // Check the command-line arguments and set up the input and output
        // 1. Check whether there are exactly four command-line arguments;
        // if not, display "Four arguments required: inputFileName outputFileName ignoreFileName maxWords" and quit.
        if ( args.length != 4 ) {	System.out.println("Four arguments required: inputFileName outputFileName ignoreFileName maxWords"); return; }
        
        // 2. Check whether input and ignore files (given as command-line arguments) exist and are readable;
        // if not, display "Error: cannot access file fileName" where fileName is the name of the appropriate file and then quit.
		String inputFileName = args[0];
		File inputFile = new File(inputFileName);
		if (!inputFile.exists() || !inputFile.canRead())
		{
			System.out.println("Error: cannot access file " + inputFileName);
			return;
		}
		
		// grab output file name for later
		String outputFileName = args[1];
		
		String ignoreFileName = args[2];
		File ignoreFile = new File(ignoreFileName);
		if (!ignoreFile.exists() || !ignoreFile.canRead())
		{
			System.out.println("Error: cannot access file " + ignoreFileName);
			return;
		}
		
		// 3. Check whether the maxWords command-line argument is a positive integer; if not, display "Error: maxWords must be a positive integer" and quit.
		int maxWords = 0;
		try{
			maxWords = Integer.parseInt(args[3]);
			if ( maxWords < 1 ) { System.out.println("Error: maxWords must be a positive integer"); return; }
		}
		catch (NumberFormatException NFex){
			System.out.println("Error: maxWords must be a positive integer");
		}
		
		// 4. Read in the ignore file and create a dictionary of words to ignore.
		Scanner inIgnore = null;   // for input from ignore file 
		BSTDictionary<String> ignoreDict = new BSTDictionary<String>();
		try {
			inIgnore = new Scanner(ignoreFile);
			while (inIgnore.hasNextLine())
			{
				List<String> words = parseLine(inIgnore.nextLine());
				for (String word : words) {

					try {
						ignoreDict.insert(word.toLowerCase());
						if ( debug ) { System.out.println("Adding ignored word: " + word); }
					}
					catch (DuplicateException e){
						// ignore duplicate words in ignore file
						if (debug) {
							System.out.println("Ignored duplicate ignored word: " + word);
						}
					}
				}
			}
		}
		catch (FileNotFoundException FNFex)
		{
			if (debug)
			{
				FNFex.printStackTrace();
			}
			System.out.println("problem with ignore file");
			return;
		}
		inIgnore.close();

        // 5. Read in the input text file and create a dictionary of key words for it (leaving out any words listed in the ignore dictionary).
        Scanner in = null;         // for input from text file
		BSTDictionary<KeyWord> dictionary = new BSTDictionary<KeyWord>(); 
		try {
			in = new Scanner(inputFile);
			while (in.hasNextLine())
			{
				List<String> words = parseLine(in.nextLine());
				for (String word : words){
					// create keyword for word to add
					word = word.toLowerCase();
					KeyWord newKey = new KeyWord( word );
					
					// make sure this keyword isn't in the ignore list
					if (ignoreDict.lookup(word) != null) {
						if (debug) {
							System.out.println("Ignored word: " + word);
						}
						continue;
						}
					
					KeyWord existingKey = dictionary.lookup(newKey);
					if ( existingKey != null ) {
						existingKey.increment();
						if ( debug ) { 
							System.out.println("Incremented dictionary word: " + word + ": " + Integer.toString(existingKey.getOccurrences()) );
							// System.out.println(dictionary.toString());
							}
					}
					else {
						try {
								dictionary.insert(newKey);
								if (debug) {
									System.out.println("Added word to dictionary: " + word);
									// System.out.println(dictionary.toString());
								}
							}
						// shouldn't be a dup since we just did lookup, but...
						catch (DuplicateException e){
							// ignore duplicate words in ignore file
							if (debug) { System.out.println("Duplicate key in dictionary: " + word);}
							newKey.increment();
						}
					}
				}
			}
		}
		catch (FileNotFoundException FNFex) {
			if (debug) {
				FNFex.printStackTrace();
			}
			System.out.println("problem with ignore file");
		}
		in.close();
		// print out in-order traversal of nodes
		if ( debug ) { System.out.println(dictionary.toString()); }

        /* 6. Print out information about the dictionary of key words in the following format:
		# keys: keys
		avg path length: average
		linear avg path: linear

		where keys is the number of keys in the dictionary,
		average is the average path length, i.e., (total path length)/(# keys),
		and linear is the average path length if the underlying data structure is linear (like a chain of linked nodes), i.e., (1 + # keys)/2 
		*/
		float keys = dictionary.size();
		if ( keys == 0 ) { System.out.println("All words ignored. No output created."); return; }
		float totalPathLength = dictionary.totalPathLength();
		
		System.out.println("# keys: " + Float.toString((int)keys));
		if (keys == 0){
			System.out.println("avg path length: N/A");
			}
		else { 
			System.out.println("avg path length: " + Float.toString(totalPathLength/keys));
			}
		System.out.println("linear avg path: " + Float.toString( (1 + keys)/2 ));
		
		// order key words in priority queue 
        BSTDictionaryIterator<KeyWord> dictIter = (BSTDictionaryIterator<KeyWord>) dictionary.iterator();
        ArrayHeap<KeyWord> cloudQueue = new ArrayHeap<KeyWord>();
        while (dictIter.hasNext()){
        	cloudQueue.insert(dictIter.next());
        }
        if ( debug ) { System.out.println( cloudQueue.toString() ); }
        
        // get top maxWords words from queue
        DictionaryADT<KeyWord> outputDict = new BSTDictionary<KeyWord>();
        int i = 0;
        while (i < maxWords){
        	i++;
        	try {
        		KeyWord temp = cloudQueue.removeMax();
				outputDict.insert( temp );
			}
        	catch (DuplicateException e) {
				if (debug) { e.printStackTrace(); }
			}
        	catch (NoSuchElementException NSEex) {
        		if (debug) { NSEex.printStackTrace(); }
        	}
        }
        
        // create output html file based on top words
        File outFile = new File(outputFileName);
        PrintStream out = null;
        try {
			out = new PrintStream(outFile);
			generateHtml(outputDict, out);
		} 
        catch (FileNotFoundException e) {
			if (debug) { e.printStackTrace(); }
		}
        out.close();
    }

    
    /**
     * Parses the given line into an array of words.
     * 
     * @param line a line of input to parse
     * @return a list of words extracted from the line of input in the order
     *         they appear in the line
     *         
     * DO NOT CHANGE THIS METHOD.
     */
    private static List<String> parseLine(String line) {
        String[] tokens = line.split("[ ]+");
        ArrayList<String> words = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {  // for each word
            
            // find index of first digit/letter
              boolean done = false; 
              int first = 0;
            String word = tokens[i];
            while (first < word.length() && !done) {
                if (Character.isDigit(word.charAt(first)) ||
                    Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;
            }
            
            // find index of last digit/letter
            int last = word.length()-1;
            done = false;
            while (last > first && !done) {
                if (Character.isDigit(word.charAt(last)) ||
                        Character.isLetter(word.charAt(last)))
                        done = true;
                    else last--;
            }
            
            // trim from beginning and end of string so that is starts and
            // ends with a letter or digit
            word = word.substring(first, last+1);
  
            // make sure there is at least one letter in the word
            done = false;
            first = 0;
            while (first < word.length() && !done)
                if (Character.isLetter(word.charAt(first)))
                    done = true;
                else first++;           
            if (done)
                words.add(word);
        }
        
        return words;
    }
    
    /**
     * Generates the html file using the given list of words.  The html file
     * is printed to the provided PrintStream.
     * 
     * @param words a list of KeyWords
     * @param out the PrintStream to print the html file to
     * 
     * DO NOT CHANGE THIS METHOD
     */
    private static void generateHtml(DictionaryADT<KeyWord> words, PrintStream out) {
    	String[] colors = { 
    			"#CD5C5C",      //INDIANRED
				"#5F9EA0",      //CADETBLUE
			 	"#FA8072",      //SALMON
			 	"#E9967A",      //DARKSALMON
			 	"#FF69B4",      //HOTPINK
			 	"#FFA500",      //ORANGE
				"#B22222",		// FIREBRICK
			 	"#E6E6FA",      //LAVENDER
			 	"#8A2BE2",      //BLUEVIOLET
			 	"#6A5ACD",      //SLATEBLUE
			 	"#7FFF00",      //CHARTREUSE
			 	"#32CD32",      //LIMEGREEN
			 	"#228B22",      //FORESTGREEN
			 	"#66CDAA",      //MEDIUMAQUAMARINE
			 	"#00FFFF",      //CYAN
			 	"#1E90FF",      //DODGERBLUE
			 	"#FFE4C4",      //BISQUE
			 	"#8B4513",      //SADDLEBROWN
			 	"#F5F5DC",      //BEIGE
			 	"#C0C0C0"       //Silver       
	    };
    	int initFontSize = 100;
    	String fontFamily = "Cursive";
           
        // Print the header information including the styles
        out.println("<head>\n<title>Word Cloud</title>");
        out.println("<style type=\"text/css\">");
        out.println("body { font-family: "+fontFamily+" }");
        
        // Each style is of the form:
        // .styleN {
        //      font-size: X%;
        //      color: #YYYYYY;

        // }
        //  where N and X are integers and Y is a hexadecimal digit
        for (int i = 0; i < colors.length; i++)
            out.println(".style" + i + 
                    " {\n    font-size: " + (initFontSize + i*20)
                    + "%;\n    color: " + colors[i] + ";\n}");

        
        out.println("</style>\n</head>\n<body><p>");        
        
        // Find the minimum and maximum values in the collection of words
        int min = Integer.MAX_VALUE, max = 0;
        for (KeyWord word : words) {
            int occur = word.getOccurrences();
            if (occur > max)
                max = occur;
            if (occur < min)
                min = occur;
        }

        double slope = (colors.length - 1.0)/(max - min);
        
        for (KeyWord word : words) {
            out.print("<span class=\"style");
            
            // Determine the appropriate style for this value using
            // linear interpolation
            // y = slope *(x - min) (rounded to nearest integer)
            // where y = the style number
            // and x = number of occurrences
            int index = (int)Math.round(slope*(word.getOccurrences() - min));
            
            out.println(index + "\">" + word.getWord() + "</span>&nbsp;");
        }
        
        // Print the closing tags
        out.println("</p></body>\n</html>");
    }
 }