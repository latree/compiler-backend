#include <stdio.h>

int main() {
  int w = 10;

  int h() {
    int n = (w>0) ? 0 : 42;
    int k(int z) {
      return z+42;
    }
    int r = k(w);

    int g(int p) {
      int f(int b,int c) {
	c = c + 1;
	return p + b * c;
      }
      return k(p) - f(r,r) + f(p,r);
    }
    return g(n) + g(w);
  }

  printf("%d\n",h());
}
  
  


