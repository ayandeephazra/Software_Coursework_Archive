/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: BattleSystemTester
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
 * This class defines 4 test methods that test the different methods in BattleCharacter and
 * MoveQueue. All the defined test methods are executed in the main, where they return either true
 * or false depending on whether the implementation is correct or not.
 * 
 * @author Ayan Deep Hazra
 */
public class BattleSystemTester {
  
  /**
   * Calls the tester methods defined in the BattleSystemTester Class.
   * 
   * @param args Input arguments
   */
  public static void main(String[] args) {
    System.out.println("testCompareToBattleCharacter:" + testCompareToBattleCharacter());
    System.out.println("testPeekBestAndClearMoveQueue:" + testPeekBestAndClearMoveQueue());
    System.out.println("testEnqueueMoveQueue:" + testEnqueueMoveQueue());
    System.out.println("testDequeueMoveQueue:" + testDequeueMoveQueue());
  }
  
  /**
   * Checks the correctness of the compareTo operation implemented in the BattleCharacter class.
   * 
   * @return true if the functionality is all correct, else false.
   */
  public static boolean testCompareToBattleCharacter() {
    BattleCharacter.resetIDGenerator();
    
    /* SCENARIO I: Basic Scenario */
    // declaring characters
    BattleCharacter character1 = new BattleCharacter("Steve", new int[] {100, 20, 45, 55, 5});
    BattleCharacter character2 = new BattleCharacter("Alex", new int[] {100, 20, 45, 55, 5});
    BattleCharacter character3 = new BattleCharacter("Notch", new int[] {50, 10, 25, 35, 500});
    BattleCharacter character4 =
        new BattleCharacter("Herobrine", new int[] {500, 50, 250, 100, 1000});
    
    // 1==1 in speed
    // even though the speed is the same, ID is different, thus lower ID gets returned
    if (character1.compareTo(character2) != 1)
      return false;
    // 2<3 in speed
    if (character2.compareTo(character3) != -1)
      return false;
    // 3>2 in speed
    if (character3.compareTo(character2) != 1)
      return false;
    // 3<4 in speed
    if (character3.compareTo(character4) != -1)
      return false;
    // 4>3 in speed
    if (character4.compareTo(character3) != 1)
      return false;
    
    /* SCENARIO II: Comparing self Scenario */
    // compareTo should return 0 when an object is compared to itself
    BattleCharacter character5 =
        new BattleCharacter("Enderman", new int[] {40, 5, 240, 170, 300});
    BattleCharacter character6 =
        new BattleCharacter("Skeleton", new int[] {30, 5, 210, 160, 400});
    BattleCharacter character7 =
        new BattleCharacter("Wither", new int[] {15, 50, 150, 150, 500});
    
    if(character5.compareTo(character5)!=0)
      return false;
    if(character6.compareTo(character6)!=0)
      return false;
    if(character7.compareTo(character7)!=0)
      return false; 
    
    /* SCENARIO III: compareTo returns 0 Scenario */
    // 0 case, both ID and speed are the same.
    // will have to reset ID to get this
    BattleCharacter.resetIDGenerator();
    BattleCharacter character8 = new BattleCharacter("Steve2", new int[] {100, 20, 45, 55, 5});
    
    // character 8 is just a deep copy of 1, but has the same ID due to reset generator
    // 1==5 in both speed and ID thus
    if (character1.compareTo(character8) != 0)
      return false;
    return true;
  }
  
  /**
   * Checks the correctness of the peekBest() and clear() operation implemented in the MoveQueue
   * class.
   * 
   * @return true if the functionality is all correct, else false.
   */
  public static boolean testPeekBestAndClearMoveQueue() {
    // reset
    BattleCharacter.resetIDGenerator();
    BattleCharacter character1 = new BattleCharacter("Ed", new int[] {10, 10, 40, 5, 5});
    BattleCharacter character2 = new BattleCharacter("Edd", new int[] {30, 60, 5, 5, 50});
    BattleCharacter character3 = new BattleCharacter("Eddy", new int[] {50, 80, 65, 35, 300});

    // queue declaration default, capacity 10
    MoveQueue queue = new MoveQueue();

    /* SCENARIO I: Basic Scenario, elements added in ascending order of Speed */
    // adding 1, fastest
    queue.enqueue(character1);
    if (queue.peekBest() != character1)
      return false;

    // adding 2, fastest
    queue.enqueue(character2);
    if (queue.peekBest() != character2)
      return false;

    // adding 3, fastest
    queue.enqueue(character3);
    if (queue.peekBest() != character3)
      return false;
    
    // size should be 3
    if(queue.size()!=3)
      return false;

    // clear operation
    queue.clear();

    // size should be 0
    if(queue.size()!=0)
      return false;
    
    BattleCharacter character4 = new BattleCharacter("Tom", new int[] {10, 10, 5, 15, 10});
    BattleCharacter character5 = new BattleCharacter("Jerry", new int[] {40, 90, 10, 5, 5});
    BattleCharacter character6 = new BattleCharacter("Spike", new int[] {70, 70, 55, 10, 20});
    
    /* SCENARIO II: andom Order of adding (by Speed) Scenario */
    // adding 4, fastest yet since queue is empty
    queue.enqueue(character4);
    if (queue.peekBest() != character4)
      return false;

    // adding 5, slower than 4
    queue.enqueue(character5);
    if (queue.peekBest() != character4)
      return false;

    // adding 6, fastest yet
    queue.enqueue(character6);
    if (queue.peekBest() != character6)
      return false;

    // size should be 3
    if(queue.size()!=3)
      return false;
    
    /* SCENARIO III: peekBest() on an empty array scenario */
    // clearing array
    queue.clear();
    // size should be 0
    if (queue.size() != 0)
      return false;

    try {
      queue.peekBest();
    }
    catch(NoSuchElementException nsee) {
      System.out.println(nsee.getMessage());
    }

    return true;
  }

