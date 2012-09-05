//file gentest.h
//Created January 29, 1995 by J. TenEyck

#ifndef _GENTEST_H
#define _GENTEST_H
#include "binstree.h"

enum menu_choice {add,del,showoff,showbr,query,quit};

template <class T>
class treetest  {
  private:
	 void insert(binstree<T> & b, T  v);
	 void remove(binstree<T> & b, T  v);
	 int IsFound(binstree<T> & b, T  v);
	 void ShowAll(binstree<T> & b);
    void breadth(binstree<T> & b);
	 void MainMenu(binstree<T> & b, menu_choice menu_item);
	 menu_choice MenuDisplay();
  public:
	 treetest(binstree<T> &);
  };
  #endif
