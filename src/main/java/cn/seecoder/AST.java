package cn.seecoder;

import java.util.*;

public abstract class AST {

    private final String[] alphabet=global.alphabet;

    public abstract String toString();

    public abstract String toString(int mode);

    public abstract boolean B_change();

    public abstract void changeToSeecoder(Map<String,Integer> map);

    public abstract Lexer_Of_HMB toLexer();

    boolean can_be_replace(){
        return this instanceof Application &&
                ((Application) this).son instanceof Abstraction;
    }

    protected abstract void a_change (String oldString , String newString, boolean shold_replace);

    void B_replace1(Lexer_Of_HMB lexer, Lexer_Of_HMB bodylexer, Parser p, String label){
        //先发现需要a替换的位置
        ArrayList<String>temp1=new ArrayList<>();
        ArrayList<String>conflict=new ArrayList<>();
        ArrayList<String>allString=new ArrayList<>();

        for(String i: lexer.my_token){
            if(!temp1.contains(i) &&  Lexer_Of_HMB.isLCID(i)  &&  !i.equals(label)){
                temp1.add(i);
            }
            if(!allString.contains(i) && Lexer_Of_HMB.isLCID(i)){
                allString.add(i);
            }
        }

        for(String i : bodylexer.my_token){
            if(!allString.contains(i) && Lexer_Of_HMB.isLCID(i)){
                allString.add(i);
            }
        }

        for (String i : temp1){
            if(!conflict.contains(i) && bodylexer.my_token.contains(i)){
                conflict.add(i);
            }
        }

        int alphabetPtr=0;//记录上次找到的位置，可以提高一点点效率
        String new_one;
        for (String old_one : conflict ){
            for(int i=alphabetPtr;i<alphabet.length;i++){
                new_one=alphabet[i];
                if(!allString.contains(new_one)){
                    allString.add(new_one);
                    this.a_change(old_one,new_one,false);
                    alphabetPtr=0;
                    break;
                }
            }
        }

        this.B_replace2(p,label);
    }

    private void B_replace2(Parser p, String label){

        if (this instanceof Application){//1.
            if(((Application) this).son instanceof Identifier){
                if(((Identifier) ((Application) this).son).name  .equals(label) ){
                    ((Application) this).son =p.parse();
                }
            }
            else {
                ((Application) this).son.B_replace2(p, label);
            }
            if(((Application) this).daughter instanceof Identifier){
                if(((Identifier) ((Application) this).daughter).name  .equals(label)){
                    ((Application) this).daughter =p.parse();
                }
            }
            else {
                ((Application) this).daughter.B_replace2(p, label);
            }
        }

        else if(this instanceof Abstraction){//2.
            if ( ! ((Abstraction) this).param.name  .equals(label)){
                //就是说如果abstraction左边等于label,那么右边的一大串都不需要搞了

                if(((Abstraction) this).body instanceof Identifier){
                    if(((Identifier) ((Abstraction) this).body).name   .equals(label)){
                        ((Abstraction) this).body=p.parse();
                    }
                }
                else {
                    ((Abstraction) this).body.B_replace2(p, label);
                }
            }
        }
        else if(this instanceof Identifier){//3
            System.out.println("bug of identify replace");
           //卧槽，好烦啊
        }

    }
}