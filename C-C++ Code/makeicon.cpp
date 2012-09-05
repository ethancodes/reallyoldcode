//MAKEICON is a little program that serves two purposes.
//One. It takes a file as an argument and turns it into an icon file. The expected
//  file must be a 64x64 bitmap.
//Two. I'm bored. Coding this will be entertaining... I hope.

#include <iostream.h>
#include <string.h>
#include <stdio.h>
#include <io.h>

void Help()
{
  cout << "How to use MAKEICON:\n"
       << "  The prospective icon must be in bitmap format, approximately 64 by 64 pixels.\n"
       << "  Type 'MAKEICON bitmapfilename', without quotes, and press ENTER.\n"
       << "    For example: MAKEICON MyComputer.bmp or MAKEICON Recycle.bmp\n"
       << "  If bitmapfilename has any spaces in it, MAKEICON can't handle it. You will\n"
       << "    have to use the DOS name for the file.\n";
  return;
}

int main(int argc, char *argv[])
{
  char *iconfile = " ", *bitmap;
  char buf[1];
  int x = 0;
  FILE *bmp, *ico;

  if (argc == 1)
  {
    Help();
    return 0;
  }

  bitmap = argv[1];

  //first find out if .BMP was put on the end of the input string
  while ((!(bitmap[x] == '.')) && (x <= strlen(bitmap)))
  {
    x++;
  }

  if (x == (strlen(bitmap) + 1))
  {
    strcpy(iconfile, bitmap);
    strcat(iconfile, ".ICO");

    strcat(bitmap, ".BMP");
  }
  else
  {
    int minus4 = strlen(bitmap) - 4;
    strcpy(iconfile, bitmap);
    iconfile[minus4] = '\0';
    strcat(iconfile, ".ICO");
  }

  //now open the bitmap file and write it to the icon file
  if ((bmp = fopen(bitmap, "rb")) == NULL)
  {
    cout << "ERROR! " << bitmap << " does not exist!\n";
    return 0;
  }
  else
  {
    ico = fopen(iconfile, "wb");
    while (!feof(bmp))
    {
      fputc(fgetc(bmp), ico);
    }
  }

  fclose(bmp);
  fclose(ico);

  cout << iconfile << " created.\n";
  return 0;
}