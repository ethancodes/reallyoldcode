//file stringh
//
// This file contains both header and implementation data and concludes
// with a small test program.  To use this in your work you should split
// it into a proper header and implementation part and delete the test
// program (after satisfying yourself of the general correctnes of the
// class.  I have tested it briefly, and think it is OK, but have not
// verified sufficiently that it is in fact error free.
//

#include <string.h>
#include <assert.h>
#include <iostream.h>

class string {
  public:
    typedef char * iterator;  //define iterator type
	 //enum bool {false, true};
       //constructors
    string (); //default constructor
    string (char *);
	 string (string &);  //copy constructor
	 ~string ();  //destructor

		 //member functions
	 iterator begin ()  {return buffer; }
	 int empty ()      {return buffer[0] == '\0'; }
	 int length ();
	 iterator end ()    {return buffer + length (); }
	 int find (string &, int);
	 int find_first_of (string &, unsigned int);
	 int find_first_not_of (string &, unsigned int);
	 void insert (unsigned int, string &);
	 string substring (unsigned int, unsigned int);
	 void remove (unsigned int, unsigned int);
	 void replace (unsigned int, unsigned int, string &);
	 void resize (unsigned int, char);

		 //operators
	 char & operator [] (unsigned int);
	 void operator = (string &);
	 void operator += (string &);

		 //friends
	 friend int operator == (string &, string &);
	 friend int operator != (string &, string &);
	 friend int operator < (string &, string &);
	 friend int operator <= (string &, string &);
	 friend int operator > (string &, string &);
	 friend int operator >= (string &, string &);
	 friend ostream & operator << (ostream &, string &);
	 friend istream & operator >> (istream &, string &);

  protected:
	 char * buffer;
	 unsigned short int bufferLength;
};

  string::string ()
  {
	  buffer = 0;
	  resize(0, ' ');
  }

  string::string (char * str)
  {
	  buffer = 0;
	  resize (strlen(str), ' ');
	  strcpy (buffer, str);
  }

  string::string (string & str)
  {
	  buffer = 0;
	  int n = str.length();
	  resize (n, ' ');
	  strcpy (buffer, str.buffer);
  }

  string::~string ()
  {
    delete [] buffer;
  }

  void string::resize (unsigned int newLength, char pad)
  {
	  if (!buffer)
		  bufferLength = 0;
     //case 1 getting smaller
	  if (newLength < bufferLength)  {
		  buffer[newLength] = '\0';
        }
     else {  //case 2.  getting larger
        int i;
		  char * newBuffer = new char[newLength + 1];
		  assert(newBuffer != 0);
		  for ( i = 0; i < bufferLength; i++) {
           newBuffer[i] = buffer[i];
           }
		  for (i = bufferLength; i < newLength; i++)
			  newBuffer[i] = pad;
		  newBuffer[i] = '\0';
		  delete [] buffer;
		  buffer = newBuffer;
		  bufferLength = newLength;
     }
  }

  int string::length ()
  {
	  for (int i = 0; i < bufferLength; i++)
		  if (buffer[i] == '\0')
           return i;
	  return bufferLength;
  }

  int string::find (string & target, int start)
  {
     int targetLength = target.length();
     int stop = length () - targetLength;
	  for (int i = start; i <= stop; i++)
		  if (substring(i,targetLength) == target)
           return i;
     return bufferLength;  //no match found -- return out of bounds index
  }

  int string::find_first_of (string & target, unsigned int start)
  {
     int targetLength = target.length();
     int stop = length () - targetLength;
     for (int i = start; i <= stop; i++)
        if (buffer[i] == target[i])
           return i;
     return bufferLength;  //no match found -- return out of bounds index
  }

  int string::find_first_not_of (string & target, unsigned int start)
  {
     int targetLength = target.length();
     int stop = length () - targetLength;
     for (int i = start; i <= stop; i++)
        if (buffer[i] != target[i])
           return i;
     return bufferLength;  //no match found -- return out of bounds index

  }

  void string::insert (unsigned int pos, string & newText)
  {
     int len = length();
     int ntLen = newText.length();
     int newLength = len + ntLen;
     resize(newLength,'\0');
     for (int i = len; i > pos; i--)
        //move existing characters over by length of newText
        buffer[i + ntLen] = buffer[i];
	  //insert new characters
	  for (i = 0; i < ntLen; i++)
		  buffer[pos + i] = newText[i];
  }

  string string::substring (unsigned int startpos, unsigned int len)
  {
     assert(startpos + len <= length ());
     string sub;
	  sub.resize(len, ' ');
     for (int i = 0; i < len; i++)
        sub[i] = buffer[startpos + i];
     return sub;
  }

  void string::remove (unsigned int startpos, unsigned int len)
  {
     assert(startpos + len <= length ());
     int stop = startpos + len;
     while ((stop < bufferLength) & (buffer[stop] != '\0'))
        buffer[startpos++] = buffer[stop++];
     buffer[startpos] = '\0';
	}

  void string::replace (unsigned int start, unsigned int len, string & newText)
  {
     remove(start, len);
     insert(start, newText);
  }

  char & string::operator [] (unsigned int pos)
  {
	  assert((pos >= 0) && (pos <= bufferLength));
     return buffer[pos];
  }

  void string::operator = (string & str)
  {
	  resize (str.length(), ' ');
     strcpy(buffer, str.buffer);
  }

  void string::operator += (string & right)
  {
     insert(length(), right);
  }

//Other operators and functions

  string operator + (string & left, string & right)
  {
     string clone(left);
     clone += right;
     return clone;
  }

  int operator == (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) == 0;
  }

  int operator != (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) == 0;
  }

  int operator < (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) < 0;
  }

  int operator <= (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) <= 0;
  }

  int operator > (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) > 0;
  }

  int operator >= (string & left, string & right)
  {
	  return strcmp (left.buffer, right.buffer) >= 0;
  }

  ostream & operator << (ostream & out, string & s)
  {
	  return out << s.buffer;
  }

  istream & operator >> (istream & in, string & str)
  {
	  char inbuffer[1000];
	  in >> inbuffer;
	  str = inbuffer;
	  return in;
  }

void main ()
{
  string s;
  string st("hello");
  string st2 (st);
  cout << st.length () << '\n';
  cout << st2 << '\n';
  st += st2;
  cout << st << '\n';
  st[0] = 'Y';
  s = "w";
  st.insert(5,s);
  cout << st << '\n';
  st.replace(5,s.length(),s);
  cout << st << '\n';
}