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
	
	Stack<BSTnode<K>> dictStack;
	BSTnode<K> curr;
	
    // TO DO:
    //
    // Add your code to implement the BSTDictionaryIterator.  To receive full
    // credit:
    // - You must not use recursion in any of methods or constructor.
    // - The constructor must have a worst-case complexity of O(height of BST).
    // 
    // Hint: use a Stack and push/pop nodes as you iterate through the BST.
    // The constructor should push all the nodes needed so the *first* call 
    // to next() returns the value in the node with the smallest key.
    // (You can use the Java API Stack or implement your own Stack - if you
    // implement your own, make sure to hand it in.)
	
	//constructor
	public BSTDictionaryIterator(BSTnode<K> root) {
		dictStack = new Stack<BSTnode<K>>();
		if (root == null) { return; }
		curr = root;
		// find smallest non-leaf node in left tree
		StackItUp( );
	}
	
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

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}