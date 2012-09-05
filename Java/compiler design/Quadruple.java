/**
 *  A class for handling quadruples.
 *
 *  @author Ethan Georgi
 */

public class Quadruple
{
  private int oper;
  private Token opnd1, opnd2;
  private int result;

  /**
   *  Constructor
   */
  public Quadruple() {}
  /**
   *  Constructor with parameters.
   *
   *  @param op the operation (from Globs)
   *  @param op1 the first parameter
   *  @param op2 the second parameter
   *  @param res where to store the result
   */
  public Quadruple(int op, Token op1, Token op2, int res)
  {
    setQuad(op, op1, op2, res);
  }

  /**
   *  set the quadruple goodies
   *
   *  @param op the operation (from Globs)
   *  @param op1 the first parameter
   *  @param op2 the second parameter
   *  @param res where to store the result
   */
  public void setQuad(int op, Token op1, Token op2, int res)
  {
    oper = op; opnd1 = op1; opnd2 = op2; result = res;
  }

  /**
   *  show me the quad
   */
  public void show()
  {
    System.out.print(Integer.toString(oper) + ", ");

    if (opnd1 != null) { opnd1.display(); }
    else { System.out.print("-"); }
    System.out.print(", ");

    if (opnd2 != null) { opnd2.display(); }
    else { System.out.print("-"); }
    System.out.print(", ");

    System.out.print(Integer.toString(result));
  }

  /**
   *  get the operation
   *
   *  @return the operation
   */
  public int getOper() { return oper; }
  /**
   *  set the operation
   *
   *  @param op the operation
   */
  public void setOper(int op) { oper = op; }
  /**
   *  get the index where we're storing the results
   *
   *  @return the index where we're storing the results
   */
  public int getResult() { return result; }
  /**
   *  set the index where we're storing the results
   *
   *  @param r the index where we're storing the results
   */
  public void setResult(int r) { result = r; }
  /**
   *  get the first parameter
   *
   *  @return the first parameter
   */
  public Token getOpnd1() { return opnd1; }
  /**
   *  get the second parameter
   *
   *  @return the second parameter
   */
  public Token getOpnd2() { return opnd2; }
}
