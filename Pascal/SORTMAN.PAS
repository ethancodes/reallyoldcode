program SortingABunchOfUselessNumbers;
{ ETHAN GEORGI -- SEE I REMEMBERED IT -- PROGRAM 14 -- CS2 -- APRIL 96 }

uses
    crt;

const
     Max = 50;

type
     darray = array [1..Max] of integer;

var
   AllData :darray;
   FileData :text;

procedure InitArray (var a :darray);
{ puts a sentinel value in each array block for easier display }
var
   x :integer;
begin
     for x := 1 to Max do
         a[x] := 999;
end;

procedure ReadData (var a :darray; var FileData :text);
{ reads in values from data file and displays them to the screen in the
  order they are in the file }
var
   x, c :integer;
begin
     assign (FileData, 'A:\DATA1.DAT');
     reset (FileData);
     c := 1;
     writeln;
     writeln ('Data in order: ');
     while not eoln(FileData) do
     begin
           read (FileData, x);
           write (x,' ');
           if c <= 50 then a[c] := x;
           c := c + 1;
     end;
     writeln;
     close (FileData);
end;

procedure Sort (var a :darray);
{ sorts the data in the array :selection sort: }
var
   x, q, min, temp :integer;
begin
     for q := 1 to Max-1 do
     begin
          min := q;
          for x := q+1 to Max do
              if a[x] < a[min] then min := x;
          if min <> q then
          begin
               temp := a[min];
               a[min] := a[q];
               a[q] := temp;
          end;
     end;
end;

procedure Display (var A :darray);
{ displays the contents of the sorted array }
var
   x :integer;
begin
     writeln ('Data after sorting: ');
     for x := 1 to Max do
          if a[x] <> 999 then write (a[x],' ');
     writeln;
end;

function Average (a :darray): real;
{ finds the average of the data in the array }
var
   sum, num, x :integer;
begin
     x := 1;
     sum := 0;
     num := 0;
     while a[x] <> 999 do
     begin
          num := num + 1;
          sum := sum + a[x];
          x := x + 1;
     end;
     Average := sum / num;
end;

function Closest (a :darray): integer;
{returns the value in the array that is closest to the average }
var
   top, bottom, mid :integer;
   prey :real;

begin
     top := 1;
     while a[top] <> 999 do
           top := top + 1;
     bottom := 1;
     prey := Average(AllData);
     while top > bottom do
     begin
          mid := (top + bottom) div 2;
          if prey > a[mid] then bottom := mid + 1
          else top := mid;
     end;
     if top <> 0 then closest := a[top];
end;

begin
     clrscr;
     InitArray (AllData);
     ReadData (AllData, FileData);
     Sort (AllData);
     Display (AllData);

     writeln ('The average of these data is ',Average(AllData):0:2);
     writeln ('The value closest to the average is ',Closest(AllData));

     readln;
end.

