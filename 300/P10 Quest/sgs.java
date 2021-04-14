
public class sgs {

  if(data[i].compareTo(data[2*i+1])==-1 && data[i].compareTo(data[2*i+2])==-1)
  {
    BattleCharacter bigger =
        (data[2 * i + 1].compareTo(data[2 * i + 2]) == 1) ? data[2 * i + 1] : data[2 * i + 2];
        // take bigger index
    int newIndex = (data[2 * i + 1].compareTo(data[2 * i + 2]) == 1) ? 2 * i + 1 : 2 * i + 2;
    BattleCharacter temp = data[i];
    data[i] = bigger;
    bigger = temp;
    percolateDown(newIndex);
  }
  // left child of i
  else if(data[i].compareTo(data[2*i+1])==-1 ) {
    BattleCharacter temp = data[i];
    data[i] = data[2*i+1];
    data[2*i+1] = temp;
    percolateDown(2*i+1);
  }
  // right child of i
  else if(data[i].compareTo(data[2*i+2])==-1) {
    BattleCharacter temp = data[i];
    data[i] = data[2*i+2];
    data[2*i+2] = temp;
    percolateDown(2*i+2);
  }
  // good node no violations
  else
    return;
}
