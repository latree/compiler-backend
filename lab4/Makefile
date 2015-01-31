# Makefile for CS322 Lab4.
#
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

gen: 	ast0psr SC0Gen.class

ast0:	ast0/Ast0.class

ast0psr: ast0/ast0Parser.class

clean:
	'rm' ast0/*.class *.class


