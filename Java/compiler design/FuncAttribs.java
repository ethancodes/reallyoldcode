/**
 *  Stores attributes for functions.
 *
 *  @author Ethan Georgi
 */

public class FuncAttribs
{
  /**
   *  Quadruple number
   */
  /**
   *  Number of parameters.
   */
  public int qnum, pcnt;
  /**
   *  The types of those parameters.
   */
  public int[] type; // size 10, or something

  /**
   *  Constructor.
   */
  public FuncAttribs()
  {
    qnum = 0; pcnt = 0; type = new int[10];
  }
  /**
   *  Constructor with parameters.
   *
   *  @param q Quadruple Number
   *  @param p Parameter Count
   *  @param t Array of types of those parameters
   */
  public FuncAttribs(int q, int p, int[] t)
  {
    qnum = q; pcnt = p; type = t;
  }
}
