//Memory Management System!

#include "FileIO.h"

#include "word.h"
#include "Logical.h"
#include "Frame.h"
#include "SegmentT.h"
#include "PageT.h"
#include "Page.h"

#include "cache.h"
#include "tlb.h"

class MMS
{
  protected:
    Cache  C;			//cache
    TLB    Tlb;		//Translation look-aside buffer
    Frame *F[1024];

  public:
    MMS()
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
    }; //end of memory initialization / constructor

    void Store(Word w, Logical l);
    Word Load(Logical l);
    Word fetch(Logical l);		//go get me this word, dammit!
    Logical fetchPage(Logical l); //return reference to a page
    void write(Word w, Logical l); //my tablets! 'tis meet that i put it down!
};

Word MMS::Load(Logical l)
{
  FileIO FIO;
  Word w;
  //first look in the cache
  FIO.writeString("MMS searching for logical address...", "MMSLOG.TXT");
  FIO.newline("MMSLOG.TXT");
  if (C.isitthere(l))
  {
    FIO.writeString("Found in Cache!", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    w = C.fetch(l);
  }
  //then try the TLB
  else if (Tlb.isitthere(l))
  {
    FIO.writeString("Found in TLB!", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    cout << "about to fetch from TLB...\n";
    Word pg = Tlb.translate(l);
    cout << "done TLB.fetch!\n";
    w = F[pg.getValue()]->fetch(l);
    cout << "done lookup.\n";
  }
  //then, if all else fails, go to main memory.
  else
  {
    FIO.writeString("MMS had to go to main memory for it.", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    w = fetch(l);

    //need to update the Cache with a cache line...
    FIO.writeString("MMS updating Cache.", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    C.write(l, w);

    //...and the TLB with a page...
    FIO.writeString("MMS updating TLB.", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    Tlb.write(l);
  }
  return w;
} //end of Word.Load(Logical)

void MMS::Store(Word w, Logical l)
{
  FileIO FIO;
  //cache
  if (C.isitthere(l))
  {
    FIO.writeString("MMS writing to Cache.", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    C.write(l, w);
  }
  //TLB
  if (Tlb.isitthere(l))
  {
    FIO.writeString("MMS writing to TLB.", "MMSLOG.TXT");
    FIO.newline("MMSLOG.TXT");
    Tlb.write(l);
  }
  //cache has to write to main memory.
}

Word MMS::fetch(Logical l)
{
  Word a; //address variable
  a = F[0]->fetch(l);			//lookup the PageTable on the SegmentTable
  a = F[a.getValue()]->fetch(l);		//lookup the Page on the PageTable
  return F[a.getValue()]->fetch(l); 	//return the Word on the Page
}

Logical MMS::fetchPage(Logical l)
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

void MMS::write(Word w, Logical l)
{
  Word a; //address variable
  a = F[0]->fetch(l);			//lookup the PageTable on the SegmentTable
  a = F[a.getValue()]->fetch(l);		//lookup the Page on the PageTable
  F[a.getValue()]->write(w, l);				//write the Word on the Page
}
