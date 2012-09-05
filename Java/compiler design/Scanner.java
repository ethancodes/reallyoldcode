/**
 *  Scanner. Reads in a lang2 program file, does some witchy stuff to it,
 *  and spits out whether the file is okay or not. Lexical analysis.
 *
 *  @author Ethan Georgi
 */

public class Scanner
{
  private static ChSet letters, digits, delimiters,
                                misc, comments, legalChars;
  private static int lineNumber;
  private static char ch;
  private static String w;
  private static char s;
  private static Token tok;
  private static prgFiler pf;
  private static tokFiler tf;
  private static RWTable rwt;
  private static IDSymbolTable idst;

  private interface SpecialSymbols
  {
    int
    SEMICOLON = 0, PERIOD = 1, COMMA = 2, LPAREN = 3, RPAREN = 4, PLUS = 5,
    MINUS = 6, MULTIPLY = 7, DIVIDE = 8, EQUAL = 9, COLONEQUAL = 10,
    COLON = 11, GREATER = 12, GREATEREQUAL = 13, LESS = 14, LESSEQUAL = 15,
    NOTEQUAL = 16, SINQUOTE = 17;
  }

  private void getNextWord()
  {
    w = "";
    while ((ch == ' ') || (ch == 9) || (ch == 13)) { getNextChar(); }
    if ((ch == '\\') && (pf.peekNextChar() == '\\')) //comments
    {
      while ((ch != 10) && !pf.endOfProgram) { getNextChar(); }
      //lineNumber++;
    }
    if (letters.in(ch)) { scanIdentifier(); }
    else if (digits.in(ch)) { scanNumeric(); }
    else if (delimiters.in(ch)) { scanSpecial(); }
    else if (comments.in(ch))
    {
      while ((ch != '}') && !pf.endOfProgram)
      {
        if (ch == 10) { lineNumber++; }
        getNextChar();
      }
      getNextChar();
    }
    else if (ch == 10)
    {
      lineNumber++;
      tok.setTokenClass(TokenClass.NEWLINE);
      tok.setTokenValue(lineNumber);
      tf.emitToken(tok);
      getNextChar();
    }
    else if (pf.endOfProgram) {}
    else
    {
      s = ch;
      w = w + s;
      error(2, lineNumber, ch);
    }
  }

  /**
   *  Handles errors which may occur.<P>
   *  1 = Numeric constant contains a letter<BR>
   *  2 = Illegal symbol found in program<BR>
   *  3 = Numeric value too large<BR>
   *  4 = Unterminated literal string<BR>
   *  5 = Unterminated literal character<BR>
   *
   *  @param n, The type of error.
   *  @param line, The line on which the error occured.
   */
  private void error(int n, int line)
  {
    String error = "";
    System.out.print("\n\nERROR detected on line " + Integer.toString(line));
    System.out.print(", #" + Integer.toString(n) + ": ");
    switch (n)
    {
      case 1 : error = "NUMERIC CONSTANT CONTAINS A LETTER!"; break;
      case 2 : error = "ILLEGAL SYMBOL FOUND IN PROGRAM!"; break;
      case 3 : error = "NUMERIC VALUE TOO LARGE!"; break; 
      case 4 : error = "UNTERMINATED LITERAL STRING!"; break;
      case 5 : error = "UNTERMINATED LITERAL CHARACTER!"; break;
    } //end of switch
    System.out.println(error + "\n");
    pf.seeErrorLines(line);
    pf.closeProgram();
    //tf.removeTokenFile();
    System.exit(0);
  }

  private void error(int n, int line, char c)
  {
    System.out.print("\n\nERROR detected on line " + Integer.toString(line));
    System.out.print(", #" + Integer.toString(n) + ": ");
    String error = "ILLEGAL SYMBOL FOUND IN PROGRAM! '" + c + "'";
    System.out.println(error + "\n");
    pf.seeErrorLines(line);
    pf.closeProgram();
    //tf.removeTokenFile();
    System.exit(0);
  }
  
