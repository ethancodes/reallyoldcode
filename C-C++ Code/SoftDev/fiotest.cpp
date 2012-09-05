//test class for FileIO

#include "FileIO.h"

int main()
{
  FileIO FIO;

  FIO.writeString("i love Kerry", "FIOTEST.TXT");
  FIO.writeInt(13, "FIOTEST.TXT");
  FIO.writeChar('X', "FIOTEST.TXT");

  return 0;
}