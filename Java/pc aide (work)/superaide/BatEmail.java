import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BatSQL;
import BatWin;
import EmailReader;
import EmailSender;

/**
 *   eMail client for the new PCAide System.
 *
 *   @author Dark Knight
 *   @version 06-05-1998
 */
public class BatEmail
{
  Frame  f;
  Button newMail;      //brings up win for creating a new mail
  Button delMail;      //deletes selected mail
  Button delAllMail;   //deletes all mail
  Button readMail;     //reads selected mail
  Button close;        //closes the client
  List   messages;     //a list of mail for user
  
  Panel  btnP;

  /**
   *   Let's get the ball rolling. 
   *
   *   @param w The SaoWorker who's mail we're getting
   */
  public BatEmail(final SaoWorker w)
  {
    btnP = new Panel();
    f = new Frame("BatEmail");
    messages = new List(25);
    newMail = new Button("Create new mail");
    delMail = new Button("Delete this mail");
    delAllMail = new Button("Delete all mail");
    readMail = new Button("Read this mail");
    close = new Button("Close BatEmail");
    
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    readMail.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (messages.getSelectedIndex() > 0)
        {
          EmailReader er = new EmailReader(w, messages.getSelectedIndex());
        }
      }
    });
    newMail.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        EmailSender es = new EmailSender(w);
      }
    });
    delMail.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (messages.getSelectedIndex() > 0)
        {
          String m = messages.getSelectedItem();
          String dl = "delete from EMAIL where ";
          dl = dl + "MFROM='" + m.substring(0, 4) + "' and ";
          dl = dl + "MTO='" + w.getUserID() + "' and ";
          String dateinfo = m.substring(5, 15);
          String diy = dateinfo.substring(0, 4);
          String dim = dateinfo.substring(5, 7);
          String did = dateinfo.substring(8);
          dl = dl + "DATEINFO=\"" + dim + "/" + did + "/" + diy + "\" and ";
          dl = dl + "TIMEINFO=" + m.substring(16, 20) + " and ";
          dl = dl + "SUBJECT='" + m.substring(21) + "'";
          BatSQL bSQL = new BatSQL();
          bSQL.update(dl);
          bSQL.disconnect();
          messages.removeAll();
          messages.add("Messages for " + w.getName());
          getMessages(w);
        }
      }
    });
    delAllMail.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String dl = "delete from EMAIL where MTO='" + w.getUserID() + "'";
        BatSQL bSQL = new BatSQL();
        bSQL.update(dl);
        bSQL.disconnect();
        messages.removeAll();
        messages.add("Messages for " + w.getName());
        getMessages(w);
      }
    });

    btnP.setLayout(new FlowLayout());
    btnP.add(newMail);
    btnP.add(readMail);
    btnP.add(delMail);
    btnP.add(delAllMail);
    btnP.add(close);
    
    messages.removeAll();
    messages.add("Messages for " + w.getName());
    getMessages(w);
    
    f.setBackground(new Color(192, 192, 255));
    f.setLayout(new BorderLayout());
    f.add(btnP, BorderLayout.NORTH);
    f.add(messages, BorderLayout.CENTER);
    
    f.pack();
    f.setLocation(120, 120);
    f.setResizable(false);
    f.show();
  } //end of constructor
  
  /**
   *   Fetches messages waiting in table EMAIL for the user and
   *   dumps them in the messages list.<P>
   *
   *   Format for table EMAIL:<BR>
   *   MFrom MTo Date Time Subject Msg...
   *
   *   @param w The SaoWorker who's mail we're fetching
   */
  public void getMessages(SaoWorker w)
  {
    String get = "select * from EMAIL where MTO='" + w.getUserID() + "'";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(get);
    
    try
    {
      boolean more = rs.next();
      while (more)
      {
        String line = rs.getString(1) + " " + rs.getString(3).substring(0, 10);
        line = line + " " + rs.getString(4) + " " + rs.getString(5);
        messages.add(line);
        more = rs.next();
      } //end of while
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
  } //end of getting Messages
} //end of class
    