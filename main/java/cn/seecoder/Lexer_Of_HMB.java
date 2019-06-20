package cn.seecoder;

import java.util.*;
import java.lang.*;

public class Lexer_Of_HMB {

    static final String left_parenthesis = global.left_parenthesis;
    static final String right_parenthesis = global.right_parenthesis;
    static final String lambda = global.lambda;
    static final String dot = global.dot;

    int index=0;
    protected ArrayList<String> my_token = new ArrayList<>();

    Lexer_Of_HMB(String source) {
        this.init(source.trim());
        try {
            CheckLegal.check_tokens(my_token);
        }catch (Exception e){
            my_token.clear();
            my_token.add("wrong");
            my_token.add("input");
            e.printStackTrace();
        }
        my_token=full_parenthesis().my_token;//额 加全括号
        index = 0;
    }
    Lexer_Of_HMB(){}//什么是构造器？？？

    void init(String trimed_sourse){
        for (int i = 0; i < trimed_sourse.length(); i++) {
            switch (trimed_sourse.charAt(i)) {
                case ('('):
                    my_token.add(left_parenthesis);
                    break;
                case (')'):
                    my_token.add(right_parenthesis);
                    break;
                case ('\\'):
                    my_token.add(lambda);
                    break;
                case ('.'):
                    my_token.add(dot);
                    break;
                case (' '):
                    break;
                default:
                    int old_index = i;//会溢出，比如以字母结尾而不是右括号的情况
                    try {
                        while (trimed_sourse.charAt(i) != '(' &&
                                trimed_sourse.charAt(i) != ')' &&
                                trimed_sourse.charAt(i) != '\\' &&
                                trimed_sourse.charAt(i) != '.' &&
                                trimed_sourse.charAt(i) != ' ') {
                            i++;
                        }
                    }catch (StringIndexOutOfBoundsException e){
                        //e.printStackTrace();
                        i=trimed_sourse.length();
                    }
                    my_token.add(trimed_sourse.substring(old_index, i));
                    i = i - 1;
                    break;
            }

        }
    }

    public String toString(){
        StringBuilder result=new StringBuilder();
        String temp="";
        for (String i : my_token){
            if(isLCID(temp)&&isLCID(i)){
                result.append(" ");
            }
            result.append(i);
            temp=i;
        }
        return result.toString();
    }


    public String getValue(){
        return my_token.get(this.index);
    }
    public String getValue(int index){
        return my_token.get(index);
    }
    int lenth() {
        return my_token.size();
    }
    static boolean isLCID(String s){
        return !s.equals(left_parenthesis)&&!s.equals(right_parenthesis)&&!s.equals(lambda)&&!s.equals(dot);
    }



//--------------------------------下面都是加括号---------------------------------------
    public int match(int left) {

    if (my_token.get(left).equals(lambda)||my_token.get(left).equals(right_parenthesis)||my_token.get(left).equals(dot)) {
        System.out.println("wrong:"+my_token.get(left));
    }

    if( !  my_token.get(left).equals(left_parenthesis)){
        return left;
    }

    int right = left - 1;
    int number = 0;
    do {
        ++right;
        if (my_token.get(right).equals(left_parenthesis)) {
            number++;
        } else if (my_token.get(right).equals(right_parenthesis)) {
            number--;
        }

    } while (number != 0);
    return right;
}

    public Lexer_Of_HMB(ArrayList<String> my_token) {
        index = 0;
        this.my_token = my_token;
    }

    public Lexer_Of_HMB subLexer(int left, int rihgt) {
        ArrayList<String>temp=new ArrayList<>();
        temp.addAll (my_token.subList(left,rihgt));
        return new Lexer_Of_HMB(temp);
    }

    public void add_a_parenthesis(){
        my_token.add(0,left_parenthesis);
        my_token.add(right_parenthesis);
    }

    public void connect(Lexer_Of_HMB a){
        this.my_token.addAll(a.my_token);
        this.index=0;
    }

    protected Lexer_Of_HMB full_parenthesis() {//好吧 这才是真正的加括号
        index=0;
        if(!getValue(0).equals(left_parenthesis)){
            if(lenth()==1) {
                return this;//即只有一个LCID
            }
        }

        //else:

            if(match(index)!=lenth()-1){
                add_a_parenthesis();
            }

            index=1;
            if(getValue(1).equals(lambda)){
                Lexer_Of_HMB a=this.subLexer(1,4);
                Lexer_Of_HMB b=this.subLexer(4,this.lenth()-1);
                if(b.lenth()!=1 && !b.getValue(0).equals(left_parenthesis)) {
                    b.add_a_parenthesis();
                }
                a.connect(b.full_parenthesis());
                a.add_a_parenthesis();
                return a;
            }

            else {
                do {
                    index = match(index) + 1;
                } while (match(index) < lenth() - 2);

                Lexer_Of_HMB a = this.subLexer(1, index);
                if(a.match(0)!=a.lenth()-1){
                    a.add_a_parenthesis();
                }
                Lexer_Of_HMB b = this.subLexer(index, this.lenth() - 1);

                a = a.full_parenthesis();
                a.connect(b.full_parenthesis());
                a.add_a_parenthesis();

                return a;
            }
        }
    }