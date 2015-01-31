//Bin Gao 
// A starting version of IR1 interpreter. (For CS322 W15 Assignment 1)
//
//
import java.util.*;
import java.io.*;
import ir1.*;

public class IR1Interp {

  static class IntException extends Exception {
    public IntException(String msg) { super(msg); }
  }

  //-----------------------------------------------------------------
  // Value representation
  //-----------------------------------------------------------------
  //
  abstract static class Val {}

  // Integer values
  //
  static class IntVal extends Val {
    int i;
    IntVal(int i) { this.i = i; }
    public String toString() { return "" + i; }
  }

  // Boolean values
  //
  static class BoolVal extends Val {
    boolean b;
    BoolVal(boolean b) { this.b = b; }
    public String toString() { return "" + b; }
  }

  // String values
  //
  static class StrVal extends Val {
    String s;
    StrVal(String s) { this.s = s; }
    public String toString() { return s; }
  }

  // A special "undefined" value
  //
  static class UndVal extends Val {
    public String toString() { return "UndVal"; }
  }

  //-----------------------------------------------------------------
  // Environment representation
  //-----------------------------------------------------------------
  //
  // Think of how to organize environments.
  // 
  // The following environments are shown in the lecture for use in 
  // an IR0 interpreter:
  //
  //   HashMap<String,Integer> labelMap;  // label table
  //   HashMap<Integer,Val> tempMap;	  // temp table
  //   HashMap<String,Val> varMap;	  // var table
  // 
  // For IR1, they need to be managed at per function level.
  // 

  //code needed..........
  //per function level environment
  static class funcEnv{
    HashMap<String,Integer> labelMap;  
    HashMap<Integer,Val> tempMap;
    HashMap<String,Val> varMap;
    
    funcEnv(HashMap<String,Integer> labelMap, 
            HashMap<Integer,Val> tempMap,
            HashMap<String,Val> varMap       ){
      this.labelMap = labelMap;
      this.tempMap = tempMap;
      this.varMap = varMap;
    }
    funcEnv(){
      this.labelMap = new HashMap<String, Integer>();
      this.tempMap = new HashMap<Integer, Val>();
      this.varMap = new HashMap<String,Val>();
    }

    public void rmAll(){
      this.labelMap.clear();
      this.tempMap.clear();
      this.varMap.clear();
    }
  }
  static funcEnv env = new funcEnv();
  static Stack<funcEnv> funcStack = new Stack<funcEnv>(); 


  //-----------------------------------------------------------------
  // Global variables and constants
  //-----------------------------------------------------------------
  //
  // These variables and constants are for your reference only.
  // You may decide to use all of them, some of these, or not at all.
  //

  // Function lookup table
  // - maps function names to their AST nodes
  //
  static HashMap<String, IR1.Func> funcMap; 	

  // Heap memory
  // - for handling 'malloc'ed data
  // - you need to define alloc and access methods for it
  //
  static ArrayList<Val> heap;		

  // Return value
  // - for passing return value from callee to caller
  //
  static Val retVal;

  // Execution status
  // - tells whether to continue with the nest inst, to jump to
  //   a new target inst, or to return to the caller
  //
  static final int CONTINUE = 0;
  static final int RETURN = -1;	

  // Argument Array
  // for temperary hold the arguments value for callee
  static ArrayList<Val> argTemp = new ArrayList<Val>();

  //-----------------------------------------------------------------
  // The main method
  //-----------------------------------------------------------------
  //
  // 1. Open an IR1 program file. 
  // 2. Call the IR1 AST parser to read in the program and 
  //    convert it to an AST (rooted at an IR1.Program node).
  // 3. Invoke the interpretation process on the root node.
  //
  public static void main(String [] args) throws Exception {
    if (args.length == 1) {
      FileInputStream stream = new FileInputStream(args[0]);
      IR1.Program p = new ir1Parser(stream).Program();
      stream.close();
      IR1Interp.execute(p);
    } else {
      System.out.println("You must provide an input file name.");
    }
  }

  //-----------------------------------------------------------------
  // Top-level IR nodes
  //-----------------------------------------------------------------
  //

