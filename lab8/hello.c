#include <stdio.h>
#include <stdlib.h>

void say_hello(char *name) {
  char buf[128];
  sprintf(buf, "Hello, %s", name);
  printf("%s!\n", buf);
}

int main (int argc, char **argv) {
  if (argc >=2)
    say_hello(argv[1]);
}
