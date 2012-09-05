/**
 *  Test file manager for source code files. You write the menu.
 *
 *  @author Ethan Georgi
 *  @version 2.11.1999
 */

public class prgFiler
{
  private FileIO fio;
  private String fname;
  public boolean endOfProgram;

  /**
   *  Constructor.
   */
  public prgFiler() { endOfProgram = false; }

  /**
   *  Opens named file if it exists, else writes error message and terminates execution.
   *
   *  @param filename, The string name of the file to open.
   */
  public void retrieveProgram(String filename)
  {
    fio = new FileIO(filename, 0);
    if (fio.numlines == 0 )
    {
      System.out.println("ERROR: File does not exist.");
      System.exit(0);
      fio = null;
    }
  }

  /**
   *  Save file name in fname (memory)
   *
   *  @param filename, The name to store in fname
   */
  public void store(String filename) { fname = filename; }

  /**
   *  Retrieve stored file name from fname
   *
   *  @return The name stored in fname
   */
  public String getProgName() { return fname; }

  /**
   *  Closes the file that was opened.
   */
  public void closeProgram()
  {
    fio = null;
  }

  /**
   *  Reads and returns next character in file.
   *
   *  @return The next character in the open file.
   */
  public char getNextChar()
  {
    char c = fio.readChar();
    if (c == 0) { endOfProgram = true; }
    return c;
  }

  /**
   *  Returns next character without extraction.
   *
   *  @return The next character in the open file.
   */
  public char peekNextChar()
  {
    char c = fio.peekChar();
    if (c == 0) { endOfProgram = true; }
    return c;
  }

  /**
   *  Displays entire file on output device. Includes line number for each
   *  program line, and simulates "more" @ 20 lines.
   */
  public void seeProgramFile()
  {
    int i = 1, j = 1;
    while (!fio.eof())
    {
      System.out.println(Integer.toString(i) + ":  " + fio.readln());
      i++; j++;
      if (j == 21)
      {
        System.out.print("Press ENTER to continue...");
        try { System.in.read(); }
        catch (Exception ex)
        {
          System.out.println("prgFiler.seeProgramFile");
          System.out.println(ex);
          System.exit(0);
        }
        j = 1;
      }
    }
    //fio.reset();
  }

  /**
   *  Displays 2 contiguous lines of file starting with line=linenum if
   *  linenum>1, else displays only first line.
   *
   *  @param linenum, The line to begin displaying lines.
   */
  public void seeErrorLines(int linenum)
  {
    if (linenum <= fio.numlines)
    {
      if (linenum > 1)
      {
        int i;
        for (i = 0; i < (linenum - 2); i++) { fio.readln(); }
        System.out.println(Integer.toString(i+1) + ":  " + fio.readln());
        System.out.println(Integer.toString(i+2) + ":  " + fio.readln());
      }
      else { System.out.println("1:  " + fio.readln()); }
    }
    else { seeErrorLines(fio.numlines); }
    //fio.reset();
  }
}