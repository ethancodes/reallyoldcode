import java.sql.*;
import java.awt.*;
import java.awt.event.*;

//BatJava
import BatSQL;
import BatWin;
import BatDate;

/**
 *   MyHours gives a record of when an aide worked.
 *   The time the aide AIDED IN and AIDED OUT are
 *   listed for each day, as well as a total number
 *   of hours for that day. The hours are totaled by
 *   week, and then for the two weeks of the pay
 *   period. This allows aides to see when they
 *   worked and how much they will get paid, as well
 *   as making the job of timesheets much MUCH
 *   easier.
 *
 *   @author   Dark Knight
 *   @version  6-18-1998
 */
 
public class MyHours
{
  private String     ppstart;
  private String     ppstop;
  private String[]   hours = {"", "", "", "", "", "", "",
                              "", "", "", "", "", "", "",
                              "", "", "", "", "", "", "",
                              "", "", "", "", "", "", ""};
  private String[]   dates = {"", "", "", "", "", "", "",
                              "", "", "", "", "", "", ""};
  int week1 = 0, week2 = 0, total = 0;
  
  /**
   *   Constructor. Calls all the functions to calculate
   *   the hours and then diplays them in some kind of
   *   window.
   *
   *   @param w SaoWorker who's hours we're finding
   */
  
  public MyHours(SaoWorker w)
  {
    findPayPeriod();
    setUpDates();
    CalcHours(w.getUserID());

    Disp();
  } //end of MyHours constructor
  
  /**
   *   Finds the dates when the pay period starts
   *   and stops. Gets this from the PAYPERIOD table.
   */
  public void findPayPeriod()
  {
    BatSQL bSQL = new BatSQL();
    
    String q = "SELECT * FROM PAYPERIOD"; 
    ResultSet rs = bSQL.query(q);
    try
    {
      rs.next();
      ppstart = rs.getString(1);
      ppstop = rs.getString(2);
    }
    catch (SQLException ex)
    {
      System.out.println("!*******SQLException caught*******!");
      System.out.println("findPayPeriod");
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
  } //end of findPayPeriod()
  
  /**
   *   Sets up the dates array for referrence. This array
   *   contains the 14 dates from when the pay period
   *   starts to when it ends.
   *
   *   Also puts all the dates into the "hours" array."
   */
  public void setUpDates()
  {
     BatDate bd = new BatDate();
     String d = ppstart;
     int i = 0; //el countero
     while (!d.equals(ppstop))
     {
       dates[i] = d;
       hours[i] = d;
       d = bd.getNextDate(d);
       i++;
     }
     dates[i] = d; //do the last one...
     hours[i] = d;
  } // end of setUpDates.
    
  /**
   *   CalcHours does all the dirty work for MyHours.
   *   It calculates all the hours worked by the aide
   *   and puts them in the String array "hours".<P>
   *
   *   Fetch all the transactions in AIDELOG for this aide in
   *   more or less chronological order. We're only going to look
   *   at the transactions dated AFTER the pay period starts.
   *   Theoretically, the actions for these transactions will go 
   *   IOIOIOIOIOIO, because the aide is going to Aide In then Out 
   *   then In then Out. So, as long as the action for the first 
   *   transaction is an I we get the time. This is the time the
   *   aide Aided In. If there are more records (because the last
   *   action could be an Aide In) and if that next record's action
   *   is an O, we get it's time. This is the time the aide went Out.
   *   If the status on the In transaction is O or X we find out
   *   which week (1 or 2) to add the total hours to.
   *   We record the date this happened, both the times, the difference
   *   between the two times (for the amount of time they were
   *   working) and based on the status some kind of remark. Now that
   *   you're thoroughly confused, cheese is not a product of 
   *   thousands of elephants turning Japanese.
   *
   *   @param id The userid of the SaoWorker as an uppercase string
   */
  public void CalcHours(String id)
  {
    BatDate bd = new BatDate();
    BatSQL bsql = new BatSQL();
    String qry = "select * from AIDELOG where USERID='" + id + "' order by DATEINFO, TIMEINFO";
    //the ORDER BY DATEINFO, TIMEINFO means we get everything
    //in "chronological" order!
    ResultSet rset = bsql.query(qry);
       
    String date1 = new String();
    int time1 = 0;
    String date2 = new String();
    int time2 = 0;
    int hx = 0;

    try
    {
       boolean more = rset.next();
       while (more) {
       
       date1 = rset.getString(3);
       if (bd.DateDiff(ppstart, date1) >= 0)
       { //this entry is after the payperiod started
         String action = rset.getString(5);
         String status = rset.getString(1);
         if (action.equals("I")) //an aide IN transaction
         {
           time1 = rset.getInt(4); //time of aide IN
         }
         more = rset.next();
         if (more) //we can only do this stuff when the
         { //last action was NOT an aide IN
           action = rset.getString(5);
           if (action.equals("O")) //an aide OUT transaction
           {
             time2 = rset.getInt(4); //time of aide OUT
           }
           //format it and put it in the array
           hours[hx] = date1.substring(0, 10) + " ";
           hours[hx] = hours[hx] + new Integer(time1).toString() + " ";
           hours[hx] = hours[hx] + new Integer(time2).toString() + " ";
           hours[hx] = hours[hx] + new Integer(bd.subtractTimes(time2, time1)).toString() + " ";
           if (status.equals("C"))
           {
             hours[hx] = hours[hx] + "Covered for";
           }
           else
           {
             if (status.equals("X"))
             {
               hours[hx] = hours[hx] + "Not scheduled";
             }
             else
             {
               hours[hx] = hours[hx] + "Scheduled";
             }
             if (bd.DateDiff(ppstart, date1) < 7) //first week
             {
               week1 = bd.addTimes(week1, bd.subtractTimes(time2, time1));
             }
             if (bd.DateDiff(ppstart, date1) >= 7) //second week
             {
               week2 = bd.addTimes(week2, bd.subtractTimes(time2, time1));
             }
             total = bd.addTimes(week1, week2);
           }
           hx++;
         } //end if more
       } //end of if within payperiod
       more = rset.next();
       } //end of while more
     } //end of try
     catch (SQLException ex)
     {
       System.out.println("!*******SQLException caught*******!");
       System.out.println("CalcHours");
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
     bsql.disconnect();
   } // end of CalcHours(userid)
   
   /**
    *   Displays the hours worked. Whoop-ie.<P>
    *
    *   For each string that we got from CalcHours, split it
    *   up into Date, In, Out, Total, Remark, and put the resulting
    *   substring into TextArea columns. We display the hours for
    *   week 1 in one text field, week 2 in another, and the total
    *   in a third.
    */
   public void Disp()
   {
     BatDate bd = new BatDate();
     final Frame f   = new Frame("MyHours");
     Panel fieldP    = new Panel();
     TextArea dta    = new TextArea("Date\n", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);
     TextArea inta   = new TextArea("In\n", 10, 6, TextArea.SCROLLBARS_VERTICAL_ONLY);
     TextArea outta  = new TextArea("Out\n", 10, 6, TextArea.SCROLLBARS_VERTICAL_ONLY);
     TextArea totta  = new TextArea("Total\n", 10, 6, TextArea.SCROLLBARS_VERTICAL_ONLY);
     TextArea remta  = new TextArea("Remark\n", 10, 15, TextArea.SCROLLBARS_VERTICAL_ONLY);
     Panel totalP    = new Panel();
     TextField Week1 = new TextField(4);
     TextField Week2 = new TextField(4);
     TextField Total = new TextField(4);
     Button ok   = new Button("OK");
     
     ok.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e)
       {
         f.dispose();
       }
     });
     
