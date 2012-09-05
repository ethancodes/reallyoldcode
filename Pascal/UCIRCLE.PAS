unit ucircle;

interface

type circletype = object
     procedure Init (R :Real);
     function Radius :Real;
     function Diameter :Real;
     function Area :Real;
     function Circumference :Real;
     procedure DisplayStatistics;

     private

     TheRadius :Real;
     end;

implementation

     procedure circletype.Init (R :Real);
     begin
          TheRadius := R;
     end;
     function circletype.Radius :Real;
     begin
          Radius := TheRadius;
     end;
     function circletype.Diameter :Real;
     begin
          Diameter := 2.0 * TheRadius;
     end;
     function circletype.Area :Real;
     begin
          Area := pi * sqr(TheRadius);
     end;
     function circletype.Circumference :Real;
     begin
          Circumference := pi * Diameter;
     end;
     procedure circletype.DisplayStatistics;
     begin
          writeln;
          writeln ('Radius = ',Radius:6:2);
          writeln ('Diameter = ',Diameter:6:2);
          writeln ('Area = ',Area:6:2);
          writeln ('Circumference = ',Circumference:6:2);
     end;
end.

