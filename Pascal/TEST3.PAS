{--------------------------------------------------------------------}
{                                                                    }
{  Test program for Laboratory 3 (Turbo Pascal version)              }
{                                                                    }
{--------------------------------------------------------------------}

PROGRAM TestListADT ( INPUT, OUTPUT );

{  Interactive test program for the operations in the List ADT.  }

uses lelement,llnk;

VAR
     TestList    : List;         {  Test list      }
     TestElement : ListElement;  {  List element   }
     Cmd         : CHAR;         {  Input command  }


BEGIN

ListCreate(TestList);

WRITELN;
WRITELN('Commands:');
WRITELN('  +x : Insert x after current element');
WRITELN('  -  : Delete current element');
WRITELN('  @  : Display current element');
WRITELN('  =x : Replace current element with x');
WRITELN('  N  : Go to next');
WRITELN('  P  : Go to prior');
WRITELN('  <  : Go to beginning');
WRITELN('  >  : Go to end');
WRITELN('  E  : Empty list?');
WRITELN('  F  : Full list?');
WRITELN('  [  : At beginning of list?');
WRITELN('  ]  : At end of list?');
WRITELN('  C  : Clear list');
WRITELN('  Q  : Quit program');
WRITELN;

    REPEAT

    ListShowStructure(TestList) ;           {  Output list   }

    WRITELN;
    WRITE('Command : ');                    {  Read command  }
    READ(Cmd);
    IF ( Cmd = '+' ) OR ( Cmd = '=' )
       THEN READLN(TestElement)
       ELSE READLN;

    IF Cmd = '+'                                 {  Insert           }
       THEN BEGIN
            WRITELN('Insert ',TestElement);
            ListInsert(TestList,TestElement);
            END
    ELSE IF Cmd = '-'                            {  Delete           }
       THEN BEGIN
            WRITELN('Delete current element');
            ListDelete(TestList);
            END
    ELSE IF Cmd = '@'                            {  Current          }
       THEN BEGIN
            ListCurrent(TestList,TestElement);
            WRITELN('Current element is ',TestElement);
            END
    ELSE IF Cmd = '='                            {  Replace          }
       THEN BEGIN
            WRITELN('Replace current element with ',TestElement);
            ListReplace(TestList,TestElement);
            END
    ELSE IF ( Cmd = 'N' ) OR ( Cmd = 'n' )       {  Go to next       }
       THEN BEGIN
            WRITELN('Go to next element');
            ListGotoNext(TestList);
            END
    ELSE IF ( Cmd = 'P' ) OR ( Cmd = 'p' )       {  Go to prior      }
       THEN BEGIN
            WRITELN('Go to prior element');
            ListGotoPrior(TestList);
            END
    ELSE IF Cmd = '<'                            {  Go to beginning  }
       THEN BEGIN
            WRITELN('Go to beginning of list');
            ListGotoBeginning(TestList);
            END
    ELSE IF Cmd = '>'                            {  Go to end        }
       THEN BEGIN
            WRITELN('Go to end of list');
            ListGotoEnd(TestList);
            END
    ELSE IF ( Cmd = 'E' ) OR ( Cmd = 'e' )       {  Empty list?      }
       THEN IF ListEmpty(TestList)
               THEN WRITELN('List is empty')
               ELSE WRITELN('List is NOT empty')
    ELSE IF ( Cmd = 'F' ) OR ( Cmd = 'f' )       {  Full list?       }
       THEN IF ListFull(TestList)
               THEN WRITELN('List is full')
               ELSE WRITELN('List is NOT full')
    ELSE IF Cmd = '['                            {  At beginning?    }
       THEN IF ListAtBeginning(TestList)
               THEN WRITELN('At beginning of list')
               ELSE WRITELN('NOT at beginning of list')
    ELSE IF Cmd = ']'                            {  At end?          }
       THEN IF ListAtEnd(TestList)
               THEN WRITELN('At end of list')
               ELSE WRITELN('NOT at end of list')
    ELSE IF ( Cmd = 'C' ) OR ( Cmd = 'c' )       {  Clear list       }
       THEN BEGIN
            WRITELN('Clear list');
            ListClear(TestList);
            END

    {  Insert the code for In-lab Exercises 3 and 4 here.  }

    ELSE IF ( Cmd <> 'Q' ) AND ( Cmd <> 'q' )
       THEN WRITELN('Invalid command');

    UNTIL ( Cmd = 'Q' ) OR ( Cmd = 'q' );

END.

{--------------------------------------------------------------------}
{                                                                    }
{  Additional code for In-lab Exercise 3 :                           }
{                                                                    }
{   ELSE IF ( Cmd = 'M' ) OR ( Cmd = 'm' )                           }
{      THEN BEGIN                                                    }
{           WRITELN('Move current element to beginning of list');    }
{           ListMoveToFirst(TestList);                               }
{           END                                                      }
{                                                                    }
{--------------------------------------------------------------------}
{                                                                    }
{  Additional code for In-lab Exercise 4 :                           }
{                                                                    }
{   ELSE IF Cmd = '#'                                                }
{      THEN WRITELN('Current element is in position ',               }
{                   ListPosition(TestList))                          }
{                                                                    }
{--------------------------------------------------------------------}