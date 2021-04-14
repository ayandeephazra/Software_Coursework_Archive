/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: VisibleObject
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
 * This class defines the Visible Object that inherits from the parent class InteractiveObject. 
 * It defines an object that can be seen on the screen.
 * 
 * It has a constructor that sets its name, x and y fields to a value and also sets the image
 * field of the instance using the name parameter that was passed in. It has a mutator method
 * called update() which processes the image with the PImage object and coordinates. It also a 
 * mutator move() that alters the x,y fields of the instance as well as two overloaded
 * isOver() methods that detect if a point is over an object or if an object is over another
 * object respectively.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class VisibleObject extends InteractiveObject {

  // the graphical representation of this object
  private PImage image;
  // the horizontal position (in pixels of this object's left side)
  private int x;
  // the vertical position (in pixels of this object's top side)
  private int y;

  /**
   * Initialize this new VisibleObject the image for this visible object should be 
   * loaded from : * "images"+File.separator+ name +".png".
   * 
   * @param name The file name to load image from
   * @param x value of x coordinate
   * @param y value of y coordinate
   */
  public VisibleObject(String name, int x, int y) {
    super(name);
    this.x = x;
    this.y = y;
    image = InteractiveObject.getProcessing().loadImage("images" + File.separator
        + name + ".png");
  }

  /**
   * Draws image at its position before returning null
   * 
   * @return null
   */
  @Override
  public Action update() {

    // processes the image with the PImage object and coordinates.
    Action a = null;
    float horizontal = (float) x;
    float vertical = (float) y;
    PApplet process = InteractiveObject.getProcessing();
    process.image(image, horizontal, vertical);
    return a;
  }

  /**
   * Changes x by adding dx to it (and y by dy).
   * 
   * @param dx The change in x to add to x
   * @param dy The change in y to add to y
   */
  public void move(int dx, int dy) {
    this.x = this.x + dx;
    this.y = this.y + dy;
  }

  /**
   * Return true only when point x,y is over image.
   * 
   * @param x coordinate to check
   * @param y coordinate to check
   * @return true if x,y is over image, else false
   */
  public boolean isOver(int x, int y) {

    // checks whether this object falls within the given limits
    if ((this.x <= x && x <= this.x + this.image.width)
        && (this.y <= y && y <= this.y + this.image.height)) {
      return true;
    }
    return false;
  }

  /**
   * return true only when other's image overlaps this one's.
   * 
   * @param other Object of type VisibleObject to compare with current instance of
   *              VisibleObject
   * @return true if other Object is over this object's instance
   */
  public boolean isOver(VisibleObject other) {

    int otherX = other.x;
    int otherY= other.y;

    // checks whether the other object falls within the limits of this particular
    // Visible object, if yes, returns true or exits if and returns false
    if ((this.x <= otherX + other.image.width && otherX <= this.x + this.image.width)
        && (this.y <= otherY + other.image.height && otherY <= this.y + this.image.height)) {
      return true;
    }

    return false;
  }

}
