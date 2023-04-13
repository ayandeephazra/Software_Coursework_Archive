//////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: BallotBox
// Course: CS 300 Fall 2020
//
// Author: 
// Email: 
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons: Prof. Hobbes Legault - General Troubleshooting
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * The BallotBox Class defines methods that use the ballots ArrayList to implement
 * the functionality of an actual BallotBox. It has methods for getting the number
 * of Ballots - getNumBallots(), the Winner in a particular office - getWinner()
 * and submitting the Ballot - submit().
 * 
 * @author Ayan Deep Hazra
 *
 */
public class BallotBox1 {

  // ArrayList of the Ballots which have been cast
  private ArrayList<Ballot> ballots;

  /**
   * A no-argument constructor, which initializes the instance variable for the object.
   */
  public BallotBox1() {
    ballots = new ArrayList<Ballot>();
  }

  /**
   * returns the total number of unique Ballots in this BallotBox
   *
   * @return int Number of ballots in the ArrayList
   */
  public int getNumBallots() {
    return ballots.size();
  }

  /**
   * Records all of the existing Ballots’ votes for the given office and returns the Candidate with
   * the most votes. Make use of your other classes’ accessor methods here!
   * 
   * @param office The office to search the ballots for
   * @return Candidate with most votes
   */
  public Candidate getWinner(String office) {

    ArrayList<Candidate> candidatesRunning = Ballot.getCandidates(office);

    int[] countOfVotes = new int[candidatesRunning.size()];


    if (candidatesRunning.size() == 0)
      return null;

    for (int i = 0; i < candidatesRunning.size(); i++) {
      for (int j = 0; j < ballots.size(); j++) {
        Candidate c = ballots.get(j).getVote(office);
        if (c.toString().equals(candidatesRunning.get(i).toString()))
          countOfVotes[i]++;
      }
    }

    int highPos = 0;

    for (int i = 0; i < countOfVotes.length; i++) {
      if (countOfVotes[i] > countOfVotes[highPos])
        highPos = i;
    }

    if (countOfVotes[highPos] != 0) {
      return candidatesRunning.get(highPos);
    } else {
      return null;
    }

  }

  /**
   * Adds a Ballot to the BallotBox if and only if the Ballot has not already been added.
   * 
   * @param b
   */

  public void submit(Ballot b) {

    for (int i = 0; i < ballots.size(); i++) {

      if (!b.equals(ballots.get(i)))
        continue;

      else
        return;

    }

    ballots.add(b);
  }

}
