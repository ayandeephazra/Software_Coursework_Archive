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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class defines a Clickable Object. The Class defines an object that can be clicked and
 * un-clicked.
 * 
 * It has a constructor, and a mutator update() method. It inherits from parent Class 
 * VisibleObject.
 * 
 * @author Ayan Deep Hazra
 *
 */

public class ClickableObject extends VisibleObject {


  // action returned from update when this object is clicked
  private Action action;
  // tracks whether the mouse was pressed during the last update()
  private boolean mouseWasPressed;

  /**
   * Initializes this new object.
   * 
   * @param name   The file name to load image from.
   * @param x      value of x coordinate.
   * @param y      value of y coordinate.
   * @param action Action returned from update when this object is clicked.
   */
  public ClickableObject(String name, int x, int y, Action action) {
    super(name, x, y);
    this.action = action;
  }

  /**
   * Calls VisibleObject update, then returns action only when mouse is first clicked on this
   * object.
   * 
   * @return Action that is performed when new mouse press happens.
   */
  @Override
  public Action update() {

    PApplet processing = InteractiveObject.getProcessing();
    Action actionPerformed = super.update();

    if (isOver(getProcessing().mouseX, getProcessing().mouseY) && getProcessing().mousePressed
        && !mouseWasPressed) {
      actionPerformed = this.action;
    }
    mouseWasPressed = getProcessing().mousePressed;
    return actionPerformed;

  }

}
