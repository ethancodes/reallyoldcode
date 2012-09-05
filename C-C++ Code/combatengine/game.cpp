#include "creature.hpp"
#include "weapon.hpp"
#include "combat.hpp"
#include "dragon.hpp"
#include "hero.hpp"
#include <iostream.h>
#include <conio.h>

int main()
{
  Hero Nigel("Nigel", 5, 5, 5, 2);
  Weapon MyToy("Broad Sword", 1, 3);

  Dragon Methadone("Methadone Dragon", 1, 1, 2, 1);
  Weapon MethBreath("Breath of Methadone", 10, 5);
  Weapon MethClaw("Claw of Methadone", 1, 3);
  Weapon MethBite("Bite of Methadone", 1, 4);

  char buf[80];
  int MethWeapon;

  Nigel.setPos(2,2);
  Methadone.setPos(2,3);

  clrscr();

  Nigel.disp();
  Methadone.disp();
  cout << "\n\nPress ENTER to continue... ";
  cin.getline(buf, 80);

  clrscr();
  battle();

  while (!Methadone.dead() && !Nigel.dead())
  {
    cout << Nigel.getName() << " attacks with " << MyToy.getName() << "... ";
    switch (attack(Nigel, Methadone, MyToy))
    {
      case -1 : cout << Nigel.getName() << " is out of range.\n"; //I should move
                break;
      case 0  : cout << Methadone.getName() << " dodges!\n";
                break;
      case 1  : cout << "A hit! ";
	        if (Methadone.dead())
                {
                  cout << Methadone.getName() << " is dead!\n";
                }
                else
                {
                  cout << endl;
                }
                break;
    } //switch    
    if (!Methadone.dead())
    {
      cout << Methadone.getName() << " attacks with ";
      MethWeapon = Methadone.pickWeapon();
      if (MethWeapon == 1)
      {
        cout << MethClaw.getName() << "... ";
        switch (attack(Methadone, Nigel, MethClaw))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 1
      if (MethWeapon == 2)
      {
        cout << MethBite.getName() << "... ";
        switch (attack(Methadone, Nigel, MethBite))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 2
      if (MethWeapon == 3)
      {
        cout << MethBreath.getName() << "... ";
        switch (attack(Methadone, Nigel, MethBreath))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 3
    } //Methadone attack
  } //end while

  if (!Nigel.dead())
  {
    killed(Nigel, Methadone);
    Nigel.levelUp();
    Nigel.disp();
  }

  cout << "\n\nPress ENTER to continue... ";
  cin.getline(buf, 80);
  clrscr();
  Methadone.gainLife(2);

  while (!Methadone.dead() && !Nigel.dead())
  {
    cout << Nigel.getName() << " attacks with " << MyToy.getName() << "... ";
    switch (attack(Nigel, Methadone, MyToy))
    {
      case -1 : cout << Nigel.getName() << " is out of range.\n"; //I should move
                break;
      case 0  : cout << Methadone.getName() << " dodges!\n";
                break;
      case 1  : cout << "A hit! ";
	        if (Methadone.dead())
                {
                  cout << Methadone.getName() << " is dead!\n";
                }
                else
                {
                  cout << endl;
                }
                break;
    } //switch    
    if (!Methadone.dead())
    {
      cout << Methadone.getName() << " attacks with ";
      MethWeapon = Methadone.pickWeapon();
      if (MethWeapon == 1)
      {
        cout << MethClaw.getName() << "... ";
        switch (attack(Methadone, Nigel, MethClaw))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 1
      if (MethWeapon == 2)
      {
        cout << MethBite.getName() << "... ";
        switch (attack(Methadone, Nigel, MethBite))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 2
      if (MethWeapon == 3)
      {
        cout << MethBreath.getName() << "... ";
        switch (attack(Methadone, Nigel, MethBreath))
        {
          case -1 : cout << Methadone.getName() << " is out of range.\n"; //Methadone should move
                    break;
          case 0  : cout << "You dodge!\n";
                    break;
          case 1  : cout << "A hit! ";
    		    if (Nigel.dead())
                    {
                      cout << "You have been killed!\n";
                    }
                    else
                    {
                      cout << Nigel.getLife() << " life left!\n";
                    }
                    break;
        } //switch
      } // if methweapon is 3
    } //Methadone attack
  } //end while

  if (!Nigel.dead())
  {
    killed(Nigel, Methadone);
    Nigel.levelUp();
    Nigel.disp();
  }

  //end game
  return 0;
}