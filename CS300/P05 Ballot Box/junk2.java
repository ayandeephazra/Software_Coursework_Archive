import java.util.ArrayList;
import java.util.NoSuchElementException;




//    Candidate c4 = new Candidate("Serena", "Secretary");
public class junk2 {

//Scenario 2 - All parties don't have a candidate for all positions
  try {

    // party 1
    Party p1 = new Party("ABC");
    Candidate c1 = new Candidate("Felix Kjellberg", "President");
    Candidate c2 = new Candidate("Sean McLaughlin", "Vice President");
    Candidate c3 = new Candidate("Sive Morten", "Secretary");
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

    // static method assignment of party objects
    Ballot.addParty(p1);
    Ballot.addParty(p2);

    // testing getCandidates() for the Secretary office
    Candidate sec1 = new Candidate("Sive Morten", "Secretary");
    Candidate sec2 = new Candidate("Eddy", "Secretary");

    ArrayList<Candidate> test_AL = new ArrayList<Candidate>();
    // expected output order -> party1 then party 2
    test_AL.add(sec1);
    test_AL.add(sec2);

    if (!Ballot.getCandidates("Secretary").toString().equals(test_AL.toString()))
      return false;

    // voting time

    // for President, both vote for opposite parties
    b1.vote(c1);
    b2.vote(c4);

    // for Vice President, both vote for party 1
    b1.vote(c2);
    b2.vote(c2);

    // for Secretary, both vote for party 2
    b1.vote(c6);
    b2.vote(c6);

    // using getVote() to verify Vice President and Secretary roles
    // vote must be to same candidate object in both offices

    if (!b1.getVote("Vice President").equals(b2.getVote("Vice President")))
      return false;

    if (!b1.getVote("Secretary").equals(b2.getVote("Secretary")))
      return false;

    // using getVote() to verify President role, we know both voters voted different
    // vote must be to different candidate object in both offices

    if (b1.getVote("President").equals(b2.getVote("President")))
      return false;

  

  } catch (NoSuchElementException nse) {
    System.out.println("No such Element was found");
  }

  catch (Exception e) {
    System.out.println("No exception should be thrown");
  }


}
////////////////



System.out.println(temp.toString());

System.out.println(c3.toString());
