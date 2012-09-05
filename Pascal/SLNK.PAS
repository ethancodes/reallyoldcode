unit slnk;
{ ETHAN GEORGI -- PROGRAM 9 -- CS2 -- MARCH 1996 }
interface
         uses
             selement;
         type
             stacknodeptr = ^stacknode;
             stacknode = record
                       element :stackelement;
                       next :stacknodeptr;
                       end;
             stack = record
                   top :stacknodeptr;
                   end;
         procedure StackCreate (var s :stack);
         procedure Push (var s :stack; newelement :stackelement);
         procedure Pop (var s :stack; var topelement :stackelement);
         function StackEmpty (s :stack): boolean;
         function StackFull (s :stack): boolean;
         procedure StackClear (var s :stack; var topelement :stackelement);
         procedure StackShowStructure (s :stack);

implementation
procedure StackCreate (var s :stack);
begin
     s.top := nil;
end;

procedure Push (var s :stack; newelement :stackelement);
var
   p :stacknodeptr;
begin
     if stackfull(s) then writeln ('Sorry! Full Stack!')
     else begin
          new (p);
          p^.element := newelement;
          p^.next := s.top;
          s.top := p;
     end;
end;

procedure Pop (var s :stack; var topelement :stackelement);
var
   p :stacknodeptr;
begin
     if stackempty(s) then writeln ('Sorry! Empty Stack!')
     else begin
          topelement := s.top^.element;
          p := s.top;
          s.top := s.top^.next;
          dispose (p);
     end;
end;

function StackEmpty (s :stack): boolean;
begin
     StackEmpty := s.top = nil;
end;

function StackFull (s :stack): boolean;
begin
     StackFull := maxavail < sizeof(stacknode);
end;

procedure StackClear (var s :stack; var topelement :stackelement);
var
   x :integer;
begin
     while s.top <> nil do
         pop (s, topelement);
     s.top := nil;
end;

PROCEDURE StackShowStructure ( S : Stack );

VAR P : StackNodePtr;       {  Iterates through the stack  }

BEGIN
IF StackEmpty(S)
   THEN WRITELN('Empty stack')
   ELSE BEGIN               {  Output stack from top down to bottom  }
        WRITE('Top ');
        P := S.Top;
        WHILE P <> NIL DO
            BEGIN
            WRITE(' ',P^.Element);
            P := P^.Next;
            END;
        WRITELN('  Bottom');
        END
END;

end.