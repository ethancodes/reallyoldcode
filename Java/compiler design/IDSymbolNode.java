/**
 *  A node for the symbol table.
 *
 *  @author Ethan Georgi
 */

public class IDSymbolNode
{
  /**
   *  The position of the first character
   */
  /**
   *  How long is the variable name
   */
  /**
   *  The index of the variable in the IDDescriptorTable
   */
  public int firstchar, length, index;
  /**
   *  Because this is part of a linked list...
   */
  public IDSymbolNode link;
  /**
   *  Constructor
   *
   *  @param l The IDSymbolNode we link to
   */
  public IDSymbolNode(IDSymbolNode l) { link = l; }
}
