/**
 *  Globs. i have no idea what that means. this is just a bunch of words
 *  that we end up using put into integer form.
 *
 *  @author Ethan Georgi
 */

public interface Globs
{
  int
  // reserved words
  IFrw = 0, BOOLEANrw = 1, CHARrw = 2, VARrw = 3, BYrw = 4, FORrw = 5,
  OFrw = 6, BEGINrw = 7, PROGRAMrw = 8, INTEGERrw = 9, THENrw = 10,
  ARRAYrw = 11, FUNCTIONrw = 12, FALSErw = 13, ORrw = 14, ISrw = 15,
  WHILErw = 16, READrw = 17, WRITELNrw = 18, NOTrw = 19, DOrw = 20,
  RETURNrw = 21, ELSErw = 22, TOrw = 23, WRITErw = 24, CONSTrw = 25,
  TRUErw = 26, PROCEDURErw = 27, DIVrw = 28, FORWARDrw = 29, ANDrw = 30,
  ENDrw = 31, MODrw = 32,

  IDtok = 33, INTtok = 34, endmark = 35,

  // special symbols
  SEMICOLONtok = 36, PERIODtok = 37, COMMAtok = 38, LPARENtok = 39,
  RPARENtok = 40, PLUStok = 41, MINUStok = 42, MULTIPLYtok = 43,
  DIVIDEtok = 44, EQUALtok = 45, COLONEQUALtok = 46, COLONtok = 47,
  GREATERtok = 48, GREATEREQUALtok = 49, LESStok = 50, LESSEQUALtok = 51,
  NOTEQUALtok = 52, SINQUOTEtok = 53, IStok = 54,

  // syntactic symbols: nonterminals
  PROGRAM = 101, HEADING = 102, PROGNAME = 103, DECS = 104,
  MOREDECLISTS = 106, DECLIST = 107, IDENTLIST = 108,
  MOREIDENTS = 109, TYPE = 110, FUNCS = 111, FUNCTION = 112,
  FUNCHEADING = 113, PARAMLIST = 114, MOREPARAMS = 115, BODY = 116,
  MORESTMTS = 117, STMT = 118, CONDITION = 119, CLIST = 120,
  EXPR = 121, ELIST = 122, TERM = 123, TLIST = 124, FACTOR = 125,
  PRIMARY = 125, FUNCCALL = 126, ARGUMENTS = 127, MOREARGS = 128,
  RELOP = 129, IDENT = 130, UNSIGNEDINT = 131, RIDENTLIST = 132,
  ELSE = 133, INITPCNT = 134, FCALLRET = 135, ACTUALPARAM = 136,
  FORMALPARAM = 137, FUNCIDENT = 138, FUNCNAME = 139, QNUM0 = 140,

  // syntactic symbols: terminals
  BOTTOMMARK = 200, FALSE = 201, TRUE = 202, LPAREN = 203,
  RPAREN = 204, ASSIGNMENT = 205, SEMICOLON = 206, COLON = 207,
  THEN = 208, doSYN = 209, END = 210, PERIOD = 211, IS = 212,

  // semantic symbols: quadruple operators
  HALTop = 300, ASSIGNop = 301, READop = 302, WRITEop = 303, ADDop = 304,
  SUBop = 305, MULop = 306, DIVop = 307, EQop = 308, NOTEQop = 309,
  LESSop = 310, GREATERop = 311, LESSEQop = 312, GREATEREQop = 313,
  NOT = 314, ODDop = 315, Bop = 316, BFop = 317, JSRop = 318,
  RTNop = 319, ASPop = 320, FSPop = 321, PSHop = 322, POPop = 323,
  WRITELNop = 324, MODop = 325, 

  // semantic symbols: other actions
  QNUM = 350, BOOL = 351, REPAIR0 = 352, REPAIR1 = 353,
  PATCH = 354, ADJUSTASP = 355, INTEGER = 356, BOOLEANsem = 357,
  IDsem = 358, INTsem = 359, FALSEsem = 360, TRUEsem = 361,
  INITDECS = 362, SAVEVARINFO = 363, INCVCNT = 364, INITFUNC = 365,
  SAVEFUNCINFO = 366, SAVEPROGINFO = 367, SETLEVEL0 = 368,
  CLEARLEVEL1 = 369;
}
