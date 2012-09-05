//test program

#include <iostream.h>
#include "logical.h"
#include "page.h"

int main()
{
  Page p;
  Word w;
  Logical l;
  Logical l2(42);

  w.setValue(911);
  l.setValue(41);
  p.write(w, l);
  w.setValue(912);
  p.write(w, l2);

  p.disp();

  return 0;
}