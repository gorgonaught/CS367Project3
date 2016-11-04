import java.util.*;

//each KeyWord object contains a word and a non-negative integer representing
//the number of times the word occurs in the input file
public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	
	private String word;
	private int occurrence;
	
	//constructor
	//Creates a new KeyWord with a default number of occurrences of 0
	public KeyWord(String word) throws IllegalArgumentException {
		if (word == null) {
			throw new IllegalArgumentException();
		}
		
		else{
			this.word = word.toLowerCase();
			this.occurrence = 0;
		}
	}
	
	//methods
	//returns the priority for a given KeyWord
	@Override
	public int getPriority() {
		return occurrence;
	}
	
	//Compares this KeyWord to the specified object
	//returns true if the argument is !=null and is a KeyWord object
	// whose word is the same as the word of this KeyWord (ignore case)
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		
//		if (this.getWord() == other.getWord()) {
//			return true
//		}
		return false;
	}
	
	//Compares the KeyWord with the one given
	//compare the word associated with the KeyWords (ignore case)
	@Override
	public int compareTo(KeyWord other) {
		String word = this.getWord();
		String otherWord = other.getWord();
		return word.compareTo(otherWord);
	}
	
	//returns the number of occurrences of a given KeyWord
	public int getOccurrences() {
		return occurrence;
	}
	
	//adds one to the number of occurrences for this KeyWord
	public void increment() {
		this.occurrence += 1;
	}
	
	//returns the word associated with the KeyWord object
	public String getWord() {
		return word;
	}
}
