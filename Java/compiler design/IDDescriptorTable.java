/**
 *  Identifier descriptor table. stores variables.
 *
 *  @author Ethan Georgi
 */

public class IDDescriptorTable
{
  /**
   *  Linked list of Attributes
   */
  public class AttribNode
  {
    private Attributes attribs;
    private AttribNode next;
    /**
     *  Constructor.
     */
    public AttribNode()
    {
      attribs = new Attributes(-1, -2000, -1, false);
    }
    /**
     *  get the Attributes for this node
     *
     *  @return This node's Attributes
     */
    public Attributes getAttribs() { return attribs; }
    /**
     *  Fetch the next AttribNode
     *
     *  @return the next AttribNode
     */
    public AttribNode getNext() { return next; }
    /**
     *  Set the next AttribNode
     *
     *  @param an the new next AttribNode
     */
    public void setNext(AttribNode an) { next = an; }
  }

  /**
   *  Linked List of arrays of AttribNodes
   */
  public class IDDesTabSegment
  {
    private AttribNode[] idSegment;
    private IDDesTabSegment next;
    /**
     *  Constructor.
     */
    public IDDesTabSegment()
    {
      idSegment = new AttribNode[5];
      int i;
      for (i = 0; i < 5; i++) { idSegment[i] = null; }
      next = null;
    }
    /**
     *  Get the next IDDesTabSegment
     *
     *  @return the next IDDesTabSegment
     */
    public IDDesTabSegment getNext() { return next; }
    /**
     *  Set the next IDDesTabSegment
     *
     *  @param idts the new next IDDesTabSegment
     */
    public void setNext(IDDesTabSegment idts) { next = idts; }
    /**
     *  Get the AttribNode within the particular segment.
     *
     *  @param n the IDSegment to get the AttribNode from
     *  @return the AttribNode
     */
    public AttribNode getIDSegment(int n) { return idSegment[n]; }
    /**
     *  Set the AttribNode within the particular segment.
     *
     *  @param n the IDSegment to set the AttribNode
     *  @param an the AttribNode to store
     */
    public void setIDSegment(int n, AttribNode an) { idSegment[n] = an; }
  }

  private IDDesTabSegment idts;
  public IDDescriptorTable() { idts = null; }

  /**
   *  Create an entry. make a new variable.
   *
   *  @param inx the index to store this in
   *  @param level the level of the variable (1=Local, 0=Global)
   *  @return true if variable already defined in this index
   */
  public boolean createEntry(int inx, int level)
  {
    boolean dupe = false;
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    IDDesTabSegment q = null;
    int i;
    for (i = 0; i <= segment; i++)
    {
      if (p != null) { q = p; p = p.getNext(); }
      else
      {
        p = new IDDesTabSegment();
        if (q == null) { idts = p; }
        else { q.setNext(p); }
        q = p;
        p = p.getNext();
      }
    } //end for i
    if ((q.getIDSegment(offset) != null)
      && (q.getIDSegment(offset).getAttribs().level == level))
    { dupe = true; }
    else
    {
      dupe = false;
      AttribNode r = new AttribNode();
      r.setNext(q.getIDSegment(offset));
      q.setIDSegment(offset, r);
    }
    return dupe;
  }

  /**
   *  Store the level of the variable
   *
   *  @param inx the index to store this in
   *  @param level the level of the variable (1=Local, 0=Global)
   */
  public void saveLevel(int inx, int level)
  {
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    int i;
    for (i = 0; i < segment; i++) { p = p.getNext(); }
    p.getIDSegment(offset).getAttribs().level = level;
  }

  /**
   *  Store the address of the variable
   *
   *  @param inx the index to store this in
   *  @param addr the address of the variable
   */
  public void saveAddr(int inx, int addr)
  {
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    int i;
    for (i = 0; i < segment; i++) { p = p.getNext(); }
    p.getIDSegment(offset).getAttribs().RAMaddr = addr;
  }

  /**
   *  Store the type of the variable
   *
   *  @param inx the index to store this in
   *  @param type the type of the variable (1=Local, 0=Global)
   */
  public void saveType(int inx, int type)
  {
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    int i;
    for (i = 0; i < segment; i++) { p = p.getNext(); }
    p.getIDSegment(offset).getAttribs().type = type;
  }

  /**
   *  Store whether this is a function or not.
   *
   *  @param inx the index to store this in
   *  @param func i'm a little func-tion, short and stout.
   */
  public void saveKind(int inx, boolean func)
  {
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    int i;
    for (i = 0; i < segment; i++) { p = p.getNext(); }
    p.getIDSegment(offset).getAttribs().func = func;
  }

  /**
   *  Fetch the Attributes of the variable in question
   *
   *  @param inx the index of the variable to fetch.
   *  @return the Attributes of the variable you asked for
   */
  public Attributes retrieveAttributes(int inx)
  {
    Attributes a = null;
    int segment = inx / 5;
    int offset = inx % 5;
    IDDesTabSegment p = idts;
    int i = 0;
    while ((p != null) && (i < segment))
    {
      p = p.getNext();
      i++;
    }
    if ((p != null) && (p.getIDSegment(offset) != null))
      { a = p.getIDSegment(offset).getAttribs(); }
    return a;
  }

  /**
   *  Removes local variables. Run for the hills.
   */
  public void clearLevel1Entries()
  {
    IDDesTabSegment p = idts;
    AttribNode r;
    while (p != null)
    {
      int offset;
      for (offset = 0; offset < 5; offset++)
      {
        r = p.getIDSegment(offset);
        if ((r != null) && (r.getAttribs().level == 1))
        {
          p.setIDSegment(offset, r.getNext());
          r = null;
        }
      }
      p = p.getNext();
    } //end while
  }

  /**
   *  show all the variables. kinda doesn't work.
   */
  public void show()
  {
    IDDesTabSegment p = idts;
    int i;
    while (p != null)
    {
      for (i = 0; i < 5; i++)
      {
        if (p.getIDSegment(i) != null)
        { p.getIDSegment(i).getAttribs().seeAttributes(); }
        System.out.println("");
      }
      p = p.getNext();
    }
  }
} //end of class
