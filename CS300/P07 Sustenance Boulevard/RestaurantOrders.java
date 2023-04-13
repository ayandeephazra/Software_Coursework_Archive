/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: RestaurantOrders
// Course: CS 300 Fall 2020
//
// Author: Ayan Deep Hazra
// Email: ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons: Prof. Hobbes Legault - General Troubleshooting
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * The RestaurantOrders class implements the SortedListADT interface and defines 
 * fields and methods that seek to define a list of LinkedOrder objects. It has 2 
 * constructors, one no argument constructor and another which assigns the data field
 * called CAPACITY. It has accessor methods which access the data fields as well as
 * methods that display the contents of the whole list, both top to bottom and bottom
 * to top. Other than these, it also defines the methods implemented from the interface.
 * 
 * @author Ayan Deep Hazra
 */
public class RestaurantOrders implements SortedListADT<Order> {
  private LinkedOrder head; // front of the doubly-linked list
  private LinkedOrder tail; // end of the doubly-linked list
  private int size; // number of orders currently in the list
  private final int CAPACITY; // maximum number of orders for this list 

  /*
   * A no-argument constructor which creates a list with capacity 20.
   */
  public RestaurantOrders() {
    this.size = 0;
    this.CAPACITY = 20;
    this.head = null;
    this.tail = null;
  }


  /**
   * A one-argument constructor which creates a list with the provided positive capacity. Throws 
   * an IllegalArgumentException if the provided capacity is 0 or negative.
   * 
   * @param capacity The numbers of maximum orders to set for the list.
   */
  public RestaurantOrders(int capacity) {
    this.size = 0;
    this.CAPACITY = capacity;
    this.head = null;
    this.tail = null;
    if (capacity == 0 || capacity < 0) {
      throw new IllegalArgumentException("Error: capacity has an invalid value (<=0).");
    }
  }


  /**
   * Returns the capacity of this list.
   * 
   * @return the CAPACITY of this list
   */
  public int capacity() {
    return this.CAPACITY;
  }

  /**
   * Returns a String representation of the orders in this list from head to tail, space-separated
   * (see sample output, next section).
   * 
   * @return String with orders
   */
  public String readForward() {
    String str = "";
    // iteration from head of list
    for (LinkedOrder curr = this.head; curr != null; curr = curr.getNext()) {
      str += curr.getOrder().getDishes() + " ";
    }
    return "The list contains " + this.size + " order(s): [ " + str + "]";
  }

  /**
   * Returns a String representation of the orders in this list from tail to head, space-separated
   * (see sample output, next section).
   * 
   * @return String with orders
   */
  public String readBackward() {
    String str = "";
    // iteration from tail of list
    for (LinkedOrder curr = this.tail; curr != null; curr = curr.getPrevious()) {
      str += curr.getOrder().getDishes() + " ";
    }
    return "The list contains " + this.size + " order(s): [ " + str + "]";
  }


  /**
   * Removes all orders from this list.
   */
  @Override
  public void clear() {
    this.size = 0;
    this.head = null;
    this.tail = null;
  }



  /**
   * Returns the order at position index of this list, without removing it.
   * 
   * @param index Index to return order at.
   * @return Order the Order object that is returned
   */
  @Override
  public Order get(int index) {
    int count = 0;
    for (LinkedOrder current = this.head; current != null; current = current.getNext()) {
      
      // return if match else increment count if match is found in iteration
      if (count == index) {
        return current.getOrder();
      }
      count++;
    }
    return null;
  }

  public int indexOf(Order findOrder) {
    int count = 0;
    // return count if match is found in iteration
    for (LinkedOrder current = this.head; current != null; current = current.getNext()) {
      if (current.getOrder().equals(findOrder)) {
        return count;
      }
      count++;
    }
    return -1;
  }


