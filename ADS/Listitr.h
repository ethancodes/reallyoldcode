// file list iterator
#include "list.h"
#include "iterator.h"
#include <assert.h>

#ifndef _LIST_ITERATOR_H
#define _LIST_ITERATOR_H

  template<class T>
  class listIterator : public iterator<T>  {
	 public:
			// constructor
                 listIterator(list<T> & L) : data(L), currentKey(L.first) {};

			// initialization
		 virtual int  init();

			// test if there is a current element
		 virtual int   operator ! ();

			// access current element
		 virtual  T & operator () ();

			// increment to next element
		 virtual int	operator ++	();

			// change current element
		 virtual void	operator = 	(T newValue);

			// methods specific to list iterators
                void sort (listIterator<T> & itr, listIterator<T> & jtr);
		int operator -- ();
		link<T> * key ();
		void set (listIterator &);

	 private:
		link<T> *  currentKey;
		list<T> &  data;
  };

  template <class T>
  int listIterator<T>::init()
  {
	  currentKey = data.first;
	  return operator !();
  }

  template <class T>
  int listIterator<T>::operator  	!()
  {
	  return currentKey != 0;
  }

  template <class T>
  T & listIterator<T>::operator		  () ()
  {
		assert (currentKey != 0);
		return currentKey -> value;
  }

  template <class T>
  int listIterator<T>::operator		  ++ ()
  {
		assert (currentKey != 0);
		currentKey = currentKey -> next;
		return  operator	!();
  }

  template <class T>
  int listIterator<T>::operator		  -- ()
  {
		assert (currentKey != 0);
		currentKey = currentKey -> previous;
		return  operator	!();
  }

  template <class T>
  void listIterator<T>::operator 	= (T  newValue)
  {
	  assert (currentKey != 0);
	  currentKey -> value = newValue;
  }

  template <class T>
  link<T> * listIterator<T>::key		()
  {
		return currentKey;
  }

  template <class T>
  void  listIterator<T>::set  (listIterator<T> & itr)
  {
	  currentKey = itr.key();
  }

  template <class T>
  void listIterator<T>::sort (listIterator<T> & itr, listIterator<T> & jtr)
  {
		for (itr.init(); ! itr; itr++) {
			for (jtr.set(itr); ! jtr; jtr++)
				if ( jtr () < itr ())
					swap(jtr (), itr ());
			}
  }

  template <class T>
  ostream & operator << (ostream & out, listIterator<T> & l)
  {
	  out << '(';
	  for (l.init(); ! l; l++)
     {
		  out << l();
		  if (! l) { out << ", "; }
	  }
	  out << ")\n";
	  return out;
  }


#endif
