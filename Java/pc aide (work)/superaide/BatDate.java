import java.sql.*;
import java.util.*;

import BatSQL;

/**
 *   BatDate is our Date class, since everything we want
 *   to do with java.util.Date has been DEPRECATED. It's
 *   also going to contain methods for manipulating the
 *   date, like converting months (APR) to months (4).<P>
 *
 *   <FONT FACE="Courier">
 *   Sat May 02 18:30:17 EDT 1998<BR>
 *   0123456789012345678901234567
 *   </FONT>
 *
 *   @author   Batman & Robin
 *   @version  5-20-1998
 *   @see      DateTimeOp
 */

public class BatDate
{
  private int DayOfMonth;
  private int DayOfPayPeriod;
  private int Month;
  private int Year;
  private int Hours;
  private int Minutes;
  private int Slot;
    
  /**
   *   Constructor. Uses today's date and time.
   */
  public BatDate()
  {
    java.util.Date ddd = new java.util.Date();
    String today = ddd.toString();
    DayOfMonth = new Integer(today.substring(8, 10)).intValue();
    Month = MonthTextToInt(today.substring(4, 7));
    Year = new Integer(today.substring(24)).intValue();
    DayOfPayPeriod = findDOPP(DayOfMonth, Month, Year);
    Hours = new Integer(today.substring(11, 13)).intValue();
    Minutes = new Integer(today.substring(14, 16)).intValue();
    Slot = new Integer(getSlot(Hours, Minutes, DayOfPayPeriod).substring(4, 6)).intValue();
  }
  
  /**
   *   Constructor where you can specify the date and time.
   *
   *   @param dom Day of the Month
   *   @param mon The Month
   *   @param yr The Year
   *   @param hrs The Hours
   *   @param mins The Minutes
   */
  public BatDate(int dom, int mon, int yr, int hrs, int mins)
  {
    DayOfMonth = dom;
    Month = mon;
    Year = yr;
    DayOfPayPeriod = findDOPP(DayOfMonth, Month, Year);
    Hours = hrs;
    Minutes = mins;
    Slot = new Integer(getSlot(Hours, Minutes, DayOfPayPeriod).substring(4, 6)).intValue();
  }
  
  /**
   *   Get the day of the month.
   *
   *   @return Day of the Month
   */
  public int getDoM() { return DayOfMonth; }
  
  /**
   *   Get the day of the pay period.
   *
   *   @return Day of the Pay Period
   */
  public int getDoPP() { return DayOfPayPeriod; }
  
  /**
   *   Get the day of the week.
   *
   *   @return Day of the Week
   */
  public int getDoW() { return (DayOfPayPeriod % 2); }
  
  /**
   *   Get the day of the week.
   *
   *   @return Day of the Week TEXT!
   */
  public String getDOW()
  {
    String dow = "";
    int dayweek = getDoW();
    if (dayweek == 1) { dow = "Sun"; }
    else if (dayweek == 2) { dow = "Mon"; }
    else if (dayweek == 3) { dow = "Tue"; }
    else if (dayweek == 4) { dow = "Wed"; }
    else if (dayweek == 5) { dow = "Thu"; }
    else if (dayweek == 6) { dow = "Fri"; }
    else if (dayweek == 7) { dow = "Sat"; }
    return dow;
  }
  
  /**
   *   Get the month.
   *
   *   @return the Month as an int.
   */
  public int getMonth() { return Month; }
  
  /**
   *   Gets the hour.
   *
   *   @return Hour as an int.
   */
  public int getHours() { return Hours; }
  
  /**
   *   Gets the hours in string form.
   *
   *   @return Hour as a string.
   */
  public String getStringHours()
  {
    String hrs = new String();
    if (Hours < 10) { hrs = "0" + new Integer(Hours).toString(); }
    else { hrs = new Integer(Hours).toString(); }
        
    return hrs;
  }

  /**
   *   Gets the minute.
   *
   *   @return Minute as an int.
   */
  public int getMinutes() { return Minutes; }
  
  /**
   *   Gets the minutes in string form.
   *
   *   @return Minutes as a string.
   */
  public String getStringMinutes()
  {
    String mins = new String();
    if (Minutes < 10) { mins = "0" + new Integer(Minutes).toString(); }
    else { mins = new Integer(Minutes).toString(); }
        
    return mins;
  }
  
  /**
   *   Gets the year.
   *
   *   @return The Year.
   */
  public int getYear() { return Year; }
  
