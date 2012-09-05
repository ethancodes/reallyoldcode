#include "equeue.h"

int main()
{
  eQueue<int> q;
  int i;

  for (i = 1; i <= 10; i++)
  {
    q.enQueue(i);
  }

  cout << "Size = " << q.size() << "Front = " << q.front() << endl;
  q.disp();

  while (q.size() > 0)
  {
    q.deQueue();
    q.disp();
  }

  return 0;
}