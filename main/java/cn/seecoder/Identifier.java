package cn.seecoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Identifier extends AST {

    String name; //名字
    private String value;//没什么用的东西

    public Identifier(String n){
        name = n;
    }

    public boolean find_and_B_change(){
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
            return "free";
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
            value="free";//大概是这个意思吧
        }
        else {
            value = Integer.valueOf(map.get(name)).toString();//所以要怎么改？？
        }
    }


    //以下打印树
    protected String node_toString(){
        return " "+name+" ";
    }
    protected String node_toString(int mode){
        if(mode<0){
            return " "+name+"-";
        }
        else {
            return "-"+name+" ";
        }
    }

    int calculate_node_distance(){
        left_distance=1;
        right_distance=1;
        return this.name.length()+2;
    }

    protected Identifier clone(){
        return new Identifier(this.name);}
}