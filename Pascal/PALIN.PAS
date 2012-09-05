program Palindrome;
{ ETHAN GEORGI -- PROGRAM 12 -- CS2 -- APRIL 1996 }

uses
    crt, qelement, selement, qlnk, slnk;

var
   PQUEUE :Queue;
   PSTACK :Stack;

procedure GetPalin (var pq :Queue; var ps :Stack);
var
   ch :char;
begin
     writeln ('TYPE IN YOUR PALINDROME.');
     write ('END WITH A PERIOD. --> ');
     read (ch);
     while ch <> '.' do
     begin
          if not (ch in [',',' ','"',':',';','?','''']) then begin
               EnQueue (pq, upcase(ch));
               Push (ps, upcase(ch));
          end;
          read (ch);
     end;
end;

function ItIsAPalin (var pq :queue; var ps :stack): Boolean;
var
   IsIt :boolean;
   char1, char2 :char;
begin
     IsIt := TRUE;
     while not StackEmpty(ps) do
     begin
          DeQueue (pq, char1);
          Pop (ps, char2);
          if char1 <> char2 then IsIt := FALSE;
     end;
     ItIsAPalin := IsIt;
end;

begin
     clrscr;
     QueueCreate (PQUEUE);
     StackCreate (PSTACK);
     GetPalin (PQUEUE, PSTACK);
     if ItIsAPalin (PQUEUE, PSTACK) then writeln ('This is a palindrome.')
     else writeln ('This is NOT a palindrome.');
     readln;
     readln;
end.