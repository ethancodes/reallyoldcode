// file iterator.h
// abstract class

#ifndef _ITERATOR_H
#define _ITERATOR_H

  template<class T>
  class iterator   {
	 public:
			// initialization
		 virtual int  init  			() = 0;

			// test if there is a current element
		 virtual int   operator !	() = 0;

			// access current element
		 virtual  T	&	operator ()	() = 0;

			// increment to next element
		 virtual int	operator ++	() = 0;

			// change current element
		 virtual void	operator = 	(T newValue) = 0;
  };

/* ostream & operator << (ostream & out, iterator & itr)
  {
		for (int i = itr.init(); ! itr; itr++)
			out << itr() << ' ';
		out << '\n';
		return out;
  }
*/
#endif
