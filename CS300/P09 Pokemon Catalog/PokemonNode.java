/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: PokemonNode
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

/**
 * This class implements a node that contains a data element of type Pokemon as well
 * as references to the left and right children of the node. It also defines many methods that 
 * relate to the functioning of the node such as constructor, accessors and mutators.
 * 
 * It has a constructor that sets the data field of the instance, and accessors that return
 * references to the data field, left and right child. It also has mutator methods that
 * change the reference of the left and right child to another instance of PokemonNode.
 * 
 * @author Ayan Deep Hazra
 */

public class PokemonNode {
  
  private Pokemon data; // data field of this PokemonNode
  private PokemonNode leftChild; // reference to the left child
  private PokemonNode rightChild; // reference to the right child

  /**
   * A one-argument constructor which sets leftChild and rightChild to null and initializes the 
   * data field. Throws an IllegalArgumentException if data is null.
   * 
   * @param data The reference to set as the data field of this instance
   * @throws IllegalArgumentException
   */
  public PokemonNode(Pokemon data) {
    if (data == null) {
      throw new IllegalArgumentException("Entered data field is null.");
    } else {
      this.data = data;
      this.leftChild = null;
      this.rightChild = null;
    }
  }

  /**
   * Returns a reference to the left child of this PokemonNode.
   * 
   * @return a reference to the left child of this PokemonNode
   */
  public PokemonNode getLeftChild() {
    return this.leftChild;
  }

  /**
   * Returns a reference to the right child of this PokemonNode.
   * 
   * @return a reference to the right child of this PokemonNode
   */
  public PokemonNode getRightChild() {
    return this.rightChild;
  }

  /**
   * Returns a reference to the Pokemon contained in this PokemonNode.
   * 
   * @return
   */
  public Pokemon getPokemon() {
    return this.data;
  }

  /**
   * Sets the left child of this PokemonNode (null values allowed).
   * 
   * @param left The reference to set as the left child.
   */
  public void setLeftChild(PokemonNode left) {
    this.leftChild = left;
  }

  /**
   * Sets the right child of this PokemonNode (null values allowed).
   * 
   * @param right The reference to set as the right child.
   */
  public void setRightChild(PokemonNode right) {
    this.rightChild = right;
  }

}
