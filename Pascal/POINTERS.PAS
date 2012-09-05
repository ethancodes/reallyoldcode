program LinkLists;
{ ETHAN GEORGI -- PROGRAM #2 -- CSII -- February 8, 1996}
uses
    crt;

type
    Ptr = ^PRecord;
    PRecord = record
            Data :Real;
            Link :Ptr;
            end;

var
   List :Ptr;
   GotoFile :text;

{------------------}
procedure CreateList (var L :Ptr);
var
   val :real;
   tempptr, last :ptr;

begin
     write ('PLEASE ENTER A VALUE, 999 TO QUIT: ');
     readln (val);
     if val <> 999 then
     begin
          new (L);
          L^.data := val;
          last := L;
          write ('PLEASE ENTER A VALUE, 999 TO QUIT: ');
          readln (val);
     end
     else L := NIL;
     while val <> 999 do
     begin
          new (tempptr);
          tempptr^.data := val;
          last^.link := tempptr;
          last := last^.link;
          write ('PLEASE ENTER A VALUE, 999 TO QUIT: ');
          readln (val);
     end;
     if L <> NIL then last^.link := NIL;
end;
{------------------}
procedure DisplayContents (L :ptr;
                          var GotoFile :text);
var
   last :ptr;

begin
     rewrite (GotoFile);
     writeln (GotoFile,'THIS LIST CONTAINS THE FOLLOWING: ');
     writeln;
     last := L;
     while last <> NIL do
     begin
          writeln (GotoFile, last^.data:0:2);
          last := last^.link;
     end;
     close (GotoFile);
end;
{------------------}
function Sum (L :ptr):real;
var
   last :ptr;
   total :real;

begin
     total := 0;
     last := L;
     while last <> NIL do
     begin
          total := total + last^.data;
          last := last^.link;
     end;
     sum := total;
end;
{------------------}
function ListSize (L :ptr):integer;
var
   last :ptr;
   size :integer;

begin
     size := 0;
     last := L;
     while last <> NIL do
     begin
          size := size + 1;
          last := last^.link;
     end;
     listsize := size;
end;
{==================}
begin
     assign (GotoFile, 'a:pointer1.dat');
     clrscr;
     CREATELIST (List);
     clrscr;
     DISPLAYCONTENTS (List, GotoFile);
     append (GotoFile);
     writeln;
     WRITELN (GotoFile,'THE SUM OF THE DATA FIELDS IN THIS LIST IS ',Sum(List):0:2);
     writeln;
     WRITELN (GotoFile,'THERE ARE ',ListSize(List),' LINKS IN THIS LIST.');
     writeln;
     writeln ('Press ENTER to continue...');
close (GotoFile);
readln;
end.