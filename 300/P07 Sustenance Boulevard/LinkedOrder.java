/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: LinkedOrder
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
 * The LinkedOrder class defines fields and methods that describe the working of a linked list
 * that implements the Order class' instances. There are two types of constructors, one
 * with 1 argument that initializes ORDER and one wtih 3 arguments that initializes all 3 
 * fields. There are also accessor methods that get each of these fields' reference and 
 * mutator methods that change the references to the previous and next nodes.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class LinkedOrder {

  private final Order ORDER; // data field of this LinkedOrder
  private LinkedOrder previous; // reference to the Order before this one
  private LinkedOrder next; // reference to the Order after this one


  /**
   * A one-argument constructor which sets previous and next to null. Throws an
   * IllegalArgumentException if order's time stamp is negative.
   * 
   * @param order The Order object passed in as parameter
   */
  public LinkedOrder (Order order) {
    this.previous = null;
    this.next = null;
    if(order.compareTo((new Order("rand", 0)))==-1)
      throw new IllegalArgumentException("The time stamp of the order might be negative");
    else
      this.ORDER = order;
  }

  /**
   * A three-argument constructor which sets all fields as specified. Throws an
   * IllegalArgumentException if order's time stamp is negative.
   * 
   * @param order The LinkedOrder object to set as the prev object
   * @param prev The LinkedOrder object to set as the prev object
   * @param next The LinkedOrder object to set as the next object
   */
  public LinkedOrder(Order order, LinkedOrder prev, LinkedOrder next) {
    this.setPrevious(prev);
    this.setNext(next);
    if (order.compareTo((new Order("rand", 0))) == -1) {
      throw new IllegalArgumentException("The time stamp of the order might be negative");
    } else {
      this.ORDER = order;
    }
  }
  
  /**
   * Returns the instance's ORDER object.
   * 
   * @return Order object in this instance
   */
  public Order getOrder() {
    return this.ORDER;
  }
  
  /**
   * Returns a reference to the previous LinkedOrder attached to this one.
   * 
   * @return Order object that's previous to this instance.
   */
  public LinkedOrder getPrevious() {
    return this.previous;
  }
  
  /**
   * Returns a reference to the next LinkedOrder attached to this one.
   * 
   * @return Order object that's next to this instance.
   */
  public LinkedOrder getNext() {
    return this.next;
  }

  /**
   * Sets the previous LinkedOrder attached to this one.

   * @param previous This object is set as the previous to this current 
   *                 instance of LinkedOrder
   */
 
  public void setPrevious(LinkedOrder previous) {
    this.previous = previous;
  }
  
  /**
   * Sets the next LinkedOrder attached to this one.

   * @param next This object is set as the next to this current 
   *                 instance of LinkedOrder 
   */
  public void setNext(LinkedOrder next) {
    this.next = next;
  }


}



















