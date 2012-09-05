//bitvector.h
#include "vector.h"

//declaration

class bitVector
{
  protected:
    vector<int> v;
  public:
    bitVector(int numberOfElements) { v.reserve(numberOfElements); };
    void pushTrue();		//pushes a true value
    void pushFalse();		//pushes a false value
    void setTrue(int index);	//sets vector[index] to true
    void setFalse(int index);	//sets vector[index] to false
    void flip(int index);	//negates vector[index]

    int & operator [] (int index) const;
};

//implementation

void bitVector::pushTrue()
{
  v.push_back(1);
  return;
}

void bitVector::pushFalse()
{
  v.push_back(0);
  return;
}

void bitVector::setTrue(int index)
{
  if (index < v.size())
  {
    v[index] = 1;
  }
  return;
}

void bitVector::setFalse(int index)
{
  if (index < v.size())
  {
    v[index] = 0;
  }
  return;
}

void bitVector::flip(int index)
{
  if (index < v.size())
  {
    v[index] = !(v[index]);
  }
  return;
}

int & bitVector::operator [] (int index) const
{
  return v[index];
}
