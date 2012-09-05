import java.awt.*;
import java.awt.image.*;

/**
 *  Frustrated with trying to put an image in my application,
 *  i wrote this. The idea is to make it very easy to put a
 *  GIF or JPEG into your Frame.<P>
 *
 *  Frame f = new Frame("Yeah Yeah!");<BR>
 *  f.setLayout(new FlowLayout());<BR>
 *  f.show(); //you <B>have</B> to show() before you add the BatImage!<BR>
 *  f.add(new BatImage("logo.gif"));<BR>
 */

public class BatImage extends Panel
{
  Image i;
  
  /**
   *  Constructor. Mmm...<P>
   *
   *  If you pass a width and height that are smaller that the
   *  actual image BatImage <I>will</I> crop it from the upper-left
   *  corner, whether you like it or not! 
   *
   *  @param filename The name of the image file you want to load as a String
   *  @param w The width of the image
   *  @param h The height of the image
   */
  public BatImage(String filename, int w, int h)
  {
    i = getToolkit().getImage(filename);
    setSize(w, h);
    repaint();
  }
  
  /**
   *   You have to redefine the paint method.
   *
   *   @param g Graphics
   */
  public void paint(Graphics g)
  {
    g.drawImage(i, 0, 0, this);
  }
}