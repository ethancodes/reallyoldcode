#include <stdlib.h>                    //used for random fuction

class Cache
//one page of most frequently used words
{
 protected:
	int clsize;							//size of cacheline
   Word words[64];

 public:
	Cache();
	int isitthere(Logical l);  //returns a 1 if address is found in cache
										//0 otherwise
	void write(Logical l, Word w); //writes to location given
	Word fetch(Logical l);     //gets a logical address from the MMS
 };

 Cache::Cache()
 {
	clsize=64;                         // size is 4 cachlines of size 16
 }

 int Cache::isitthere(Logical l)
 {  int boolean = 0;
	for (int x=0; x<=(clsize-1); x++){
		if (words[x].getValue() == l.getWord() )
			boolean = 1;
	}
	return boolean;
 }

 void Cache::write(Logical l, Word w)
 { int index= (random(clsize))-1;
	words[index] = l.getWord();
 }

 Word Cache::fetch(Logical l)
 {	int ref= l.getWord();
	return words[ref];
 }