  private boolean checkCH()
  {
    return (ch!=' ' && ch!='\\' && ch!=9 && ch!=10 && ch!=13 
      && !delimiters.in(ch) && !pf.endOfProgram);
  }

  private void getNextChar()
  {
    ch = pf.getNextChar();
    if ((ch >= 'a') && (ch <= 'z')) { ch = (char)(ch - 32); } //uppercase
  }

  /**
   *  Reads in an identifier and deals with it.
   */
  private void scanIdentifier()
  {
    while (checkCH())
    {
      s = ch;
      w = w + s;
      getNextChar();
      if (!legalChars.in(ch))
        { if ((ch!=13) && (ch!=10)) { error(2, lineNumber); } }
    } //end of while
    if (w.length() >= 2) 
    {
      int rwHashValue = rwt.rwHash(w);
      if (rwt.isReservedWord(w))
      {
       tok.setTokenClass(TokenClass.RESERVED);
       tok.setTokenValue(rwHashValue);
       tf.emitToken(tok);
      }
      else
      {
        tok.setTokenClass(TokenClass.IDENTIFIER);
        tok.setTokenValue(idst.searchAndMaybeInsert(w));
        tf.emitToken(tok);
      }
    }
    else
    {
      tok.setTokenClass(TokenClass.IDENTIFIER);
      tok.setTokenValue(idst.searchAndMaybeInsert(w));
      tf.emitToken(tok);
    }
  }


  /**
   *  This is some kind of number, right?
   */
  private void scanNumeric()
  {
    while (checkCH())
    {
      s = ch;
      w = w + s;
      getNextChar();
      if (letters.in(ch)) { error(1, lineNumber); }
      else if (!legalChars.in(ch)) { error(2, lineNumber); }
    } //end of while
    int value = strToInt(w);
    tok.setTokenClass(TokenClass.NUMBER);
    tok.setTokenValue(value);
    tf.emitToken(tok);   
  }

