#include <stdlib.h>              //used with random function

class TLB : public Frame
//Translation Look-aside Buffer
{
 protected:
	int TLBsize;                   //size of TLB in words

 public:
	TLB();
	int isitthere(Logical l);      //is this Logical address in the TLB
	void write(Logical l); 			 //writes this word at the location given
	Word translate(Logical l);		  //returns a Word, which is a reference to
											 //a Page
   Word fetch(Logical l) {};
   void write(Word w, Logical l) {};  //to make the compiler smile! :-)
};

TLB::TLB()
{
	TLBsize=32;
}

int TLB::isitthere(Logical l)
{  int boolean = 0;
	for (int x=0; x<=(TLBsize-1); x++){
		if (words[x].getValue() == l.getWord() )
			boolean = 1;
	}
	return boolean;
}


void TLB::write(Logical l)
{  int index= (random(TLBsize))-1;
	words[index]= l.getPage();
}

Word TLB::translate(Logical l)
{  cout << "in TLB.fetch\n";
   Word ref(l.getPage());
   cout << "leaving TLB.fetch\n";
	return ref;
}