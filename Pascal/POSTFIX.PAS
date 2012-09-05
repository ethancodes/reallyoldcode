program PostFix;
{ ETHAN GEORGI -- PROGRAM #10 -- CS2 -- APRIL 1996 }

uses
    selement, sarr, crt;

var
   Solution :stack;
   ch :char;
   legal :boolean;

procedure Add (var Sum :Stack);
var
   numl, numr :real;
begin
     pop (Sum, numr);
     pop (Sum, numl);
     push (Sum, numl + numr);
end;

procedure Subtract (var Sum :Stack);
var
   numl, numr :real;
begin
     pop (Sum, numr);
     pop (Sum, numl);
     push (Sum, numl - numr);
end;

procedure Multiply (var Sum :Stack);
var
   numl, numr :real;
begin
     pop (Sum, numr);
     pop (Sum, numl);
     push (Sum, numl * numr);
end;

procedure Divide (var Sum :Stack; var Legal :boolean);
var
   numl, numr :real;
begin
     pop (Sum, numr);
     pop (Sum, numl);
     if numr <> 0 then push (Sum, numl / numr)
     else legal := FALSE;
end;

function Answer (Sum :stack): Real;
var
   total :real;
begin
     pop (Sum, total);
     Answer := total;
end;

{===}

begin
     clrscr;
     legal := TRUE;
     writeln ('PLEASE ENTER THE POSTFIX ARITHMETIC EXPRESSION.');
     write ('END THE EXPRESSION WITH A PERIOD -> ');
     read (ch);
     writeln;
     write ('THE ANSWER TO ');
     while ch <> '.' do
     begin
          write (ch);
          if legal then
             case ch of
                  '0' : push (Solution, 0);
                  '1' : push (Solution, 1);
                  '2' : push (Solution, 2);
                  '3' : push (Solution, 3);
                  '4' : push (Solution, 4);
                  '5' : push (Solution, 5);
                  '6' : push (Solution, 6);
                  '7' : push (Solution, 7);
                  '8' : push (Solution, 8);
                  '9' : push (Solution, 9);
                  '+' : Add (Solution);
                  '-' : Subtract (Solution);
                  '*' : Multiply (Solution);
                  '/' : Divide (Solution, legal);
             end;
     read (ch);
     end; {while}
     if legal then writeln (' IS ',Answer(Solution):0:2)
     else writeln (' DOES NOT EXIST. CANNOT DIVIDE BY 0.');
     readln;
     readln;
end.

