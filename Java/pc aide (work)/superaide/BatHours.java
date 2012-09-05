import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//BatJava
import BatSQL;
import BatWin;
import SaoWorker;
import HoursHack;
import HoursAdd;

/**
 *   A class that will allow the supervisors to edit the hours
 *   an aide has worked. This is useful if someone forgets to
 *   AIDE OUT at the end of a shift. So is shooting the person,
 *   but it's almost 3am so i need to code something or i won't
 *   be able to stay awake.<P>
 *
 *   Revisions made during normal working hours.
 *
 *   @author Batman
 *   @version 06-18-1998
 */

public class BatHours
{
  static Frame  f;
  Panel p;
  static List   l;
  Button edit;
  Button done;
  Button refresh;
  Button del;
  Button add;
  
  /**
   *   Constructor.<P>
   *
   *   Sets up buttons for Edit Add Delete and Refresh.
   *
   *   @param w SaoWorker. Go figure.
   */
  
  public BatHours(final SaoWorker w)
  {
    f = new Frame("Edit Hours");
    p = new Panel();
    l = new List();
    edit = new Button("Edit");
    done = new Button("Done");
    refresh = new Button("Refresh");
    del = new Button("Delete");
    add = new Button("Add");
    
    edit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (l.getSelectedIndex() > -1)
        {
          HoursHack hh = new HoursHack(l.getSelectedItem(), w.getUserID());
        }
      }
    });
    done.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    refresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        fillList(w.getUserID());
      }
    });
    del.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String line = l.getSelectedItem();
        String stat = line.substring(0, 1);
        String dinf = line.substring(2, 12);
        String tinf = line.substring(13, line.length() - 2);
        String act  = line.substring(line.length() - 1);
        
        String delline = "delete from AIDELOG where ";
        delline = delline + "STATUS='" + stat + "' and ";
        delline = delline + "DATEINFO=\"";
        delline = delline + dinf.substring(5, 7) + "/";
        delline = delline + dinf.substring(8) + "/";
        delline = delline + dinf.substring(0, 4) + "\" and ";
        delline = delline + "TIMEINFO=" + tinf + " and ";
        delline = delline + "ACTION='" + act + "' and ";
        delline = delline + "USERID='" + w.getUserID() + "'";
        
        BatSQL bSQL = new BatSQL();
        bSQL.update(delline);
        bSQL.disconnect();
      }
    });
    add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        HoursAdd ha = new HoursAdd(w.getUserID());
      }
    });
    
    p.setLayout(new FlowLayout());
    p.add(edit);
    p.add(add);
    p.add(del);
    p.add(refresh);
    
    fillList(w.getUserID());
    
    f.setBackground(new Color(192, 192, 255));
    f.setLayout(new BorderLayout());
    f.add(p, BorderLayout.NORTH);
    f.add(l, BorderLayout.CENTER);
    f.add(done, BorderLayout.SOUTH);
    f.pack();
    f.setLocation(160, 160);
    f.setResizable(false);
    f.show();
  } //end of constructor
  
  /**
   *   Fills the java.awt.List with everything that the
   *   SaoWorker has in the aidelog. Messy fetches from
   *   AIDELOG.
   *
   *   @param id The UserID of the aide..
   */
  public void fillList(String id)
  {
    l.removeAll();
    String logq = "select * from aidelog where USERID='" + id + "' ";
    logq = logq + "order by DATEINFO, TIMEINFO";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(logq);
    
    try
    {
      boolean more = rs.next();
      String line = new String();
      while (more)
      {
        line = rs.getString(1) + " " + rs.getString(3).substring(0, 10) + " ";
        line = line + rs.getString(4) + " " + rs.getString(5);
        l.add(line);
        more = rs.next();
      }
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
  } //end of fillList
} //end of class