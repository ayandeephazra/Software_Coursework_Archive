/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: Inbox
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
/**
 * This class implements the Inbox Object with different data fields and methods.
 * 
 * @author Ayan Deep Hazra
 */
public class Inbox {

  private MessageStack readMessageBox; // stack which stores read messages
  private MessageStack unreadMessageBox; // stack which stores unread messages
  
  /**
   * This no-argument constructor creates a new empty inbox and initializes its instance fields.
   * Both unreadMessageBox and readMessageBox stacks of this inbox must be initially empty.
   */
  public Inbox() {
    this.readMessageBox = new MessageStack();
    this.unreadMessageBox = new MessageStack();
  }

  /**
   * Reads the message at the top of the unreadMessageBox. Once read, the message must be 
   * moved from the unreadMessageBox to the readMessageBox. This method returns the string
   * representation of the message at the top of the unreadMessageBox, or "Nothing in
   * Unread" if the unreadMessageBox of this inbox is empty.
   * 
   * @return String Message that has been read from the Read stack.
   */
  public String readMessage() {
  
    // empty case
    if (this.unreadMessageBox.isEmpty())
      return "Nothing in Unread";

    // non-empty case
    Message popped;
    popped = this.unreadMessageBox.pop();

    this.readMessageBox.push(popped);
    return popped.toString();
  }

  /**
   * Returns the string representation of the message at the top of the readMessageBox. 
   * This method returns the string representation of the message at the top 
   * readMessageBox and "Nothing in Read" if the readMessageBox is empty.
   * 
   * @return Message at the top of the Read stack.
   */
  public String peekReadMessage() {
    Message peekRead;

    // try to catch an error
    try {
      peekRead = this.readMessageBox.peek();
    } catch (EmptyStackException ese) {
      return "Nothing in Read";
    }

    // if no error, then return formattes String of the Message Object
    return peekRead.toString();
  }

  /**
   * Marks all messages in the unread message box as read. The unread message box must be empty
   * after this method returns. Every message marked read must be moved to the read messages box.
   * This method returns the total number of messages marked as read.
   * 
   * @return int total number of messages marked as read.
   */
  public int markAllMessagesAsRead() {
    int count = 0;

    while (this.unreadMessageBox.size() != 0) {
      Message popped;

      // pop from Unread and push to Read stack
      popped = this.unreadMessageBox.pop();
      this.readMessageBox.push(popped);
      count++;
    }
    return count;
  }

  /**
   * Pushes a newMessage into the unread message box. newMessage represents a reference to the
   * received message. Note that this method can be invoked each time a new message will be 
   * received and pushed to the unreadMessageBox.
   * 
   * @param newMessage The Message to push into the Unread Stack.
   */
  public void receiveMessage(Message newMessage) {
   
    // simple push operation
    this.unreadMessageBox.push(newMessage);
  }

  /**
   * Removes permanently all the messages from the readMessageBox. This method returns the total
   * number of the removed messages.
   * 
   * @return number of removed messages.
   */
  public int emptyReadMessageBox() {
    int count = 0;

    while (this.readMessageBox.size() != 0) {
      this.readMessageBox.pop();
      count++;
    }

    return count;
  }

  /**
   * Gets the statistics of this In box Returns a String formatted as follows: "Unread (size1)" +
   * "\n" + "Read (size2)", where size1 and size2 represent the number of unread and read messages
   * respectively.
   * 
   * @return Formatted String which has statistics of the Stacks.
   */
  public String getStatistics() {
    
    String formattedStr = "";

    formattedStr = formattedStr + "Unread (" + this.unreadMessageBox.size() + ")\n";
    formattedStr = formattedStr + "Read (" + this.readMessageBox.size() + ")";

    return formattedStr;

  }

  /**
   * Traverses all the unread messages and return a list of their ID + " " + SUBJECT,
   * as a string. Every string representation of a message is provided in a new line.
   * This method returns a String representation of the contents of the unread message box.
   * 
   * @return Formatted String or The returned output has the following format:
   *         Unread(unreadMessageBox_size)\n + list of the messages in unreadMessageBox
   *         (ID + " " + SUBJECT) each in a line.
   */
  public String traverseUnreadMessages() {
 
    String formattedString = "";
    Iterator<Message> iter = unreadMessageBox.iterator();

    // enhanced for loop to go through the contents of the iterator
    for (Message currentMessage : unreadMessageBox) {
      if (iter.hasNext()) {
        currentMessage = iter.next();
        // adding to string
        formattedString += "" + currentMessage.getID() + " " + currentMessage.getSUBJECT() + "\n";
      }
    }

    return "Unread" + "(" + unreadMessageBox.size() + ")" + "\n" + formattedString;

  }

  /**
   * Traverses all the read messages and return a list of their string representations, ID + " " +
   * SUBJECT, each per new line, as a string. This method returns a String representation of the
   * contents of the read message box
   * 
   * @return The returned output has the following format: Read(readMessageBox_size)\n + list of
   *         the messages in readMessageBox (ID + " " + SUBJECT) each in a line.
   */
  public String traverseReadMessages() {


    String formattedString = "";
    Iterator<Message> iter = readMessageBox.iterator();

    // enhanced for loop to go through the contents of the iterator
    for (Message currentMessage : readMessageBox) {
      if (iter.hasNext()) {
        currentMessage = iter.next();
        // adding to string
        formattedString += "" + currentMessage.getID() + " " + currentMessage.getSUBJECT() + "\n";
      }
    }

    return "Read" + "(" + readMessageBox.size() + ")" + "\n" + formattedString;

  }

}
