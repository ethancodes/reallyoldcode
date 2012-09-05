/**
 *  A class for keeping track of instructions.
 *
 *  @author Ethan Georgi
 */

public class Instruction
{
  private int opcode, opnd;

  /**
   *  Constructor
   */
  public Instruction() {}
  /**
   *  Constructor with parameters!
   *
   *  @param opc the opcode
   *  @param opn the operand
   */
  public Instruction(int opc, int opn) { setOPCode(opc); setOPND(opn); }
  /**
   *  set the opcode
   *
   *  @param opc the new opcode
   */
  public void setOPCode(int opc) { opcode = opc; }
  /**
   *  set the operand
   *
   *  @param opn the new operand
   */
  public void setOPND(int opn) { opnd = opn; }
  /**
   *  what is the opcode?
   *
   *  @return the opcode
   */
  public int getOPCode() { return opcode; }
  /**
   *  what is the operand?
   *
   *  @return the operand
   */
  public int getOPND() { return opnd; }
  /**
   *  display the instruction
   *
   *  @param newline Append a newline to this?
   */
  public void show(boolean newline)
  {
    System.out.print("OPCODE" + Integer.toString(getOPCode()) + " ");
    System.out.print("OPND" + Integer.toString(getOPND()));
    if (newline) { System.out.println(""); }
  }

} //end of class