  // Program ---
  //  Func[] funcs;
  //
  // 1. Establish the function lookup map
  // 2. Lookup 'main' in funcMap, and 
  // 3. start interpreting from main's AST node
  //
  public static void execute(IR1.Program n) throws Exception { 
    funcMap = new HashMap<String,IR1.Func>();
    heap = new ArrayList<Val>();
    retVal = new UndVal();
    for (IR1.Func f: n.funcs)
      funcMap.put(f.name, f);
    execute(funcMap.get("main"));
  }

  // Func ---
  //  String name;
  //  Var[] params;
  //  Var[] locals;
  //  Inst[] code;
  //
  // 1. Collect label decls information and store them in
  //    a label-lookup table for later use.
  // 2. Execute the fetch-and-execute loop.
  //
  static void execute(IR1.Func n) throws Exception { 
    //push an empty env contains label,var,temp,maps to the top of stack
    funcStack.push(env);
    //collect label decls
    for (int i =0; i<n.code.length;++i){
      if (n.code[i] instanceof IR1.LabelDec)
	funcStack.peek().labelMap.put(((IR1.LabelDec)n.code[i]).name, i);
    }
    //collect args decls
    if (!argTemp.isEmpty()){
      for (int j=0; j<n.params.length; ++j){
        funcStack.peek().varMap.put(n.params[j] ,argTemp.get(j));
      }
    }
    
    // The fetch-and-execute loop
    int idx = 0;
    while (idx < n.code.length) {
      int next = execute(n.code[idx]);
      if (next == CONTINUE)
	idx++; 
      else if (next == RETURN)
        break;
      else
	idx = next;
    }
  }

