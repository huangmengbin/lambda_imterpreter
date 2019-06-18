package cn.seecoder;

import java.util.*;
import java.lang.*;
public abstract class AST {

    protected int left_distance;
    protected  int right_distance;


    private final String[] alphabet=global.alphabet;

    public abstract String toString();

    public abstract String toString(int mode);

    public abstract boolean find_and_B_change();//寻找规约的地方，并将其规约

    public abstract void changeToSeecoder(Map<String,Integer> map);

    public abstract Lexer_Of_HMB toLexer();

    boolean can_be_replace(){
        return this instanceof Application &&
                ((Application) this).left_son instanceof Abstraction;
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
                    this.a_change(old_one,new_one,false);//a等价替换
                    alphabetPtr=0;
                    break;
                }
            }
        }

        this.B_replace2(p,label);//开始B替换第二步
    }

    private void B_replace2(Parser p, String label){

        if (this instanceof Application){//1.
            if(((Application) this).left_son instanceof Identifier){
                if(((Identifier) ((Application) this).left_son).name  .equals(label) ){
                    ((Application) this).left_son =p.parse();
                }
            }
            else {
                ((Application) this).left_son.B_replace2(p, label);
            }
            if(((Application) this).right_daughter instanceof Identifier){
                if(((Identifier) ((Application) this).right_daughter).name  .equals(label)){
                    ((Application) this).right_daughter =p.parse();
                }
            }
            else {
                ((Application) this).right_daughter.B_replace2(p, label);
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




    //-----------------------------以下为打印一棵不太好看的树

    public void print_tree(int tree_mode){
        calculate_node_distance();
        ArrayList<AST> to_print=new ArrayList<>();
        to_print.add(this);
        print_lines(to_print,tree_mode);
    }

    protected abstract String node();
    protected abstract String node(int left_or_right);//  i<0;左边；   i>0;右边。

    private static void print_lines(ArrayList<AST> asts  ,  int print_mode ) {

        StringBuilder lines = new StringBuilder();

        int number = 0;
        int left_or_right = 1;
        for (int i = 0; i < asts.size(); i++) {
            if (is_space(asts.get(i)) || (i == asts.size() - 1 && left_or_right > 0)) {
                lines.append(asts.get(i).node());
            } else {
                lines.append(asts.get(i).node(left_or_right = -left_or_right));
                number++;//只有这种非空的才算入number里面
            }//第一次为-1，左
            if (!is_space(asts.get(i)) &&
                    i < asts.size() - 1 && !is_space(asts.get(i + 1))
                    && (number & 1) == 1) {
                lines.append("--^--");//-----------------------与下面对应
            } else {
                lines.append("     ");//-----------------长度要和那个的一样
            }
        }


        //开始打印了


        if(print_mode==0){


        //1.要不要____/ \___
        for (int i = 0; i < lines.length(); i++) {
            if(i==0||i==lines.length()-1){
                System.out.print(' ');
            }
            else if (lines.charAt(i) == '-') {
                  if (lines.charAt(i + 1) == '^') {
                    System.out.print('/');
                } else if (lines.charAt(i - 1) == '^') {
                    System.out.print('\\');
                } else if ( lines.charAt(i-1)=='-'&&lines.charAt(i+1)=='-'){
                    System.out.print('_');
                } else {
                    System.out.print(' ');
                }
            } else if (lines.charAt(i) == '^') {
                System.out.print('^');//-----------   '^' , ' ' 随便了
            } else {
                System.out.print(' ');
            }
        }
        System.out.print('\n');



  //2.要不要  /  \
        for (int i = 0; i < lines.length(); i++) {
            if (i >0 && lines.charAt(i) == '-' && lines.charAt(i-1) != '-' && lines.charAt(i-1)!= '^' ) {
                System.out.print('/');
            } else if (i <lines.length() && lines.charAt(i)=='-' &&lines.charAt(i+1)!='-' && lines.charAt(i+1)!= '^') {
                System.out.print('\\');
            } else {
                System.out.print(' ');
            }
        }
        System.out.print('\n');



        //最后一行，比较简约
        for (int i = 0; i < lines.length(); i++) {
            if (lines.charAt(i) == '-' || lines.charAt(i) == '^') {
                System.out.print(' ');
            } else {
                System.out.print(lines.charAt(i));
            }
        }
        System.out.print('\n');
    }
        if(print_mode==1) {

            //要不要那根竖起来的棍子
            for (int i = 0; i < lines.length(); i++) {
                if (lines.charAt(i) == '^') {//---------------------与上面对应
                    System.out.print('|');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.print('\n');

            System.out.println(lines.toString());//还是就觉得这个舒服
        }

        ArrayList<AST> next_asts=new ArrayList<>();
        boolean to_continue=false;
        for(AST i:asts){
            if(i instanceof Application){
                to_continue=true;
                next_asts.add(((Application) i).left_son);
                next_asts.add(((Application) i).right_daughter);
            }
            else if(i instanceof Abstraction){
                to_continue=true;
                next_asts.add(((Abstraction) i).param);
                next_asts.add(((Abstraction) i).body);
            }
            else if(i instanceof Identifier){
                String new_name=global.to_space(((Identifier) i).name);
                next_asts.add(new Identifier(new_name));
            }
        }//end for


        if(to_continue){
            print_lines(next_asts,print_mode);
        }

    }

    private static boolean is_space(AST ast){
        if(ast instanceof Identifier){
            if(((Identifier) ast).name.trim().equals("")){
                return true;
            }
        }
        return false;
    }

    abstract int calculate_node_distance();


}