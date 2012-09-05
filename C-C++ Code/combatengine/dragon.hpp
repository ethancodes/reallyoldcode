class Dragon : public Creature
// can attack with breath weapon, claws, or bite
{
  public:
    Dragon (char *n, int a = 3, int d = 1, int l = 20, int m = 1) :
		Creature(n, a, d, l, m, 500) {};
    int pickWeapon(); //pick how Dragon attacks
};

int Dragon::pickWeapon()
{
  return (1 + rand() % 3);
}