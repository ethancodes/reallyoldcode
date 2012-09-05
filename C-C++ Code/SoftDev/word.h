//class Word

class Word
{
  protected:
    int value;
  public:
    Word() { value = 0; };				//default constructor
    Word(int v) { value = v; };		//just to be thorough...

    void setValue(int v) { value = v; };		//set a new value

    int getValue() { return value; };			//return the value
};
