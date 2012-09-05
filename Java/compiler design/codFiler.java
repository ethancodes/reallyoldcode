import java.io.File;

/**
 *  Class to read and write g592 machine code.
 *
 *  @author Ethan Georgi
 */

public class codFiler
{
  private FileIO fio;
  String fname;

  /**
   *  Constructor.
   */
  public codFiler() {}
  /**
   *  Save the filename to use for code.
   */
  public void store(String filename) { fname = filename; }
  /**
   *  Create a new file for code using the name stored by
   *  store(String filename). Calls removeCodeFile()
   */
  public void createCodeFile()
  {
    removeCodeFile();
    fio = new FileIO(fname, 1);
  }

  /**
   *  Open a code file for reading.
   *
   *  @param filename The code file to open- getCodeFilename()
   */
  public void retrieveCodeFile(String filename)
  {
    fio = new FileIO(filename, 0);
    if (fio.numlines == 0)
    {
      System.out.println("ERROR: File does not exist: " + filename);
      System.exit(0);
    }
  }

  /**
   *  Close the code file.
   */
  public void closeCodeFile() { fio = null; }

  /**
   *  Remove the code file. Calls closeCodeFile()
   */
  public void removeCodeFile()
  {
    closeCodeFile();
    File f = new File(fname);
    if (!f.delete()) {}
    f = null;
  }

  /**
   *  What's the name of the code file we're using?
   *
   *  @return The Code Filename
   */
  public String getCodeFilename() { return fname; }

  /**
   *  Write an instruction to the code file.
   *
   *  @param i The instruction to write
   *  @param inum The instruction number
   */
  public void emitCode(Instruction i, int inum)
  {
    fio.write(Integer.toString(i.getOPCode()) + " ");
    fio.write(Integer.toString(i.getOPND()) + " ");
    fio.writeln(Integer.toString(inum));
  }

  /**
   *  Fetch the next instruction from the code file.
   *
   *  @return The next instruction
   */
  public Instruction getNextInstruction()
  {
    int opcode, operand;
    Instruction i;
    String line = fio.readln(), chr;
    if (line != null)
    {
      int space = line.indexOf(" ");
      opcode = Integer.parseInt(line.substring(0, space));
      operand = Integer.parseInt(line.substring(space + 1));
      i = new Instruction(opcode, operand);
    }
    else { i = null; }
    return i;
  }

  /**
   *  Display the code file nicely.
   */
  public void seeCodeFile()
  {
    int n, k = 1; int opc = 0, opn = 0;
    Instruction i;
    String buffer;
    retrieveCodeFile(fname);
    System.out.println("The set of g592 instructions for " + fname + " is:");
    System.out.println("inst #     instruction");
    System.out.println("----------------------");
    while (!fio.eof())
    {
      i = getNextInstruction();
      buffer = Integer.toString(k);
      for (n = 0; n < (6 - buffer.length()); n++)
        { System.out.print(" "); }
      System.out.print(buffer + "     ");
      i.show(true);
    }
    closeCodeFile();
    
  }
} //end of class
