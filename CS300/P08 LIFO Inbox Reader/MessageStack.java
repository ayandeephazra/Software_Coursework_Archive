/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: MessageStack
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

import java.util.EmptyStackException;
import java.util.Iterator;
import java.lang.Iterable;

/**
 * This class implements both the StackADT interface and the Iterable Interface with
 * the non generic data type of Message.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class MessageStack implements StackADT<Message>, Iterable<Message> {

  // top of the stack
  private LinkedNode <Message> top;
  // size of the message objects stored in stack
  private int size;
  
  /**
   * Add an element to this stack
   * 
   * @param element an element to be added
   * @throws java.lang.IllegalArgumentException with a descriptive error message if the input
   *         element is null
   */
  @Override
  public void push(Message element) {

    if(element == null) {
      throw new IllegalArgumentException("Element entered as parameter is null.");
    }
    LinkedNode<Message> temp = this.top;
    LinkedNode<Message> newMessage = new LinkedNode<Message>(element, temp);
    this.top = newMessage;

    this.size++;
  }

  /**
   * Remove the element on the top of this stack and return it
   * 
   * @return the element removed from the top of the stack
   * @throws java.util.EmptyStackException without error message if the stack is empty
   */
  @Override
  public Message pop() {
    if (this.isEmpty()) {
      throw new EmptyStackException();
    }
    LinkedNode<Message> temp = top;

    this.top = this.top.getNext();
    this.size--;
    return temp.getData();
  }


  /**
   * Get the element on the top of this stack
   * 
   * @return the element on the stack top
   * @throws java.util.EmptyStackException without error message if the stack is empty
   */
  @Override
  public Message peek() {
    if (this.isEmpty()) {
      throw new EmptyStackException();
    } else {
      return top.getData();
    }
  }

  /**
   * Check whether this stack is empty or not
   * 
   * @return true if this stack contains no elements, otherwise false
   */
  @Override
  public boolean isEmpty() {
    if (this.top == null && this.size == 0)
      return true;
    else
      return false;
  }

  /**
   * Get the number of elements in this stack
   * 
   * @return the size of the stack
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * MessageStack’s iterator() method must return a new MessageStackIterator that starts 
   * at the top of the stack of Messages.
   * 
   * @return MessageStackIterator that starts at the top of the stack of Messages.
   */
  @Override
  public Iterator<Message> iterator() {
    return new MessageStackIterator(this.top);
  }
  
}
