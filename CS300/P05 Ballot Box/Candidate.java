/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: Candidate
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
 * The Candidate Class, is the first of many that models an election. It models a Candidate 
 * and has methods that return the name - getName() and office - getOffice() of the candidate
 * as well as a toString() method that displays the contents in a formatted string. 
 * This toString() method is written with an Override tag to override the Object class'
 * toString() method.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class Candidate {

  /*
   * Initialize this to contain at least three (3) different office titles of your choice.
   * You may use President, Vice President, Secretary if you like, but you may also be 
   * more (or less) creative.
   */

  protected static final String[] OFFICE = {"President", "Vice President", "Secretary"};

  private String name;
  private String office;

  /**
   * A two-argument constructor, which initializes the instance variables for the object 
   * Checks that the office is valid and throws an IllegalArgumentException if not
   * 
   * @param name   The name of the candidate to initialize the object of the class with
   * @param office position of to initialize the object of the class with
   */

  public Candidate(String name, String office) {
    this.name = name;

    for (int i = 0; i < OFFICE.length; i++) {
      if (office.equals(OFFICE[i])) {
        this.office = office;
        return;
      }
    }

    throw new IllegalArgumentException("Office Entered is not valid");
  }

  // Accessor methods for both of the objectâ€™s instance variables:

  /**
   * To get the String name associated with this object.
   */
  public String getName() {
    return name;
  }

  /**
   * To get the String office associated with this object.
   */
  public String getOffice() {
    return office;
  }

  /**
   * Returns a String representation of this Candidate as NAME (OFFICE). For example, Mouna Kacem
   * (President) or â€œHobbes LeGault (Court Jester)
   * 
   * @return String formatted string in the format NAME (OFFICE)
   */

  @Override
  public String toString() {
    return getName() + " (" + getOffice() + ")";
  }

}
