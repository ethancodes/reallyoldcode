unit relative1;

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  Menus, StdCtrls, FileCtrl, ComCtrls, Buttons, ExtCtrls;

type
  TForm1 = class(TForm)
    MainMenu1: TMainMenu;
    Options1: TMenuItem;
    About1: TMenuItem;
    Exit2: TMenuItem;
    PrintToFile: TCheckBox;
    DriveComboBox1: TDriveComboBox;
    DirectoryListBox1: TDirectoryListBox;
    datafilename: TEdit;
    Label1: TLabel;
    busspeed: TEdit;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Label7: TLabel;
    Label8: TLabel;
    Label9: TLabel;
    Label10: TLabel;
    TowerTime: TLabel;
    TowerDistance: TLabel;
    TowerLength: TLabel;
    TowerPerceived: TLabel;
    BusTime: TLabel;
    BusDistance: TLabel;
    BusLength: TLabel;
    BusPerceived: TLabel;
    Label11: TLabel;
    UpDown1: TUpDown;
    numiterations: TEdit;
    BitBtn1: TBitBtn;
    BitBtn2: TBitBtn;
    BitBtn3: TBitBtn;
    Tower: TImage;
    Bus: TImage;
    Timer: TTimer;
    Label12: TLabel;
    ElapsedTime: TLabel;
    Label13: TLabel;
    Label14: TLabel;
    Step: TCheckBox;
    StepBtn: TBitBtn;
    procedure Exit2Click(Sender: TObject);
    procedure DriveComboBox1Change(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure BitBtn1Click(Sender: TObject);
    procedure BitBtn2Click(Sender: TObject);
    procedure BitBtn3Click(Sender: TObject);
    procedure TimerTimer(Sender: TObject);
    procedure numiterationsChange(Sender: TObject);
    procedure About1Click(Sender: TObject);
    procedure StepClick(Sender: TObject);
    procedure StepBtnClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

const
     zero = 425;

var
  Form1: TForm1;
  running: boolean;
  iteration: integer;
  minutes, hours, distance: double;
  drive, dir, filename, fileworks, s: string;
  output: TextFile;


implementation

uses relative2;

{$R *.DFM}

procedure TForm1.Exit2Click(Sender: TObject);
begin
     close;
end;

procedure TForm1.DriveComboBox1Change(Sender: TObject);
begin
     DirectoryListBox1.Drive := DriveComboBox1.Drive;
end;

procedure TForm1.FormShow(Sender: TObject);
begin
     running := false;
     BitBtn3Click(Form1); { initialze everything }
end;

procedure TForm1.BitBtn1Click(Sender: TObject); { begin }
begin
     running := true;
     BitBtn1.enabled := false;
     BitBtn2.enabled := true;
     Timer.enabled := true;

     if (PrintToFile.Checked) then
     begin
       filename := datafilename.Text;
       drive := DriveComboBox1.Drive;
       dir := DirectoryListBox1.Directory;
       fileworks := Concat('Writing to ', dir, '\', filename);
       AssignFile(output, filename);
       Rewrite(output);
       Writeln(output, 'RELATIVITY SIMULATION');
       Writeln(output, '---------------------');
       Writeln(output, Concat('Speed of Bus: ', busspeed.Text, 'c'));
       Writeln(output, Concat(numiterations.Text, ' iterations.'));
       Writeln(output, '');
       { format is :
       [---normals-------------] [----tower--------]  [-----bus-------------]
       Proper  Distance  Length  Length   Perceived   Distance  Perceived
       Time    to Bus   of Bus  of Bus  Time on Bus  to Tower  Time on Tower
       }
       Write(output, Concat(TowerTime.Caption, ', '));
       Write(output, Concat(TowerDistance.Caption, ', '));
       Write(output, Concat(IntToStr(100), ', '));
       Write(output, Concat(TowerLength.Caption, ', '));
       Write(output, Concat(TowerPerceived.Caption, ', '));
       Write(output, Concat(BusDistance.Caption, ', '));
       Writeln(output, BusPerceived.Caption);
     end;
end;

procedure TForm1.BitBtn2Click(Sender: TObject); { pause }
begin
     running := false;
     BitBtn1.enabled := true;
     BitBtn2.enabled := false;
     Timer.enabled := false;
end;

procedure TForm1.BitBtn3Click(Sender: TObject); { reset }
begin
     iteration := 0; Bus.Left := zero;
     distance := StrToFloat('108000000000');
     minutes := 0; hours := 12;

     TowerTime.Caption := IntToStr(1200);
     TowerDistance.Caption := '108000000000';
     TowerLength.Caption := IntToStr(100);
     TowerPerceived.Caption := FloatToStr(1154);
     BusTime.Caption := IntToStr(1200);
     BusDistance.Caption := '108000000000';
     BusLength.Caption := IntToStr(100);
     BusPerceived.Caption := FloatToStr(1154);
     ElapsedTime.Caption := '0';
     BitBtn1.enabled := true;
end;

procedure TForm1.TimerTimer(Sender: TObject);
var speedms, speedc, diff, dil, l, lzero, busl,
    pmins, phrs: double;
    hrs, mins, buslength, dist, percmins, perchrs: string;

begin
     iteration := iteration + 1;
     Bus.Left := Bus.Left - (390 div StrToInt(numiterations.Text));

     if (iteration = StrToInt(numiterations.Text)) then
     begin
       BitBtn2Click(Form1);
       BitBtn1.enabled := false;
     end;

     distance := distance - (StrToFloat('108000000000') / StrToInt(numiterations.Text));
     speedms := StrToFloat(busspeed.Text) * 300000000;
     speedc := StrToFloat(busspeed.Text);

     { clocks }
     dil := StrToFloat('108000000000') / speedms / StrToInt(numiterations.Text);
     diff := dil / 60;
     minutes := minutes + diff;
     if (minutes > 59) then
     begin
       hours := hours + 1;
       minutes := minutes - 60;
     end;
     Str(minutes:4:2, mins);
     Str(hours:2:0, hrs);
     if (hours < 10) then hrs := Concat('0', hrs);
     if (minutes < 10) then mins := Concat('0', mins);
     TowerTime.Caption := Concat(hrs, mins);
     BusTime.Caption := Concat(hrs, mins);
     ElapsedTime.Caption := FloatToStr(StrToFloat(Elapsedtime.Caption) + dil);

     { calc bus length }
     busl := 100 * (Sqrt(1 - Sqr(speedc)));
     Str(busl:6:2, buslength);
     TowerLength.Caption := buslength;

     { calc length contraction }
     { for tower }
     lzero := distance;
     TowerDistance.Caption := FloatToStr(lzero);
     { for bus }
     l := lzero * (Sqrt(1 - Sqr(speedc)));
     Str(l:0:2, dist);
     BusDistance.Caption := dist;

     { calc perceived time }
     pmins := minutes - (distance / 300000000 / 60);
     phrs := hours;
     if (pmins < 0) then
     begin
          pmins := 60 + pmins;
          phrs := phrs - 1;
     end;
     Str(pmins:4:2, percmins);
     Str(phrs:2:0, perchrs);
     if (pmins < 10) then percmins := Concat('0', percmins);
     TowerPerceived.Caption := Concat(perchrs, percmins);
     pmins := minutes - (StrToFloat(BusDistance.Caption) / 300000000 / 60);
     phrs := hours;
     if (pmins < 0) then
     begin
          pmins := 60 + pmins;
          phrs := phrs - 1;
     end;
     Str(pmins:4:2, percmins);
     Str(phrs:2:0, perchrs);
     if (pmins < 10) then percmins := Concat('0', percmins);
     BusPerceived.Caption := Concat(perchrs, percmins);

     { write to data file }
     { maybe }
     if (PrintToFile.Checked) then { write data to file }
     begin
          { format is :
          [---normals-------------] [----tower--------]  [-----bus-------------]
          Proper  Distance  Length  Length   Perceived   Distance  Perceived
           Time    to Bus   of Bus  of Bus  Time on Bus  to Tower  Time on Tower
          }
          Write(output, Concat(TowerTime.Caption, ', '));
          Write(output, Concat(TowerDistance.Caption, ', '));
          Write(output, Concat(IntToStr(100), ', '));
          Write(output, Concat(TowerLength.Caption, ', '));
          Write(output, Concat(TowerPerceived.Caption, ', '));
          Write(output, Concat(BusDistance.Caption, ', '));
          Writeln(output, BusPerceived.Caption);
          if (iteration = StrToInt(numiterations.Text)) then CloseFile(output);
     end;

     { are we stepping through iterations one at a time? }
     if (Step.Checked) then Timer.enabled := false;
end;

procedure TForm1.numiterationsChange(Sender: TObject);
begin
     if (StrToInt(numiterations.Text) > 100) then
       numiterations.Text := '100';
     if (StrToInt(numiterations.Text) < 3) then
       numiterations.Text := '3';
end;

procedure TForm1.About1Click(Sender: TObject);
begin
     AboutForm.show;
end;

procedure TForm1.StepClick(Sender: TObject);
begin
     if (Step.Checked) then StepBtn.enabled := true
     else StepBtn.enabled := false;
end;

procedure TForm1.StepBtnClick(Sender: TObject);
begin
     Timer.enabled := true;
end;

end.
