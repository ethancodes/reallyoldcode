//seive of what's-his-name
//find the primes from 2 to 1000
#include "bitvector.h"
#include <iomanip.h>

int main()
{
  bitVector myBits(999);
  int i, j;

  //set all the bits to TRUE
  for (i = 2; i <= 1000; i++) { myBits.pushTrue(); }

  for (i = 2; i <= 1000; i++)
  {
    for (j = i + 1; j <= 1000; j++)
    {
      if ((j % i) == 0) { myBits.setFalse(j-2); }
    } //end for j
  } //end for i

  //now let's print the primes...
  cout << "Prime #s between 2 and 1000...\n";

  for (i = 2; i <= 1000; i++)
  {
    if (myBits[i-2] == 1)
    {
      cout << setw(3) << i << " ";
    }
  }

  return 0;
}