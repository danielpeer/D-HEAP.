

import java.util.Arrays;
/**
 * D-Heap
 * 
 * 	 Students:
 *	 Yotam Manne יותם מנה 204717862 username: yotammanne
 *	 Daniel Peer 206283442 דניאל פאר username: danielpeer
 * 
 * 
 */

public class DHeap
{
	
    private int size, max_size, d;
    public DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
       max_size = m_size;
	   d = m_d;
       array = new DHeap_Item[max_size];
       size = 0;
    }
	
	/**
	 * public int getSize()
	 * Returns the number of elements in the heap.
	 */
	public int getSize() {
		return size;
	}
	
	
  /**
     * public int arrayToHeap()
     *
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * preconidtion: array1.length() <= max_size
     * postcondition: isHeap()
     * 				  size = array.length()
     * Returns number of comparisons along the function run. 
	 */
    public int arrayToHeap(DHeap_Item[] array1) 
    {
        int count = 0;
        size=array.length;
        array = array1;
        for(int i = parent(array[size-1].getPos(),d);i>=0;i--) {
        		count+=this.heapifyDown(i);
        }
    		return count;
    }

    /**
     * public boolean isHeap()
     *
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or has size == 0.
     *   
     */
    public boolean isHeap() 
    {   
    		if(size==0)
    			return true;
	    	for(int i=1;i<this.size;i++) {
	    		if(this.array[i].getKey()<this.array[parent(i,d)].getKey())
	    			return false;
	    	}
	    	return true;
    }


 /**
     * public static int parent(i,d), child(i,k,d)
     * (2 methods)
     *
     * precondition: i >= 0, d >= 2, 1 <= k <= d
     *
     * The methods compute the index of the parent and the k-th child of 
     * vertex i in a complete D-ary tree stored in an array. 
     * Note that indices of arrays in Java start from 0.
     */
    public static int parent(int i, int d) {
    	return Math.floorDiv(i-1, d);
    	}
    
    public static int child (int i, int k, int d) { return d*i+k;}
    /**
    * public int Insert(DHeap_Item item)
    *
	* Inserts the given item to the heap.
	* Returns number of comparisons during the insertion.
	*
    * precondition: item != null
    *               isHeap()
    *               size < max_size
    * 
    * postcondition: isHeap()
    */
    public int Insert(DHeap_Item item) 
    { 
	    	this.array[size]=item;
	    	item.setPos(size);
	    int a=size;
	    size++;
	    return heapifyUp(a);
    }
    
    public int heapifyUp(int child) {
    	if (child==0) {
    		return 0;
    	}
    	int parent=parent(child,d);
    	if(this.array[child].getKey()<this.array[parent].getKey()) {// replace child with father
    		DHeap_Item temp=this.array[parent];
    		this.array[parent]=this.array[child];
    		this.array[parent].setPos(parent);
    		this.array[child]=temp;
    		this.array[child].setPos(child);
    		return 1+heapifyUp(parent);
    	}
    	return 1;
    		
    	}
    

 /**
    * public int Delete_Min()
    *
	* Deletes the minimum item in the heap.
	* Returns the number of comparisons made during the deletion.
    * 
	* precondition: size > 0
    *               isHeap()
    * 
    * postcondition: isHeap()
    */
    public int Delete_Min()
    {
    		this.array[0]=this.array[size-1];
    		this.array[0].setPos(0);
    		this.array[size-1]=null;
    		size--;
    		return heapifyDown(0);
    		
    }
    public int heapifyDown(int item) {
    	int [] tup = this.findMinChildIndex(item); //find the index of item's child with min key
    	if(this.size==1||tup[1]==-1) { 
    		return 0;
    	}
    	int counter=tup[0];
    	int child=tup[1];
    	if(this.array[child].getKey()<this.array[item].getKey()) {
    		DHeap_Item temp=this.array[item];
    		this.array[item]=this.array[child];
    		this.array[item].setPos(item);
    		this.array[child]=temp;
    		this.array[child].setPos(child);
    		if (child!=size-1)
    			return 1+counter+heapifyDown(child);
    		else
    			return 1+counter;
    	}
    	return 1+counter;
    	
    }
    	  
    
   public int [] findMinChildIndex(int item) {
    		int count=0;
    		if(child(item,1,d)>size-1) {
    			int [] r= {0,-1};
    			return r;
    		}
    		DHeap_Item min = array[child(item,1,d)];
    		int i=2;
    		while(child(item,i,d)<this.size && i<=d) {
    			if(array[child(item,i,d)].getKey() < min.getKey()) {
    				min=array[child(item,i,d)];
    			}
    			count++;
    			i++;
    		}
    		int [] r= {count,min.getPos()};
    		return r;
    }
    
    /**
     * public DHeap_Item Get_Min()
     *
	 * Returns the minimum item in the heap.
	 *
     * precondition: heapsize > 0
     *               isHeap()
     *		size > 0
     * 
     * postcondition: isHeap()
     */
    public DHeap_Item Get_Min()
    {
	return array[0];
    }
	
  /**
     * public int Decrease_Key(DHeap_Item item, int delta)
     *
	 * Decerases the key of the given item by delta.
	 * Returns number of comparisons made as a result of the decrease.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Decrease_Key(DHeap_Item item, int delta)
    {
    		if(item.getKey()>0)
    			item.setKey(item.getKey()-delta);
    		else {//avoiding the scenario of key-delta<MIN_VALUE
    			if(Math.abs(Integer.MIN_VALUE-item.getKey())<delta){
    				item.setKey(Integer.MIN_VALUE);
    			}
    			else {
    				item.setKey(item.getKey()-delta);
    			}
    		}
	    return heapifyUp(item.getPos());
    }
	
	  /**
     * public int Delete(DHeap_Item item)
     *
	 * Deletes the given item from the heap.
	 * Returns number of comparisons during the deletion.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Delete(DHeap_Item item)
    {
    		int count = 0;
    		count+=this.Decrease_Key(item, Integer.MAX_VALUE);
    		return count+this.Delete_Min();
    }
	
	/**
	* Sort the input array using heap-sort (build a heap, and 
	* perform n times: get-min, del-min).
	* Sorting should be done using the DHeap, name of the items is irrelevant.
	* 
	* Returns the number of comparisons performed.
	* 
	* postcondition: array1 is sorted 
	*/
	public static int DHeapSort(int[] array1, int d) {
		int count = 0;
		DHeap_Item[] array2=new DHeap_Item[array1.length];
		for(int i=0;i<array2.length;i++) {//convert array1 of type int to array2 of type DHeap_Item
			array2[i]=new DHeap_Item(Integer.toString(array1[i]),array1[i]);
			array2[i].setPos(i);
		}
		DHeap heap = new DHeap(d,array2.length);
		count+=heap.arrayToHeap(array2);//turn array2 to a d-heap
		for(int i=0;i<array2.length;i++) {//delete-min until heap is empty to sort array1
			array1[i]=heap.array[0].getKey();
			count+=heap.Delete_Min();
		}
		return count;
	}
	
	public String toString() {
		return Arrays.toString(this.array);
	}
}

