/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: ClickableObject
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

public class ClickableObject extends VisibleObject {
  
  // action returned from update when this object is clicked
  private Action action;
  // tracks whether the mouse was pressed during the last update()
  private boolean mouseWasPressed;

  /**
   * Initializes this new object.
   * 
   * @param name The file name to load image from.
   * @param x value of x coordinate.
   * @param y value of y coordinate.
   * @param action Action returned from update when this object is clicked.
   */ 
  public ClickableObject(String name, int x, int y, Action action) {
    super(name, x, y);
    //mouseWasPressed = false;
    this.action = action;
  }
   
  /**
   * Calls VisibleObject update, then returns action only when mouse is first 
   * clicked on this object. 
   * 
   * @return Action that is performed when new mouse press happens.
   */

  @Override
  public Action update() {

    Action a = super.update();
    
    if (isOver(getProcessing().mouseX, getProcessing().mouseY) && 
            getProcessing().mousePressed && !mouseWasPressed ) {
        a = this.action;
    }
     mouseWasPressed = getProcessing().mousePressed; 
     return a;
    
}


}
