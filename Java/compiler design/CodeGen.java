/**
 *  Generates code for the g592 machine
 *
 *  @author Ethan Georgi
 */

public class CodeGen
{
  private qFiler qf;
  private codFiler cf;
  private InstList iList;
  private int comparetype; //0=none 1=LT 2=LE 3=EQ 4=GE 5=GT 6=NE

  /**
   *  Constructor.
   */
  public CodeGen()
  {
    qf = new qFiler();
    cf = new codFiler();
    iList = new InstList();
    comparetype = 0;
  }

  private void emitAllInstructions()
  {
    int iNum = 0;
    InstNode q;
    HeadNode p = iList.getHead();
    while (p != null)
    {
      q = p.getInstNext();
      while (q != null)
      {
        cf.emitCode(new Instruction(q.getOPCode(), q.getOperand()), iNum);
        iNum++;
        q = q.getNext();
      }
      p = p.getHeadNext();
    }
  }

  private int setAddrMode(int opco, Token opnd)
  {
    int newopco = opco;
    switch (opnd.getTokenClass())
    {
      case 1 :
      case 4 :
      case 6 :
        if ((opnd.getTokenValue() / 2000) == 0) { newopco += 5; }
        else { opnd.setTokenValue(opnd.getTokenValue() % 2000); }
        break;
      case 2 :
      case 3 :
        newopco++;
        break;
      default :
        System.out.print("Error in opnd class of a quadruple! ");
        opnd.display(); System.out.println("");
        System.exit(0);
    }
    return newopco;
  }

  /**
   *  Store the filename we're working on.
   */
  public void distributeProgName(String pname)
  {
    qf.store(pname.substring(0, pname.lastIndexOf('.')) + ".qd");
    cf.store(pname.substring(0, pname.lastIndexOf('.')) + ".cod");
  }

  /**
   *  Display the code file.
   */
  public void seeInstructions() { cf.seeCodeFile(); }

