import java.io.File;

/**
 *  File IO for quadruples.
 *
 *  @author Ethan Georgi
 */

public class qFiler
{
  private FileIO fio;
  private String fname;
  private String buffer;
  private Quadruple q;

  /**
   *  Constructor
   */
  public qFiler() { q = new Quadruple(); }

  /**
   *  save the filename to operate on for safe keeping
   *
   *  @param filename the name of the quad file to use
   */
  public void store(String filename) { fname = filename; }

  /**
   *  create a new quad file. calls removeQuadFile()
   */
  public void createQuadFile()
  {
    removeQuadFile();
    fio = new FileIO(fname, 1);
  }

  /**
   *  open the quad file specified
   *
   *  @param filename the quad file to open- use getQuadFilename
   */
  public void retrieveQuadFile(String filename)
  {
    fio = new FileIO(filename, 0);
    if (fio.numlines == 0 )
    {
      System.out.println("ERROR: File does not exist: " + filename);
      System.exit(0);
      fio = null;
    }
    store(filename);
  }

  /**
   *  close the quad file
   */
  public void closeQuadFile() { fio = null; }

  /**
   *  delete the quad file. calls closeQuadFile()
   */
  public void removeQuadFile()
  {
    closeQuadFile();
    File f = new File(fname);
    if (!f.delete()) {}
    f = null;
  }

  /**
   *  write a quadruple to the file
   *
   *  @param q the quadruple to write
   */
  public void emitQuad(Quadruple q)
  {
    int t1a, t1b, t2a, t2b;
    String qw = Integer.toString(q.getOper()) + " ";
    if (q.getOpnd1() == null)
    {
      qw = qw + "- ";
    }
    else
    {
      t1a = q.getOpnd1().getTokenClass();
      t1b = q.getOpnd1().getTokenValue();
      qw = qw + Integer.toString(t1a) + " " + Integer.toString(t1b) + " ";
    }
    if (q.getOpnd2() == null)
    {
      qw = qw + "- ";
    }
    else
    {
      t2a = q.getOpnd2().getTokenClass();
      t2b = q.getOpnd2().getTokenValue();
      qw = qw + Integer.toString(t2a) + " " + Integer.toString(t2b) + " ";
    }
    qw = qw + Integer.toString(q.getResult());
    //System.out.println("Writing " + qw);
    fio.writeln(qw);
  }

  /**
   *  show me the quad file!
   */
  public void seeQuadFile()
  {
    int i = 1, j = 1;
    int op, t1a, t1b, t2a, t2b, res;
    Quadruple q;
    Token opnd1 = new Token(), opnd2 = new Token();
    retrieveQuadFile(fname);
    System.out.println("The set of quadruples for " + fname + " is:");
    System.out.println("quad # | quadruple");
    System.out.println("------------------");
    while (!fio.eof())
    {
      q = getNextQuad();
      int c;
      for (c = 0; c < 6 - (Integer.toString(i).length()); c++) { System.out.print(" "); }
      System.out.print(Integer.toString(i) + "   ");
      q.show(); System.out.println("");
      i++; j++;
      if (j == 21)
      {
        System.out.print("Press ENTER to continue...");
        try { System.in.read(); }
        catch (Exception ex)
        {
          System.out.println("qFiler.seeQuadFile");
          System.out.println(ex);
          System.exit(0);
        }
        j = 1;
      }
    }
  }

  /**
   *  fetch the next quadruple from the file
   *
   *  @return the next quadruple
   */
  public Quadruple getNextQuad()
  {
    int op = 0, t1a = 0, t1b = 0, t2a = 0, t2b = 0, res = 0;
    Token opnd1 = new Token(), opnd2 = new Token();
    String line = fio.readln(), chr;
    boolean one = true, two = true;
    //break up the line into the parts of the quad
    int space = 0, ch = 0, counter = 0;
    while (counter < 6)
    {
      space = line.indexOf(" ", ch);
      if (space > 0) { chr = line.substring(ch, space); }
      else { chr = line.substring(ch); }
      //System.out.print(chr);
      if (chr.equals("-"))
      {
        if (counter == 1) { one = false; counter = 3; }
        else if (counter == 3) { two = false; counter = 5; }
      }
      else
      {
        switch (counter)
        {
          case 0 : op  = Integer.parseInt(chr); break;
          case 1 : t1a = Integer.parseInt(chr); break;
          case 2 : t1b = Integer.parseInt(chr); break;
          case 3 : t2a = Integer.parseInt(chr); break;
          case 4 : t2b = Integer.parseInt(chr); break;
          case 5 : res = Integer.parseInt(chr); break;
        }
        counter++;
      }
      ch = space + 1;
    }
    //done
    if (one) { opnd1.setTokenClass(t1a); opnd1.setTokenValue(t1b); }
    else { opnd1 = null; }
    if (two) { opnd2.setTokenClass(t2a); opnd2.setTokenValue(t2b); }
    else { opnd2 = null; }
    q.setQuad(op, opnd1, opnd2, res);

    return q;
  }

  /**
   *  what is the name of the quad file we're using
   *
   *  @return the name of the file we're using
   */
  public String getQuadFilename() { return fname; }
}
