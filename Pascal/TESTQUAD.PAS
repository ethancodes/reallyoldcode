program TestQuad;
{ ETHAN GEORGI -- PROGRAM #4 PART TWO -- CS2 -- FEBRUARY 23, 1996 }
uses
    crt, quad;

type
    numtype = record
            first,
            second,
            third :real;
            end;

var
   qd1, qd2, qd3 :quadratic;
   num1, num2, num3 :numtype;

begin
     clrscr;
     writeln ('FIRST FUNCTION');
     write ('TYPE IN THE FIRST NUMBER IN THIS QUADRATIC FUNCTION ');
     readln (num1.first);
     write ('TYPE IN THE SECOND NUMBER ');
     readln (num1.second);
     write ('TYPE IN THE THIRD NUMBER ');
     readln (num1.third);
     qd1.initialize (num1.first, num1.second, num1.third);

     writeln ('SECOND FUNCTION');
     write ('TYPE IN THE FIRST NUMBER IN THIS QUADRATIC FUNCTION ');
     readln (num2.first);
     write ('TYPE IN THE SECOND NUMBER ');
     readln (num2.second);
     write ('TYPE IN THE THIRD NUMBER ');
     readln (num2.third);
     qd2.initialize (num2.first, num2.second, num2.third);

     writeln ('THIRD FUNCTION');
     write ('TYPE IN THE FIRST NUMBER IN THIS QUADRATIC FUNCTION ');
     readln (num3.first);
     write ('TYPE IN THE SECOND NUMBER ');
     readln (num3.second);
     write ('TYPE IN THE THIRD NUMBER ');
     readln (num3.third);
     qd3.initialize (num3.first, num3.second, num3.third);

     clrscr;
     qd1.display;
     qd2.display;
     qd3.display;

     writeln;
     write ('THE DISCRIMINANT OF THE FIRST QUADRATIC FUNCTION IS ');
     writeln (qd1.discriminant:0:1);

     writeln;
     qd1.displayrealroots;
     qd2.displayrealroots;
     qd3.displayrealroots;
readln;
end.

