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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class defines the Draggable Object that inherits from the parent class VisibleObject.
 * It defines an object that can be dragged across the screen.
 * 
 * It has a constructor that sets its name, x and y fields to a value and also has
 * a mutator method called update() which updates objects on screen with mouse
 * movements. It also a mutator drop() that returns null, it is overridden in the base 
 * classes that derive from this class.
 * 
 * @author Ayan Deep Hazra
 *
 */

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
   * @param x    value of x coordinate
   * @param y    value of y coordinate
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
    
    // case that verifies if mouse was not pressed and is currently pressed and
    // if the mouse is currently over object
    if (getProcessing().mousePressed && this.isOver(getProcessing().mouseX,
        getProcessing().mouseY) && this.mouseWasPressed == false) {
      isDragging = true;
      this.mouseWasPressed = getProcessing().mousePressed;
      this.oldMouseX = getProcessing().mouseX;
      this.oldMouseY = getProcessing().mouseY;
    }

    // if that checks if object is currently being dragged and mouse is not being pressed.
    // then update isDragging and mouseWasPressed and return drop
    else if (this.isDragging && !getProcessing().mousePressed) {
      this.isDragging = false;
      this.mouseWasPressed = getProcessing().mousePressed;
      return drop();
    }

    // if object is being dragged, then call move() and update Old Mouse vals
    // and mouseWasPressed
    else if (this.isDragging) {
      this.move(getProcessing().mouseX - this.oldMouseX, getProcessing().mouseY - this.oldMouseY);
      this.mouseWasPressed = getProcessing().mousePressed;
      this.oldMouseX = getProcessing().mouseX;
      this.oldMouseY = getProcessing().mouseY;

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
  protected Action drop() {
    return null;
  }
}
