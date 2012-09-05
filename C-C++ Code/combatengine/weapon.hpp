class Weapon
{
  protected:
    int range,
        damage;
    char *name;
  public:
    Weapon(char *n = "Weapon", int r = 1, int d = 1) : name(n), range(r), damage(d) {};
    char* getName();
    int getRange();
    int getDamage();
};

char* Weapon::getName()
{
  return name;
}

int Weapon::getRange()
{
  return range;
}

int Weapon::getDamage()
{
  return damage;
}