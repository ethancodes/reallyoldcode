//file randgen.cpp
#include <math.h>
#include <iostream.h>
#include "randgen.h"

  randnum :: randnum (unsigned int mySeed) : seed (mySeed) {srand(mySeed); }

  double randnum :: uniform (double low, double high)
  {
	 return ((rand ()/ double(RAND_MAX)) * (high - low) + low);
  }

  double randnum :: expon (double lambda)
  {
	 const eps = 1.00E-6;
	 double rnum = (rand () / double(RAND_MAX));
	 if (rnum != 0.0)
		return (-1.0) * log (rnum)/lambda;
	 else
		return (-1.0) * log (eps) / lambda;
  }
/*  test program
  void main ()
  {
	 randnum rsource(21);
	 for (int i = 0; i < 10; i++)
		cout << rsource.expon (1) << '\n';
	 for (i = 0; i < 10; i++)
		cout <<  rsource.uniform(10.0, 25.0) <<  '\n';  */
  }


