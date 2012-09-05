/**
 *  Reserved word table. And baby there's a lot of them.
 *
 *  @author Ethan Georgi
 */

public class RWTable
{
  private static int[] C;
  private static String[] H;

  /**
   *  Constructor.
   */
  public RWTable()
  {
    C = new int[128];
    int ch;
    for (ch = 0; ch < 128; ch++) { C[ch] = 100; }
    C['A'] = -1;  C['B'] = -6; C['C'] = 0; C['D'] = 15; C['E'] = 0;
    C['F'] = -10; C['I'] = 0;  C['M'] = 0; C['N'] = 0;  C['O'] = 0;
    C['P'] = 1;   C['R'] = -2; C['S'] = 5; C['T'] = 2;  C['V'] = 2;
    C['W'] = 0;   C['Y'] = 7;

    H = new String[33];
    H[ 0] = "IF"; H[ 1] = "BOOLEAN"; H[ 2] = "CHAR"; H[ 3] = "VAR";
    H[ 4] = "BY"; H[ 5] = "FOR"; H[ 6] = "OF"; H[ 7] = "BEGIN";
    H[ 8] = "PROGRAM"; H[ 9] = "INTEGER"; H[10] = "THEN"; H[11] = "ARRAY";
    H[12] = "FUNCTION"; H[13] = "FALSE"; H[14] = "OR"; H[15] = "IS";
    H[16] = "WHILE"; H[17] = "READ"; H[18] = "WRITELN"; H[19] = "NOT";
    H[20] = "DO"; H[21] = "RETURN"; H[22] = "ELSE"; H[23] = "TO";
    H[24] = "WRITE"; H[25] = "CONST"; H[26] = "TRUE"; H[27] = "PROCEDURE";
    H[28] = "DIV"; H[29] = "FORWARD"; H[30] = "AND"; H[31] = "END";
    H[32] = "MOD";
  }

  /**
   *  Hash the supposed reserved word.
   *
   *  @param w, The supposed reserved word
   *  @return The integer hashed value
   */
  public int rwHash(String w)
  {
    int first = 0;
    int last = w.length() - 1;
    int h = C[w.charAt(first)] + C[w.charAt(last)] + last+1;
    h += w.charAt(last - 1) - 'A';
    return h;
  }

  /**
   *  Returns the reserved word with the given hash value.
   *
   *  @param rwhvalue, The reserved word hash value
   *  @return The reserved word
   */
  public String resWord(int rwhvalue)
  {
    if ((rwhvalue < 33) && (rwhvalue >= 0)) { return H[rwhvalue]; }
    else { return ""; }
  }

  /**
   *  Is this word a reserved word?
   *
   *  @param w, The maybe reserved word
   *  @return True if it's a reserved word, false otherwise
   */
  public boolean isReservedWord(String w)
  {
    return (w.equals(resWord(rwHash(w))));
  }
} //end of class
