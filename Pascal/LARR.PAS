unit larr;
{ ETHAN GEORGI -- PROGRAM #6 -- CS2 -- MARCH 1996 }

interface
uses
    lelement;

{ supposedly you all have packets containing what these procedures and
            functions do }
const
     MaxListArraySize = 10;
type
    List = record
         size, current :integer;
         element :array [1..MaxListArraySize] of ListElement;
         end;

procedure ListCreate (var L :List);
procedure ListInsert (var L :List;
                     NewElement :ListElement);
procedure ListDelete (var L :List);
procedure ListCurrent (var L :List;
                      var CurrentElement :ListElement);
procedure ListReplace (var L :List;
                     NewElement :ListElement);
procedure ListGotoNext (var L :List);
procedure ListGotoPrior (var L :List);
procedure ListGotoBeginning (var L :List);
procedure ListGotoEnd (var L :List);
procedure ListClear (var L :List);
procedure ListShowStructure (L :List);
function ListEmpty (L :List): Boolean;
function ListFull (L :List): Boolean;
function ListAtBeginning (L :List): Boolean;
function ListAtEnd (L :List): Boolean;

implementation

{ here's the part where all those packets actually get used !!! }
procedure ListCreate (var L :List);
begin
     L.Size := 0;
     L.Current := 0;
end;

procedure ListInsert (var L :List;
                     NewElement :ListElement);
var
   x :integer;
begin
     if (not ListFull(L)) then begin
     if ListEmpty(L) then begin
        L.Element[1] := NewElement;
        L.Size := 1;
        L.Current := 1;
     end
     else begin
          for x := L.Size downto (L.Current + 1) do
              L.Element[x+1] := L.Element[x];
          L.Element[L.Current+1] := NewElement;
          L.Size := L.Size + 1;
          L.Current := L.Current + 1;
     end;
     end; {not ListFull}
end;

procedure ListDelete (var L :List);
var
   x :integer;
begin
     if (not ListEmpty(L)) then begin
        for x := L.Current to L.Size do
            L.Element[x] := L.Element[x+1];
        L.Size := L.Size - 1;
     end; {not ListFull}
     if L.Current > L.Size then L.Current := 1;
end;

procedure ListCurrent (var L :List;
                      var CurrentElement :ListElement);
begin
     CurrentElement := L.Element[L.Current];
end;

procedure ListReplace (var L :List;
                     NewElement :ListElement);
begin
     L.Element[L.Current] := NewElement;
end;

procedure ListGotoNext (var L :List);
begin
     if (not ListAtEnd(L)) then
        L.Current := L.Current + 1;
end;

procedure ListGotoPrior (var L :List);
begin
     if (not ListAtBeginning(L)) then
        L.Current := L.Current - 1;
end;

procedure ListGotoBeginning (var L :List);
begin
     L.Current := 1;
end;

procedure ListGotoEnd (var L :List);
begin
     L.Current := L.Size;
end;

procedure ListClear (var L :List);
var
   x, size :integer;
begin
     size := L.Size;
     ListGotoBeginning(L);
     for x := 1 to size do
         ListDelete(L);
end;

procedure ListShowStructure (L :List);
var
   x :integer;
begin
     if ListEmpty(L) then writeln ('EMPTY LIST.')
     else begin
          writeln ('L.CURRENT = ',L.Current,'   L.SIZE = ',L.Size);
          for x := 1 to MaxListArraySize do write (x:3);
          writeln;
          for x := 1 to L.Size do write (L.Element[x]:3);
          writeln;
     end;
end;

function ListEmpty (L :List): Boolean;
begin
     if L.Size = 0 then ListEmpty := TRUE
     else ListEmpty := FALSE;
end;

function ListFull (L :List): Boolean;
begin
     if L.Size = MaxListArraySize then ListFull := TRUE
     else ListFull := FALSE;
end;

function ListAtBeginning (L :List): Boolean;
begin
     if L.Current = 1 then ListAtBeginning := TRUE
     else ListAtBeginning := FALSE;
end;

function ListAtEnd (L :List): Boolean;
begin
     if L.Current = L.Size then ListAtEnd := TRUE
     else ListAtEnd := FALSE;
end;

end.