  /**
   *  Converts a String month to it's int equiv.
   *
   *  @return Month as an int 1..12
   */
   public int MonthTextToInt(String mon) {
   
             if (mon.toLowerCase().equals("jan")) return 1;
        else if (mon.toLowerCase().equals("feb")) return 2;
        else if (mon.toLowerCase().equals("mar")) return 3;
        else if (mon.toLowerCase().equals("apr")) return 4;
        else if (mon.toLowerCase().equals("may")) return 5;
        else if (mon.toLowerCase().equals("jun")) return 6;
        else if (mon.toLowerCase().equals("jul")) return 7;
        else if (mon.toLowerCase().equals("aug")) return 8;
        else if (mon.toLowerCase().equals("sep")) return 9;
        else if (mon.toLowerCase().equals("oct")) return 10;
        else if (mon.toLowerCase().equals("nov")) return 11;
        else if (mon.toLowerCase().equals("dec")) return 12;
       else return 0;
       
   } //MonthTextToInt
  
  
  /**
   *  Given the time, and the day of the payperiod,
   *  this will calculate the slot in regards to the
   *  aidesched. Each slot is a half hour. They are 
   *  as follows:<P>
   *
   *  1   - 24  Sunday 11am to 11pm<BR>
   *  25  - 54  Monday 8am to 11pm<BR>
   *  55  - 84  Tuesday, see above<BR>
   *  85  - 114 Wednesday, see above<BR>
   *  115 - 144 Thursday, see above<BR>
   *  145 - 174 Friday, see above<BR>
   *  175 - 204 Saturday, see above<BR>
   *
   *  @return Slot ID, SLOT1, SLOT56, etc...
   */
  public String getSlot(int hours, int minutes, int dayofpayperiod)
  {
    int dpp = dayofpayperiod;
    int gSlot = 0;
    if (dpp == 3) //sunday
    {
      if (hours == 11)
      {
        if (minutes <= 30) { gSlot = 1; }
        else { gSlot = 2; }
      }
      if (hours == 12)
      {
        if (minutes <= 30) { gSlot = 3; }
        else { gSlot = 4; }
      }
      if (hours == 13)
      {
        if (minutes <= 30) { gSlot = 5; }
        else { gSlot = 6; }
      }
      if (hours == 14)
      {
        if (minutes <= 30) { gSlot = 7; }
        else { gSlot = 8; }
      }
      if (hours == 15)
      {
        if (minutes <= 30) { gSlot = 9; }
        else { gSlot = 10; }
      }
      if (hours == 16)
      {
        if (minutes <= 30) { gSlot = 11; }
        else { gSlot = 12; }
      }
      if (hours == 17)
      {
        if (minutes <= 30) { gSlot = 13; }
        else { gSlot = 14; }
      }
      if (hours == 18)
      {
        if (minutes <= 30) { gSlot = 15; }
        else { gSlot = 16; }
      }
      if (hours == 19)
      {
        if (minutes <= 30) { gSlot = 17; }
        else { gSlot = 18; }
      }
      if (hours == 20)
      {
        if (minutes <= 30) { gSlot = 19; }
        else { gSlot = 20; }
      }
      if (hours == 21)
      {
        if (minutes <= 30) { gSlot = 21; }
        else { gSlot = 22; }
      }
      if (hours == 22)
      {
        if (minutes <= 30) { gSlot = 23; }
        else { gSlot = 24; }
      }
    } //end of sunday
    else
    { //every other day besides sunday
      if (hours == 8)
      {
        if (minutes <= 30) { gSlot = 25; }
        else { gSlot = 26; }
      }
      if (hours == 9)
      {
        if (minutes <= 30) { gSlot = 27; }
        else { gSlot = 28; }
      }
      if (hours == 10)
      {
        if (minutes <= 30) { gSlot = 29; }
        else { gSlot = 30; }
      }
      if (hours == 11)
      {
        if (minutes <= 30) { gSlot = 31; }
        else { gSlot = 32; }
      }
      if (hours == 12)
      {
        if (minutes <= 30) { gSlot = 33; }
        else { gSlot = 34; }
      }
      if (hours == 13)
      {
        if (minutes <= 30) { gSlot = 35; }
        else { gSlot = 36; }
      }
      if (hours == 14)
      {
        if (minutes <= 30) { gSlot = 37; }
        else { gSlot = 38; }
      }
      if (hours == 15)
      {
        if (minutes <= 30) { gSlot = 39; }
        else { gSlot = 40; }
      }
      if (hours == 16)
      {
        if (minutes <= 30) { gSlot = 41; }
        else { gSlot = 42; }
      }
      if (hours == 17)
      {
        if (minutes <= 30) { gSlot = 43; }
        else { gSlot = 44; }
      }
      if (hours == 18)
      {
        if (minutes <= 30) { gSlot = 45; }
        else { gSlot = 46; }
      }
      if (hours == 19)
      {
        if (minutes <= 30) { gSlot = 47; }
        else { gSlot = 48; }
      }
      if (hours == 20)
      {
        if (minutes <= 30) { gSlot = 49; }
        else { gSlot = 50; }
      }
      if (hours == 21)
      {
        if (minutes <= 30) { gSlot = 51; }
        else { gSlot = 52; }
      }
      if (hours == 22)
      {
        if (minutes <= 30) { gSlot = 53; }
        else { gSlot = 54; }
      }
    } //end of m - s
    
    if (dpp == 1) { gSlot = gSlot + 120; }
    if (dpp == 2) { gSlot = gSlot + 150; }
    if (dpp == 5) { gSlot = gSlot + 30; }
    if (dpp == 6) { gSlot = gSlot + 60; }
    if (dpp == 7) { gSlot = gSlot + 90; }
    
    String gtSlot = "SLOT";
    if (gSlot > 0)
    {
      gtSlot = gtSlot + new Integer(gSlot).toString();
    }
    else
    {
      gtSlot = gtSlot + "00";
    }
    
    return gtSlot;
  } //end of getSlot
  
