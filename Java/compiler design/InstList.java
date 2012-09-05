/**
 *  A data structure for storing instructions.
 *
 *  @author Ethan Georgi
 */

public class InstList
{
  private HeadNode head, hptr;
  private InstNode iptr;
  private int qnum, inum;

  /**
   *  Constructor
   */
  public InstList()
  {
    qnum = 0; inum = 0; head = null; hptr = null; iptr = null;
  }

  /**
   *  Get me the HeadNode for this instruction
   *
   *  @return the HeadNode
   */
  public HeadNode getHead() { return head; }

  /**
   *  Fetch the quadruple number
   *
   *  @return the quadruple number
   */
  public int getQuadNum() { return qnum; }

  /**
   *  Make me a new instruction
   *
   *  @param q the quadruple to make an instruction out of.
   */
  public void createEntry(Quadruple q)
  {
    qnum++;
    HeadNode p = new HeadNode(qnum, inum, 0);
    if ((q.getOper() == Globs.BFop) ||
        (q.getOper() == Globs.Bop) ||
        (q.getOper() == Globs.JSRop))
    {
      p.setJumpToQuad(q.getResult());
    }
    else { p.setJumpToQuad(0); }
    p.setInstNext(null); p.setHeadNext(null);
    if (head == null) { head = p; hptr = p; }
    else { hptr.setHeadNext(p); hptr = hptr.getHeadNext(); }
  }

  /**
   *  make this instruction
   *
   *  @param opco The opcode
   *  @param o The token to make the instruction out of
   */
  public void enterInst(int opco, Token o)
  {
    //System.out.print("Entering instruction " + Integer.toString(opco));
    //System.out.print(", "); o.display(); System.out.println("");
    inum++;
    InstNode r = new InstNode(opco, o.getTokenValue());
    if (hptr.getInstNext() == null)
    {
      hptr.setInstNext(r); iptr = r;
    }
    else
    {
      iptr.setNext(r);
      iptr = iptr.getNext();
    }                                      
  }

  /**
   *  make all the instructions jump to the right places
   */
  public void repairJumps()
  {
    int k = 1;
    InstNode r = new InstNode();
    HeadNode q = new HeadNode(), p = head;
    while (p != null)
    {
      if ((p.getJumpToQuad() != 0) &&
          (p.getJumpToQuad() != (p.getQuadNum() + 1)))
      {
        if (p.getJumpToQuad() <= k) { q = head; }
        else { q = p; }
        while (q.getQuadNum() != p.getJumpToQuad())
        {
          q = q.getHeadNext();
        }
        r = p.getInstNext();
        while ((r.getOPCode() / 100) != 8)
        {
          r = r.getNext();
        }
        r.setOperand(q.getInstNum());
        //r.setOperand(p.getJumpToQuad());
      }
      p = p.getHeadNext(); k++;
    } //while
  }
} //end of class
