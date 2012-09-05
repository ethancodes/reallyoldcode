unit COMPLEX;
{ For use with programs requiring complex numbers.
  by Ethan Georgi                                  }

interface

type
    ComplexNumber = record
                  r, i :real;
                  end;

procedure InputComplex (var n :ComplexNumber);
{ allows user to input values into the real and imaginary fields
  of a complex number                                            }

procedure OutputComplex (n :ComplexNumber);
{ will output the desired complex number in a + bi format }

procedure AddComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will add complex numbers a & b and assign the sum to c }

procedure SubtractComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will subtract complex numbers a & b and assign the difference to c }

procedure MultiplyComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will multiply complex numbers a & b and assign the product to c }

procedure DivideComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will divide complex numbers a & b and assign the quotient to c }

function MagnitudeOfComplex (n :ComplexNumber):Real;
{ will give the magnitude of the desired complex number }

function ReturnReal (n :ComplexNumber):Real;
{ will return the real field of the complex number }

function ReturnImaginary (n :ComplexNumber):Real;
{ will return the imaginary field of the complex number }

implementation

procedure InputComplex (var n :ComplexNumber);
{ allows user to input values into the real and imaginary fields
  of a complex number                                            }
begin
     write ('ENTER THE VALUE FOR THE REAL FIELD OF THIS COMPLEX NUMBER: ');
     readln (n.r);
     write ('ENTER THE VALUE FOR THE IMAGINARY FIELD OF THIS COMPLEX NUMBER: ');
     readln (n.i);
end;

procedure OutputComplex (n :ComplexNumber);
{ will output the desired complex number in a + bi format }
begin
     write (n.r:0:2);
     if n.i < 0 then
        writeln (' - ',abs(n.i):0:2,'i')
     else writeln (' + ',n.i:0:2,'i');
end;

procedure AddComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will add complex numbers a & b and assign the sum to c }
begin
     c.r := a.r + b.r;
     c.i := a.i + b.i;
     write ('THE SUM IS ');
     OutputComplex (c);
end;

procedure SubtractComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will subtract complex numbers a & b and assign the difference to c }
begin
     c.r := a.r - b.r;
     c.i := a.i - b.i;
     write ('THE DIFFERENCE IS ');
     OutputComplex (c);

end;


procedure MultiplyComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will multiply complex numbers a & b and assign the product to c }
begin
     c.r := a.r * b.r - a.i * b.i;
     c.i := a.r * b.i + a.i * b.r;
     write ('THE PRODUCT IS ');
     OutputComplex (c);

end;

procedure DivideComplex (a, b :ComplexNumber; var c :ComplexNumber);
{ will divide complex numbers a & b and assign the quotient to c }
begin
     c.r := (a.r * b.r + a.i * b.i)/(sqr(b.r) + sqr(b.i));
     c.i := (a.i * b.r - a.r * b.i)/(sqr(b.r) + sqr(b.i));
     write ('THE QUOTIENT IS ');
     OutputComplex (c);

end;

function MagnitudeOfComplex (n :ComplexNumber):Real;
{ will give the magnitude of the desired complex number }
begin
     MagnitudeOfComplex := sqrt(sqr(n.r) + sqr(n.i));
end;

function ReturnReal (n :ComplexNumber):Real;
{ will return the real field of the complex number }
begin
     ReturnReal := n.r;
end;

function ReturnImaginary (n :ComplexNumber):Real;
{ will return the imaginary field of the complex number }
begin
     ReturnImaginary := n.i;
end;

end. {unit}
