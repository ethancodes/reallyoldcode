/**
 *  Generic Queue - Handles all reference types.
 *  Note: this queue does not accept integers
 *
 *  @author Ken Schulz
 *  @version 09.29.1998, 12.06.1998
 */
import java.util.Vector;

public class Queue
{
   protected Vector data;

/**
 * Default constructor
 */
   public Queue()
   {
      data = new Vector();
   }

/**
 * This constructor accepts an integer value for the default size of the queue
 */
   public Queue(int size)
   {
      data = new Vector(size);
   }

/**
 * enqueue places an element at the end of a queue
 *
 * @param obj is the object to be enqueued
 */
   public void enqueue(Object obj)
   {
      data.addElement(obj);
   }

/**
 * places an element at the beginning of a queue
 *
 * @param obj is the object to be pushed
 */
   public void push(Object obj)
   {
      data.insertElementAt(obj, 0);
   }

/**
 * dequeue removes and returns the first element in the queue
 *
 * @return the dequeued element in the queue
 */
   public Object dequeue()
   {
      Object returnObject = data.firstElement();
      data.removeElementAt(0);
      return returnObject;
   }

/**
 * getSize returns the number of elements in the queue
 *
 * @return the number of elements in the queue
 */
   public int getSize()
   {
      return data.size();
   }

/**
 * isEmpty returns true if the queue is empty
 *
 * @return true if queue is empty
 */
   public boolean isEmpty()
   {
      return data.isEmpty();
   }

/**
 * getElementAt returns the current element at specified index
 *
 * @param index of element to be returned
 * @return element at index
 */
   public Object getElementAt(int index)
   {
      return data.elementAt(index);
   }

}  // END of class Queue