/**
 *  Lang2 compiler.
 *
 *  @author Ethan Georgi
 */

public class L2Compiler
{
  private static Scanner scan;
  private static Parser  parse;
  private static CodeGen codegen;
  private static String pname;

  /**
   *  Let's get the ball rolling... uh-oh.
   */
  public static void main(String[] args)
  {
    try { pname = args[0]; } catch (Exception ex) { getProgName(); }

    System.out.print("Compiling ");
    System.out.println(pname.substring(0, pname.lastIndexOf('.')) + "...");

    scan = new Scanner();
    scan.distributeProgName(pname);
    scan.getProgram();
    scan.program();

    parse = new Parser();
    parse.distributeProgName(pname);
    parse.tokenSequence();

    codegen = new CodeGen();
    codegen.distributeProgName(pname);
    codegen.generate();

    System.out.println("Done.");
    System.exit(0);
  }

  private static void getProgName()
  {
    byte[] b = new byte[11];
    System.out.print("Enter the name of the Lang2 file to compile: ");
    try
    {
      System.in.read(b);
      pname = new String(b);
    }
    catch (Exception ex) { System.out.println(ex); System.exit(0); }
  }
}
