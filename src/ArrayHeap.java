import java.util.NoSuchElementException;

///////////////////////////////////////////////////////////////////////////////
//
//Main Class File:  WordCloudGenerator
//File:             ArrayHeap
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

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {
    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100; // default initial size of a heap
    private int n = 0; // number of entries in heap
    private E[] arrayHeap = null; // default empty array

    /**
     * Creates a new array heap using the default size
     * 
     */
    public ArrayHeap() {
    	//create a heap where array has size of INIT_SIZE + 1 to account for unused pos[0]
		arrayHeap = (E[]) new Prioritizable[INIT_SIZE + 1];
    	n = 0;
    }
    
    /**
     * Creates a new ArrayHeap of the given size
     * @param initSize - size of array  heap
     * @throws IllegalArgumentException if the size is less than 0
     */
    public ArrayHeap(int initSize) {
    	if (initSize < 0){
    		throw new IllegalArgumentException();
    	}
    	
    	arrayHeap = (E[])(new Prioritizable[initSize]);
    	n = 0;
    }

    /**
     * Determines if the ArrayHeap is empty
     * @return true if the array has no elements
     */
    public boolean isEmpty() {
        return (n == 0);
    }

    /**
     * Adds given item to the ArrayHeap
     * 
     * @param item - element to add to the heap
     */
    public void insert(E item) {
    	//should ignore position 0 and start adding at pos[1]
    	n += 1;
    	if ( n >= arrayHeap.length ) { 
    		this.resize(); 
    	}
    	
    	arrayHeap[n] = item;
    	// System.out.println("Adding (" + n + ") : " + item.toString());
    	// System.out.println( this.toString());
    	int currPos = n;
    	while ( !IsOrdered( parent(currPos )) ) {
    		// save current value
    		E currVal = arrayHeap[currPos];
    		int currPrior = arrayHeap[currPos].getPriority();
    		int parentPrior = arrayHeap[parent( currPos )].getPriority();
    		if ( currPrior >= parentPrior  ) {
    			// System.out.println("Swapping: " + "(" + currPos + ")" + currVal.toString() + " to " + "(" + parent(currPos) + ")" + arrayHeap[parent(currPos)].toString() );
    			arrayHeap[currPos] = arrayHeap[parent( currPos )];
    			arrayHeap[parent( currPos )] = currVal;
    			currPos = parent( currPos );
    		}
    		if ( currPrior == parentPrior ) { 
    			currPos = parent( currPos );
    		}
    		// System.out.println(this.toString());
    	}
    	// System.out.println( this.toString());
    }
    
    /**resizes the array if it gets too big
     * Creates a new ArrayHeap of twice the original size
     */
    private void resize() {
    	E[] newHeap = (E[])(new Prioritizable[ arrayHeap.length * 2 ]);
    	for (int i = 1; i <= n - 1; i++ ){
    		newHeap[i] = arrayHeap[i]; // O(N) = bad
    	}
    	arrayHeap = newHeap;
	}
    
	/**returns the item with the highest priority
	 * 
	 * @return element in the ArrayHeap with the highest priority
	 * @throws NoSuchElementException if the array has no items
	 */
    public E getMax() {
		if ( n < 1 ) { throw new NoSuchElementException(); }
        return arrayHeap[1];
    }
    
    /**removes and returns the item with the highest priority
     * It is replaced with the last value in the array which is
     * then incrementally moved to keep the heap ordered
     * 
     * @return item with the highest priority
     * @throws NoSuchElementException if the queue is empty
     */
	public E removeMax() {
		if ( n < 1 ) { throw new NoSuchElementException(); }
		// save value to return
		E returnVal = this.getMax();
		// System.out.println( "Removing: (1)" + returnVal.toString());
		
		// remove old max and re-order the array
		// replace value removed with value from end and remove value from end
		arrayHeap[1] = arrayHeap[n];
		int currPos = 1;
		while ( !IsOrdered( currPos ) ) {
			E currVal = arrayHeap[currPos];
			int lcPos = 2 * currPos;
			int rcPos = 2 * currPos + 1;
			// no children is already ordered
			// left child only
			if ( rcPos > n ) {
				if ( arrayHeap[lcPos].getPriority() > arrayHeap[currPos].getPriority() ) {
					// System.out.println("Moving: (" + currPos + ")" + " to (" + lcPos + ")" + arrayHeap[lcPos].toString());
					arrayHeap[currPos] = arrayHeap[lcPos];
					arrayHeap[lcPos] = currVal;
					currPos = lcPos;
					continue;
				}
			}
			else {
				int biggerPos = PrioritizedPosition( lcPos, rcPos );
				if ( biggerPos == 0) { continue; }
				if ( arrayHeap[biggerPos].getPriority() > arrayHeap[currPos].getPriority() ) {
					// System.out.println("Moving: (" + biggerPos + ")" + arrayHeap[biggerPos].toString() + " to (" + currPos + ")" + arrayHeap[currPos].toString());
					arrayHeap[currPos] = arrayHeap[biggerPos];
					arrayHeap[biggerPos] = currVal;
					currPos = biggerPos;
				}
			}
		}
		
		n--;
		return returnVal;  // return position 1
    }
	
	/**
	 * Checks if the array is ordered for a given position
	 * 
	 * @param pos position in the array to check
	 * @return
	 */
	private boolean IsOrdered ( int pos ) {
		if ( pos < 1 ) { return true; }
		int lcPos = 2 * pos;
		int rcPos = 2 * pos + 1;
		
		// no left child = no children = ordered node
		if ( lcPos > n ) { return true; }
		
		// left child only
		if ( rcPos > n ) { return arrayHeap[pos].getPriority() >= arrayHeap[lcPos].getPriority(); }
		
		// both children
		int posPrior = arrayHeap[pos].getPriority();
		int lcPrior = arrayHeap[lcPos].getPriority();
		int rcPrior = arrayHeap[rcPos].getPriority();
		return ( posPrior >= lcPrior && posPrior >= rcPrior );
	}
	
	/**
	 * Returns the array position with a greater priority
	 * Used to see which child is greater
	 * 
	 * @param lPos - left child node to compare
	 * @param rPos - right child node to compare
	 * @return node with the higher priority
	 */
	private int PrioritizedPosition ( int lPos, int rPos ) {
		if ( lPos > n ) { return 0; }
		if ( rPos > n ) { return lPos; }
		if ( arrayHeap[lPos].getPriority() > arrayHeap[rPos].getPriority() ) { return lPos; }
		return rPos;
	}
    
	/*
    // get left child of current node
    private int leftchild ( int pos ) {
    	if ( 2 * pos > this.n ) {
    		return 0;
    	}
    	return ( 2 * pos );
    }
    // get right child of current node
    private int rightchild ( int pos ) {
    	if ( 2 * pos + 1 > this.n ) {
    		return 0;
    	}
    	return ( 2 * pos + 1 );
    }
    */
	
    /** get parent of current node
     * 
     * @param pos - node to find the parent
     * @return parent position of the given node
     */
    private int parent( int pos ) {
    	if ( pos < 0 ) { return 0;}
    	return pos/2; // integer division always rounds down
    }
 
    /**returns number of items in PQ
     * 
     */
    public int size() {
        return this.n;
    }
    
    /**
     * Displays an ArrayHeap as a string for debugging/review
     */
    public String toString() {
    	String retStr = "";
    	int i = 1;
    	while ( arrayHeap[i] != null ) {
    		if ( i != 1 ) { retStr += ", "; }
    		retStr += arrayHeap[i].toString();
    		i++;
    	}
    	return retStr;
    }
}