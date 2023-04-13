//////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: BallotBox
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

/**
 * The BallotBox Class defines methods that use the ballots ArrayList to implement
 * the functionality of an actual BallotBox. It has methods for getting the number
 * of Ballots - getNumBallots(), the Winner in a particular office - getWinner()
 * and submitting the Ballot - submit().
 * 
 * @author Ayan Deep Hazra
 *
 */
public class BallotBox {

  // ArrayList of the Ballots which have been cast
  private ArrayList<Ballot> ballots;

  /**
   * A no-argument constructor, which initializes the instance variable for the object.
   */
  public BallotBox() {
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
   * Records all of the existing Ballots’ votes for the given office and returns the 
   * Candidate with the most votes. Make use of your other classes’ accessor methods here!
   * 
   * @param office The office to search the ballots for
   * @return Candidate with most votes
   */
  public Candidate getWinner(String office) {

    ArrayList<Candidate> candidatesOffice = Ballot.getCandidates(office);
    // int array of the same length to record the number of votes per candidate
    // each index in int array corresponds to number of votes candidate in the same
    // index in ArrayList has
    int[] voteCounter = new int[candidatesOffice.size()];

    // no candidates, return null
    if (candidatesOffice.size() == 0)
      return null;

    for (int i = 0; i < candidatesOffice.size(); i++) {
      for (int j = 0; j < ballots.size(); j++) {
        Candidate temp = ballots.get(j).getVote(office);
        if (temp.toString().equals(candidatesOffice.get(i).toString()))
          voteCounter[i]++;
      }
    }

    int max_pos = 0;

    for (int i = 0; i < voteCounter.length; i++) {
      if (voteCounter[i] > voteCounter[max_pos])
        max_pos = i;
    }

    // if the number of votes received by the most popular candidate is 0
    // that means nobody was selected by any voter for that office
    // -> return null in that case
    if (voteCounter[max_pos] != 0) {
      return candidatesOffice.get(max_pos);
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
      // move on to the next if ballot doesn't match
      if (!b.equals(ballots.get(i)))
        continue;
      // if it does match, this person is trying to double vote and you return
      else
        return;
    }

    // if we are not returned from the for loop, then no double voting took place
    // and
    // ballot can very much be added to the ballots ArrayList
    ballots.add(b);
  }

}