     fieldP.setLayout(new FlowLayout());
     fieldP.add(dta);
     fieldP.add(inta);
     fieldP.add(outta);
     fieldP.add(totta);
     fieldP.add(remta);
          
     //now fill the fields
     int ind1 = 0, ind2 = 0;
     int counter;
     for (counter = 0; counter < 28; counter++)
     {
       if (hours[counter].length() > 19)
       {
         dta.append(hours[counter].substring(0, 10) + "\n");

         ind1 = hours[counter].indexOf(" ", 11);
         inta.append(hours[counter].substring(11, ind1) + "\n");

         ind2 = hours[counter].indexOf(" ", ind1 + 1);
         outta.append(hours[counter].substring(ind1, ind2) + "\n");

         ind1 = hours[counter].indexOf(" ", ind2 + 1);
         totta.append(hours[counter].substring(ind2, ind1) + "\n");
         
         remta.append(hours[counter].substring(ind1, hours[counter].length()) + "\n");
       }
     }
     
     totalP.setLayout(new GridLayout(0, 1));
     totalP.add(new Label("Format HH.MM"));
     totalP.add(Week1);
     totalP.add(Week2);
     totalP.add(Total);
     totalP.add(ok);
     
     //now calc hours for each week
     double w1 = week1 / 100.0;
     double w2 = week2 / 100.0;
     double tot = bd.addTimes(week1, week2) / 100.0;
     Week1.setText("Week 1 Hours: " + w1);
     Week2.setText("Week 2 Hours: " + w2);
     Total.setText("Total Hours:  " + tot);
     
     f.setBackground(new Color(192, 192, 255));     
     f.setLayout(new GridLayout(2, 1));
     f.add(fieldP);
     f.add(totalP);
          
     f.pack();
     f.setLocation(200, 50);
     f.setResizable(false);
     f.show();
   
   } //end of display
 } // end of whole fucking class