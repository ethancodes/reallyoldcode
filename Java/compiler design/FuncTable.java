/**
 *  A data structure for storing functions.
 *
 *  @author Ethan Georgi
 */

public class FuncTable
{
  /**
   *  A linked list of FuncAttribs
   */
  public class IDFuncNode
  {
    private int index;
    private FuncAttribs fattribs;
    private IDFuncNode next;

  /**
   *  Constructor.
   */
    public IDFuncNode()
    {
      index = 0; fattribs = new FuncAttribs(); next = null;
    }
  /**
   *  The index of this node
   *
   *  @return Index of this node
   */
    public int getIndex() { return index; }
  /**
   *  Set the index of this node.
   *
   *  @param i The new index of this node.
   */
    public void setIndex(int i) { index = i; }
  /**
   *  Get the function attributes.
   *
   *  @return The function Attributes
   */
    public FuncAttribs getFAttribs() { return fattribs; }
  /**
   *  Set the function attributes.
   *
   *  @param f The new function Attributes
   */
    public void setFAttribs(FuncAttribs f) { fattribs = f; }
  /**
   *  Get the next node.
   *
   *  @return The next node.
   */
    public IDFuncNode getNext() { return next; }
  /**
   *  Set the next node.
   *
   *  @param n The new next node.
   */
    public void setNext(IDFuncNode n) { next = n; }
  }

  private IDFuncNode ifn;

  /**
   *  Constructor.
   */
  public FuncTable() { ifn = null; }
  /**
   *  Constructor with parameters...
   *
   *  @param blah The initial IDFuncNode to use.
   */
  public FuncTable(IDFuncNode blah) { ifn = blah; }

  /**
   *  Create an new IDFuncNode. This means a new function.
   *
   *  @param inx the index of where we should put it.
   */
  public void createEntry(int inx)
  {
    IDFuncNode p = ifn;
    IDFuncNode q = null;
    while (p != null) { q = p; p = p.getNext(); }
    IDFuncNode r = new IDFuncNode();
    r.setIndex(inx);
    r.getFAttribs().qnum = -1;
    r.getFAttribs().pcnt = -1;
    int i;
    for (i = 0; i < 10; i++) { r.getFAttribs().type[i] = -1; }
    r.setNext(null);
    if (q == null) { ifn = r; } else { q.setNext(r); }
  }

  /**
   *  Store the quadruple number.
   *
   *  @param inx the index of the function to store this in
   *  @param qnum the quadruple number to store
   */
  public void saveQNUM(int inx, int qnum)
  {
    IDFuncNode p = ifn;
    boolean found = false;
    while ((p != null) && !found)
    {
      if (p.getIndex() == inx)
      {
        p.getFAttribs().qnum = qnum;
        found = true;
      }
      else { p = p.getNext(); }
    }
  }

  /**
   *  Store the number of parameters.
   *
   *  @param inx the index of the function to store this in
   *  @param pcnt the number of parameters
   */
  public void savePCNT(int inx, int pcnt)
  {
    IDFuncNode p = ifn;
    boolean found = false;
    while ((p != null) && !found)
    {
      if (p.getIndex() == inx)
      {
        p.getFAttribs().pcnt = pcnt;
        found = true;
      }
      else { p = p.getNext(); }
    }
  }

  /**
   *  Store the type of this parameter
   *
   *  @param inx the index of the function to store this in
   *  @param pcnt which parameter
   *  @param type the type of the parameter
   */
  public void saveTYPE(int inx, int pcnt, int type)
  {
    IDFuncNode p = ifn;
    boolean found = false;
    while ((p != null) && !found)
    {
      if (p.getIndex() == inx)
      {
        p.getFAttribs().type[pcnt] = type;
        found = true;
      }
      else { p = p.getNext(); }
    }
  }

  /**
   *  Store everything. Performs the functions of
   *  saveQNUM(int, int), savePCNT(int, int), saveTYPE(int, int, int)
   *
   *  @param inx the index of the function to store this in
   *  @param qnum the quadruple number to store
   *  @param pcnt which parameter
   *  @param type the type of the parameter
   */
  public void save(int inx, int qnum, int pcnt, int type)
  {
    IDFuncNode p = ifn;
    boolean found = false;
    while ((p != null) && !found)
    {
      if (p.getIndex() == inx)
      {
        p.getFAttribs().qnum = qnum;
        p.getFAttribs().pcnt = pcnt;
        p.getFAttribs().type[pcnt] = type;
        found = true;
      }
      else { p = p.getNext(); }
    }
  }

  /**
   *  Fetch the attributes of the function at index inx
   *
   *  @param inx the index of the function to get attributes of
   *  @return the function attributes
   */
  public FuncAttribs retrieveFAttribs(int inx)
  {
    IDFuncNode p = ifn; boolean found = false;
    FuncAttribs f = new FuncAttribs();
    while ((p != null) && !found)
    {
      if (p.getIndex() == inx) { f = p.getFAttribs(); found = true; }
      else { p = p.getNext(); }
    }
    return f;
  }

} //end of class
