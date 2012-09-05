/**
 *  Parse for minLang2. and maybe more.
 *
 *  @author Ethan Georgi
 */

public class Parser
{
  /**
   *  Classes:
   *  <UL>
   *  <LI>1 identifier
   *  <LI>2 number
   *  <LI>3 boolean
   *  <LI>6 temporary
   *  <LI>10 line number
   *  </UL>
   */
  public interface Classes
  {
    int identclass = 1, numclass = 2, boolclass = 3,
    tempclass = 6, linenumclass = 10;
  }

  private static prgFiler pf;
  private static tokFiler tf;
  private static qFiler   qf;
  private static QuadList qList;
  private static Stack    s, qs;
  private int quadNum, linenum, level, idinx;
  private static Token tok;
  private static IDDescriptorTable iddt;
  private static FuncTable idft;
  private int vcnt, vcntprime, varcnt, varmax, localmax, localtempsmax;
  private int startAddrForGlobalTemps, startAddrForLocalTemps, pcnt, temp;
  private boolean negate, progIdent, arg, areturn;

  /**
   *  Constructor
   */
  public Parser()
  {
    s = new Stack(); s.push(Globs.BOTTOMMARK); //bottom marker
    qs = new Stack();
    quadNum = 1; linenum = 0; level = 0; 
    tok = new Token(TokenClass.RESERVED, 0);
    pf = new prgFiler();
    tf = new tokFiler();
    qf = new qFiler();
    iddt = new IDDescriptorTable();
    idft = new FuncTable();
    qList = new QuadList();
    vcnt = 0; vcntprime = 0; varcnt = 0; varmax = 2; localmax = 0;
    localtempsmax = 0; negate = false; progIdent = false; arg = false;
    startAddrForGlobalTemps = 2; startAddrForLocalTemps = 0;
    areturn = false;
  }

  /**
   *  Store me the name of the file i'm suppose to be using.
   *
   *  @param filename the name of the file to use
   */
  public void distributeProgName(String filename)
  {
    String shortName = filename.substring(0, filename.indexOf('.'));
    pf.store(filename);
    tf.store(shortName + ".tok");
    qf.store(shortName + ".qd");
  }

  /**
   *  allocate a temporary memory address
   *
   *  @return the address in memory that has been allocate temporarily
   */
  public int allocTempAddr()
  {
    int val = 0;
    if (level == 0) { varmax++; val = varmax + 2000; }
    else if (level == 1)
    {
      localmax--; val = localmax;
      if (localmax < localtempsmax) { localtempsmax = localmax; }
    }
    return val;
  }

  /**
   *  release temporary memory address allocated by allocTempAddr()
   */
  public void freeTempAddrs()
  {
    if (level == 0) { varmax = startAddrForGlobalTemps; }
    else if (level == 1) { localmax = startAddrForLocalTemps; }
  }

  /**
   *  figure out the operand information
   *
   *  @param opnd an empty Token
   *  @return the integer array with the level, type, func
   */
  public int[] getOPNDinfo(Token opnd)
  {
    int[] results = new int[3];
    Attributes declared = new Attributes();
    results[0] = -1;
    results[1] = 0;
    results[2] = 0;
    opnd.setTokenClass(qs.topop());
    if (opnd.getTokenClass() == TokenClass.TEMP)
    {
      results[1] = qs.topop();
      opnd.setTokenValue(qs.topop()); 
    }
    else if (opnd.getTokenClass() == TokenClass.NUMBER)
    {
      results[1] = 1; opnd.setTokenValue(qs.topop());
    }
    else if (opnd.getTokenClass() == TokenClass.SYMBOL)
    {
      results[1] = 2; opnd.setTokenValue(qs.topop());
    }
    else if (opnd.getTokenClass() == TokenClass.IDENTIFIER)
    {
      opnd.setTokenValue(qs.topop());
      declared = iddt.retrieveAttributes(opnd.getTokenValue());
      if (declared == null) { error(71, linenum); }
      if (declared.type == 0) { error(72, linenum); }
      results[0] = declared.level;
      opnd.setTokenValue(declared.RAMaddr);
      results[1] = declared.type;
      if (declared.func) { results[2] = 1; }
      else { results[2] = 0; }
    }
    else
    {
      System.out.print("problems in getOPNDinfo ");
      System.out.println(opnd.getTokenClass());
    }
    return results;
  }

  /**
   *  get the next token and figure out what it is
   *
   *  @param tok The temporary token the parser uses
   *  @return a special number
   */
  public int getInTok(Token tok)
  {
    int in = -1;
    switch (tok.getTokenClass())
    {
      case TokenClass.RESERVED :
        in = tok.getTokenValue();
        break;
      case TokenClass.IDENTIFIER :
        in = Globs.IDtok;
        break;
      case TokenClass.NUMBER :
        in = Globs.INTtok;
        break;
      case TokenClass.SYMBOL :
        in = Globs.SEMICOLONtok + tok.getTokenValue();
        break;
      case TokenClass.ENDOFTEXT :
        in = Globs.endmark;
        break;
    } // end of switch
    return in;
  }

