/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: DroppableObject
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
 * This class defines the Droppable Object that inherits from the parent class DraggableObject.
 * It defines an object that can be dropped on another object.
 * 
 * It has a constructor that sets its name, x and y fields as well as the target variable of type 
 * VisibleObject and action of type Action to a value and also has a mutator drop() that deactivate
 * a target and also the current instance and return the action object if there is an overlap
 * and also if the target is active.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class DroppableObject extends DraggableObject {

  // object over which this object can be dropped
  private VisibleObject target;
  // action that results from dropping this object over target
  private Action action;
  /**
   * Paramterized constructor for the DraggableObject class.
   * 
   * @param name   The name that needs to be assigned.
   * @param x      value of x coordinate.
   * @param y      value of y coordinate.
   * @param target object over which this object can be dropped.
   * @param action Action returned from update when this object is clicked.
   */
  public DroppableObject(String name, int x, int y, VisibleObject target, Action action) {
    super(name, x, y);
    this.target = target;
    this.action = action;
  }

  /**
   * Returns action and deactivates objects in response to successful drop. When this object is over
   * its target and its target is active: deactivate both this object and the target object, and
   * return action, otherwise return null.
   */
  @Override
  public Action drop() {
    
    // if target is over this instance and target is active, deactivate both and
    // return action
    if (this.isOver(this.target) && target.isActive()) {
      target.deactivate();
      this.deactivate();
      return action;
    }
    return null;

  }


}
