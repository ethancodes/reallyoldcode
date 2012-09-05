program TestCandS;
{ ETHAN GEORGI -- PROGRAM #4 PART ONE -- CS2 -- FEBRUARY 22, 1996 }
uses
    crt, ucircle, usphere;

var
   circle1, circle2, circle3 :circletype;
   sphere1, sphere2, sphere3 :spheretype;
   r :real;

begin
     clrscr;
     write ('ENTER THE RADIUS OF A CIRCLE: ');
     readln (r);
     circle1.init (r);
     write ('ENTER THE RADIUS OF ANOTHER CIRCLE: ');
     readln (r);
     circle2.init (r);
     write ('ENTER THE RADIUS OF ANOTHER CIRCLE: ');
     readln (r);
     circle3.init (r);
     Circle1.displaystatistics;
     Circle2.displaystatistics;
     Circle3.displaystatistics;
     writeln;
     writeln ('Press ENTER to continue. . .');
     readln;
     clrscr;
     write ('ENTER THE RADIUS OF A SPHERE: ');
     readln (r);
     sphere1.init (r);
     write ('ENTER THE RADIUS OF ANOTHER sphere: ');
     readln (r);
     sphere2.init (r);
     write ('ENTER THE RADIUS OF ANOTHER sphere: ');
     readln (r);
     sphere3.init (r);
     sphere1.displaystatistics;
     sphere2.displaystatistics;
     sphere3.displaystatistics;
     readln;
end.
