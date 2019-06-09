package cn.seecoder;

import java.util.*;

//final

public class Interpreter {//final
    AST myast;
    int print_mode ;
    int step;
    int total=0;

    public Interpreter(Parser p){
        myast = p.parse();
        step=0;
        print_mode=0;
    }
    public Interpreter(Parser p,int print_mode){
        myast = p.parse();
        step=0;
        this.print_mode=print_mode;
    }

    public Interpreter(){}


    AST eval(){
        if(global.to_seecoder){
            myast.changeToSeecoder(new HashMap<String, Integer>());
        }
        return evalAST(myast);
    }

    AST evalAST(AST ast) {
        Abstraction result=new Abstraction(new Identifier("result"),ast);
        System.out.println("Running...\n");
        while (  result.B_change() ){
            total++;
        }

        if(print_mode ==0){
            Map<String,Integer>map=new HashMap<>();
            result.changeToSeecoder(map);
        }

        if(!global.to_seecoder){//额。。。提交时不能打印这个东东
            System.out.println("\nThe answer is:\n"+result.body.toString(print_mode));
            System.out.println("You can press Enter to return.");
            String another_string;
            Scanner scanner=new Scanner(System.in);
            do{
                another_string=scanner.nextLine().toLowerCase().trim();
                switch (another_string){
                    case "":
                        break;
                    case "total":
                        System.out.println("The total step is "+(total-1));
                        break;
                    case "seecoder":
                        result.body.changeToSeecoder(new HashMap<>());
                        System.out.println(result.body.toString(0));
                        break;
                    case "simplify":
                    case "simplified":
                        System.out.println(result.body.toString(1));
                        break;
                    case "medium":
                        System.out.println(result.body.toString(2));
                        break;
                    case "full":
                        System.out.println(result.body.toString(3));
                        break;
                    default:
                        System.out.println("unrecognized");
                }
            }while (!another_string.equals(""));
            System.out.println("\n");
        }

        return result.body;
    }

}