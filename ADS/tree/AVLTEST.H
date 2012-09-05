//file avltest.h
//Created January 29, 1995 by J. TenEyck

#ifndef _AVLTEST_H
#define _AVLTEST_H
#include "avltree.h"

enum menu_choice {add,del,showoff,query,quit};

template <class T>
class treetest  {
  private:
	 void insert(AVLtree<T> & b, T  v);
	 void remove(AVLtree<T> & b, T  v);
	 int IsFound(AVLtree<T> & b, T  v);
	 void ShowAll(AVLtree<T> & b);
	 void MainMenu(AVLtree<T> & b, menu_choice menu_item);
	 menu_choice MenuDisplay();
  public:
	 treetest(AVLtree<T> &);
  };
  #endif
