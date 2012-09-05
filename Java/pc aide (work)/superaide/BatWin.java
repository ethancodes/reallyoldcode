import java.awt.*;
import java.awt.event.*;

/**
 *  BatWin is a Generic Popup Window.
 *
 *  @author   Robin
 *  @version  4-16-1998
 */

public class BatWin
{

  private Frame notify;
  private Button ok    = new Button("OK");

  /**
   *  Pops up a window (duh!)
   *
   *  @param title Window Title
   *  @param text  What the window says (as an array)
   */

  public BatWin(String title, String[] text)
  {
  
    notify = new Frame(title);
        
    ok.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        notify.dispose();
      }
    });

    notify.setBackground(new Color(255, 0, 128));
    notify.setLayout(new GridLayout(text.length+1, 1));
    for (int i = 0; i < text.length; i++) {
      notify.add(new Label(text[i]));
    }
    notify.add(ok);
    notify.pack();
    notify.setLocation(175, 100);
    notify.setResizable(false);
    notify.show();
  } //end of BatWin

  /**
   *  Pops up a window (duh!)
   *
   *  @param title Window Title
   *  @param text  What the window says
   */
   
   public
   BatWin(String title, String text) {
   
     String[] s = { text };
     BatWin bw = new BatWin(title, s);
     
    } //end BatWin  
     
} //end of class