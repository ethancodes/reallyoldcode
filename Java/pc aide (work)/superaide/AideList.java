import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//BatJava
import BatSQL;
import BatWin;

/**
 *   Allows supervisors to add and delete records from the Aide List.
 *   The Aide List is a list of everybody who works for the SAO. It
 *   contains the following information on each aide:<BR>
 *   <LI>Real name<BR>
 *   <LI>UserID<BR>
 *   <LI>Job Title<BR>
 */

public class AideList
{
  private Frame     naF;
  private TextField fname;
  private TextField lname;
  private TextField id;
  private Choice    title;
  private Button    add;
  private Button    no;
  
  private Panel fnP;
  private Panel lnP;
  private Panel idP;
  private Panel ttP;
  private Panel btnP;
  
  /**
   *   Constructor. It handles requests for a new aide.
   *   Pops up a window and when you click the ADD button
   *   it throws the information into the database. Also
   *   puts the aide into the Aide Schedule.
   */
  public AideList()
  {
    naF   = new Frame("New Aide");
    fname = new TextField(10);
    lname = new TextField(10);
    id    = new TextField(5);
    title = new Choice();
    add   = new Button("Add");
    no    = new Button("Cancel");
    
    fnP = new Panel();
    lnP = new Panel();
    idP = new Panel();
    ttP = new Panel();
    btnP = new Panel();
    
    no.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        naF.dispose();
      }
    }); //end of cancel button
    add.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        String aa = "insert into AIDELIST values (";
        aa = aa + "'" + id.getText().toUpperCase() + "',";
        aa = aa + "\"" + lname.getText() + "\",";
        aa = aa + "\"" + fname.getText() + "\",";
        aa = aa + "'PCAIDE98',";
        aa = aa + "'" + title.getSelectedItem() + "')";
        BatSQL bSQL = new BatSQL();
        bSQL.update(aa);
        aa = "insert into AIDESCHED values ('" + id.getText().toUpperCase() + "', ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, ";
        aa = aa + "0, 0, 0, 0, 0, 0, 0, 0, 0)";
        bSQL.update(aa);

        bSQL.disconnect();
        BatWin bw = new BatWin("Add a new Aide", "Password set to PCAIDE98");
        naF.dispose();
      }
    }); //end of add the new aide button
    
    fnP.setLayout(new FlowLayout());
    fnP.add(new Label("First name"));
    fnP.add(fname);
    
    lnP.setLayout(new FlowLayout());
    lnP.add(new Label("Last name"));
    lnP.add(lname);
    
    idP.setLayout(new FlowLayout());
    idP.add(new Label("UserID"));
    idP.add(id);
    
    ttP.setLayout(new FlowLayout());
    ttP.add(new Label("Title"));
    ttP.add(title);
    
    btnP.setLayout(new FlowLayout());
    btnP.add(add);
    btnP.add(no);
    
    title.add("Aide");
    title.add("Supervisor");
    title.add("Programmer");
    
    naF.setBackground(new Color(192, 192, 255));
    naF.setLayout(new GridLayout(0, 1));
    naF.add(new Label("Add a new aide"));
    naF.add(fnP);
    naF.add(lnP);
    naF.add(idP);
    naF.add(ttP);
    naF.add(btnP);
    
    naF.pack();
    naF.setLocation(320, 160);
    naF.setResizable(false);
    naF.show();
  } //end of constructor
  
  /**
   *   Delete an existing aide by userid. Also deletes the aide
   *   from the Aide Schedule.
   *
   *   @param w The SaoWorker to be removed from the aide list.
   */
  public static void DeleteAide(SaoWorker w)
  {
    BatSQL bSQL = new BatSQL();
    String da = "delete from AIDELIST where USERID='" + w.getUserID() + "'";
    bSQL.update(da);
    da = "delete from AIDESCHED where USERID='" + w.getUserID() + "'";
    bSQL.update(da);
    BatWin bw = new BatWin("Delete from Aide List", "Aide has been removed.");
  }
  
  /**
   *   Somehow it lists everybody in the Aide List, grouped their
   *   job title. Shows both real name and userid.
   */
  public static void showAll()
  {
    superFrame.wrkrta.setText("");
    superFrame.wrkrta.append("Aide List...\n");
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
    
    superFrame.wrkrta.append("Aides:\n");
    if (a > 0)
    {
      int i;
      for (i = 0; i < a; i++)
      {
        superFrame.wrkrta.append("   " + aides[i]);
      }
    }
    superFrame.wrkrta.append("Supervisors:\n");    
    if (s > 0)
    {
      int i;
      for (i = 0; i < s; i++)
      {
        superFrame.wrkrta.append("   " + supers[i]);
      }
    }
    superFrame.wrkrta.append("Programmers:\n");    
    if (g > 0)
    {
      int i;
      for (i = 0; i < g; i++)
      {
        superFrame.wrkrta.append("   " + gods[i]);
      }
    }
  } //end of showAll
} //end of class AideList
    