#include <stdio.h>

int main () {
  int a;
  printf("0x%016lx\n",(unsigned long int) (&a));
  return 0;
}
