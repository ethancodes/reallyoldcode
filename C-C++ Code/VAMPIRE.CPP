#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <dos.h>

void intro(void);
void instruct(void);
void about(void);
void DrawWorld(void);
void KillChr(void);
int DeadYet(void);
int GetDir(void);
int NewDir(void);
int Biten(void);
void drawscreen(void);

int dead = 0;
int kills = 0;
/* screen 35x25 */
int vxpos = 35, vypos = 10, vdir = 0; /* vampire */
int vhxpos = 1, vhypos = 1, vhdir = 0; /* vampire hunter */
int vh = 0, vhtimer = 0; /* they're only around for so long... */
int pxpos = 70, pypos = 20, pdir = 0; /* prey */
int p = 0, ptimer = 0; /* they're only around for so long... */

/* the um... characters for the characters*/
int vamp = 118, hunter = 197, prey = 1;

void main(void) {
	int key;
	int vhlimit = (random(290) + 11), vhshow = (random(290) + 11);
	int plimit = (random(290) + 11), pshow = (random(290) + 11);
	intro();
	drawscreen();
	do {
		if (!DeadYet()) {
		DrawWorld();
		delay(75);

		if (Biten()) {
			p = 0;
			ptimer = 0;
			gotoxy(pxpos, pypos);
			KillChr();
			pxpos = 1;
			pypos = 1;
			pdir = 0;
			pshow = (random(290) + 11);
			kills++;
		}

/* vampire hunter's section */
		if (vh) {
			if (NewDir) {
				vhdir = GetDir();
			}
			/* is it time for the vampire hunter to go away */
			if (vhtimer == vhlimit) {
				vh = 0;
				vhtimer = 0;
				gotoxy(vhxpos, vhypos);
				KillChr();
				vhxpos = 1;
				vhypos = 1;
				vhdir = 0;
				vhshow = (random(290) + 11);
			}
			else {
				vhtimer++;
			}
		} /* end if there is a vampire hunter */
		else {
			/* is it time for the vampire hunter to show up */
			if (vhtimer == vhshow) {
				vh = 1;
				vhxpos = (random(69) + 2);
				vhypos = (random(18) + 2);
				vhdir = 0;
				vhtimer = 0;
				vhlimit = (random(290) + 11);
			}
			else {
				vhtimer++;
			}
		} /* end of no vh yet */

/* vampire's section */
		if (kbhit()) {
			key = getch();
			if (key == 0) {
				key = getch();
			}
			if ((key == 115) || (key == 83)) {
				vdir = 0;
			}
			if ((key == 107) || (key == 75)) {
				gotoxy(1, 22);
				printf("%d victims have been bled. . .\n", kills);
			}
			if (key == 75) {
				vdir = 4;
			}
			if (key == 77) {
				vdir = 2;
			}
			if (key == 72) {
				vdir = 1;
			}
			if (key == 80) {
				vdir = 3;
			}
			if ((key == 113) || (key == 81)) {
				/* then die */
				dead = 1;
			}
		} /* end of if kb hit */
		} /* end of not dead yet */

/* prey's section */
		if (p) {
			if (NewDir) {
				pdir = GetDir();
			}
			/* is it time for the prey to leave */
			if (ptimer == plimit) {
				p = 0;
				ptimer = 0;
				gotoxy(pxpos, pypos);
				KillChr();
				pxpos = 1;
				pypos = 1;
				pdir = 0;
				pshow = (random(290) + 11);
			}
			else {
				ptimer++;
			}
		} /* end if there is prey */
		else {
			/* is it time for the prey to show up */
			if (ptimer == pshow) {
				p = 1;
				pxpos = (random(69) + 2);;
				pypos = (random(18) + 2);;
				pdir = 0;
				ptimer = 0;
				plimit = (random(290) + 11);
			}
			else {
				ptimer++;
			}
		} /* end of no prey yet */

	} while (!dead);

/* game's over */
	clrscr();
	if (DeadYet()) {
		puts(" ");
		puts("A vampire hunter got you!");
	}
	puts(" ");
	printf("You drank the blood of %d humans!\n", kills);
	puts(" ");
	puts("Thank you for playing Vampire Tag!");
}

