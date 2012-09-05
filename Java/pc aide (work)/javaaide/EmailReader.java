import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BatSQL;
import SaoWorker;

/**
 *   Pops up a window containing the message selected.
 *
 *   @author i'll give you three guesses
 *   @version june 6 1998
 */

public class EmailReader
{
  Frame f;
  Button close;
  TextArea t;
  Panel p;
  
  /**
   *  Constructor displays everything.
   *
   *  @param w The SaoWorker
   *  @param i The index of the message from the list
   */
  public EmailReader(SaoWorker w, int i)
  {
    String from = new String();
    String date = new String();
    String time = new String();
    String subj = new String();
    String msg  = new String();
    
    String get = "select * from EMAIL where MTO='" + w.getUserID() + "'";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(get);
    
    try
    {
      int j;
      for (j = 0; j < i; j++)
      {
        rs.next();
      }
      from = rs.getString(1);
      date = rs.getString(3).substring(0, 10);
      time = rs.getString(4);
      subj = rs.getString(5);
      msg  = rs.getString(6);      
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
    
    f = new Frame(subj);
    close = new Button("Close");
    t = new TextArea("", 5, 25, TextArea.SCROLLBARS_VERTICAL_ONLY);
    t.setEditable(false);
    p = new Panel();
    
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    
    p.setLayout(new GridLayout(0, 1));
    p.add(new Label("From: " + from));
    p.add(new Label("Date: " + date));
    p.add(new Label("Time: " + time));
    p.add(new Label("Subj: " + subj));
    p.add(close);    
    f.setLayout(new GridLayout(2, 1));
    t.setText(msg);
    f.add(p);
    f.add(t);
    
    f.setBackground(new Color(192, 192, 255));
    f.pack();
    f.setLocation(160, 160);
    f.setResizable(false);
    f.show();
  } //end of constructor
}