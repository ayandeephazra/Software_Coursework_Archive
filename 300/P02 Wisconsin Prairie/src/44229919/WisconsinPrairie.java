/**
 * 
 */


import java.util.Random;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * This class visualises the various functions.
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
	* @param processingObj represents a reference to the graphical interface of
	* the application
	*/
	public static void setup(PApplet processingObj) {
		
		randGen = new Random();
		
		processing = processingObj; // initialize the processing field to the one passed
		// into the input argument parameter.

		// initialize and load the image of the background
		backgroundImage = processing.loadImage("images/background.png");

		
		float w = (float)randGen.nextInt(processing.width);
		float h = (float)randGen.nextInt(processing.height);
		
		cows[0] = new Cow(processing, w, h);
		
		
	}
	
	
	
	/**
	* Draws and updates the application display window.
	* This callback method called in an infinite loop.
	*/
	public static void draw() {
		
		// Draw the background image at the center of the screen
		processing.image(backgroundImage, processing.width / 2, processing.height / 2);
		// width [resp. height]: System variable of the processing library that stores
		// the width [resp. height] of the display window.
		
		for (int i = 0; i < cows.length && cows[i] != null; i++) {
			cows[i].draw();
			//cows[i].setPositionX(processing.mouseX);
			//cows[i].setPositionY(processing.mouseY);
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

		
		if(cow==null)
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

		
		for(int i = 0; i<cows.length && cows[i]!=null; i++)
		{
			if(cows[i].isDragging())
				cows[i].setDragging(true);

		}
	}

	/**
	 * Callback method called each time the mouse is released
	 */
	public static void mouseReleased() {

		for (int i = 0; i < cows.length && cows[i] != null; i++) {
			if (!cows[i].isDragging())
				cows[i].setDragging(false);
		}

	}

	
	/**
	 * Callback method called each time the user presses a key
	 */
	public static void keyPressed() {
		randGen = new Random();
		Random r = randGen;
		float w, h;
		char currKey = processing.key;
		int i = 0;
		boolean found = false;

		if (currKey == 'c' || currKey == 'C') {
			for (i = 0; i < cows.length && found == false; i++) {
				if (cows[i] == null) {
					w = (float) r.nextInt(processing.width);
					h = (float) r.nextInt(processing.height);

					cows[i] = new Cow(processing, w, h);

					found = true;
					
					break;

				}

			}
		}

		if (currKey == 'd' || currKey == 'D') {

			for (i = 0; i < cows.length; i++) {
				
				if(isMouseOver(null))
				{
					break;
				}
				if (isMouseOver(cows[i])) {
					cows[i] = null;

					break;
				}
			}
		}
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Utility.startApplication();
	}

}