  // Dispatch execution to an individual Inst node.
  //
  static int execute(IR1.Inst n) throws Exception {
    if (n instanceof IR1.Binop)    return execute((IR1.Binop) n);
    if (n instanceof IR1.Unop) 	   return execute((IR1.Unop) n);
    if (n instanceof IR1.Move) 	   return execute((IR1.Move) n);
    if (n instanceof IR1.Load) 	   return execute((IR1.Load) n);
    if (n instanceof IR1.Store)    return execute((IR1.Store) n);
    if (n instanceof IR1.Jump) 	   return execute((IR1.Jump) n);
    if (n instanceof IR1.CJump)    return execute((IR1.CJump) n);
    if (n instanceof IR1.Call)     return execute((IR1.Call) n);
    if (n instanceof IR1.Return)   return execute((IR1.Return) n);
    if (n instanceof IR1.LabelDec) return CONTINUE;
    throw new IntException("Unknown Inst: " + n);
  }
/*
  //-----------------------------------------------------------------
  // Execution routines for individual Inst nodes
  //-----------------------------------------------------------------
  //
  // - Each execute() routine returns CONTINUE, RETURN, or a new idx 
  //   (target of jump).
  //
*/
  // Binop ---
  //  BOP op;
  //  Dest dst;
  //  Src src1, src2;
  //
  static int execute(IR1.Binop n) throws Exception {
    Val val1 = evaluate(n.src1);
    Val val2 = evaluate(n.src2);
    Val res;
    if (n.op == IR1.AOP.ADD){
      res = new IntVal( ((IntVal) val1).i + ((IntVal) val2).i );
    }
    else if (n.op == IR1.AOP.SUB){
      res = new IntVal( ((IntVal) val1).i - ((IntVal) val2).i );    
    }
    else if (n.op == IR1.AOP.MUL){
      res = new IntVal( ((IntVal) val1).i * ((IntVal) val2).i );   
    }
    else if (n.op == IR1.AOP.DIV){
      res = new IntVal( ((IntVal) val1).i / ((IntVal) val2).i );    
    }
    else if (n.op == IR1.AOP.AND){
      res = new BoolVal( ((BoolVal) val1).b && ((BoolVal) val2).b );
    }
    else if (n.op == IR1.AOP.OR){
      res = new BoolVal( ((BoolVal) val1).b || ((BoolVal) val2).b );
    }
    else if (n.op == IR1.ROP.EQ){
      res = new BoolVal( ((IntVal) val1).i == ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.NE){
      res = new BoolVal( ((IntVal) val1).i != ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.LT){
      res = new BoolVal( ((IntVal) val1).i < ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.LE){
      res = new BoolVal( ((IntVal) val1).i <= ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.GT){
      res = new BoolVal( ((IntVal) val1).i > ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.GE){
      res = new BoolVal( ((IntVal) val1).i >= ((IntVal) val2).i );
    }
    else
      res = new UndVal();
    if (n.dst instanceof IR1.Id)
      funcStack.peek().varMap.put( ((IR1.Id) n.dst).name, res );   
    else if (n.dst instanceof IR1.Temp)
      funcStack.peek().tempMap.put( ((IR1.Temp) n.dst).num, res );
    return CONTINUE;  
  }

  // Unop ---
  //  UOP op;
  //  Dest dst;
  //  Src src;
  //
  static int execute(IR1.Unop n) throws Exception {
    Val val = evaluate(n.src);
    Val res;
    if (n.op == IR1.UOP.NEG)
      res = new IntVal(-((IntVal) val).i);
    else if (n.op == IR1.UOP.NOT)
      res = new BoolVal(!((BoolVal) val).b);
    else{
      res = new UndVal();
      throw new IntException("Wrong op in Unop inst: " + n.op);
    }

    if (n.dst instanceof IR1.Id)
      funcStack.peek().varMap.put( ((IR1.Id) n.dst).name, res );
    else if (n.dst instanceof IR1.Temp)
      funcStack.peek().tempMap.put( ((IR1.Temp) n.dst).num, res );

    return CONTINUE;  
  }

  // Move ---
  //  Dest dst;
  //  Src src;
  //
  static int execute(IR1.Move n) throws Exception {
    if (n.dst instanceof IR1.Id)
      funcStack.peek().varMap.put(((IR1.Id) (n.dst)).name, evaluate(n.src));
    else
      funcStack.peek().tempMap.put(((IR1.Temp) (n.dst)).num, evaluate(n.src));
    return CONTINUE;
  }
  // Load ---  
  //  Dest dst;
  //  Addr addr;
  //
  static int execute(IR1.Load n) throws Exception {
      if (n.dst instanceof IR1.Temp)
        funcStack.peek().tempMap.put( ((IR1.Temp)n.dst).num , heap.get( ((IntVal) evaluate(n.addr)).i) );
      if (n.dst instanceof IR1.Id)
        funcStack.peek().varMap.put( ((IR1.Id)n.dst).name , heap.get( ((IntVal) evaluate(n.addr)).i) );
    return CONTINUE;
  }

  // Store ---  
  //  Addr addr;
  //  Src src;
  //
  static int execute(IR1.Store n) throws Exception {
    heap.add( ((IntVal) evaluate(n.addr)).i, evaluate(n.src));
    return CONTINUE;
  }

  // CJump ---
  //  ROP op;
  //  Src src1, src2;
  //  Label lab;
  //
  static int execute(IR1.CJump n) throws Exception {
    Val val1 = evaluate(n.src1);
    Val val2 = evaluate(n.src2);
    Val res;
    if (n.op == IR1.ROP.EQ){
      res = new BoolVal( ((IntVal) val1).i == ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.NE){
      res = new BoolVal( ((IntVal) val1).i != ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.LT){
      res = new BoolVal( ((IntVal) val1).i < ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.LE){
      res = new BoolVal( ((IntVal) val1).i <= ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.GT){
      res = new BoolVal( ((IntVal) val1).i > ((IntVal) val2).i );
    }
    else if (n.op == IR1.ROP.GE){
      res = new BoolVal( ((IntVal) val1).i >= ((IntVal) val2).i );
    }
    else
      res = new UndVal();

    if ( ((BoolVal)res).b == true )
      return funcStack.peek().labelMap.get(n.lab.name);
    else 
      return CONTINUE;
  }	
  // Jump ---
  //  Label lab;
  //
  static int execute(IR1.Jump n) throws Exception {
    return funcStack.peek().labelMap.get(n.lab.name);
  }	
  // Call ---
  //  String name;
  //  Src[] args;
  //  Dest rdst;
  //
  static int execute(IR1.Call n) throws Exception {
    // ... code needed ...
    if (n.name.equals("printStr") 
      || n.name.equals("printInt")
      || n.name.equals("printBool")){
      
      if (n.args.length == 0)
	System.out.println();
      for (int i =0; i<n.args.length; ++i){
	  System.out.println(evaluate((n.args)[i]));
      }
    }
    else if (n.name.equals("malloc")){
      if ( (n.args)[0] instanceof IR1.IntLit){
        int space =   ((IR1.IntLit) (n.args)[0]).i / 4;
	for (int i = 0; i < space; ++i)
          heap.add(new UndVal());
        if (n.rdst instanceof IR1.Temp){
          funcStack.peek().tempMap.put( ((IR1.Temp) n.rdst).num,
                                       new IntVal( (heap.size()-space) ));
        }
        else if (n.rdst instanceof IR1.Id){
          funcStack.peek().varMap.put( ((IR1.Id) n.rdst).name,
                                       new IntVal( (heap.size()-space) ));
        }
        else
          System.out.println("Malloc Des can only be Id or Temp");
      }
      else
	System.out.println("No indicated number of mem to create");  
      //for testing purpose
      //if (n.rdst instanceof IR1.Temp)
      //  System.out.println( funcStack.peek().tempMap.get( ((IR1.Temp) (n.rdst)).num ) );
      //else if (n.rdst instanceof IR1.Id)
      //  System.out.println( funcStack.peek().varMap.get( ((IR1.Id) (n.rdst)).name ) );
      //else 
      //  System.out.println("Malloc Des can only be Id or Temp");
      //System.out.println(heap);
    }
    else{  
    //user defined functions
    //1.push its new Env to funcStack
    //2.collect arguments info to its Env
    //execute(funcMap.get(n.name));
    //returen value if needed to "static Val retVal;"
      if (n.args.length == 0){
        //do nothing
      }
      else{
        for (int i =0; i<n.args.length; ++i){
          argTemp.add(i, evaluate(n.args[i]));
        }
      }
      funcEnv calleeEnv = new funcEnv();
      funcStack.push(calleeEnv);
      execute(funcMap.get(n.name));  
      if(n.rdst != null){
        if(n.rdst instanceof IR1.Temp)
          funcStack.peek().tempMap.put(((IR1.Temp) n.rdst).num, retVal);
        if(n.rdst instanceof IR1.Id)
          funcStack.peek().varMap.put(((IR1.Id) n.rdst).name, retVal);
      }
      //clear the retVal
      retVal = new UndVal();
    }
    return CONTINUE;
  }
 
  // Return ---  
  //  Src val;
  //
  static int execute(IR1.Return n) throws Exception {
    retVal = evaluate(n.val);
    env.rmAll();
    funcStack.pop();
    argTemp.clear();
    return RETURN;
  }
  //-----------------------------------------------------------------
  // Evaluatation routines for address
  //-----------------------------------------------------------------
  //
  // - Returns an integer (representing index to the heap memory).
  //
  // Address ---
  //  Src base;  
  //  int offset;
  //
  static Val evaluate(IR1.Addr n) throws Exception {
    if (n.offset == 0)
      return new IntVal( (((IntVal) evaluate(n.base)).i / 4) + (((IntVal) evaluate(n.base)).i % 4) );
    else
      return new IntVal( (((IntVal) evaluate(n.base)).i / 4) + (((IntVal) evaluate(n.base)).i % 4)  + n.offset/4 );
  }
  //-----------------------------------------------------------------
  // Evaluatation routines for operands
  //-----------------------------------------------------------------
  //
  // - Each evaluate() routine returns a Val object.
  //
  static Val evaluate(IR1.Src n) throws Exception {
    Val val;
      if (n instanceof IR1.Temp)    val = funcStack.peek().tempMap.get(((IR1.Temp) n).num);
      else if (n instanceof IR1.Id)      val = funcStack.peek().varMap.get(((IR1.Id) n).name);
      else if (n instanceof IR1.IntLit)  val = new IntVal(((IR1.IntLit) n).i);
      else if (n instanceof IR1.BoolLit) val = new BoolVal(((IR1.BoolLit) n).b);
      else if (n instanceof IR1.StrLit)  val = new StrVal(((IR1.StrLit) n).s);
      else 
        val = new UndVal();
    return val;
  }
/*
  static Val evaluate(IR1.Dest n) throws Exception {
    Val val;
    // if (n instanceof IR0.Temp) val = 
    // if (n instanceof IR0.Id)   val = 
    return val;
  }
*/
}
