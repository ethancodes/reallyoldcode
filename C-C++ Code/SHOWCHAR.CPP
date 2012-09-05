#include <stdio.h>
#include <conio.h>

void main(void) {
	int chr;
	do {
		puts("Enter the ASCII code and I'll show you the character!");
		scanf("%d", &chr);
		printf("%d looks like %c\n", chr, chr);
	} while (chr > 0);
}