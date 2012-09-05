import java.sql.*;
import java.util.*;

//BatJava
import BatSQL;

/**
 *  Handles WhoIs requests.
 *
 *  @author  Batman
 *  @version 5-21-1998
 */
 
public class AideInfo
{

  /**
   *  Queries the database. When given a userid it
   *  returns the corresponding real name, when
   *  given a real name it returns the userid that
   *  belongs to that person.<P>
   *
   *  If the length of the string entered is 4 then it's
   *  a userid, which means we're looking for a real name.
   *  If the length is not 4, it's a real name and we're
   *  looking for a userid. Parse the real name string into
   *  first and last name. Match the information in the
   *  database and return the result.
   *
   *  @param   id User ID (ST??) or Real name
   *  @return Real name or UserID
   */
  public String WhoIs(String id)
  {
    BatSQL bSQL = new BatSQL(); //establish a connection
    String name  = "Not Found.";
    String fname = new String();
    String lname = new String();
    boolean userid = true;
    String q = "select * from aidelist where";
    if (id.length() == 4) //ST??
    {
      id = id.toUpperCase();
      q = q + " USERID='" + id + "'";
    }
    else
    {
      userid = false; //do not have a userid
      fname = id.substring(0, id.lastIndexOf(" "));
      lname = id.substring(id.lastIndexOf(" ") + 1, id.length());
      q = q + " FIRSTNAME='" + fname + "' and";
      q = q + " LASTNAME='" + lname + "'";
    }
    
    ResultSet rset = bSQL.query(q);
    try
    {
      boolean more = rset.next();
      if (more)
      {
        if (userid)
        {
          name = rset.getString(3) + " " + rset.getString(2);
        }
        else
        {
          name = rset.getString(1);
        }
      } //end of if more
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
    return name;
  } //end of WhoIs(ST??)
} //end of AideInfo