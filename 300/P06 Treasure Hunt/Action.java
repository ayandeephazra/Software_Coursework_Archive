/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: Action
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
 * This class defines the Action object. It has three constructors each taking a different
 * set of parameters, either message or object or both. It also has a mutator method
 * called act that takes an ArrayList of type InteractiveObject as parameter.
 * 
 * @author Ayan Deep Hazra
 */
public class Action {

  // message printed by this action (or null to do nothing)
  private String message;
  private InteractiveObject object;

  /**
   * Create and initialize this new action.
   * 
   * @param message String that sets value of instance field message
   */
  public Action(String message) {
    this.message = message;
  }
  
  /**
   * Create and initialize this new action.
   * 
   * @param object Object of type InteractiveObject that sets the instance field of same name
   */
  public Action(InteractiveObject object) {
    this.object = object;
  }

  /**
   * Create and initialize this new action.
   * 
   * @param message String that sets value of instance field message
   * @param object  Object of type InteractiveObject that sets the instance field of same name
   */
  public Action(String message, InteractiveObject object) {
    this.message = message;
    this.object = object;
  }


  /**
   * When message is not null, message is printed to System.out
   * 
   * @param object ArrayList that may or may not have the instance field of type InteractiveObject
   *               be added to it
   */
  public void act(ArrayList<InteractiveObject> object) {

    // null condition for message
    if (this.message != null) {
      System.out.println(this.message);
    }

    // null condition for object
    if (this.object != null) {
      this.object.activate();
      object.add(this.object);
      this.object = null;

    }
  }

}
