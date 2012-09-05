{--------------------------------------------------------------------}
{                                                                    }
{  Test program for Laboratory 4 (Turbo Pascal version)              }
{                                                                    }
{--------------------------------------------------------------------}

PROGRAM TestStackADT ( INPUT, OUTPUT );

{  Interactive test program for the operations in the Stack ADT.  }

uses selement, slnk; {singly linked list version}
{uses selement, sarr;}
VAR
     TestStack   : Stack;         {  Test stack          }
     TestElement : StackElement;  {  Stack element       }
     Cmd         : CHAR;          {  User input command  }


BEGIN

StackCreate(TestStack);

WRITELN;
WRITELN('Commands:');
WRITELN('  +x : Push x');
WRITELN('  -  : Pop');
WRITELN('  E  : Empty stack?');
WRITELN('  F  : Full stack?');
WRITELN('  C  : Clear stack');
WRITELN('  Q  : Quit program');
WRITELN;

    REPEAT

    StackShowStructure(TestStack);          {  Output stack  }
    WRITELN;

    WRITE('Command : ');                    {  Read command  }
    READ(Cmd);
    IF Cmd = '+'
       THEN READLN(TestElement)
       ELSE READLN;

    IF Cmd = '+'                                    {  Push          }
       THEN BEGIN
            WRITELN('Push ',TestElement);
            Push(TestStack,TestElement);
            END
    ELSE IF Cmd = '-'                               {  Pop           }
       THEN BEGIN
            Pop(TestStack,TestElement);
            WRITELN('Popped ',TestElement);
            END
    ELSE IF ( Cmd = 'E' ) OR ( Cmd = 'e' )          {  Empty stack?  }
       THEN IF StackEmpty(TestStack)
               THEN WRITELN('Stack is empty')
               ELSE WRITELN('Stack is NOT empty')
    ELSE IF ( Cmd = 'F' ) OR ( Cmd = 'f' )          {  Full stack?   }
       THEN IF StackFull(TestStack)
               THEN WRITELN('Stack is full')
               ELSE WRITELN('Stack is NOT full')
    ELSE IF ( Cmd = 'C' ) OR ( Cmd = 'c' )          {  Clear stack   }
       THEN BEGIN
            WRITELN('Clear stack');
            StackClear(TestStack);
            END

    {  Insert the code for In-lab Exercise 4 here.  }

    ELSE IF ( Cmd <> 'Q' ) AND ( Cmd <> 'q' )
       THEN WRITELN('Invalid command');

    UNTIL ( Cmd = 'Q' ) OR ( Cmd = 'q' );

END.

{--------------------------------------------------------------------}
{                                                                    }
{  Additional code for In-lab Exercise 4 :                           }
{                                                                    }
{   ELSE IF Cmd = '='                                                }
{      THEN BEGIN                                                    }
{           WRITELN('Copy stack');                                   }
{           StackCopy(TestStack,CopyOfStack);                        }
{           WRITE('Original stack : ');                              }
{           StackShowStructure(TestStack);                           }
{           WRITE('Copy of stack  : ');                              }
{           StackShowStructure(CopyOfStack);                         }
{           WRITELN('Clear original stack');                         }
{           StackClear(TestStack);                                   }
{           WRITE('Original stack : ');                              }
{           StackShowStructure(TestStack);                           }
{           WRITE('Copy of stack  : ');                              }
{           StackShowStructure(CopyOfStack);                         }
{           WRITELN;                                                 }
{           END                                                      }
{                                                                    }
{  where CopyOfStack is of type Stack.                               }
{                                                                    }
{--------------------------------------------------------------------}
