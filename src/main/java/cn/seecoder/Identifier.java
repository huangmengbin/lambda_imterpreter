package cn.seecoder;

import java.util.ArrayList;
import java.util.Map;

public class Identifier extends AST {

    String name; //名字
    private String value;//没用的东西

    public Identifier(String n){
        name = n;
    }

    public boolean B_change(){
        return false;
    }

    protected void a_change(String oldString , String newString, boolean should_replace){
        if(name.equals(oldString)&&should_replace){
            name=newString;
        }
    }

    public String toString(){
            return value;
    }

    public String toString(int mode){
        if(mode==0){
            return value;
        }
        else {
            return name;
        }
    }

    public Lexer_Of_HMB toLexer(){
        ArrayList<String>arrayList=new ArrayList<>();
        arrayList.add(name);
        return new Lexer_Of_HMB(arrayList);
    }

    public void changeToSeecoder(Map<String,Integer> map){
        if (!map.containsKey(name)){
            value="free";//我也很绝望啊
        }
        else {
            value = Integer.valueOf(map.get(name)).toString();
        }
    }
}