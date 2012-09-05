// file binStree.h
#ifndef _BINSTREE_H
#define _BINSTREE_H

#include <assert.h>
#include "gentree.h"

template <class T> class inorderTreeTraversal;

  template <class T>
  class bstnode {
	 friend class inorderTreeTraversal<T>;
	 public:
		bstnode (T  v) : data(v), leftptr(0), rightptr(0) {}
		bstnode (T  v, bstnode<T> * left, bstnode<T> * right)
					: data(v), leftptr(left), rightptr(right) {}

				// access to children
		void				left		(bstnode<T> * tnode);
		bstnode<T> *  	left 		()	const;
		void				right		(bstnode<T> * tnode);
		bstnode<T> *  	right		()	const;
		int				find		(T item);
		int 				size 		() const;

				// virtual methods
		bstnode<T>  *	add		(T value);
		bstnode<T>  *	remove	(T , bstnode<T>	* &);
		bstnode<T>	*	copy		(bstnode<T> * ) const;
		void			   release	();

				// the data field is public
		T	data;

	 protected:

				// data areas
		bstnode<T>	*	leftptr;
		bstnode<T>	* 	rightptr;

		void insert 		(bstnode<T> * newNode);
		bstnode<T> *  	removeLeftmostDescendant (bstnode<T>	* & childptr);
  };

  template <class T>
  class binstree : public Tree<T> {
	 friend class inorderTreeTraversal<T>;
	 public:
			// constructors
		binstree () {root = 0; }
		binstree (T  v) : root(v,0,0,0) {}
		binstree (binstree<T> & bst); //copy constructor
		~binstree()  {deleteAll(); }

			// operations
		virtual	void		add				(T	value);
		virtual	void		deleteAll		()	 {root -> release (); delete root;}
		virtual	int		includes			(T value) const;
		virtual	void		remove			(T value);
		virtual	int		empty				() const	 {return root == 0; }

			// probes
		int size () const {return root -> size(); }
		bstnode<T> *  	begin () const	{return root;}

	 protected:
		 bstnode<T> * root;
  };

  template <class T>
  void bstnode<T>::insert (bstnode<T> * newNode)
  {
	  assert(newNode != 0);
	  if (newNode -> data < data ) {
		  if (leftptr != 0)
			  leftptr -> insert (newNode);
		  else
			  leftptr = newNode;
		  }
	  else if (newNode -> data > data) {
		  if (rightptr != 0)
			  rightptr -> insert (newNode);
		  else
			  rightptr = newNode;
		  }
	  //else do nothing if key already appears in the tree
  }

  template <class T>
  bstnode<T> * bstnode<T>::add		(T value)
  {
	  bstnode<T> * newNode = new bstnode<T> (value);
	  insert (newNode);
	  return newNode;
  }

  template <class T>
  bstnode<T> *	bstnode<T>::remove	(T value, bstnode<T>	* & bst)
  {
	  if (value == data) { // we're the one
		  bst = this;
		  if (!right ())
			  return left ();
		  // else find and rmove the leftmost descendant of the right child
		  bstnode<T> * newroot;
		  right(right() -> removeLeftmostDescendant(newroot) );
		  // connect the new root
		  newroot -> left(left() );
		  newroot -> right(right() );
		  return  newroot;
		  }
	  else if (value < data) {  // remove from left child
		  if (!left() )
			  return this;   // no left child
		  // do the deletion
		  left(left()-> remove(value, bst));
		  return this;
		  }
	  else { // remove from right child
		  if (!right() )
			  return this;  // no right child
		  // do deletion
		  right(right() -> remove(value, bst));
		  return this;
		  }
  }

  template <class T>
  bstnode<T> * bstnode<T>::removeLeftmostDescendant (bstnode<T> * & childptr)
  {
	  // see if we are the leftmost node
	  bstnode<T> * leftchild = left();
	  if (!leftchild)  { // we are
		  childptr = this;
		  return right(); // remove self
		  }
	  // else do the deletion
	  left(leftchild -> removeLeftmostDescendant (childptr) );
	  return this;
  }

 template <class T>
  void bstnode<T>::left(bstnode<T> * tnode)
  {
	  leftptr = tnode;
  }

  template <class T>
  bstnode<T> * bstnode<T>::left () const
  {
	  return leftptr;
  }

  template <class T>
  void bstnode<T>::right(bstnode<T> * tnode)
  {
	  rightptr = tnode;
  }

  template <class T>
  bstnode<T> * bstnode<T>::right 	() const
  {
	  return rightptr;
  }

  template <class T>
  int bstnode<T>::find (T item)
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
  int bstnode<T>::size () const
  {
	  int count = 1;
	  if (leftptr != 0)
		  count += leftptr -> size();
	  if (rightptr != 0)
		  count += rightptr -> size();
	  return count;
  }

  template <class T>
  bstnode<T> * bstnode<T>::copy (bstnode<T> * root) const
  {
	  bstnode<T> * newRoot;
	  if (root != 0) {
		  newRoot = new bstnode<T> (root -> data);
		  newRoot -> leftptr = root -> copy (root -> left());
		  newRoot -> rightptr = root -> copy (root -> right());
		  }
	  else
		  newRoot = 0;
	  return newRoot;
  }

  template <class T>
  void bstnode<T>::release ()
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

  /*  template <class T>
  bstnode<T> * bstnode<T>::merge (bstnode<T> * left, bstnode<T> * right)
  {
	  if (left == 0)
		  return right;
	  if (right == 0)
		  return left;
	  bstnode<T> * child = merge (left, right -> leftptr);
	  child -> parent = right;
	  right -> leftptr = child;
	  return right;
  }
*/

  template <class T>
  binstree<T>::binstree (binstree<T> & bst)
  {
	  if (root)
		  delete root;
	  if (!bst.empty())
		  root -> copy(bst.root);
	  else
		  root = 0;
  }

  template <class T>
  void binstree<T>::add(T  item)
  {
	  if (root == 0)
		  root = new bstnode<T> (item);
	  else
		  root -> add (item);
  }

  template <class T>
  void binstree<T>::remove (T  item)
  {
	  if (root)  {
		  bstnode<T> * junknode = 0;
		  bstnode<T> * hold;
		  hold = root -> remove (item, junknode);
		  if (junknode) {
				if (root == junknode)
					root = hold;
				delete junknode;
				}
		  }
  }

  template <class T>
  int  binstree<T>::includes (T  item) const
  {
		return root -> find(item);
  }

#endif
