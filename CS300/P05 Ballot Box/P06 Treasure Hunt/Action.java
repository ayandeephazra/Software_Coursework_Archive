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
 * 
 */

/**
 * @author Ayan Deep Hazra
 *
 */
public class Action {

  // message printed by this action (or null to do nothing)
  private String message;
  private InteractiveObject object;

  /**
   * Create and initialize this new action.
   * 
   * @param message
   */
  public Action(String message) {
    this.message = message;
  } 
  
  public Action(InteractiveObject object) {
    this.object = object;
  }
  
  public Action(String message, InteractiveObject object) {
    this.object = object;
    this.message = message;
  }

  /**
   * when message is not null, message is printed to System.out
   */
  public void act(ArrayList<InteractiveObject> objlist) {

    if (this.message != null)
      System.out.println(this.message);

    if (this.object != null) {
      this.object.activate();
      objlist.add(this.object);
      this.object = null;
    }

  }
  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }
}
