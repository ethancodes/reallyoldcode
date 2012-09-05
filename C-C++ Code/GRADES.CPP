// This program will make easy work out of calculating your GPA.

#include <iostream.h>
#include <iomanip.h>
#include <string.h>
#include <conio.h>
#include <ctype.h>

float pointsFor(char *letter)
{
  float points;

  letter[0] = toupper(letter[0]);
  if (!strcmp(letter, "A+")) { points = 4.3; }
  if (!strcmp(letter, "A")) { points = 4.0; }
  if (!strcmp(letter, "A-")) { points = 3.7; }
  if (!strcmp(letter, "B+")) { points = 3.3; }
  if (!strcmp(letter, "B")) { points = 3.0; }
  if (!strcmp(letter, "B-")) { points = 2.7; }
  if (!strcmp(letter, "C+")) { points = 2.3; }
  if (!strcmp(letter, "C")) { points = 2.0; }
  if (!strcmp(letter, "C-")) { points = 1.7; }
  if (!strcmp(letter, "D+")) { points = 1.3; }
  if (!strcmp(letter, "D")) { points = 1.0; }
  if (!strcmp(letter, "D-")) { points = 0.7; }
  if (!strcmp(letter, "F+")) { points = 0.3; }
  if (!strcmp(letter, "F")) { points = 0.0; }
  if (!strcmp(letter, "F-")) { points = 0.0; }

  return points;
}

int main()
{
  int credHrs = 1, totCredHrs = 0, x = 1;
  float qualityPoints = 0.0, gpa;
  char *letterGrade;

  clrscr();

  while (credHrs)
  {
	cout << "\nEnter the credit hours for class #" << x << ", 0 when done: ";
	cin >> credHrs;
	if (credHrs)
	{
	  totCredHrs += credHrs;
	  cout << "Enter the letter grade issued for class #" << x << ": ";
	  cin >> letterGrade;
	  qualityPoints = (pointsFor(letterGrade) * credHrs) + qualityPoints;
	}
	cout << qualityPoints << " " << totCredHrs << endl;
	x++;
  }

  gpa = qualityPoints / totCredHrs;
  cout << setprecision(2) << setiosflags(ios::fixed | ios::showpoint)
	   << "\nYour GPA is " << gpa << endl;

  return 0;
}
