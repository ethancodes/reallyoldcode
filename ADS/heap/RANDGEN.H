//file randgen.h
#ifndef _RANDGEN_H
#define _RANDGEN_H
#include <stdlib.h>

  class randnum  {
	 public:
		randnum (unsigned int mySeed );
		double uniform (double low, double high);
		double expon (double lambda);   // lambda is the reciprocal of the mean
	 private:
		double seed;
  };

#endif
