import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//"what kind of a world do we live in where a man dressed up
// as a BAT gets all my press?"
import BatSQL;
import BatWin;

/**
 *  Handles changing the pay period. A window pops up with the
 *  current pay period and you can change it in the same
 *  window.
 *
 *  @author Namtab
 *  @version 5-28-1998
 */

public class ChangePP
{
  private Frame ppF;
  private TextField start;
  private TextField stop;
  private Button change;
  private Button cancel;
  
  private Panel startP;
  private Panel stopP;
  private Panel btnP;
  
  private String keepStart;
  
  /**
   *  Constructor. Also displays window. Retrieves current
   *  settings for the pay period, and allows supervisors
   *  to change these settings. Prevents user from entering
   *  blanks.
   */
  public ChangePP()
  {
    ppF    = new Frame("Change the Pay Period");
    start  = new TextField(11);
    stop   = new TextField(11);
    change = new Button("Change");
    cancel = new Button("Cancel");
    
    startP = new Panel();
    stopP  = new Panel();
    btnP   = new Panel();
    
    change.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (!start.getText().equals("") && !stop.getText().equals(""))
        {
          String newstart = start.getText();
          String newstop = stop.getText();
          String ppup = "delete from PAYPERIOD"; //don't shriek yet!
          BatSQL bSQL = new BatSQL();
          bSQL.update(ppup);
          ppup = "insert into PAYPERIOD values (\"";
          ppup = ppup + newstart + "\", \"";
          ppup = ppup + newstop + "\")";
          /*here's how this works:
           *  there's only one record in PAYPERIOD (PP).
           *  the normal way of updating this record would look
           *  something like "update PAYPERIOD set START="5/15/1998"
           *  STOP="5/28/1998"
           *  granted that's 1 line and it's quick. but why be normal?
           *  since there's only one record you can delete that one
           *  record and fill it with a new one. yeah, two lines as
           *  opposed to one, but it's simpler when you think about
           *  it.
           */
          bSQL.update(ppup);
          //i also want to get rid of old stuff in aidelog
          //keeps the db from getting cluttered
          ppup = "delete from AIDELOG where dateinfo<=\"" + keepStart + "\"";
          bSQL.update(ppup);
          bSQL.disconnect();
          BatWin bw = new BatWin("Change Pay Period", "Pay Period Changed.");
          ppF.dispose();
        }
        else
        {
          BatWin dumbass = new BatWin("ERROR!",
          	"You must supply dates in both fields.");
        }
      }
    }); //end of change pay period button
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        ppF.dispose();
      }
    }); //end of cancel button
    
    //fetch current setting for pay period
    String ppq = "select * from PAYPERIOD";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(ppq);
    
    try
    {
      rs.next();
      String s = rs.getString(1).substring(0, 10);
      String ss = s.substring(5, 7) + "/" + s.substring(8, 10);
      ss = ss + "/" + s.substring(0, 4);
      start.setText(ss);
      s = rs.getString(2).substring(0, 10);
      ss = s.substring(5, 7) + "/" + s.substring(8, 10);
      ss = ss + "/" + s.substring(0, 4);
      stop.setText(ss);
      } //end of try
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
    //now display the goodies!    
    startP.setLayout(new FlowLayout());
    startP.add(new Label("Pay Period Starts: "));
    startP.add(start);
    keepStart = start.getText();
    stopP.setLayout(new FlowLayout());
    stopP.add(new Label("Pay Period Stops:  "));
    stopP.add(stop);
    btnP.setLayout(new FlowLayout());
    btnP.add(change);
    btnP.add(cancel);
    
    ppF.setBackground(new Color(192, 192, 255));
    ppF.setLayout(new GridLayout(4, 1));
    ppF.add(new Label("Change the Pay Period"));
    ppF.add(startP);
    ppF.add(stopP);
    ppF.add(btnP);
    
    ppF.pack();
    ppF.setLocation(320, 160);
    ppF.setResizable(false);
    ppF.show();
  } //end of changing the pay period
}//end of class