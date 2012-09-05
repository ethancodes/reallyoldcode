class Hero : public Creature
{
  private:
    int lvl();
    double xplvl(int lvl);
  protected:
    int magick;
  public:
    Hero (char *n, int a = 3, int d = 3, int l = 3, int m = 1, int mk = 1) :
	  Creature(n, a, d, l, m, 0), magick(mk) {};
    void levelUp();
    double nextLevel();
    void disp();
};

void Hero::disp()
{
  cout << name << ":\n";
  cout << "   ATTACK " << attack << endl;
  cout << "   DODGE  " << dodge << endl;
  cout << "   LIFE   " << life << endl;
  cout << "   MAGICK " << magick << endl;
  cout << "   EXP    " << xpoints << endl;
  cout << "   LEVEL  " << level << endl;
  cout << "   NEXT   " << nextLevel() - xpoints << "\n\n";
  return;
}

void Hero::levelUp()
{
  while (lvl())
  {
    attack++;
    dodge++;
    life += 3;
    magick++;
    level++;
  }
  return;
}

int Hero::lvl()
{
  return xpoints >= nextLevel();
}

double Hero::nextLevel()
{
  return xplvl(level);
}

double Hero::xplvl(int lvl)
{
  if (lvl == 1)
  {
    return 150;
  }
  else
  {
    return xplvl(lvl - 1) + (lvl * 150);
  }
}