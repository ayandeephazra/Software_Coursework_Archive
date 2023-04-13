/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: PokemonTreeTester
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

import java.util.NoSuchElementException;

/**
 * This class checks the correctness of the implementation of the methods defined in the class
 * PokemonTree.
 *
 * @author Ayan Deep Hazra
 */

public class PokemonTreeTester {

  /**
   * Checks the correctness of the implementation of both addPokemon() and toString() methods
   * implemented in the PokemonTree class. This unit test considers at least the following
   * scenarios. (1) Create a new empty PokemonTree, and check that its size is 0, it is empty, and
   * that its string representation is an empty string "".(2)try adding one Pokemon and then check
   * that the addPokemon() method call returns true, the tree is not empty, its size is 1, and the
   * .toString() called on the tree returns the expected output. (3) Try adding another Pokemon
   * which is more powerful than the one at the root, (4) Try adding a third Pokemon which is less
   * powerful than the one at the root, (5) Try adding at least two further Pokemons such that one
   * must be added at the left subtree, and the other at the right subtree. For all the above
   * scenarios, and more, double check each time that size()method returns the expected value, the
   * add method call returns true, and that the .toString() method returns the expected string
   * representation of the contents of the binary search tree in an ascendant order from the most
   * powerful Pokemon to the least powerful one.(6)Try adding a Pokemon whose CP value was used as
   * a key for a Pokemon already stored in the tree. Make sure that the addPokemon() method call
   * returned false, and that the size of the tree did not change.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPokemonToStringSize() {

    PokemonTree tree = new PokemonTree();
    // Case number 1: checking if its empty
    if (!tree.isEmpty()) {
      return false;
    }
    // Empty string case
    if (!tree.toString().equals("")) {
      return false;
    }
    // Case number 2: adding 1 Pokemon
    Pokemon p1 = new Pokemon("Pikachu", "1,2,3");
    tree.addPokemon(p1);
    // size check
    if (tree.size() != 1) {
      return false;
    }
    // testing toString method
    if (!tree.toString().equals("[Pikachu CP:123 (A:1 S:2 D:3)]\n")) {
      return false;
    }
    // Adding 4 more pokemon objects
    Pokemon p2 = new Pokemon("Eevee", "2,2,4"); // stronger than root
    Pokemon p3 = new Pokemon("Snorlax", "4,4,8"); // stronger than previous
    Pokemon p4 = new Pokemon("Charmander", "3,2,1"); // weaker than previous
    Pokemon p5 = new Pokemon("Lapras", "1,1,1"); // left subtree
    tree.addPokemon(p2);
    tree.addPokemon(p3);
    tree.addPokemon(p4);
    tree.addPokemon(p5);
    if (tree.size() != 5) {
      return false;
    }
    // adding pokemon with same key
    Pokemon p6 = new Pokemon("Squirtle", "1,1,1");

    // should be rejected because key is the exact same as p5
    if (tree.addPokemon(p6)) {
      return false;
    }
    if (tree.size() != 5) { // size should be the same
      return false;
    }
    // testing toString method
    if (!tree.toString()
        .equals("[Lapras CP:111 (A:1 S:1 D:1)]\n" + "[Pikachu CP:123 (A:1 S:2 D:3)]\n"
            + "[Eevee CP:224 (A:2 S:2 D:4)]\n" + "[Charmander CP:321 (A:3 S:2 D:1)]\n"
            + "[Snorlax CP:448 (A:4 S:4 D:8)]" + "\n")) {
      return false;
    }
    return true;
  }

  /**
   * This method checks mainly for the correctness of the PokemonTree.lookup() method. It must
   * consider at least the following test scenarios. (1) Create a new PokemonTree. Then, check that
   * calling the lookup() method with any valid CP value must throw a NoSuchElementException. (2)
   * Consider a PokemonTree of height 3 which consists of at least 5 PokemonNodes.Then, try to call
   * lookup() method to search for the Pokemon at the root of the tree, then a Pokemon at the right
   * and left subtrees at different levels. Make sure that the lookup() method returns the expected
   * output for every method call. (3) Consider calling .lookup() method on a non-empty PokemonTree
   * with a CP value not stored in the tree, and ensure that the method call throws a
   * NoSuchElementException.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testAddPokemonAndLookup() {
    PokemonTree tree = new PokemonTree();
    Pokemon p1 = new Pokemon("Eevee", "2,2,4");

    tree.addPokemon(p1);
    try { 
      tree.lookup(345);
    } catch (NoSuchElementException e) {
      System.out.println("No pokemon with this CP");
    }
    // 5 nodes, height = 3
    Pokemon p2 = new Pokemon("Snorlax", "4,4,8"); // stronger than previous
    Pokemon p3 = new Pokemon("Charmander", "1,1,2"); // weaker than previous
    Pokemon p4 = new Pokemon("Lapras", "1,1,1"); // left subtree
    Pokemon p5 = new Pokemon("Pikachu", "3,3,5");
    tree.addPokemon(p2);
    tree.addPokemon(p3);
    tree.addPokemon(p4);
    tree.addPokemon(p5);

    // looking for root Pokemon
    if (tree.lookup(224) != p1) {
      return false;
    }
    // right subtree Pokemon
    if (tree.lookup(448) != p2) {
      return false;
    }
    // left subtree Pokemon
    if (tree.lookup(112) != p3) {
      return false;
    }
    // subtree of right subtree Pokemon
    if (tree.lookup(335) != p5) {
      return false;
    }
    // subtree of left subtree Pokemon
    if (tree.lookup(111) != p4) {
      return false;
    }
    return true;
  }

  /**
   * Checks for the correctness of PokemonTree.height() method. This test must consider several
   * scenarios such as, (1) ensures that the height of an empty Pokemon tree is zero. (2) ensures
   * that the height of a tree which consists of only one node is 1. (3) ensures that the 
   * height of a PokemonTree with the following structure for instance, is 4. (*) / \ (*) 
   * (*) \ / \ (*)(*) (*) / (*)
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testHeight() {

    PokemonTree tree = new PokemonTree();

    if (tree.height() != 0) { // empty tree
      return false;
    }
    Pokemon p1 = new Pokemon("Eevee", "2,2,4");
    tree.addPokemon(p1);

    if (tree.height() != 1) { // tree consists of only 1 node
      return false;
    }

    Pokemon p2 = new Pokemon("Snorlax", "4,4,8");
    Pokemon p3 = new Pokemon("Charmander", "1,1,2"); // weaker than previous
    Pokemon p4 = new Pokemon("Lapras", "1,1,1"); // left subtree
    Pokemon p5 = new Pokemon("Pikachu", "3,3,5"); // right subtree
    Pokemon p6 = new Pokemon("Mewtwo", "9,9,9"); // right of right subtree
    
    // adding all the above declared Pokemon objects
    
    tree.addPokemon(p2);
    tree.addPokemon(p3);
    // current situation:
    //              p1
    //             /  \
    //            p3  p2
    if (tree.height() != 2) { // height should be 2 from above tree diagram
      return false;
    }
    tree.addPokemon(p4);
    tree.addPokemon(p5);
    // current situation:
    //              p1
    //             /  \
    //            p3  p2
    //           /   /  
    //          p4  p5
    if (tree.height() != 3) { // height should be 3 from above tree diagram
      return false;
    }
    tree.addPokemon(p6);
    // current situation:
    //              p1
    //             /  \
    //            p3  p2
    //           /   /  \
    //          p4  p5   p6
    if (tree.height() != 3) { // height should be 4 from above tree diagram
      return false;
    }
    return true;
  }

  /**
   * Checks for the correctness of PokemonTree.getLeastPowerfulPokemon() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetLeastPowerfulPokemon() {
    PokemonTree tree = new PokemonTree();

    Pokemon p1 = new Pokemon("Eevee", "2,2,4");
    tree.addPokemon(p1);
    if (tree.getLeastPowerfulPokemon() != p1) { // Eevee is least powerful with CP 224
      return false;
    }
    Pokemon p2 = new Pokemon("Snorlax", "4,4,8");
    tree.addPokemon(p2);
    if (tree.getLeastPowerfulPokemon() != p1) { // Eevee is least powerful with CP 224
      return false;
    }
    Pokemon p3 = new Pokemon("Charmander", "1,1,2"); // weaker than previous
    tree.addPokemon(p3);
    if (tree.getLeastPowerfulPokemon() != p3) { // Charmander is least powerful with CP 112
      return false;
    }
    Pokemon p4 = new Pokemon("Lapras", "1,1,1"); // left subtree
    tree.addPokemon(p4);
    if (tree.getLeastPowerfulPokemon() != p4) { // Lapras is least powerful with CP 111
      return false;
    }
    Pokemon p5 = new Pokemon("Pikachu", "3,3,5"); // right subtree
    tree.addPokemon(p5);
    if (tree.getLeastPowerfulPokemon() != p4) { // Lapras is least powerful with CP 111
      return false;
    }
    Pokemon p6 = new Pokemon("Mewtwo", "9,9,9"); // rightmost node, strongest
    tree.addPokemon(p6);
    if (tree.getLeastPowerfulPokemon() != p4) { // Lapras is least powerful with CP 111
      return false;
    }   
    if (tree.getLeastPowerfulPokemon() != p4) { // Lapras is least powerful with CP 111
      return false;
    }

    return true;
  }

  /**
   * Checks for the correctness of PokemonTree.getMostPowerfulPokemon() method.
   * 
   * @return true when this test verifies a correct functionality, and false otherwise
   */
  public static boolean testGetMostPowerfulPokemon() {

    PokemonTree tree = new PokemonTree();

    Pokemon p1 = new Pokemon("Eevee", "2,2,4");
    tree.addPokemon(p1);
    if (tree.getMostPowerfulPokemon() != p1) { // Only one pokemon so it should return it
      return false;
    }
    Pokemon p2 = new Pokemon("Snorlax", "4,4,8");
    tree.addPokemon(p2);
    if (tree.getMostPowerfulPokemon() != p2) { // Only two pokemon so it should return p2
      return false;
    }
    Pokemon p3 = new Pokemon("Charmander", "1,1,2"); // weaker than previous
    tree.addPokemon(p3);
    if (tree.getMostPowerfulPokemon() != p2) { // It should return p2
      return false;
    }
    Pokemon p4 = new Pokemon("Lapras", "1,1,1"); // left subtree
    tree.addPokemon(p4);
    if (tree.getMostPowerfulPokemon() != p2) { // It should return p2
      return false;
    }
    Pokemon p5 = new Pokemon("Pikachu", "3,3,5"); // right subtree
    tree.addPokemon(p5);
    if (tree.getMostPowerfulPokemon() != p2) { // It should return p2
      return false;
    }
    Pokemon p6 = new Pokemon("Mewtwo", "9,9,9"); // rightmost node, strongest
    tree.addPokemon(p6);

    if (tree.getMostPowerfulPokemon() != p6) { // Mewtwo is the most powerful with CP 99
      return false;
    }

    return true;
  }

  /**
   * Calls the test methods
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    System.out.println("testAddPokemonToStringSize(): " + testAddPokemonToStringSize());
    System.out.println("testAddPokemonAndLookup(): " + testAddPokemonAndLookup());
    System.out.println("testHeight(): " + testHeight());
    System.out.println("testGetLeastPowerfulPokemon(): " + testGetLeastPowerfulPokemon());
    System.out.println("testGetMostPowerfulPokemon(): " + testGetMostPowerfulPokemon());
  }

}
