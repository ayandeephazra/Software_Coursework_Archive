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

public class DroppableObject extends DraggableObject { 

  // object over which this object can be dropped
  private VisibleObject target;
  // action that results from dropping this object over target
  private Action action;

  /**
   * Paramterized constructor for the DraggableObject class.
   * 
   * @param name The name that needs to be assigned.
   * @param x value of x coordinate.
   * @param y value of y coordinate.
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
  protected Action drop() {

    if (this.isOver(this.target) && target.isActive()) {
     
      target.deactivate();
      this.deactivate();
      return action;
    }
    return null;
  }

}
