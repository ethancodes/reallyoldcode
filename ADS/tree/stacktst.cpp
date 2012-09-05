#include "estack.h"

int main()
{
  eStack<int> s;
  int i;

  for (i = 1; i <= 10; i++)
  {
    s.push(i);
  }

  s.disp();
  cout << s.size() << endl;
  if (s.empty()) { cout << "Empty stack!\n"; }

  for (i = 10; i >= 1; i--)
  {
    cout << s.top();
    s.pop();
  }

  if (s.empty()) { cout << "Empty stack!\n"; }
  return 0;
}
