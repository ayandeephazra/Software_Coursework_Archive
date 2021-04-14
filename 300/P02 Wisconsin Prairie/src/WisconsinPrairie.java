/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title:    Wisconsin Prairie
// Course:   CS 300 Fall 2020
//
// Author:   Ayan Deep Hazra
// Email:    ahazra2@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// OUTSIDE HELP ////////////////////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class builds and implements the Wisconsin Prairie functions.
 * 
 * @author Ayan Deep Hazra
 *
 */
public class WisconsinPrairie {

	private static PApplet processing; // PApplet object that represents the graphic
	// interface of the WisconsinPrairie application
	private static PImage backgroundImage; // PImage object that represents the
	// background image
	private static Cow[] cows = new Cow[10]; // array storing the current cows present
	// in the Prairie
	private static Random randGen; // Generator of random numbers

	/**
	 * Defines the initial environment properties of the application
	 * 
	 * @param processingObj represents a reference to the graphical interface of the
	 *                      application
	 */
	public static void setup(PApplet processingObj) {

		randGen = new Random();

		processing = processingObj; // initialize the processing field to the one passed
		// into the input argument parameter.

		// initialize and load the image of the background
		backgroundImage = processing.loadImage("images/background.png");

	}

	/**
	 * Draws and updates the application display window. This callback method called
	 * in an infinite loop.
	 */
	public static void draw() {

		// Draw the background image at the center of the screen
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);
		// width [resp. height]: System variable of the processing library that stores
		// the width [resp. height] of the display window.

		for (int i = 0; i < cows.length; i++) {
			// do not draw if null element
			if (cows[i] != null)
				cows[i].draw();

		}
	}

	/**
	 * Checks if the mouse is over a given cow whose reference is provided as input
	 * parameter
	 *
	 * @param cow reference to a given cow object
	 * @return true if the mouse is over the given cow object (i.e. over the image
	 *         of the cow), false otherwise
	 */
	public static boolean isMouseOver(Cow cow) {

		if (cow == null)
			return false;
		float xPos = cow.getPositionX();
		float yPos = cow.getPositionY();
		int w = cow.getImage().width;
		int h = cow.getImage().height;

		if ((processing.mouseX >= xPos - w / 2) && (processing.mouseX <= xPos + w / 2)
				&& (processing.mouseY >= yPos - h / 2) && (processing.mouseY <= yPos + h / 2)) {

			return true;
		}

		return false;

	}

	/**
	 * Callback method called each time the user presses the mouse
	 */
	public static void mousePressed() {

		int i = 0;
		for (i = 0; i < cows.length; i++) {

			// null element is ignored
			if (cows[i] == null)
				continue;

			// if mouse id over a cow, it is proceeded to be dragged
			if (isMouseOver(cows[i])) {

				cows[i].setDragging(true);
				break;
			}
		}

	}

	/**
	 * Callback method called each time the mouse is released
	 */
	public static void mouseReleased() {

		for (int i = 0; i < cows.length; i++) {

			// if index is not null, then no dragging is set on the cow
			if (cows[i] != null)
				cows[i].setDragging(false);
		}

	}

	/**
	 * Callback method called each time the user presses a key
	 */
	public static void keyPressed() {
		randGen = new Random();
		Random r = randGen;
		// width and height fields in random generation
		float w, h;
		// current key is set from the key field of processing obj
		char currKey = processing.key;
		int i = 0;

		if (currKey == 'c' || currKey == 'C') {
			for (i = 0; i < cows.length; i++) {
				if (cows[i] == null) {

					// set random float values for the width and height parameters
					w = (float) r.nextInt(processing.width);
					h = (float) r.nextInt(processing.height);

					cows[i] = new Cow(processing, w, h);

					// draws the cow element and breaks off from loop
					cows[i].draw();
					break;

				}

			}
		}

		if (currKey == 'd' || currKey == 'D') {

			// since array is traversed low to high index, first instance is deleted
			// ifMouseOver is true, thus lowest index cow gets removed
			for (i = 0; i < cows.length; i++) {

				if (cows[i] != null) {
					if (isMouseOver(cows[i])) {
						cows[i] = null;

						// deletes the cow element and breaks off from loop
						break;
					}
				}
			}
		}
	}

	/**
	 * Implements the startApplication method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Utility.startApplication();
	}

}
