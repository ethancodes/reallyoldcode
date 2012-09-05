#include "vector.h"

template <class T>
class eQueue
{
  protected:
    vector<T> v;
    int qSize, qF;
  public:
    eQueue() { qF = qSize = 0; };
    void enQueue(T newItem);  //add a new item to the back
    void deQueue();           //remove something from the front
    int size();               //how many items in q?
    T front();                //returns front item
    void disp();              //display the q
};

template <class T>
void eQueue<T>::enQueue(T newItem)
{
  qSize++;
  v.push_back(newItem);
}

template <class T>
void eQueue<T>::deQueue()
{
  qSize--;
  qF++;
}

template <class T>
int eQueue<T>::size()
{
  return qSize;
}

template <class T>
T eQueue<T>::front()
{
  if (qSize > 0) { return v[qF]; }
}

template <class T>
void eQueue<T>::disp()
{
  int i;
  cout << "Front > ";
  for (i = qF; i <= (qSize + qF - 1); i++)
  {
    cout << v[i] << " ";
  }
  cout << " < Back " << endl;
}
