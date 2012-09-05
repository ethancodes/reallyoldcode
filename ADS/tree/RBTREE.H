// file RBtree.h
#ifndef _RBTREE_H
#define _RBTREE_H

#include <assert.h>
#include "gentree.h"

  template <class T>
  class rbnode /*: public treenode<T> */{
	 friend class inorderRBTreeTraversal<T>;
	 public:
		enum color {crimson, red, black, absidian};
		rbnode (T  v);
		rbnode (color tint = black) : leftptr(0), rightptr(0), shade(tint),
												leaf(1) {}  // external node constructor

				// redefine access to children and
		void				left		(rbnode<T> * tnode);
		rbnode<T> *  	left 		()	const;
		void				right		(rbnode<T> * tnode);
		rbnode<T> *  	right		()	const;
		int				find		(T item);
		int 				size 		() const;

				// virtual methods
		rbnode<T>  *	add		(T value);
		rbnode<T>  *	remove	(T , rbnode<T>	* &);
		rbnode<T>  *	copy		(rbnode<T> * ) const;
		void			   release	();
		void				blacken	()  {shade = black;}
		int				isLeaf 	()	 {return leaf == 1;}

				// public data
		T data;

	 protected:

				// data area
		color 		shade;
		short			leaf;
		rbnode<T> * leftptr;
		rbnode<T> * rightptr;

				// private methods
		rbnode<T> *		RotateLeft					 ();
		rbnode<T> *		RotateRight					 ();
		rbnode<T> *		restoreLeftBalance		 ();
		rbnode<T> *		restoreRightBalance		 ();
		rbnode<T> *		balance						 ();
		rbnode<T> * 	removeLeftmostDescendant (rbnode<T> * & );
  };

  template <class T>
  class RedBlackTree : public Tree<T> {
	 public:
			// constructors
		RedBlackTree () {root = 0; }
		RedBlackTree (RedBlackTree<T> & bst); //copy constructor
		~RedBlackTree () {deleteAll (); }

			// operations  -- redefine
		virtual	void		add				(T	value);
		virtual	void		remove			(T value);
		virtual	void		deleteAll		()	{rootptr ()-> release(); delete root; }
		virtual	int		includes			(T value) const;
		virtual	int		empty				() const	 {return root == 0; }

			// probes
		int size () {return root -> size(); }
		rbnode<T> *  	begin () const	{return root;}

	 protected:
		 rbnode<T> * root ;
		 rbnode<T> * rootptr () {return root; }
 };

  template <class T>
  rbnode<T>::rbnode (T  v) : data(v), leaf (0), shade (red)
  {
	  leftptr = new rbnode (black);
	  rightptr = new rbnode (black);
  }

  template <class T>
  rbnode<T> * rbnode<T>::RotateLeft ()
  {  // perform single left rotation of the current node
	  rbnode<T> * nodeA = this;
	  rbnode<T> * nodeB = nodeA -> right ();
		// reconnect
	  nodeA -> right(nodeB -> left ());
	  nodeB -> left(nodeA);
	  return nodeB;
  }

  template <class T>
  rbnode<T> * rbnode<T>::RotateRight ()
  {  // perform single left rotation of the current node
	  rbnode<T> * nodeA = this;
	  rbnode<T> * nodeB = nodeA -> left ();
		// reconnect
	  nodeA -> left(nodeB -> right ());
	  nodeB -> right(nodeA);
	  return nodeB;
  }

  template <class T>
  rbnode<T> * rbnode<T>::restoreLeftBalance()
  {
	  if (left ()-> shade == absidian) {
		  if ((right ()-> shade == red) && (shade == black)) {// case 1
			  rbnode<T> *  nodeB = right ();
			  shade = red;  // re-color ourself
			  nodeB -> shade = black;
			  rbnode<T> * parent = RotateLeft ();
			  parent -> left (restoreLeftBalance ());
			  return parent;
			  }
			rbnode<T> * rightchild = right ();
			rbnode<T> * leftsib = rightchild -> left ();
			rbnode<T> * rightsib = rightchild -> right ();
			if ((leftsib -> shade == black) && (rightsib -> shade == black)) {
				// case 2
				rightchild -> shade = red;
				left ()-> shade = black;
				if (shade == red)
					shade = black;
				else
					shade = absidian;  // promote the extra unit of black up the tree
				return this;
				}
			if ((leftsib -> shade == red) && (rightsib -> shade == black)) {
				// case 3
				leftsib -> shade = black;
				rightchild -> shade = red;
				rightsib = rightchild;
				right (rightchild -> RotateRight ());
				}
			if (rightsib -> shade == red) { // case 4
				rightsib -> shade = black;
				right ()-> shade = shade;
				left ()-> shade = black;
				shade = black;
				return RotateLeft ();
				}
			}
	  return this;
  }

  template <class T>
  rbnode<T> * rbnode<T>::restoreRightBalance()
  {
	  if (right ()-> shade == absidian) {
		  if ((left ()-> shade == red) && (shade == black)) {// case 1
			  rbnode<T> *  nodeB = left ();
			  shade = red;  // re-color ourself
			  nodeB -> shade = black;
			  rbnode<T> * parent = RotateRight ();
			  parent -> right (restoreRightBalance ());
			  return parent;
			  }
			rbnode<T> * leftchild = left ();
			rbnode<T> * leftsib = leftchild -> left ();
			rbnode<T> * rightsib = leftchild -> right ();
			if ((leftsib -> shade == black) && (rightsib -> shade == black)) {
				// case 2
				leftchild -> shade = red;
				right ()-> shade = black;
				if (shade == red)
					shade = black;
				else
					shade = absidian;  // promote the extra unit of black up the tree
				return this;
				}
			if ((rightsib -> shade == red) && (leftsib -> shade == black)) {
				// case 3
				rightsib -> shade = black;
				leftchild -> shade = red;
				leftsib = leftchild;
				left (leftchild -> RotateLeft ());
				}
			if (leftsib -> shade == red) { // case 4
				leftsib -> shade = black;
				left ()-> shade = shade;
				right ()-> shade = black;
				shade = black;
				return RotateRight ();
				}
			}
	  return this;
  }

  template <class T>
  rbnode<T> * rbnode<T>::balance ()
  {  // balance tree rooted at node using single or double rotations
	  if (left ()-> shade == crimson) {
		 if (right () -> shade == red) { // case 1
			 right ()-> shade = black;
			 left ()-> shade = black;
			 shade = red;  // color myself red
			 return this;
			 }
		 else { // case 2 or 3
			 if (left ()-> left ()-> shade == red) {
				 left ()-> shade = black;
				 shade = red;
				 return RotateRight ();  // case 3
				 }
			 else  { // perform double rotation
				 left ()-> shade = red;  // no longer crimson
				 left (left () -> RotateLeft ()); // convert to case 3
				 left ()-> shade = black;
				 shade = red;
				 return RotateRight ();
				 }
			 }
		 }
	  else {  // right child is crimson
		 if (left () -> shade == red) { // case 1
			 right ()-> shade = black;
			 left ()-> shade = black;
			 shade = red;  // color myself red
			 return this;
			 }
		 else { // case 2 or 3
			 if (right ()-> right ()-> shade == red) {
				 right ()-> shade = black;
				 shade = red;
				 return RotateLeft ();  // case 3
				 }
			 else  { // perform double rotation
				 right ()-> shade = red;  // no longer crimson
				 right (right () -> RotateRight ()); // convert to case 3
				 right ()-> shade = black;
				 shade = red;
				 return RotateLeft ();
				 }
			 }
		 }
  }

  template <class T>
  rbnode<T> * rbnode<T>::add		(T value)
  {
	  rbnode<T> * childptr;
	  if (value < data) {
		  if (!left ()-> leaf) {
			  left(left () -> add(value));
			  childptr = left ();
			  }
		  else {
			  delete left ();
			  left(new rbnode<T> (value));
			  childptr = left ();
			  }
		  }
	  else if (value > data) {
		  if (!right ()-> leaf) {
			  right(right () -> add(value));
			  childptr = right ();
			  }
		  else {
			  delete right ();
			  right(new rbnode<T> (value));
			  childptr = right ();
			  }
		  }
		  // check to make sure the tree is not out of balance
	  if ((shade == red) && (childptr -> shade == red)) {
		  shade = crimson;
		  return this;
		  }
	  if (childptr -> shade != crimson)
		  return this;
	  return balance ();
  }

  template <class T>
  rbnode<T> *	rbnode<T>::remove	(T value, rbnode<T>	* & bst)
  {
	  rbnode<T> * x;
	  if (value == data) { // we're the one
		  bst = this;
		  if (right ()-> leaf) {
			  delete right ();		// delete external node
			  x = left ();
			  if (shade == black)
				  if (x -> shade == red)
					  x -> shade = black;
				  else
					  x -> shade = absidian;
			  return left ();
			  }
		  // else find and remove the leftmost descendant of the right child
		  rbnode<T> * newroot;
		  right(right() -> removeLeftmostDescendant(newroot) );
		  // connect the new root
		  newroot -> left(left() );
		  newroot -> right(right() );
		  newroot -> shade = shade;
		  return  newroot -> restoreRightBalance();
		  }
	  else if (value < data) {  // remove from left child
		  if (left()-> leaf )
			  return this;   // no left child
		  // do the deletion
		  left(left()-> remove(value, bst));
		  return restoreLeftBalance ();
		  }
	  else { // remove from right child
		  if (right()-> leaf )
			  return this;  // no right child
		  // do deletion
		  right(right() -> remove(value, bst));
		  return restoreRightBalance ();
		  }
  }

  template <class T>
  rbnode<T> * rbnode<T>::removeLeftmostDescendant (rbnode<T> * & childptr)
  {
	  // see if we are the leftmost node
	  rbnode<T> * leftchild = left();
	  if (leftchild -> leaf)  { // we are
		  childptr = this;
		  rbnode<T> * x = right ();
		  delete left ();					// remove exterior node
		  if (shade == black)
			  if (x -> shade == red)
				  x -> shade = black;
			  else
				  x -> shade = absidian;
		  return right(); // remove self
		  }
	  // else do the deletion
	  left(leftchild -> removeLeftmostDescendant (childptr) );
	  return restoreLeftBalance ();
  }

 template <class T>
  void rbnode<T>::left(rbnode<T> * tnode)
  {
	  leftptr = tnode;
  }

  template <class T>
  rbnode<T> * rbnode<T>::left ()	const
  {
	  return leftptr;
  }

  template <class T>
  void rbnode<T>::right(rbnode<T> * tnode)
  {
	  rightptr = tnode;
  }

  template <class T>
  rbnode<T> * rbnode<T>::right  ()	const
  {
	  return rightptr;
  }

  template <class T>
  int rbnode<T>::find (T item)
  {
	  int found = 0;
	  if (data == item)
		  found = 1;
	  else if ((item < data) && (!left ()-> leaf))
		  found = left() -> find (item);
	  else if (!right ()-> leaf)
		  found = right() -> find (item);
	  return found;
  }

  template <class T>
  int rbnode<T>::size () const
  {
	  int count = 1;
	  if (leftptr != 0)
		  count += leftptr -> size();
	  if (rightptr != 0)
		  count += rightptr -> size();
	  return count;
  }


  template <class T>
  rbnode<T> * rbnode<T>::copy (rbnode<T> * root) const
  {
	  rbnode<T> * newRoot;
	  if (root != 0) {
		  newRoot = new rbnode<T> (root -> data);
		  if (!root -> left ()-> leaf)
			  delete newRoot -> left ();
		  if (!root -> right ()-> leaf)
			  delete newRoot -> right ();
		  newRoot -> leaf = root -> leaf;
		  newRoot -> shade = root -> shade;
		  newRoot -> leftptr = root -> copy (root -> left());
		  newRoot -> rightptr = root -> copy (root -> right());
		  }
	  else
		  newRoot = 0;
	  return newRoot;
  }

  template <class T>
  void rbnode<T>::release ()
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
  RedBlackTree<T>::RedBlackTree (RedBlackTree<T> & bst)
  {
	  if (root)
		  deleteAll ();
	  if (!bst.empty())
		  root-> copy(bst.root);
	  else
		  root = 0;
  }

  template <class T>
  void RedBlackTree<T>::add(T  item)
  {
	  if (root == 0)
		  root = new rbnode<T> (item);
	  else
		  root = rootptr ()-> add (item);
	  root -> blacken ();  // root node is always black
  }

  template <class T>
  void RedBlackTree<T>::remove (T  item)
  {
	  if (root)  {
		  rbnode<T> * junknode = 0;
		  root = rootptr () -> remove (item, junknode);
		  root -> blacken ();  // be sure to maintain the color of the root
		  if (root -> isLeaf ()) {
			  delete root;
			  root = 0;
			  }
		  if (junknode)
			  delete junknode;
		  }
  }

  template <class T>
  int	RedBlackTree<T>::includes	(T value) const
  {
	  return root -> find( value );
  }

#endif