program DNA;
{ ETHAN GEORGI -- PROGRAM 8 -- CS2 -- MARCH 5, 1996 }

uses
    crt, lelement, llnk;

{const
     maxdnalength = maxlistarraysize;  so it doesn't exceed larr's stats }
type
    Basetype = set of char;

var
   bases :basetype;
   DNAString :List;

procedure Store (var DNAString :List;
                bases :basetype);
var
   chr :char;
begin
     write ('ENTER THE DNA STRING: ');
     read (chr);
     while chr in bases do
     begin
          ListInsert (DNAString, chr);
          read (chr);
     end;
     writeln;
end;

procedure GetMeThatInformation (var DNAString :List);
var
   acount, ccount,
   gcount, tcount :integer;
   current :ListElement;
begin
     acount := 0;
     ccount := 0;
     gcount := 0;
     tcount := 0;
     ListGotoBeginning (DNAString);
     while (not ListAtEnd(DNAString)) do
     begin
          ListCurrent (DNAString, current);
          case current of
               'A','a' : acount := acount + 1;
               'C','c' : ccount := ccount + 1;
               'G','g' : gcount := gcount + 1;
               'T','t' : tcount := tcount + 1;
          end;
          ListGotoNext (DNAString);
     end;
     { since we forgot the last one. . . }
     ListCurrent (DNAString, current);
     case current of
          'A','a' : acount := acount + 1;
          'C','c' : ccount := ccount + 1;
          'G','g' : gcount := gcount + 1;
          'T','t' : tcount := tcount + 1;
     end;
     writeln ('NUMBER OF A''s = ',acount);
     writeln;
     writeln ('NUMBER OF C''s = ',ccount);
     writeln;
     writeln ('NUMBER OF G''s = ',gcount);
     writeln;
     writeln ('NUMBER OF T''s = ',tcount);
end;

begin
     bases := ['A','C','T','G','a','c','t','g'];
     ListCreate (DNAString);
     clrscr;
     Store (DNAString, bases);
     GetMeThatInformation (DNAString);
readln;
end.