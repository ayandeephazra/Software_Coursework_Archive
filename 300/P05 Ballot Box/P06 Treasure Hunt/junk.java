
public class junk {

  /*
   * int xval = InteractiveObject.getProcessing().mouseX; int yval =
   * InteractiveObject.getProcessing().mouseY;
   * 
   * while (isOver(xval, yval) && InteractiveObject.getProcessing().mousePressed) {
   * 
   * xval = InteractiveObject.getProcessing().mouseX; yval =
   * InteractiveObject.getProcessing().mouseY;
   * 
   * move(xval - oldMouseX, yval - oldMouseY); super.update();
   * 
   * oldMouseX = xval; oldMouseY = yval;
   * 
   * }
   */
  isOver(InteractiveObject.getProcessing().mouseX, 
      InteractiveObject.getProcessing().mouseY)) {
        
        
        isOver(InteractiveObject.getProcessing().mouseX, InteractiveObject.getProcessing().mouseY))
      }
      
      int otherX = other.x;
      int otherY = other.y;
      int otherWidth = other.image.width;
      int otherHeight = other.image.height;
      
      int lowerXOther = otherX - otherWidth/2;
      int higherXOther = otherX + otherWidth/2;
      
      int lowerYOther = otherY - otherHeight/2;
      int higherYOther = otherY + otherHeight/2;
      
      int imgWidth = image.width;
      int imgHeight = image.height;
      
      int lowerX = this.x - imgWidth/2;
      int higherX = this.x + imgWidth/2;
      
      int lowerY = this.y - imgHeight/2;
      int higherY = this.y + imgHeight/2;
      
      for(int i = lowerXOther; i<higherXOther; i++)
      {
        for(int j = lowerX; j<higherX; j++)
        {
          if(i==j) {
            return true;
          }
        }
      }
      
      for(int i = lowerYOther; i<higherYOther; i++)
      {
        for(int j = lowerY; j<higherY; j++)
        {
          if(i==j) {
            return true;
          }
        }
      }
      
      
      //isOver(oldMouseX, oldMouseY)
      /*
      int xval = InteractiveObject.getProcessing().mouseX;
      int yval = InteractiveObject.getProcessing().mouseY;
      */

}