  /**
   *  Handles crazy stuff.
   */
  private void scanSpecial()
  {
    boolean justDidLitStr = false, justDidLitChar = false;
    int special = -1;
    s = ch;
    w = w + s;
    switch(ch)
    {
      case ';' : special = SpecialSymbols.SEMICOLON; getNextChar(); break;
      case '.' : special = SpecialSymbols.PERIOD; getNextChar(); break;
      case ',' : special = SpecialSymbols.COMMA; getNextChar(); break;
      case '(' : special = SpecialSymbols.LPAREN; getNextChar(); break;
      case ')' : special = SpecialSymbols.RPAREN; getNextChar(); break;
      case '+' : special = SpecialSymbols.PLUS; getNextChar(); break;
      case '-' : special = SpecialSymbols.MINUS; getNextChar(); break;
      case '*' : special = SpecialSymbols.MULTIPLY; getNextChar(); break;
      case '/' : special = SpecialSymbols.DIVIDE; getNextChar(); break;
      case '#' : special = SpecialSymbols.NOTEQUAL; getNextChar(); break;
      case '=' : special = SpecialSymbols.EQUAL; getNextChar(); break;
      case ':' : 
        special = SpecialSymbols.COLON;
        getNextChar();
        if (ch == '=')
        {
          s = ch;
          w = w + s;
          special = SpecialSymbols.COLONEQUAL;
          getNextChar();
        }
        break;
      case '>' : 
        special = SpecialSymbols.GREATER;
        getNextChar();
        if (ch == '=')
        {
          s = ch;
          w = w + s;
          special = SpecialSymbols.GREATEREQUAL;
          getNextChar();
        }
        break;
      case '<' : 
        special = SpecialSymbols.LESS;
        getNextChar();
        if ((ch == '=') || (ch == '>'))
        {
          s = ch;
          w = w + s;
          if (ch == '=') { special = SpecialSymbols.LESSEQUAL; }
          if (ch == '>') { special = SpecialSymbols.NOTEQUAL; }
          getNextChar();
        }
        break;
      case ''' : //literal character 'a' and so on
        justDidLitChar = true;
        getNextChar();
        tok.setTokenClass(TokenClass.LITCHAR);
        tok.setTokenValue(ch);
        tf.emitToken(tok);
        getNextChar();
        if (ch != ''') { error(5, lineNumber); }
        else { getNextChar(); };
        break;
      case '"' : //literal string
        justDidLitStr = true;
        getNextChar();
        boolean terminated = false;
        while (!pf.endOfProgram && !terminated)
        {
          if (ch == 13) { getNextChar(); } //do nothing
          else if (ch == 10)
          {
            lineNumber++;
            tok.setTokenClass(TokenClass.NEWLINE);
            tok.setTokenValue(lineNumber);
            tf.emitToken(tok);
            getNextChar();
          }
          else
          {
            if (ch == 34) { terminated = true; }
            else
            {
              tok.setTokenClass(TokenClass.LITSTR);
              tok.setTokenValue(ch);
              tf.emitToken(tok);
            }
            getNextChar();
          }
        }
        if (!terminated) { error(4, lineNumber); }
        break;
    } //end of switch
    if (justDidLitStr) { justDidLitStr = false; }
    else if (justDidLitChar) { justDidLitChar = false; }
    else
    {
      tok.setTokenClass(TokenClass.SYMBOL);
      tok.setTokenValue(special);
      tf.emitToken(tok);
    }
  }

  /**
   *  Turns a string of supposed numbers into a number. Or not.
   *
   *  @param s, The string of "numbers"
   *  @return The integer value
   */
  private int strToInt(String s)
  {
    int n = 0, i = 0, len = w.length(), digit;
    boolean ok = true;
    while (ok && (i < len))
    {
      digit = w.charAt(i) - '0';
      if (n <= ((32767-digit)/10))
      {
        n = (n * 10) + digit;
        i++;
      }
      else { ok = false; }
    } //end of while
    if (!ok) { error(3, lineNumber); }
    return n;
  }

  /**
   *  Constructor. Sets up everything.
   */
  public Scanner()
  {
    letters = new ChSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    digits = new ChSet("0123456789");
    delimiters = new ChSet(";.,():+-*/#=<>'" + (char)34);
    misc = new ChSet(" ");
    comments = new ChSet("{}");
    legalChars = letters;
    legalChars = legalChars.union(legalChars, digits);
    legalChars = legalChars.union(legalChars, delimiters);
    legalChars = legalChars.union(legalChars, misc);
    legalChars = legalChars.union(legalChars, comments);
    rwt = new RWTable();
    idst = new IDSymbolTable();
    lineNumber = 1;
    w = "";
    s = '\0';

    pf = new prgFiler();
    tf = new tokFiler();
    tok = new Token();
  }

  /**
   *  Get the program name to all the places it needs to be.
   *
   *  @param pname, The program file name
   */
  public void distributeProgName(String pname)
  {
    pf.store(pname);
    tf.store(pname.substring(0, pname.lastIndexOf('.')) + ".tok");
  }

  /**
   *  Open the program file
   */
  public void getProgram() { pf.retrieveProgram(pf.getProgName()); }

  /**
   *  Dump the program file to the screen.
   */
  public void seeProgram() { pf.seeProgramFile(); }

  public void program()
  {
    tf.createTokenFile();
    tok.setTokenClass(TokenClass.NEWLINE);
    tok.setTokenValue(lineNumber);
    tf.emitToken(tok);
    getNextChar();
    while (!pf.endOfProgram) { getNextWord(); }
    tok.setTokenClass(TokenClass.ENDOFTEXT);
    tok.setTokenValue(0);
    tf.emitToken(tok);
    tf.closeTokenFile();
    System.out.println("Scanner reports no errors in lexical analysis.");
    System.out.println("This is a good thing!");
  }

  /**
   *  Dump the token file to the screen.
   */
  public void seeTokens() { tf.seeTokenFile(); }
}
