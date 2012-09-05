#include <iostream.h>
#include <iomanip.h>

int main()
{
  int sweeps = 0, good = 0;
  int i, j, x, y;
  float v[8][8];
  float error, n;

  //initalize the grid thingy
  for (i = 0; i <= 7; i++)
  {
    for (j = 0; j <= 7; j++)
    {
      v[i][j] = 0.0;
    }
  }
  v[0][0] = v[0][7] = v[7][7] = v[7][0] = 1;
  v[0][1] = v[0][6] = v[1][0] = v[1][7] = .75;
  v[6][0] = v[6][7] = v[7][1] = v[7][6] = .75;
  v[0][2] = v[0][5] = v[2][0] = v[2][7] = .5;
  v[5][0] = v[5][7] = v[7][2] = v[7][5] = .5;
  v[0][3] = v[0][4] = v[3][0] = v[3][7] = .25;
  v[4][0] = v[4][7] = v[7][3] = v[7][4] = .25;

  //show me the grid
  for (i = 0; i <= 7; i++)
  {
    for (j = 0; j <= 7; j++)
    {
      cout << setprecision(2) << setw(5) << v[i][j];
    }
    cout << endl;
  }

  //do the seidel
  while (!good)
  {
    error = 0.0;
    for (i = 0; i <= 7; i++)
    {
      for (j = 0; j <= 7; j++)
      {
        if ((i != 0) && (i != 7) && (j != 0) && (j != 7))
        {
          n = (v[i-1][j] + v[i+1][j] + v[i][j-1] + v[i][j+1])/4.0;
          error = error + ((v[i][j] - n) * (v[i][j] - n));
          v[i][j] = n;
        } //if
      } //for j
    } //for i
    sweeps++;

    if (error <= 0.1) { good = 1; }
    else
    {
      cout << "Error of " << error << ". Trying again...\n";
    }
   //show me the grid
   for (x = 0; x <= 7; x++)
   {
     for (y = 0; y <= 7; y++)
     {
       cout << setprecision(2) << setw(5) << v[x][y];
     }
     cout << endl;
   }
   cin >> sweeps;

  } //end while not good */

  cout << "Did it in " << sweeps << " sweeps.\n";

  //show me the grid
  for (i = 0; i <= 7; i++)
  {
    for (j = 0; j <= 7; j++)
    {
      cout << setprecision(2) << setw(5) << v[i][j];
    }
    cout << endl;
  }

  return 0;
}