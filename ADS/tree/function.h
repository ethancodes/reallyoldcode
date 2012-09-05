//function.h

//for swapping generic items
template <class T>
void swap (T & x, T & y)
{
  T temp;
  temp = x;
  x = y;
  y = temp;
}

//for outputting generic vectors
template <class T>
ostream & operator << (ostream & out, vector<T> & v)
// not a class function, but an overloaded operator that is used
// to output a vector
{
  out << '(';
  for (int i = 0; i < v.size(); i++) {
    out << v[i];
	 if (i < v.size() - 1) { out << ", "; }
  }
  out << ")\n";
  return out;
}
