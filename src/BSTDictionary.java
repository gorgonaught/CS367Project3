import java.util.Iterator;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  WordCloudGenerator
//File:             BSTDictionary
//Semester:         CS367 Fall 2016
//
//Author1:          Justin High (jshigh@wisc.edu)
//CS Login:         high
//Author2:			Aaron Gordner (agordner@wisc.edu)
//CS Login:			gordner
//Lecturer's Name:  Charles Fischer
//Lab Section:      004
//
///////////////////////////////////////////////////////////////////////////////

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary

    /**
     * Constructor for BSTDictionary.
     * Creates an empty tree setting the root to null
     * and the number of item in the tree to 0.
     */
    public BSTDictionary() {
    	this.root = null;
    }
    
    /**inserts given key into dictionary if not already found
     * 
     * @param key - the key to insert into the tree
     * @throws DuplicateException if the key is already in the dictionary
     */
    public void insert(K key) throws DuplicateException {
    	root = insertNode(root, key);
    	numItems ++;
    }

    /**helper function to recursively find where the node should go
     * returns a BSTnode indicating the root of the modified tree
     * 
     * @param node - root of the subtree to insert
     * @param key - key to insert
     * @return the node as modified
     * @throws DuplicateException
     */
    private BSTnode<K> insertNode(BSTnode<K> node, K key) throws DuplicateException {
    	if (node == null) {
    		return new BSTnode<K>(key, null, null);
    	}
    	
    	if (node.getKey().equals(key)) {
    		throw new DuplicateException();
    	}
    	
    	//add key to left subtree
    	if (key.compareTo(node.getKey()) < 0) {
    		node.setLeft(insertNode(node.getLeft(), key));
    		return node;
    	}
    	
    	//otherwise, add to right subtree
    	else {
    		node.setRight(insertNode(node.getRight(), key));
    		return node;
    	}
	}
    
    /**if key is in dictionary, delete and return true. Return false if key not found
     * 
     * @param key - key to delete
     */
	public boolean delete(K key) {
		root = deleteNode(root, key);
		if (root == null){
			return false;
		}
		else {
			numItems --;
			return true;
		}
    }
	
	/**
	 * Helper function to find a node and delete it
	 * 
	 * @param node - root of the subtree to search
	 * @param key - key to delete
	 * @return the key if found, otherwise null
	 */
	private BSTnode<K> deleteNode(BSTnode<K> node, K key) {
		if (node == null) {
			return null;
		}
		
		//this is the node to delete, find out if it has children
		if (key.equals(node.getKey())){
			if (node.getLeft() == null && node.getRight() == null) {
				return null;
			}
			
			if (node.getLeft() == null) {
				return node.getRight();
			}
			
			if (node.getRight() == null) {
				return node.getLeft();
			}
			
			K smallVal = smallest(node.getRight());
			node.setKey(smallVal);
			node.setRight(deleteNode(node.getRight(), smallVal));
			return node;
		}
		
		else if (key.compareTo(node.getKey()) < 0) {
			node.setLeft(deleteNode(node.getLeft(), key));
			return node;
		}
		
		else {
			node.setRight(deleteNode(node.getRight(), key));
			return node;
		}
	}

	/**return the smallest value in a non-null BST
	 * 
	 * @param node - root of subtree to search
	 * @return Smallest node in the subtree rooted at node
	 */
	private K smallest(BSTnode<K> node) {
		//if it has no left children, return this node
		if (node.getLeft() == null) {
			return node.getKey();
		}
		
		//otherwise keep looking for left children
		else {
			return smallest(node.getLeft());
		}
	}

	/**search for key and return it. If not found, return null
	 * 
	 * @param key - key to find
	 */
    public K lookup(K key) {
    	return lookup(root, key);
    }
    
    /**helper function to search for a key in the BST and return it
     * returns the key from the BST if it exists, else null
     * 
     * @param node - root of subtree to search
     * @param key - key to find
     * @return The key if found, otherwise null
     */
    private K lookup(BSTnode<K> node, K key) {
		if (node == null) {
			return null;
		}
		
		//This is the key we're looking for
		if (node.getKey().equals(key)) {
			return node.getKey();
		}
		
		//if the key of the node is less than the passed in key, look in the left tree, else look in the right Tree 
		if (key.compareTo(node.getKey()) < 0) {
			return lookup(node.getLeft(), key);
		}
		else {
			return lookup(node.getRight(), key);
		}
	}

	/**
	 * Return true iff the dictionary is empty
	 * @return True if dictionary is empty, else false
	 */
    public boolean isEmpty() {
    	if (numItems == 0) {
    		return true;
    	}
        return false;
    }
    
    /**
     * Return the number of keys in the dictionary
     * @return number of keys in dictionary
     */
    public int size() {
        return numItems;
    }
    
    /**
     * Return the total path length: the sum of the lengths of the paths to each (key,value) pair
     * @return total path length of the BST
     */
    public int totalPathLength() {
    	return totalPathLength(root, 1);
    }
    
    /**returns the total path length for the given BST node
     * 
     * @param node - key at which to start counting
     * @return - total path length for node and all of node's children
     */
    private int totalPathLength(BSTnode<K> node, int D) {
    	// base case of no nodes or only the root
    	if (node == null) { return 0; }
    	
    	// for a leaf, we add in the leaf's depth
    	if ( node.getLeft() == null && node.getRight() == null ) { return D; }
		
    	// for a node with children, we add 
		return ( D + totalPathLength( node.getLeft(), D + 1 ) + totalPathLength( node.getRight(), D + 1 ) );
	}

	/**
	 * return new iterator over the dictionary that iterates over the keys using in-order traversal
	 * @return Iterator on the BST dictionary
	 */
    public Iterator<K> iterator() {
    	return new BSTDictionaryIterator<K>(root);
    }
    
    /**
     * Displays a BSTDictionary as a string for debugging/review
     */
    public String toString() {
    	String retString = "";
        BSTDictionaryIterator<KeyWord> myIter = (BSTDictionaryIterator<KeyWord>) this.iterator();

        while (myIter.hasNext()){
        	retString += myIter.next().toString() + " | ";
        }
    	return retString;
    }
}