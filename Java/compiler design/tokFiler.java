import java.io.File;

/**
 *  Token file manager.
 *
 *  @author Ethan Georgi
 */

public class tokFiler
{
  private FileIO fio;
  private String fname;

  /**
   *  Constructor.
   */
  public tokFiler() {}

  /**
   *  Opens named file if it exists, else writes error message and terminates execution.
   *
   *  @param filename, The string name of the file to open
   */
  public void retrieveTokenFile(String filename)
  {
    fio = new FileIO(filename, 0);
    if (fio.numlines == 0 )
    {
      System.out.println("ERROR: File does not exist: " + filename);
      System.exit(0);
      fio = null;
    }
  }

  public String getProgName() { return fname; }

  /**
   *  Save file name in fname (memory). fname must have .extension.
   *
   *  @param filename, The name to store in fname
   */
  public void store(String filename) { fname = filename; }

  /**
   *  Creates a new token file. If one exists, this
   *  overwrites it.
   */
  public void createTokenFile()
  {
    removeTokenFile();
    fio = new FileIO(fname, 1);
  }

  /**
   *  Closes the file that was opened.
   */
  public void closeTokenFile() { fio = null; }

  /**
   *  Writes a token to the file.
   *
   *  @param tok, The token to write to the file
   */
  public void emitToken(Token tok)
  { 
    if (tok.getTokenClass() == TokenClass.NEWLINE) { fio.writeln(tok.forDisplay()); }
    else { fio.write(tok.forDisplay()); }
  }

  /**
   *  Displays entire file on output device. Includes line number for each
   *  line, and simulates "more" @ 20 lines.
   */
  public void seeTokenFile()
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
          System.out.println("tokFiler.seeTokenFile");
          System.out.println(ex);
          System.exit(0);
        }
        j = 1;
      }
    } //end of while
  }

  /**
   *  Returns the next token in the open token file.
   *
   *  @return The next token
   */
  public Token getNextToken()
  {
    char mumble = fio.readChar();
    String token = "";
    int tc, tv;
    while (mumble != ' ')
    {
      if (mumble == 13) {}
      else if (mumble == 10) {}
      else { token = token + mumble; }
      mumble = fio.readChar();
    } //end while
    tc = Integer.parseInt(token);
    token = "";
    mumble = fio.readChar();
    while (mumble != ' ')
    {
      token = token + mumble;
      mumble = fio.readChar();
    } //end while
    tv = Integer.parseInt(token);
    //System.out.print("GOT TOKEN (");
    //System.out.print(tc); System.out.print(", ");
    //System.out.print(tv); System.out.println(")");
    return new Token(tc, tv);
  }

  /**
   *  Get rid of that pesky token file!
   */
  public void removeTokenFile()
  {
    closeTokenFile();
    File f = new File(fname);
    if (!f.delete()) { System.out.println("No token file to delete."); }
    f = null;
  }

} //end of class
