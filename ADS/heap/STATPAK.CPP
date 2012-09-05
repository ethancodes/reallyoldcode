//file stats.h
#include <iomanip.h>
#include <string.h>
#include <assert.h>
#include <math.h>
#include <stdlib.h>
#include <values.h>
#include "statpak.h"

  time_dep_stats :: time_dep_stats (char * str)
  {
	 unsigned int length = strlen(str);
	 name = new char[length + 1];
	 strcpy (name, str);
  }

  time_dep_stats :: ~time_dep_stats ()
  {
	 delete [] name;
  }

  void time_dep_stats :: init (double start_time)
  {
	 minVal = MAXINT;
	 maxVal = -MAXINT;
	 tstart = start_time;
	 tlast_entry = start_time;
	 last_val = 0;
	 total = 0;
	 sum_sqs = 0;
	 count = 0;
  }

  void time_dep_stats :: save (double time, int value)
  {
	 if (time >= tstart)
	 {
		if (value < minVal)
		  minVal = value;
		if (value > maxVal)
		  maxVal = value;
		double delta = time - tlast_entry;
		tlast_entry = time;
		total += last_val * delta;
		sum_sqs += last_val * last_val * delta;
		last_val = value;
		count++;
	 }
  }

  void time_dep_stats :: tell (ofstream & outs, double stop_time)
  {
	 double delta = stop_time - tlast_entry;
	 total += last_val * delta;
	 sum_sqs += last_val * last_val * delta;
	 double mean = total / (stop_time - tstart);
	 double diffsq = sum_sqs - mean * mean;
	 assert(diffsq >= 0);
	 double stdev = sqrt (diffsq);
	 // output name, mean, std. dev., min value, max value, count
	 // 10, 10:2, 12:2, 12:2, 12:2, 12
	 outs << setw(10) << name;
	 outs.setf(ios::fixed,	ios::floatfield);
	 outs.precision(2);
//	 outs.setf(ios::setpoint);
	 outs << setw(10) << mean << setw(12) << stdev << setw(12) << minVal;
	 outs << setw(12) << maxVal << setw(12) << count << '\n';
  }

  size_dep_stats :: size_dep_stats (char * str)
  {
	 unsigned int length = strlen(str);
	 name = new char[length + 1];
	 strcpy (name, str);
  }

  size_dep_stats :: ~size_dep_stats ()
  {
	 delete [] name;
  }

  void size_dep_stats :: init (double start_time)
  {
	 minVal = MAXFLOAT;
	 maxVal = -MAXFLOAT;
	 tstart = start_time;
	 total = 0.0;
	 sum_sqs = 0.0;
	 count = 0;
  }

  void size_dep_stats :: save (double time, double value)
  {
	 if (time > tstart)
	 {
		if (value < minVal)
		  minVal = value;
		if (value > maxVal)
		  maxVal = value;
		total += value;
		sum_sqs += value * value;
		count++;
		}
  }

  void size_dep_stats :: tell (ofstream & outs)
  {
	 double mean = total / double(count);
	 double diffsq = sum_sqs - double(count) * mean * mean;
	 assert(diffsq >= 0.0);
	 double stdev = diffsq / double (count - 1);
	 // output name, mean, std. dev., min value, max value, count
	 // 10, 10:2, 12:2, 12:2, 12:2, 12
	 outs << setw(10) << name;
	 outs.setf(ios::fixed,	ios::floatfield);
	 outs.precision(2);
//	 outs.setf(ios::setpoint);
	 outs << setw(10) << mean << setw(12) << stdev << setw(12) << minVal;
	 outs << setw(12) << maxVal << setw(12) << count << '\n';
  }

  histogram :: histogram (char * str)
  {
	 unsigned int length = strlen(str);
	 name = new char[length + 1];
	 strcpy (name, str);
	 cell_bounds = NULL;
	 cell_vals = NULL;
  }

  histogram :: ~histogram ()
  {
	 delete [] name;
	 delete [] cell_bounds;
	 delete [] cell_vals;
  }

  void histogram :: init (double start_time, unsigned int num_cells,
											double high, double low)
  {
	 double cwidth = (high - low) / double (num_cells);
	 cell_bounds = new double [num_cells + 1];
	 cell_vals = new int [num_cells + 1];
	 low_bound = low;
	 double bound = low_bound;
	 for (int i = 0; i < num_cells; i++)
	 {
		bound += cwidth;
		cell_bounds [i] = bound;
		cell_vals [i] = 0;
		}
	 // last cell is for all the overflow
	 cell_bounds [num_cells] = MAXFLOAT;
	 cell_vals [num_cells] = 0;
	 cells = num_cells;
	 tstart = start_time;
	 high_bound = high;
	 count = 0;
  }

  void histogram :: save (double time, double value)
  {
	 if (time >= tstart)
	 {
		int mark = 0;
		int i = 0;
		while ((i <= cells) && (value > cell_bounds[i]))
		{
		  mark++;
		  i++;
		  }
		cell_vals [mark] += 1;
		count++;
		}
  }

  void histogram :: tell (ofstream & outs)
  {
	 const max = 50;
	 outs << '\n' << '\n' << setw(42) << name << '\n' << '\n';
	 outs << setw(11) << "rel" << setw(6) << "cuml" << setw(8) << "upper";
	 outs << '\n';
	 outs << setw(5) << "obsv" << setw(6) << "freq" << setw(6) << "freq";
	 outs << setw(9) << " cell lim"  << " 0";
	 for (int i = 20; i <= 100; i += 20)
		outs << setw(10) << i;
	 outs << '\n';
	 outs << setw(28) << '+';
	 for (i = 0; i < 10; i++)
		outs << "----+";
	 outs << '\n';
	 double cum = 0.0;
	 double rel;
	 int stars;
	 int atsym_loc;
	 int j, diff;
	 for (i = 0; i < cells; i++)
	 {
		outs << setw(5) << cell_vals[i];
		if (count == 0)
		  count++;						// avoid divide by zero error
		rel = cell_vals[i]/double (count);
		cum += rel;
		stars = int(rel * 100 + 0.5) / 2;
		atsym_loc = int(cum * 100 + 0.5) / 2;
		outs.setf(ios::fixed,	ios::floatfield);
		outs.precision(2);
		outs << setw(6) << rel << setw(6) << cum << setw(9);
		outs << cell_bounds[i] << " |";
		for (j = 0; j < stars; j++)
		  outs << '*';
		diff = atsym_loc - stars;
		if (diff > 0)
		  outs << setw(diff) << '@';
		diff = max - atsym_loc;
		if (atsym_loc < max)
		  outs << setw(diff) << '|';
		 outs << '\n';
		}
	 outs << setw(5) << cell_vals[cells];
	 rel = cell_vals[i]/double (count);
	 cum += rel;
	 outs << setw(6) << rel << setw(6) << cum << setw(8) << "inf" << "  |";
	 stars = int(rel * 100 + 0.5) / 2;
	 for (j = 0; j < stars; j++)
		outs << '*';
	 diff = max - stars;
	 outs << setw(diff) << '@' << '\n';
	 outs << "-----" << setw(23) << '|';
	 for (j = 0; j < 10; j++)
		outs << "----+";
	 outs << '\n';
	 outs << setw(5) << count << setw(23) << '0';
	 for (j = 20; j <= 100; j += 20)
		outs << setw(10) << j;
	 outs << '\n' << '\n' << '\n';
  }

/*  test program
  void main ()
  {
	 time_dep_stats my_tds ("tds number 1");
	 size_dep_stats my_sds ("sds number 1");
	 histogram my_histo ("Histogram Number 1");
	 my_tds.init(0.0);
	 my_sds.init(10.0);
	 my_histo.init (0.0,10,100.0,0.0);
	 double t;
	 double val;
	 for (int i = 0; i < 100; i++) {
		t = i/5.0;
		val = (100.0 * i - double (i * i))/37.0;
		my_tds.save(t,i);
		my_sds.save(t,val);
		my_histo.save(t, val);
		}
	 ofstream outfile;
	 outfile.open("testdata.dat");
	 assert(outfile);
	 my_tds.tell(outfile, 100);
	 my_sds.tell(outfile);
	 my_histo.tell(outfile);
	 outfile.close();                       */
  }



