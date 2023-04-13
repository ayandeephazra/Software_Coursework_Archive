/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: DraggableObject
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

public class DraggableObject extends VisibleObject {

  // similar to use in ClickableObject
  private boolean mouseWasPressed;
  // true when this object is being dragged by the user
  private boolean isDragging;
  // horizontal position of mouse during last update
  private int oldMouseX;
  // vertical position of mouse during last update
  private int oldMouseY;

  /**
   * Initializes new draggable object.
   * 
   * @param name assign to instance of DraggableObject
   * @param x value of x coordinate
   * @param y value of y coordinate
   */
  public DraggableObject(String name, int x, int y) {
    super(name, x, y);
  }

  /**
   * Calls VisibleObject update() first, then moves according to mouse drag.
   * 
   * @return Action that is performed when new mouse dragging occurs.
   */
  @Override
  public Action update() {
    super.update();
    
    int xval = getProcessing().mouseX;
    int yval = getProcessing().mouseY;
    
    if (!this.mouseWasPressed && getProcessing().mousePressed
        && isOver(xval, yval)) {
      this.isDragging = true;

      this.move(xval - oldMouseX, yval - oldMouseY);
      
      this.oldMouseX = xval;
      this.oldMouseY = yval;
      this.mouseWasPressed = true;
    }

    else if (isDragging && !getProcessing().mousePressed) {
      this.isDragging = false;
      this.mouseWasPressed = false;
      this.drop(); 
    }
    
    else if (isDragging) {
    
      this.move(xval - this.oldMouseX, yval - this.oldMouseY);
      
      this.oldMouseX = xval;
      this.oldMouseY = yval;
      this.mouseWasPressed = true;
    }
    return null;
  }
  
  /**
   * Each time isDragging changes from true to false, the drop() method below will be
   * called once and any action objects returned from that method should then be 
   * returned from update() this method returns null. subclass types will override
   * this drop() method to perform more interesting behaviour.
   * 
   * @return Action but null is returned in this
   */
  protected Action drop() { return null; } 
}
