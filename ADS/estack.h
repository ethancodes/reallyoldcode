#include "vector.h"

template <class T>
class eStack
{
  protected:
    vector<T> v;
    int stackTop;
  public:
    eStack() { stackTop = 0; };
    void push(T newItem);  //push a new item on the stack
    void pop();            //remove an item from the stack
    T top();               //what's the top element?
    int size();            //how many items are in the stack?
    void disp();           //display the stack...
};

template <class T>
void eStack<T>::push(T newItem)
{
  stackTop++;
  v.push_back(newItem);
}

template <class T>
void eStack<T>::pop()
{
  stackTop--;
}

template <class T>
T eStack<T>::top()
{
  return v[stackTop-1];
}

template <class T>
int eStack<T>::size()
{
  return stackTop;
}

template <class T>
void eStack<T>::disp()
{
  int i;
  for (i = stackTop-1; i >= 0; i--)
  {
    cout << v[i] << endl;
  }
  return;
}
