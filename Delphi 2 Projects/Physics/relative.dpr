program relative;

uses
  Forms,
  relative1 in 'relative1.pas' {Form1},
  relative2 in 'relative2.pas' {AboutForm};

{$R *.RES}

begin
  Application.Initialize;
  Application.Title := 'Relativity Simulation';
  Application.CreateForm(TForm1, Form1);
  Application.CreateForm(TAboutForm, AboutForm);
  Application.Run;
end.
