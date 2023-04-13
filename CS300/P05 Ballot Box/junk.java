
public class junk {

//////////////////////////////////////////////////////

	public static void main(String[] args) {
		Ballot b = new Ballot();
		Ballot c = new Ballot();
		Object o = new Object();

		System.out.print(c.equals(b));
		System.out.print(c.equals(o));
		System.out.print(c.equals(c));
		
		
		   // party 3
	      Party p3 = new Party("GHI");
	      Candidate c7 = new Candidate("Ed", "President");
	      
	      
	      // output matching
	      //System.out.println(Ballot.getCandidates("Secretary"));
	      //
	     
	}
	
	//!c.getName().equals(getCandidate(c.getOffice()).getName()))

/*
 * if(getCandidate(runningFor) != null) throw new
 * IllegalArgumentException("Candidate running for same" +
 * " office as another member");
 */


/*
if (getCandidate(runningFor).getName()!=null
		  || !getCandidate(runningFor).getName().equals(""))
	throw new IllegalArgumentException("Candidate running for same"
		  + " office as another member");
else
	candidates.add(c);
	
	
	
	///////////
	 * 
	 * 
	 * 
	 * if(!getCandidate(runningFor).equals(""))
	throw new IllegalArgumentException("Candidate running for same"
		  + " office as another member");

candidates.add(c);
	*/

}
