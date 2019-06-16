package cn.seecoder;

import java.util.*;

class Interpreter_getline extends Interpreter{

    Interpreter_getline(Parser p,int print_mode){
        myast = p.parse();
        step=0;
        this.print_mode=print_mode;
    }
    Interpreter_getline(){}

    AST evalAST(AST ast) {

        String string_inner;
        Scanner scanner=new Scanner(System.in);
        Abstraction result=new Abstraction(new Identifier("result"),ast);

        boolean pause=false;//垃圾代码
        do{
            if(!pause) {

                if(print_mode ==0){
                    result.changeToSeecoder(new HashMap<>());
                }
                System.out.println(result.body.toString(print_mode)+"\n");//打印

            }
           pause=false;

            if(step<=0) {
                System.out.print("What's your next thought?     ");
                string_inner = scanner.nextLine().toLowerCase().trim();
                switch (string_inner) {
                    case "":
                        break;
                    case"out":
                        return null;
                    case "seecoder":
                        System.out.println(result.body.toString(0));
                        pause=true;
                        break;
                    case "simplify":
                    case "simplified":
                        System.out.println(result.body.toString(1));
                        pause=true;
                        break;
                    case "medium":
                        System.out.println(result.body.toString(2));
                        pause=true;
                        break;
                    case "full":
                        System.out.println(result.body.toString(3));
                        pause=true;
                        break;
                    case "t":
                    case "tree":
                        result.body.print_tree();
                        //(\67.\67NB. 67  (67 67NB))(\67666.\67NB. 67666(  67666 67NB))
                        pause=true;
                        break;
                    case "total":
                        System.out.println("The total step is "+total);
                        pause=true;
                        break;
                    case "step":
                        System.out.print("step=");

                        int temp;
                        try {
                            temp = scanner.nextInt();
                        }
                        catch (Exception e){
                            temp=999999999;
                        }

                        scanner.nextLine();//太气了
                        if (temp > 0) {
                            step = temp;
                        }
                        else {
                            System.out.println("Wrong input\n");
                            pause=true;
                        }
                        break;

                    default:
                        System.out.println("unrecognized\n");
                        pause=true;
                }//end switch
            }//end if

           if(!pause) {
               total++;
               step--;
           }
        }while ( pause || result.find_and_B_change() );//利用了逻辑短路


        System.out.println("\nThe answer is:\n"+result.body.toString(print_mode)+"\n");//
        System.out.println("You can press Enter to return.");

       after_B_change(result.body,total);

        return result.body;
    }
}
