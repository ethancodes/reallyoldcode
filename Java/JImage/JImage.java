import BatImage;
import java.awt.*;
import java.awt.event.*;

public class JImage
{
  private static Frame f;
  private static Panel p;
  private static String picName;
  private static int width, height;

  public JImage(String pN, int w, int h)
  {
    picName = pN;
    width = w;
    height = h;
    f = new Frame(picName);
    p = new Panel();

    f.addWindowListener(new WindowAdapter() {
      public void windowClosing( WindowEvent e )
      {
        f.dispose();
        System.exit(0);
      }
    });

    p.setLayout(new BorderLayout());

    f.add(p);
    f.setLocation(100, 100);

    // * NOTE:
    // *   Width + 25, Height + 8

    f.setSize(width + 8, height + 25);
    f.setResizable(false);
    f.setBackground(new Color(0, 0, 0));
    f.show();

    p.add(new BatImage(picName, width, height), BorderLayout.CENTER);
  }

  public static void help()
  {
    System.out.println("JImage:");
    System.out.println("  JImage (filename) (width) (height)");
    System.out.println("  (filename) The name of the image file");
    System.out.println("  (width)    How many pixels wide the image is");
    System.out.println("  (height)   How many pixels high the image is");
    System.out.println("");
    System.out.println("  JImage supports JPGs and GIFs");
    System.exit(0);
  }

  public static void main(String[] args)
  {
    if (args.length < 3) { help(); }
    int w = Integer.parseInt(args[1]),
        h = Integer.parseInt(args[2]);
    JImage img = new JImage(args[0], w, h);
  }
} //end class
