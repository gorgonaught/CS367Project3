import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  WordCloudGenerator
//File:             KeyWord
//Semester:         CS367 Fall 2016
//
//Author1:          Justin High (jshigh@wisc.edu)
//CS Login:         high
//Author2:			Aaron Gordner (agordner@wisc.edu)
//CS Login:		 	gordner
//Lecturer's Name:  Charles Fischer
//Lab Section:      004
//
///////////////////////////////////////////////////////////////////////////////

//each KeyWord object contains a word and a non-negative integer representing
//the number of times the word occurs in the input file
public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	
	private String word;
	private int occurrence;
	
	//constructor
	/**Creates a new KeyWord with a default number of occurrences of 1
	 * 
	 * @param word word to associate with this KeyWord
	 * @throws IllegalArgumentException - if word is null
	 */
	public KeyWord(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		
		else{
			this.word = word.toLowerCase();
			this.occurrence = 1;
		}
	}
	
	//methods
	/**returns the priority for a given KeyWord
	 * 
	 * @return the priority for a keyword
	 */
	@Override
	public int getPriority() {
		return occurrence;
	}
	
	/**
	 * compares this Keyword with the given object. If the object is a KeyWord
	 * and the KeyWord's word is the same as this word, then return true. Case-insensitive
	 * 
	 * @param other object with which to compare this Keyword
	 * @return True if other's word is the same as this word, else false
	 */
	public boolean equals(Object other) {
		if (other == null || !(other instanceof KeyWord)) {
			return false;
		}
		
		KeyWord otherWord = (KeyWord) other;
		return word.equalsIgnoreCase(otherWord.getWord());
	}
	
	/**Compares the KeyWord with the one given (case insensitive)
	 * 
	 * @param other - KeyWord to compare to
	 */
	@Override
	public int compareTo(KeyWord other) {
		String word = this.getWord();
		String otherWord = other.getWord();
		//convert KeyWords to strings and compare them using the built in method
		return word.compareTo(otherWord);
	}
	
	/**returns the number of occurrences of a given KeyWord
	 * 
	 * @return number of occurrences of a given keyword
	 */
	public int getOccurrences() {
		return occurrence;
	}
	
	//adds one to the number of occurrences for this KeyWord
	public void increment() {
		occurrence += 1;
	}
	
	/**returns the word associated with the KeyWord object
	 * 
	 * @return word associated with the KeyWord
	 */
	public String getWord() {
		return word;
	}
	
	public String toString() {
		if ( this.word != null ) { return word + " : " + Integer.toString(occurrence); }
		return "";
	}
}
