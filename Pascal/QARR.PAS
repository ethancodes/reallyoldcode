unit qarr;
{ ETHAN GEORGI -- PROGRAM 11 -- CS2 -- APRIL 1996 }

interface

         uses
             qelement;
         const
              MaxQueueSize = 10;
         type
             Queue = record
                   Front, Rear :0..MaxQueueSize;
                   Element :array [1..MaxQueueSize] of QueueElement;
                   end;

         procedure QueueCreate (var q :queue);
         procedure EnQueue (var q :queue; newelement :queueelement);
         procedure DeQueue (var q :queue; var frontelement :queueelement);
         function QueueEmpty (q :queue): boolean;
         function QueueFull (q :queue): boolean;
         procedure QueueClear (var q :queue; var frontelement :queueelement);
         procedure QueueShowStructure (q :queue);

implementation

procedure QueueCreate (var q :queue);
begin
     q.front := 0;
     q.rear := 0;
end;

procedure EnQueue (var q :queue; newelement :queueelement);
begin
     if QueueFull(q) then writeln ('Sorry! Full Queue!')
     else begin
     q.rear := (q.rear mod MaxQueueSize) + 1;
     if q.front = 0 then begin
        q.front := 1;
        q.element[q.front] := newelement;
     end
     else q.element[q.rear] := newelement;
     end;
end;

procedure DeQueue (var q :queue; var frontelement :queueelement);
begin
     if QueueEmpty(q) then writeln ('Sorry! Empty Queue!')
     else begin
          frontelement := q.element[q.front];
          q.front := (q.front mod MaxQueueSize) + 1;
     end;
end;

function QueueEmpty (q :queue): boolean;
begin
     QueueEmpty := q.front = 0;
end;

function QueueFull (q :queue): boolean;
begin
     if ((q.rear + 1) = q.front) or ((q.front = 1) and (q.rear = MaxQueueSize))
        then QueueFull := TRUE
     else QueueFull := FALSE;
end;

procedure QueueClear (var q :queue; var frontelement :queueelement);
begin
     q.front := 0;
     q.rear := 0;
end;

PROCEDURE QueueShowStructure ( Q : Queue );

VAR J : INTEGER;   {  Iterates through the queue  }

BEGIN
IF QueueEmpty(Q)
   THEN WRITELN('Empty queue')
   ELSE BEGIN
        WRITELN('  Q.Front = ',Q.Front,'   Q.Rear =',Q.Rear);
        FOR J := 1 TO MaxQueueSize DO WRITE(J:3);
        WRITELN;
        IF Q.Rear >= Q.Front
           THEN FOR J := 1 TO MaxQueueSize DO
                    IF ( J < Q.Front ) OR ( J > Q.Rear )
                       THEN WRITE('   ')
                       ELSE WRITE(Q.Element[J]:3)
           ELSE FOR J := 1 TO MaxQueueSize DO
                    IF ( J <= Q.Rear ) OR ( J >= Q.Front )
                       THEN WRITE(Q.Element[J]:3)
                       ELSE WRITE('   ');
        WRITELN;
        END;
END;

end.


