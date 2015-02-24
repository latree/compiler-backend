# Makefile for CS322 Lab8.
#

examples: example1 example1_O1 example2 example2_O1 

example1: example1.s
	$(CC) -g -o example1 example1.s

example1.s: example1.c
	$(CC) -S -o example1.s example1.c

example1_O1: example1_O1.s
	$(CC) -g -o example1_O1 example1_O1.s

example1_O1.s: example1.c
	$(CC) -O1 -S -o example1_O1.s example1.c

example2: example2.s
	$(CC) -g -o example2 example2.s

example2.s: example2.c
	$(CC) -S -o example2.s example2.c

example2_O1: example2_O1.s
	$(CC) -g -o example2_O1 example2_O1.s

example2_O1.s: example2.c
	$(CC) -O1 -S -o example2_O1.s example2.c

hello: hello.c
	$(CC) -z execstack -fno-stack-protector -o hello hello.c

shellcode.dumped: shellcode.o
	objdump -S shellcode.o > shellcode.dumped

shellcode.o: shellcode.s
	$(CC) -c shellcode.s

getsp: getsp.c
	$(CC) -o getsp getsp.c

clean:
	rm -f example1*.s example1 example2*.s example2 shellcode.dumped shellcode.o shellcode.dumped hello getsp