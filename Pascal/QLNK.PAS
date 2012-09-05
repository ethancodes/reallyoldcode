unit qlnk;
{ ETHAN GEORGI -- PROGRAM 11 -- CS2 -- APRIL 1996 }
interface
         uses
             qelement;
         type
             Queuenodeptr = ^queuenode;
             queuenode = record
                       element :queueelement;
                       next :queuenodeptr;
                       end;
             queue = record
                   front, rear :queuenodeptr;
                   end;
         procedure QueueCreate (var q :queue);
         procedure EnQueue (var q :queue; newelement :queueelement);
         procedure DeQueue (var q :queue; var topelement :queueelement);
         function QueueEmpty (q :queue): boolean;
         function QueueFull (q :queue): boolean;
         procedure QueueClear (var q :queue; var topelement :queueelement);
         procedure QueueShowStructure (q :queue);

implementation
procedure QueueCreate (var q :queue);
begin
     q.front := nil;
     q.rear := nil;
end;

procedure EnQueue (var q :queue; newelement :queueelement);
var
   p :queuenodeptr;
begin
     if queuefull(q) then writeln ('Sorry! Full Queue!')
     else begin
          new (p);
          p^.element := newelement;
          p^.next := nil;
          if q.front = NIL then q.front := p
          else q.rear^.next := p;
          q.rear := p;
     end;
end;

procedure DeQueue (var q :queue; var topelement :queueelement);
var
   p :queuenodeptr;
begin
     if queueempty(q) then writeln ('Sorry! Empty Queue!')
     else begin
          topelement := q.front^.element;
          p := q.front;
          q.front := q.front^.next;
          dispose (p);
     end;
end;

function QueueEmpty (q :queue): boolean;
begin
     QueueEmpty := q.front = nil;
end;

function QueueFull (q :queue): boolean;
begin
     QueueFull := maxavail < sizeof(queuenode);
end;

procedure QueueClear (var q :queue; var topelement :queueelement);
var
   x :integer;
begin
     while q.front <> nil do
         DeQueue (q, topelement);
     q.front := nil;
     q.rear := nil;
end;

PROCEDURE QueueShowStructure ( Q : Queue );

VAR P : QueueNodePtr;       {  Iterates through the queue  }

BEGIN
IF QueueEmpty(Q)
   THEN WRITELN('Empty queue')
   ELSE BEGIN               {  Output queue from front to rear  }
        WRITE('Front ');
        P := Q.Front;
        WHILE P <> NIL DO
            BEGIN
            WRITE(' ',P^.Element);
            P := P^.Next;
            END;
        WRITELN('  Rear');
        END
END;

end.