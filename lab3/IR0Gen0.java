// This is supporting software for CS321/CS322 Compilers and Language Design.
// Copyright (c) Portland State University
// 
// IR0 code generator. (For CS322 Lab3)
//
//
import java.util.*;
import java.io.*;
import ast0.*;
import ir0.*;

class IR0Gen {

  static class GenException extends Exception {
      public GenException(String msg) { super(msg); }
  }

  // For returning <src,code> pair from gen routines
  //
  static class CodePack {
    IR0.Src src;
    List<IR0.Inst> code;
    CodePack(IR0.Src src, List<IR0.Inst> code) { 
	this.src=src; this.code=code; 
    }
  } 

  // For returning <addr,code> pair from genAddr routines
  //
  static class AddrPack {
    IR0.Addr addr;
    List<IR0.Inst> code;
    AddrPack(IR0.Addr addr, List<IR0.Inst> code) { 
	this.addr=addr; this.code=code; 
    }
  }

  // The main routine
  //
  public static void main(String [] args) throws Exception {
    if (args.length == 1) {
      FileInputStream stream = new FileInputStream(args[0]);
      Ast0.Program p = new ast0Parser(stream).Program();
      stream.close();
      IR0.Program ir0 = IR0Gen.gen(p);
      System.out.print(ir0.toString());
    } else {
      System.out.println("You must provide an input file name.");
    }
  }

  // Ast0.Program ---
  // Ast0.Stmt[] stmts;
  //
  // AG:
  //   code: stmts.c  -- append all individual stmt.c
  //
  public static IR0.Program gen(Ast0.Program n) throws Exception {
    List<IR0.Inst> code = new ArrayList<IR0.Inst>();
    for (Ast0.Stmt s: n.stmts)
      code.addAll(gen(s));
    return new IR0.Program(code);
  }

  // STATEMENTS

  static List<IR0.Inst> gen(Ast0.Stmt n) throws Exception {
    if (n instanceof Ast0.Assign)      return gen((Ast0.Assign) n);
    else if (n instanceof Ast0.If)     return gen((Ast0.If) n);
    else if (n instanceof Ast0.While)  return gen((Ast0.While) n);
    else if (n instanceof Ast0.Print)  return gen((Ast0.Print) n);
    throw new GenException("Unknown Ast0 Stmt: " + n);
  }

  // Ast0.Assign ---
  // Ast0.Exp lhs, rhs;
  //
  // AG:
  //   code: rhs.c + lhs.c + ( lhs.l = rhs.v     # if lhs is id
  //                         | [lhs.l] = rhs.v ) # otherwise
  //
  static List<IR0.Inst> gen(Ast0.Assign n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	if (n.lhs instanceof Ast0.Id){
		CodePack rhs = gen(n.rhs);
		CodePack lhs = gen(n.lhs);
		code.addAll(rhs.code);
		code.addAll(lhs.code);
		code.add(new IR0.Move((IR0.Id ) lhs.src, rhs.src));
	}
	else{
		CodePack rhs = gen(n.rhs);
		AddrPack lhs = genAddr((Ast0.ArrayElm)n.lhs);
		code.addAll(rhs.code);
		code.addAll(lhs.code);
		code.add(new IR0.Store(lhs.addr, rhs.src));
		
	}
    	return code;
  }
  // Ast0.If ---
  // Ast0.Exp cond;
  // Ast0.Stmt s1, s2;
  //
  // AG:
  //   newLabel: L1[,L2]
  //   code: cond.c 
  //         + "if cond.v == false goto L1" 
  //         + s1.c 
  //         [+ "goto L2"] 
  //         + "L1:" 
  //         [+ s2.c]
  //         [+ "L2:"]
  //
  static List<IR0.Inst> gen(Ast0.If n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	IR0.Label l1 = new IR0.Label();
	CodePack cond = gen(n.cond);
	if (n.s2 != null){
		IR0.Label l2 = new IR0.Label();
		code.addAll(cond.code);
		code.add(new IR0.CJump(IR0.ROP.EQ, cond.src, IR0.FALSE, l1));
		code.addAll(gen(n.s1));
		code.add(new IR0.Jump(l2));
		code.add(new IR0.LabelDec(l1));
		code.addAll(gen(n.s2));
		code.add(new IR0.LabelDec(l2));
	}
	else{
		code.addAll(cond.code);
		code.add(new IR0.CJump(IR0.ROP.EQ, cond.src, IR0.FALSE, l1));
                code.addAll(gen(n.s1));
                code.add(new IR0.LabelDec(l1));
	}
    	return code;
  }

  // Ast0.While ---
  // Ast0.Exp cond;
  // Ast0.Stmt s;
  //
  // AG:
  //   newLabel: L1,L2
  //   code: "L1:" 
  //         + cond.c 
  //         + "if cond.v == false goto L2" 
  //         + s.c 
  //         + "goto L1" 
  //         + "L2:"
  //
  static List<IR0.Inst> gen(Ast0.While n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	IR0.Label l1 = new IR0.Label();
	IR0.Label l2 = new IR0.Label();
	CodePack cond = gen(n.cond);
	code.add(new IR0.LabelDec(l1));
	code.addAll(cond.code);
	code.add(new IR0.CJump(IR0.ROP.EQ, cond.src, IR0.FALSE, l2));
	code.addAll(gen(n.s));
	code.add(new IR0.Jump(l1));
        code.add(new IR0.LabelDec(l2));
    	return code;
  }
  
