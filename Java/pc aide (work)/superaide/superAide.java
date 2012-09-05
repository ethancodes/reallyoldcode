import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//BatJava
import BatSQL;
import BatWin;
import superFrame;
import SaoWorker;

public class superAide
{
  static final Frame saF = new Frame("Supervisor LogIn");
  static TextField id    = new TextField(4);
  static TextField pw    = new TextField();
  static Button cancel   = new Button("Cancel");
  static Button login    = new Button("LogIn");
  
  static SaoWorker w     = new SaoWorker();
  
  public static void main(String[] arg)
  {
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        saF.dispose();
        System.exit(0);
      }
    }); //end of exit button
    login.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        String q = "select * from AIDELIST where USERID='";
        q = q + id.getText().toUpperCase() + "' and ";
        q = q + "PASS='" + pw.getText().toUpperCase() + "'";
        BatSQL bSQL = new BatSQL();
        ResultSet rs = bSQL.query(q);
        
        try
        {
          boolean anything = rs.next();
          if (anything)
          { 
            String title = rs.getString(5);
            if (title.equals("Aide"))
            {
              BatWin bw = new BatWin("Sorry!", "You are not authorized for this application.");
            }
            else
            {
              w.setFName(rs.getString(3));
              w.setLName(rs.getString(2));
              w.setUserID(rs.getString(1));
              w.setPWord(rs.getString(4));
              w.setTitle(title);
              saF.dispose();
              superFrame sF = new superFrame(w);
            }
          }
          else
          {
            BatWin bw = new BatWin("Sorry!", "Invalid Password.");
          }
        }//end of try
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
          System.out.println("superAide");
          System.exit(0);
        } //end catching other Exceptions
      }
    }); //end of login button
    
    pw.setEchoChar('*');

    saF.setBackground(new Color(192, 192, 255));    
    saF.setLayout(new GridLayout(0, 2));
    saF.add(new Label("Supervisor"));
    saF.add(new Label("Log In"));
    saF.add(new Label("UserID: "));
    saF.add(id);
    saF.add(new Label("Password: "));
    saF.add(pw);
    saF.add(login);
    saF.add(cancel);
    
    saF.setSize(180, 120);
    saF.setLocation(320, 240);
    saF.setResizable(false);
    saF.show();
  } //end of main
} //end of class superAide
    