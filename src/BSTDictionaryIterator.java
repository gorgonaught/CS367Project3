import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  WordCloudGenerator
//File:             BSTDictionaryIterator
//Semester:         CS367 Fall 2016
//
//Author1:          Justin High (jshigh@wisc.edu)
//CS Login:         high
//Author2:			 Aaron Gordner (agordner@wisc.edu)
//CS Login:		 gordner
//Lecturer's Name:  Charles Fischer
//Lab Section:      004
//
///////////////////////////////////////////////////////////////////////////////

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	
	private Stack<BSTnode<K>> dictStack;
	private BSTnode<K> curr;
	
	//constructor
	/**
	 * Create a new BSTDictionary iterator starting at the given root node
	 * When the iterator is created, the root is pushed along with all of
	 * the root's left children
	 * 
	 * @param root The root of the tree to iterate over
	 */
	public BSTDictionaryIterator(BSTnode<K> root) {
		dictStack = new Stack<BSTnode<K>>();
		if (root == null) { return; }
		curr = root;
		// find smallest non-leaf node in left tree
		StackItUp( );
	}
	
	/**
	 * Pushes all of a node's left children onto dictStack
	 */
	private void StackItUp(){
		while ( curr != null ) {
			dictStack.push( curr );
			curr = curr.getLeft();
		}
	}

	/**
	 * Returns whether or not the stack is empty
	 * @return true if there is an item in the stack; else false.
	 */
    public boolean hasNext() {
        return !dictStack.empty();
    }

    /**
	 * Returns the data value for the next node in the tree/stack.
	 * @return the data held in the next node.
	 * @throws NoSuchElementException if dictionary is empty
	 */
    public K next() {
    	// exception if already empty
    	if ( !this.hasNext() ) { throw  new NoSuchElementException(); }

    	K retKey = null;
    	if ( curr == null && !dictStack.isEmpty() ) {
    		curr = dictStack.pop();
    		retKey = curr.getKey();
    		curr = curr.getRight();
    		StackItUp();
    	}
    	return retKey;
    }

    /**
     * Not implemented
     */
    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}