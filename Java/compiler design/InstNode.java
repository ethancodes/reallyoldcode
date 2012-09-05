/**
 *  a linked list of instructions
 */

public class InstNode
{
  private int opcode, operand;
  private InstNode next;

  /**
   *  Constructor
   */
  public InstNode() { next = null; }
  /**
   *  Constructor with parameters
   *
   *  @param opc The opcode
   *  @param opm the operand
   */
  public InstNode(int opc, int opn)
  {
    setOPCode(opc); setOperand(opn);
    next = null;
  }
  /**
   *  get the opcode
   *
   *  @return the opcode for this instruction
   */
  public int getOPCode() { return opcode; }
  /**
   *  get the operand
   *
   *  @return the operand for this instruction
   */
  public int getOperand() { return operand; }
  /**
   *  set the opcode
   *
   *  @param opc the new opcode for this instruction
   */
  public void setOPCode(int opc) { opcode = opc; }
  /**
   *  set the operand
   *
   *  @param opn the new operand for this instruction
   */
  public void setOperand(int opn) { operand = opn; }
  /**
   *  get the next InstNode
   *
   *  @return the next InstNode
   */
  public InstNode getNext() { return next; }
  /**
   *  set the next InstNode
   *
   *  @param n the new next InstNode
   */
  public void setNext(InstNode n) { next = n; }
} //end of class InstNode

