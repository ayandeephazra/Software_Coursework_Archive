/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: RestaurantOrdersTester
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
 * This class implements unit test methods to check the correctness of LinkedOrders and
 * RestaurantOrders classes defined in the CS300 Fall 2020 - P07 Restaurant Orders programming
 * assignment.
 *
 *@author Ayan Deep Hazra
 */
public class RestaurantOrdersTester {

  /**
   * This method should test and make use of at least the LinkedOrders constructor, an accessor
   * (getter) method, and a mutator (setter) method defined in the LinkedOrders class.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */

  public static boolean testLinkedOrders() {
    Order order1 = new Order("Burger", 1);
    Order order2 = new Order("Beer", 2);
    Order invalidOrder = new Order("Milk", -19);

    // test one argument constructor
    LinkedOrder linkedOrder1 = new LinkedOrder(order1);
    // checks if linkedOrder1 has the same order as order1 or it is null
    if (linkedOrder1.getOrder() == null || !linkedOrder1.getOrder().equals(order1)) {
      return false;
    }

    // test three argument constructor
    LinkedOrder linkedOrder2 = new LinkedOrder(order2, linkedOrder1, null);
    // checks if linkedOrder2 has the same order as order2 or it is null
    if (linkedOrder2.getOrder() == null || !linkedOrder2.getOrder().equals(order2)) {
      return false;
    }
    // checks the previous order of linkedOrder2
    if (!linkedOrder2.getPrevious().getOrder().equals(order1)) {
      return false;
    }

    if (linkedOrder2.getNext() != null) {
      return false;
    }

    // test invalid constructor
    try {
      LinkedOrder linkedOrder3 = new LinkedOrder(invalidOrder);
      return false;
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    // test setNext methods
    Order order4 = new Order("Milk", 5);
    LinkedOrder linkedOrder4 = new LinkedOrder(order4, linkedOrder1, null);
    linkedOrder1.setNext(linkedOrder4);

    // checks if order1 has order4 as next
    if (!linkedOrder1.getNext().equals(linkedOrder4)) {
      return false;
    }

    // test getPrevious method
    linkedOrder1.setPrevious(linkedOrder2);

    // checks if order1 has order 2 as previous
    if (!linkedOrder1.getPrevious().equals(linkedOrder2)) {
      return false;
    }



    return true;
  }

  /**
   * This method checks for the correctness of both RestaurantOrders constructors and the instance
   * method isEmpty() when called on an empty restaurant orders object.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRestaurantOrdersConstructorIsEmpty() {
    RestaurantOrders orders = new RestaurantOrders();

    // checks the no argument constructor
    if (orders.capacity() != 20) {
      return false;
    }
    // checks isEmpty method
    if (!orders.isEmpty()) {
      return false;
    }

    return true;
  }

  /**
   * This method checks for the correctness of the RestaurantOrders(int) constructor when passed a
   * negative int value for the capacity.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRestaurantOrdersConstructorBadInput() {
    try {
      RestaurantOrders orders = new RestaurantOrders(-7);
      return false;
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

    return true;
  }


  /**
   * This method checks for the correctness of the RestaurantOrders.placeOrder()() method when it 
   * is passed bad inputs. This method must consider at least the test scenarios provided in the
   * detailed description of these javadocs. (1) Try adding a null to the list; (2) Try adding an
   * order which carries a negative timestamp. (3) Try adding an order with an existing timestamp 
   * to the list.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRestaurantOrdersAddBadInput() {
    RestaurantOrders orders = new RestaurantOrders();

    // scenario 1 adding a null to the list
    try {
      orders.placeOrder(null);
      return false;
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }
    // scenario 2 adding an order which carries a negative timestamp
    try {
      orders.placeOrder(new Order("cheese", -12));
      return false;
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

    // scenario 3 adding an order with an existing timestamp to the list.
    try {
      orders.placeOrder(new Order("cheese", 12));
      orders.placeOrder(new Order("water", 12));
      return false;
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }

    return true;
  }

  /**
   * This method checks for the correctness of the RestaurantOrders.placeOrder()() considering at
   * least the test scenarios provided in the detailed description of these javadocs. (1) Try 
   * adding an order to an empty list; (2) Try adding an order which is expected to be 
   * added at the head of a non-empty restaurant list; (3) Try 
   * adding an order which is expected to be added at the end
   * of a non-empty restaurant list; (4) Try adding an order which is expected to be added at the
   * middle of a non-empty restaurant list. For each of those scenarios, make sure that the size 
   * of the list is appropriately updated after a call without errors of the add() method, 
   * and that the contents of the list is as expected whatever if list is read in the forward 
   * or backward directions. You can also check the correctness of RestaurantOrders.get(int),
   * RestaurantOrders.indexOf(Order), and RestaurantOrders.size() in this test method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRestaurantOrdersAdd() {
    // scenario 1 adding an order to an empty list
    RestaurantOrders orders = new RestaurantOrders(25);

    Order order1 = new Order("Burger", 2);
    orders.placeOrder(order1);
    // check the size
    if (orders.size() != 1) {
      return false;
    }

    // check the content
    if (!orders.readForward().equals("The list contains 1 order(s): [ Burger ]")) {
      return false;
    }

    // scenario 2 adding an order to the head of a non-empty list
    Order order2 = new Order("CheeseCurds", 1);
    orders.placeOrder(order2);
    // check the size
    if (orders.size() != 2) {
      return false;
    }
    // check the content
    if (!orders.readForward().equals("The list contains 2 order(s): [ CheeseCurds Burger ]")) {
      return false;
    }

    // scenario 3 adding an order to the end of a non-empty
    Order order3 = new Order("Sandwich", 4);
    orders.placeOrder(order3);
    // check the size
    if (orders.size() != 3) {
      return false;
    }
    // check the content
    if (!orders.readForward().equals("The list contains 3 order(s): [ CheeseCurds Burger "
        + "Sandwich ]")) {
      return false;
    }
    // scenario 4 adding an order to the middle of a non-empty
    Order order4 = new Order("Pizza", 3);
    orders.placeOrder(order4);
    // check the size
    if (orders.size() != 4) {
      return false;
    }
    // check the content
    if (!orders.readForward().equals("The list contains 4 order(s): [ CheeseCurds Burger "
        + "Pizza Sandwich ]")) {
      return false;
    }

    return true;
  }

  /**
   * This method checks for the correctness of the RestaurantOrders.removeOrder() considering at
   * least the test scenarios provided in the detailed description of these javadocs. (1) Try
   * removing an order from an empty list or pass a negative index to 
   * RestaurantOrders.removeOrder() method; (2) Try removing an order (at position index 0) 
   * from a list which contains only one order; (3) Try to remove an order (position index 0)
   * from a list which contains at least 2 orders; (4) Try to remove an order from the middle 
   * of a non-empty list containing at least 3 orders; (5) Try to remove the order at the
   * end of a list containing at least two orders. For each of those scenarios,
   * make sure that the size of the list is appropriately updated after a
   * call without errors of the add() method, and that the contents of the list is as expected
   * whatever if list is read in the forward or backward directions.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testRestaurantOrdersRemove() {

    // scenario 1 removing an order from an empty list or pass a negative index
    RestaurantOrders orders = new RestaurantOrders(25);
    try {
      orders.removeOrder(0);
      return false;
    } catch (IndexOutOfBoundsException iae) {
      System.out.println(iae.getMessage());
    }

    // scenario 2 removing an order (at position index 0) from a list which contains only one 
    // order
    Order order1 = new Order("Fries", 2);
    orders.placeOrder(order1);

    orders.removeOrder(0);

    // check the size
    if (orders.size() != 0) {
      return false;
    }

    // scenario 3 removing an order from (position index 0) from a list which contains at least 2
    // orders
    Order order2 = new Order("Lasagna", 4);
    orders.placeOrder(order1);
    orders.placeOrder(order2);

    Order order3 = new Order("Cheese", 1);
    orders.placeOrder(order3);
    orders.removeOrder(0);

    // check the size
    if (orders.size() != 2) {
      return false;
    }

    // check the content
    if (!orders.readForward().equals("The list contains 2 order(s): [ Fries Lasagna ]")) {
      return false;
    }
    // scenario 4 removing an order from the middle of a non-empty list containing at least 3 
    // orders
    orders.placeOrder(order3);
    orders.removeOrder(1);

    // check the size
    if (orders.size() != 2) {
      return false;
    }

    // check the content
    if (!orders.readForward().equals("The list contains 2 order(s): [ Cheese Lasagna ]")) {
      return false;
    }

    // scenario 5 removing an order from end of a list containing at least two orders
    orders.removeOrder(1);

    // check the size
    if (orders.size() != 1) {
      return false;
    }

    // check the content
    if (!orders.readForward().equals("The list contains 1 order(s): [ Cheese ]")) {
      return false;
    }

    return true;
  }


  /**
   * This method calls all the test methods defined and implemented in your RestaurantOrdersTester
   * class.
   * 
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {

    System.out.println("testLinkedOrders(): " + testLinkedOrders());
    System.out.println(
        "testRestaurantOrdersConstructorIsEmpty(): " + testRestaurantOrdersConstructorIsEmpty());
    System.out.println(
        "testRestaurantOrdersConstructorBadInput(): "
        + testRestaurantOrdersConstructorBadInput());
    System.out.println("testRestaurantOrdersAddBadInput(): " + testRestaurantOrdersAddBadInput());
    System.out.println("testRestaurantOrdersAdd(): " + testRestaurantOrdersAdd());
    System.out.println("testRestaurantOrdersRemove(): " + testRestaurantOrdersRemove());
    
    // checking if all passed
    if(testLinkedOrders() && testRestaurantOrdersConstructorIsEmpty() 
        && testRestaurantOrdersConstructorBadInput() && testRestaurantOrdersAddBadInput()
        && testRestaurantOrdersAdd() && testRestaurantOrdersRemove())
      return true;
    else
      return false;
  }

  /**
   * Driver method defined in this RestaurantOrdersTester class
   * 
   * @param args input arguments if any.
   */
  public static void main(String[] args) {

    System.out.println("runAllTests(): " + runAllTests());

  }
}
