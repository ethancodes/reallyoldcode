//file rbtrtest.h
//Created January 29, 1995 by J. TenEyck

#ifndef _RED_BLACK_TEST_H
#define _RED_BLACK_TEST_H
#include "rbtree.h"

enum menu_choice {add,del,showoff,query,quit};

template <class T>
class RedBlackTest  {
  private:
	 void insert(RedBlackTree<T> & b, T  v);
	 void remove(RedBlackTree<T> & b, T  v);
	 int IsFound(RedBlackTree<T> & b, T  v);
	 void ShowAll(RedBlackTree<T> & b);
	 void MainMenu(RedBlackTree<T> & b, menu_choice menu_item);
	 menu_choice MenuDisplay();
  public:
	 RedBlackTest(RedBlackTree<T> &);
  };
#endif
