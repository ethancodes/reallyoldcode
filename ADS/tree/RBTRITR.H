//file rbtritrs.h

#ifndef _RED_BLACK_TREE_ITERATOR_H
#define _RED_BLACK_TREE_ITERATOR_H
#include "iterator.h"
#include "stack.h"
#include "rbtree.h"
#include "gentree.h"

  template <class T>
  class postorderRBTreeTraversal : public iterator <T> {
	 public:
		// constructor
		postorderRBTreeTraversal	(rbnode <T> * );

		// reimplement iterator protocol
		virtual int 	init				();
		virtual int		operator 	!	();
		virtual	T		operator		()	();
		virtual void	operator 	=	(T val);
		virtual int		operator 	++	();

		// permit changing the root;
		void				setRoot			(rbnode<T> * newroot) {root = newroot; }
	 private:
		// data areas
		rbnode<T>	*	root;
		stack<rbnode<T> *>	itrstack;

		// internal method for stack the children of a node
		void 	stackChildren	(rbnode<T> *);
  };

  template <class T>
  class inorderRBTreeTraversal : public iterator <T> {
	 public:
		// constructor
		inorderRBTreeTraversal	(rbnode <T> * );

		// reimplement iterator protocol
		virtual int 	init				();
		virtual int		operator 	!	();
		virtual	T &	operator		()	();
		virtual void	operator 	=	(T val);
		virtual int		operator 	++	();

		// permit changing the root;
//		void				setRoot			(rbnode<T> * newroot) {root = newroot; }

	 private:
		// data areas
		rbnode<T>	*	root;
		stack<rbnode<T> *>	itrstack;

		// internal method for stack the children of a node
		void 	slideLeft	(rbnode<T> *);
  };

  template <class T>
  postorderRBTreeTraversal<T>::postorderRBTreeTraversal(rbnode<T> * n)
  {
		root = n;
  }
  template <class T>
  int postorderRBTreeTraversal<T>::init				()
  {
		itrstack.deleteAllValues (); // clear the stacks!
		if (root != 0)
			stackChildren(root);
		return ! itrstack.empty ();
  }

  template <class T>
  void postorderRBTreeTraversal<T>::stackChildren(rbnode<T> * n)
  { // stack all the children of the present node
	  itrstrack.push (n);
	  rbnode<T> * next = n -> right ();
	  if (next)
		  stackChildren (next);
	  next = n -> left ();
	  if (next)
		  stackChildren (next);
  }

  template <class T>
  int postorderRBTreeTraversal<T>::operator ! ()
  {  // we are done when the stack is empty
	  return ! itrstack.empty ();
  }

  template <class T>
  T postorderRBTreeTraversal<T>::operator () ()
  {
	  rbnode<T> * n = itrstack.top();
	  return n -> data;
  }

  template <class T>
  int postorderRBTreeTraversal<T>::operator ++ ()
  {  // move to the next item in the sequence an pop
	  // the current item from the itrstack
	  itrstack.pop ();
	  return ! itrstack.empty ();
  }

  template <class T>
  void postorderRBTreeTraversal<T>::operator = (T val)
  {
	  rbnode<T> * current = itrstack.top ();
	  current -> data = val;
  }

  template <class T>
  inorderRBTreeTraversal<T>::inorderRBTreeTraversal(rbnode<T> * mynode)
  {
		root = mynode;
  }
  template <class T>
  int inorderRBTreeTraversal<T>::init				()
  {
		itrstack.deleteAllValues (); // clear the stacks!
		slideLeft(root);
		return ! itrstack.empty ();
  }

  template <class T>
  void inorderRBTreeTraversal<T>::slideLeft(rbnode<T> * current)
  { // slide left from the present node
	  while (!current -> isLeaf ()) {
		  itrstack.push (current);
		  current = current -> left ();
		  }
  }

  template <class T>
  int inorderRBTreeTraversal<T>::operator ! ()
  {  // we are done when the stack is empty
	  return ! itrstack.empty ();
  }

  template <class T>
  T & inorderRBTreeTraversal<T>::operator () ()
  {
	  rbnode<T> * n = itrstack.top();
	  return n -> data;
  }

  template <class T>
  int inorderRBTreeTraversal<T>::operator ++ ()
  {  // move to the next item in the sequence an pop
	  // the current item from the itrstack
	  if (!itrstack.empty() ) {
		  rbnode<T> * n = itrstack.top ();
		  itrstack.pop ();
		  rbnode<T> * next = n -> rightptr;
		  if (!next -> isLeaf ())
			  slideLeft (next);
		  }
	  return ! itrstack.empty ();
  }

  template <class T>
  void inorderRBTreeTraversal<T>::operator = (T val)
  {
	  rbnode<T> * current = itrstack.top ();
	  current -> data = val;
  }

#endif