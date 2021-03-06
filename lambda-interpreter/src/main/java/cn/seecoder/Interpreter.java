package cn.seecoder;

import java.util.*;

//final

public class Interpreter {//final模式，如果不需要git到seecoder可以加一个接口
    AST myast;
    int print_mode ;
    int tree_mode;
    int step;
    int total=0;

    public Interpreter(Parser p){
        myast = p.parse();
        step=0;
        print_mode=0;
    }

    public Interpreter(Parser p,int print_mode,int tree_mode){
        myast = p.parse();
        step=0;
        this.print_mode=print_mode;
        this.tree_mode=tree_mode;
    }

    public Interpreter(){}


    AST eval(){//
        return evalAST(myast);
    }

    ///下面是真正的工作部分
    AST evalAST(AST ast) {

        Abstraction result=new Abstraction(new Identifier("result"),ast);//嘿嘿嘿
        System.out.println("Running...\n");

        while (  result.find_and_B_change() ){
            total++;
        }

        if(global.to_seecoder){//额。。。提交时还是要改一下哎
            Map<String,Integer>map=new HashMap<>();
            result.change_to_seecoder(map);
        }

        else {//额。。。提交时不能打印这个东东
            System.out.println("\nThe answer is:\n"+result.body.toString(print_mode));
            System.out.println("You can press Enter to return.");

            do_something_after_B_change(result.body,total,tree_mode);

        }
        return result.body;
    }

    static void do_something_after_B_change(AST ast, int total, int tree_mode){
        //完成规约之后所干的事
        String another_string;
        Scanner scanner=new Scanner(System.in);

        do{
            another_string=scanner.nextLine().toLowerCase().trim();
            switch (another_string){
                case "":
                case "out":
                    break;
                case "total":
                    System.out.println("The total step is "+(total-1));
                    break;
                case "t":
                case "tree":
                    ast.print_tree(tree_mode);
                    break;
                case "67":
                case "seecoder":
                    System.out.println(ast.toString(0));
                    break;
                case "s":
                case "simplify":
                case "simplified":
                    System.out.println(ast.toString(1));
                    break;
                case "m":
                case "medium":
                    System.out.println(ast.toString(2));
                    break;
                case "f":
                case "full":
                    System.out.println(ast.toString(3));
                    break;
                default:
                    System.out.println("unrecognized");
            }
        }while (!another_string.equals(""));
        System.out.println("\n");
    }

}