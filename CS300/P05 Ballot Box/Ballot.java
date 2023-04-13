/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: Ballot
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
 * The Ballot class defines variables and methods relating to a Ballot including 
 * methods that add a Party to the Ballot called addParty(), get the Candidates
 * for a particular office called getCandidates(), a method to compare two Ballot 
 * objects called equals(), a vote() method that casts the vote to the Ballot and 
 * a getVode() method that returns a Candidate for the office 
 * provided.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class Ballot {

  // parties, an ArrayList containing the Parties for the election
  private static ArrayList<Party> parties = new ArrayList<Party>();
  // counter, an int value for generating unique ID values for each Ballot
  private static int counter = 1010;

  /**
   * Adds a Party to the class ArrayList If the class ArrayList already contains this Party, do
   * nothing.
   * 
   * @param p The party object to add to the parties ArrayList
   */

  public static void addParty(Party p) {
    boolean found = false;

    // if found, then don't add, else add
    for (int i = 0; i < parties.size(); i++) {
      if (parties.get(i).equals(p))
        found = true;
    }
    
    // adding case
    if (!found)
      parties.add(p);
  }

  /**
   * Creates and returns an ArrayList of all Candidates running for the given office. Should not
   * crash if a Party has no Candidate running for the given office.
   * 
   * @param office The office to search and maybe return in all Parties.
   * @return ArrayList<Candidates> an ArrayList of the candidates running for the given office.
   */

  public static ArrayList<Candidate> getCandidates(String office) {
    ArrayList<Candidate> candidatesForThisOffice = new ArrayList<Candidate>();

    // iterate through loop to find a match, with error handling
    for (int i = 0; i < parties.size(); i++) {
      try {
        candidatesForThisOffice.add(parties.get(i).getCandidate(office));
      } catch (NoSuchElementException nsee) {
        continue;
      }
    }

    return candidatesForThisOffice;
  }

  // an array of Candidates with the same number of elements as the constant
  // String array in the Candidate class. Each element of this array corresponds
  // to the
  // Ballot’s vote for that office, where a null entry means no vote has (yet)
  // been
  // cast for that office.
  private Candidate[] votes;

  // unique ID of ballot
  private final int ID;

  /**
   * A no-argument constructor, which initializes the votes array to the correct length 
   * and creates a unique ID number for the Ballot.
   */

  public Ballot() {
    votes = new Candidate[Candidate.OFFICE.length];
    ID = counter;
    counter++;
  }

  /**
   * Returns true if the argument is equal to this Ballot, false otherwise. Overrides 
   * the default Object method equals, and determines equality by comparing the unique 
   * ID values of two Ballots. If the provided Object o is not a Ballot, this should return 
   * false.
   * 
   * @param o the object to compare to this Ballot
   * @return true if the Ballots have the same ID, false otherwise
   */
  @Override

  public boolean equals(Object o) {

    // o is not an instance of Ballot straight up
    if (!(o instanceof Ballot))
      return false;

    // o is an instance of Ballot but ID doesn't match
    if (o instanceof Ballot) {

      Ballot b = (Ballot) o;
      if (b.ID != this.ID)
        return false;
    }

    // if both cases are false above, then o is an instance of Ballot and ID matches
    return true;
  }

  /**
   * Returns the Candidate that this Ballot has voted for, or null if there is no vote for that
   * office.
   * 
   * @param office
   * @return
   */

  public Candidate getVote(String office) {

    // return vote if cast, else null
    for (int i = 0; i < Candidate.OFFICE.length; i++) {
      if (Candidate.OFFICE[i].equals(office))
        return votes[i];
    }

    return null;
  }

  /**
   * Records the vote for the given Candidate at the appropriate index in the Ballot’s 
   * array. Can overwrite an existing vote.
   * 
   * @param c Candidate to enter in apt position in votes array.
   */

  public void vote(Candidate c) {

    String office = c.getOffice();

    // iterate through loop, and if office matches, cast vote (assign)
    // overwrites permitted
    for (int i = 0; i < Candidate.OFFICE.length; i++) {
      if (Candidate.OFFICE[i].equals(office))
        votes[i] = c;
    }

  }

}
