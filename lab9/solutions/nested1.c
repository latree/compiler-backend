#include <stdio.h>

int main() {
  
  int w = 10;

  int h(int m) {
    int n = (m>0) ? 0 : 42;
    int k(int z) {
      return z+42;
    }
    int r = k(m);

    int g(int p, int q) {
      int f(int a, int b, int c) {
	c = c + 1;
	return a + b * c;
      }
      return k(p) - f(p,q,q) + f(p,p,q);
    }
    return g(n,r) + g(m,r);
  }

  printf("%d\n",h(w));
}
  
  