  // Ast0.Print ---
  // Ast0.Exp [arg];
  //
  // AG:
  //   code: arg.c 
  //         + "print (arg.v)" 
  //     or  "print ()"               if arg==null
  //
  static List<IR0.Inst> gen(Ast0.Print n) throws Exception {
    List<IR0.Inst> code = new ArrayList<IR0.Inst>();
     	if (n.arg==null){
   		code.add(new IR0.Print());
 	}
	else{
		CodePack cPack = gen(n.arg);
		if (cPack.code.isEmpty())
			code.add(new IR0.Print(cPack.src));
		else{
			code.addAll(cPack.code);
			code.add(new IR0.Print(cPack.src));
		}
	}
    return code;
  }

  // EXPRESSIONS

  static CodePack gen(Ast0.Exp n) throws Exception {
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
  // AG:
  //   newTemp: t
  //   code: e1.c + e2.c
  //         + "t = e1.v op e2.v"
  //
  static CodePack gen(Ast0.Binop n) throws Exception {
  	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	CodePack cp1 = gen(n.e1);
	CodePack cp2 = gen(n.e2);
	IR0.Temp t = new IR0.Temp();
	code.addAll(cp1.code);
	code.addAll(cp2.code);
	code.add(new IR0.Binop(gen(n.op), t, cp1.src, cp2.src));
	return new CodePack(t, code);
  }

  // Ast0.Unop ---
  // Ast0.UOP op;
  // Ast0.Exp e;
  //
  // AG:
  //   newTemp: t
  //   code: e.c + "t = op e.v"
  //
  static CodePack gen(Ast0.Unop n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	CodePack cp = gen(n.e);
	IR0.Temp t = new IR0.Temp();
	code.addAll(cp.code);
	code.add(new IR0.Unop(gen(n.op), t, cp.src));
	return new CodePack(t,code);
  }
  // Ast0.NewArray ---
  // int len;
  // 
  // AG:
  //   newTemp: t
  //   code: "t = malloc (len * 4)"
  //
  static CodePack gen(Ast0.NewArray n) throws Exception {
   	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	IR0.Temp t = new IR0.Temp();
	code.add(new IR0.Malloc(t, new IR0.IntLit(n.len*4)));
	return new CodePack(t, code);
  }

  // Ast0.ArrayElm ---
  // Ast0.Exp ar, idx;
  //
  // AG:
  //   newTemp: t1,t2,t3
  //   code: ar.c + idx.c
  //         + "t1 = idx.v * 4"
  //         + "t2 = ar.v + t1"
  //         + "t3 = [t2]"
  //
  static CodePack gen(Ast0.ArrayElm n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
    	IR0.Temp t1 = new IR0.Temp();
	IR0.Temp t2 = new IR0.Temp();
	IR0.Temp t3 = new IR0.Temp();
	CodePack ar = gen(n.ar);
	CodePack idx = gen(n.idx);
	code.addAll(ar.code);
	code.addAll(idx.code);
	code.add(new IR0.Binop(IR0.AOP.MUL, t1, idx.src, new IR0.IntLit(4)));
	code.add(new IR0.Binop(IR0.AOP.ADD, t2, ar.src, t1));
	code.add(new IR0.Load(t3, new IR0.Addr(t2)));
    	return new CodePack(t3, code);
  }

  static AddrPack genAddr(Ast0.ArrayElm n) throws Exception {
    	List<IR0.Inst> code = new ArrayList<IR0.Inst>();
	IR0.Addr addr;
	IR0.Temp t1 = new IR0.Temp();
        IR0.Temp t2 = new IR0.Temp();
	CodePack ar = gen(n.ar);
	CodePack idx = gen(n.idx);
	code.addAll(ar.code);
	code.addAll(idx.code);
	code.add(new IR0.Binop(IR0.AOP.MUL, t1, idx.src, new IR0.IntLit(4)));
        code.add(new IR0.Binop(IR0.AOP.ADD, t2, ar.src, t1));
	addr = new IR0.Addr(t2);
	return new AddrPack(addr, code);
		
  }

  // Ast0.Id ---
  // String nm;
  //
  static CodePack gen(Ast0.Id n) throws Exception {
  	return new CodePack(new IR0.Id(n.nm), new ArrayList<IR0.Inst>());
  }

  // Ast0.IntLit ---
  // int i;
  //
  static CodePack gen(Ast0.IntLit n) throws Exception {
	return new CodePack(new IR0.IntLit(n.i),new ArrayList<IR0.Inst>());
  }

  // Ast0.BoolLit ---
  // boolean b;
  //
  static CodePack gen(Ast0.BoolLit n) throws Exception {
 	return new CodePack(new IR0.BoolLit(n.b),new ArrayList<IR0.Inst>()); 
  }

  // OPERATORS

  static IR0.BOP gen(Ast0.BOP op) {
    IR0.BOP irOp = null;
    switch (op) {
    case ADD: irOp = IR0.AOP.ADD; break;
    case SUB: irOp = IR0.AOP.SUB; break;
    case MUL: irOp = IR0.AOP.MUL; break;
    case DIV: irOp = IR0.AOP.DIV; break;
    case AND: irOp = IR0.AOP.AND; break;
    case OR:  irOp = IR0.AOP.OR;  break;
    case EQ:  irOp = IR0.ROP.EQ;  break;
    case NE:  irOp = IR0.ROP.NE;  break;
    case LT:  irOp = IR0.ROP.LT;  break;
    case LE:  irOp = IR0.ROP.LE;  break;
    case GT:  irOp = IR0.ROP.GT;  break;
    case GE:  irOp = IR0.ROP.GE;  break;
    }
    return irOp;
  }
  static IR0.UOP gen(Ast0.UOP op){
  	IR0.UOP irOp = null;
	switch(op){
		case NEG: irOp = IR0.UOP.NEG; break;
		case NOT: irOp = IR0.UOP.NOT; break;
	}
	return irOp;
  }
   
}
