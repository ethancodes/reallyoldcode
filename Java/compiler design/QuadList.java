/**
 *  A class for lists of quadruples.
 *
 *  @author Ethan Georgi
 */

public class QuadList
{
  private static int QLSEGSIZE = 5;
  private Quadruple[] quadSeg = new Quadruple[QLSEGSIZE];
  private QuadList next;

  /**
   *  Constructor.
   */
  public QuadList()
  {
    Token t1 = new Token(TokenClass.RESERVED, 0),
          t2 = new Token(TokenClass.RESERVED, 0);
    Quadruple q = new Quadruple(0, t1, t2, 0);
    int i;
    for (i = 0; i < QLSEGSIZE; i++) { quadSeg[i] = q; }
    next = null;
  }

  /**
   *  make room for a new quadruple
   *
   *  @param inx the index where we should put this
   *  @param quad the quadruple to store
   */
  public void enterQuad(int inx, Quadruple quad)
  {
    int segment, offset;
    QuadList p, q;
    segment = (inx - 1) / QLSEGSIZE;
    offset = (inx - 1) % QLSEGSIZE;
    p = this; q = null;
    int i;
    for (i = 0; i < segment; i++) { q = p; p = p.next; }
    if (p == null)
    {
      p = new QuadList();
      for (i = 0; i < QLSEGSIZE; i++) { p.quadSeg[i].setOper(0); }
      q.next = p;
      p.next = null;
    }
    p.quadSeg[offset] = quad;
  }

  /**
   *  change a quadruple around
   *
   *  @param inx the index of the quadruple to change
   *  @param qnum the new result
   */
  public void repairQuad(int inx, int qnum)
  {
    int segment, offset;
    QuadList p;
    segment = (inx - 1) / QLSEGSIZE;
    offset = (inx - 1) % QLSEGSIZE;
    p = this;
    int i;
    for (i = 0; i < segment; i++) { p = p.next; }
    p.quadSeg[offset].setResult(qnum);
  }

  /**
   *  change a quadruple around
   *
   *  @param inx the index of the quadruple to change
   *  @param locals the parameter
   */
  public void adjustQuad(int inx, int locals)
  {
    int segment, offset;
    QuadList p;
    segment = (inx - 1) / QLSEGSIZE;
    offset = (inx - 1) % QLSEGSIZE;
    p = this;
    int i;
    for (i = 0; i < segment; i++) { p = p.next; }
    p.quadSeg[offset].getOpnd1().setTokenValue(locals);
  }

  /**
   *  get me a quadruple
   *
   *  @param n the index of the quadruple you want
   *  @return the quadruple you asked for
   */
  public Quadruple getQuadSeg(int n) { return quadSeg[n]; }

  /**
   *  get the next segment of the linked list
   *
   *  @return the next QuadList
   */
  public QuadList getNext() { return next; }

  /**
   *  display the quads we've got so far
   */
  public void showQuadList()
  {
    int i;
    QuadList p = this;
    while (p != null)
    {
      for (i = 0; i < 5; i++)
      {
        if (p.getQuadSeg(i) != null) { p.getQuadSeg(i).show(); }
        System.out.print(" | ");
      }
      System.out.println("");
      p = p.next;
    }
  }
}
