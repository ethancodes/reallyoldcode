#include "vector.h"
#include "stringh.h"
#include <iostream.h>

class hashLink
{
  protected:
    hashLink * next;
    string value;
  public:
    hashLink(string s, hashLink * l) { value = s; next = l; } //constructor
    void linkto(hashLink * l) { next = l; }
    hashLink * link() { return next; }
    string getValue() { return value; }
};

class hashTable
{
  protected:
    vector<hashLink *> v;
    int hashNum;
    int slotsUsed;
    int cap;
    void init(int num, vector<hashLink *> &vhl);//initialize all the hashLink *s
    void expand();						//doubles size of hash table
    void contract();						//halves size of hash table
  public:
    hashTable() { slotsUsed = hashNum = 0; init(10, v); }
    int hash(string s, int size);	 //returns index of hashed value
    void insert(string s);				//insert s into hash table
    void remove(string s);				//remove s from hash table
    void disp();
    int capacity() { return v.size(); }
};

int hashTable::hash(string s, int size)
{
  char ch;
  int h = 0, i;
  for (i = 0; i < 3; i++)
  {
    ch = s[i];
    h = h + ch;
  }
  h = (h % size) - 1;
  return h;
}

void hashTable::insert(string s)
{
  hashLink * newlink = new hashLink(s, NULL);
  int hashvalue = hash(s, v.size());
  hashLink * hl;
  hl = v[hashvalue]->link();
  if (hl == NULL)
  { //nothing there yet
    v[hashvalue]->linkto(newlink);
    slotsUsed++;
  }
  else
  { //something there... chain
    hl->linkto(newlink);
  }
  hashNum++;
  if (hashNum == v.size()) { expand(); }
}

void hashTable::remove(string s)
{
  int hashVal = hash(s, v.size());
  hashLink * hl1;
  hashLink * hl2;
  hl1->linkto(v[hashVal]);
  hl2->linkto(hl1->link());
  while ((hl2->getValue()) != s)
  {
    hl1 = hl2;
    hl2->linkto(hl1->link());
  }
  hl1->linkto(hl2->link());
  hashNum--;
  if ((v[hashVal]->link()) == NULL) { slotsUsed--; }
  if (hashNum < (v.size()/4)) { contract(); }
  return;
}

void hashTable::expand()
{
  vector<hashLink *> v2;
  init(v.size() * 2, v2);
  //rehash
  int i, size = v.size();
  hashLink * hl;
  for (i = 0; i < size; i++)
  {
    hl = v[i]->link();
    if (hl != NULL)
    {
      int hv = hash(hl->getValue(), v2.size());
      v2[hv]->linkto(hl);
    }
  }
  for (i = 0; i < v.size(); i++) { v.pop_back(); }
  v.resize(v2.size());
  for (i = 0; i < v.size(); i++) { v[i] = v2[i]; }
  return;
}

void hashTable::contract()
{
  vector<hashLink *> v2;
  init(v.size() / 4, v2);
  //rehash
  int i, size = v.size();
  hashLink * hl;
  for (i = 0; i < size; i++)
  {
    hl = v[i]->link();
    if (hl != NULL)
    {
      int hv = hash(hl->getValue(), v2.size());
      v2[hv]->linkto(hl);
    }
  }
  for (i = 0; i < v.size(); i++) { v.pop_back(); }
  v.resize(v2.size());
  for (i = 0; i < v.size(); i++) { v[i] = v2[i]; }
  return;
}

void hashTable::init(int num, vector<hashLink *> &vhl)
{
  int i;
  string s = " ";
  for (i = 1; i <= num; i++)
  {
    hashLink * hl = new hashLink(s, NULL);
    vhl.push_back(hl);
  }
}

void hashTable::disp()
{
  cout << "# of hashed elements: " << hashNum
       << " | Size of hash table: " << v.size()
       << " | Slots Used: " << slotsUsed << endl;
}