  /**
   * Returns true if and only if the list is currently empty; false otherwise.
   * 
   * @return boolean true if list is empty, else false
   */
  @Override
  public boolean isEmpty() {
    if (this.head == null && this.tail == null) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Adds a new Order to this sorted list in the correct position if there is room in the list.
   * Throws an IllegalArgumentException if newOrder has the same timestamp as an existing order, a
   * negative timestamp, or is null.
   * 
   * @param newOrder This object is added to the list if space is there.
   */
  @Override
  public void placeOrder(Order newOrder) {
    
    // null newOrder
    if (newOrder == null) {
      throw new IllegalArgumentException("Error: newOrder is null.");
    }

    // full capacity
    if (this.size == this.CAPACITY) {
      return;
    }

    // empty Object, needs setting of both head and tail
    LinkedOrder order = new LinkedOrder(newOrder);
    if (this.head == null && this.tail == null) {
      this.head = order;
      this.tail = order;
      this.size++;
      return;
    }

    // match of previous element with this newOrder, cancelled
    if (this.head.getOrder().compareTo(newOrder) == 0) {
      // Error
      throw new IllegalArgumentException("Error: element is repeated.");
    } 
    // sort condition, if bigger then add at beginning
    else if (this.head.getOrder().compareTo(newOrder) > 0) {
      // Add at Beginning
      order.setNext(this.head);
      this.head.setPrevious(order);
      this.head = order;
      this.size++;
      return;
    }

    // match of previous element with this newOrder, cancelled
    if (this.tail.getOrder().compareTo(newOrder) == 0) {
      // Error
      throw new IllegalArgumentException("Error");
    }
    // sort condition, if smaller then add at end
    else if (this.tail.getOrder().compareTo(newOrder) < 0) {
      // Add at End
      order.setPrevious(this.tail);
      this.tail.setNext(order);
      this.tail = order;
      this.size++;
      return;
    }

    LinkedOrder current = head;

    while (current.getNext() != null) {
      if (current.getNext().getOrder().compareTo(order.getOrder()) == -1) {
        // skip to next
        current = current.getNext();
      } else if (current.getNext().getOrder().compareTo(order.getOrder()) == 0) {
        // Error
        throw new IllegalArgumentException("Error: Repeated value.");
      } else {
        break;
      }
    }
    // setting next for the order
    order.setNext(current.getNext());

    // only do if next is not null
    if (current.getNext() != null) {
      order.getNext().setPrevious(order);
    }
    // setting next and previous elements
    current.setNext(order);
    order.setPrevious(current);
    this.size++;
  }


  /**
   * Returns the number of orders currently in the list.
   * 
   * @param int the size of the list
   */
  @Override
  public int size() {
    return this.size;
  }


  /**
   * Removes and returns the order at the given index.
   * 
   * @param index The index to find and return Order from
   * @return Order the Order object that gets returned
   */
  @Override
  public Order removeOrder(int index) {
    // index out of bounds condition
    if(index>=size||index<0) {
      throw new IndexOutOfBoundsException("Error: Index out of bounds.");
    }

    // minimum occupancy of 1
    if (this.size == 1) {
      LinkedOrder current = this.head;
      this.head = null;
      this.tail = null;
      this.size--;
      return current.getOrder();
    }
    
    // at the start
    if (index == 0) {
      // start from front, delete and decrement size
      LinkedOrder current = this.head;
      this.head = this.head.getNext();
      this.head.getPrevious().setNext(null);
      this.head.setPrevious(null);
      this.size--;
      // temp case returning
      return current.getOrder();
    }
    // at the end
    else if (index == this.size - 1) {
      
      // start from back, delete and decrement size
      LinkedOrder current = this.tail;
      this.tail = this.tail.getPrevious();
      this.tail.getNext().setPrevious(null);
      this.tail.setNext(null);
      this.size--;
      // temp case returning
      return current.getOrder();
    }
    // middle case
    else {
      int count = 0;
      //iterate through start of list to end
      for (LinkedOrder current = this.head; this.head != this.tail; current = current.getNext()) {

        // match case, delete and decrement size
        if (count == index) {
          current.getPrevious().setNext(current.getNext());
          current.getNext().setPrevious(current.getPrevious());
          current.setPrevious(null);
          current.setNext(null);
          this.size--;
          // temp case returning
          return current.getOrder();
        }
        count++;
      }
    }
    // return null if nothing matches
    return null;
  }
}
