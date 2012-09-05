//class FileIO
//makes writing to files easier
//
//                  *** ALWAYS APPENDS TO THE FILE ***
//
//this means it won't overwrite the file...!

#include <stdio.h>

class FileIO
{
  public:
    FileIO() {};
    void writeString(char *s, char *outfile);		//write a string
    void writeInt(int i, char *outfile);				//write an integer
    void writeChar(char c, char *outfile);			//write a character
    void newline(char *outfile);							//a \n
};

void FileIO::writeString(char *s, char *outfile)
{
  //should look something like this:
  //FileIO.writeString("Holy Page Faults, Batman!", "COREDUMP.TXT");

  FILE *outf;

  if ((outf = fopen(outfile, "a+t")) != NULL)
  {
    fputs(s, outf);
  }
  fclose(outf);		//don't forget to close the file
}

void FileIO::writeInt(int i, char *outfile)
{
  //should look something like this:
  //FileIO.writeInt(13, "COREDUMP.TXT");

  FILE *outf;

  if ((outf = fopen(outfile, "a+t")) != NULL)
  {
    fprintf(outf, "%d", i);
  }
  fclose(outf);
}

void FileIO::writeChar(char c, char *outfile)
{
  //should look something like this:
  //FileIO.writeChar('V', "COREDUMP.TXT");

  FILE *outf;

  if ((outf = fopen(outfile, "a+t")) != NULL)
  {
    fputc(c, outf);
  }
  fclose(outf);
}

void FileIO::newline(char *outfile)
{
  //FileIO.newline("COREDUMP.TXT")

  FILE *outf;

  if ((outf = fopen(outfile, "a+t")) != NULL)
  {
    fputs("\n", outf);
  }
  fclose(outf);
}
