//class PageTable

class PageTable : public Frame
{
  public:
    PageTable(int a) //a is the address in memory where the PageTable starts
    {
      int i;
      for (i = (a + 1); i <= (a + 32); i++)
      {
        words[i - a - 1].setValue(i);
      }
    }; //constructor
    Word fetch(Logical l);	//returns the index of the page to lookup
    void write(Word w, Logical l) {}; //to make the compiler happy
};

Word PageTable::fetch(Logical l)
{
  FileIO FIO;
  int index = l.getPage();
  FIO.writeString("PageTable returning reference to Page at address ",
      "MMSLOG.TXT");
  FIO.writeInt(index, "MMSLOG.TXT");
  FIO.newline("MMSLOG.TXT");
  return words[index];
}
