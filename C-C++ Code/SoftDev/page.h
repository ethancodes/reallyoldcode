//class Page

class Page : public Frame
{
  public:
    Page() {};									//constructor
    void write(Word w, Logical l);     //writes the Word given into the slot
    												//indicated by the Logical address
    Word fetch(Logical l);					//returns a copy of the Word in the
    												//slot indicated by the Logical address
};

void Page::write(Word w, Logical l)
{
  FileIO FIO;
  words[l.getWord()] = w.getValue();
  FIO.writeString("Word at Page address ", "MMSLOG.TXT");
  FIO.writeInt(l.getWord(), "MMSLOG.TXT");
  FIO.writeString(" written to.", "MMSLOG.TXT");
  FIO.newline("MMSLOG.TXT");
}

Word Page::fetch(Logical l)
{
  FileIO FIO;
  Word w;
  w.setValue(words[l.getWord()].getValue());
  FIO.writeString("Returning copy of word at Page address ", "MMSLOG.TXT");
  FIO.writeInt(l.getWord(), "MMSLOG.TXT");
  FIO.newline("MMSLOG.TXT");
  return w;
}
