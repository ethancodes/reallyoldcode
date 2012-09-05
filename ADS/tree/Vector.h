//file vector.h
#include <assert.h>
#include <iostream.h>
# ifndef VECTOR_H
# define VECTOR_H

template <class T>
class vector  {
  public:
		 // constructors
	 vector () {buffer = 0; resize(0); }
	 vector (unsigned int numberElements) {buffer = 0; resize(numberElements); }
	 vector (unsigned int numberElements, T & initVal); //init with default val
	 vector (vector & source);  //copy constructor
	 virtual ~vector () {delete [] buffer; }

		 //Element Access
	 T back() {return buffer[mySize - 1]; }  //error if vector is empty
	 int empty()  {return mySize == 0; }
	 T front() {return buffer[0]; }          //error if vector is empty
	 void pop_back() {mySize--; }
	 void push_back(T);
	 void remove (unsigned  int index); 		// remove and fill hole

	 T & operator [] (unsigned int index) const;

		 //Size
	 unsigned int length () const;
	 void resize (unsigned int newSize) {reserve(newSize); mySize = newSize; }
	 void reserve (unsigned int newCapacity);
	 int size () {return mySize; }
	 int capacity () {return myCapacity; }

  protected:
	 unsigned int mySize;
	 unsigned int myCapacity;
	 T * buffer;
  };

  template <class T>
  void vector<T>::reserve (unsigned int newCapacity)
  {
	 if (buffer == 0) {
		mySize = 0;
		myCapacity = 0;
	 }
	 if (newCapacity <= myCapacity)
		return;
	 T * newBuffer = new T[newCapacity];
	 assert(newBuffer);
	 for (int i = 0; i < mySize; i++)
		 newBuffer[i] = buffer[i];
	 myCapacity = newCapacity;
	 delete [] buffer;
	 buffer = newBuffer;
  }

  template <class T> vector<T>::vector(unsigned int numberElements,
			  T & initVal) : myCapacity(numberElements)
  {
	 buffer = new T[myCapacity];
	 assert(buffer);
	 for (int i = 0; i < myCapacity; i++)
		buffer[i] = initVal;
	 mySize = myCapacity;
  }

  template <class T> vector<T>::vector(vector<T> & source)
  {
	 buffer = new T [source.capacity()];
	 mySize = source.size();
	 assert(buffer);
	 for (int i = 0; i < mySize; i++)
		buffer[i] = source.buffer[i];
  }

  template <class T> void vector<T>::push_back(T value)
  {
	 if (mySize >= myCapacity)
		 reserve(myCapacity + 5);
	 buffer[mySize++] = value;
  }

  template <class T>
  void vector<T>::remove(unsigned int index)
  {
	  assert (index < mySize);
	  int i = index + 1;
	  while (i < mySize)
		  buffer [index++] = buffer [i++];
	  mySize--;
  }

  template <class T>
  unsigned int vector<T>::length() const
  {
	  return mySize;
  }

  template <class T>
  T & vector<T>::operator [] (unsigned int index) const
  {
	  assert(index < mySize);
	  return buffer[index];
  }
#endif