  /**
   *   Does the same thing as getSlot except that it returns
   *   an integer of the number. For example, for SLOT123 it
   *   will return 123.
   *
   *   @param h The hours
   *   @param m The minutes
   *   @param d The day of the pay period
   *   @return The slot as an int
   */
  public int getIntSlot(int h, int m, int d)
  {
    String s = getSlot(h, m, d);
    int slot = new Integer(s.substring(4, s.length())).intValue();
    return slot;
  }
  
  /**
   *   Calculates the day of the pay period.<P>
   *   <TABLE>
   *   <TR><TD>S</TD><TD>M</TD><TD>T</TD><TD>W</TD><TD>T</TD><TD>F</TD><TD>S</TD></TR>
   *   <TR><TD></TD><TD></TD><TD></TD><TD></TD><TD></TD><TD>1</TD><TD>2</TD></TR>
   *   <TR><TD>3</TD><TD>4</TD><TD>5</TD><TD>6</TD><TD>7</TD><TD>8</TD><TD>9</TD></TR>
   *   <TR><TD>10</TD><TD>11</TD><TD>12</TD><TD>13</TD><TD>14</TD><TD></TD><TD></TD></TR>
   *   </TABLE>
   *
   *   @return Day of Pay Period, 1..14
   */
  public int findDOPP(int dom, int m, int y) {
  
     int DOPP = 0;
  
     BatSQL bs = new BatSQL();
     
     String q = "SELECT * FROM PAYPERIOD";
     ResultSet rs = bs.query(q);
     
     String PPStart = new String();
     String PPStop  = new String();
     
     try {
     
        rs.next();
        PPStart = rs.getString(1);
        PPStop  = rs.getString(2);
        String nowy = new Integer(y).toString();
        String nowm = new Integer(m).toString();
        if (m < 10) { nowm = "0" + nowm; }
        String nowdom = new Integer(dom).toString();
        if (dom < 10) { nowdom = "0" + nowdom; }
        String now = nowy + "-" + nowm + "-" + nowdom + " 00:00:00";
        DOPP = DateDiff(PPStart, now);
                
     } catch (SQLException ex) {  
     
        System.out.println("!*******SQLException caught*******!");
        while (ex != null) {
        
          System.out.println ("SQLState: " + ex.getSQLState ());
          System.out.println ("Message:  " + ex.getMessage ());
          System.out.println ("Vendor:   " + ex.getErrorCode ());
          ex = ex.getNextException ();
          System.out.println ("");
        
        } //while
     
        System.exit(0);
     
     } //end catching SQLExceptions
     
     
     catch (java.lang.Exception ex) {
     
      System.out.println("!*******Exception caught*******!");
      System.out.println("findDOPP");
      System.exit(0);
    
     } //end catching other Exceptions
     
     bs.disconnect();     
     return DOPP + 1;
     
  } //end of findDOPP
  
