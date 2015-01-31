# Makefile for CS322 Lab3.
#
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

gen: 	ast0psr ir0 IR0Gen.class

ast0:	ast0/Ast0.class

ast0psr: ast0/ast0Parser.class

ir0:	ir0/IR0.class

clean:
	'rm' ast0/*.class ir0/*.class *.class


