import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Date;

public class coolDice
{
  static Frame     f;
  static List      diceToChooseFrom;
  static List      diceToRoll;
  static Button    roll;
  static Button    exit;
  static Button    clear;
  static Button    add;
  static Button    remove;
  static TextArea  results;
  static TextField modifier;
  static Button    mode1;
  static Button    mode2;
  static Button    mode3;
  static TextField mode;
  static TextField target;
  
  static Panel p1;
  static Panel p2;
  static Panel p3;
  static Panel p4;
  static Panel p5;
  static Panel p6;
  static Panel p7;
  static Panel p8;
  static Panel p9;
  static Panel p10;
  static Panel p11;
  static Panel p12;
  static Panel p13;
  
  static Date d;
  static Random r;
  
  
  public static void main(String[] arg)
  {
    f = new Frame("coolDice");
    diceToChooseFrom = new List(10);
    diceToRoll = new List(10);
    roll = new Button("Roll");
    exit = new Button("Exit");
    clear = new Button("Clear");
    add = new Button("Add >>");
    remove = new Button("Remove <<");
    results = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
    modifier = new TextField();
    mode1 = new Button("Add");
    mode2 = new Button("Highest");
    mode3 = new Button("Target");
    mode = new TextField();
    target = new TextField();
    
    d = new Date();
    r = new Random(d.getTime());

    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        f.dispose();
        System.exit(0);
      }
    });
    
    add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        int i = diceToChooseFrom.getSelectedIndex();
        if (i >= 0)
        {
          diceToRoll.add(diceToChooseFrom.getSelectedItem());
        }
      }
    });
    
    remove.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        int i = diceToRoll.getSelectedIndex();
        if (i >= 0)
        {
          diceToRoll.delItem(i);
        }
      }
    });
    
    clear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        results.setText("");
      }
    });
    
    mode1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        mode.setText("Add results");
      }
    });

    mode2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        mode.setText("Highest result");
      }
    });

    mode3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        mode.setText("Results >= target");
      }
    });
    
    roll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //first make sure that something is in diceToRoll
        if (!(diceToRoll.getItemCount() == 0))
        {
          //get the modifier, if any
          int mod = 0;
          if (!modifier.getText().equals(""))
          {
            if (modifier.getText().substring(0, 1).equals("+"))
            {
              mod = new Integer(modifier.getText().substring(1)).intValue();
            }
            else
            {
              mod = new Integer(modifier.getText()).intValue();
            }
          }
          
          //get mode
          int mode2 = 0;
          if (mode.getText().equals("Add results"))
          {
            mode2 = 1;
          }
          if (mode.getText().equals("Highest result"))
          {
            mode2 = 2;
          }
          if (mode.getText().equals("Results >= target"))
          {
            mode2 = 3;
          }
          
          //get an array of numbers for the rolled dice...
          int num = diceToRoll.getItemCount();
          int rolled[] = new int[num];
          int i;
          int sides = 0;
          for (i = 0; i < num; i++)
          {
            sides = new Integer(diceToRoll.getItem(i).substring(1)).intValue();
            rolled[i] = Math.abs((r.nextInt() % sides)) + 1;
          }
          
          //output
          String output = "Results [";
          for (i = 0; i < num; i++)
          {
            output = output + diceToRoll.getItem(i);
            if (!((i + 1) == num))
            {
              output = output + ", ";
            }
          }
          output = output + "] = ";
          for (i = 0; i < num; i++)
          {
            output = output + new Integer(rolled[i]).toString();
            if (!((i + 1) == num))
            {
              output = output + ", ";
            }
          }
          results.append(output + "\n");
          //do the mode stuff...
          if (mode2 == 1)
          {
            int total = 0;
            for (i = 0; i < num; i++)
            {
              total = total + rolled[i];
            }
            output = "Add results = " + new Integer(total + mod).toString();
            results.append(output + "\n");
          }
        }
      }
    });
    
    diceToChooseFrom.add("d2");
    diceToChooseFrom.add("d3");
    diceToChooseFrom.add("d4");
    diceToChooseFrom.add("d5");
    diceToChooseFrom.add("d6");
    diceToChooseFrom.add("d8");
    diceToChooseFrom.add("d10");
    diceToChooseFrom.add("d12");
    diceToChooseFrom.add("d20");
    diceToChooseFrom.add("d30");
    diceToChooseFrom.add("d100");
    
    mode.setText("Add results");
    
    p1 = new Panel();
    p1.setLayout(new GridLayout(0, 1));
    p1.add(add);
    p1.add(remove);
    p1.add(roll);
    
    p2 = new Panel();
    p2.setLayout(new FlowLayout());
    p2.add(diceToChooseFrom);
    
    p3 = new Panel();
    p3.setLayout(new FlowLayout());
    p3.add(diceToRoll);
    
    p4 = new Panel();
    p4.setLayout(new GridLayout(1, 3));
    p4.add(p2);
    p4.add(p1);
    p4.add(p3);
    
    p6 = new Panel();
    p6.setLayout(new FlowLayout());
    p6.add(mode1);
    p6.add(mode2);
    p6.add(mode3);
    
    p7 = new Panel();
    p7.setLayout(new GridLayout(1, 2));
    p7.add(new Label("Mode"));
    p7.add(mode);
    
    p8 = new Panel();
    p8.setLayout(new GridLayout(1, 2));
    p8.add(new Label("Modifier"));
    p8.add(modifier);
    
    p9 = new Panel();
    p9.setLayout(new GridLayout(1, 2));
    p9.add(new Label("Target"));
    p9.add(target);
    
    p5 = new Panel();
    p5.setLayout(new GridLayout(0, 1));
    p5.add(p6);
    p5.add(p7);
    p5.add(p8);
    p5.add(p9);
    
    p10 = new Panel();
    p10.setLayout(new BorderLayout());
    p10.add(p4, BorderLayout.CENTER);
    p10.add(p5, BorderLayout.SOUTH);
    
    p13 = new Panel();
    p13.setLayout(new FlowLayout());
    p13.add(clear);
    p13.add(exit);
    
    p12 = new Panel();
    p12.setLayout(new BorderLayout());
    p12.add(results, BorderLayout.CENTER);
    p12.add(p13, BorderLayout.SOUTH);
    
    p11 = new Panel();
    p11.setLayout(new BorderLayout());
    p11.add(p10, BorderLayout.CENTER);
    p11.add(p12, BorderLayout.SOUTH);
    
    f.setLayout(new FlowLayout());
    f.add(p11);
    f.setResizable(false);
    f.setLocation(175, 15);
    f.pack();
    f.show();
  }
}