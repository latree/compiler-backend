// This is supporting software for CS321/CS322 Compilers and Language Design.
// Copyright (c) Portland State University
// 
// SC0 code generator. (For CS322 Lab4)
//
//
import java.util.*;
import java.io.*;
import ast0.*;

class SC0Gen {

  static class GenException extends Exception {
    public GenException(String msg) { super(msg); }
  }

  // The var array
  //
  static ArrayList<String> vars = new ArrayList<String>();

  // The main routine
  //
  public static void main(String [] args) throws Exception {
    if (args.length == 1) {
      FileInputStream stream = new FileInputStream(args[0]);
      Ast0.Program p = new ast0.ast0Parser(stream).Program();
      stream.close();
      List<String> code = gen(p);
      String[] insts = code.toArray(new String[0]);
      int i = 0;
      System.out.print("# Stack Code (SC0)\n\n");
      for (String inst: insts) {
	System.out.print(i++ + ". " + inst + "\n");
      }
    } else {
      System.out.println("You must provide an input file name.");
    }
  }

  // Ast0.Program ---
  // Ast0.Stmt[] stmts;
  //
  static List<String> gen(Ast0.Program n) throws Exception {
    ArrayList<String> code = new ArrayList<String>();
    for (Ast0.Stmt s: n.stmts)
      code.addAll(gen(s));
    return code;
  }

  // STATEMENTS

  static List<String> gen(Ast0.Stmt n) throws Exception {
    if (n instanceof Ast0.Assign)      return gen((Ast0.Assign) n);
    else if (n instanceof Ast0.If)     return gen((Ast0.If) n);
    else if (n instanceof Ast0.While)  return gen((Ast0.While) n);
    else if (n instanceof Ast0.Print)  return gen((Ast0.Print) n);
    throw new GenException("Unknown Ast0 Stmt: " + n);
  }

  // Ast0.Assign ---
  // Ast0.Exp lhs, rhs;
  //
  static List<String> gen(Ast0.Assign n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.If ---
  // Ast0.Exp cond;
  // Ast0.Stmt s1, s2;
  //
  static List<String> gen(Ast0.If n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.While ---
  // Ast0.Exp cond;
  // Ast0.Stmt s;
  //
  static List<String> gen(Ast0.While n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }
  
  // Ast0.Print ---
  // Ast0.PrArg arg;
  //
  static List<String> gen(Ast0.Print n) throws Exception {
    List<String> code = gen((Ast0.Exp) n.arg);
    code.add("PRINT");
    return code;
  }

  // EXPRESSIONS

  static List<String> gen(Ast0.Exp n) throws Exception {
    if (n instanceof Ast0.Binop)    return gen((Ast0.Binop) n);
    if (n instanceof Ast0.Unop)     return gen((Ast0.Unop) n);
    if (n instanceof Ast0.NewArray) return gen((Ast0.NewArray) n);
    if (n instanceof Ast0.ArrayElm) return gen((Ast0.ArrayElm) n);
    if (n instanceof Ast0.Id)	    return gen((Ast0.Id) n);
    if (n instanceof Ast0.IntLit)   return gen((Ast0.IntLit) n);
    if (n instanceof Ast0.BoolLit)  return gen((Ast0.BoolLit) n);
    throw new GenException("Unknown Exp node: " + n);
  }

  // Ast0.Binop ---
  // Ast0.BOP op;
  // Ast0.Exp e1,e2;
  //
  static List<String> gen(Ast0.Binop n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.Unop ---
  // Ast0.UOP op;
  // Ast0.Exp e;
  //
  static List<String> gen(Ast0.Unop n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }
  
  // Ast0.NewArray ---
  // int len;
  // 
  static List<String> gen(Ast0.NewArray n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.ArrayElm ---
  // Ast0.Exp ar, idx;
  //
  static List<String> gen(Ast0.ArrayElm n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }
  
  // Ast0.Id ---
  // String nm;
  //
  static List<String> gen(Ast0.Id n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.IntLit ---
  // int i;
  //
  static List<String> gen(Ast0.IntLit n) throws Exception {
    List<String> code;

    // ... node code ...

    return code;
  }

  // Ast0.BoolLit ---
  // boolean b;
  //
  static List<String> gen(Ast0.BoolLit n) {
    List<String> code = new ArrayList<String>();
    code.add("CONST " + (n.b ? 1 : 0));
    return code;
  }

  // OPERATORS

  static String gen(Ast0.BOP op) {
    switch (op) {
    case ADD: return "ADD";
    case SUB: return "SUB";
    case MUL: return "MUL";
    case DIV: return "DIV";
    case AND: return "AND";
    case OR:  return "OR";
    case EQ:  return "IFEQ";
    case NE:  return "IFNE"; 
    case LT:  return "IFLT"; 
    case LE:  return "IFLE"; 
    case GT:  return "IFGT"; 
    case GE:  return "IFGE"; 
    }
    return null;
  }
   
}
