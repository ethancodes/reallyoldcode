unit sarr;
{ ETHAN GEORGI -- PROGRAM #9 -- CS2 -- MARCH 1996 }
interface
         uses
             selement;
         const
              maxstacksize = 10;
         type
             stack = record
                   top :0..maxstacksize;
                   element :array [1..maxstacksize] of stackelement;
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
     s.top := 0;
end;

procedure Push (var s :stack; newelement :stackelement);
begin
     if stackfull(s) then writeln ('Sorry! Full Stack!')
     else begin
          if s.top = 0 then begin
             s.top := 1;
             s.element[1] := newelement;
          end
          else begin
               s.top := s.top + 1;
               s.element[s.top] := newelement;
          end;
     end;
end;

procedure Pop (var s :stack; var topelement :stackelement);
begin
     if stackempty(s) then writeln ('Sorry! Empty Stack!')
     else begin
          topelement := s.element[s.top];
          s.top := s.top - 1;
     end;
end;

function StackEmpty (s :stack): boolean;
begin
     StackEmpty := s.top = 0;
end;

function StackFull (s :stack): boolean;
begin
     StackFull := s.top = maxstacksize;
end;

procedure StackClear (var s :stack; var topelement :stackelement);
var
   x :integer;
begin
     for x := 1 to s.top do
         pop (s, topelement);
     s.top := 0;
end;

procedure StackShowStructure (s :stack);
VAR J : INTEGER;   {  Iterates through the stack  }

BEGIN
IF StackEmpty(S)
   THEN WRITELN('Empty stack')
   ELSE BEGIN
        WRITELN('  S.Top = ',S.Top);
        FOR J := 1 TO MaxStackSize DO WRITE(J:3);
        WRITELN;
        FOR J := 1 TO S.Top DO WRITE(S.Element[J]:3);
        WRITELN;
        END;
END;

end.