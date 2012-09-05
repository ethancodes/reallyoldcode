#include <iostream.h>
#include "mms.h"
class cpu
{
	public:
		cpu::cpu(void) {i = random(32767); d = random(32767);};
		void cpu::execute(void);

	private:
		MMS m;
		int i;
		int d;
};

void cpu::execute(void)
{
	for(int x = 0; x < 100; x++) { //main loop starts here
   cout << "*";
	int j = random(9);
	int k = random(9);
		switch (j){
			case 0 : i = random(32767);
			break;
			case 1 : i += 10;
			break;
			case 2 : i -=10;
			break;
			case 3 : i++;
			break;
			case 4 : i++;
			break;
			case 5 :
			case 6 :
			case 7 :
			case 8 :
			case 9 : i++;
			break;
		}
		if (i > 32767) {i = random(32767);};
		if (i < 0) {i = random(32767);};
		switch (k){
			case 0 : d = random(32767);
			break;
			case 1 : d += 10;
			break;
			case 2 : d -=10;
			break;
			case 3 : d++;
			break;
			case 4 : d++;
			break;
		}
		if (d > 32767) {d = random(32767);};
		if (d < 0) {d = random(32767);};

		Logical l(i);
		cout << "loading " << i << endl;
		Word w = m.Load(l);
		Logical d1(d);
		cout << "loading " << d << endl;
		w = m.Load(d1);
		cout << "loading " << d << endl;
		w = m.Load(d1);
		cout << "storing " << d << endl;
		m.Store(w, d1);
	}  //end for
} //end execute

