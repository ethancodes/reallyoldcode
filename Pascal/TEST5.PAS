{--------------------------------------------------------------------}
{                                                                    }
{  Test program for Laboratory 5 (Turbo Pascal version)              }
{                                                                    }
{--------------------------------------------------------------------}

PROGRAM TestQueueADT ( INPUT, OUTPUT );

{  Interactive test program for the operations in the Queue ADT.  }

uses qelement,qlnk; {singly linked list version}
{uses qelement,qarr;}

VAR
     TestQueue   : Queue;         {  Test queue     }
     TestElement : QueueElement;  {  Queue element  }
     Cmd         : CHAR;          {  Input command  }

BEGIN

QueueCreate(TestQueue);

WRITELN('Commands:');
WRITELN('  +x : Enqueue x');
WRITELN('  -  : Dequeue');
WRITELN('  E  : Empty queue?');
WRITELN('  F  : Full queue?');
WRITELN('  C  : Clear queue');
WRITELN('  Q  : Quit program');
WRITELN;

    REPEAT

    QueueShowStructure(TestQueue);          {  Output queue  }
    WRITELN;

    WRITE('Command : ');                    {  Read command  }
    READ(Cmd);
    IF Cmd = '+'
       THEN READLN(TestElement)
       ELSE READLN;

    IF Cmd = '+'                                    {  Enqueue       }
       THEN BEGIN
            WRITELN('Enqueue ',TestElement);
            Enqueue(TestQueue,TestElement);
            END
    ELSE IF Cmd = '-'                               {  Dequeue       }
       THEN BEGIN
            Dequeue(TestQueue,TestElement);
            WRITELN('Dequeued ',TestElement);
            END
    ELSE IF ( Cmd = 'E' ) OR ( Cmd = 'e' )          {  Empty queue?  }
       THEN IF QueueEmpty(TestQueue)
               THEN WRITELN('Queue is empty')
               ELSE WRITELN('Queue is NOT empty')
    ELSE IF ( Cmd = 'F' ) OR ( Cmd = 'f' )          {  Full queue?   }
       THEN IF QueueFull(TestQueue)
               THEN WRITELN('Queue is full')
               ELSE WRITELN('Queue is NOT full')
    ELSE IF ( Cmd = 'C' ) OR ( Cmd = 'c' )          {  Clear queue   }
       THEN BEGIN
            WRITELN('Clear queue');
            QueueClear(TestQueue, TestElement);
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
{   ELSE IF Cmd = '>'                                                }
{      THEN BEGIN                                                    }
{           WRITELN('Put in front ',TestElement);                    }
{           QueuePutFront(TestQueue,TestElement);                    }
{           END                                                      }
{   ELSE IF Cmd = '='                                                }
{      THEN BEGIN                                                    }
{           QueueGetRear(TestQueue,TestElement);                     }
{           WRITELN('From rear ',TestElement);                       }
{           END                                                      }
{                                                                    }
{  You also must change the statement                                }
{                                                                    }
{     IF Cmd = '+'                                                   }
{        THEN READLN(TestElement)                                    }
{        ELSE READLN;                                                }
{                                                                    }
{  to the statement                                                  }
{                                                                    }
{     IF ( Cmd = '+' ) OR ( Cmd = '>' )                              }
{        THEN READLN(TestElement)                                    }
{        ELSE READLN;                                                }
{                                                                    }
{--------------------------------------------------------------------}
{                                                                    }
{  Additional code for In-lab Exercise 4 :                           }
{                                                                    }
{   ELSE IF Cmd = '#'                                                }
{      THEN WRITELN('Queue length = ',QueueLength(TestQueue))        }
{                                                                    }
{--------------------------------------------------------------------}