//class SegmentTable

class SegmentTable : public Frame
{
  public:
    SegmentTable()
    {
      int i, j;
      for (i = 1, j = 1; i <= 31; i++, j = j + 33)
      {
        words[i].setValue(j);
      }
    }; //constructor
    Word fetch(Logical l);	//returns the index of the pagetable to lookup
    void write(Word w, Logical l) {};
};

Word SegmentTable::fetch(Logical l)
{
  FileIO FIO;
  int index = l.getPageTable();
  FIO.writeString("SegmentTable returning reference to PageTable at address ",
    "MMSLOG.TXT");
  FIO.writeInt(index, "MMSLOG.TXT");
  FIO.newline("MMSLOG.TXT");
  return words[index];
}
