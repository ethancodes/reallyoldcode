import java.sql.*;
import java.awt.*;
import java.awt.event.*;

//BatJava
import BatSQL;

/**
 *  Allows supervisors to change the message of the day!
 *
 *  @author Batman, Dark Knight Detective
 *  @version 06-01-1998
 */

public class ChangeMOTD
{
  private static Frame f;
  private static TextArea ta;
  private static Button ok;
  private static Button nok;
  private static Panel p1;
  
  /**
   *  Constructor does everything. Puts current MOTD from table
   *  into the text area. If user clicks "OK" button it updates
   *  the table.
   */
  public ChangeMOTD()
  {
    f = new Frame("Change MOTD");
    ta = new TextArea("", 5, 35, TextArea.SCROLLBARS_NONE);
    ok = new Button("OK");
    nok = new Button("Cancel");
    p1 = new Panel();
    
    nok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String newmotd = new String();
        //we need to make sure that any quotation marks are
        //dealt with
        String motd = ta.getText();
        if (motd == null)
          { motd = "There is no message of the day."; }
        int i = 0;
        char c;
        char[] chars = { 'F' };
        while (i < motd.length())
        {
          c = motd.charAt(i);
          if (c == '\"') { newmotd = newmotd + "\""; }
          chars[0] = c ;
          newmotd = newmotd + new String(chars);
          i++;
        }
        System.out.println(newmotd);
        //done.
        String upmotd = "delete from MOTD";
        BatSQL bSQL = new BatSQL();
        bSQL.update(upmotd);
        upmotd = "insert into MOTD values (\"";
        upmotd = upmotd + newmotd + "\")";
        bSQL.update(upmotd);
        bSQL.disconnect();
        f.dispose();
      }
    });
    
    String getMOTD = "select * from MOTD";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(getMOTD);
    
    try
    {
      boolean more = rs.next();
      if (more) { ta.setText(rs.getString(1)); }
      else { ta.setText(""); }
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
    
    p1.setLayout(new FlowLayout());
    p1.add(ok);
    p1.add(nok);

    f.setBackground(new Color(192, 192, 255));    
    f.setLayout(new BorderLayout());
    f.add(new Label("Change the Message Of The Day"), BorderLayout.NORTH);
    f.add(ta, BorderLayout.CENTER);
    f.add(p1, BorderLayout.SOUTH);
    f.pack();
    f.setLocation(320, 160);
    f.setResizable(false);
    f.show();
  }  //end of constructor
}