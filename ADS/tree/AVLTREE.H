// file AVLtree.h
#ifndef _AVLTREE_H
#define _AVLTREE_H

#include <assert.h>
#include "gentree.h"

template <class T> class inorderAVLTreeTraversal;

  template <class T>
  class avlnode {
    friend class inorderAVLTreeTraversal<T> ;
	 public:
		avlnode (T  v);
		avlnode (T  v, avlnode<T> * left, avlnode<T> * right);

				// redefine access to children and
		void				left		(avlnode<T> * tnode);
		avlnode<T> *  left 		()	const;
		void				right		(avlnode<T> * tnode);
		avlnode<T> *  right		()	const;
		int				find		(T item);
		int 				size 		() const;

				// virtual methods
		avlnode<T>  *	add		(T value);
		avlnode<T>  *	remove	(T , avlnode<T>	* &);
		avlnode<T>	*	copy		(avlnode<T> * ) const;
		void			   release	();

				// public data
		T data;

	 protected:

				// data area
		short 	bf;
		avlnode<T> * leftptr;
		avlnode<T> * rightptr;

				// private methods
		avlnode<T> *		RotateLeft					 ();
		avlnode<T> *		RotateRight					 ();
		avlnode<T> *		restoreLeftBalance		 (int);
		avlnode<T> *		restoreRightBalance		 (int);
		avlnode<T> *		balance						 ();
		avlnode<T> * 		removeLeftmostDescendant (avlnode<T> * & );
  };

  template <class T>
  class AVLtree : public Tree<T> {
	 public:
			// constructors
		AVLtree () {root = 0; }
		AVLtree (AVLtree<T> & bst); //copy constructor
		~AVLtree () {deleteAll (); }

			// operations  -- redefine
		virtual	void		add				(T	value);
		virtual	void		remove			(T value);
		virtual	void		deleteAll		()		{rootptr ()-> release (); }
		virtual	int		includes			(T value) const;
		virtual	int		empty				() const	 {return root == 0; }

			// probes
		int size () {return root -> size(); }
		avlnode<T> *  	begin () const	{return root;}

	 protected:
		 avlnode<T> * root ;
		 avlnode<T> * rootptr () {return root; }
 };

  template <class T>
  avlnode<T>::avlnode (T  v) : data(v), leftptr(0), rightptr(0), bf(0) {}

  template <class T>
  avlnode<T>::avlnode (T  v, avlnode<T> * left, avlnode<T> * right )
					: data(v), leftptr(left), rightptr(right), bf(0) {}

  template <class T>
  avlnode<T> * avlnode<T>::RotateLeft ()
  {  // perform single left rotation of the current node
	  avlnode<T> * nodeA = this;
	  avlnode<T> * nodeB = nodeA -> right ();
		// reconnect
	  nodeA -> right(nodeB -> left ());
	  nodeB -> left(nodeA);
		// update the balance factors
	  int Abf = nodeA -> bf;
	  int Bbf = nodeB -> bf;
	  if (Bbf <= 0) {
		  if (Abf >= 1)
			 nodeB -> bf = (Bbf - 1);
		  else
			 nodeB -> bf = (Abf + Bbf - 2);
		  nodeA -> bf = (Abf - 1);
		  }
	  else {
		  if (Abf <= Bbf)
			 nodeB -> bf = (Abf - 2);
		  else
			 nodeB -> bf = (Bbf - 1);
		  nodeA -> bf = ((Abf - Bbf) - 1);
		  }
	  return nodeB;
  }

  template <class T>
  avlnode<T> * avlnode<T>::RotateRight ()
  {  // perform single left rotation of the current node
	  avlnode<T> * nodeA = this;
	  avlnode<T> * nodeB = nodeA -> left ();
		// reconnect
	  nodeA -> left(nodeB -> right ());
	  nodeB -> right(nodeA);
		// update the balance factors
	  int Abf = nodeA -> bf;
	  int Bbf = nodeB -> bf;
	  if (Bbf <= 0) {
		  if (Bbf > Abf)
			 nodeB -> bf = (Bbf + 1);
		  else
			 nodeB -> bf = (Abf + 2);
		  nodeA -> bf = (1 + Abf - Bbf);
		  }
	  else {
		  if (Abf <= -1)
			 nodeB -> bf = (Bbf + 1);
		  else
			 nodeB -> bf = (Abf + Bbf + 2);
		  nodeA -> bf = (Abf + 1);
		  }
	  return nodeB;
  }

  template <class T>
  avlnode<T> * avlnode<T>::restoreLeftBalance(int oldbf)
  {
	  avlnode<T> * leftchild = left ();
	  if (!leftchild)
		  bf++;
	  else if ((leftchild -> bf  != oldbf) && (leftchild -> bf == 0 ))
		  bf++;
	  if (bf > 1)
		  return balance ();
	  return this;
  }

  template <class T>
  avlnode<T> * avlnode<T>::restoreRightBalance(int oldbf)
  {
	  avlnode<T> * rightchild = right ();
	  if (!rightchild)
		  bf--;
	  else if ((rightchild -> bf  != oldbf) && (rightchild -> bf == 0 ))
		  bf--;
	  if (bf < -1)
		  return balance ();
	  return this;
  }

  template <class T>
  avlnode<T> * avlnode<T>::balance ()
  {  // balance tree rooted at node using single or double rotations
	  if (bf < 0) {
		 if (left () -> bf <= 0)
			 return RotateRight ();
		 else  { // perform double rotation
			 left (left () -> RotateLeft ());
			 return RotateRight ();
			 }
		 }
	  else {
		 if (right () -> bf >= 0)
			 return RotateLeft ();
		 else  { // perform double rotation
			 right (right () -> RotateRight ());
			 return RotateLeft ();
			 }
		 }
  }

  template <class T>
  avlnode<T> * avlnode<T>::add		(T value)
  {
	  if (value < data) {
		  if (left ()) {
			  int oldbf = left () -> bf;
			  left(left () -> add(value));
				// check to see if tree grew
			  if ((left () -> bf != oldbf) && (left () -> bf))
				  bf--;
			  }
		  else {
			  left(new avlnode<T> (value));
			  bf--;
			  }
		  }
	  else if (value > data) {
		  if (right ()) {
			  int oldbf = right () -> bf;
			  right(right () -> add(value));
				// check to see if tree grew
			  if ((right () -> bf != oldbf) && (right () -> bf))
				  bf++;
			  }
		  else {
			  right(new avlnode<T> (value));
			  bf++;
			  }
		  }
		  // check to make sure the tree is not out of balance
	  if ((bf < -1) || (bf > 1))
		  return balance ();
	  return this;
  }

  template <class T>
  avlnode<T> *	avlnode<T>::remove	(T value, avlnode<T>	* & bst)
  {
	  if (value == data) { // we're the one
		  bst = this;
		  if (!right ())
			  return left ();
		  // else find and rmove the leftmost descendant of the right child
		  int oldbf = right () -> bf;
		  avlnode<T> * newroot;
		  right(right() -> removeLeftmostDescendant(newroot) );
		  // connect the new root
		  newroot -> left(left() );
		  newroot -> right(right() );
		  newroot -> bf = bf;
		  return  newroot -> restoreRightBalance(oldbf);
		  }
	  else if (value < data) {  // remove from left child
		  if (!left() )
			  return this;   // no left child
		  // do the deletion
		  int oldbf = left () -> bf;
		  left(left()-> remove(value, bst));
		  return restoreLeftBalance (oldbf);
		  }
	  else { // remove from right child
		  if (!right() )
			  return this;  // no right child
		  // do deletion
		  int oldbf = right () -> bf;
		  right(right() -> remove(value, bst));
		  return restoreRightBalance (oldbf);
		  }
  }

  template <class T>
  avlnode<T> * avlnode<T>::removeLeftmostDescendant (avlnode<T> * & childptr)
  {
	  // see if we are the leftmost node
	  avlnode<T> * leftchild = left();
	  if (!leftchild)  { // we are
		  childptr = this;
		  return right(); // remove self
		  }
	  // else do the deletion
	  int oldbf = leftchild -> bf;
	  left(leftchild -> removeLeftmostDescendant (childptr) );
	  return restoreLeftBalance (oldbf);
  }

 template <class T>
  void avlnode<T>::left(avlnode<T> * tnode)
  {
	  leftptr = tnode;
  }

  template <class T>
  avlnode<T> * avlnode<T>::left ()	const
  {
	  return leftptr;
  }

  template <class T>
  void avlnode<T>::right(avlnode<T> * tnode)
  {
	  rightptr = tnode;
  }

  template <class T>
  avlnode<T> * avlnode<T>::right  ()	const
  {
	  return rightptr;
  }

  template <class T>
  int avlnode<T>::find (T item)
  {
	  int found = 0;
	  if (data == item)
		  found = 1;
	  else if ((item < data) && (left () != 0))
		  found = left() -> find (item);
	  else if (right () != 0)
		  found = right() -> find (item);
	  return found;
  }

  template <class T>
  int avlnode<T>::size () const
  {
	  int count = 1;
	  if (leftptr != 0)
		  count += leftptr -> size();
	  if (rightptr != 0)
		  count += rightptr -> size();
	  return count;
  }


  template <class T>
  avlnode<T> * avlnode<T>::copy (avlnode<T> * root) const
  {
	  avlnode<T> * newRoot;
	  if (root != 0) {
		  newRoot = new avlnode<T> (root -> data);
		  newRoot -> bf = root -> bf;
		  newRoot -> leftptr = root -> copy (root -> left());
		  newRoot -> rightptr = root -> copy (root -> right());
		  }
	  else
		  newRoot = 0;
	  return newRoot;
  }

  template <class T>
  void avlnode<T>::release ()
  {
	  if (leftptr != 0) {
		  leftptr -> release ();
		  delete leftptr;
		  leftptr = 0;
		  }
	  if (rightptr != 0) {
		  rightptr -> release ();
		  delete rightptr;
		  rightptr = 0;
		  }
  }

  template <class T>
  AVLtree<T>::AVLtree (AVLtree<T> & bst)
  {
	  if (root)
		  deleteAll ();
	  if (!bst.empty())
		  root-> copy(bst.root);
	  else
		  root = 0;
  }

  template <class T>
  void AVLtree<T>::add(T  item)
  {
	  if (root == 0)
		  root = new avlnode<T> (item);
	  else
		  root = rootptr ()-> add (item);
  }

  template <class T>
  void AVLtree<T>::remove (T  item)
  {
	  if (root)  {
		  avlnode<T> * junknode = 0;
		  root = rootptr () -> remove (item, junknode);
		  if (junknode)
			  delete junknode;
		  }
  }

  template <class T>
  int	AVLtree<T>::includes	(T value) const
  {
	  return root -> find( value );
  }

#endif
