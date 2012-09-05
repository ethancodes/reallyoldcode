/**
 *  Stores instruction information.
 *
 *  @author Ethan Georgi
 */

public class HeadNode
{
  private int quadNum, instNum, jumpToQuad;
  private HeadNode next;
  private InstNode inst;

  /**
   *  Constructor.
   */
  public HeadNode() { next = null; }
  /**
   *  Constructor with parameters.
   *
   *  @param q the quadruple number
   *  @param i the instruction number
   *  @param j the number of the quadruple this instruction jumps to
   */
  public HeadNode(int q, int i, int j)
  {
    setQuadNum(q); setInstNum(i); setJumpToQuad(j);
    next = null; inst = null;
  }
  /**
   *  Fetch the quadruple number
   *
   *  @return The quadruple number
   */
  public int getQuadNum() { return quadNum; }
  /**
   *  Fetch the instruction number
   *
   *  @return The instruction number
   */
  public int getInstNum() { return instNum; }
  /**
   *  Fetch the quadnum we jump to
   *
   *  @return The quadruple number we jump to
   */
  public int getJumpToQuad() { return jumpToQuad; }
  /**
   *  Set the quadruple number
   *
   *  @param q The quadruple number
   */
  public void setQuadNum(int q) { quadNum = q; }
  /**
   *  Set the instruction number
   *
   *  @param i The instruction number
   */
  public void setInstNum(int i) { instNum = i; }
  /**
   *  Set the quadruple number we jump to
   *
   *  @param j The quadruple number we jump to
   */
  public void setJumpToQuad(int j) { jumpToQuad = j; }
  /**
   *  Get the next HeadNode
   *
   *  @return the next HeadNode
   */
  public HeadNode getHeadNext() { return next; }
  /**
   *  Set the next HeadNode
   *
   *  @param n the new next HeadNode
   */
  public void setHeadNext(HeadNode n) { next = n; }
  /**
   *  Get the next InstNode
   *
   *  @return the next InstNode
   */
  public InstNode getInstNext() { return inst; }
  /**
   *  Set the next InstNode
   *
   *  @param n the new next InstNode
   */
  public void setInstNext(InstNode n) { inst = n; }
} //end of class HeadNode
