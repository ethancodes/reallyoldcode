import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

//BatJava
import BatWin;
import BatDate;
import BatSQL;

/**
 *  AideInOut handles the actual Aiding IN and OUT of aides.
 *
 *  @author  Batman
 *  @version 06-18-1998
 */

public class AideInOut
{
  private BatDate bd;

  /**
   *  Aides an aide in (hopefully).  Updates the database and stuff.<P>
   *
   *  If the aide is not already Aided In, make a record of them in
   *  the AIDEDIN table. Find out if this aide is scheduled to work
   *  in this slot. If not, pop a window and ask who they're covering
   *  for. Record the transaction in AIDELOG.<P>
   *
   *  Status characters for AIDELOG:<BR>
   *  O means on time.<BR>
   *  X means covering another aide.<BR>
   *  C means being covered.<BR>
   *
   *  @param w Worker (to be aided in) Data
   *  @see SaoWorker
   *  @see SaoFrame
   */

  public void AideIn(SaoWorker w)
  {
    bd = new BatDate();
    w.fill();
    if (!AidedIn(w))
    {
      BatSQL bSQL = new BatSQL();
      //insert into table AIDEDIN
      String insert = "insert into aidedin values (\"" + w.getUserID() + "\", 'No')";
      bSQL.update(insert);
      //insert into table AIDELOG
      String t = bd.getStringHours() + bd.getStringMinutes();
      String s = new String();
      if (Scheduled(w))
      {
        s = "'O'";
      }
      else
      {
        s = "'X'";
        //ask who they're covering for
        WhoRUCovering(w);
      }
      insert = "insert into aidelog values (" + s + ", \"" + w.getUserID() + "\", \"" + bd.getDate() + "\", " + t + ", 'I')";
      bSQL.update(insert);
      BatWin cool = new BatWin("Success", "You have been Aided IN");
      bSQL.disconnect();
    }
    else { 
    
      String[] s = { "You are already AIDED IN.", "You must AIDE OUT first." }; 
    
      BatWin aie = new BatWin("Sorry!", s); 
      
    } //if-else
  } //end of AideIn!
  
  /**
   *  Takes an aide out of the tables. Updates the 
   *  database and stuff.<P>
   *
   *  If this aide is covering for anybody, be sure to make a record
   *  that the aide being covered has "stopped working" and is no
   *  longer being covered. Remove this aide from AIDEDIN.<P>
   *  
   *  @param w Worker (to be aided out) Data
   *  @see SaoWorker
   *  @see SaoFrame
   */
  
  public void AideOut(SaoWorker w)
  {
    bd = new BatDate();
    if (AidedIn(w))
    {
      //if this aide is covering someone
      //then take out that someone
      Covering(w);
      //remove from AIDEDIN
      BatSQL bSQL = new BatSQL();
      String remove = "delete from aidedin where USERID='" + w.getUserID() + "'";
      bSQL.update(remove);
      //make a log of this "transaction" in AIDELOG
      String t = bd.getStringHours() + bd.getStringMinutes();
      remove = "insert into aidelog values ('O', \"" + w.getUserID() + "\", \"" + bd.getDate() + "\", " + t + ", 'O')";
      bSQL.update(remove);
      BatWin cool = new BatWin("Success!", "You have been Aided OUT");
      bSQL.disconnect();
    }
    else { 
      String[] s = { "You are not AIDED IN.", "You must AIDE IN first." }; 
      BatWin aie = new BatWin("Sorry!", s); 
    }

  } //end of AideOut!
  
  /**
   *  Queries if an aide is currently aided in.<P>
   *
   *  If you're in AIDEDIN, you are Aided In, imagine that.
   *
   *  @param w Worker (in question) Data
   *  @see SaoWorker
   *  @see SaoFrame
   */
  
  private boolean AidedIn(SaoWorker w)
  {
    BatSQL bSQL = new BatSQL();
    boolean success = false;
    String id    = w.getUserID();
    String q = "select * from aidedin where USERID='" + id + "'";
    ResultSet rs = bSQL.query(q);
        
    try
    {
      boolean more = rs.next();
      if (more) { success = true; }
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
    return success;
  } //end of AidedIn?
  
  /**
   *   Finds out if a SaoWorker is supposed to be working now
   *   or not. It consults the tables AIDESCHED for this, and
   *   if they have a entry in the current slot then they're
   *   okay. Otherwise they're covering for somebody.<P>
   *
   *   Also, if it's 10 minutes before a new slot starts this
   *   looks at the next slot. This is to accomodate for the
   *   fact that sometimes aides will try to Aide In a few
   *   minutes before they're supposed to start working.<P>
   *
   *   What's more, if the SaoWorker trying to AIDE IN is a
   *   Supervisor or Programmer, the method considers them
   *   always scheduled. How convenient!
   *
   *   @param w The Aide trying to aide in
   *   @return True scheduled for this slot, or False not
   */
  public boolean Scheduled(SaoWorker w)
  {
    BatSQL bSQL = new BatSQL();
    String q = "select * from AIDESCHED where USERID='" + w.getUserID() + "'";
    ResultSet rs = bSQL.query(q);
    int h = bd.getHours();
    int m = bd.getMinutes();
    int slot = 0;
    int sched = 0;
    
    //first check if the worker is a Supervisor or Programmer
    //these two Titles are always Scheduled to work.
    if ((w.getTitle().equals("Supervisor")) || (w.getTitle().equals("Programmer")))
    {
      sched = 1;
    }
    else { //aides have to be in the correct slot      

    //this is the part where we check if they're early
    if (((m >= 20) && (m < 30)) || ((m >= 50) && (m < 60)))
    {
      if (m > 30) { h = h + 1; m = m + 10 - 60; }
      else { m = m + 10; }
      int dopp = bd.getDoPP();
      if (dopp > 7) { dopp = dopp - 7; }
      slot = bd.getIntSlot(h, m, dopp);
    }
    else 
    { 
      int dopp = bd.getDoPP();
    	 if (dopp > 7) { dopp = dopp - 7; }
    	 slot = bd.getIntSlot(h, m, dopp);
    }
    //now we carry on as usual

    try
    {
      rs.next();
      sched = rs.getInt(slot+1); //+1 to skip the USERID field!
    } //end of try
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("Scheduled");
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
      System.out.println("Scheduled");      
      System.exit(0);
    } //end catching other Exceptions
    
    } //end of else this is an aide
    
    boolean scheded = (sched == 1);
    return scheded;
  } //end of Scheduled?
  
