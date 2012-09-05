#include "minheap.h"

int main()
{
  minHeap<int> h;
  vector<int> v;
  int i;

  for (i = 1; i <= 7; i++)
  {
    h.add(i * 2);
    h.disp();
    cout << "Top item is " << h.top() << endl;
  }

  for (i = 1; i <= 7; i++)
  {
    cout << "Deleted " << h.deletemin() << endl;
    h.disp();
  }

  cout << endl;

  v.push_back(5);
  v.push_back(6);
  v.push_back(4);
  v.push_back(7);
  v.push_back(3);
  v.push_back(8);
  v.push_back(2);
  v.push_back(9);
  v.push_back(1);
  v.push_back(0);

  cout << "Vector before heapification...\n";

  for (i = 0; i <= 9; i++)
  {
    cout << v[i] << " ";
  }

  h.heapify(v);

  cout << "\nVector after heapification...\n";

  for (i = 0; i <= 9; i++)
  {
    cout << v[i] << " ";
  }

  return 0;
}
