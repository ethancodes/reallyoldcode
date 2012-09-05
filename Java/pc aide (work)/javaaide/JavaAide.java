import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.sql.*;

//BatKav Productions' classes
import SaoWorker;
import SaoFrame;
import BatWin;

/**
 *   JavaAide is the "root" class. It is the LogIn
 *   screen to the JavaAide System.
 *
 *   @author   Batman
 *   @version  4-22-1998
 */

public class JavaAide
{
  private Frame     myFrame   = new Frame("LogIn");
  private Button    button1   = new Button("OK");
  private Button    button2   = new Button("Cancel");
  private TextField idField   = new TextField(4);
  private TextField pwField   = new TextField(8);
  
  private SaoWorker worker    = new SaoWorker();
  
  /**
   *   Checks to see if the user entered a valid
   *   UserID and Password.
   */
    
  public boolean canLogIn()
  {
    boolean success = false;
    String url   = "jdbc:odbc:AideSys2";
    String query = "select * from aidelist where";
    String id    = idField.getText();
    String pw    = pwField.getText();
    
    id = id.toUpperCase();
    pw = pw.toUpperCase();
    
    query = query + " USERID='" + id + "'";
    query = query + " and ";
    query = query + " PASS='" + pw + "'";
    
    try
    {
      Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver");
      Connection con = DriverManager.getConnection(url, "sysdba", "dbcatwoman");
      DatabaseMetaData dma = con.getMetaData ();
      System.out.println("\nConnected to " + dma.getURL());
      
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      
      ResultSetMetaData rsmd = rs.getMetaData();
      boolean more = rs.next();
      if (more)
      {
        success = true;
        worker.setFName(rs.getString(3));
        worker.setLName(rs.getString(2));
        worker.setUserID(rs.getString(1));
        worker.setPWord(rs.getString(4));
        worker.setTitle(rs.getString(5));
      }
                 
      //now close EVERYTHING
      rs.close();
      stmt.close();
      con.close();
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
    
    return success;
  } // end of canLogIn()
  
  /**
   *   Constructor.
   */

  public JavaAide()
  {
    class MyListener implements ActionListener
    {
      public void actionPerformed( ActionEvent e )
      {
        myFrame.dispose();
        System.exit(0);
      }
    } //end of class MyListener
    
    MyListener mEars = new MyListener();
    
    button1.addActionListener(new ActionListener() {
      public void actionPerformed( ActionEvent e )
      {
        if (canLogIn())
        {
          myFrame.dispose();
          SaoFrame saoFrame = new SaoFrame(worker);
        }
        else
        {
          String[] s = {"Error Logging In!", "Make sure you typed your UserID and Password correctly."};
          BatWin popup = new BatWin("Sorry!", s);
        }
      }
    }); //end of LogIn button

    button2.addActionListener( mEars );
    pwField.setEchoChar('*');
        
    FlowLayout myFlow = new FlowLayout(FlowLayout.CENTER, 50, 25);
    myFrame.setLayout(myFlow);
    myFrame.add(new Label("LogIn to the JavaAide System", Label.CENTER));
    myFrame.add(new Label("UserID"));
    myFrame.add(idField);
    myFrame.add(new Label("Password"));
    myFrame.add(pwField);
    myFrame.add(button1);
    myFrame.add(button2);
    
    myFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing( WindowEvent e )
      { 
        myFrame.dispose();
        System.exit(0);
      }
    }); 

    myFrame.setBackground(new Color(192, 192, 255));    
    myFrame.setResizable(false);
    myFrame.setSize(320, 240);
    myFrame.setLocation( 160, 120 );
    myFrame.setResizable(false);
    myFrame.show();
  } //end of JavaAide() constructor
  
  /**
   *   Since this is the root class we need a main.
   */
             
  public static void main (String[] arg)
  {
    System.out.println("Starting JavaAide...");
    JavaAide win = new JavaAide();
  }
    
} //end of class JavaAide