import java.awt.*;
import java.awt.event.*;

//BatJava
import BatSQL;
import BatDate;

/**
 *   This class allows supervisors to add a line to the AIDELOG
 *   database.
 *
 *   @author Batman
 *   @version 06-18-1998
 */

public class HoursAdd
{
  BatDate bd;
  Frame f;
  Button ok;
  Button no;
  TextField status;
  TextField dateinfo;
  TextField timeinfo;
  TextField action;

  /**
   *   Constructor.
   *
   *   @param id The SaoWorker's UserID
   */
  public HoursAdd(final String id)
  {
    bd = new BatDate();
    f = new Frame("Add Hours");
    ok = new Button("OK");
    no = new Button("Cancel");
    status = new TextField();
    dateinfo = new TextField();
    timeinfo = new TextField();
    action = new TextField();

    no.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
      }
    });
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        String addline = "insert into AIDELOG values('";
        addline = addline + status.getText().toUpperCase() + "', ";
        addline = addline + "'" + id + "', ";
        addline = addline + "\"" + dateinfo.getText() + "\", ";
        addline = addline + timeinfo.getText() + ", ";
        addline = addline + "'" + action.getText().toUpperCase() + "')";
        BatSQL bSQL = new BatSQL();
        bSQL.update(addline);
        bSQL.disconnect();
        f.dispose();
      }
    });

    status.setText("O");
    dateinfo.setText(bd.getDate());
    timeinfo.setText(bd.getStringHours() + bd.getStringMinutes());
    action.setText("I");

    f.setLayout(new GridLayout(5, 2));
    f.setBackground(new Color(192, 192, 255));
    f.add(new Label("STATUS: "));
    f.add(status);
    f.add(new Label("DATE: "));
    f.add(dateinfo);
    f.add(new Label("TIME: "));
    f.add(timeinfo);
    f.add(new Label("ACTION: "));
    f.add(action);
    f.add(ok);
    f.add(no);
    f.pack();
    f.setLocation(170, 170);
    f.setResizable(false);
    f.show();
  } //end of HoursAdd
} //end of class