/////////////// FILE HEADER //////////////////////////////////////////////////
//
// Title: PokemonTree
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
 * This class implements a binary search tree (BST) which stores a set of Pokemons.The left subtree
 * contains the Pokemons who are less powerful than the Pokemon stored at a parent node. The right
 * subtree contains the Pokemons who are more powerful than the Pokemon stored at a parent node.
 * 
 * @author Ayan Deep Hazra
 */

public class PokemonTree {
  private PokemonNode root; // root of this binary search tree
  private int size; // total number of Pokemons stored in this tree.

  /**
   * Checks whether this binary search tree (BST) is empty
   * 
   * @return true if this PokemonTree is empty, false otherwise
   */
  public boolean isEmpty() {

    if (root == null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns the number of Pokemons stored in this BST.
   * 
   * @return the size of this PokemonTree
   */
  public int size() {

    return size;
  }

  /**
   * Recursive helper method to add a new Pokemon to a PokemonTree rooted at current.
   * 
   * @param current    The "root" of the subtree we are inserting new pokemon into.
   * @param newPokemon The Pokemon to be added to a BST rooted at current.
   * @return true if the newPokemon was successfully added to this PokemonTree, false otherwise
   */
  public static boolean addPokemonHelper(Pokemon newPokemon, PokemonNode current) {

    // return false; // remove this statement. A default return statement added to let this code
    // compile.

    // null case
    if (current == null) {
      current = new PokemonNode(newPokemon);
      return true;
    }
    
    // greater case
    if (current.getPokemon().compareTo(newPokemon) > 0) {
      if (current.getLeftChild() != null) {
        return addPokemonHelper(newPokemon, current.getLeftChild());
      } else {
        current.setLeftChild(new PokemonNode(newPokemon));
        return true;
      }
    }
    
    // lesser case
    if (current.getPokemon().compareTo(newPokemon) < 0) {
      if (current.getRightChild() != null) {
        return addPokemonHelper(newPokemon, current.getRightChild());
      } else {
        current.setRightChild(new PokemonNode(newPokemon));
        return true;
      }
    }
    return false;
  }

  /**
   * Adds a new Pokemon to this PokemonTree
   * 
   * @param newPokemon a new Pokemon to add to this BST.
   * @return true if the new was successfully added to this BST, and returns false if there is a
   *         match with this Pokemon already already stored in this BST.
   */
  public boolean addPokemon(Pokemon newPokemon) {

    boolean pokemonAdded;
    if (isEmpty()) { // Add new to an empty PokemonTree
      root = new PokemonNode(newPokemon);
      pokemonAdded = true;
      size++;
    } else { // Add new to an non-empty PokemonTree
    
      pokemonAdded = addPokemonHelper(newPokemon, root);
      if (pokemonAdded == true) {
        size++;
      }
    }
    return pokemonAdded;

  }

  /**
   * Recursive helper method which returns a String representation of the BST rooted at current. An
   * example of the String representation of the contents of a PokemonTree is provided in the
   * description of the above toString() method.
   * 
   * @param current reference to the current PokemonNode within this BST.
   * @return a String representation of all the Pokemons stored in the sub-tree PokemonTree rooted
   *         at current in increasing order with respect to the CP values. Returns an empty String
   *         "" if current is null.
   */
  public static String toStringHelper(PokemonNode current) {
    
    String str = "";
    if (current == null) {
      return str;
    } else {
      // left -> center -> right
      str += toStringHelper(current.getLeftChild());
      str += current.getPokemon().toString() + "\n";
      str += toStringHelper(current.getRightChild());
    }
    return str;
  }

  /**
   * Returns a String representation of all the Pokemons stored within this BST in the increasing
   * order, separated by a newline "\n". For instance: "[Pikachu CP:123 (A:1 S:2 D:3)]" + "\n" +
   * "[Eevee CP:224 (A:2 S:2 D:4)]" + "\n" + [Lapras CP:735 (A:7 S:3 D:5)] + "\n" + "[Mewtwo CP:999
   * (A:9 S:9 D:9)]" + "\n"
   * 
   * @return a String representation of all the Pokemons stored within this BST sorted in an
   *         increasing order with respect to the CP values. Returns an empty string "" if this BST
   *         is empty.
   */
  public String toString() {
    // [Hint]: call toStringHelper() method to help implement this behavior.
    return PokemonTree.toStringHelper(this.root); 
  }

  /**
   * Search for a Pokemon (Pokemon) given the CP value as lookup key.
   * 
   * @param cp combat power of a Pokemon
   * @return the Pokemon whose CP value equals our lookup key.
   * @throws a NoSuchElementException with a descriptive error message if there is no Pokemon found
   *           in this BST having the provided CP value
   */
  public Pokemon lookup(int cp) {
    return lookupHelper(cp, this.root);
  }

  /**
   * Recursive helper method to lookup a Pokemon given a reference Pokemon with the same CP in the
   * subtree rooted at current
   * 
   * @param cp      the CP of the Pokemon target we are looking for in the BST rooted at current.
   * @param current "root" of the subtree we are looking for a match to find within it.
   * @return reference to the Pokemon stored stored in this BST whose CP matches the query value.
   * @throws NoSuchElementException with a descriptive error message if there is no Pokemon whose 
   *                                CP value matches target value stored in this BST.
   */
  public static Pokemon lookupHelper(int cp, PokemonNode current) {

    if (current == null) {
      throw new NoSuchElementException("No Such Element found.");
    }
    // left subtree
    if (cp < current.getPokemon().getCP()) {
      current = current.getLeftChild();
      // recurse
      return PokemonTree.lookupHelper(cp, current);
    }
    // right subtree
    else if (cp > current.getPokemon().getCP()) {
      current = current.getRightChild();
      // recurse
      return PokemonTree.lookupHelper(cp, current);
    }
    // that node
    else {
      return current.getPokemon();
    }

  }

  /**
   * Computes and returns the height of this BST, counting the number of nodes (PokemonNodes) from
   * root to the deepest leaf.
   * 
   * @return the height of this Binary Search Tree
   */
  public int height() {
    return heightHelper(root);
  }

  /**
   * Recursive helper method that computes the height of the subtree rooted at current
   * 
   * @param current pointer to the current PokemonNode within a PokemonTree
   * @return height of the subtree rooted at current, counting the number of PokemonNodes
   */
  public static int heightHelper(PokemonNode current) {

    // return 0; // remove this statement.

    if (current == null) {
      return 0;
    }
    if (current.getLeftChild() == null && current.getRightChild() == null) {
      // just the node, leaf node, thus return 1
      return 1;
    } else {
      // repeat with left
      int l = heightHelper(current.getLeftChild());
      // repeat with right
      int r = heightHelper(current.getRightChild());
      // return value
      return (1 + ((l > r) ? l : r));

    }
  }

  /**
   * Returns the Pokemon of the least powerful Pokemon in this BST.
   * 
   * @return the Pokemon of the least powerful Pokemon in this BST and null if this tree is empty.
   */
  public Pokemon getLeastPowerfulPokemon() {

    // Feel free to implement either the iterative OR the recursive version of this
    // method.

    // If you choose recursion to implement this behavior, add a private helper
    // method and call it here.
    if(this.isEmpty())
      return null;
    PokemonNode curNode = this.root;
    // keep looking for last node on the left, it is smallest in a BST
    while(curNode.getLeftChild()!=null) {
      curNode = curNode.getLeftChild();
    }

    return curNode.getPokemon(); 
  }

  /**
   * Returns the Pokemon of the most powerful Pokemon in this BST.
   * 
   * @return the Pokemon of the most powerful Pokemon in this BST, and null if this tree is empty.
   */
  public Pokemon getMostPowerfulPokemon() {

    // Feel free to implement either the iterative OR the recursive version of this
    // method.

    // If you choose recursion to implement this behavior, add a private helper
    // method and call it here.

    if(this.isEmpty())
      return null;
    PokemonNode curNode = this.root;
    // keep looking for last node on the RIGHT, it is largest in a BST
    while (curNode.getRightChild() != null) {
      curNode = curNode.getRightChild();
    }

    return curNode.getPokemon(); 
  }

}
