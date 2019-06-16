package cn.seecoder;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

abstract class Function {


    static void func (String function_place){
        System.out.print("function: ");
        Scanner in=new Scanner(System.in);
        String string = in.nextLine().trim().toLowerCase();

        switch (string){
            case "init":
                key_value=init_key_value;
                System.out.println("Success!");
                write(function_place);
                break;
            case "add":
            case "define":
                define();
                write(function_place);
                break;
            case "check":
                print();
                break;
            case "delete":
            case "del":
                delete();
                write(function_place);
                break;
        }

    }

    static String replace(String source){
        for(int i= key_value.size()-2;i>=0;i-=2){
            source=source.replace(key_value.get(i),key_value.get(i+1));
        }
        return source;
    }

    static void read(String function_place){
        try {
            File file=new File(function_place);
            Scanner in=new Scanner(file);
            String[] all = in.nextLine().split(",");
            Collections.addAll(key_value,all);
        }catch (Exception e){
            e.printStackTrace();
            key_value=init_key_value;
            System.out.println("尝试读取function文件时发生错误\n已恢复至默认模式");
        }
    }
    static void write(String function_place){
        try {
            FileWriter writer=new FileWriter(function_place);
            StringBuilder result =new StringBuilder();
            for(String i:key_value){
                result.append(i);
                result.append(",");
            }
            writer.write(result.toString());
            System.out.println("Function file saved！");
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("尝试写function文件失败");
        }
    }


    static private void define(){
        Scanner in = new Scanner(System.in);
        System.out.print("number=");
        int number1;
        try {
            number1 = in.nextInt();
            if(number1>key_value.size()/2-1){
                number1=key_value.size()/2-1;
            }
        }
        catch (Exception e){
            number1=key_value.size()/2-1;
        }
        in.nextLine();
        System.out.print("Your function name is: ");
        String function_name;
        do {
            function_name = in.nextLine().trim();
        }while (function_name.isEmpty());
        System.out.print("Your function define is: ");
        String function_define;
        do {
            function_define = in.nextLine().trim();
        }while (function_define.isEmpty());
        key_value.add(number1*2+2,function_define);
        key_value.add(number1*2+2,function_name);
        System.out.println("Success!");
    }

    static private void print(){
        for (int i=0;i<key_value.size();i+=2){
            System.out.println((i/2)+":\t"+
                    key_value.get(i)+"\t\t"+
                    key_value.get(i+1));
        }
    }

    static private void delete(){
        Scanner in = new Scanner(System.in);
        System.out.print("number=");
        int number2=in.nextInt();
        key_value.remove(number2*2);
        key_value.remove(number2*2);
        System.out.println("Success!");
    }

    private static ArrayList<String> init_key_value= new ArrayList<>(
            Arrays.asList(
                    "ZERO", "(\\f.(\\x.x))",
                    "ONE", "(\\f.(\\x.(f x)))",
                    "TWO", "(\\f.(\\x.(f(f x))))",
                    "THREE", "(\\f.(\\x.(f(f(f x)))))",
                    "FOUR", "(\\f.(\\x.(f(f(f(f x))))))",
                    "FIVE", "(\\f.(\\x.(f(f(f(f(f x)))))))",
                    "SIX", "(\\f.(\\x.(f(f(f(f(f(f x))))))))",
                    "SIXSEVEN", "(67_NB)",
                    "SEVEN", "(\\f.(\\x.(f(f(f(f(f(f(f x)))))))))",
                    "EIGHT", "(\\f.(\\x.(f(f(f(f(f(f(f(f x))))))))))",
                    "NINE", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f x)))))))))))",
                    "TEN", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f x))))))))))))",
                    "ELEVEN", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f(f x)))))))))))))",
                    "TWELVE", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f(f(f x))))))))))))))",
                    "SUCC", "(\\n.(\\f.(\\x.(f((n f)x)))))",
                    "PLUS", "(\\m.(\\n.((mSUCC)n)))",
                    "MULT", "(\\m.(\\n.(\\f.(m(n f)))))",
                    "POW", "(\\b.(\\e.(e b)))",
                    "PRED", "(\\n.(\\f.(\\x.(((n(\\g.(\\h.(h(g f)))))(\\u.x))(\\v.v)))))",
                    "SUB", "(\\m.(\\n.((nPRED)m)))",
                    "TRUE", "(\\u.(\\v.u))",
                    "FALSE", "(\\u.(\\v.v))",
                    "AND", "(\\p.(\\q.((p q)p)))",
                    "OR", "(\\p.(\\q.((p p)q)))",
                    "NOT", "(\\p.(\\a.(\\b.((p b)a))))",
                    "IF", "(\\p.(\\a.(\\b.((p a)b))))",
                    "ISZERO", "(\\h.((h(\\t.FALSE))TRUE))",
                    "LEQ", "(\\m.(\\n.(ISZERO((SUBm)n))))",
                    "EQ", "(\\m.(\\n.((AND((LEQm)n))((LEQn)m))))",
                    "LEQ", "(\\m.(\\n.(ISZERO((SUBm)n))))",
                    "FACT1", "(\\a.(\\b.((((b(\\t.(\\d.(\\e.e))))(\\i.(\\j.i)))(\\k.(\\l.(k l))))(\\p.(b(((a a)(\\r.(\\s.(((b(\\g.(\\w.(w(g r)))))(\\y.s))(\\z.z)))))p))))))",
                    "FACT", "(FACT1FACT1)",
                    "FACT1", "(\\f.(\\n.(((ISZEROn)ONE)((MULTn)((f f)(PREDn))))))",
                    "MAX", "(\\x.(\\y.(((IF((LEQx)y))y)x)))",
                    "MIN", "(\\x.(\\y.(((IF((LEQx)y))x)y)))",
                    "CDR", "(\\p.(pFALSE))",
                    "CAR", "(\\p.(pTRUE))",
                    "CONS", "(\\x.(\\y.(\\f.((f x)y))))",
                    "NULL", "(\\p.(p(\\x.(\\y.FALSE))))",
                    "NIL", "(\\x.TRUE)"     ));

    private static ArrayList<String> key_value=new ArrayList<>();
}


