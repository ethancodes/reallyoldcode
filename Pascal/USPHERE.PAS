unit usphere;

interface
uses
    ucircle;

type spheretype = object (circletype)
     function Area :Real;
     function Volume :Real;
     procedure DisplayStatistics;

     end;

implementation

     function spheretype.Area :Real;
     begin
          Area := 4.0 * pi * sqr(Radius);
     end;
     function spheretype.Volume :Real;
     begin
          Volume := 4.0 * pi * Radius * sqr(Radius) / 3.0;
     end;
     procedure spheretype.DisplayStatistics;
     begin
          writeln;
          writeln ('Radius = ',Radius:6:2);
          writeln ('Diameter = ',Diameter:6:2);
          writeln ('Surface Area = ',Area:6:2);
          writeln ('Equator Length = ',Circumference:6:2);
          writeln ('Volume = ',Volume:6:2);
     end;
end.

