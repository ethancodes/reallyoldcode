#include <stdlib.h>
#include <time.h>

void battle()
{
  srand(time(NULL));
  return;
}

int inRange(Creature &predator, int wrange, Creature &prey)
{ //am i within range of what i'm attacking
  int preyx, preyy, predx, predy;

  predator.getPos(predx, predy);
  prey.getPos(preyx, preyy);

  if ((abs(preyx - predx) <= wrange) && (abs(preyy - predy) <= wrange))
  {
    return 1;
  }
  else
  {
    return 0;
  }
}

int attack(Creature &predator, Creature &prey, Weapon &w)  
{ //pass in attacked, attacker, and weapon used
  //return 1 if successful, 0 if miss, -1 if out of range
  int target, diceroll;

  if (inRange(predator, w.getRange(), prey))
  {
    target = prey.getDodge() - predator.getAttack();
    diceroll = 1 + rand() % 100;

    if (target == 0)
    { // 50% chance of hit
      target = 50;
      if (diceroll <= target)
      { // a hit
        prey.loseLife(w.getDamage());
        return 1; //the prey is hit
      }
      else
      {
        return 0;
      }
    }
    if (target < 0)
    { // 100 - (target*10)% chance of hit
      target = 100 - (target*10);
      if (diceroll <= target)
      { // a hit
        prey.loseLife(w.getDamage());
        return 1; //the prey is hit
      }
      else
      {
        return 0;
      }
    }
    if (target > 0)
    { // (target*10)% chance of hit
      target = abs(target * 10);
      if (diceroll <= target)
      { // a hit
        prey.loseLife(w.getDamage());
        return 1; //the prey is hit
      }
      else
      {
        return 0;
      }
    }
  } //if in range
  else { return -1; } // out of range
}

void killed(Creature &survivor, Creature &deceased)
{
  survivor.gainXP(deceased.getXP(survivor.getLevel()));
  return;
}