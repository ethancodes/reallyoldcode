// file vector application
# include <iostream.h>
# include "vector.h"

  void main()
  {
        int i;
	 vector<int> v(10);
	 for (int i = 0; i < 10; i++)
		 v[i] = i;
	 vector<int> v2 = v;
	cout << v2;
	 vector<int> u ;
	 for (i = 0; i < 10; i++)
		 u.push_back(i);
	cout << u;
	 for (i = 0; i < 10; i++)
		 cout << u[i] << ' ';
	 cout << '\n';
	 u.remove(3);
	 cout << u << '\n';
	 u.push_back(u.front());
	 u.push_back(v2.back());
	 for (int j = 0; j < v2.size(); j++)
		 u.push_back(v2[j]);
	 cout << u << '\n';
	 u.pop_back();
	 cout << u << '\n';
	 u = v;
	 cout << u << '\n';
  }
