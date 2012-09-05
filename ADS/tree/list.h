//list.cpp
#include <assert.h>

#ifndef _LIST_H
#define _LIST_H

template <class T> class list;
template <class T> class listIterator;

  template <class T>
  class link   {
	 // double link
	 public:
		link * insert (T val);

	 private:
			// constructor
		link 	(T val, link * prevPtr, link * nextPtr);
		~link () {previous = next = 0; }

			// duplicate
		link<T> * duplicate ();

			// data areas
		T	value;
		link<T> * next;
		link<T> * previous;

			// friends
      friend list<T>;
	   friend listIterator<T>;

  };

  template <class T>
  class list		{
	 // Only provides for insertions and deletions at the front and back
	 public:
			// constructor and destructor
		list	() : first(0), last(0) {}
		list	(const list<T> & lst);		// copy constructor
		~list ();

			// operations
		int empty 	()	{return first == 0; }
		int size    ();
		T & back		()	{return last -> value; }
		T & front	() {return first -> value; }
		void	push_front	(T & );
		void	push_back	(T & );
		void	pop_front	();
		void	pop_back		();
		void	deleteAllValues ();

	 protected:
			// data area
		link<T> * first;
		link<T> * last;

			// friends
		friend class listIterator<T>;
  };

  template <class T>
  link<T> * link<T>::insert (T  val)
	// insert after me
  {
		next = new link<T> (val,this, next);
		assert (next != 0);
		return next;
  }

  template <class T>
  link<T>::link (T val, link<T> * prevPtr,  link<T> * nextPtr) : value(val),
						previous(prevPtr), next(nextPtr)
  {}  // create and initialize a new link

  template <class T>
  link<T> * link<T>::duplicate ()
	 // copy the series of links starting at this node
  {
	  link<T> * ptr;
	  if (next != 0)
		 ptr = new link<T> (value, previous, ptr ->duplicate());
	  else
		 ptr = new link<T> (value,previous,0);
	  // check that allocation was successful
	  assert(ptr != 0);
	  return ptr;
  }

  template <class T>
  list<T>::list (const list<T> & lst)
  {
	  if (lst.first == 0)
		 first = last = 0;
	  else  {
		 link<T> * p;
		 first = p -> duplicate();
		 p = first;
		 while (p != 0) {
			last = p;
			p = p -> next;
			}
		 }
  }

  template <class T>
  list<T>::~list ()
  {
	  deleteAllValues();
  }

  template <class T>
  void list<T>::deleteAllValues ()
  {
		link<T> * ptr;
		for (link<T> * p = first; p != 0; p = ptr)  {
			ptr = p -> next;
			p -> next = 0;
			delete p;
			}
		first = 0;
		last = 0;
  }

  template <class T>
  int list<T>::size ()
  {
	  int count = 0;
	  for (link<T> * p = first; p != 0; p = p -> next)
		  count++;
	  return count;
  }

  template <class T>
  void list<T>::push_front (T & item)
  {
	  link<T> * newLink = new link<T> (item, 0, 0);
	  if (first == 0)
		  first = last = newLink;
	  else  {
		  newLink -> next = first;
		  first -> previous = newLink;
		  first = newLink;
		  }
  }

  template <class T>
  void list<T>::push_back  (T & item)
  {
	  link<T> * newLink = new link<T> (item, 0, 0);
	  if (first == 0)
		  first = last = newLink;
	  else  {
		  last -> next = newLink;
		  newLink -> previous = last;
		  last = newLink;
		  }
	}

  template <class T>
  void list<T>::pop_back 	()
  {
	  assert (last != 0);
	  link<T> * p = last;
	  if (first == last)
		 first =  last = 0;
	  else {
		 last = last -> previous;
		 last -> next = 0;
		 }
	  delete p;
  }

  template <class T>
  void list<T>::pop_front	()
  {
	  assert (first != 0);
	  link<T> * p = first;
	  if (first == last)
		 first =  last = 0;
	  else {
		 first = first -> next;
		 first -> previous = 0;
		 }
	  delete p;
  }

#endif

