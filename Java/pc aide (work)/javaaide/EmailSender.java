import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import BatSQL;
import BatWin;
import BatDate;

/**
 *   Allows the user to send a message to another user.
 *
 *   @author me
 *   @version today
 */

public class EmailSender
{
  Frame f;
  Button close;
  Button send;
  Panel btnP;
  TextField to;
  TextField subj;
  Panel fldP;
  TextArea msg;
  /**
   *  Constructor.<P>
   *
   *  Sao Mailing List: <B>SSAO</B>
   *
   *  @param w The SaoWorker who's sending the mail
   */
  public EmailSender(final SaoWorker w)
  {
    f = new Frame("New Mail");
    close = new Button("Close");
    send = new Button("Send");
    to = new TextField(4);
    subj = new TextField(25);
    msg = new TextArea("", 3, 50, TextArea.SCROLLBARS_NONE);
    btnP = new Panel();
    fldP = new Panel();
    
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    send.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        BatDate bd = new BatDate();
        String m = "insert into EMAIL values ('" + w.getUserID() + "', ";
        if (!to.getText().toUpperCase().equals("SSAO"))
        {
          m = m + "'" + to.getText().toUpperCase() + "', ";
          m = m + "\"" + bd.getMonth() + "/" + bd.getDoM() + "/" + bd.getYear() + "\", ";
          int hr = bd.getHours();
          String hrs = new String();
          if (hr < 10) { hrs = "0" + new Integer(hr).toString(); }
          else { hrs = new Integer(hr).toString(); }
          int mn = bd.getMinutes();
          String mins = new String();
          if (mn < 10) { mins = "0" + new Integer(mn).toString(); }
          else { mins = new Integer(mn).toString(); }
          m = m + hrs + mins + ", ";
          
          //fix any 's in the subject
          //String subject = subj.getText();
          
          m = m + "\"" + subj.getText() + "\", ";
          
          //fix and 's in the msg
          m = m + "\"" + msg.getText() + "\")";
          BatSQL bSQL = new BatSQL();
          bSQL.update(m);
          bSQL.disconnect();
          BatWin bw = new BatWin("New Mail", "New mail sent!");
          f.dispose();
        } //end if not to SSAO list
        else //sending to SSAO list - propogate the message to everyone
        {
          String useridq = "select * from AIDELIST";
          BatSQL bSQL = new BatSQL();
          ResultSet rs = bSQL.query(useridq);
          
          try
          {
            boolean more = rs.next();
            int hr = 0, mn = 0;
            String hrs = new String();
            String mins = new String();
            while (more)
            {
              m = "insert into EMAIL values ('" + w.getUserID() + "', ";
              m = m + "'" + rs.getString(1) + "', ";
              m = m + "\"" + bd.getMonth() + "/" + bd.getDoM() + "/" + bd.getYear() + "\", ";
              hr = bd.getHours();
              if (hr < 10) { hrs = "0" + new Integer(hr).toString(); }
              else { hrs = new Integer(hr).toString(); }
              mn = bd.getMinutes();
              if (mn < 10) { mins = "0" + new Integer(mn).toString(); }
              else { mins = new Integer(mn).toString(); }
              m = m + hrs + mins + ", ";
              m = m + "'" + subj.getText() + "', ";
              m = m + "\"" + msg.getText() + "\")";
              bSQL.update(m);
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
          BatWin bw = new BatWin("New Mail", "New mail sent!");
          f.dispose();
        } //end else (if to SSAO list)
      }
    });
    
    btnP.setLayout(new FlowLayout());
    btnP.add(send);
    btnP.add(close);
    
    fldP.setLayout(new GridLayout(2, 2));
    fldP.add(new Label("To:"));
    fldP.add(to);
    fldP.add(new Label("Subj:"));
    fldP.add(subj);
    
    f.setBackground(new Color(192, 192, 255));
    f.setLayout(new BorderLayout());
    f.add(fldP, BorderLayout.NORTH);
    f.add(msg, BorderLayout.CENTER);
    f.add(btnP, BorderLayout.SOUTH);
    
    f.pack();
    f.setLocation(160, 160);
    f.setResizable(false);
    f.show();
  }
}