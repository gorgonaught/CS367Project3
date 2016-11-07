import java.util.NoSuchElementException;

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
    private int size = 0;

    // TO DO:
    //
    // Add a no-argument constructor that constructs a heap whose underlying
    // array has enough space to store INIT_SIZE items before needing to 
    // expand.
    //
    // Add a 1-argument constructor that takes an integer parameter and 
    // constructs a heap whose underlying array has enough space to store the 
    // number of items given in the parameter before needing to expand.  If
    // the parameter value is less 0, an IllegalArgumentException is thrown.
    //
    // Add your code to implement the PriorityQueue ADT operations using a
    // heap whose underlying data structure is an array.
    
    
    
    //=================================================
    //TODO: remove this - found a helpful sample program here http://people.cs.vt.edu/shaffer/Book/JAVA/progs/MaxHeap/MaxHeap.java
    //TODO: more examples - http://www.cs.bu.edu/~snyder/cs112/CodeExamples/MaxHeap.java, http://courses.cs.washington.edu/courses/cse373/11wi/homework/5/BinaryHeap.java <- min-heap instead of max
    //=================================================
    
    
    
    //create a new array heap using the default size
    public ArrayHeap() {
    	//create a heap where array has size of INIT_SIZE + 1 to account for unused pos[0]
    	E[] arrayHeap = (E[])(new Prioritizable[INIT_SIZE + 1]);
    	size = 0;
    }
    
    //create a new array heap of the given size
    //throws IllegalArgumentException if the size is less than 0
    public ArrayHeap(int initSize) {
    	if (initSize < 0){
    		throw new IllegalArgumentException();
    	}
    	
    	E[] arrayHeap = (E[])(new Prioritizable[initSize]);
    	size = 0;//
    }

    //returns true if the PQ contains no items
    public boolean isEmpty() {
        return this.size == 0;  // replace this stub with your code
    }

    //adds given item to the PQ
    public void insert(E item) {
    	//should ignore position 0 and start adding at pos[1]

    	//increment the size variable and add to correct node
        // add your code
    }
    
    //resizes the array if it gets too big
    private Object resize() {
		// TODO Auto-generated method stub
    	//copy the arrayHeap into a new one of bigger size
    	//use the ArrayHeap(initSize) constructor
    	//return new ArrayHeap<E>(size * 2);
		return null;
	}
    
    //removes and returns the item with the highest priority
	public E removeMax() {
		//if queue is null, throw NoSuchElementException
        return null;  // replace this stub with your code
    }
	
	//returns the item with the highest priority
    public E getMax() {
		//if queue is null, throw NoSuchElementException
        return null;  // replace this stub with your code
    }
    
    //returns number of items in PQ
    public int size() {
        return this.size;  // replace this stub with your code
    }
}