  /**
   *  Generate instructions based on the quadruples produced.
   */
  public void generate()
  {
    Token temp = new Token(0, 0);
    Quadruple quad;
    int opcode = 0, instnum = 0, curinstnum = 0;
    boolean end = false;
    qf.retrieveQuadFile(qf.getQuadFilename());
    while (!end)
    {
      quad = qf.getNextQuad();
      iList.createEntry(quad);
      switch (quad.getOper())
      {
        case Globs.ASSIGNop :
          opcode = 110;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          temp.setTokenClass(1);
          temp.setTokenValue(quad.getResult());
          opcode = 160;
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 2;
          break;
        case Globs.READop :
          opcode = 520;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          instnum += 1;
          break;
        case Globs.WRITEop :
          opcode = 500;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          instnum += 1;
          break;
        case Globs.WRITELNop :
          iList.enterInst(511, new Token(2, 10));
          iList.enterInst(511, new Token(2, 13));
          instnum += 2;
          break;
        case Globs.ADDop :
        case Globs.SUBop :
        case Globs.MULop :
        case Globs.DIVop :
        case Globs.MODop :
          opcode = 110;
          opcode = setAddrMode(opcode, quad.getOpnd2());
          iList.enterInst(opcode, quad.getOpnd2());
          switch (quad.getOper())
          {
            case Globs.ADDop : opcode = 210; break;
            case Globs.SUBop : opcode = 260; break;
            case Globs.MULop : opcode = 310; break;
            case Globs.DIVop : opcode = 360; break;
            case Globs.MODop : opcode = 410; break;
          }
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          temp.setTokenClass(4);
          temp.setTokenValue(quad.getResult());
          opcode = 160;
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 3;
          break;
        case Globs.EQop :
        case Globs.NOTEQop :
        case Globs.LESSop :
        case Globs.GREATERop :
        case Globs.LESSEQop :
        case Globs.GREATEREQop :
          opcode = 110;
          opcode = setAddrMode(opcode, quad.getOpnd2());
          iList.enterInst(opcode, quad.getOpnd2());
          opcode = 460;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          curinstnum = instnum + 2;
          temp.setTokenClass(2); temp.setTokenValue(curinstnum + 3);
          switch (quad.getOper())
          {
            case Globs.EQop        : iList.enterInst(820, temp); break;
            case Globs.NOTEQop     : iList.enterInst(850, temp); break;
            case Globs.LESSop      : iList.enterInst(810, temp); break;
            case Globs.GREATERop   : iList.enterInst(830, temp); break;
            case Globs.LESSEQop    : iList.enterInst(860, temp); break;
            case Globs.GREATEREQop : iList.enterInst(840, temp); break;
          }
          temp.setTokenValue(0);
          iList.enterInst(111, temp);
          curinstnum = instnum + 4;
          temp.setTokenValue(curinstnum + 2);
          iList.enterInst(800, temp);
          temp.setTokenValue(1);
          iList.enterInst(111, temp);
          opcode = 160;
          temp.setTokenClass(4); temp.setTokenValue(quad.getResult());
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 7;
          break;
        case Globs.NOT :
          temp.setTokenClass(2); temp.setTokenValue(0);
          iList.enterInst(111, temp);
          opcode = 460;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          curinstnum = instnum + 2;
          temp.setTokenValue(curinstnum + 2);
          iList.enterInst(850, temp);
          temp.setTokenValue(1);
          iList.enterInst(111, temp);
          opcode = 160;
          temp.setTokenClass(4); temp.setTokenValue(quad.getResult());
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 5;
          break;
        case Globs.ODDop :
          opcode = 110;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          temp.setTokenClass(2); temp.setTokenValue(2);
          iList.enterInst(411, temp);
          opcode = 160;
          temp.setTokenClass(4); temp.setTokenValue(quad.getResult());
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 3;
          break;
        case Globs.Bop :
          if (quad.getResult() != (iList.getQuadNum() + 1))
          {
            temp.setTokenValue(0);
            iList.enterInst(800, temp);
            instnum += 1;
          }
          break;
        case Globs.BFop :
          opcode = 110;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          temp.setTokenClass(2); temp.setTokenValue(0);
          iList.enterInst(461, temp);  
          temp.setTokenClass(2); temp.setTokenValue(quad.getResult());
          opcode = 820;
          iList.enterInst(opcode, temp);
          instnum += 3;
          break;
        case Globs.JSRop :
          temp.setTokenValue(0);
          iList.enterInst(870, temp);
          instnum += 1;
          break;
        case Globs.RTNop :
          temp.setTokenValue(0);
          iList.enterInst(900, temp);
          instnum += 1;
          break;
        case Globs.ASPop :
          if (quad.getOpnd1().getTokenValue() != 0)
          {
            iList.enterInst(651, quad.getOpnd1());
            instnum += 1;
          }
          break;
        case Globs.FSPop :
          if (quad.getOpnd1().getTokenValue() != 0)
          {
            iList.enterInst(661, quad.getOpnd1());
            instnum += 1;
          }
          break;
        case Globs.PSHop :
          opcode = 600;
          opcode = setAddrMode(opcode, quad.getOpnd1());
          iList.enterInst(opcode, quad.getOpnd1());
          instnum += 1;
          break;
        case Globs.POPop :
          opcode = 610;
          temp.setTokenClass(1); temp.setTokenValue(quad.getResult());
          opcode = setAddrMode(opcode, temp);
          iList.enterInst(opcode, temp);
          instnum += 1;
          break;
        case Globs.HALTop :
          temp.setTokenValue(0);
          iList.enterInst(990, temp);
          instnum += 1;
          end = true; //YAY!
          break;
        default :
          System.out.print("Unidentified quadruple: ");
          System.out.println(quad.getOper());
          System.exit(0);
      } //end of switch quad oper
    } //end while not end
    iList.repairJumps();
    qf.closeQuadFile();
    cf.createCodeFile();
    emitAllInstructions();
    cf.closeCodeFile();
  }
} //end of class

