//function.h

template <class T>
void swap (T & x, T & y)
{
  T temp;
  temp = x;
  x = y;
  y = temp;
}
