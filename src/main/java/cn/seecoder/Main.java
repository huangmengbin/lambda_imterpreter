package cn.seecoder;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        global.to_seecoder =false;

        String mode_file_place="mode";
        String function_place="function";

        int mode;
        int run_mode;
        int print_mode;
        int tree_mode;

        try {
            File file=new File(mode_file_place);
            Scanner file_inner=new Scanner(file);
            mode=file_inner.nextInt();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("尝试读取mode文件时发生错误\n已恢复至默认模式");
            mode=6;
        }//其实这个最好写成 Setting 的成员函数

        print_mode =  mode     & 3;
        run_mode   = (mode>>2) & 3;
        tree_mode= (mode &16) >>4;

        Function.read(function_place);
//-----------------------------------------分割线------------------------------
        String source;
        Scanner in = new Scanner(System.in);

        while(true){

            try {

                System.out.print("Lambda-> ");

                source = in.nextLine().trim();

                if (source.equalsIgnoreCase("set")) {
                    mode = Setting.set(mode, mode_file_place);
                    print_mode = mode & 3;
                    run_mode = (mode >> 2) & 3;
                    tree_mode = (mode & 16) >> 4;
                } else if (source.equalsIgnoreCase("function") || source.equals("func")) {
                    Function.func(function_place);
                } else if (source.equals("help")) {
                    System.out.println(global.help);
                } else if (source.equals("tip") || source.equals("tips")) {
                    global.print_tip();
                } else if (source.equalsIgnoreCase("quit") || source.equals("out")) {
                    break;
                } else if (source.isEmpty()) {
                    continue;
                } else {//这时候认为它就是lambda表达式,开始工作了
                    try {
                        CheckLegal.checklegal(source);
                        source = Function.replace(source);
                        Lexer_Of_HMB lexer_of_hmb = new Lexer_Of_HMB(source);
                        Parser parser = new Parser(lexer_of_hmb);
                        Interpreter interpreter;
                        switch (run_mode) {
                            //给seecoder的是 final + seecoder，即mode=3
                            case 0:
                                interpreter = new Interpreter(parser, print_mode, tree_mode);
                                break;//final
                            case 1:
                                interpreter = new Interpreter_process(parser, print_mode, tree_mode);
                                break;
                            default:
                                interpreter = new Interpreter_getline(parser, print_mode, tree_mode);
                                break;
                        }
                        interpreter.eval();
                    } catch (Exception e) {
                        System.out.println("运行时错误");
                        e.printStackTrace();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Function.write(function_place);

        System.out.print("\nThanks for your using.\n");



/**
        for(int i=6;i<=31; i++) {
            String source = sources[i];
            Lexer_Of_HMB lexer_of_hmb = new Lexer_Of_HMB(source);
            Parser parser = new Parser(lexer_of_hmb);
            Interpreter interpreter;
            switch (run_mode) {
                case 0:interpreter = new Interpreter(parser);break;//默认为0，给seecoder
                case 1:interpreter = new Interpreter_process(parser, print_mode);break;
                default:interpreter = new Interpreter_getline(parser, print_mode);break;
            }
            System.out.println("初步的树:"+interpreter.myast.toString(print_mode)+"\n");
            AST result = interpreter.eval();
            System.out.println(i+". 最终:" + result.toString(print_mode)+"\n\n\n");

        }
*/
    }

//------------------------------------完了---------------------------------

    static String ZERO = "(\\f.\\x.x)";
    static String SUCC = "(\\n.\\f.\\x.f (n f x))";
    static String ONE = app(SUCC, ZERO);
    static String TWO = app(SUCC, ONE);
    static String THREE = app(SUCC, TWO);
    static String FOUR = app(SUCC, THREE);
    static String FIVE = app(SUCC, FOUR);
    static String PLUS = "(\\m.\\n.((m "+SUCC+") n))";
    static String POW = "(\\b.\\e.e b)";       // POW not ready
    static String PRED = "(\\n.\\f.\\x.n(\\g.\\h.h(g f))(\\u.x)(\\u.u))";
    static String SUB = "(\\m.\\n.n"+PRED+"m)";
    static String TRUE = "(\\x.\\y.x)";
    static String FALSE = "(\\x.\\y.y)";
    static String AND = "(\\p.\\q.p q p)";
    static String OR = "(\\p.\\q.p p q)";
    static String NOT = "(\\p.\\a.\\b.p b a)";
    static String IF = "(\\p.\\a.\\b.p a b)";
    static String ISZERO = "(\\n.n(\\x."+FALSE+")"+TRUE+")";
    static String LEQ = "(\\m.\\n."+ISZERO+"("+SUB+"m n))";
    static String EQ = "(\\m.\\n."+AND+"("+LEQ+"m n)("+LEQ+"n m))";
    static String MAX = "(\\m.\\n."+IF+"("+LEQ+" m n)n m)";
    static String MIN = "(\\m.\\n."+IF+"("+LEQ+" m n)m n)";

    private static String app(String func, String x){
        return "(" + func + x + ")";
    }
    private static String app(String func, String x, String y){
        return "(" +  "(" + func + x +")"+ y + ")";
    }
    private static String app(String func, String cond, String x, String y){
        return "(" + func + cond + x + y + ")";
    }


//-----------------------------------------------------------------------------------------
    private static String[] sources = {
            ZERO,//0
            ONE,//1
            TWO,//2
            THREE,//3
            app(PLUS, ZERO, ONE),//4
            app(PLUS, TWO, THREE),//5
            app(POW, TWO, TWO),//6
            app(PRED, ONE),//7
            app(PRED, TWO),//8
            app(SUB, FOUR, TWO),//9
            app(AND, TRUE, TRUE),//10
            app(AND, TRUE, FALSE),//11
            app(AND, FALSE, FALSE),//12
            app(OR, TRUE, TRUE),//13
            app(OR, TRUE, FALSE),//14
            app(OR, FALSE, FALSE),//15
            app(NOT, TRUE),//16
            app(NOT, FALSE),//17
            app(IF, TRUE, TRUE, FALSE),//18
            app(IF, FALSE, TRUE, FALSE),//19
            app(IF, app(OR, TRUE, FALSE), ONE, ZERO),//20
            app(IF, app(AND, TRUE, FALSE), FOUR, THREE),//21
            app(ISZERO, ZERO),//22
            app(ISZERO, ONE),//23
            app(LEQ, THREE, TWO),//24
            app(LEQ, TWO, THREE),//25
            app(EQ, TWO, FOUR),//26
            app(EQ, FIVE, FIVE),//27
            app(MAX, ONE, TWO),//28
            app(MAX, FOUR, TWO),//29
            app(MIN, ONE, TWO),//30
            app(MIN, FOUR, TWO),//31
            "((\\x.x)b)"
     };

}