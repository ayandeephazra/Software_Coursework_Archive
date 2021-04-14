/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: InteractiveObject
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
 * The Interactive Object is the first class out of many which defines an Object on screen
 * that can have more functionality. This parent class is where other Object types such as 
 * DroppableObject, DraggableObject and ClickableObject are derived from. 
 * 
 * It has a constructor which initializes the name and isActive fields and accessor and 
 * mutator methods which are:
 * 
 * hasName() - compares NAME field and parameter for equality
 * isActive() - returns isActive
 * activate() - sets isActive to true
 * deactivate() - sets isActive to false
 * setProcessing() - sets the processing object to the passed in parameter
 * getProcessing() - returns the processing obect
 * 
 * @author Ayan Deep Hazra
 *
 */
public class InteractiveObject {


  // the constant name identifying this interactive object
  private final String NAME;
  // active means this interactive object is visible and
  // can be interacted with
  private boolean isActive;

  private static PApplet processing = null;

  /**
   * Initializes the name of this object, and sets isActive to true
   * 
   * @param name Name to initialize the object with
   */
  public InteractiveObject(String name) {
    this.NAME = name;
    isActive = true;
  }

  /**
   * returns true only when contents of name equal NAME
   * 
   * @param name String to check class variable NAME against
   * @return True or False depending on whether condition is true or false
   */
  public boolean hasName(String name) {
    if (name.equals(this.NAME)) {
      return true;
    }
    return false;
  }

  /**
   * Returns true only when isActive is true.
   * 
   * @return true only when isActive is true, else false.
   */
  public boolean isActive() {
    if (isActive) {
      return true;
    }
    return false;
  }


  /**
   * Changes isActive to true.
   */
  public void activate() {
    isActive = true;
  }


  /**
   * Changes isActive to false.
   */
  public void deactivate() {
    isActive = false;
  }


  /**
   * This method returns nulL subclass types will override this update() method to do more
   * interesting things
   * 
   * @return null
   */
  public Action update() {
    return null;
  }

  /**
   * Initializes processing field.
   * 
   * @param processing
   */
  public static void setProcessing(PApplet processing) {
    InteractiveObject.processing = processing;
  }

  /**
   * Accessor method to retrieve this static field.
   * 
   * @return PApplet object that references processing.
   */
  protected static PApplet getProcessing() {
    return processing;
  }

}
