import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	
	Stack<K> dictStack;
	
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
		dictStack = new Stack(BSTnode<K>());
		//push root, then push each left node
		while (root != null) {
			dictStack.push(root);
			root = root.getLeft();
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
    	BSTnode<K> next = dictStack.pop();
    	BSTnode<K> node = next;
    	//if node has right children, push each of them
    	if (node.getRight() != null) {
    		node = node.getRight();
    		while (node != null) {
    			dictStack.push(node);
    			node = node.getRight();
    		}
    	}
        return next;
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}