  /**
   *   Returns the number of days between the two dates
   *   passed in. Assumes the second date is AFTER the
   *   first.
   *
   *   @param a The first date
   *   @param b The second date
   *   @return Number of days<BR>-1 if a is after b<BR>0 if a is b
   */
  public int DateDiff(String a, String b)
  {
    String a2 = a;
    int diff = 0;
    boolean okay = false;
    
    int amonth = new Integer(a2.substring(5, 7)).intValue();
    int aday   = new Integer(a2.substring(8, 10)).intValue();
    int bmonth = new Integer(b.substring(5, 7)).intValue();
    int bday   = new Integer(b.substring(8, 10)).intValue();
    
    if ((amonth < bmonth) || ((amonth == bmonth) && (aday < bday)))
    {    
      while (!a2.equals(b))
      {
        a2 = getNextDate(a2);
        diff++;
      }
    } //end if b is after a
    
    if ((amonth > bmonth) || ((amonth == bmonth) && (aday > bday)))
    {
      diff = -1;
    }
    
    return diff;
  } //end of DateDiff
  
  /**
   *   Calculates the previous slot id.
   *
   *   @param s Slot as a string, ex. SLOT42
   *   @return Previous slot, ex. SLOT41
   */
  public String prevSlot(String s)
  {
    int num = 0;
    String pSlot = new String();
    num = new Integer(s.substring(4, s.length())).intValue();
    num--;
    pSlot = "SLOT" + new Integer(num).toString();
    return pSlot;
  }      
  
  /**
   *   Calculates the day after the one supplied.
   *   Also known as the tomorrow method.
   *
   *   @param d Date/Time string
   *   @return Date/Time string of "tomorrow"
   */
  public String getNextDate(String d)
  {
    int month = new Integer(d.substring(5, 7)).intValue();
    int day   = new Integer(d.substring(8, 10)).intValue();
    int year  = new Integer(d.substring(0, 4)).intValue();
        
     if (month == 12)
     {
       if (day < 31) { day++; }
       else { month = 1; day = 1; year++; }
     }
     else if (month == 11)
     {
     if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 10)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 9)
     {
       if (day < 30) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 8)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 7)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 6)
     {
       if (day < 30) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 5)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 4)
     {
       if (day < 30) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 3)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 2)
     {
       if (day < 28) { day++; }
       else { month++; day = 1; }
     }
     else if (month == 1)
     {
       if (day < 31) { day++; }
       else { month++; day = 1; }
     }
     String y = new Integer(year).toString();
     String m = new Integer(month).toString();
     if (month < 10) { m = "0" + m; }
     String dy = new Integer(day).toString();
     if (day < 10) { dy = "0" + dy; }
     
     String tomorrow = y + "-" + m + "-" + dy + " 00:00:00";
     return tomorrow;
   } //end of tomorrow function
   
   /**
    *   Adds two times together. 630 + 140 is not
    *   770, it's 810.
    *
    *   @param a First time
    *   @param b Second time
    *   @return Total as an int
    */
   public int addTimes(int a, int b)
   {
     int ahrs = (a - (a % 100)) / 100; //gets us the hours
     int amin = a % 100; //gets us the minutes
     int bhrs = (b - (b % 100)) / 100;
     int bmin = b % 100;
     
     int hrs = ahrs + bhrs;
     int min = amin + bmin;
     if (min > 59)
     {
       min = min - 60;
       hrs++;
     }
     return (hrs * 100) + min;
   } //end of addTimes
   
   /**
    *  Subtracts two times. 630 - 140 is not 490, it's 350.
    *  Assumes second time less than first time.
    *
    *  @param a First time
    *  @param b Second time
    *  @return Difference as an int
    */
   public int subtractTimes(int a, int b)
   {
     int ahrs = (a - (a % 100)) / 100; //gets us the hours
     int amin = a % 100; //gets us the minutes
     int bhrs = (b - (b % 100)) / 100;
     int bmin = b % 100;
     
     int hrs = ahrs - bhrs;
     int min = amin - bmin;
     if (min < 0)
     {
       min = min + 60;
       hrs--;
     }
     return (hrs * 100) + min;
   } //end of subtract times
   
   /**
    *   Returns the date formatted like a lot of people write it.
    *
    *   @return The Date MM/DD/YYYY
    */
   public String getDate()
   {
     String d = new String();
     int number = getMonth();
     if (number < 10)
     {
       d = "0" + new Integer(getMonth()).toString() + "/";
     }
     else { d = new Integer(getMonth()).toString() + "/"; }
     number = getDoM();
     if (number < 10)
     {
       d = d + "0" + new Integer(getDoM()).toString() + "/";
     }
     else { d = d + new Integer(getDoM()).toString() + "/"; }
     d = d + new Integer(getYear()).toString();
     return d;
   } //end of getDate
      
} //the whole enchillada
     