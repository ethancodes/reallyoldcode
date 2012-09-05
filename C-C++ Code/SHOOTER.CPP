#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <dos.h>

void intro(void);
void instruct(void);
void DrawWorld(void);
void KillChr(void);
int Hits(void);

int dead = 0;
/* screen 35x25 */
int bgpos = 35, bgdir = 1;
int ggpos = 35, ggdir = 0;
int GFire = 0, GFireYPos = 20, GFireXPos;
int BFire = 0, BFireYPos = 3, BFireXPos, BFireCount = 0;
int NextBFire;

/* the um... characters for the characters*/
int badguy = 1, goodguy = 142;

void main(void) {
	int key;
	intro();
	clrscr();
	gotoxy(35, 1);
	puts("Shooter97!");
	NextBFire = (random(289)+11);
	do {
		DrawWorld();
		if (!Hits()) {
		delay(75);
		BFireCount++;
		if (((BFireCount % NextBFire) == 0) && (!BFire)) {
			BFire = 1;
			BFireXPos = bgpos;
			NextBFire = (random(289)+11);
		}
		if (kbhit()) {
			key = getch();
			if (key == 0) {
				key = getch();
			}
			if (key == 75) {
				ggdir = -1;
			}
			if (key == 77) {
				ggdir = 1;
			}
			if ((key == 72) && (!GFire)) {
				GFire = 1;
				GFireXPos = ggpos;
			}
			if ((key == 113) || (key == 81)) {
				/* then die */
				dead = 1;
			}
		} /* end of if kb hit */
		} /* end of nobody got hit */
	} while (!dead);
	clrscr();
	switch (Hits()) {
		case -1 : {
				puts(" ");
				puts("The BadGuy shot you! GAME OVER!");
				break;
				}
		case 1 : {
				puts(" ");
				puts("You shot the BadGuy! YOU WIN!");
				break;
				}
	}
	puts(" ");
	puts("Thank you for playing Shooter97!");
}

void DrawWorld(void) {
	int i, j;
/*	clrscr(); */

	/* draw the bad guy */
	if (bgpos == 70) {
		bgdir = -1;
	}
	if (bgpos == 1) {
		bgdir = 1;
	}
	switch (bgdir) {
		case 1 : {
			if (bgpos < 70) {
				gotoxy(bgpos, 3);
				KillChr();
				bgpos++;
			}
			break;
			}
		case -1 : {
			if (bgpos > 1) {
				gotoxy(bgpos, 3);
				KillChr();
				bgpos--;
			}
			break;
			}
	} /* end switch */
	gotoxy(bgpos, 3);
	printf("%c", badguy);

	/* draw the good guy */
	switch (ggdir) {
		case 1 : {
			if (ggpos < 70) {
				gotoxy(ggpos, 20);
				KillChr();
				ggpos++;
			}
			break;
			}
		case -1 : {
			if (ggpos > 1) {
				gotoxy(ggpos, 20);
				KillChr();
				ggpos--;
			}
			break;
			}
	} /* end switch */
	gotoxy(ggpos, 20);
	printf("%c", goodguy);

	/* draw the good guy's missile if it's there */
	if (GFireYPos < 3) {
		GFire = 0;
		GFireYPos = 20;
	}
	if (GFire) {
		gotoxy(GFireXPos, GFireYPos);
		KillChr();
		GFireYPos--;
		gotoxy(GFireXPos, GFireYPos);
		printf("%c", 124);
	}

	/* draw the BadGuy's missile if it's there */
	if (BFireYPos > 20) {
		BFire = 0;
		BFireYPos = 3;
	}
	if (BFire) {
		gotoxy(BFireXPos, BFireYPos);
		KillChr();
		BFireYPos++;
		gotoxy(BFireXPos, BFireYPos);
		printf("%c", 15);
	}

}

void intro(void) {
	char chr;

	clrscr();
	puts("Welcome to Shooter97!");
	puts(" ");
	puts("For instructions press I, to play the game press P.");
	chr = getch();
	if ((chr == 'I') || (chr == 'i')) {
		instruct();
	}
}

void instruct(void) {
	int chr;
	puts(" ");
	puts("Use the arrow keys <- and -> to move left and right.");
	puts("Press the up arrow to fire.");
	puts("Don't get hit by the bombs!");
	puts("Destroy the BadGuy- if you can.");
	puts(" ");
	puts("Press any key to continue...");
	chr = getch();
	printf("%c\n", chr);
}

void KillChr(void) {
	printf("%c", 0);
}

int Hits(void) {
	if ((GFireXPos == bgpos) && (GFireYPos == 3)) {
		dead = 1;
		return 1;
	}
	if ((BFireXPos == ggpos) && (BFireYPos == 20)) {
		dead = 1;
		return -1;
	}
	return 0;
}