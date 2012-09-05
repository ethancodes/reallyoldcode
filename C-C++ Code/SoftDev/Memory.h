//class Memory

#include "word.h"
#include "Logical.h"
#include "Frame.h"
#include "SegmentT.h"
#include "PageT.h"
#include "Page.h"

//what memory will look like. v2.0
//an array of 1024 Frames, F
//F[0]  . SegmentTable
//F[1]  . PageTable1
//F[2]  . Page1
//F[3]  . Page2
//...
//F[33] . Page32
//F[34] . PageTable2
//F[35] . Page1
//F[36] . Page2
//...

class Memory
{
  protected:
    Frame* F[1024];
  public:
    Memory()
    {
      FileIO FIO;

      F[0] = new SegmentTable;
      FIO.writeString("SegmentTable initialized.", "MMSLOG.TXT");
      FIO.newline("MMSLOG.TXT");

      int i, j, k;
      PageTable * pt;
      for (i = 1, j = 1; i < 32; i++, j = j + 33)
      {
        pt = new PageTable(j);
        FIO.writeString("PageTable initialized at address ", "MMSLOG.TXT");
        FIO.writeInt(j, "MMSLOG.TXT");
        FIO.newline("MMSLOG.TXT");
        F[j] = pt;
        for (k = (j + 1); k < (j + 33); k++)
        {
          F[k] = new Page;
          FIO.writeString("Page initialized at address ", "MMSLOG.TXT");
          FIO.writeInt(k, "MMSLOG.TXT");
          FIO.newline("MMSLOG.TXT");
        }
      } //end of for loop
    } //end of Memory initialization

    Word fetch(Logical l);		//go get me this word, dammit!
    Logical fetchPage(Logical l); //return reference to a page
    void write(Word w, Logical l); //my tablets! 'tis meet that i put it down!
};//end of class declaration

Word Memory::fetch(Logical l)
{
  Word a; //address variable
  a = F[0]->fetch(l);			//lookup the PageTable on the SegmentTable
  a = F[a.getValue()]->fetch(l);		//lookup the Page on the PageTable
  return F[a.getValue()]->fetch(l); 	//return the Word on the Page
}

Logical Memory::fetchPage(Logical l)
{
  //go get the stick, boy! go fetch!
  //pant pant pant pant
  //dumb dog
  Logical l2;
  Word a;
  a = F[0]->fetch(l);
  a = F[a.getValue()]->fetch(l); // a now has address of Page
  l2.setValue(a.getValue());
  return l2;
}

void Memory::write(Word w, Logical l)
{
  Word a; //address variable
  a = F[0]->fetch(l);			//lookup the PageTable on the SegmentTable
  a = F[a.getValue()]->fetch(l);		//lookup the Page on the PageTable
  F[a.getValue()]->write(w, l);				//write the Word on the Page
}
