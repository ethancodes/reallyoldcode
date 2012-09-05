import java.sql.*;
import BatSQL;

/**
 *   SaoWorker is an object to hold all the data
 *   relevent to the currently LoggedIn aide. This
 *   data can be "probed" and changed. There is more
 *   documentation in this class than actual code!
 *
 *   @author   Dark Knight Detective
 *   @version  6-15-1998
 */

public class SaoWorker
{
  public String fName,   //first name
                lName,   //last name
                userID,  //ST??
                pWord,   //password
                title;   //job title

  /**
   *  Default constructor.
   */
  public SaoWorker()
  {
    fName = "SAO";
    lName = "Worker";
    userID = "ST??";
    pWord = "PCAIDE98";
    title = "SAO Aide";
  }
  
  /**
   *   Constructor.
   *
   *   @param f First Name
   *   @param l Last Name
   *   @param id UserID
   *   @param pw Password
   *   @param t Job Title
   */
  public SaoWorker(String f, String l, String id, String pw, String t)
  {
    fName = f;
    lName = l;
    userID = id;
    pWord = pw;
    title = t;
  }
  //probes
  /**
   *  Retrieve the First Name.
   */
  public String getFName() { return fName; }

  /**
   *  Retrieve the Last Name.
   */
  public String getLName() { return lName; }
  
  /**
   *  Retrieve the whole name.
   */
  public String getName() { return fName + " " + lName; }
  
  /**
   *  Retrieve the UserID.
   */
  public String getUserID() { return userID; }
  
  /**
   *  Retrieve the Password.
   */
  public String getPWord() { return pWord; }
  
  /**
   *  Retrieve the Job Title.
   */
  public String getTitle() { return title; }

  //modification functions
  /**
   *  Change the First Name.
   *
   *  @param f New first name
   */
  public void setFName(String f) { fName = f; }
  
  /**
   *  Change the Last Name.
   *
   *  @param l New last name
   */
  public void setLName(String l) { lName = l; }
  
  /**
   *  Change the UserID.
   *
   *  @param id New UserID
   */
  public void setUserID(String id) { userID = id; }
  
  /**
   *  Set the password.
   *
   *  @param pw New password
   */
  public void setPWord(String pw) { pWord = pw; }
  
  /**
   *  Change the Job Title.
   *
   *  @param t New job title
   */
  public void setTitle(String t) { title = t; }
  
  /**
   *  There comes a time when you need ALL the information
   *  about an aide and all you have is the UserID. This
   *  method fills all the fields by going to the database
   *  and getting the rest of the information for this UserID.
   */
  public void fill()
  {
    String fillq = "select * from AIDELIST where USERID='" + getUserID() + "'";
    BatSQL bSQL = new BatSQL();
    ResultSet rs = bSQL.query(fillq);
    
    try
    {
      rs.next();
      setLName(rs.getString(2));
      setFName(rs.getString(3));
      setTitle(rs.getString(5));
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
  } //end of fill
} //end of class SaoWorker