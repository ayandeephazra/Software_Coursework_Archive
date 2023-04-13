/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: MoveQueue
// Course: CS 300 Fall 2020
//
// Author: Ayan Deep Hazra
// Email: ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons: Prof. Hobbes Legault - General Troubleshooting
//          TASs in the TA Office Hours
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class defines the Queue that maintains an array of type BattleCharacter as well as its
 * size variable and a host of methods, that include constructors, mutators and accessors. 
 * 
 * @author Ayan Deep Hazra
 */
public class MoveQueue implements PriorityQueueADT<BattleCharacter> {

  private BattleCharacter[] data; // array-based max heap storing the data
  private int size; // size of this MoveQueue

  /**
   * Constructor that builds an empty priority queue with array size or the given capacity. Throws
   * IllegalArgumentException if capacity is zero or negative.
   * 
   * @param capacity The capacity to set as the number of array elements.
   */
  public MoveQueue(int capacity) {
    if (capacity <= 0) { 
      throw new IllegalArgumentException("Capacity is either 0 or negative.");
    } else {
      data = new BattleCharacter[capacity];
    }
  }

  /**
   * Constructor that builds an empty priority queue with array size 10.
   */
  public MoveQueue() {
    data = new BattleCharacter[10];
  }

  /**
   * Returns a String representation of the current contents of the MoveQueue in order from first
   * to last.
   * 
   * @author Michelle
   */
  @Override
  public String toString() {
    String s = ("[ ");
    for (int i = 0; i < size; i++) {
      s += (data[i].toString() + " | ");
    }
    s += ("]");
    return s;
  }
  
  /**
   * Returns a boolean value depending on whether the array is empty or not.
   * 
   * @return boolean if empty return true, else false.
   */
  @Override
  public boolean isEmpty() {
    return (this.size == 0);
  }

  /**
   * This method returns the size of array of BattleCharacters.
   * 
   * @return int the size of the array.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Adds an element if space allows.
   */
  @Override
  public void enqueue(BattleCharacter element) {
    if(element == null) {
      throw new IllegalArgumentException("Element is null.");
    }
    if(size==data.length) {
      throw new IllegalStateException("Queue is full.");
    }
    data[size] = element;
    // change size
    size++;
    this.percolateUp(size-1);
  }

  /**
   * Removes the highest priority element.
   * 
   * @return An instance of the deleted element of type BattleCharacter.
   */
  @Override
  public BattleCharacter dequeue() {
    if(this.isEmpty())
      throw new NoSuchElementException("Queue is Empty.");
    else {
    BattleCharacter ret = this.peekBest();
    data[0] = data[size-1];
    data[size-1] = null;
    // change size
    size--;
    this.percolateDown(0);
    return ret;
    }
  }

  /**
   * This method returns the highest priority element of the queue (index 0).
   */
  @Override
  public BattleCharacter peekBest() {
    if(this.isEmpty())
      throw new NoSuchElementException("Queue is Empty.");
    else {
      // return reference to element at 0 index
      return this.data[0];
    }
  }

  /*
   * This method clears all the array contents.
   */
  @Override
  public void clear() {
    // tempSize to record the size of the array
    // size variable keeps on changing, so we need a local copy to keep the temporary value
    int tempSize = this.size();
    // if this is not done, not all elements will be deleted in the end
    for(int i = 0; i<tempSize; i++) {
      this.dequeue();
    }
  }
  
  /**
   * Recursively propagates max-heap order violations up. Checks to see if the current node i
   * violates the max-heap order property by checking its parent. If it does, swap them and 
   * continue to ensure the heap condition is satisfied.
   * 
   * @param i index of the current node in this heap
   */
  protected void percolateUp(int i) {
    // PARENT NULL CASE
    if (data[(i - 1) / 2] == null) {
      BattleCharacter temp = data[i];
      data[i] = data[(i - 1) / 2];
      data[(i - 1) / 2] = temp;
      return;
    }
    // PARENT NOT NULL AND ALSO CHILD GREATER THAN PARENT CASE
    else if (data[(i - 1) / 2] != null && data[i].compareTo(data[(i - 1) / 2]) == 1) {
      BattleCharacter temp = data[i];
      data[i] = data[(i - 1) / 2];
      data[(i - 1) / 2] = temp;
      // repeat with parent
      this.percolateUp((i - 1) / 2);
    } 
    else {
      return;
    }
  }
 
