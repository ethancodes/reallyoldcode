#include <stdio.h>
#include <conio.h>
#include <string.h>

void hack(void);
void RGBtoHEX(void);
void HEXtoRGB(void);

char fhex[7] = { '\0' };
int r, g, b;

void main(int argc, char *argv[]) {
	int chr;
	char arg[4] = { '\0' }, hexcpy[7] = { '\0' };

	if (argc == 1) {
		hack();
	}
	else {
		strcpy(arg, strupr(argv[1]));
		if (strcmp(arg, "RGB") == 0) {
			puts("Enter the RGB code.");
			do {
			puts("What is the R(ed) value?");
			scanf("%d", &r);
			} while ((r < 0) || (r > 255));
			do {
			puts("What is the G(reen) value?");
			scanf("%d", &g);
			} while ((g < 0) || (g > 255));
			do {
			puts("What is the B(lue) value?");
			scanf("%d", &b);
			} while ((b < 0) || (b > 255));
			RGBtoHEX();
			puts(" ");
			printf("The HexCode for %d %d %d is %s.", r, g, b, fhex);
		}
		if (strcmp(arg, "HEX") == 0) {
			do {
			puts("Enter the hexadecimal code. For example: FFA0FF.");
			scanf("%s", &hexcpy);
			} while (strlen(hexcpy) != 6);
			strcpy(fhex, strupr(hexcpy));
			HEXtoRGB();
			puts(" ");
			printf("The RGB code for %s is %d %d %d.", fhex, r, g, b);
		}
	} /* end ifs */
	puts("\n\nThanks for using HEX2RGB!");
}

void HEXtoRGB(void) {
/* parse the string into three pieces of two */
	char red[3], green[3], blue[3];
	red[0] = fhex[0];
	red[1] = fhex[1];
	green[0] = fhex[2];
	green[1] = fhex[3];
	blue[0] = fhex[4];
	blue[1] = fhex[5];

/* RED ********************************************/
	switch (red[0]) {
		case '0' : { r = 0; break; }
		case '1' : { r = 16; break; }
		case '2' : { r = 32; break; }
		case '3' : { r = 48; break; }
		case '4' : { r = 64; break; }
		case '5' : { r = 80; break; }
		case '6' : { r = 96; break; }
		case '7' : { r = 112; break; }
		case '8' : { r = 128; break; }
		case '9' : { r = 144; break; }
		case 'A' : { r = 160; break; }
		case 'B' : { r = 176; break; }
		case 'C' : { r = 192; break; }
		case 'D' : { r = 208; break; }
		case 'E' : { r = 224; break; }
		case 'F' : { r = 240; break; }
	}

	switch (red[1]) {
		case '0' : { r = r + 0; break; }
		case '1' : { r = r + 1; break; }
		case '2' : { r = r + 2; break; }
		case '3' : { r = r + 3; break; }
		case '4' : { r = r + 4; break; }
		case '5' : { r = r + 5; break; }
		case '6' : { r = r + 6; break; }
		case '7' : { r = r + 7; break; }
		case '8' : { r = r + 8; break; }
		case '9' : { r = r + 9; break; }
		case 'A' : { r = r + 10; break; }
		case 'B' : { r = r + 11; break; }
		case 'C' : { r = r + 12; break; }
		case 'D' : { r = r + 13; break; }
		case 'E' : { r = r + 14; break; }
		case 'F' : { r = r + 15; break; }
	}

/* GREEN ********************************************/
	switch (green[0]) {
		case '0' : { g = 0; break; }
		case '1' : { g = 16; break; }
		case '2' : { g = 32; break; }
		case '3' : { g = 48; break; }
		case '4' : { g = 64; break; }
		case '5' : { g = 80; break; }
		case '6' : { g = 96; break; }
		case '7' : { g = 112; break; }
		case '8' : { g = 128; break; }
		case '9' : { g = 144; break; }
		case 'A' : { g = 160; break; }
		case 'B' : { g = 176; break; }
		case 'C' : { g = 192; break; }
		case 'D' : { g = 208; break; }
		case 'E' : { g = 224; break; }
		case 'F' : { g = 240; break; }
	}
	switch (green[1]) {
		case '0' : { g = g + 0; break; }
		case '1' : { g = g + 1; break; }
		case '2' : { g = g + 2; break; }
		case '3' : { g = g + 3; break; }
		case '4' : { g = g + 4; break; }
		case '5' : { g = g + 5; break; }
		case '6' : { g = g + 6; break; }
		case '7' : { g = g + 7; break; }
		case '8' : { g = g + 8; break; }
		case '9' : { g = g + 9; break; }
		case 'A' : { g = g + 10; break; }
		case 'B' : { g = g + 11; break; }
		case 'C' : { g = g + 12; break; }
		case 'D' : { g = g + 13; break; }
		case 'E' : { g = g + 14; break; }
		case 'F' : { g = g + 15; break; }
	}

/* BLUE ********************************************/
	switch (blue[0]) {
		case '0' : { b = 0; break; }
		case '1' : { b = 16; break; }
		case '2' : { b = 32; break; }
		case '3' : { b = 48; break; }
		case '4' : { b = 64; break; }
		case '5' : { b = 80; break; }
		case '6' : { b = 96; break; }
		case '7' : { b = 112; break; }
		case '8' : { b = 128; break; }
		case '9' : { b = 144; break; }
		case 'A' : { b = 160; break; }
		case 'B' : { b = 176; break; }
		case 'C' : { b = 192; break; }
		case 'D' : { b = 208; break; }
		case 'E' : { b = 224; break; }
		case 'F' : { b = 240; break; }
	}

	switch (blue[1]) {
		case '0' : { b = b + 0; break; }
		case '1' : { b = b + 1; break; }
		case '2' : { b = b + 2; break; }
		case '3' : { b = b + 3; break; }
		case '4' : { b = b + 4; break; }
		case '5' : { b = b + 5; break; }
		case '6' : { b = b + 6; break; }
		case '7' : { b = b + 7; break; }
		case '8' : { b = b + 8; break; }
		case '9' : { b = b + 9; break; }
		case 'A' : { b = b + 10; break; }
		case 'B' : { b = b + 11; break; }
		case 'C' : { b = b + 12; break; }
		case 'D' : { b = b + 13; break; }
		case 'E' : { b = b + 14; break; }
		case 'F' : { b = b + 15; break; }
	}
} /* end of Hex to RGB */



