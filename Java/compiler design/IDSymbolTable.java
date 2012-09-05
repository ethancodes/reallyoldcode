import java.util.Vector;

/**
 *  The symbol table for indentifiers.
 *
 *  @author Ethan Georgi
 */

public class IDSymbolTable
{
  private static int NUMOFKEYS = 101;

  private static IDSymbolNode[] idsymtab = new IDSymbolNode[NUMOFKEYS];
  private static Vector str = new Vector();
  private static int freeIndex;
  private static int idindex;

  /**
   *  The magic hash function.
   *
   *  @param w, The indentifier to hash.
   *  @return The hashed value.
   */
  private int idHash(String w)
  {
    int MAX=65408;
    int sum = 0;
    int len = w.length(), i;
    for (i = 0; i < len; i++) { sum = (sum + w.charAt(i)) % MAX; }
    return sum % NUMOFKEYS;
  }

  /**
   *  Constructor.
   */
  public IDSymbolTable()
  {
    int i;
    for (i = 0; i < NUMOFKEYS; i++) { idsymtab[i] = null; }
    freeIndex = 0;
    idindex = 0;
  }

  /**
   *  Given an indentifier, hashes the indentifier and searches through
   *  the symbol table to see what's in the hashed location.
   *
   *  @param s, The indentifier.
   *  @return The index of the identifier that was found/inserted.
   */
  public int searchAndMaybeInsert(String s)
  {
    boolean done = false;
    int k, idinx = 0;
    String w = "";
    char t;
    int idhashvalue = idHash(s);
    IDSymbolNode p = idsymtab[idhashvalue];
    while (p != null && !done)
    {
      w = "";
      k = p.firstchar;
      while (k < (p.firstchar + p.length))
      {
        Character c = (Character)str.elementAt(k);
        w = w + c.charValue();
        k++;
      } //end while
      if (w.equals(s))
      {
        idinx = p.index;
        done = true;
      }
      else { p = p.link; }
    } //end while
    if (p == null)
    {
      int len = s.length();
      k = 0;
      while (k < len)
      {
        str.addElement(new Character(s.charAt(k)));
        k++;
      }
      IDSymbolNode q = idsymtab[idhashvalue];
      p = new IDSymbolNode(q);
      p.firstchar = freeIndex;
      p.length = k;
      p.index = idindex;
      idsymtab[idhashvalue] = p;
      freeIndex += k;
      idinx = idindex;
      idindex++;
    } //end of if p is null
    return idinx;
  }

} //end of class