  /**
  * Recursively propagates max-heap order violations down.
  * Checks to see if the current node i violates the max-heap order
  * property by checking its children. If it does, swap it with the optimal
  * child and continue to ensure the heap condition is met.
  * 
  * @param i index of the current node in this heap
  */
  protected void percolateDown(int i) {

    // check left index is valid and then check if element at the index is not null
    // do the same for right index
    // check right index is valid and then then check if element at the index is not null
    // if anything fails, the whole if branch is not taken and nothing is returned
    if ((2 * i + 1 < data.length) && data[2 * i + 1] != null && (2 * i + 2 < data.length)
        && data[2 * i + 2] != null) {

      // if just the left child is larger than the parent
      if (data[i].compareTo(data[2 * i + 1]) == -1 && data[i].compareTo(data[2 * i + 2]) == 1) {
        BattleCharacter temp = data[i];
        data[i] = data[2 * i + 1];
        data[2 * i + 1] = temp;
        // percolate on with the left child index
        percolateDown(2 * i + 1);
      } 
      // if just the right child is larger than the parent
      else if (data[i].compareTo(data[2 * i + 1]) == 1
          && data[i].compareTo(data[2 * i + 2]) == -1) {
        BattleCharacter temp = data[i];
        data[i] = data[2 * i + 2];
        data[2 * i + 2] = temp;
        // percolate on with the right child index
        percolateDown(2 * i + 2);
      } 
      // parent is smaller than both children
      else if (data[i].compareTo(data[2 * i + 1]) == -1
          && data[i].compareTo(data[2 * i + 2]) == -1) {

        // which to replace?
        // compare both
        // if left is smaller, replace parent with right and continue percolating with right
        if(data[2*i+1].compareTo(data[2*i+2])==-1) {
          BattleCharacter temp = data[2*i+2];
          data[2*i+2] = data[i];
          data[i] = temp;
          // percolate on with the right child index
          percolateDown(2*i+2);
        }
        // if right is smaller, replace parent with left and continue percolating with left
        else {
          BattleCharacter temp = data[2*i+1];
          data[2 * i + 1] = data[i];
          data[i] = temp;
          // percolate on with the left child index
          percolateDown(2 * i+1);
        }
   
      }
    }
    // just the parent node and a left node exists
    if ((2 * i + 1 < data.length) && data[2 * i + 1] != null) {
      // in this case, if parent is smaller than left node child, then swapping takes place
      if (data[i].compareTo(data[2 * i + 1]) == -1) {
        BattleCharacter temp = data[2 * i + 1];
        data[2 * i + 1] = data[i];
        data[i] = temp;
        // percolate on with the left child index
        percolateDown(2 * i + 1);
      }
    }

  }
  
  /**
  * Eliminates all heap order violations from the heap data array.
  */
  protected void heapify() {

    // start at level one, first node (index 1)
    for(int i =1; i<size; i++) {
      if(data[i].compareTo(data[(i-1)/2])==1)
      {
        int j = i;
        
        // compare with parent, replace, and iterate
        while(data[j].compareTo(data[(j-1)/2])==1) {
          BattleCharacter temp = data[j];
          data[j] = data[(j-1)/2];
          data[(j-1)/2] = temp;
          j = (j-1)/2;
        }
      }
    }

    
  }
  
  /**
   * This method finds a matching character in the array and replaces it, or if the character is
   * dead, the method removes it by finding its index in the array.
   * 
   * @param updateChara The updated character to replace or remove.
   */
  public void updateCharacter(BattleCharacter updateChara){
    /*
    * TODO:
    * (1) Find matching character in the MoveQueue
    * (Rely on the BattleCharacter.equals() method to find the match with updated).
    * (2) Replace it with the updated version of the character.
    * If character is dead, remove it entirely.
    * (3) Make sure the structure is maintained.
    * Note: You can also use the code below to eliminate holes (null references)
    * in the array. Then, call heapify() method to eliminate all the ordering violations.
    */

    int gapIndex = -1; // change this to the index of the character that died
    for (int i = 0; i < size; i++) {
      if (this.data[i].equals(updateChara)) {
        if (this.data[i].isAlive()) {
          this.data[i] = updateChara;
          this.heapify();
        }
        else if (!this.data[i].isAlive())
          gapIndex = i;
      }
    }

    // with the found gapIndex, replace elements at that index and beyond
    if (gapIndex != -1) {
      data[gapIndex] = data[size-1]; // gapIndex is the index of the dead character
      data[size - 1] = null;
      size--;
      this.heapify();
    }
  }

}
