unit llnk;
{ ETHAN GEORGI -- PROGRAM #8 -- CS2 -- MARCH 1996 }

interface
uses
    lelement;

{ supposedly you all have packets containing what these procedures and
            functions do }
type
    ListNodePtr = ^ListNode;
    ListNode = record
             element :ListElement;
             next :ListNodePtr;
             end;
    List = record
         Head, Current :ListNodePtr;
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
     L.Head := NIL;
     L.Current := NIL;
end;

procedure ListInsert (var L :List;
                     NewElement :ListElement);
var
   p :listnodeptr;
begin
     new (p);
     p^.element := newelement;
     if ListEmpty(L) then begin
        L.Head := P;
        L.Current := P;
        l.current^.next := nil;
     end
     else begin
          p^.next := l.current^.next;
          l.current^.next := p;
          l.current := p;
     end;
end;

procedure ListDelete (var L :List);
var
   p, q :listnodeptr;
begin
     if l.current = l.head then
     begin
          p := l.head;
          l.head := l.head^.next;
          l.current := l.head;
     end
     else if l.current^.next <> NIL then
     begin
          p := l.current^.next;
          l.current^ := p^;
     end
     else begin
          p := l.current;
          q := l.head;
          while q^.next <> l.current do
                q := q^.next;
          q^.next := l.current^.next;
          l.current := l.head;
     end;
     dispose (p);
end;

procedure ListCurrent (var L :List;
                      var CurrentElement :ListElement);
begin
     CurrentElement := L.Current^.element;
end;

procedure ListReplace (var L :List;
                     NewElement :ListElement);
begin
     L.Current^.element := NewElement;
end;

procedure ListGotoNext (var L :List);
begin
     if (not ListAtEnd(L)) then
        L.Current := L.Current^.next;
end;

procedure ListGotoPrior (var L :List);
var
   p :listnodeptr;
begin
     p := l.head;
     while p^.next <> l.current do
        p := p^.next;
     l.current := p;
end;

procedure ListGotoBeginning (var L :List);
begin
     L.Current := l.head;
end;

procedure ListGotoEnd (var L :List);
var
   p :listnodeptr;
begin
     p := l.head;
     while p^.next <> nil do
        p := p^.next;
     l.current := p;
end;

procedure ListClear (var L :List);
var
   x, size :integer;
begin
     ListGotoBeginning(L);
     while l.current <> NIL do
         ListDelete(L);
end;

procedure ListShowStructure (L :List);
var
   p :listnodeptr;
begin
     if ListEmpty(L) then writeln ('EMPTY LIST.')
     else begin
          p := l.head;
          while p <> NIL do
          begin
               if p = l.current then
                  write (' [',p^.element,']')
                  else write (' ',p^.element);
               p := p^.next;
          end;
          writeln;
     end;
end;

function ListEmpty (L :List): Boolean;
begin
     if L.head = NIL then ListEmpty := TRUE
     else ListEmpty := FALSE;
end;

function ListFull (L :List): Boolean;
begin
     if sizeof(listnode) < maxavail then ListFull := FALSE
     else ListFull := TRUE;
end;

function ListAtBeginning (L :List): Boolean;
begin
     if L.Current = l.head then ListAtBeginning := TRUE
     else ListAtBeginning := FALSE;
end;

function ListAtEnd (L :List): Boolean;
begin
     if L.Current^.next = nil then ListAtEnd := TRUE
     else ListAtEnd := FALSE;
end;

end.




