//file avltest.cpp
//Created October 1, 1994  by J. TenEyck
#include <iostream.h>
#include <stdlib.h>
#include <conio.h>
#include "avltree.h"
#include "stringh.h"
#include "avlitr.h"
#include "avltest.h"

  template <class T>
  void avltest<T>::insert(AVLtree<T> & b, T  v)  {
	 b.add(v);
  }

  template <class T>
  void avltest<T>::remove(AVLtree<T> & b, T  v)  {
	 b.remove(v);
  }

  template <class T>
  int avltest<T>::IsFound(AVLtree<T> & b, T  v)  {
	 return b.includes(v);
  }

  template <class T>
  void avltest<T>::ShowAll(AVLtree<T> & b)
  {
	 inorderAVLTreeTraversal<T>  itr(b.begin());
	 itr.init();
	 while (!itr)  {
		 cout << itr () << '\n';
		 itr++;
		 }
  }

  template <class T>
  void avltest<T>::MainMenu(AVLtree<T> & b, menu_choice menu_item)  {
	 T v;
	 switch (menu_item)  {
		case add:
			cout <<"\nEnter value of item to be added! : ";
			cin >> v;
			cout <<"\n";
			insert(b,v);
			break;
		case del:
			cout <<"\nEnter key of item to be deleted! : ";
			cin >> v;
			cout <<"\n";
			remove(b,v);
			break;
		case showoff:
			ShowAll(b);
			cout <<"\n\n";
			break;
		case query:
			cout <<"\nEnter key to be searched for! : ";
			cin >> v;
			cout <<"\n";
			if (IsFound(b,v))
			  cout <<"\nThe key "<< v <<" has been found! \n";
			else
			  cout <<"\nThe key "<< v <<" is not in the tree! \n";
			break;
		case quit:
			cout <<"Test ended.  Final traversal of the tree:\n";
			break;
		default:
			cout <<"\nOperation not supported.  Please select again! \n";
	 }
  }

  template <class T>
  menu_choice avltest<T>::MenuDisplay() {
	 char num;
	 cout <<"TREETEST MAIN MENU\n\n\n";
	 cout <<"A. Add a new item to the tree \n\n";
	 cout <<"D. Delete a key from the tree \n\n";
	 cout <<"S. Show an inorder list of all the keys in the tree \n\n";
	 cout <<"Q. Query whether a particular key is in the tree \n\n";
	 cout <<"E. Exit \n\n";
	 cout <<"Give letter of your choice: ";
	 cin >> num;
	 switch (num) {
		case 'A', 'a':
			return add;
		case 'D', 'd':
			return del;
		case 'S', 's':
			return showoff;
		case 'Q', 'q':
			return query;
		case 'E', 'e':
			return quit;
		default:
			return query;
	 }
  }

  template<class T>
  avltest<T>::avltest(AVLtree<T> & bst)  {
	 int flag = 0;
	 do {
		clrscr;
		menu_choice entree=MenuDisplay();
		MainMenu(bst,entree);
		flag = (entree == quit);
	 } while (!flag);
	 ShowAll(bst);
  }

  void main()  {
//	 RedBlackTree<string> RBT;
	 AVLtree<string>  BST;
	 avltest<string> tt(BST);
  }