void DrawWorld(void) {
	int i, j;

/* draw vampire */
	switch (vdir) {
		case 1 : { /* up */
			if (vypos > 3) {
				gotoxy(vxpos, vypos);
				KillChr();
				vypos--;
			}
			break;
			}
		case 2 : { /* right */
			if (vxpos < 70) {
				gotoxy(vxpos, vypos);
				KillChr();
				vxpos++;
			}
			break;
			}
		case 3 : { /* down */
			if (vypos < 20) {
				gotoxy(vxpos, vypos);
				KillChr();
				vypos++;
			}
			break;
			}
		case 4 : { /* left */
			if (vxpos > 2) {
				gotoxy(vxpos, vypos);
				KillChr();
				vxpos--;
			}
			break;
			}
	} /* end switch */
	gotoxy(vxpos, vypos);
	printf("%c", vamp);

/* draw vampire hunter */
	if (vh) {
	switch (vhdir) {
		case 1 : { /* up */
			if (vhypos > 3) {
				gotoxy(vhxpos, vhypos);
				KillChr();
				vhypos--;
			}
			break;
			}
		case 2 : { /* right */
			if (vhxpos < 70) {
				gotoxy(vhxpos, vhypos);
				KillChr();
				vhxpos++;
			}
			break;
			}
		case 3 : { /* down */
			if (vhypos < 20) {
				gotoxy(vhxpos, vhypos);
				KillChr();
				vhypos++;
			}
			break;
			}
		case 4 : { /* left */
			if (vhxpos > 2) {
				gotoxy(vhxpos, vhypos);
				KillChr();
				vhxpos--;
			}
			break;
			}
	} /* end switch */
	gotoxy(vhxpos, vhypos);
	printf("%c", hunter);
	} /* end of if vampire hunter exists */

/* draw prey */
	if (p) {
	switch (pdir) {
		case 1 : { /* up */
			if (pypos > 3) {
				gotoxy(pxpos, pypos);
				KillChr();
				pypos--;
			}
			break;
			}
		case 2 : { /* right */
			if (pxpos < 70) {
				gotoxy(pxpos, pypos);
				KillChr();
				pxpos++;
			}
			break;
			}
		case 3 : { /* down */
			if (pypos < 20) {
				gotoxy(pxpos, pypos);
				KillChr();
				pypos++;
			}
			break;
			}
		case 4 : { /* left */
			if (pxpos > 2) {
				gotoxy(pxpos, pypos);
				KillChr();
				pxpos--;
			}
			break;
			}
	} /* end switch */
	gotoxy(pxpos, pypos);
	printf("%c", prey);
	} /* end of prey exists */

}

void intro(void) {
	char chr;

	clrscr();
	puts("Welcome to Vampire Tag!");
	puts(" ");
	puts("For instructions press I, to play the game press P.");
	chr = getch();
	if ((chr == 'I') || (chr == 'i')) {
		instruct();
	}
	if ((chr == 'V') || (chr == 'v')) {
		about();
	}
}

void instruct(void) {
	int chr;
	puts(" ");
	puts("Use the arrow keys (left, right, up, and down) to move.");
	puts("S to stop. K for your kills so far. Q to quit.");
	puts("You're a vampire [v], you've got to drink the blood of as many");
	printf("humans [%c] as you can.\n", 1);
	puts(" ");
	printf("But beware, there are vampire hunters [%c] out there who will kill you!\n", 197);
	puts(" ");
	puts("Press any key to become undead...");
	chr = getch();
	printf("%c\n", chr);
}

void about(void) {
	int chr;

	clrscr();
	puts("Vampire Tag was programmed by Ethan Georgi in the summer of 1997.");
	puts("This game is dedicated to all the people who've died at the hands,");
	puts("or teeth, of people who get inspired by games and movies and music");
	puts("to kill.");
	puts(" ");
	puts("EMail: succubus@nightly.com");
	puts(" ");
	puts("Press any key to become undead...");
	chr = getch();
	printf("%c\n", chr);

}

void KillChr(void) {
	printf("%c", 0);
}

int DeadYet(void) {
	if ((vhxpos == vxpos) && (vhypos == vypos)) {
		dead = 1;
		return 1;
	}
	return 0;
}

int GetDir(void) {
	int d;
	d = ((random(4)) + 1);
	return d;
}

int NewDir(void) {
	int n;
	n = random(9);
	if (n > 0) {
		return 0;
	}
	else {
		return 1;
	}
}

int Biten(void) {
	if ((vxpos == pxpos) && (vypos == pypos)) {
		return 1;
	}
	return 0;
}

void drawscreen(void) {
	int i, j;

	clrscr();
	gotoxy(35, 1);
	puts("Vampire Tag!");
	gotoxy(1, 2);
	printf("%c", 201);
	for (i = 2; i <= 71; i++) {
		printf("%c", 205);
	}
	printf("%c\n", 187);
	for (i = 3; i <= 20; i++) {
		printf("%c", 186);
		for (j = 2; j <= 71; j++) {
			printf(" ");
		}
		printf("%c\n", 186);
	}
	printf("%c", 200);
	for (i = 2; i <= 71; i++) {
		printf("%c", 205);
	}
	printf("%c\n", 188);
}