  /**
   *   Handles when aides cover each other. They must tell
   *   us who they're covering so that that poor person
   *   doesn't get a missed shift. This of course means i
   *   have to work on MyHours some more... If they select
   *   a valid userid to cover for makes the appropriate
   *   entry in AIDELOG.
   *
   *   @param An SaoWorker who is covering someone else
   */
  public void WhoRUCovering(final SaoWorker w)
  {
    int i = 0;
    int h = bd.getHours();
    int m = bd.getMinutes();
    if (((m >= 20) && (m < 30)) || ((m >= 50) && (m < 60)))
    {
      if (m < 30) { m = m + 10; }
      else { m = m + 10 - 60; h = h + 1; }      
    }
    int dopp = bd.getDoPP();
    if (dopp > 7) { dopp = dopp - 7; }
    String slotid = bd.getSlot(h, m, dopp);
    String q = "select * from AIDESCHED where " + slotid + "=1";
    final String[] userids = {"", "", "", "", "", "", "", "", "", ""};
    final BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(q);
    
    try
    {
      boolean more = rs.next();
      if (more) //because there might be only one person for this slot
      {
        while (more)
        {
          userids[i] = rs.getString(1);
          i++;
          more = rs.next();
        }
      } //end of if more
    } //end of try
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("WhoRUCovering");
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
      System.out.println("WhoRUCovering");      
      System.exit(0);
    } //end catching other Exceptions
    
    final Frame coverF = new Frame("Covering?");
    final Panel     p  = new Panel();
    final Panel  btnP  = new Panel();
    final List  coverL = new List();
    Button    ok = new Button("Cover");
    Button   nok = new Button("Cancel");
    final int i2 = i;
    
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String id = coverL.getSelectedItem();
        BatSQL bS = new BatSQL();
        String a = "insert into aidelog values ('C', \"" + id + "\", \"" + bd.getDate() + "\", " + bd.getStringHours() + bd.getStringMinutes() + ", 'I')";
        bS.update(a);
        a = "update AIDEDIN set COVERING='" + id + "' where USERID='" + w.getUserID() + "'";
        bS.update(a);
        bS.disconnect();
        coverF.dispose();
      }
    });
    nok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        coverF.dispose();
      }
    });
    
    btnP.setLayout(new FlowLayout());
    btnP.add(ok);
    btnP.add(nok);
    
    p.setLayout(new BorderLayout());
    p.add(new Label("Who are you covering for?"), BorderLayout.NORTH);
    int j;
    for (j = 0; j <= i; j++)
    {
      coverL.add(userids[j]);
    }
    p.add(coverL, BorderLayout.CENTER);
    p.add(btnP, BorderLayout.SOUTH);
    
    coverL.select(0);
    
    coverF.setLayout(new FlowLayout());
    coverF.add(p);    
    coverF.pack();
    coverF.setLocation(200, 200);
    coverF.show();

  } //end of WhoRUCovering
  
  /**
   *   Finds out if an aide is covering for another aide
   *   and makes an entry to the AIDELOG to note that
   *   coverage has ended.
   *
   *   @param The SaoWorker who might be covering
   */
  public void Covering(final SaoWorker w)
  {
    BatSQL bSQL = new BatSQL();
    String qc = "select * from AIDEDIN where USERID='" + w.getUserID() + "'";
    ResultSet rs = bSQL.query(qc);
    String cid = new String();
    
    try
    {
      rs.next();
      cid = rs.getString(2);
    }
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("Covering");
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
      System.out.println("Covering");      
      System.exit(0);
    } //end catching other Exceptions
    
    if (!cid.equals("No")) //then covering
    {
      String uc = "insert into AIDELOG values ('C', '" + cid + "', \"" + bd.getDate() + "\", " + bd.getStringHours() + bd.getStringMinutes() + ", 'O')"; 
      bSQL.update(uc);
    }
    bSQL.disconnect();
      
  } //end of Covering

} //class AideInOut