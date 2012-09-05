program FunkingAroundWithFiles;
{ ETHAN GEORGI -- csII -- PROGRAM #1 -- February 6, 1996 }
uses
    crt;
var
   F1, F2, F3 :text;
   operation :integer;
   continue :char;

{-----------------------}
procedure DisplayOneFile (var F :text);
var
   y :integer;
begin
     reset (F);
     while not seekeof(F) do
     begin
           readln (F, y);
           writeln (y:5);
     end;
     close (F);
end;
{-----------------------}
procedure DisplayTwoFiles (var Fone, Ftwo :text);
var
   x, z :integer;
begin
     reset (Fone);
     reset (Ftwo);
     while not seekeof(Ftwo) and not seekeof(Fone) do
     begin
          readln (Fone, z);
          write (z:5);
          readln (Ftwo, x);
          writeln (x:10);
     end;
     while not seekeof(Ftwo) do
     begin
          readln (Ftwo, x);
          writeln (x:15);
     end;
     while not seekeof(Fone) do
     begin
          readln (Fone, z);
          writeln (z:5);
     end;
     close (Fone);
     close (Ftwo);
end;
{-----------------------}
procedure Combine (var Fone, Ftwo, Fthree :text);
var
   a, b :integer;
begin
     reset (Fone);
     reset (Ftwo);
     rewrite (Fthree);
     while not seekeof(Fone) do
     begin
          readln (Fone, a);
          writeln (Fthree, a:5);
     end;
     while not seekeof(Ftwo) do
     begin
          readln (Ftwo, b);
          writeln (Fthree, b:5);
     end;
     close (Fone);
     close (Ftwo);
     close (Fthree);
end;
{=======================}
begin
     clrscr;
     assign (F1, 'a:\FILE1.DAT');
     assign (F2, 'a:\FILE2.DAT');
     assign (F3, 'a:\FILE3.DAT');
     COMBINE (F1, F2, F3);
     writeln ('WELCOME!');
     writeln;
     repeat
     writeln ('PRESS:    TO:');
     writeln ('  1...    Display File Three');
     writeln ('  2...    Display Files One & Two');
     writeln ('  3...    Display Files Two & One');
     writeln;
     write ('--> ');
     readln (operation);
     case operation of
             1 : DISPLAYONEFILE (F3);
             2 : DISPLAYTWOFILES (F1, F2);
             3 : DISPLAYTWOFILES (F2, F1);
     end;
     writeln;
     write ('DO YOU WANT TO CONTINUE? ');
     readln (continue);
     clrscr;
     until (continue = 'N') or (continue = 'n');
end.


