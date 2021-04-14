/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: Party
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

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * The Party Class defines several methods and variables that model the working a Party. It has a
 * getName() method that returns the name of the party. It has a getSize() method that returns the
 * number of candidates running from the party. And addCandidate() and getCandidates() to
 * respectively add and get Candidates.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class Party {

  // a String representing the Party’s name
  private String name;
  // an ArrayList of Candidates affiliated with that Party
  private ArrayList<Candidate> candidates;

  /**
   * A one-argument constructor, which initializes the instance variables for the object.
   * 
   * @param name The name of the candidate to initialize the object of the class with.
   */

  public Party(String name) {
    this.name = name;
    candidates = new ArrayList<Candidate>();
  }

  // Accessor methods for both of the object’s instance variables:

  /**
   * To get the String name associated with this object.
   * 
   * @return String the name of associated with the object.
   */
  public String getName() {
    return name;
  }

  /**
   * To get the number of candidates associated with this party.
   * 
   * @return int the size associated with the object.
   */
  public int getSize() {
    return candidates.size();
  }

  /**
   * Finds the candidate from the Party running for the given office. Throws a
   * NoSuchElementException if no Candidate is found.
   * 
   * @param office Checks who is running for this office from a Party object
   * @return Candidate returns the candidate who is running for this office and otherwise 
   *         it throws a NoSuchElementException
   */
  public Candidate getCandidate(String office) {

    for (int i = 0; i < getSize(); i++) {
      if (candidates.get(i).getOffice().equals(office)) {

        return candidates.get(i);
      }
    }

    throw new NoSuchElementException(
        "No such candidate was found " + "in this office or office inavlid.");

  }

  /**
   * Adds a Candidate to the Party. Throws an IllegalArgumentException if the provided
   * Candidate is running for the same office as another member of the Party.
   * 
   * @param c Candidate to check and add to the ArrayList
   */

  public void addCandidate(Candidate c) {

    String runningFor = c.getOffice();
    // error handling code
    try {
      Candidate checker = getCandidate(runningFor);
      throw new IllegalArgumentException(
          "Candidate running for same" + " office as another member");
    } catch (NoSuchElementException nsee) {
      candidates.add(c);
    }
  }

}
