import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.sql.*;

//BatKav Productions java classes
//aka BatJava
import AideInOut;
import AideInfo;
import BatSQL;
import ChangePW;
import MyHours;
import BatImage;
import BatEmail;
import BatDate;

/**
 *   SaoFrame is the primary window. Once a user has
 *   successfully LoggedIn, any actions will take
 *   place from this window.
 *
 *   @author   Caped Crusader
 *   @version  6-18-1998
 */

public class SaoFrame
{
  //stuff on the frame
  private Frame        saoFrame = new Frame("SAO Aide Program");
  private Panel        mainP    = new Panel();
  private Panel        lblP     = new Panel();  
    private Button     exitBtn  = new Button("Exit");
  private Panel        btnsP    = new Panel();
  private Panel        aideP    = new Panel();
    private Button     aideIn   = new Button("Aide IN");
    private Button     aideOut  = new Button("Aide OUT");
  private Panel        moreP    = new Panel();
    private Button     wrksBtn  = new Button("Workers");
    private Button     allBtn   = new Button("View all Aides");
  private Panel        whoIsP   = new Panel();
    private TextField  whoIs    = new TextField(25);
    private Button     whoIsBtn = new Button("?");
  private Panel        workersP = new Panel();
    private static TextArea   workers  = new TextArea("Workers...", 8, 25, TextArea.SCROLLBARS_VERTICAL_ONLY);
  private Panel        myhrsP   = new Panel();
    private Button     myhrsBtn = new Button("MyHours");
    private Button     chngBtn  = new Button("Change Password");
    private Button     mailBtn  = new Button("eMail");
  private Panel        motdP    = new Panel();
    private TextArea   motd     = new TextArea("The Message of the Day...\n", 5, 35, TextArea.SCROLLBARS_VERTICAL_ONLY);
  private Panel        about    = new Panel();
  
  private AideInOut AideIO  = new AideInOut();
  private AideInfo  AideInf = new AideInfo();
  
  /**
   *   Constructor.
   *
   *   @param w The SaoWorker object passed from JavaAide
   *   @see JavaAide
   */
  