  /**
   * Checks the correctness of the enqueue operation implemented in the MoveQueue class.
   * 
   * @return true if the functionality is all correct, else false.
   */
  public static boolean testEnqueueMoveQueue() {
    BattleCharacter.resetIDGenerator();
    // queue declaration
    MoveQueue queue = new MoveQueue();
    // character declaration
    BattleCharacter character1 =
        new BattleCharacter("Big Chungus", new int[] {10, 20, 30, 1000, 100});
    BattleCharacter character2 = new BattleCharacter("Mario", new int[] {20, 200, 300, 10, 1000});
    BattleCharacter character3 = new BattleCharacter("Luigi", new int[] {50, 300, 400, 5, 300});

    /* SCENARIO I: Basic scenario */
    if(queue.size()!=0)
      return false;
    
    // enqueue 1 and size check
    queue.enqueue(character1);
    if(queue.size()!=1)
      return false;
    if (!queue.toString().equals("[ Big Chungus(1, 100) | ]"))
      return false;
    if(queue.peekBest()!=character1)
      return false;
    
    // enqueue 2 and size check
    queue.enqueue(character2);
    if(queue.size()!=2)
      return false;
    if (!queue.toString().equals("[ Mario(2, 1000) | Big Chungus(1, 100) | ]"))
      return false;
    if(queue.peekBest()!=character2)
      return false;
    
    // enqueue 3 and size check
    queue.enqueue(character3);
    if(queue.size()!=3)
      return false;
    if (!queue.toString().equals("[ Mario(2, 1000) | Big Chungus(1, 100) | Luigi(3, 300) | ]"))
      return false;
    if(queue.peekBest()!=character2)
      return false;

    /*
     * SCENARIO II: Queue is full scenario, capacity is just 1 so adding two elements throws an
     * error
     */
    MoveQueue queue2 = new MoveQueue(1);
    // first element added
    queue2.enqueue(character1);
    if (!queue2.toString().equals("[ Big Chungus(1, 100) | ]"))
      return false;
    // trying to add element 2
    try {
      queue2.enqueue(character2);
    } catch (IllegalStateException ise) {
      System.out.println(ise.getMessage());
    }

    /* SCENARIO III :Illegal argument Scenario, added element is a null value */
    MoveQueue queue3 = new MoveQueue(5);

    // adding null as an element
    try {
      queue3.enqueue(null);
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

    return true;
  }

  /**
   * Checks the correctness of the dequeue operation implemented in the MoveQueue class.
   * 
   * @return true if the functionality is all correct, else false.
   */
  public static boolean testDequeueMoveQueue() {
    
    BattleCharacter.resetIDGenerator();
    // queue declaration
    MoveQueue queue = new MoveQueue();
    // character declaration
    BattleCharacter character1 = new BattleCharacter("Big Chungus",new int[]{10, 20, 30, 0, 100});
    BattleCharacter character2 = new BattleCharacter("Mario", new int[] {20, 200, 300, 10, 1000});
    BattleCharacter character3 = new BattleCharacter("Luigi", new int[] {50, 300, 400, 5, 300});

    /*
     * SCENARIO I: Basic Scenario - Elements ordered as per heapified structure from start in terms
     * of Speed
     */
    queue.enqueue(character2);
    queue.enqueue(character3);
    queue.enqueue(character1);

    // fastest of the three
    if(queue.dequeue()!=character2)
      return false;
    
    if(queue.size()!=2)
      return false;
    
    // second fastest of the three
    if(queue.dequeue()!=character3)
      return false;
    
    if(queue.size()!=1)
      return false;
    
    // slowest of the three
    if(queue.dequeue()!=character1)
      return false;
    
    if(queue.size()!=0)
      return false;
        
    /* SCENARIO II: Random Ordering Scenario, not heapified structure from the get go */
    // adding elements
    queue.enqueue(character1);
    queue.enqueue(character2);
    queue.enqueue(character3);

    // fastest of the three
    if(queue.dequeue()!=character2)
      return false;
    
    if(queue.size()!=2)
      return false;
    
    // second fastest of the three
    if(queue.dequeue()!=character3)
      return false;
    
    if(queue.size()!=1)
      return false;
    
    // slowest of the three
    if(queue.dequeue()!=character1)
      return false;
    
    if(queue.size()!=0)
      return false;
    
    /* SCENARIO III: Empty Queue Scenario */
    
    MoveQueue queue2 = new MoveQueue();
    
    try {
      queue2.dequeue();
    }
    catch(NoSuchElementException nsee) {
      System.out.println(nsee.getMessage());
    }
    
    return true;
  }
  
}

