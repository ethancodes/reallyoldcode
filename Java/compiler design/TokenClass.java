/**
 *  TokenClass.
 *  <UL>
 *  <LI>0 Reserved word
 *  <LI>1 Identifier
 *  <LI>2 Number
 *  <LI>3 Symbol
 *  <LI>4 Literal String
 *  <LI>5 Literal Character
 *  <LI>6 Temporary
 *  <LI>10 Newline
 *  <LI>11 End of text
 *  </UL>
 *
 *  @author Ethan Georgi
 */
public interface TokenClass
{
  int
  RESERVED   = 0,
  IDENTIFIER = 1,
  NUMBER     = 2,
  SYMBOL     = 3,
  LITSTR     = 4,
  LITCHAR    = 5,
  TEMP       = 6,
  NEWLINE    = 10,
  ENDOFTEXT  = 11;
}