  public SaoFrame(final SaoWorker w)
  {
    exitBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        saoFrame.dispose();
        System.exit(0);
      }
    });
    aideIn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        AideIO.AideIn(w);
        updateWorkers();
      }
    });
    aideOut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        AideIO.AideOut(w);
        updateWorkers();
      }
    });
    whoIsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        whoIs.setText(AideInf.WhoIs(whoIs.getText()));
      }
    });
    chngBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ChangePW newPW = new ChangePW(w);
      }
    });
    myhrsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        MyHours mh = new MyHours(w);
      }
    });
    mailBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        BatEmail email = new BatEmail(w);
      }
    });
    wrksBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        updateWorkers();
      }
    });
    allBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        aideShowAll();
      }
    });

    workers.setEditable(false);
    motd.setEditable(false);
    updateWorkers();
    getMotd();

    mainP.setLayout(new GridLayout(2,2));
    lblP.setLayout(new FlowLayout());
    aideP.setLayout(new FlowLayout());
    whoIsP.setLayout(new FlowLayout());
    workersP.setLayout(new FlowLayout());
    myhrsP.setLayout(new FlowLayout());
    btnsP.setLayout(new GridLayout(0, 1));
    motdP.setLayout(new FlowLayout());
    about.setLayout(new BorderLayout(10, 10));
    moreP.setLayout(new FlowLayout());
        
    lblP.add(new Label("WELCOME " + w.getName()));
    lblP.add(exitBtn);
    aideP.add(aideIn);
    aideP.add(aideOut);
    whoIsP.add(new Label("WhoIs "));
    whoIsP.add(whoIs);
    whoIsP.add(whoIsBtn);
    workersP.add(workers);
    myhrsP.add(chngBtn);
    myhrsP.add(myhrsBtn);
    myhrsP.add(mailBtn);
    motdP.add(motd);
    moreP.add(wrksBtn);
    moreP.add(allBtn);
    
    btnsP.add(lblP);
    btnsP.add(aideP);
    btnsP.add(moreP);
    btnsP.add(myhrsP);
    btnsP.add(whoIsP);
    
    mainP.add(btnsP);
    mainP.add(workersP);
    mainP.add(motdP);
    mainP.add(about);
          
    saoFrame.setBackground(new Color(192, 192, 255));
    saoFrame.add(mainP);
    saoFrame.pack();
    saoFrame.setLocation(50, 80);
    saoFrame.setResizable(false);
    saoFrame.show();
    
    about.add(new BatImage("logo.gif", 210, 100), BorderLayout.CENTER);
    reminder();
  } //end constructor
  
  /**
   *   Updates the list of aides currently working.
   */
  
  public void updateWorkers()
  {
    workers.setText("");
    workers.append("Workers...\n");
    String q = "select * from aidedin";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(q);
    try
    {
      boolean more = rs.next();
      while (more) 
      { 
        workers.append(AideInf.WhoIs(rs.getString(1)) + "\n");
        more = rs.next();
      }
    } 
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("updateworkers");
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
   *  Reads in the Message Of The Day from the table MOTD
   *  and dumps it into a text area for the user to read. This
   *  allows supervisors to convey cheery messages, harsh warnings,
   *  or reminders about meetings. This will NOT take the place of
   *  eMail, but everybody who logs in will see it- some people
   *  never check their eMail.
   */
  public void getMotd()
  {
    String motdq = "select * from MOTD";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(motdq);
    
    try
    {
      rs.next();
      motd.append(rs.getString(1));
    } //end of try
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("getmotd");
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
  } //end of getMotd()
  
  /**
   *   This method reminds workers that they need to sign their
   *   time sheets or pick up their paychecks if it's time to
   *   do it.
   */
  public void reminder()
  {
    BatDate bd = new BatDate();
    if (bd.getDoPP() == 13) //time sheets are due tomorrow
    {
      String msgs[] = { "Time sheets are due tomorrow!",
                        "Be sure to sign yours!" };
      BatWin remindMe = new BatWin("REMINDER!", msgs); 
    }
    if (bd.getDoPP() == 14) //time sheets are due today1
    {
      String msgs[] = { "Time sheets are due today!",
                        "Have you signed yours?" };
      BatWin remindMe = new BatWin("REMINDER!", msgs); 
    }
    if (bd.getDoPP() == 8) //pay day!
    {
      String msgs[] = { "It's pay day!",
                        "Pick up your paycheck in payroll!" };
      BatWin remindMe = new BatWin("REMINDER!", msgs);
    }
  } //end of reminder
  
  /**
   *   Does the same this as showAll, except that
   *   it shows up in the javaAide side. This is
   *   because Tim Korba's an ass and thinks that
   *   the aides NEED to know the names and accts
   *   for everybody in the SAO.
   */
  public static void aideShowAll()
  {
    workers.setText("");
    workers.append("Aide List...\n");
    String allq = "select * from aidelist";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(allq);
    
    String ti = new String();
    String fn = new String();
    String ln = new String();
    String id = new String();
    int a = 0, s = 0, g = 0; //index counters
    String[] aides  = new String[100];
    String[] supers = new String[20];
    String[] gods   = new String[10];
    
    try
    {
      boolean more = rs.next();
      while (more)
      {
        ti = rs.getString(5);
        if (ti.equals("Aide"))
        {
          aides[a] = rs.getString(3) + " " + rs.getString(2) + " as " + rs.getString(1) + "\n";
          a++;
        }
        if (ti.equals("Supervisor"))
        {
          supers[s] = rs.getString(3) + " " + rs.getString(2) + " as " + rs.getString(1) + "\n";
          s++;
        }
        if (ti.equals("Programmer"))
        {
          gods[g] = rs.getString(3) + " " + rs.getString(2) + " as " + rs.getString(1) + "\n";
          g++;
        }
        more = rs.next();
      } //end of while more
    } //end of try block
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
    
    workers.append("Aides:\n");
    if (a > 0)
    {
      int i;
      for (i = 0; i < a; i++)
      {
        workers.append("   " + aides[i]);
      }
    }
    workers.append("Supervisors:\n");    
    if (s > 0)
    {
      int i;
      for (i = 0; i < s; i++)
      {
        workers.append("   " + supers[i]);
      }
    }
    workers.append("Programmers:\n");    
    if (g > 0)
    {
      int i;
      for (i = 0; i < g; i++)
      {
        workers.append("   " + gods[i]);
      }
    }
  } //end of aideShowAll
} //end of class SaoFrame