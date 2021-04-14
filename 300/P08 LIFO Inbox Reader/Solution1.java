
public class Solution1 {

  public int solution(int K, int[][] A) {
    // write your code in Java SE 11
    boolean klvalid = false;
    boolean valid = false;
    int suitable = 0;
    
    for(int i = 0; i<A.length; i++)
    {
      for(int j = 0; j<A[i].length; j++)
      {
        if(A[i][j]==1) {
          continue;
        }
        
        klvalid=false;
        for(int y = 0; y<A.length; y++)
        {
          for(int z = 0; z<A[y].length; z++)
          {
            if(A[y][z]==0) {
              continue;
            }
            else if(A[y][z]==1) {
              if (Math.abs(z + y - i - j) <= K) {
                klvalid = true;
              }
              else {
                klvalid = false;
              }
            }
          }
          
          if(klvalid==false) {
            valid = false;
            break;
          }
          else {
            valid = true;
            continue;
          }
        }
        
        if(valid) {
          suitable++;
        }
        
        
        
      }
    }
    
    return suitable;
  }
}
