import java.util.ArrayList;
import java.util.NoSuchElementException;

// Scenario 2 - Parties may or may not have a candidate for all positions
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
        //Candidate sec2 = new Candidate("Edd", "Vice President");
        Candidate sec3 = new Candidate("Captain America", "Vice President");

        ArrayList<Candidate> candidatesTestList = new ArrayList<Candidate>();
        // expected output order -> party1 then party 3
        candidatesTestList.add(sec1);
        //candidatesTestList.add(sec2);
        candidatesTestList.add(sec3);

        System.out.println(Ballot.getCandidates("Vice President").toString());
        System.out.println(candidatesTestList.toString());
        if (!Ballot.getCandidates("Vice President").toString()
            .equals(candidatesTestList.toString()))
          return false;
        
        System.out.print("HHHH");

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

        if (!b3.getVote("President").equals(b4.getVote("President")))
          return false;

        if (!b3.getVote("Vice President").equals(b4.getVote("Vice President")))
          return false;

        if (!b3.getVote("Secretary").equals(b4.getVote("Secretary")))
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