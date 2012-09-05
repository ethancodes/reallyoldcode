//AVLTree header file

class AVLTree
{
  protected:
    list l;
    int size;

  public:
    AVLTree() : size(0) {};
    void insert(key x);
    void remove(key x);
    key find_smallest(int k);
    int size();
};