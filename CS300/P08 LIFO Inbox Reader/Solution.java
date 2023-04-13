class Solution {

  public int solution(int K, int[][] A) {
    // write your code in Java SE 11
    int suitable = 0;
    boolean valid = true;
    boolean nohouse = true;
    for (int i = 0; i < A.length; i++) {
      for (int j = 0; j < A[i].length; j++) {
        
        
        
        if (A[i][j] == 1) {
          continue;
        } 
        else {
          boolean klvalid = false;
          valid = true;
          nohouse = true;
          for (int k = 0; k < A.length; k++) {
            for (int l = 0; l < A[k].length; l++) {
              // house
              if (A[k][l] == 1) {
                
                nohouse = false;
                if (Math.abs(k + l - i - j) <= K) {
                  valid = true;
                }
                else {
                  valid = false;
                }
                // inner if
              }
              // house end
              else {
                continue;
              }
            }
      
          }
            
          
        }
        
        if(valid && !nohouse)
          suitable++;      
      }
    }
    
    return suitable;
  }
}
