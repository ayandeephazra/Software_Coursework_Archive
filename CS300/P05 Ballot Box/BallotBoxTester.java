/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: BallotBoxTester
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
 * The BallotBoxTester Class defines and tests test methods relating to the Candidate, Party, 
 * Ballot and BallotBox classes.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class BallotBoxTester {

  /**
   * Tests the Candidate Class and returns true if the functionality works as expected or false if
   * it doesn't.
   * 
   * @return true or false depending on the correctness of the Candidate Class functionality
   */

  public static boolean testCandidate() {

    // Scenario 1 - All good, valid office, valid name
    try {
      Candidate c1 = new Candidate("Bucky Badger", "President");
      Candidate c2 = new Candidate("Pounce The Panther", "Vice President");
      Candidate c3 = new Candidate("Clash", "Secretary");

      // c1
      if (!c1.getName().equals("Bucky Badger")) {
        return false;
      }

      if (!c1.getOffice().equals("President")) {
        return false;
      }

      if (!c1.toString().equals("Bucky Badger (President)")) {
        return false;
      }

      // c2
      if (!c2.getName().equals("Pounce The Panther")) {
        return false;
      }

      if (!c2.getOffice().equals("Vice President")) {
        return false;
      }

      if (!c2.toString().equals("Pounce The Panther (Vice President)")) {
        return false;
      }

      // c3
      if (!c3.getName().equals("Clash")) {
        return false;
      }

      if (!c3.getOffice().equals("Secretary")) {
        return false;
      }

      if (!c3.toString().equals("Clash (Secretary)")) {
        return false;
      }

    } catch (Exception e) {
      System.out.println("No exception should be thrown");
    }

    // Scenario 2 - Invalid Office (Queens can't have power in a representative
    // democracy)
    try {
      Candidate c2 = new Candidate("Liz", "Queen");

      // results in exception being thrown
    } catch (IllegalArgumentException nse) {
      System.out.println("No such Position being contested");
    } catch (Exception e) {
      System.out.println("No exception should be thrown");
    }

    return true;
  }

  /**
   * Tests the Party Class and returns true if the functionality works as expected or false if it
   * doesn't.
   * 
   * @return true or false depending on the correctness of the Party Class functionality
   */

  public static boolean testParty() {

    // Scenario 1 - All good inputs
    try {
      Party p1 = new Party("ABC");

      Candidate c1 = new Candidate("Felix Kjellberg", "President");
      Candidate c2 = new Candidate("Sean McLaughlin", "Vice President");
      Candidate c3 = new Candidate("Sive Morten", "Secretary");

      p1.addCandidate(c1);
      p1.addCandidate(c2);
      p1.addCandidate(c3);

      if (!p1.getName().equals("ABC")) {
        return false;
      }

      if (p1.getSize() != 3) {
        return false;
      }

      // might throw NoSuchElementException
      try {
        // checking if the c1 reference matches
        if (p1.getCandidate("President") != c1) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

      // might throw NoSuchElementException
      try {
        // checking if the c2 reference matches
        if (p1.getCandidate("Vice President") != c2) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

      // might throw NoSuchElementException
      try {
        // checking if the c3 reference matches
        if (p1.getCandidate("Secretary") != c3) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

    } catch (NoSuchElementException nsee) {
      System.out.println("No such Element was found");
    }

    catch (Exception e) {
      System.out.println("No exception should be thrown");
    }

    // Scenario 2 - Random corner cases, exception handling test case
    try {
      Party p1 = new Party("Pokemon");

      Candidate c1 = new Candidate("Ash", "President");
      Candidate c2 = new Candidate("Misty", "Vice President");
      Candidate c3 = new Candidate("Brock", "Secretary");

      p1.addCandidate(c1);
      p1.addCandidate(c2);
      p1.addCandidate(c3);

      Party p2 = new Party("Team Rocket");

      /*
       * p1 checking
       */
      if (!p1.getName().equals("Pokemon")) {
        return false;
      }

      if (p1.getSize() != 3) {
        return false;
      }

      // checking if the c1 reference matches
      if (p1.getCandidate("President") != c1) {
        return false;
      }

      // checking if the c2 reference matches
      if (p1.getCandidate("Vice President") != c2) {
        return false;
      }

      // checking if the c3 reference matches
      if (p1.getCandidate("Secretary") != c3) {
        return false;
      }

      /*
       * p2 checking
       */
      if (!p2.getName().equals("Team Rocket")) {
        return false;
      }

      if (p2.getSize() != 0) {
        return false;
      }

      // All the statements below would throw a NoSuchElementException

      // will throw NoSuchElementException
      try {
        // checking if the c1 reference matches
        if (p2.getCandidate("President") != null) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

      // will throw NoSuchElementException
      try {
        // checking if the c2 reference matches
        if (p2.getCandidate("Vice President") != null) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

      // will throw NoSuchElementException
      try {
        // checking if the c3 reference matches
        if (p2.getCandidate("Secretary") != null) {
          return false;
        }
      } catch (NoSuchElementException nsee) {
        System.out.println("No such Element was found");
      }

    } catch (NoSuchElementException nsee) {
      System.out.println("No such Element was found");
    }

    catch (Exception e) {
      System.out.println("No exception should be thrown");
    }

    return true;
  }

  /**
   * Tests the Ballot Class and returns true if the functionality works as expected or false if it
   * doesn't.
   * 
   * @return true or false depending on the correctness of the Ballot Class functionality
   */

  public static boolean testBallot() {

    // Scenario 1 - Parties may or may not have a candidate for all positions
    {
      try {

        // party 1
        Party party1 = new Party("PQR");
        Candidate c1 = new Candidate("Felix Kjellberg", "President");
        Candidate c2 = new Candidate("Sean McLaughlin", "Vice President");
        Candidate c3 = new Candidate("Sive Morten", "Secretary");
        party1.addCandidate(c1);
        party1.addCandidate(c2);
        party1.addCandidate(c3);

        // party 2
        Party party2 = new Party("STU");
        Candidate c4 = new Candidate("Ed", "President");
        Candidate c5 = new Candidate("Eddy", "Secretary");
        party2.addCandidate(c4);
        party2.addCandidate(c5);

        // party 3
        Party party3 = new Party("VWX");
        Candidate c6 = new Candidate("Captain America", "Vice President");
        Candidate c7 = new Candidate("Iron Man", "Secretary");
        party3.addCandidate(c6);
        party3.addCandidate(c7);

        // ballots
        Ballot b1 = new Ballot();
        Ballot b2 = new Ballot();
        Ballot b3 = new Ballot();
        Ballot b4 = new Ballot();
        Ballot b5 = new Ballot();

        // static method assignment of party objects
        Ballot.addParty(party1);
        Ballot.addParty(party2);
        Ballot.addParty(party3);

        // testing getCandidates() for Vice President office
        Candidate sec1 = new Candidate("Sean McLaughlin", "Vice President");
        // Candidate sec2 = new Candidate("Edd", "Vice President");
        Candidate sec3 = new Candidate("Captain America", "Vice President");

        ArrayList<Candidate> candidatesTestList = new ArrayList<Candidate>();
        // expected output order -> party1 then party 3
        candidatesTestList.add(sec1);
        // candidatesTestList.add(sec2);
        candidatesTestList.add(sec3);

        if (!Ballot.getCandidates("Vice President").toString()
            .equals(candidatesTestList.toString()))
          return false;

        // voting time

        // for President, 3 votes for party 2 and 2 votes for party 1

        b1.vote(c4);
        b2.vote(c4);
        b3.vote(c1);
        b4.vote(c1);
        b5.vote(c4);

        // for Vice President, all vote for party 1

        b1.vote(c2);
        b2.vote(c2);
        b3.vote(c2);
        b4.vote(c2);
        b5.vote(c2);

        // for Secretary, 3 votes for party 3 and 2 votes for party 2

        b1.vote(c7);
        b2.vote(c7);
        b3.vote(c5);
        b4.vote(c5);
        b5.vote(c7);

        // As we can see, b4 and b3 have voted exactly identical

        // using getVote() to verify President, Vice President and Secretary roles
        // vote must be to same candidate object in both offices

        if (!b3.getVote("President").toString().equals(b4.getVote("President").toString()))
          return false;

        if (!b3.getVote("Vice President").toString()
            .equals(b4.getVote("Vice President").toString()))
          return false;

        if (!b3.getVote("Secretary").toString().equals(b4.getVote("Secretary").toString()))
          return false;

        // using getVote() to verify President role, we know both voters voted different
        // vote must be to different candidate object in both offices

        if (!b1.getVote("President").toString().equals(b2.getVote("President").toString()))
          return false;

      } catch (NoSuchElementException nse) {
        System.out.println("No such Element was found");
      }

      catch (Exception e) {
        System.out.println("No exception should be thrown");
      }
    }

    return true;
  }

  /**
   * Tests the BallotBox Class and returns true if the functionality works as expected or false if
   * it doesn't.
   * 
   * @return true or false depending on the correctness of the BallotBox Class functionality
   */

  public static boolean testBallotBox() {

    // Scenario 1 - All parties have a candidate for all positions
    try {

      // party 1
      Party p1 = new Party("ABC");
      Candidate c1 = new Candidate("Harry", "President");
      Candidate c2 = new Candidate("Hermione", "Vice President");
      Candidate c3 = new Candidate("Ron", "Secretary");
      p1.addCandidate(c1);
      p1.addCandidate(c2);
      p1.addCandidate(c3);

      // party 2
      Party p2 = new Party("DEF");
      Candidate c4 = new Candidate("Ed", "President");
      Candidate c5 = new Candidate("Edd", "Vice President");
      Candidate c6 = new Candidate("Eddy", "Secretary");
      p2.addCandidate(c4);
      p2.addCandidate(c5);
      p2.addCandidate(c6);

      // ballots
      Ballot b1 = new Ballot();
      Ballot b2 = new Ballot();
      Ballot b3 = new Ballot();

      // static method assignment of party objects
      Ballot.addParty(p1);
      Ballot.addParty(p2);

      // voting time

      // for President, vote for different parties
      b1.vote(c1);
      b2.vote(c4);
      b3.vote(c4);

      // for Vice President, all 3 vote for party 1
      b1.vote(c2);
      b2.vote(c2);
      b3.vote(c2);

      // for Secretary, all 3 vote for party 2

      b1.vote(c3);
      b2.vote(c3);
      b3.vote(c3);

      BallotBox bb = new BallotBox();

      bb.submit(b1);
      bb.submit(b2);
      bb.submit(b3);

      Candidate temp = bb.getWinner("Secretary");

      if (!temp.toString().equals(c3.toString()))
        return false;


    } catch (NoSuchElementException nse) {
      System.out.println("No such Element was found");
      return false;
    }

    catch (Exception e) {
      System.out.println("No exception should be thrown");
      return false;
    }

    return true;
  }


  /**
   * Tests the test methods defined in the class and returns true if they work as required and false
   * otherwise.
   * 
   * @param args
   */

  public static void main(String[] args) {

    System.out.println("testCandidate: " + testCandidate());

    System.out.println("testParty: " + testParty());

    System.out.println("testBallot: " + testBallot());

    System.out.println("testBallotBox: " + testBallotBox());

  }

}
