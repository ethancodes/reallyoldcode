/**
 *  Class for "creating and massagin sets of characters".
 *
 *  @author Ethan Georgi
 */

public class ChSet
{
  private String set; //the set of characters

  /**
   *  Construct-o-rama!
   *
   *  @param s, The set of characters to begin with.
   */
  public ChSet(String s)
  {
    if (s == null) { set = null; }
    else //make sure we have no duplicates - UNIQUE CHARACTERS!
    {
      int size = s.length();
      String temp = "";
      int j;
      for (j = 0; j < size; j++)
      {
        if (temp.indexOf(s.charAt(j)) == -1) { temp = temp + s.charAt(j); }
      }
      set = temp;
    }
  } //end constructor

  /**
   *  How long is the set?
   *
   *  @return The length of the set of characters.
   */
  public int setLength() { return set.length(); }

  /**
   *  Assign a new set.
   *
   *  @param c, The new set of characters.
   */
  public void setSet(ChSet c) { set = c.getSet(); }

  public String getSet() { return set; }

  /**
   *  Is this character in the set?
   *
   *  @param ch, The character to look for in the set
   */
  public boolean in(char ch) { return (set.indexOf(ch) != -1); }

  /**
   *  Include a new character to the set.
   *
   *  @param ch, The new character to include
   */
  public void include(char ch) { if (!in(ch)) { set = set + ch; } }

  /**
   *  Add two ChSets together and return the result. Note that the
   *  resulting union removes duplicate characters.
   *
   *  @param c1, The first ChSet
   *  @param c2, The second ChSet
   *  @return The resulting union of c1 and c2.
   */
  public ChSet union(ChSet c1, ChSet c2)
  {
    String temp1 = c1.getSet();
    String temp2 = c2.getSet();
    int j;
    for (j = 0; j < temp2.length(); j++)
    {
      if (temp1.indexOf(temp2.charAt(j)) == -1)
      {
        temp1 = temp1 + temp2.charAt(j);
      }
    }
    return new ChSet(temp1);
  }

  /**
   *  Display the set.
   */
  public void display() { System.out.print("{" + getSet() + "}"); }

} //end of class
