import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//BatJava
import BatSQL;

/**
 *   This is the class that brings up the window with the
 *   selected aidelog transaction and allows the user to
 *   completely modify it.
 *
 *   @author Just play the damned theme music already!
 *   @version 6.18.1998
 */

public class HoursHack
{
  static Frame f;
  static Button hack;
  static Button done;
  TextField status;
  TextField dateinfo;
  TextField timeinfo;
  TextField action;
  Panel left;
  Panel right;
  
  String stat;
  String dinfo;
  String tinfo;
  String act;
  
  /**
   *   Constructor. Does everything.<P>
   *
   *   If the user clicks the Update button, it sets all the
   *   fields that look like the old information to the new
   *   new information (even if some didn't change).
   *   
   *
   *   @param l The line from the List
   *   @param id The aide's UserID
   */
  public HoursHack(String l, final String id)
  {
    f = new Frame("Edit Hours");
    hack = new Button("Update");
    done = new Button("Done");
    status = new TextField();
    dateinfo = new TextField();
    timeinfo = new TextField();
    action = new TextField();
    left = new Panel();
    right = new Panel();
    
    done.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    
    hack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String hacker = "update AIDELOG set ";
        hacker = hacker + "STATUS='" + status.getText().toUpperCase() + "', ";
        hacker = hacker + "DATEINFO=\"" + dateinfo.getText().substring(5, 7);
        hacker = hacker + "/" + dateinfo.getText().substring(8);
        hacker = hacker + "/" + dateinfo.getText().substring(0, 4);
        hacker = hacker + "\", ";
        hacker = hacker + "TIMEINFO=" + timeinfo.getText() + ", ";
        hacker = hacker + "ACTION='" + action.getText().toUpperCase() + "' ";
        
        hacker = hacker + "where STATUS='" + stat + "' and ";
        hacker = hacker + "DATEINFO=\"" + dinfo.substring(5, 7);
        hacker = hacker + "/" + dinfo.substring(8);
        hacker = hacker + "/" + dinfo.substring(0, 4);
        hacker = hacker + "\" and ";
        hacker = hacker + "TIMEINFO=" + tinfo + " and ";
        hacker = hacker + "ACTION='" + act + "' and ";
        hacker = hacker + "USERID='" + id + "'"; 
        
        BatSQL bSQL = new BatSQL();
        bSQL.update(hacker);
        bSQL.disconnect();
        f.dispose();
      }
    });
    
    left.setLayout(new GridLayout(0, 1));
    right.setLayout(new GridLayout(0, 1));
    left.add(new Label("STATUS: " + l.substring(0, 1)));
    right.add(status);
    status.setText(l.substring(0, 1));
    stat = l.substring(0, 1);
    left.add(new Label("DATE: " + l.substring(2, 12)));
    right.add(dateinfo);
    dateinfo.setText(l.substring(2, 12));
    dinfo = l.substring(2, 12);
    left.add(new Label("TIME: " + l.substring(13, l.length() - 2)));
    right.add(timeinfo);
    timeinfo.setText(l.substring(13, l.length() - 2));
    tinfo = l.substring(13, l.length() - 2);
    left.add(new Label("ACTION: " + l.substring(l.length()-1)));
    right.add(action);
    action.setText(l.substring(l.length()-1));
    act = l.substring(l.length()-1);
    
    left.add(hack);
    right.add(done);
    
    f.setLayout(new GridLayout(1, 2));
    f.setBackground(new Color(192, 192, 255));
    f.add(left);
    f.add(right);
    f.pack();
    f.setLocation(180, 180);
    f.setResizable(false);
    f.show();
  }
} //end of class