//another test class.

#include <iostream.h>
#include "memory.h"

int main()
{
  Memory  M;
  Word    w(179);
  Word    ww2;					//just to be funny...
  Logical l(1313);

  M.write(w, l);
  cout << "Contents of Memory Address 1313: ";
  ww2.setValue(M.fetch(l).getValue());
  cout << ww2.getValue();

  return 0;
}