void RGBtoHEX(void) {
	int num;

/* RED ***********************************************/
	num = r / 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}
	num = r % 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}

/* GREEN ***********************************************/
	num = g / 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}
	num = g % 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}

/* BLUE ***********************************************/
	num = b / 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}
	num = b % 16;
	switch (num) {
		case 0 : { strcat(fhex, "0"); break; }
		case 1 : { strcat(fhex, "1"); break; }
		case 2 : { strcat(fhex, "2"); break; }
		case 3 : { strcat(fhex, "3"); break; }
		case 4 : { strcat(fhex, "4"); break; }
		case 5 : { strcat(fhex, "5"); break; }
		case 6 : { strcat(fhex, "6"); break; }
		case 7 : { strcat(fhex, "7"); break; }
		case 8 : { strcat(fhex, "8"); break; }
		case 9 : { strcat(fhex, "9"); break; }

		case 10 : { strcat(fhex, "A"); break; }
		case 11 : { strcat(fhex, "B"); break; }
		case 12 : { strcat(fhex, "C"); break; }
		case 13 : { strcat(fhex, "D"); break; }
		case 14 : { strcat(fhex, "E"); break; }
		case 15 : { strcat(fhex, "F"); break; }
	}
} /* end RGBtoHEXadecimal */

void hack(void) {
	puts(" ");
	puts("HEX2RGB was written by Ethan Georgi. He was working with OS/2 and");
	puts("HTML and got sick of not being able to use colors from one on the");
	puts("other. He sat down one morning and figured out what FFA0FF was in");
	puts("'normal' decimal numbers, and then tried that as a RGB code. It");
	puts("matched the color exactly. To make life easier, and to chew up time");
	puts("and practice C/C++ coding, he wrote a program that did the number");
	puts("crunching for him.");
	puts(" ");
	puts("And now he's sharing it with you. Don't you feel special?");
	puts(" ");
	puts("Add the option of your choice when you run this program. Available");
	puts("options are:");
	puts("		RGB convert from RGB to hexadecimal code");
	puts("		HEX convert from hexadecimal to RGB code");
}

