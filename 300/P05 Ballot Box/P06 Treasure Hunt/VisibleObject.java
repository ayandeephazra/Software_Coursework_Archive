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

public class VisibleObject extends InteractiveObject{

  // the graphical representation of this object
  private PImage image;
  // the horizontal position (in pixels of this object’s left side)
  private int x;
  // the vertical position (in pixels of this object’s top side)
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
    // temporary PApplet object
    PApplet p = InteractiveObject.getProcessing();
    image = p.loadImage("images" + File.separator + name + ".png");
    this.x = x;
    this.y = y;
  }

  /**
   * Draws image at its position before returning null
   * 
   * @return null
   */
  
  @Override
  public Action update() {

    Action a = null;
    float hor = (float) x;
    float ver = (float) y;
    PApplet process = InteractiveObject.getProcessing();
    process.image(image , hor, ver);
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
    float imgWidth = this.image.width;
    float imgHeight = this.image.height;

    if (x >= this.x && x <= this.x + imgWidth)
      if (y >= this.x && y <= this.x + imgHeight)
        return true;

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
    float imgWidth = this.image.width;
    float imgHeight = this.image.height;

    int otherX = other.x;
    int otherY = other.y;

    if ((otherX >= this.x && otherY > this.y) && (otherX < this.x + imgWidth)
        && (otherY <= this.y + imgHeight))
      return true;

    return false;
  }
}
