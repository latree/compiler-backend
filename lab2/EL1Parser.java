/* Generated By:JavaCC: Do not edit this line. EL1Parser.java */
import java.io.*;

public class EL1Parser implements EL1ParserConstants {
  public static void main(String [] args) throws Exception {
    if (args.length == 1) {
        FileInputStream stream = new FileInputStream(args[0]);
        EL1.Exp prog = new EL1Parser(stream).Program();
        stream.close();
        System.out.println(prog);
    } else {
        System.out.println("Need a file name as command-line argument.");
    }
  }

// Program -> Exp
//
  static final public EL1.Exp Program() throws ParseException {
  EL1.Exp e;
    e = Exp(null);
    jj_consume_token(0);
                      {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

// Exp -> fn var => Exp
//     |  Factor [+ Exp]
//
  static final public EL1.Exp Exp(EL1.Exp e0) throws ParseException {
  EL1.Exp e;
  EL1.Var var;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 10:
      jj_consume_token(10);
      var = Var();
      jj_consume_token(15);
      e = Exp(null);
                                      e = new EL1.Func(var,e);
      break;
    case 7:
    case Num:
    case Var:
    case 18:
      e = Factor(null);
                     if (e0 != null) e = new EL1.Plus(e0,e);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 16:
        jj_consume_token(16);
        e = Exp(e);
        break;
      default:
        jj_la1[0] = jj_gen;
        ;
      }
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

// Factor -> Base [Factor]
//
  static final public EL1.Exp Factor(EL1.Exp e0) throws ParseException {
  EL1.Exp e;
  EL1.Var var;
    e = Base();
             if (e0 != null) e = new EL1.Call(e0,e);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 7:
    case Num:
    case Var:
    case 18:
      e = Factor(e);
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

// Base -> let var = Exp in Exp end
//      |  ( Exp )
//      |  var
//      |  num
//
  static final public EL1.Exp Base() throws ParseException {
  EL1.Exp e, e1, e2;
  EL1.Var var;
  EL1.Num num;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 7:
      jj_consume_token(7);
      var = Var();
      jj_consume_token(17);
      e1 = Exp(null);
      jj_consume_token(8);
      e2 = Exp(null);
      jj_consume_token(9);
      e = new EL1.Let(var,e1,e2);
      break;
    case 18:
      jj_consume_token(18);
      e = Exp(null);
      jj_consume_token(19);
      break;
    case Var:
      e = Var();
      break;
    case Num:
      e = Num();
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  static final public EL1.Var Var() throws ParseException {
  Token t;
    t = jj_consume_token(Var);
            {if (true) return new EL1.Var(t.image);}
    throw new Error("Missing return statement in function");
  }

  static final public EL1.Num Num() throws ParseException {
  Token t;
    t = jj_consume_token(Num);
            {if (true) return new EL1.Num(Integer.parseInt(t.image));}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public EL1ParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x10000,0x46480,0x46080,0x46080,};
   }

  /** Constructor with InputStream. */
  public EL1Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public EL1Parser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new EL1ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public EL1Parser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new EL1ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public EL1Parser(EL1ParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(EL1ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[20];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 20; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
