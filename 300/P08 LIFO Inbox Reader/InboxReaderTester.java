/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: InboxReaderTester
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
import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of the implementation of the
 * MessageStack, Inbox, and MessageStackIterator classes defined in the CS300 Fall 2020 - P08 LIFO
 * Inbox Reader programming assignment.
 * 
 * @author Ayan Deep Hazra
 */
public class InboxReaderTester {

  /**
   * Calls your tester methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {

    // test runInboxReaderTestSuite()
    System.out.println(
        "runInboxReaderTestSuite(): " + runInboxReaderTestSuite());
    
    // test testMessageStackIterator()
    System.out.print("testMessageStackIterator(): " + testMessageStackIterator());
 
  }

  // add the runInboxReaderTestSuite() method and your additional tester methods

  /**
   * This test method should run all the test methods implemented in your InboxReaderTester 
   * class. It should return false if any of its component tests fail, 5 and true if they
   * all succeed.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean runInboxReaderTestSuite() {

    // Constructor isEmpty(), push and peek tester
    if (!testStackConstructorIsEmptyPushPeek()) {
      System.out.println(
          "testStackConstructorIsEmptyPushPeek(): " + testStackConstructorIsEmptyPushPeek());
      return false;
    } else {
      System.out.println(
          "testStackConstructorIsEmptyPushPeek(): " + testStackConstructorIsEmptyPushPeek());
    }

    // test stack pop
    if (!testStackPop()) {
      System.out.println("testStackPop(): " + testStackPop());
      return false;
    } else {
      System.out.println("testStackPop(): " + testStackPop());
    }

    // test Inbox Read Message
    if (!testInboxReadMessage()) {
      System.out.println("testInboxReadMessage(): " + testInboxReadMessage());
      return false;
    } else {
      System.out.println("testInboxReadMessage(): " + testInboxReadMessage());
    }

    // test Inbox Receive Message
    if (!testInboxReceiveMessage()) {
      System.out.println("testInboxReceiveMessage(): " + testInboxReceiveMessage());
      return false;
    } else {
      System.out.println("testInboxReceiveMessage(): " + testInboxReceiveMessage());
    }

    // mark all messages as read tester
    if (!testInboxMarkAllMessagesAsRead()) {
      System.out.println("testInboxMarkAllMessagesAsRead(): " + testInboxMarkAllMessagesAsRead());
      return false;
    } else {
      System.out.println("testInboxMarkAllMessagesAsRead(): " + testInboxMarkAllMessagesAsRead());
    }



    return true;
  }

  /**
   * Checks for the correctness of the constructor of the MessageStack, MessageStack.push(),
   * MessageStack.peek(), MessageStack.isEmpty(),and MessageStack.size() methods. This method must
   * consider at least the test scenarios provided in the detailed description of these javadocs.
   * (1) Create a new MessageStack object.The new created stack must be empty and its size must be
   * zero. (2) You can consider calling peek method on the empty stack. An EmptyStackException is
   * expected to be thrown by the peek method call.(3)Then, push a Message onto the stack and then
   * use peek to verify that the correct item is at the top of the stack. Make sure also to check
   * that isEmpty() must return false and the size of the stack is one. The peek() method call
   * should not make any change to the contents of the stack. (4) You can further consider pushing
   * other elements onto the stack. Then, each call of peek() method should return the correct
   * Message object that should be at the top of the stack.After pushing a new message to the stack
   * double check that the size of the stack was incremented by one.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testStackConstructorIsEmptyPushPeek() {
    
    MessageStack msgStack = new MessageStack();
    Message.resetIdGenerator();
    // testing size() and isEmpty() to be 0 and false respectively
    if (msgStack.size() != 0)
      return false;
    if (!msgStack.isEmpty())
      return false;
    // peek() method, must throw exception
    try {
      msgStack.peek();
    } catch (EmptyStackException ese) {
      System.out.println("Stack is empty at this stage.");
    }
    
    // push() method
    Message msg1 = new Message("Physics", "Black Holes are Awesome!");
    msgStack.push(msg1);
    if(!msgStack.peek().getSUBJECT().equals("Physics"))
      return false;
    if(!msgStack.peek().getTEXT().equals("Black Holes are Awesome!"))
      return false;
    if (msgStack.isEmpty())
      return false;
    if (msgStack.size() != 1)
      return false;
    
    // second push
    Message msg2 = new Message("Mathematics",
        "Time Complexity is a topic is Discrete Math.");
    msgStack.push(msg2);
    if(!msgStack.peek().getSUBJECT().equals("Mathematics"))
      return false;
    if(!msgStack.peek().getTEXT().equals("Time Complexity is a topic is Discrete Math."))
      return false;
    if (msgStack.isEmpty())
      return false;
    if (msgStack.size() != 2)
      return false;
    
    // third push
    Message msg3 = new Message("Comp Sci", "Java is not a dynamically typed language.");
    msgStack.push(msg3);
    if(!msgStack.peek().getSUBJECT().equals("Comp Sci"))
      return false;
    if(!msgStack.peek().getTEXT().equals("Java is not a dynamically typed language."))
      return false;
    if (msgStack.isEmpty())
      return false;
    if (msgStack.size() != 3)
      return false;
    
    return true;
  } //


  /**
   * Checks for the correctness of MessageStack.pop(). It calls MessageStack.push() and
   * MessageStack.peek() methods. This method must consider at least the test scenarios provided in
   * the detailed description of these javadocs. (1) Try to create a new empty MessageStack. Then,
   * try calling pop method on the empty stack.An EmptyStackException is expected to be thrown as a
   * result. (2) Try to push a message to the stack. Then, try to call the pop method on the stack
   * which contains only one element. Make sure that the pop() operation returned the expected
   * message, and that the stack is empty and its size is zero.(3) Then, try to push at least three
   * messages, then call pop method on the stack which contains 3 elements, the element at the top
   * of the stack must be returned and removed from the stack and its size must be decremented by
   * one. You can further keep popping the elements of the stack one by one until it becomes empty
   * and check each time that the pop operation performs appropriately.This test method must return
   * false if it detects at least one defect.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testStackPop() {
    MessageStack msgStack = new MessageStack();
    Message.resetIdGenerator();
    // peek() method, must throw exception
    try {
      msgStack.peek();
    } catch (EmptyStackException ese) {
      System.out.println("Stack is empty at this stage.");
    }
    try {
      msgStack.pop();
    } catch (EmptyStackException ese) {
      System.out.println("Stack is empty at this stage.");
    }

    // push() method
    Message msg1 = new Message("Physics", "Black Holes are Awesome!");
    
    msgStack.push(msg1);

    // peeking
    if (!msgStack.peek().getSUBJECT().equals("Physics"))
      return false;
    if (!msgStack.peek().getTEXT().equals("Black Holes are Awesome!"))
      return false;
    // temporary object to hold popped object
    Message temp = msgStack.pop();
    if (!temp.getSUBJECT().equals("Physics"))
      return false;
    if (!temp.getTEXT().equals("Black Holes are Awesome!"))
      return false;
    if (msgStack.size() != 0)
      return false;

    // declaring 3 messages
    Message msg2 = new Message("Biochem", "Mitochondria is the powehouse of the cell.");
    msgStack.push(msg2);
    if (msgStack.size() != 1)
      return false;
    Message msg3 = new Message("Philosophy", "What is life?");
    msgStack.push(msg3);
    if (msgStack.size() != 2)
      return false;
    Message msg4 = new Message("Economics", "S is inversely proportional to D.");
    msgStack.push(msg4);
    if (msgStack.size() != 3)
      return false;

    // peeking
    if (!msgStack.peek().getSUBJECT().equals("Economics"))
      return false;
    if (!msgStack.peek().getTEXT().equals("S is inversely proportional to D."))
      return false;
    // popping 1
    Message temp2 = msgStack.pop();
    if (!temp2.getSUBJECT().equals("Economics"))
      return false;
    if (!temp2.getTEXT().equals("S is inversely proportional to D."))
      return false;
    if (msgStack.size() != 2)
      return false;

    // peeking
    if (!msgStack.peek().getSUBJECT().equals("Philosophy"))
      return false;
    if (!msgStack.peek().getTEXT().equals("What is life?"))
      return false;
    // popping
    Message temp3 = msgStack.pop();
    if (!temp3.getSUBJECT().equals("Philosophy"))
      return false;
    if (!temp3.getTEXT().equals("What is life?"))
      return false;
    if (msgStack.size() != 1)
      return false;

    // peeking
    if (!msgStack.peek().getSUBJECT().equals("Biochem"))
      return false;
    if (!msgStack.peek().getTEXT().equals("Mitochondria is the powehouse of the cell."))
      return false;
    // popping
    Message temp4 = msgStack.pop();
    if (!temp4.getSUBJECT().equals("Biochem"))
      return false;
    if (!temp4.getTEXT().equals("Mitochondria is the powehouse of the cell."))
      return false;
    if (msgStack.size() != 0)
      return false;

    return true;
  }

  /**
   * Checks for the correctness of the Inbox.ReadMessage() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testInboxReadMessage() {
    // Define your own test scenarios to check the correctness of Inbox.ReadMessage()
    // Your test method must return false if it detects at least one defect
    // Vary your test scenarios. Make sure to consider at least two test scenarios:
    // (1) when Inbox.unreadMessageBox is empty
    // (2) when Inbox.unreadMessageBox is not empty. You have to make sure the read message
    // has been popped off the Inbox.unreadMessageBox and pushed into the Inbox.readMessageBox
    // You can rely on Inbox.peekReadMessage() and Inbox.getStatistics() to check the method
    // behavior was as expected.
    
    Inbox newInbox = new Inbox();
    Message.resetIdGenerator();
    
    // Empty Inbox testing
    // peek should give nothing
    if(!newInbox.peekReadMessage().equals("Nothing in Read"))
      return false;
    // statistics should give 0 in both
    if(!newInbox.getStatistics().equals("Unread (0)\nRead (0)"))
      return false;
    // nothing should be there in Unread Inbox
    if(!newInbox.readMessage().equals("Nothing in Unread"))
      return false;
    
    Message msg1 = new Message("Urban Planning", "Cul de sacs are plentiful in suburbs.");
    Message msg2 = new Message("Astronomy", "Jupiter has the most moons in the Solar System.");
    Message msg3 = new Message("Statistics", "Mean of two numbers is the halfway point.");
    
    //pushing 3 new messages in
    newInbox.receiveMessage(msg1);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg3);
    
    if (!newInbox.getStatistics().equals("Unread (3)\nRead (0)"))
      return false;
    // peeking read stack, nothing is there in it
    if (!newInbox.peekReadMessage().equals("Nothing in Read"))
      return false;

    // PUSHING ONE TO READ STACK
    if (!newInbox.readMessage()
        .equals("[3] Statistics: Mean of two numbers is the halfway point."))
      return false;

    if (!newInbox.getStatistics().equals("Unread (2)\nRead (1)"))
      return false;
    // peeking read stack, 1 thing is there in it
    if (!newInbox.peekReadMessage()
        .equals("[3] Statistics: Mean of two numbers is the halfway point."))
      return false;

    // PUSHING ANOTHER ONE TO READ STACK
    if (!newInbox.readMessage()
        .equals("[2] Astronomy: Jupiter has the most moons in the Solar System."))
      return false;

    if (!newInbox.getStatistics().equals("Unread (1)\nRead (2)"))
      return false;
    // peeking read stack, 1 thing is there in it
    if (!newInbox.peekReadMessage()
        .equals("[2] Astronomy: Jupiter has the most moons in the Solar System."))
      return false;

    // PUSHING LAST ONE TO READ STACK
    if (!newInbox.readMessage()
        .equals("[1] Urban Planning: Cul de sacs are plentiful in suburbs."))
      return false;

    if (!newInbox.getStatistics().equals("Unread (0)\nRead (3)"))
      return false;
    // peeking read stack, 1 thing is there in it
    if (!newInbox.peekReadMessage()
        .equals("[1] Urban Planning: Cul de sacs are plentiful in suburbs."))
      return false;
    
    return true;
  }

  /**
   * Checks for the correctness of the Inbox.ReceiveMessage() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testInboxReceiveMessage() {
    // Define your own test scenarios to check the correctness of Inbox.receiveMessage()
    // Your test method must return false if it detects at least one defect
    
    Inbox newInbox = new Inbox();
    Message.resetIdGenerator();
    
    // declaring messages
    Message msg1 = new Message("History", "MLK was inspired by Gandhi.");
    Message msg2 = new Message("Data Science", "Python and R are important to Data Science.");
    Message msg3 = new Message("Social Science",
        "Strauss-Howe Theory talks about periodic changes in population.");
    
    // no messages as of yet, so both are 0
    if (!newInbox.getStatistics().equals("Unread (0)\nRead (0)"))
      return false;

    newInbox.receiveMessage(msg1);

    // 1 message as of yet, so unread is 1
    if (!newInbox.getStatistics().equals("Unread (1)\nRead (0)"))
      return false;

    newInbox.receiveMessage(msg1);

    // 2 messages as of yet, so unread is 2
    if (!newInbox.getStatistics().equals("Unread (2)\nRead (0)"))
      return false;

    newInbox.receiveMessage(msg2);

    // 3 messages as of yet, so unread is 3
    if (!newInbox.getStatistics().equals("Unread (3)\nRead (0)"))
      return false;

    newInbox.receiveMessage(msg3);

    // 4 messages as of yet, so unread is 4
    if (!newInbox.getStatistics().equals("Unread (4)\nRead (0)"))
      return false;

    newInbox.receiveMessage(msg3);

    // 5 messages as of yet, so unread is 5
    if (!newInbox.getStatistics().equals("Unread (5)\nRead (0)"))
      return false;

    newInbox.readMessage();
    // one message is read so unread count is down to 4
    
    newInbox.receiveMessage(msg2);

    // 5 messages as of yet, so unread is 5 and read is 1 as we know
    if (!newInbox.getStatistics().equals("Unread (5)\nRead (1)"))
      return false;
    
    newInbox.readMessage();
    newInbox.readMessage();
    newInbox.readMessage();
    // three messages are read, so unread count is down to 2
    
    // 2 messages as of yet, so unread is 2 and read is 4 as we know
    if (!newInbox.getStatistics().equals("Unread (2)\nRead (4)"))
      return false;

    return true;
  }

  /**
   * Checks for the correctness of the Inbox.markAllMessagesAsRead() method
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testInboxMarkAllMessagesAsRead() {
    // Define your own test scenarios to check the correctness of Inbox.markAllMessagesAsRead()
    // Your test method must return false if it detects at least one defect
    
    Inbox newInbox = new Inbox();
    Message.resetIdGenerator();
    
    // declaring messages
    Message msg1 = new Message("Geography",
        "An isthmus is a narrow strip of" + " land between two bodies of water.");
    Message msg2 = new Message("Culinary Arts", "Lasagna is hard to make.");
    Message msg3 = new Message("Theatre", "Shakespeare wrote many plays.");
    Message msg4 = new Message("Linguistics",
        "Languages having similiar characteristics are said to belong to a language family.");
    Message msg5 = new Message("Cosmology", "The Universe began at the Big Bang.");

    // no messages added yet to any of the two stacks
    if (!newInbox.getStatistics().equals("Unread (0)\nRead (0)"))
      return false;
    
    // adding 5 messages
    newInbox.receiveMessage(msg1);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg5);

    // all messages are unread, nothing is read
    if (!newInbox.getStatistics().equals("Unread (5)\nRead (0)"))
      return false;

    // READ ALL
    if (newInbox.markAllMessagesAsRead() != 5)
      return false;

    // all messages are read, nothing is unread
    if (!newInbox.getStatistics().equals("Unread (0)\nRead (5)"))
      return false;

    // adding 25 messages
    newInbox.receiveMessage(msg1);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg5);
    newInbox.receiveMessage(msg1);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg1);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg5);
    newInbox.receiveMessage(msg4);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg3);
    newInbox.receiveMessage(msg2);
    newInbox.receiveMessage(msg5);

    // 25 messages are unread, 5 are read
    if (!newInbox.getStatistics().equals("Unread (25)\nRead (5)"))
      return false;

    // reading 25 messages
    if (newInbox.markAllMessagesAsRead() != 25)
      return false;

    // all messages are read, nothing is unread
    if (!newInbox.getStatistics().equals("Unread (0)\nRead (30)"))
      return false;

    return true;
  }

  /**
   * Checks for the correctness of MessageStackIterator.hasNext() and MessageStackIterator.next()
   * methods. You can rely on the constructor of the LinkedNode class which takes two input
   * parameters (setting both data and next instance fields) to create a chain of linked nodes (at
   * least 3 linked nodes) which carry messages as data fields. Then, create a new
   * MessageStackIterator() and pass it the head of the chain of linked nodes that you created. We
   * recommend that you consider at least the following test scenarios in this tester method. (1)
   * Try to call next() on the iterator. The first call of next() must return the message at the
   * head of your chain of linked nodes. (2) Try to call hasNext() on your iterator,it must return
   * true. (3) The second call of next() must return the message which has been initially at index
   * 1 of your chain of linked nodes. (4) The third call of next() on your iterator must return 
   * the message initially at index 2 of your chain of linked nodes. (4) If you defined a chain 
   * of 3 linked nodes in this scenario, hasNext() should return false, and the fourth call of 
   * next() on the iterator must throw a NoSuchElementException.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise.
   */
  public static boolean testMessageStackIterator() {
    // resetting Id Generator
    Message.resetIdGenerator();
    
    // declaring messages
    Message msg1 = new Message("Business", "Stocks are crashing down today.");
    Message msg2 = new Message("Actuarial Science", "Risk is an important parameter in finance.");
    Message msg3 = new Message("Art History", "Ancient Humans used red, white and black paints.");
    
    LinkedNode <Message> linked3 = new LinkedNode<Message> (msg3);
    LinkedNode <Message> linked2 = new LinkedNode<Message> (msg2, linked3);
    LinkedNode <Message> linked1 = new LinkedNode<Message> (msg1, linked2);

    MessageStackIterator msgStackIterator1 = new MessageStackIterator(linked1);

    // first next() should return the first message object
    if (!msgStackIterator1.next().toString()
        .equals("[1] Business: Stocks are crashing down today."))
      return false;

    // hasNext() should return true
    if (!msgStackIterator1.hasNext())
      return false;

    // second next() should return the message object at Index 1
    if (!msgStackIterator1.next().toString()
        .equals("[2] Actuarial Science: Risk is an important parameter in finance."))
      return false;

    // hasNext() should return true
    if (!msgStackIterator1.hasNext())
      return false;

    // third next() should return the message object at Index 2
    if (!msgStackIterator1.next().toString()
        .equals("[3] Art History: Ancient Humans used red, white and black paints."))
      return false;

    // hasNext() should return false
    if (msgStackIterator1.hasNext())
      return false;
    
    // catching a NoSuchElementException
    try {
      msgStackIterator1.next();
    }
    catch(NoSuchElementException nsee) {
      System.out.println("No Such Element exists in the LinkedNode at this Index.");
    }

    return true;
  }

}
