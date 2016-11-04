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
    
    public void insert(K key) throws DuplicateException {
        // TODO: add your code
    }

    public boolean delete(K key) {
        return false;  // TODO: replace this stub with your code
    }

    public K lookup(K key) {
        return null;  // TODO: replace this stub with your code
    }

    public boolean isEmpty() {
    	if (numItems == 0) {
    		return true;
    	}
        return false;
    }

    public int size() {
        return numItems;
    }
    
    public int totalPathLength() {
        return 0;  // TODO: replace this stub with your code
    }
    
    public Iterator<K> iterator() {
        return null;  // TODO: replace this stub with your code
    }
}