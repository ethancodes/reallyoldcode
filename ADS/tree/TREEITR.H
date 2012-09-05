//file treeitrs.h

#ifndef _TREE_ITERATOR_H
#define _TREE_ITERATOR_H
#include "iterator.h"
#include "eStack.h"
#include "binstree.h"
#include "gentree.h"

  template <class T>
  class postorderTreeTraversal : public iterator <T> {
	 public:
		// constructor
		postorderTreeTraversal	(bstnode <T> * );

		// reimplement iterator protocol
		virtual int 	init				();
		virtual int		operator 	!	();
		virtual	T		operator		()	();
		virtual void	operator 	=	(T val);
		virtual int		operator 	++	();

		// permit changing the root;
		void				setRoot			(bstnode<T> * newroot) {root = newroot; }
	 private:
		// data areas
		bstnode<T>	*	root;
		eStack<bstnode<T> *>	itrstack;

		// internal method for stack the children of a node
		void 	stackChildren	(bstnode<T> *);
  };

  template <class T>
  class inorderTreeTraversal : public iterator <T> {
	 public:
		// constructor
		inorderTreeTraversal	(bstnode <T> * );

		// reimplement iterator protocol
		virtual int 	init				();
		virtual int		operator 	!	();
		virtual	T &	operator		()	();
		virtual void	operator 	=	(T val);
		virtual int		operator 	++	();

		// permit changing the root;
//		void				setRoot			(bstnode<T> * newroot) {root = newroot; }

	 private:
		// data areas
		bstnode<T>	*	root;
		eStack<bstnode<T> *>	itrstack;

		// internal method for stack the children of a node
		void 	slideLeft	(bstnode<T> *);
  };

  template <class T>
  postorderTreeTraversal<T>::postorderTreeTraversal(bstnode<T> * n)
  {
		root = n;
  }
  template <class T>
  int postorderTreeTraversal<T>::init				()
  {
		itrstack.deleteAllValues (); // clear the stacks!
		if (root != 0)
			stackChildren(root);
		return ! itrstack.empty ();
  }

  template <class T>
  void postorderTreeTraversal<T>::stackChildren(bstnode<T> * n)
  { // stack all the children of the present node
	  itrstrack.push (n);
	  bstnode<T> * next = n -> right ();
	  if (next)
		  stackChildren (next);
	  next = n -> left ();
	  if (next)
		  stackChildren (next);
  }

  template <class T>
  int postorderTreeTraversal<T>::operator ! ()
  {  // we are done when the stack is empty
	  return ! itrstack.empty ();
  }

  template <class T>
  T postorderTreeTraversal<T>::operator () ()
  {
	  bstnode<T> * n = itrstack.top();
	  return n -> data;
  }

  template <class T>
  int postorderTreeTraversal<T>::operator ++ ()
  {  // move to the next item in the sequence an pop
	  // the current item from the itrstack
	  itrstack.pop ();
	  return ! itrstack.empty ();
  }

  template <class T>
  void postorderTreeTraversal<T>::operator = (T val)
  {
	  bstnode<T> * current = itrstack.top ();
	  current -> data = val;
  }

  template <class T>
  inorderTreeTraversal<T>::inorderTreeTraversal(bstnode<T> * mynode)
  {
		root = mynode;
  }
  template <class T>
  int inorderTreeTraversal<T>::init				()
  {
		itrstack.deleteAllValues (); // clear the stacks!
		slideLeft(root);
		return ! itrstack.empty ();
  }

  template <class T>
  void inorderTreeTraversal<T>::slideLeft(bstnode<T> * current)
  { // slide left from the present node
	  while (current) {
		  itrstack.push (current);
		  current = current -> left ();
		  }
  }

  template <class T>
  int inorderTreeTraversal<T>::operator ! ()
  {  // we are done when the stack is empty
	  return ! itrstack.empty ();
  }

  template <class T>
  T & inorderTreeTraversal<T>::operator () ()
  {
	  bstnode<T> * n = itrstack.top();
	  return n -> data;
  }

  template <class T>
  int inorderTreeTraversal<T>::operator ++ ()
  {  // move to the next item in the sequence an pop
	  // the current item from the itrstack
	  if (!itrstack.empty() ) {
		  bstnode<T> * n = itrstack.top ();
		  itrstack.pop ();
		  bstnode<T> * next = n -> rightptr;
		  if (next) { slideLeft (next); }
		  }
	  return ! itrstack.empty ();
  }

  template <class T>
  void inorderTreeTraversal<T>::operator = (T val)
  {
	  bstnode<T> * current = itrstack.top ();
	  current -> data = val;
  }

#endif
