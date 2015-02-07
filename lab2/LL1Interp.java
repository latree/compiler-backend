// This is supporting software for CS321/CS322 Compilers and Language Design.
// Copyright (c) Portland State University
// 
// An LL1 interpreter. (For CS322, Jingke Li)
//
//
import java.io.*;
import java.util.*;

public class LL1Interp {

  static enum OpCode { LOAD, STORE, MOVE, ADD, SUB, JUMP, JUMPZ, CALL, RETURN, HALT }
  static enum State  { RUNNING, HALTED, FAILED }

  static class Inst {
    public OpCode op;
    public int n;
    public Inst(OpCode op, int n) { this.op=op; this.n=n; }
    public Inst(OpCode op) { this.op=op; this.n=0; }
  }

  static final int CODESIZE=4096, MEMSIZE=4096;
  static Inst[] code = new Inst[CODESIZE];
  static int[] mem = new int[MEMSIZE];
  static int PC, ACC;
  static State status;
  static Stack<Integer> st = new Stack<Integer>();//*********************

  public static void main(String [] args) {
    PC = 0; ACC = 0; status = State.RUNNING;
    //load();

    //new test adding 1,2,3
    load1();
    
    // Instruction Semantics:
    // 	 LOAD n:  ACC <- mem[n]		 
    // 	 STORE n: mem[n] <- ACC		 
    // 	 MOVE n:  ACC <- n			 
    // 	 ADD n:   ACC <- ACC + mem[n]	 
    // 	 SUB n:   ACC <- ACC - mem[n]	 
    // 	 JUMP n:  PC <- n			 
    // 	 JUMPZ n: if ACC==0 then PC <- n  <<< new inst
    //	 CALL n:  save PC; PC <- n        <<< new inst
    //   RETURN:  PC <- saved caller's PC
    // 	 HALT:    stop execution               
    //
    do {
      Inst inst = code[PC++];
      int n = inst.n;
      switch (inst.op) {
      case LOAD:   ACC = mem[n]; break;
      case STORE:  mem[n] = ACC; break;
      case MOVE:   ACC = n; break;
      case ADD:    ACC += mem[n]; break;
      case SUB:    ACC -= mem[n]; break;
      case JUMP:   PC = n; break;
      case JUMPZ:  if (ACC==0) PC = n; break;
      case CALL:   st.push(PC); PC = n; break;	// ... need code ...
      case RETURN: PC = st.pop(); break;	// ... need code ...
      case HALT:   status = State.HALTED; break;
      default:     status = State.FAILED;
      }
    } while (status == State.RUNNING);

    if (status == State.HALTED)
      System.out.println(ACC);
    else
      System.out.println("Program failed");
  }

  // A test program for adding 1, 2, and 3.
  //   main() calls add(), which returns the 
  //   sum of 1, 2, and 3.
  //
  //     ; program starts here
  // 0.  CALL 2       ; call add()
  // 1.  HALT
  //     ; add function starts here
  // 2.  MOVE 1       ; load 1 to ACC
  // 3.  STORE 0      ; store 1 to mem[0]
  // 4.  MOVE 2       ; load 2 to ACC 
  // 5.  STORE 1      ; store 2 to mem[1]
  // 6.  MOVE 3       ; load 3 to ACC
  // 7.  ADD 0        ; ACC += mem[0] 
  // 8.  ADD 1        ; ACC += mem[1] 
  // 9.  RETURN       ; return (ret val in ACC)
  // 
  static void load() {
    code[0] = new Inst(OpCode.CALL, 2);
    code[1] = new Inst(OpCode.HALT);
    code[2] = new Inst(OpCode.MOVE, 1);
    code[3] = new Inst(OpCode.STORE, 0);
    code[4] = new Inst(OpCode.MOVE, 2);
    code[5] = new Inst(OpCode.STORE, 1);
    code[6] = new Inst(OpCode.MOVE, 3);
    code[7] = new Inst(OpCode.ADD, 0);
    code[8] = new Inst(OpCode.ADD, 1);
    code[9] = new Inst(OpCode.RETURN);
  }

  // A test program for adding 1, 2, and 3.
  // main() calls f() which calls g(i),
  // g(i) add 2 and 3 to i and returns the sum
  //
  // 0. CALL 2		; call f()
  // 1. HALT 
  //    ; f function starts here
  // 2. CALL 4		; call g()
  // 3. RETURN		; 
  // 4. MOVE 2		; load 2 to ACC
  // 5. STORE 0		; store 2 to men[0]
  // 6. MOVE 3 		; load 3 to ACC
  // 7. STORE 1		; store 3 to men[1]
  // 8. MOVE 1 		; load 1 to ACC
  // 9. ADD 0		; ACC += mem[0]
  // 10. ADD 1		; ACC += mem[1]
  // 11. RETURN
  static void load1(){
    code[0] = new Inst(OpCode.CALL, 2);
    code[1] = new Inst(OpCode.HALT);
    code[2] = new Inst(OpCode.CALL, 4);
    code[3] = new Inst(OpCode.RETURN);
    code[4] = new Inst(OpCode.MOVE, 3);
    code[5] = new Inst(OpCode.STORE, 0);
    code[6] = new Inst(OpCode.MOVE, 3);
    code[7] = new Inst(OpCode.STORE, 1);
    code[8] = new Inst(OpCode.MOVE, 1);
    code[9] = new Inst(OpCode.ADD, 0);
    code[10]= new Inst(OpCode.ADD, 1);
    code[11]= new Inst(OpCode.RETURN);
  }

}























