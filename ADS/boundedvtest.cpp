//test boundedv.h
#include "boundedv.h"
#include <iostream.h>

int main()
{
  int low = 1, high = 69;

  boundedV<int> bv(low, high);
  int i, x = 1; //counters

  for (i = low; i <= high; i++)
  {
    bv[i] = i * i;
  }

  cout << "Size: " << bv.size()
       << " Capacity: " << bv.capacity()
       << " Front: " << bv.front()
       << " Back: " << bv.back()
       << endl;

  for (i = low, x = 1; i <= high; i++, x++)
  {
    if (x > 8) { x = 1; cout << endl; }
    cout << bv[i] << " ";
  }
  cout << endl;

  bv.remove(5);

  cout << "Size: " << bv.size()
       << " Capacity: " << bv.capacity()
       << " Front: " << bv.front()
       << " Back: " << bv.back()
       << endl;

  for (i = low, x = 1; i <= high; i++, x++)
  {
    if (x > 8) { x = 1; cout << endl; }
    cout << bv[i] << " ";
  }
  cout << endl;

  return 0;
}