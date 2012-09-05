import java.awt.*;
import java.awt.event.*;

//BatJava
import BatSQL;
import BatWin;

/**
 *  ChangePW is a complete password-change utility.
 *
 *  @author  Batman
 *  @version 4-16-1998
 */

public class ChangePW
{
  private Frame pwF           = new Frame("Change Password");
  private Panel currentP      = new Panel();
    private TextField current = new TextField(8);
  private Panel new1P         = new Panel();
    private TextField new1    = new TextField(8);
  private Panel new2P         = new Panel();
    private TextField new2    = new TextField(8);
  private Panel btnP          = new Panel();
    private Button submit     = new Button("Submit");
    private Button cancel     = new Button("Cancel");
   
  /**
   *  Pops open a window, flags errors, and updates the database (God Function) .
   *
   *  @param w The Aide in Question
   *  @see SaoWorker
   *  @see SaoFrame
   */
  public ChangePW(final SaoWorker w)
  {
    cancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        pwF.dispose();
      }
    });
    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String n1 = new1.getText();
        String n2 = new2.getText();
        String c  = current.getText();
        if (n1.toUpperCase().equals("") || n2.toUpperCase().equals("") || c.toUpperCase().equals(""))
        {
          BatWin popup = new BatWin("Error!", "You must enter something in each of the fields.");
        }
        else if (!n1.toUpperCase().equals(n2.toUpperCase()))
        {
          BatWin popup = new BatWin("Error!", "Your new passwords do not match!");
        }
        else if (!c.toUpperCase().equals(w.getPWord()))
        {
          BatWin popup = new BatWin("Error!", "The password you entered is not your current password!");
        }
        else
        {
          BatSQL bSQL = new BatSQL();
          String up = "update AIDELIST set PASS=\"" + new1.getText().toUpperCase();
          up = up + "\" where USERID=\"" + w.getUserID() + "\"";
          bSQL.update(up);
          bSQL.disconnect();
          //don't forget to update the SaoWorker object
          w.setPWord(new1.getText().toUpperCase());
          BatWin popup = new BatWin("Notify", "Password successfully changed.");
          pwF.dispose();
        }
      } //end of actionPerformed...
    });
    current.setEchoChar('*');
    new1.setEchoChar('*');
    new2.setEchoChar('*');
    
    pwF.setLayout(new GridLayout(4, 1));
    currentP.setLayout(new FlowLayout());
    new1P.setLayout(new FlowLayout());
    new2P.setLayout(new FlowLayout());
    btnP.setLayout(new FlowLayout());
    
    currentP.add(new Label("Current: "));
    currentP.add(current);
    new1P.add(new Label("New: "));
    new1P.add(new1);
    new2P.add(new Label("Confirm new: "));
    new2P.add(new2);
    btnP.add(submit);
    btnP.add(cancel);
    
    pwF.setBackground(new Color(192, 192, 255));    
    pwF.add(currentP);
    pwF.add(new1P);
    pwF.add(new2P);
    pwF.add(btnP);
    pwF.pack();
    pwF.setLocation(300, 100);
    pwF.setResizable(false);
    pwF.show();
  } //end of constructor
} //end of class
    