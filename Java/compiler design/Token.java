/**
 *  Token class. There aer several types of tokens, which are indicated by the value
 *  of the tokenClass variable.
 *  <LI>TokenClass.RESERVED (0)
 *  <LI>TokenClass.IDENTIFIER (1)
 *  <LI>TokenClass.NUMBER (2)
 *  <LI>TokenClass.SYMBOL (3)
 *  <LI>TokenClass.NEWLINE (10)
 *  <LI>TokenClass.ENDOFTEXT (11)
 *
 *  <P>More will probably be added. Check TokenClass.java for complete information.
 *  @author Ethan Georgi
 */

public class Token
{
  private int tokenClass;
  private int tokenValue;

  /**
   *  Constructor.
   */
  public Token() {}

  /**
   *  Constructor.
   *
   *  @param tc, The token class
   *  @param tv, The token value
   */
  public Token(int tc, int tv) { tokenClass = tc; tokenValue = tv; }

  /**
   *  Forces the class of this token.
   *
   *  @param tc, The new value of tokenClass
   */
  public void setTokenClass(int tc) { tokenClass = tc; }

  /**
   *  Forces the value of this token.
   *
   *  @param tc, The new value of tokenValue
   */
  public void setTokenValue(int tv) { tokenValue = tv; }

  /**
   *  What is the class of this token?
   *
   *  @return tokenClass
   */
  public int getTokenClass() { return tokenClass; }

  /**
   *  What is the value of this token?
   *
   *  @return tokenValue
   */
  public int getTokenValue() { return tokenValue; }

  /**
   *  Display the token. Ick. tokenValue or tokenClass? or both?
   *  I'll go with something like (tokenClass, tokenValue)
   */
  public void display()
  {
    System.out.print("(" + Integer.toString(tokenClass) + ", ");
    System.out.print(Integer.toString(tokenValue) + ")");
  }

  public String forDisplay()
  {
    String disp = Integer.toString(tokenClass) + " ";
    disp = disp + Integer.toString(tokenValue) + " ";
    return disp;
  }
}
