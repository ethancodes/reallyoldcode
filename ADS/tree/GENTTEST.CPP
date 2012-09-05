//file genttest.cpp
//Created October 1, 1994  by J. TenEyck
#include <iostream.h>
#include <stdlib.h>
#include <conio.h>
#include <stdio.h>
#include "genttest.h"
#include "binstree.h"
#include "stringh.h"
#include "treeitr.h"
#include "eQueue.h"
#include "function.h"

  template <class T>
  void treetest<T>::insert(binstree<T> & b, T  v)  {
	 b.add(v);
  }

  template <class T>
  void treetest<T>::remove(binstree<T> & b, T  v)  {
	 b.remove(v);
  }

  template <class T>
  int treetest<T>::IsFound(binstree<T> & b, T  v)  {
	 return b.includes(v);
  }

  template <class T>
  void treetest<T>::ShowAll(binstree<T> & b)
  {
	 inorderTreeTraversal<T>  itr(b.begin());
	 itr.init();
	 while (!itr)  {
		 cout << itr () << '\n';
		 itr++;
		 }
  }

  template <class T>
  void treetest<T>::breadth(binstree<T> & b)
  {
    FILE * output;
    eQueue<bstnode<T> *> q;
    bstnode<T> * crlf = new bstnode<T> ("####");
    bstnode<T> * node;

    cout << "Breadth first traversal:\n";
    if ((output = fopen("BREADTH.TXT", "w+t")) == NULL)
    {
      cout << "Cannot open file.\n";
    }

    //put the root in the q
    q.enQueue(b.begin());
    q.enQueue(crlf);

    while (q.size() > 0)
    {
      node = q.front();
      q.deQueue();

      if (node == NULL)
      {
        fwrite("-", sizeof("-"), 1, output);
      }
      else if ((node->data) == (crlf->data))
      {
        fwrite("\n", sizeof("\n"), 1, output);
      }
      else
      {
        string Str = node->data;
        int i;
        for (i = 0; i <= Str.length(); i++)
        {
          fputc(Str[i], output);
        }
        q.enQueue(node->left());
        q.enQueue(node->right());
        q.enQueue(crlf);
      } //end else
    } //end while
    cout << "Output written to BREADTH.TXT\n ";
    fclose(output);
    return;
  } //end void breadth()

  template <class T>
  void treetest<T>::MainMenu(binstree<T> & b, menu_choice menu_item)  {
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
      case showbr:
         breadth(b);
         cout << "\n\n";
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
  menu_choice treetest<T>::MenuDisplay() {
	 char num;
	 cout <<"TREETEST MAIN MENU\n\n\n";
	 cout <<"A. Add a new item to the tree \n\n";
	 cout <<"D. Delete a key from the tree \n\n";
	 cout <<"S. Show an inorder list of all the keys in the tree \n\n";
    cout <<"B. Breadth first dispaly of the tree\n\n";
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
      case 'B', 'b':
         return showbr;
		case 'Q', 'q':
			return query;
		case 'E', 'e':
			return quit;
		default:
			return query;
	 }
  }

  template<class T>
  treetest<T>::treetest(binstree<T> & bst)  {
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
	 binstree<string>  BST;
	 treetest<string> tt(BST);
  }


