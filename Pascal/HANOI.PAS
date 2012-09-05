program TowersOfHanoi;
uses
    crt;
type
    peg = 1..3;
var
   number, x, counter :integer;

procedure Move (n :integer; var counter :integer; a, b, c :peg);
begin
     if n > 0 then
     begin
          move (n-1, counter, a, c, b);
          counter := counter + 1;
          move (n-1, counter, c, b, a);
     end;
end;

begin
     clrscr;
     for x := 3 to 15 do
     begin
          counter := 0;
          move (x, counter, 1, 3, 2);
          write ('If you solved HANOI for ',x,' disks it would take ');
          writeln (counter,' moves.');
     end;
     readln;
end.