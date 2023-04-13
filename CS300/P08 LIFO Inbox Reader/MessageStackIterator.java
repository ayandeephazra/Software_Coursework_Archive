/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: MessageStackIterator
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

import java.util.NoSuchElementException;
import java.util.Iterator;
/**
 * This class implements the Iterator Interface with the non generic data type Message
 * and defines the nest() and the hasNext() methods apart from the constructor.
 * 
 * @author Ayan Deep Hazra
 */
public class MessageStackIterator implements Iterator<Message> {

  private LinkedNode < Message > next;
  
  public MessageStackIterator(LinkedNode < Message > newTopNode) {
    next = newTopNode;
  }
 
  @Override
  public boolean hasNext() {
    if(next!=null)
      return true;
    else
      return false;
  }
  @Override
  public Message next() {

    if(this.hasNext()) {
      // set a temp node to next, advance next and return temp's data
      LinkedNode<Message>temp = next;
      next = next.getNext();
      return temp.getData();
    }
    else {
      throw new NoSuchElementException("There is no next element in this stack.");
    }
  }

}
