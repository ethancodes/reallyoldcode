#include <iostream.h>
#include "logical.h"

int main()
{
  Logical l(6886);

  cout << "Logical:   " << l.getValue() << endl;
  cout << "PageTable: " << l.getPageTable() << endl;
  cout << "Page:      " << l.getPage() << endl;
  cout << "Word:      " << l.getWord() << endl;

  return 0;
}
