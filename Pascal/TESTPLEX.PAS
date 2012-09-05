program TestUnitComplex;
{ ETHAN GEORGI -- PROGRAM #3 -- CS2 -- FEBRUARY 15, 1996 }

uses
    crt, Complex;

var
   A, B, C, D :ComplexNumber; { D is reserved for answers }

begin
     clrscr;

     InputComplex (A);
     InputComplex (B);
     InputComplex (C);
     OutputComplex (A);
     OutputComplex (B);
     OutputComplex (C);
     writeln;

     AddComplex (A, B, D);

     SubtractComplex (A, B, D);

     MultiplyComplex (A, C, D);

     DivideComplex (B, C, D);

     Writeln ('THE MAGNITUDE IS ',MagnitudeOfComplex(C):0:2);
     Writeln ('THE REAL FIELD IS ',ReturnReal(B):0:2);
     Writeln ('THE IMAGINARY FIELD IS ',ReturnImaginary(A):0:2);

readln;
end.