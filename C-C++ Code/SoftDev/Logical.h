//class Logical

class Logical : public Word
{
  //Logical address structure
  //-------------------------
  //the Logical address is stored in an int, so it can go to 32767, storing
  //32768 addresses. 32768 is 2 to the 15th. the Logical address is therefore
  //divided up into three equal parts, as follows:
  // Bit: 15 14 13 12 11 10 09 08 07 06 05 04 03 02 01
  //      |____________| |____________| |____________|
  //        PageTable        Page           Word
  //          within        within           within
  //        SegmentTable     PageTable      Page

  public:
    Logical() { value = 0; }; 	  //end of default Logical constructor
    Logical(int a) { value = a; }; //a is the address you want to set Logical to

    int getPageTable();				  	//returns the portion of the whole address
    											//which references the PageTable within
                                    //SegmentTable
    int getPage();                  //returns the portion of the whole address
    											//which references the Page within a
                                    //PageTable
    int getWord();						//returns the portion of the whole address
    											//which referneces the Word within a Page
}; //end of class declaration

int Logical::getPageTable()
{
  int v = value, i, j, k, pt = 0;
  for (i = 0, j = 16384, k = 16; i < 5; i++, j = (j/2), k = (k/2))
  {
    if (v >= j)
    {
      v = v - j;
      pt = pt + k;
    }
  }
  return pt;
}

int Logical::getPage()
{
  int v = value, i, j, k, p = 0;
  for (i = 0, j = 16384, k = 512; i < 10; i++, j = (j/2), k = (k/2))
  {
    if (v >= j)
    {
      v = v - j;
      if (i >= 5) { p = p + k; }
    }
  }
  return p;
}

int Logical::getWord()
{
  int v = value, i, j, k, w = 0;
  for (i = 0, j = 16384, k = 16384; i < 15; i++, j = (j/2), k = (k/2))
  {
    if (v >= j)
    {
      v = v - j;
      if (i >= 10) { w = w + k; }
    }
  }
  return w;
}
