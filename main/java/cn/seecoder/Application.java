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

        //顺序是可调的，因为真的不懂怎么搞

        if(!have_found && left_son.can_be_replace()){
            have_found=true;//find         //and
            left_son =((Application) left_son).B_replace();//B change
        }

        if(!have_found && !(left_son instanceof Identifier)){
            have_found=left_son.find_and_B_change();
        }


        if(!have_found && right_daughter.can_be_replace()){
            have_found=true;
            right_daughter =((Application) right_daughter).B_replace();
        }

        if (!have_found && !(right_daughter instanceof Identifier)){
            have_found=right_daughter.find_and_B_change();
        }






        return have_found;
    }

    protected AST B_replace(){//B替换第0步，
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
                Lexer_Of_HMB bodyLexer=((Abstraction) left_son).body.toLexer();
                ((Abstraction) left_son).body.B_replace1(lexer,bodyLexer,right_daughter.clone(),label);//进入了B替换第1步
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
                if( ! (right_daughter instanceof Identifier) ){
                    temp2.insert(0,"(");
                    temp2.append(")");
                }
                if(left_son instanceof Abstraction){
                    temp1.insert(0,"(");
                    temp1.append(")");
                }
                return temp1.toString()+" "+temp2.toString();

            case 2:
                return "("+ left_son.toString(mode)+" "+ right_daughter.toString(mode)+")";
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
    protected String node_toString(){
        StringBuilder result=new StringBuilder();
        for(int i=0;i<left_distance;i++)result.append(' ');
        result.append(symbol);
        for(int i=0;i<right_distance;i++)result.append(' ');
        return result.toString();
    }
    protected String node_toString(int mode){
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


    protected AST clone(){
        return new Application(left_son.clone(),right_daughter.clone());
    }
}