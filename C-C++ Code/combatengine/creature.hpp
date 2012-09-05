#include <iostream.h>

class Creature
{
  protected:
    int attack,
        dodge,
        life,
        movement,
        xpos,
        ypos,
        level;
    char *name;
    double xpoints;
  public:
    Creature(char *n = "Goon", int a = 1, int d = 1, int l = 1, int m = 1, double exp = 10) : 
		name(n), attack(a), dodge(d), life(l), movement(m), level(1), xpoints(exp) {};
    int getAttack(); //returns creature's attack value
    int getDodge(); //returns creature's dodge value
    int getLife(); //returns creature's life value
    int getMove(); //return creature's movement value
    char* getName(); //return the name of the creature
    int getLevel(); //return the creature's level
    void loseLife(int n); //sets creature's life value to current minus n
    void gainLife(int n); //sets creature's life value to current plus n
    int dead(); //returns true if getLife() returns 0
    void getPos(int &x, int &y); //returns creatures position
    void setPos(int x, int y); //set the position
    double getXP(int lvl); //returns xpoints
    void gainXP(double exp); //adds exp to xpoints
    void disp(); //display the creature
};

double Creature::getXP(int lvl)
{ //pass in the level of the creature who is killing it, since experience is dependent
  //on the level of who's getting it.
  int xp;

  xp = xpoints / lvl;
  if (xp < 1)
  {
    xp = 1;
  }

  return xp;
}

void Creature::gainXP(double exp)
{
  xpoints += exp;
}

char* Creature::getName()
{
  return name;
}

int Creature::getLevel()
{
  return level;
}

void Creature::setPos(int x, int y)
{
  xpos = x;
  ypos = y;
  return;
}

void Creature::getPos(int &x, int &y)
{
  x = xpos;
  y = ypos;
  return;
}

int Creature::dead()
{
  return (life <= 0);
}

int Creature::getAttack()
{
  return attack;
}

int Creature::getDodge()
{
  return dodge;
}

int Creature::getLife()
{
  return life;
}

int Creature::getMove()
{
  return movement;
}

void Creature::loseLife(int n)
{
  life -= n;
  return;
}

void Creature::gainLife(int n)
{
  life += n;
  return;
}

void Creature::disp()
{
  cout << name << ":\n";
  cout << "   ATTACK " << attack << "\n";
  cout << "   DODGE  " << dodge << "\n";
  cout << "   LIFE   " << life << "\n\n";
  return;
}