#include "function.h"
#include "vector.h"
#include <iostream.h>

template <class T>
class minHeap
{
  protected:
    int heapSize;
    vector<T> v;
    void SiftDown(int index);
    void SiftUp(int index);
    void SiftDownV(int index, vector<T> &v); //for heapify
  public:
    minHeap() { heapSize = 0; } //constructor
    void add(T item);    //add a new element
    T deletemin();       //remove top element
    T top();             //return top element
    void heapify(vector<T> &v); //turn a vector into a heap
    void disp();         //display in array format...
};

template <class T>
T minHeap<T>::top()
{
  return v[0];
}

template <class T>
void minHeap<T>::add(T item)
{
  v.push_back(item);
  heapSize++;
  SiftUp(heapSize);
  return;
}

template <class T>
T minHeap<T>::deletemin()
{
  T top = v[0];
  v[0] = v[heapSize-1];
  heapSize--;
  SiftDown(1);
  return top;
}

template <class T>
void minHeap<T>::SiftUp(int index)
{
  if (index-1 > 0) {
  if (v[index-1] < v[(index/2)-1])
  {
    swap(v[index-1], v[(index/2)-1]);
    index = index / 2;
    SiftUp(index);
  }
  } //end if index > 1
  return;
}

template <class T>
void minHeap<T>::SiftDown(int index)
{
  if (index*2 <= heapSize) {
  if (index*2 >= heapSize)
  { //only one child
    if (v[index-1] > v[(index*2)-1])
    {
      swap(v[index-1], v[(index*2)-1]);
    }
  } //end if one child
  else { //two children
    if (v[index*2] < v[(index*2)-1])
    {
      if (v[index-1] > v[index*2])
      {
        swap(v[index-1], v[index*2]);
        index = (index * 2) + 1;
        SiftDown(index);
      }
    }
    else if (v[index*2] > v[(index*2)-1])
    {
      if (v[index-1] > v[(index*2)-1])
      {
        swap(v[index-1], v[(index*2)-1]);
        index = index * 2;
        SiftDown(index);
      }
    }
  } //end else, two children
  } //end if children exist...
  return;
}

template <class T>
void minHeap<T>::heapify(vector<T> &v)
{
  int i;
  int n = v.size();
  n = n / 2;

  for (i = n; i > 0; i--)
  {
    SiftDownV(i, v);
  }
  return;
}

template <class T>
void minHeap<T>::disp()
{
  int i;
  for (i = 0; i < heapSize; i++)
  {
    cout << v[i] << " ";
  }
  return;
}

template <class T>
void minHeap<T>::SiftDownV(int index, vector<T> &v)
{
  if (index*2 <= v.size()) {
  if (index*2 >= v.size())
  { //only one child
    if (v[index-1] > v[(index*2)-1])
    {
      swap(v[index-1], v[(index*2)-1]);
    }
  } //end if one child
  else { //two children
    if (v[index*2] < v[(index*2)-1])
    {
      if (v[index-1] > v[index*2])
      {
        swap(v[index-1], v[index*2]);
        index = (index * 2) + 1;
        SiftDownV(index, v);
      }
    }
    else if (v[index*2] > v[(index*2)-1])
    {
      if (v[index-1] > v[(index*2)-1])
      {
        swap(v[index-1], v[(index*2)-1]);
        index = index * 2;
        SiftDownV(index, v);
      }
    }
  } //end else, two children
  } //end if children exist...
  return;
}

