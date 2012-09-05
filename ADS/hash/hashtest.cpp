#include "hashTable.h"

int main()
{
  hashTable ht;
  string str;

  str = "ABC";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "DEF";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "GHI";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "JKL";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "MNO";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "PQR";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "STU";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "VWX";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "YZ!";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "@#$";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();
  str = "%^&";
  cout << str << " % " << ht.hash(str, ht.capacity());
  ht.insert(str);
  ht.disp();

  str = "ABC";
  ht.remove(str);
  ht.disp();
  str = "DEF";
  ht.remove(str);
  ht.disp();
  str = "GHI";
  ht.remove(str);
  ht.disp();

  return 0;
}

