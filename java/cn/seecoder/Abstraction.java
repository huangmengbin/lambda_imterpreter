package cn.seecoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Abstraction extends AST {
    Identifier param;//变量
    AST body;//表达式

    Abstraction(Identifier p, AST b){
        param = p;
        body = b;
    }

    public boolean find_and_B_change(){
        boolean have_found=false;
        if (body.can_be_replace()){
            have_found=true;
            body=((Application)body).B_replace();
        }

        if(!have_found ){
            if(body.find_and_B_change()){
                have_found=true;
            }
        }

        return have_found;
    }

    protected void a_change(String oldString , String newString, boolean should_replace){
        if(   param.name.equals(oldString)  ){
            param.name=newString;
            body.a_change(oldString,newString,true);
        }
        body.a_change(oldString,newString,should_replace);
    }

    public String toString(){
            return "\\."+body.toString();
    }

    public String toString(int mode){
        switch (mode){
            case 1:
                if(body instanceof Application){
                    return "\\"+param.toString(mode)+".("+body.toString(mode)+")";
                }
            case 2:
                return "\\"+param.toString(mode)+"."+body.toString(mode);      //不太完整的括号
            case 3:
                return "(\\"+param.toString(mode)+"."+body.toString(mode)+")";//完完整整的括号
            default:
                this.changeToSeecoder(new HashMap<>());
                return "\\."+body.toString();
        }
    }

    public Lexer_Of_HMB toLexer(){
        ArrayList<String>arrayList=new ArrayList<>();
        arrayList.add("\\");
        arrayList.add(param.name);
        arrayList.add(".");
        Lexer_Of_HMB lexer=new Lexer_Of_HMB(arrayList);
        lexer.connect(body.toLexer());
        lexer.add_a_parenthesis();
        return lexer;
    }

    public void changeToSeecoder(Map<String,Integer> map){
        Map<String,Integer>map1=new HashMap<>();
        for (String key :map.keySet()) {
            map1.put(key,map.get(key)+1);
        }
        map1.put(param.name,0);
        body.changeToSeecoder(map1);
    }


    //以下打印树
    private final static String symbol="[Abs]";
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
        left_distance=param.calculate_node_distance();
        right_distance=body.calculate_node_distance();
        return left_distance+right_distance+symbol.length();//"abs"的长度
    }
}