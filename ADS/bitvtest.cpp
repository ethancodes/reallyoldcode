//test bitvector.h
#include "bitvector.h"

int main()
{
  bitVector myBits(8);
  int i;	//counters

  for (i = 0; i <= 13; i++)
  {
    if ((i % 2) == 0) { myBits.pushTrue(); }
    else { myBits.pushFalse(); }
  }

  cout << "Bit 0 1 2 3 4 5 6 7 8 9 A B C D\n    ";
  for (i = 0; i <= 13; i++)
  {
    cout << myBits[i] << " ";
  }
  cout << endl << endl;

  for (i = 0; i <= 13; i++)
  {
    myBits.flip(i);
  }

  cout << "Bit 0 1 2 3 4 5 6 7 8 9 A B C D\n    ";
  for (i = 0; i <= 13; i++)
  {
    cout << myBits[i] << " ";
  }
  cout << endl << endl;

  for (i = 0; i <= 13; i++)
  {
    if (myBits[i] == 1) { myBits.flip(i); }
  }

  cout << "Bit 0 1 2 3 4 5 6 7 8 9 A B C D\n    ";
  for (i = 0; i <= 13; i++)
  {
    cout << myBits[i] << " ";
  }
  cout << endl << endl;

  return 0;
}