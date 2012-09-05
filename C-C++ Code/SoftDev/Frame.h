//class Frame. from which all is derived.

class Frame
{
  protected:
    Word words[32];
  public:
    Frame() {};				//constructor
    Word & operator [] (unsigned int index);
    virtual Word fetch(Logical l) = 0;
    virtual void write(Word w, Logical l) = 0;
}; //end of class declaration

Word & Frame::operator [] (unsigned int index)
{
  return words[index];
}
