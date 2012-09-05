unit Quad;
{ for use in programs requiring quadratic number such as axE2 + bx + c = 0 }

interface

type
    quadratic = object
              procedure Initialize (h, i, j :real);
              procedure Display;
              procedure DisplayRealRoots;
              function Discriminant :Real;
              function NumberRealRoots :Integer;

              private

              a, b, c :real;

              end;

implementation

procedure quadratic.Initialize (h, i, j :real);
begin
     a := h;
     b := i;
     c := j;
end;

procedure quadratic.Display;
begin
     if a = 1 then write ('xE2 ')
     else if a = -1 then write ('-xE2 ')
          else write (a:0:1,'xE2 ');
     if b = 1 then write ('x ')
     else if b = -1 then write ('-x ')
          else if b < -1 then write ('- ',abs(b):0:1,'x ')
               else write ('+ ',b:0:2,'x ');
     if c < 0 then write ('- ',abs(c):0:1)
     else write ('+ ',c:0:1);
     writeln (' = 0');
end;

procedure quadratic.DisplayRealRoots;
begin
     writeln ('THE NUMBER OF REAL ROOTS IS ',NumberRealRoots,'.');
     if NumberRealRoots <> 0 then
        if NumberRealRoots = 1 then
           writeln ('IT IS ',((b * -1) + sqrt(sqr(b)-4*a*c)) / 2*a:0:2)
        else begin
        write ('THEY ARE ',((b * -1) + sqrt(sqr(b)-4*a*c)) / 2*a:0:2);
        writeln (' AND ',((b * -1) - sqrt(sqr(b)-4*a*c)) / 2*a:0:2);
        end;
end;

function quadratic.Discriminant :Real;
begin
     Discriminant := (sqr(b) - 4 * a * c);
end;

function quadratic.NumberRealRoots :Integer;
var
   roots :real;

begin
   roots := (sqr(b) - 4 * a * c);
   if roots < 0 then NumberRealRoots := 0
   else if roots = 0 then NumberRealRoots := 1
        else NumberRealRoots := 2;
end;

end.