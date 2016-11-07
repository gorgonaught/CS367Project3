import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary

    // TODO:
    //
    // Add a no-argument constructor
    //
    // Add your code to implement the Dictionary ADT operations using a binary
    // search tree.
    // You may use any code given in the on-line reading on BSTs.
    public BSTDictionary() {
    	this.root = null;
    }
    
    //inserts given key into dictionary if not already found
    //if key is already there, throws an exception
    public void insert(K key) throws DuplicateException {
        // TODO: add your code
    	root = insertNode(root, key);
    }

    //helper function to recursively find where the node should go
    //returns a BSTnode indicating the root of the modified tree
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
    
    //if key is in dictionary, delete and return true. Return false if key not found
	public boolean delete(K key) {
		root = deleteNode(root, key);
		if (root == null){
			return false;
		}
		else {
			return true;
		}
    }
	
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

	//return the smallest value in a non-null BST
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

	//search for key and return it. If not found, return null
    public K lookup(K key) {
    	return lookupNode(root, key);
    }
    
    //helper function to search for a key in the BST and return it
    //returns they key from the BST if it exists, else null
    private K lookupNode(BSTnode<K> node, K key) {
		if (node == null) {
			return null;
		}
		
		//This is the key we're looking for
		if (node.getKey().equals(key)) {
			return key;
		}
		
		//if the key of the node is less than the passed in key, look in the left tree, else look in the right Tree 
		if (key.compareTo(node.getKey()) < 0) {
			return lookupNode(node.getLeft(), key);
		}
		else {
			return lookupNode(node.getRight(), key);
		}
	}

	//return true iff the dictionary is empty
    public boolean isEmpty() {
    	if (numItems == 0) {
    		return true;
    	}
        return false;
    }
    
    //return the number of keys in the dictionary
    public int size() {
        return numItems;
    }
    
    //return the total path length: the sum of the lengths of the paths to each (key,value) pair
    //MUST use recursion
    public int totalPathLength() {
    	return countTotalPathLength(root);
    }
    
    //returns the total path length for the given BST node
    private int countTotalPathLength(BSTnode<K> node) {
    	//base cases of no nodes or only the root
    	if (node == null) {
			return 0;
		}
		if ((node.getLeft() == null) && (node.getRight() == null)) {
			return 1;
		}
		
		//check for children
    	//return the greater of the recursive call for each child
		int leftCount = 0;
		int rightCount = 0;
		if (node.getLeft() != null) {
			leftCount = countTotalPathLength(node.getLeft());
		}
		if (node.getRight() != null) {
			rightCount = countTotalPathLength(node.getRight());
		}
		
		//if the left count is greater, return that
		//otherwise if the right count is greater or they are the same, return the right count
		if (leftCount > rightCount) {
			return leftCount;
		}
		else {
			return rightCount;
		}
	}

	//return new iterator over the dictionary that iterates over the keys using in-order
    public Iterator<K> iterator() {
    	BSTDictionaryIterator<K> BSTiter = new BSTDictionaryIterator<K>(root);
        return BSTiter;
    }
}