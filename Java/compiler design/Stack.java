import java.util.Vector;

/**
 *  A dynamic stack, which can contain integers.
 *
 *  @author Ethan Georgi
 *  @version 1/31/99
 */

public class Stack
{
  private Vector v;
  private int count;

  /**
   *  Constructor with no arguments
   */
  public Stack() { v = new Vector(); count = 0; }

  /**
   *  Constructor.
   *
   *  @param size, The size of the stack to create
   */
  public Stack(int size) { v = new Vector(size); count = 0; }

  /**
   *  Push an element on to the top of the stack.
   *
   *  @param value, The value to place on the top of the stack.
   */
  public void push(int value)
  {
    count++;
    v.addElement(new Integer(value));
  }

  /**
   *  Peek at the value on the top of the stack.
   *
   *  @return The value on the top of the stack.
   */
  public int top()
  {
    Integer i = (Integer)v.lastElement();
    return i.intValue();
  }

  /**
   *  Remove the value at the top of the stack.
   */
  public void pop()
  {
    count--;
    v.removeElementAt(count);
  }

  /**
   *  Is the stack empty?
   *
   *  @return TRUE is there is nothing in the stack.
   */
  public boolean isEmpty() { return (count == 0); }

  /**
   *  Pop the top item and push the new item
   *
   *  @param n The new integer to push
   */
  public void replace(int n) { pop(); push(n); }

  /**
   *  Returns the integer on the top of the stack and then pops
   *  it off the stack. equivalent to top(); pop();
   *
   *  @return The integer on top of the stack
   */
  public int topop()
  {
    int t = top();
    pop();
    return t;
  }

  public void show()
  {
    System.out.print("[ ");
    int i;
    for (i = 0; i < count; i++)
    {
      Integer j = (Integer)v.elementAt(i);
      System.out.print(j.intValue());
      System.out.print(" ");
    }
    System.out.print("]");
  }
}
