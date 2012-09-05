//file vectitrf.cpp
#include "function.h"
#include "vectitr.h"
#include "listitr.h"
#include <iostream.h>

template <class T>
void sort1 (iterator<T> & itr, iterator<T> & jtr)
{
  for (itr.init(); ! itr; itr++)
  		for (jtr.init(); ! jtr; jtr++)
  			if ( jtr () < itr ())
  				swap(jtr (), itr ());
}

int main ()
{
	vector<double> v(10, 0.0);
	list<double> L;
	for (int i = 0; i < 10; i++)  {
		L.push_back(100 - i * i);
		v.push_back(10-i);
		}
	vectorIterator<double> itr(v);
	for (itr.init(); ! itr; itr++)
	  cout << itr( ) << ' ';
	cout << '\n';
	double total = 0;
	for (itr.init(); ! itr; itr++)
	  total += itr ();
	cout << "total is " << total << '\n';
	vectorIterator<double> jtr(v);
	sort1(itr, jtr);
	for (itr.init(); ! itr; itr++)
	  cout << itr( ) << ' ';
	cout << '\n';
	itr.sort(itr, jtr);
	for (itr.init(); ! itr; itr++)
	  cout << itr( ) << ' ';
	cout << '\n';
	listIterator<double> lt1(L);
	for (lt1.init(); ! lt1; lt1++)
	  cout << lt1( ) << ' ';
	cout << '\n';
	total = 0;
	for (lt1.init(); ! lt1; lt1++)
	  total += lt1 ();
	cout << "total is " << total << '\n';
	listIterator<double> jt1(L);
	sort1(lt1, jt1);
	for (lt1.init(); ! lt1; lt1++)
	  cout << lt1( ) << ' ';
	cout << '\n';
	lt1.sort(lt1, jt1);
	for (lt1.init(); ! lt1; lt1++)
	  cout << lt1( ) << ' ';
	cout << '\n';

   cout << "\nMY STUFF BEGINS HERE!\n";
   cout << "VECTOR ITERATOR:\n" << itr;
   cout << "LIST ITERATOR:\n" << lt1;
   return 1;
}