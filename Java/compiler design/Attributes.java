/**
 *  Stores the attributes of a variable.
 *
 *  @author Ethan Georgi
 */

public class Attributes
{
  /**
   *  Level 0 is global, level 1 is local
   */
  public int level;
  /**
   *  The address is RAM where the data is stored.
   */
  public int RAMaddr;
  /**
   *  The data type: boolean or integer
   */
  public int type;
  /**
   *  Is this a function?
   */
  public boolean func;
  /**
   *  Constructor.
   */
  public Attributes() {}
  /**
   *  Constructor with parameters.
   *
   *  @param l Level
   *  @param r RAM Address
   *  @param t Type
   *  @param f Function?
   */
  public Attributes(int l, int r, int t, boolean f)
  {
    level = l; RAMaddr = r; type = t; func = f;
  }

  /**
   *  Display the attribute
   */
  public void seeAttributes()
  {
    System.out.print("level" + Integer.toString(level) + " ");
    System.out.print("RAMaddr" + Integer.toString(RAMaddr) + " ");
    System.out.print("type" + Integer.toString(type) + " ");
    if (func) { System.out.print("FUNC"); }
    System.out.println("");
  }
}