  /**
   *  parses the token file
   */
  public void tokenSequence()
  {
    tf.retrieveTokenFile(tf.getProgName());
    int intok, stackSymbol, qnumSaved, idinx, intliteral;
    int activefunc = 0;
    boolean endParse = false;
    s.push(Globs.PROGRAM);
    tok = tf.getNextToken();
    while (!endParse)
    {
      //System.out.print(" S"); s.show(); System.out.print(" | ");
      //System.out.print("QS"); qs.show();
      while (tok.getTokenClass() == TokenClass.NEWLINE)
      {
        linenum++;
        tok = tf.getNextToken();
      }
      //tok.display();
      intok = getInTok(tok);
      stackSymbol = s.topop();
      //System.out.print(" level is " + Integer.toString(level));
      //System.out.print(" localmax is " + Integer.toString(localmax));
      //System.out.print(" quadNum is " + Integer.toString(quadNum));
      //if (!arg) { System.out.print(" !"); }
      //else { System.out.print("  "); }
      //System.out.print("arg");
      //System.out.print(" intok is " + Integer.toString(intok));
      //System.out.print(" stackSymbol: " + Integer.toString(stackSymbol));
      //System.out.println("");
      switch (stackSymbol)
      {
      //nonterminals
        case Globs.PROGRAM :
          if (intok == Globs.PROGRAMrw)
          {
            //qs.push(1);
            s.push(Globs.HALTop);  s.push(Globs.PERIOD);
            s.push(Globs.PROGNAME);
            s.push(Globs.BODY);    s.push(Globs.SETLEVEL0);
            s.push(Globs.REPAIR0); s.push(Globs.FUNCS);
            s.push(Globs.DECS); s.push(Globs.QNUM); s.push(Globs.QNUM);
            s.push(Globs.Bop);     s.push(Globs.HEADING);
          }
          else { error(10, linenum); }
          break;
        case Globs.HEADING :
          if (intok == Globs.PROGRAMrw)
          {
            s.push(Globs.IS); s.push(Globs.SAVEPROGINFO);
            s.push(Globs.PROGNAME);
            tok = tf.getNextToken();
          }
          else { error(11, linenum); }
          break;
        case Globs.PROGNAME :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.IDENT);
            if (!progIdent) { progIdent = true; }
            else
            {
              if (tok.getTokenValue() != 0)
              {
                error(12, linenum);
              }
            }
          }
          else { error(12, linenum); }
          break;
        case Globs.DECS :
          if (intok == Globs.VARrw)
          {
            s.push(Globs.MOREDECLISTS); s.push(Globs.DECLIST);
            s.push(Globs.INITDECS);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.FUNCTIONrw) {}
          else if (intok == Globs.BEGINrw) {}
          else { error(13, linenum); }
          break;
        case Globs.MOREDECLISTS :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.MOREDECLISTS); s.push(Globs.DECLIST);
          }
          else if (intok == Globs.BEGINrw) {}
          else if (intok == Globs.FUNCTIONrw) {}
          else { error(14, linenum); }
          break;
        case Globs.DECLIST :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.SAVEVARINFO); s.push(Globs.SEMICOLON);
            s.push(Globs.TYPE); s.push(Globs.COLON);
            s.push(Globs.IDENTLIST);
          }
          else { error(15, linenum); }
          break;
        case Globs.IDENTLIST :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.MOREIDENTS);
            s.push(Globs.IDENT); s.push(Globs.INCVCNT);
          }
          else { error(16, linenum); }
          break;
        case Globs.MOREIDENTS :
          if (intok == Globs.COMMAtok)
          {
            s.push(Globs.IDENTLIST);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.COLONtok) {}
          else if (intok == Globs.RPARENtok) {}
          else { error(17, linenum); }
          break;
        case Globs.RIDENTLIST :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.RIDENTLIST);
            s.push(Globs.IDENT);
          }
          else if (intok == Globs.COMMAtok)
          {
            s.push(Globs.RIDENTLIST);
            s.push(Globs.READop);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.RPARENtok) {}
          else { error(16, linenum); }
          break;
        case Globs.TYPE :
          if (intok == Globs.INTEGERrw)
          {
            s.push(Globs.INTEGER);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.BOOLEANrw)
          {
            s.push(Globs.BOOLEANsem);
            tok = tf.getNextToken();
          }
          else { error(18, linenum); }
          break;
        case Globs.FUNCS :
          if (intok == Globs.FUNCTIONrw)
          {
            s.push(Globs.FUNCS); s.push(Globs.SEMICOLON);
            s.push(Globs.FUNCTION);
          }
          else if (intok == Globs.BEGINrw) {}
          else { error(19, linenum); }
          break;
        case Globs.FUNCTION :
          if (intok == Globs.FUNCTIONrw)
          {
            s.push(Globs.CLEARLEVEL1); s.push(Globs.RTNop);
            s.push(Globs.ADJUSTASP); s.push(Globs.ADJUSTASP);
            s.push(Globs.FSPop); s.push(Globs.FUNCNAME);
            s.push(Globs.BODY); s.push(Globs.QNUM0); s.push(Globs.QNUM);
            s.push(Globs.ASPop); s.push(Globs.DECS);
            s.push(Globs.FUNCHEADING);
          }
          else { error(20, linenum); }
          break;
        case Globs.FUNCIDENT :
          s.push(Globs.IDENT);
          activefunc = tok.getTokenValue();
          break;
        case Globs.FUNCNAME :
          qs.push(quadNum);
          if (tok.getTokenValue() == activefunc) { tok = tf.getNextToken(); }
          else { error(1, linenum); }
          break;
        case Globs.FUNCHEADING :
          if (intok == Globs.FUNCTIONrw)
          {
            s.push(Globs.SAVEFUNCINFO); s.push(Globs.IS);
            s.push(Globs.TYPE); s.push(Globs.COLON);
            s.push(Globs.RPAREN); s.push(Globs.PARAMLIST);
            s.push(Globs.INITFUNC); s.push(Globs.LPAREN);
            s.push(Globs.FUNCIDENT);
            tok = tf.getNextToken();
          }
          else { error(21, linenum); }
          break;
        case Globs.PARAMLIST :
          if (intok == Globs.IDtok)
          {
            s.push(Globs.MOREPARAMS); s.push(Globs.FORMALPARAM);
            s.push(Globs.TYPE); s.push(Globs.COLON);
            s.push(Globs.IDENT);
          }
          else if (intok == Globs.RPARENtok) {}
          else { error(22, linenum); }
          break;
        case Globs.MOREPARAMS :
          if (intok == Globs.SEMICOLONtok)
          {
            s.push(Globs.PARAMLIST);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.RPARENtok) {}
          else { error(23, linenum); }         
          break;
        case Globs.IDENT :
          if (intok == Globs.IDtok)
          {
            s.push(tok.getTokenValue());
            s.push(Globs.IDsem);
            //if (!arg) { tok = tf.getNextToken(); }
            tok = tf.getNextToken();
          }
          else { error(39, linenum); }
          break;
        case Globs.IS :
          if (intok == Globs.ISrw)
          {
            tok = tf.getNextToken();
          }
          else { error(0, linenum); }
          break;
        case Globs.BODY :
          if (intok == Globs.BEGINrw)
          {
            s.push(Globs.END); s.push(Globs.MORESTMTS);
            s.push(Globs.STMT);
            tok = tf.getNextToken();
          }
          else { error(24, linenum); }
          break;
        case Globs.MORESTMTS :
          if (intok == Globs.SEMICOLONtok)
          {
            s.push(Globs.MORESTMTS); s.push(Globs.STMT);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.ENDrw) {}
          else if (intok == Globs.ELSErw) {}
          else { error(25, linenum); }
          break;
        case Globs.STMT :
          switch (intok)
          {
            case Globs.READrw :
              s.push(Globs.RPAREN); s.push(Globs.READop);
              s.push(Globs.RIDENTLIST); s.push(Globs.IDENT);
              s.push(Globs.LPAREN);
              tok = tf.getNextToken();
              break;
            case Globs.WRITErw :
              s.push(Globs.RPAREN); s.push(Globs.WRITEop);
              s.push(Globs.EXPR); s.push(Globs.LPAREN);
              tok = tf.getNextToken();
              break;
            case Globs.WRITELNrw :
              s.push(Globs.WRITELNop);
              tok = tf.getNextToken();
              break;
            case Globs.IDtok :
              s.push(Globs.ASSIGNop); s.push(Globs.CONDITION);
              s.push(Globs.ASSIGNMENT); s.push(Globs.IDENT);
              break;
            case Globs.IFrw :
              s.push(Globs.END);
              s.push(Globs.ELSE); //s.push(Globs.REPAIR0);
              s.push(Globs.MORESTMTS); s.push(Globs.STMT);
              s.push(Globs.QNUM); s.push(Globs.BFop);
              s.push(Globs.THEN); s.push(Globs.CONDITION);
              tok = tf.getNextToken();
              break;
            case Globs.WHILErw :
              s.push(Globs.END); s.push(Globs.PATCH);
              s.push(Globs.Bop); s.push(Globs.REPAIR1);
              s.push(Globs.MORESTMTS); s.push(Globs.STMT);
              s.push(Globs.QNUM); s.push(Globs.BFop);
              s.push(Globs.doSYN); s.push(Globs.CONDITION);
              s.push(Globs.QNUM);
              tok = tf.getNextToken();
              break;
            case Globs.RETURNrw :
              s.push(Globs.ASSIGNop); s.push(Globs.CONDITION);
              qs.push(activefunc); qs.push(1);
              tok = tf.getNextToken();
              break;
            case Globs.ENDrw :
            case Globs.ELSErw :
            case Globs.SEMICOLON :
              break;
            default : error(26, linenum);
          } //end of switch intok
          break;
        case Globs.ELSE :
          if (intok == Globs.ELSErw)
          {
            s.push(Globs.REPAIR0);
            s.push(Globs.MORESTMTS); s.push(Globs.STMT);
            s.push(Globs.QNUM); s.push(Globs.Bop);
            s.push(Globs.REPAIR1);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.ENDrw) //no else part.
          {
            s.push(Globs.REPAIR0);
          } 
          break;
        case Globs.CONDITION :
          switch (intok)
          {
            case Globs.NOTrw :
            case Globs.LPARENtok :
            case Globs.IDtok :
            case Globs.INTtok :
            case Globs.FALSErw :
            case Globs.TRUErw :
            case Globs.MINUStok :
              s.push(Globs.CLIST); s.push(Globs.EXPR);
              break;
            default : error(27, linenum);
          }
          break;
        case Globs.CLIST :
          switch (intok)
          {
            case Globs.EQUALtok :
            case Globs.NOTEQUALtok :
            case Globs.GREATERtok :
            case Globs.GREATEREQUALtok :
            case Globs.LESStok :
            case Globs.LESSEQUALtok :
              s.push(Globs.BOOL); s.push(Globs.EXPR);
              s.push(Globs.RELOP);
              break;
            case Globs.SEMICOLONtok :
            case Globs.ENDrw :
            case Globs.RPARENtok :
            case Globs.COMMAtok :
            case Globs.THENrw :
            case Globs.DOrw :
              break;
            default : error(28, linenum);
          }
          break;
        case Globs.RELOP :
          switch (intok)
          {
            case Globs.EQUALtok :
              s.push(Globs.EQop); tok = tf.getNextToken();
              break;
            case Globs.NOTEQUALtok :
              s.push(Globs.NOTEQop); tok = tf.getNextToken();
              break;
            case Globs.LESStok :
              s.push(Globs.LESSop); tok = tf.getNextToken();
              break;
            case Globs.GREATERtok :
              s.push(Globs.GREATERop); tok = tf.getNextToken();
              break;
            case Globs.LESSEQUALtok :
              s.push(Globs.LESSEQop); tok = tf.getNextToken();
              break;
            case Globs.GREATEREQUALtok :
              s.push(Globs.GREATEREQop); tok = tf.getNextToken();
              break;
            default : error(38, linenum);
          }
          break;
        case Globs.EXPR :
          switch (intok)
          {
            case Globs.NOTrw :
            case Globs.LPARENtok :
            case Globs.FALSErw :
            case Globs.TRUErw :
            case Globs.INTtok :
            case Globs.IDtok :
            case Globs.MINUStok :
              s.push(Globs.ELIST); s.push(Globs.TERM);
              break;
            case Globs.SEMICOLONtok :
              break;
            default : error(29, linenum);
          }
          break;
        case Globs.ELIST :
          switch (intok)
          {
            case Globs.PLUStok :
              s.push(Globs.ELIST); s.push(Globs.ADDop);
              s.push(Globs.TERM);
              tok = tf.getNextToken();
              break;
            case Globs.MINUStok :
              s.push(Globs.ELIST); s.push(Globs.SUBop);
              s.push(Globs.TERM);
              tok = tf.getNextToken();
              break;
            case Globs.INTtok :
            case Globs.IDtok :
            case Globs.SEMICOLONtok :
            case Globs.ENDrw :
            case Globs.RPARENtok :
            case Globs.COMMAtok :
            case Globs.EQUALtok :
            case Globs.NOTEQUALtok :
            case Globs.GREATERtok :
            case Globs.GREATEREQUALtok :
            case Globs.LESStok :
            case Globs.LESSEQUALtok :
            case Globs.THENrw :
            case Globs.DOrw :
              break;
            default : error(30, linenum);
          }
          break;
        case Globs.TERM :
          switch (intok)
          {
            case Globs.LPARENtok :
            case Globs.NOTrw :
            case Globs.FALSErw :
            case Globs.TRUErw :
            case Globs.INTtok :
            case Globs.IDtok :
            case Globs.MINUStok :
              s.push(Globs.TLIST); s.push(Globs.PRIMARY);
              break;
            default : error(31, linenum);
          }
          break;
        case Globs.TLIST :
          switch (intok)
          {
            case Globs.MULTIPLYtok :
              s.push(Globs.TLIST); s.push(Globs.MULop);
              s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.DIVIDEtok :
              s.push(Globs.TLIST); s.push(Globs.DIVop);
              s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.DIVrw :
              s.push(Globs.TLIST); s.push(Globs.DIVop);
              s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.MODrw :
              s.push(Globs.TLIST); s.push(Globs.MODop);
              s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.COMMAtok :
              if (!arg)
              {
                s.push(Globs.WRITEop); s.push(Globs.TERM);
                tok = tf.getNextToken();
              }
              break;
            case Globs.PLUStok :
            case Globs.MINUStok :
            case Globs.SEMICOLONtok :
            case Globs.ENDrw :
            case Globs.EQUALtok :
            case Globs.NOTEQUALtok :
            case Globs.GREATERtok :
            case Globs.GREATEREQUALtok :
            case Globs.LESStok :
            case Globs.LESSEQUALtok :
            case Globs.THENrw :
            case Globs.DOrw :
            case Globs.RPARENtok :
              break;
            default : System.out.println(intok); error(32, linenum);
          }
          break;
        case Globs.PRIMARY :
          switch (intok)
          {
            case Globs.NOTrw :
              s.push(Globs.NOT); s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.MINUStok :
              negate = true;
              s.push(Globs.PRIMARY);
              tok = tf.getNextToken();
              break;
            case Globs.LPARENtok :
              s.push(Globs.RPAREN); s.push(Globs.CONDITION);
              tok = tf.getNextToken();
              break;
            case Globs.IDtok :
              s.push(Globs.FUNCCALL); s.push(Globs.IDENT);
              break;
            case Globs.INTtok :
              s.push(Globs.UNSIGNEDINT);
              break;
            case Globs.FALSErw :
              s.push(Globs.FALSE);
              break;
            case Globs.TRUErw :
              s.push(Globs.TRUE);
              break;
            default : error(34, linenum);
          }
          break;
        case Globs.FUNCCALL :
          switch (intok)
          {
            case Globs.LPARENtok :
              s.push(Globs.FCALLRET); s.push(Globs.RPAREN);
              s.push(Globs.ARGUMENTS);
              s.push(Globs.INITPCNT);
              tok = tf.getNextToken();
            break;
            case Globs.MULTIPLYtok : case Globs.DIVIDEtok :
            case Globs.PLUStok : case Globs.MINUStok :
            case Globs.SEMICOLONtok : case Globs.ENDrw :
            case Globs.RPARENtok : case Globs.COMMAtok :
            case Globs.EQUALtok : case Globs.NOTEQUALtok :
            case Globs.GREATERtok : case Globs.GREATEREQUALtok :
            case Globs.LESStok : case Globs.LESSEQUALtok :
            case Globs.THENrw : case Globs.DOrw :
            case Globs.MODrw : case Globs.DIVrw :
              break;
            default : error(35, linenum);
          }
          break;
        case Globs.ARGUMENTS :
          switch (intok)
          {
            case Globs.NOTrw : case Globs.LPARENtok : case Globs.IDtok :
            case Globs.INTtok : case Globs.FALSErw : case Globs.TRUErw :
              arg = true;
              s.push(Globs.MOREARGS); s.push(Globs.ACTUALPARAM);
              s.push(Globs.CONDITION);
              break;
            case Globs.RPARENtok :
              arg = false;
              break;
            default : error(36, linenum);
          }
          break;
        case Globs.MOREARGS :
          if (intok == Globs.COMMAtok)
          {
            s.push(Globs.MOREARGS); s.push(Globs.ACTUALPARAM);
            s.push(Globs.CONDITION);
            tok = tf.getNextToken();
          }
          else if (intok == Globs.RPARENtok) { arg = false; }
          else { error(37, linenum); }
          break;
        case Globs.UNSIGNEDINT :
          if (intok == Globs.INTtok)
          {
            if (negate)
            {
              s.push(tok.getTokenValue() * -1);
              negate = false;
            }
            else { s.push(tok.getTokenValue()); }
            s.push(Globs.INTsem);
            tok = tf.getNextToken();
          }
          else { error(40, linenum); }
          break;
      //terminals
        case Globs.FALSE :
          if (intok == Globs.FALSErw)
          {
            s.push(0);
            s.push(Globs.FALSEsem);
            tok = tf.getNextToken();
          }
          else { error(50, linenum); }
          break;
        case Globs.TRUE :
          if (intok == Globs.TRUErw)
          {
            s.push(1);
            s.push(Globs.TRUEsem);
            tok = tf.getNextToken();
          }
          else { error(51, linenum); }
          break;
        case Globs.LPAREN :
          if (intok == Globs.LPARENtok) { tok = tf.getNextToken(); }
          else if (intok == Globs.SEMICOLONtok) {}
          else { error(52, linenum); }
          break;
        case Globs.RPAREN :
          if (intok == Globs.RPARENtok) { tok = tf.getNextToken(); }
          else { error(53, linenum); }
          break;
        case Globs.ASSIGNMENT :
          if (intok == Globs.COLONEQUALtok) { tok = tf.getNextToken(); }
          else { error(54, linenum); }
          break;
        case Globs.SEMICOLON :
          if (intok == Globs.SEMICOLONtok) { tok = tf.getNextToken(); }
          else { error(55, linenum); }
          break;
        case Globs.COLON :
          if (intok == Globs.COLONtok) { tok = tf.getNextToken(); }
          else { error(56, linenum); }
          break;
        case Globs.THEN :
          if (intok == Globs.THENrw) { tok = tf.getNextToken(); }
          else { error(57, linenum); }
          break;
        case Globs.doSYN :
          if (intok == Globs.DOrw) { tok = tf.getNextToken(); }
          else { error(58, linenum); }
          break;
        case Globs.END :
          if (intok == Globs.ENDrw) { tok = tf.getNextToken(); }
          else { error(59, linenum); }
          break;
        case Globs.PERIOD :
          if (intok == Globs.PERIODtok) { tok = tf.getNextToken(); }
          else { error(60, linenum); }
          break;
        case Globs.BOTTOMMARK :
          if (intok == Globs.endmark)
          {
            System.out.println("Program syntactically correct!");
            endParse = true;
          }
          else { error(61, linenum); }
          break;
      //semantic action
        case Globs.HALTop :
          createHALTquad();
          break;
        case Globs.ASSIGNop :          
          createASSIGNquad();
          break;
        case Globs.READop :
        case Globs.WRITEop :
        case Globs.WRITELNop :
          createIOquad(stackSymbol);
          break;
        case Globs.ADDop :
        case Globs.SUBop :
        case Globs.MULop :
        case Globs.DIVop :
        case Globs.MODop :
          createARITHquad(stackSymbol);
          break;
        case Globs.EQop :
        case Globs.NOTEQop :
        case Globs.LESSop :
        case Globs.GREATERop :
        case Globs.LESSEQop :
        case Globs.GREATEREQop :
          qs.push(stackSymbol);
          break;
        case Globs.BOOL :
          createBOOLquad();
          break;
        case Globs.Bop :
        case Globs.BFop :
          createJUMPquad(stackSymbol);
          break;
        case Globs.RTNop :
          createRTNquad();
          break;
        case Globs.FSPop :
        case Globs.ASPop :
          createFUNCquad(stackSymbol);
          break;
        case Globs.QNUM :
          qs.push(quadNum - 1);
          break;
        case Globs.QNUM0 :
          qs.push(quadNum - 2);
          break;
        case Globs.REPAIR0 :
          qnumSaved = qs.topop();
          qList.repairQuad(qnumSaved, quadNum);
          break;
        case Globs.REPAIR1 :
          qnumSaved = qs.topop();
          qList.repairQuad(qnumSaved, quadNum + 1);
          break;
        case Globs.PATCH :
          qnumSaved = qs.topop();
          qList.repairQuad(quadNum - 1, qnumSaved + 1);
          break;
        case Globs.ADJUSTASP :
          qs.show();
          qnumSaved = qs.topop();
          qList.adjustQuad(qnumSaved + 1, -localtempsmax);
          break;
        case Globs.INTEGER :
          qs.push(1);
          break;
        case Globs.BOOLEANsem :
          qs.push(2);
          break;
        case Globs.IDsem :
          idinx = s.topop();
          qs.push(idinx); qs.push(Classes.identclass);
          break;
        case Globs.INTsem :
          qs.push(s.topop());
          qs.push(Classes.numclass);
          break;
        case Globs.INITDECS :
          vcntprime = 0; vcnt = 0;
          break;
        case Globs.SAVEVARINFO :
          saveVARinfo();
          break;
        case Globs.INCVCNT :
          vcnt++;
          break;
        case Globs.INITFUNC :
          pcnt = 0; level = 1; localmax = 0; localtempsmax = 0;
          temp = qs.topop();
          if (temp == Classes.identclass)
          {
            idinx = qs.topop();
            qs.push(quadNum);
            qs.push(idinx);
            qs.push(temp);
          }
          else
          {
            System.out.println("probs in INITFUNC " + Integer.toString(temp));
          }
          break;
        case Globs.SAVEFUNCINFO :
          saveFUNCinfo();
          break;
        case Globs.FORMALPARAM :
          FORMALparam();
          break;
        case Globs.INITPCNT :
          pcnt = 0; qs.push(pcnt);
          break;
        case Globs.FCALLRET :
          FCallRet();
          break;
        case Globs.ACTUALPARAM :
          ACTUALparam();
          break;
        case Globs.SAVEPROGINFO :
          savePROGinfo();
          break;
        case Globs.SETLEVEL0 :
          level = 0;
          break;
        case Globs.CLEARLEVEL1 :
          iddt.clearLevel1Entries();
          break;
        default :
          System.out.println("no case for " + Integer.toString(stackSymbol));
      } // end of big ass switch statement
    } // end while not endParse

    tf.closeTokenFile();
    qf.createQuadFile();
    emitAllQuads();
    qf.closeQuadFile();
  }

  /**
   *  create a HALT quadruple
   */
  public void createHALTquad()
  {
    qList.enterQuad(quadNum, new Quadruple(Globs.HALTop, null, null, 0));
  }

  /**
   *  create a boolean quadruple
   */
  public void createBOOLquad()
  {
    Token o1 = new Token(), o2 = new Token();
    int type1 = 0, type2 = 0, level1 = 0, level2 = 0;
    Attributes attribs2 = new Attributes(), declared = new Attributes();
    boolean func1 = false, func2 = false;
    int[] opndinfo = new int[3];
    opndinfo = getOPNDinfo(o1);
    level1 = opndinfo[0];
    type1 = opndinfo[1];
    if ((o1.getTokenClass() == TokenClass.IDENTIFIER) && func1)
    { error(84, linenum); }
    int quadoper = qs.topop();
    opndinfo = getOPNDinfo(o2);
    level2 = opndinfo[0];
    type2 = opndinfo[1];
//    if ((o2.getTokenClass() == TokenClass.IDENTIFIER) && func2)
//    { error(84, linenum); }
    if ((type1 != 1) || (type2 != 1)) { error(83, linenum); }
    int tempAddr = allocTempAddr();
    qs.push(tempAddr); qs.push(2); qs.push(Classes.tempclass);
    if ((level1 == 0) && (o1.getTokenClass() == TokenClass.IDENTIFIER))
    {
      o1.setTokenValue(o1.getTokenValue() + 2000);
    }
    if ((level2 == 0) && (o2.getTokenClass() == TokenClass.IDENTIFIER))
    {
      o2.setTokenValue(o2.getTokenValue() + 2000);
    }
    qList.enterQuad(quadNum, new Quadruple(quadoper, o1, o2, tempAddr));
    quadNum++;
  }

  /**
   *  create a input/output quadruple
   *
   *  @param oper the operation (Globs.WRITEop, Globs.WRITELNop,
        Globs.READop)
   */
  public void createIOquad(int oper)
  {
    Token opnd = new Token();
    int type = 0, level1 = 0;
    boolean func = false;
    int[] opndinfo = new int[3];
    if ((oper == Globs.WRITEop) || (oper == Globs.READop))
    {
      opndinfo = getOPNDinfo(opnd);
      level1 = opndinfo[0];
      type = opndinfo[1];
      if (opndinfo[2] == 0) { func = false; } else { func = true; }
//      if ((oper == Globs.READop) && 
//        ((opnd.getTokenClass() != TokenClass.IDENTIFIER) || func))
//      { error(84, linenum); }
//      if ((oper == Globs.WRITEop) && func) { error(84, linenum); }
//      if ((oper == Globs.WRITELNop) && func) { error(84, linenum); }
      if (type != 1) { error(81, linenum); }
      if ((level1 == 0) && (opnd.getTokenClass() == TokenClass.IDENTIFIER))
      {
        opnd.setTokenValue(opnd.getTokenValue() + 2000);
      }
    }
    else if (oper == Globs.WRITELNop)
    {
      opnd = null;
    }
    qList.enterQuad(quadNum, new Quadruple(oper, opnd, null, 0));
    quadNum++;
  }

  /**
   *  create an assignment quadruple
   */
  public void createASSIGNquad()
  {
    Token opnd1 = new Token(), opnd2 = new Token();
    int type1 = 0, type2 = 0, level1 = 0, level2 = 0;
    Attributes attribs2 = new Attributes(), declared = new Attributes();
    boolean func1 = false, func2 = false;
    int[] opndinfo = new int[3];
    opndinfo = getOPNDinfo(opnd1);
    level1 = opndinfo[0];
    type1 = opndinfo[1];
    if (opndinfo[2] == 0) { func1 = false; } else { func1 = true; }
//    if ((opnd1.getTokenClass() == TokenClass.IDENTIFIER) && func1)
//    { error(84, linenum); }
    opndinfo = getOPNDinfo(opnd2);
    level2 = opndinfo[0];
    type2 = opndinfo[1];
    if (opndinfo[2] == 0) { func2 = false; } else { func2 = true; }
//    if ((level2 == 0) && func2) { error(84, linenum); }
//    if (type1 != type2) { error(82, linenum); }
    if ((level1 == 0) && (opnd1.getTokenClass() == TokenClass.IDENTIFIER))
    {
      opnd1.setTokenValue(opnd1.getTokenValue() + 2000);
    }
    if ((level2 == 0) && (opnd2.getTokenClass() == TokenClass.IDENTIFIER))
    {
      opnd2.setTokenValue(opnd2.getTokenValue() + 2000);
    }
    qList.enterQuad(quadNum, new Quadruple(Globs.ASSIGNop, opnd1, null, opnd2.getTokenValue()));
    quadNum++;
    freeTempAddrs();
  }

  /**
   *  create an arithmetic (<I>math</I>) quadruple
   *
   *  @param op the operation (Globs.ADDop, Globs.SUBop, Globs.MULop,
        Globs.DIVop, Globs.MODop)
   */
  public void createARITHquad(int op)
  {
    Token o1 = new Token(), o2 = new Token();
    int type1 = 0, type2 = 0, level1 = 0, level2 = 0;
    boolean func1 = false, func2 = false;
    int[] opndinfo = new int[3];
    opndinfo = getOPNDinfo(o1);
    level1 = opndinfo[0];
    type1 = opndinfo[1];
    if (opndinfo[2] == 0) { func1 = false; } else { func1 = true; }
//    if ((o1.getTokenClass() == TokenClass.IDENTIFIER) && func1)
//      { error(84, linenum); }
    opndinfo = getOPNDinfo(o2);
    level2 = opndinfo[0];
    type2 = opndinfo[1];
    if (opndinfo[2] == 0) { func2 = false; } else { func2 = true; }
    if ((type1 != 1) || (type2 != 1))
    {
      System.out.print(">" + Integer.toString(type1) + " ");
      System.out.println(Integer.toString(type2) + "<");
      error(83, linenum);
    }
    int tempaddr = allocTempAddr();
    qs.push(tempaddr);
    qs.push(1); qs.push(Classes.tempclass);
    if ((level1 == 0) && (o1.getTokenClass() == TokenClass.IDENTIFIER))
    {
      o1.setTokenValue(o1.getTokenValue() + 2000);
    }
    if ((level2 == 0) && (o2.getTokenClass() == TokenClass.IDENTIFIER))
    {
      o2.setTokenValue(o2.getTokenValue() + 2000);
    }
    qList.enterQuad(quadNum, new Quadruple(op, o1, o2, tempaddr));
    quadNum++;
    //freeTempAddrs();  //this does bad things. bad bad BAD things.
  }

  /**
   *  create a jump quadruple
   *
   *  @param branch The jump operation (Globs.Bop, Globs.BFop)
   */
  public void createJUMPquad(int branch)
  {
    Token opnd = new Token();
    int type, level1, val;
    boolean func;
    Quadruple q = new Quadruple();
    int[] opndinfo = new int[3];
    if (branch == Globs.Bop)
    {
      q.setQuad(Globs.Bop, null, null, 0);
      qList.enterQuad(quadNum, q);
      quadNum++;
    }
    else
    {
      opndinfo = getOPNDinfo(opnd);
      level1 = opndinfo[0];
      type = opndinfo[1];
      if (opndinfo[2] == 0) { func = false; } else { func = true; }
//      if ((opnd.getTokenClass() == TokenClass.IDENTIFIER) && func)
//      { error(84, linenum); }
      if ((level1 == 0) && (opnd.getTokenClass() == TokenClass.IDENTIFIER))
      { opnd.setTokenValue(opnd.getTokenValue() + 2000); }

      q.setQuad(Globs.BFop, opnd, null, 0);
      qList.enterQuad(quadNum, q);
      quadNum++;
    }
  }

  /**
   *  create a function quadruple
   *
   *  @param op the operation (Globs.ASPop, Globs.FSPop)
   */
  public void createFUNCquad(int op)
  {
    Token o = new Token(TokenClass.NUMBER, -localtempsmax);
    qList.enterQuad(quadNum, new Quadruple(op, o, null, 0));
    quadNum++;
  }

  /**
   *  create a return quadruple
   */
  public void createRTNquad()
  {
    qs.pop();
    qList.enterQuad(quadNum, new Quadruple(Globs.RTNop, null, null, 0));
    quadNum++;
  }

  /**
   *  save the program information
   */
  public void savePROGinfo()
  {
    int temp = qs.topop();
    if (temp == Classes.identclass)
    {
      int id = qs.topop();
      boolean duplicate = iddt.createEntry(idinx, level);
      if (duplicate) { error(70, linenum); }
      iddt.saveLevel(idinx, 0);
      iddt.saveAddr(idinx, -2000);
      iddt.saveType(idinx, 0);
    }
    else
    {
      System.out.print("Problems in savePROGinfo: ");
      System.out.println(temp);
    }
  }

  /**
   *  save the variable information
   */
  public void saveVARinfo()
  {
    int temp, addr = 0;
    int type = qs.topop();
    int k;
    for (k = vcnt + vcntprime; k > vcntprime; k--)
    {
      temp = qs.topop();
      if (temp == TokenClass.IDENTIFIER)
      {
        int idinx = qs.topop();
        boolean duplicate = iddt.createEntry(idinx, level);
        if (duplicate) { error(70, linenum); }
        iddt.saveLevel(idinx, level);
        iddt.saveType(idinx, type);
        if (level == 1) { addr = -k; localmax--; }
        else if (level == 0) { addr = k + 2; }
        iddt.saveAddr(idinx, addr);
      }
      else
      {
        System.out.print("Problems in saveVARinfo: ");
        System.out.println(temp);
      }
    }
    vcntprime += vcnt; vcnt = 0;
    if (level == 0)
    {
      varcnt = vcntprime; varmax = varcnt + 2;
      startAddrForGlobalTemps = varmax;
    }
    else if (level == 1) { startAddrForLocalTemps = localmax; }
  }

  /**
   *  save the function information
   */
  public void saveFUNCinfo()
  {
    int idinx; Attributes declared;
    int type = qs.topop(); qs.pop();
    int idinxp = qs.topop();
    idft.createEntry(idinxp);
    idft.savePCNT(idinxp, pcnt);
    if (pcnt > 10) { error(100, linenum); }
    int i;
    for (i = 1; i <= pcnt; i++)
    {
      temp = qs.topop();
      idinx = qs.topop();
      iddt.saveAddr(idinx, 6+i);
      declared = iddt.retrieveAttributes(idinx);
      if (declared == null) { error(71, linenum); }
      idft.saveTYPE(idinxp, pcnt-i+1, declared.type);
    }
    idft.saveQNUM(idinxp, qs.topop());
    boolean duplicate = iddt.createEntry(idinxp, 0);
    if (duplicate) { error(70, linenum); }
    iddt.saveLevel(idinxp, 0);
    iddt.saveAddr(idinxp, -2000);
    iddt.saveType(idinxp, type);
    iddt.saveKind(idinxp, true);
    duplicate = iddt.createEntry(idinxp, level);
    if (duplicate) { error(70, linenum); }
    iddt.saveAddr(idinxp, 1);
    iddt.saveAddr(idinxp, 6);
    iddt.saveType(idinxp, type);
    iddt.saveKind(idinxp, true);
  }

  /**
   *  do stuff for parameters specified in function definition
   */
  public void FORMALparam()
  {
    int type = qs.topop();
    temp = qs.topop();
    int idinx = qs.topop();
    int tempp = qs.topop();
    int idinxp = qs.topop();
    if ((temp == TokenClass.IDENTIFIER) && (tempp == TokenClass.IDENTIFIER))
    {
      pcnt++;
      boolean duplicate = iddt.createEntry(idinx, level);
      if (duplicate) { error(70, linenum); }
      iddt.saveLevel(idinx, level);
      iddt.saveType(idinx, type);
      qs.push(idinx); qs.push(temp);
      qs.push(idinxp); qs.push(tempp);
    }
    else
    {
      System.out.print("problems in FORMALparam: ");
      System.out.print(Integer.toString(temp) + " ");
      System.out.println(Integer.toString(tempp));
    }    
  }

  /**
   *  do stuff for parameters listed in function call
   */
  public void ACTUALparam()
  {
    Token o = new Token();
    int t1, t2, l;
    boolean func;
    int[] opndinfo = new int[3];
    opndinfo = getOPNDinfo(o);
    l = opndinfo[0];
    t2 = opndinfo[1];
    if (opndinfo[2] == 0) { func = false; } else { func = true; }
//    if ((o.getTokenClass() == TokenClass.IDENTIFIER) && func)
//      { error(84, linenum); }
    pcnt = qs.topop();
    pcnt++;
    temp = qs.topop();
    int idinx = qs.topop();
    FuncAttribs f = idft.retrieveFAttribs(idinx);
    t1 = f.type[pcnt];
    if (t1 != t2) { error(87, linenum); }
    if ((l == 0) && (o.getTokenClass() == TokenClass.IDENTIFIER))
    {
      o.setTokenValue(o.getTokenValue() + 2000);
    }
    qList.enterQuad(quadNum, new Quadruple(Globs.PSHop, o, null, 0));
    quadNum++;
    qs.push(idinx);
    qs.push(temp);
    qs.push(pcnt);
  }

  /**
   *  do stuff for a function call return
   */
  public void FCallRet()
  {
    pcnt = qs.topop(); temp = qs.topop(); int idinx = qs.topop();
    FuncAttribs f = idft.retrieveFAttribs(idinx);
    if (f.pcnt != pcnt) { error(86, linenum); }
    Token o = new Token(TokenClass.NUMBER, 1);
    qList.enterQuad(quadNum, new Quadruple(Globs.ASPop, o, null, 0));
    quadNum++;
    qList.enterQuad(quadNum, new Quadruple(Globs.JSRop, null, null, f.qnum));
    quadNum++;
    int tempaddr = allocTempAddr();
    qs.push(tempaddr);
    Attributes a = iddt.retrieveAttributes(idinx);
    if (!a.func) { error(88, linenum); }
    if (a.type == 1) { qs.push(1); }
    else if (a.type == 2) { qs.push(2); }
    else
    {
      System.out.println("probs in FCallRet: " + Integer.toString(a.type));
    }
    qs.push(TokenClass.TEMP);
    qList.enterQuad(quadNum, new Quadruple(Globs.POPop, null, null, tempaddr));
    quadNum++;
    if (pcnt > 0)
    {
      o.setTokenClass(TokenClass.NUMBER);
      o.setTokenValue(pcnt);
      qList.enterQuad(quadNum, new Quadruple(Globs.FSPop, o, null, 0));
      quadNum++;
    }
  }

  /**
   *  dump a parsing error. this means the program is not syntactically
   *  correct
   *
   *  @param type The error number
   *  @param lnum The line of the program where the error popped up
   */
  public void error(int type, int lnum)
  {
    String bad = "";
    System.out.print("ERROR #" + Integer.toString(type) + ": ");
    switch (type)
    {
      case  0 : bad = "'IS' expected."; break;
      case  1 : bad = "the current function name expected."; break;
      case 10 :
      case 11 : bad = "'PROGRAM' expected."; break;
      case 12 : bad = "a program name expected."; break;
      case 13 : bad = "'VAR', 'FUNCTION', or 'BEGIN' expected."; break;
      case 14 : bad = "an indetifier, 'FUNCTION', or 'BEGIN' expected."; break;
      case 15 :
      case 16 : bad = "an indentifier expected."; break;
      case 17 : bad = "',' or ':' expected."; break;
      case 18 : bad = "'INTEGER' or 'BOOLEAN' expected."; break;
      case 19 : bad = "'FUNCTION' or 'BEGIN' expected."; break;
      case 20 :
      case 21 : bad = "'FUNCTION' expected."; break;
      case 22 : bad = "an identifier or ')' expected."; break;
      case 23 : bad = "';' or ')' expected."; break;
      case 24 : bad = "'BEGIN' expected."; break;
      case 25 : bad = "';' or 'END' expected."; break;
      case 26 : bad = "an identifier, 'READ', 'WRITE', 'IF', 'WHILE', " +
                      "'BEGIN', 'END', or ';' expected."; break;
      case 27 : bad = "'(', an identifier, a number, 'NOT', " +
                      "'FALSE', or 'TRUE' expected."; break;
      case 28 : bad = "';', a relational operator, ')', ',', " +
                      "'END', 'THEN', or 'DO' expected."; break;
      case 29 : bad = "'(', an identifier, a number, 'NOT', " +
                      "'FALSE', or 'TRUE' expected."; break;
      case 30 : bad = "'+', '-', ';', a relational operator, ')', ',', " +
                      "'END', 'THEN', or 'DO' expected."; break;
      case 31 : bad = "'(', an identifier, a number, 'NOT', " +
                      "'FALSE', or 'TRUE' expected."; break;
      case 32 : bad = "'+', '-', '*', '/', ';', a relational operator, " +
                      "')', ',', 'END', 'THEN', or 'DO' expected."; break;
      case 34 : bad = "'(', an identifier, a number, 'FALSE', " +
                      "or 'TRUE' expected."; break;
      case 35 : bad = "'(', '+', '-', '*', '/', ')', a relational operator, " +
                      "';', ',', 'END', 'THEN', or 'DO' expected."; break;
      case 36 : bad = "'(', ')', an identifier, a number, 'NOT', " +
                      "'ODD', 'FALSE', or 'TRUE' expected."; break;
      case 37 : bad = "',' or ')' expected."; break;
      case 38 : bad = "a relational operator expected."; break;
      case 39 : bad = "an indentifier expected."; break;
      case 40 : bad = "an integer number expected."; break;
      case 50 : bad = "'FALSE' expected."; break;
      case 51 : bad = "'TRUE' expected."; break;
      case 52 : bad = "'(' expected."; break;
      case 53 : bad = "')' expected."; break;
      case 54 : bad = "':=' expected."; break;
      case 55 : bad = "';' expected."; break;
      case 56 : bad = "':' expected."; break;
      case 57 : bad = "'THEN' expected."; break;
      case 58 : bad = "'DO' expected."; break;
      case 59 : bad = "'END' expected."; break;
      case 60 : bad = "'.' expected."; break;
      case 61 : bad = "end of program expected."; break;
      case 70 : bad = "identifier declared twice."; break;
      case 71 : bad = "undeclared identifier."; break;
      case 72 : bad = "illegal use of program name."; break;
      case 81 : bad = "argument must be of type INTEGER."; break;
      case 82 : bad = "operand type incompatability."; break;
      case 83 : bad = "operand types must be of type INTEGER."; break;
      case 84 : bad = "operand must be a variable."; break;
      case 85 : bad = "argument must be of type BOOLEAN."; break;
      case 86 : bad = "formal and actual parameter counts unequal."; break;
      case 87 : bad = "formal and matching actual parameters " +
                      "of different types, or formal and actual " +
                      "parameters counts unequal."; break;
      case 88 : bad = "illegal use of variable name."; break;
      default : bad = "Undefined error."; break;
    }
    System.out.println(bad);
    tf.closeTokenFile();
    //tf.removeTokenFile();
    pf.retrieveProgram(pf.getProgName());
    pf.seeErrorLines(lnum);
    emitAllQuads();
    System.exit(0);
  }

  /**
   *  write ALL of the quadruples we've generated thus far to the file
   */
  public void emitAllQuads()
  {
    qf.createQuadFile();
    int i;
    Quadruple q;
    QuadList p = qList;
    while (p != null)
    {
      for (i = 0; i < 5; i++)
      {
        if (p.getQuadSeg(i).getOper() != 0)
        {
          q = p.getQuadSeg(i);
          if (q != null) { qf.emitQuad(q); }
        }
      }
      p = p.getNext();
    }
  }
} //end of clas Parser
