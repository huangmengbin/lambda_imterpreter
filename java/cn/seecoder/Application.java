package cn.seecoder;

import java.util.HashMap;
import java.util.Map;

public class Application extends AST{
    AST left_son;//左树
    AST right_daughter;//右树

    Application(AST left, AST right){
        left_son = left;
        right_daughter = right;
    }

    public boolean find_and_B_change(){

        boolean have_found=false;

        if(left_son.can_be_replace()){
            have_found=true;
            left_son =((Application) left_son).B_replace();
        }

        if((left_son instanceof Abstraction || left_son instanceof Application )&&!have_found){
            if( left_son.find_and_B_change()){
                have_found=true;
            }
        }

        if(right_daughter.can_be_replace()&&!have_found){
            have_found=true;
            right_daughter =((Application) right_daughter).B_replace();
        }

        if ((right_daughter instanceof Abstraction || right_daughter instanceof Application)&&!have_found ){
            if( right_daughter.find_and_B_change()){
                have_found=true;
            }
        }

        return have_found;
    }

    public AST B_replace(){//B替换第0步，
        if(! (left_son instanceof Abstraction) ) {
            System.out.println("B规约出现bug");
            return this;
        }

            String label =((Abstraction) left_son).param.name;

            if(((Abstraction) left_son).body instanceof Identifier){
                if(((Identifier) ((Abstraction) left_son).body).name.equals(label)){
                    return right_daughter;
                }
                else {
                    return ((Abstraction) left_son).body;
                }
            }

            else {
                Lexer_Of_HMB lexer = right_daughter.toLexer();
                Parser p=new Parser(lexer);
                Lexer_Of_HMB bodyLexer=((Abstraction) left_son).body.toLexer();
                ((Abstraction) left_son).body.B_replace1(lexer,bodyLexer,p,label);//进入了B替换第1步
                return ((Abstraction) left_son).body;
            }


    }

    protected void a_change(String oldString , String newString, boolean should_replace){
        left_son.a_change(oldString,newString,should_replace);
        right_daughter.a_change(oldString,newString,should_replace);
    }

    public String toString(){
        return "("+ left_son.toString()+" "+ right_daughter.toString()+")";
    }

    public String toString(int mode){
        switch (mode){
            case 1:
                StringBuilder temp1=new StringBuilder(left_son.toString(mode));
                StringBuilder temp2=new StringBuilder(right_daughter.toString(mode));
                if(right_daughter instanceof Application || right_daughter instanceof Abstraction){
                    temp2.insert(0,"(");
                    temp2.append(")");
                }
                if(left_son instanceof Abstraction){
                    temp1.insert(0,"(");
                    temp1.append(")");
                }
                return temp1.toString()+" "+temp2.toString();

            case 2:
            case 3:
                return "("+ left_son.toString(mode)+" "+ right_daughter.toString(mode)+")";
            default:
                this.changeToSeecoder(new HashMap<>());
                return this.toString();
        }
    }

    public Lexer_Of_HMB toLexer(){
        Lexer_Of_HMB a= left_son.toLexer();
        Lexer_Of_HMB b= right_daughter.toLexer();
        a.connect(b);
        a.add_a_parenthesis();
        return a;
    }

    public void changeToSeecoder(Map<String,Integer> map){
        left_son.changeToSeecoder(map);
        right_daughter.changeToSeecoder(map);
    }


    //以下打印树,可移植性很低
    private final static String symbol="[APP]";
    protected String node(){
        StringBuilder result=new StringBuilder();
        for(int i=0;i<left_distance;i++)result.append(' ');
        result.append(symbol);
        for(int i=0;i<right_distance;i++)result.append(' ');
        return result.toString();
    }
    protected String node(int mode){
        StringBuilder result=new StringBuilder();
        if(mode<0) {
            for (int i = 0; i < left_distance; i++) result.append(' ');
            result.append(symbol);
            for (int i = 0; i < right_distance; i++) result.append('-');
        }
        if(mode>0) {
            for (int i = 0; i < left_distance; i++) result.append('-');
            result.append(symbol);
            for (int i = 0; i < right_distance; i++) result.append(' ');
        }
        return result.toString();
    }

    int calculate_node_distance(){
        left_distance=left_son.calculate_node_distance();
        right_distance=right_daughter.calculate_node_distance();
        return left_distance+right_distance+symbol.length();//长度
    }
}