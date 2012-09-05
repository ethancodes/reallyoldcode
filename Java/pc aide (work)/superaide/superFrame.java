import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//BatJava
import SaoWorker;
import AideInfo;
import ChangePW;
import AideInOut;
import MyHours;
import AideList;
import ChangePP;
import ChangeMOTD;
import BatEmail;
import BatHours;
import BatDate;

public class superFrame
{
  AideInfo AideInf = new AideInfo();
  AideInOut AideIO = new AideInOut();

  final Frame sF    = new Frame("Supervisor Aide Program");
  Panel p4          = new Panel();
  Panel ppP         = new Panel();
    Button chppBtn  = new Button("Change Pay Period");
  Panel alP         = new Panel();
    Button allBtn   = new Button("View all aides");
    Button newaBtn  = new Button("Add a new Aide");
  Panel p5          = new Panel();
    Button wrkBtn   = new Button("Workers");
  Panel chpwP       = new Panel();    
    Button chpwBtn  = new Button("Change Password");
    Button exitBtn  = new Button("Exit");    
  Panel mainP       = new Panel();
  TextField aideF   = new TextField(15);
  Panel p1          = new Panel();
    Button whoBtn   = new Button("WhoIs this aide");
    Button mhBtn    = new Button("MyHours for this aide");
  Panel p2          = new Panel();
    Button inBtn    = new Button("Aide this aide In");
    Button outBtn   = new Button("Aide this aide Out");
  Panel p3          = new Panel();
    Button schBtn   = new Button("View/Edit Schedule");
    Button hrsBtn   = new Button("View/Edit Hours");
    Button delBtn   = new Button("Delete this Aide");
  Panel workersP    = new Panel();
    static TextArea wrkrta = new TextArea("Workers...", 8, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
  Panel p6          = new Panel();
    Button chMOTD   = new Button("Change MOTD");
    Button mailBtn  = new Button("eMail");
  Panel headP       = new Panel();
  
  /**
   *   Constructor. Puts everything on the screen. Formats
   *   buttons and such.
   *
   *   @param w The SaoWorker Supervisor
   */
  public superFrame(final SaoWorker w)
  {
    exitBtn.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        sF.dispose();
        System.exit(0);
      }
    }); //end of exit button
    whoBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          aideF.setText(AideInf.WhoIs(aideF.getText()));
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    }); //end of whois button
    chpwBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ChangePW chpw = new ChangePW(w);
      }
    }); //end of change password button
    inBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          SaoWorker aide = new SaoWorker();
          aide.setUserID(aideF.getText().toUpperCase());
          AideIO.AideIn(aide);
          updateWorkers();
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    }); //end of aide in button
    outBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          SaoWorker aide = new SaoWorker();
          aide.setUserID(aideF.getText().toUpperCase());
          AideIO.AideOut(aide);
          updateWorkers();
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    }); //end of aide out button
    mhBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          SaoWorker aide = new SaoWorker();
          aide.setUserID(aideF.getText().toUpperCase());
          MyHours mh = new MyHours(aide);
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    }); //end of myhours button
    newaBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        AideList AideL = new AideList();
      }
    }); //end of new aide button
    delBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          SaoWorker aide = new SaoWorker();
          aide.setUserID(aideF.getText().toUpperCase());
          AideList.DeleteAide(aide);
          aideF.setText("");
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    }); //end of delete aide button
    allBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        AideList.showAll();
      }
    }); //end of view all aides button
    wrkBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        updateWorkers();
      }
    }); //end of workers button
    schBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        GraphicSched s = new GraphicSched();
      }
    }); //end of workers button
    chppBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ChangePP chpp = new ChangePP();
      }
    }); //end of change pay period button
    chMOTD.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ChangeMOTD chmotd = new ChangeMOTD();
      }
    }); //end of change motd button
    mailBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        BatEmail email = new BatEmail(w);
      }
    });
    hrsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!aideF.getText().equals(""))
        {
          SaoWorker aide = new SaoWorker();
          aide.setUserID(aideF.getText().toUpperCase());
          BatHours bh = new BatHours(aide);
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
            "You must supply a UserID in the text field.");
        }
      }
    });
    
    updateWorkers();
    wrkrta.setEditable(false);
    
    ppP.setLayout(new FlowLayout());
    ppP.add(chppBtn);
    p1.setLayout(new FlowLayout());
    p1.add(whoBtn);
    p1.add(mhBtn);
    p2.setLayout(new FlowLayout());
    p2.add(inBtn);
    p2.add(outBtn);
    p3.setLayout(new FlowLayout());
    p3.add(schBtn);
    p3.add(delBtn);
    chpwP.setLayout(new FlowLayout());
    chpwP.add(chpwBtn);
    chpwP.add(mailBtn);
    alP.setLayout(new FlowLayout());
    alP.add(allBtn);
    alP.add(newaBtn);
    p5.setLayout(new FlowLayout());
    p5.add(wrkBtn);
    p5.add(ppP);
    p4.setLayout(new GridLayout(3, 1));
    p4.add(alP);
    p4.add(p5);    
    p4.add(chpwP);
    p6.setLayout(new FlowLayout());
    p6.add(hrsBtn);
    p6.add(chMOTD);
    
    workersP.setLayout(new GridLayout(2, 1));
    workersP.add(wrkrta);
    workersP.add(p4);
    mainP.setLayout(new GridLayout(0, 1));
    headP.setLayout(new FlowLayout());
    headP.add(new Label("      Welcome " + w.getName()));
    headP.add(exitBtn);
    mainP.add(headP);
    mainP.add(aideF);
    mainP.add(p1);
    mainP.add(p2);
    mainP.add(p3);
    mainP.add(p6);
    
    sF.setBackground(new Color(192, 192, 255));
    sF.setLayout(new GridLayout(1, 2));
    sF.add(mainP);
    sF.add(workersP);
    sF.setSize(512, 240);
    sF.setLocation(120, 120);
    sF.setResizable(false);
    sF.show();

    reminder();
  } //end of constructor
  
  /**
   *   Updates the list of aides currently working.
   */
  public void updateWorkers()
  {
    wrkrta.setText("");
    wrkrta.append("Workers...\n");
    String q = "select * from aidedin";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(q);
    try
    {
      boolean more = rs.next();
      while (more) 
      { 
        wrkrta.append(AideInf.WhoIs(rs.getString(1)) + "\n");
        more = rs.next();
      }
    } 
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      while (ex != null)
      {
        System.out.println ("SQLState: " + ex.getSQLState ());
        System.out.println ("Message:  " + ex.getMessage ());
        System.out.println ("Vendor:   " + ex.getErrorCode ());
        ex = ex.getNextException ();
        System.out.println ("");
      }
      System.exit(0);
    } //end catching SQLExceptions
    catch (java.lang.Exception ex)
    {
      System.out.println("!*******Exception caught*******!");
      System.exit(0);
    } //end catching other Exceptions  
    bSQL.disconnect();
  } //end of updateWorkers
  
  /**
   *   This method reminds supervisors when they can pick up
   *   their paychecks!
   */
  public void reminder()
  {
    BatDate bd = new BatDate();
    
    if(bd.getDoPP() == 8) //pay day!
    {
      String msgs[] = { "It's pay day!",
                        "Pick up your paycheck in payroll!" };
      BatWin remindMe = new BatWin("REMINDER!", msgs);
    }
    else if ( bd.getDoPP() == 13 )
    {
      String msgs[] = { "Time sheets are due tomorrow." };
      BatWin remindMe = new BatWin( "REMINDER!", msgs);
    }
    else if ( bd.getDoPP() == 14 )
    {
      String msgs[] = { "Time sheets are due today!!" };
      BatWin remindMe = new BatWin( "REMINDER!", msgs);
    }
    else if ( bd.getDoPP() >= 15 )
    {
      String msgs[] = { "WARNING:  The current date is outside of the",
                        "pay period!  You must update the pay period!!" };
      BatWin remindMe = new BatWin( "** WARNING **", msgs);
    
    }
  } //end of reminder

} //end of superFrame class    
    