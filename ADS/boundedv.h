//BOUNDEDV.H
#include "Vector.h"

//declaration

template <class T>
class boundedV : public vector<T>
{
  protected:
    int offset;

  public:
    boundedV(int lowBound, int highBound) : 
      vector<T>((highBound - lowBound) + 1), offset(lowBound) {};
    void remove(int index);
    T & operator [] (int index);
};

//implementation

template <class T> T & boundedV<T>::operator [] (int index)
{
  return buffer[index - offset];
}

template <class T> void boundedV<T>::remove(int index)
{
  int i;
  if (index < mySize)
  {
    i = index;
    while (i < mySize)
    {
      buffer[index++] = buffer[i++];
    }
    mySize--;
  }
}