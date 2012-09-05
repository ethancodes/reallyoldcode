import java.sql.*;

/**
 *  Handles all SQL Functions.
 *
 *  @author  Batman
 *  @version 4-16-1998
 */

public class BatSQL
{
  private static Connection con;
  private static DatabaseMetaData dma;
  
  /**
   *  Logs-on to database.
   */
    
  public BatSQL() //constructor makes connection
  {
    String url   = "jdbc:odbc:AideSys2";
    
    try
    {
      Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
      con = DriverManager.getConnection(url, "sysdba", "dbcatwoman");
      dma = con.getMetaData ();
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
  } //end of constructor/makeConnection
  
  /**
   *  Queries the database (duh!).
   *
   *  @param  s Query String
   *  @return A ResultSet (see JDBC docs)
   */
  
  public static ResultSet query(String s)
  {
    ResultSet rs = null;
    try
    {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery(s);
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
    return rs;
  } //end of query
  
  /**
   *  Updates (changes) the database.
   *  
   *  @param s Update String
   */
  
  public static void update(String s)
  {
    try
    {
      Statement st = con.createStatement();
      st.executeUpdate(s);
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
  } //end of update
  
  /**
   *  Logs off database and does cleanup.
   */
  
  public static void disconnect()
  {
    try { con.close(); }
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
  } //end of disconnect
} //end of class BatSQL