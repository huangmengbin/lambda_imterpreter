package cn.seecoder;

import java.util.*;
import java.lang.*;
public abstract class AST {

    int left_distance;
    int right_distance;


    private final String[] alphabet=global.alphabet;

    public abstract String toString();

    public abstract String toString(int mode);

    public abstract boolean find_and_B_change();//寻找规约的地方，并将其规约

    public abstract Lexer_Of_HMB toLexer();

    boolean can_be_replace(){
        return this instanceof Application &&
                ((Application) this).lhs instanceof Abstraction;
    }

    protected abstract void a_change (String oldString , String newString, boolean shold_replace);

    void B_replace1(Lexer_Of_HMB rhs_lexer, Lexer_Of_HMB bodylexer, AST ast, String label){
        //功能是

        //先发现需要a替换的位置
        ArrayList<String>temp1=new ArrayList<>();
        ArrayList<String>conflict=new ArrayList<>();
        ArrayList<String>allString=new ArrayList<>();

        for(String i: rhs_lexer.my_token){
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
            if(!conflict.contains(i) && bodylexer.my_token.contains(i)){//发现了字母冲突的地方
                conflict.add(i);//那就把它加进去
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

        this.B_replace2(ast,label);//开始B替换第二步
    }
    //实际上B_replace1,B_replace2可以写在interpreter里头，会更清晰
    private void B_replace2(AST ast, String label){
        //我们要把很多东西替成传进来的那个AST
        if (this instanceof Application){//1.
            if(((Application) this).lhs instanceof Identifier){
                if(((Identifier) ((Application) this).lhs).name  .equals(label) ){
                    ((Application) this).lhs =ast.clone();
                }
            }
            else {
                ((Application) this).lhs.B_replace2(ast, label);
            }
            if(((Application) this).rhs instanceof Identifier){
                if(((Identifier) ((Application) this).rhs).name  .equals(label)){
                    ((Application) this).rhs =ast.clone();
                }
            }
            else {
                ((Application) this).rhs.B_replace2(ast, label);
            }
        }

        else if(this instanceof Abstraction){//2.
            if ( ! ((Abstraction) this).param.name  .equals(label)){
                //就是说如果abstraction左边等于label,那么右边的一大串都不需要搞了

                if(((Abstraction) this).body instanceof Identifier){
                    if(((Identifier) ((Abstraction) this).body).name   .equals(label)){
                        ((Abstraction) this).body=ast.clone();
                    }
                }
                else {
                    ((Abstraction) this).body.B_replace2(ast, label);
                }
            }
        }
        else if(this instanceof Identifier){//3
            System.out.println("bug of identify replace");
           //卧槽，好烦啊
        }

    }

    public abstract void change_to_seecoder(Map<String,Integer> map);


    //-----------------------------以下为打印一棵不太好看的树

    void print_tree(int tree_mode){
        calculate_node_distance();
        ArrayList<AST> to_print=new ArrayList<>();
        to_print.add(this);
        print_lines(to_print,tree_mode);
    }

    protected abstract String node_toString();
    protected abstract String node_toString(int left_or_right);//  i<0;左边；   i>0;右边。

    private static void print_lines(ArrayList<AST> ast_list  ,  int print_tree_mode ) {

        StringBuilder line = new StringBuilder();

        int number = 0;
        int left_or_right = 1;
        for (int i = 0; i < ast_list.size(); i++) {
            if (is_space(ast_list.get(i)) || (i == ast_list.size() - 1 && left_or_right > 0)) {
                line.append(ast_list.get(i).node_toString());
            } else {
                line.append(ast_list.get(i).node_toString(left_or_right = -left_or_right));
                number++;//只有这种非空的才算入number里面
            }//第一次为-1，左
            if (!is_space(ast_list.get(i)) &&
                    i < ast_list.size() - 1 && !is_space(ast_list.get(i + 1))
                    && (number & 1) == 1) {
                line.append("--^--");//-----------------------与下面对应，长度与[APP][Abs]一致
            } else {
                line.append("     ");//-----------------------与上面对应，长度与[APP][Abs]一致
            }
        }


        //开始打印了


        if(print_tree_mode==0){

        //1.要不要____/ \___
        for (int i = 0; i < line.length(); i++) {
            if(i==0||i==line.length()-1){
                System.out.print(' ');
            }
            else if (line.charAt(i) == '-') {
                  if (line.charAt(i + 1) == '^') {
                    System.out.print('/');
                } else if (line.charAt(i - 1) == '^') {
                    System.out.print('\\');
                } else if ( line.charAt(i-1)=='-'&&line.charAt(i+1)=='-'){
                    System.out.print('_');
                } else {
                    System.out.print(' ');
                }
            } else if (line.charAt(i) == '^') {
                System.out.print('^');//-----------   '^' , ' ' 随便了
            } else {
                System.out.print(' ');
            }
        }
        System.out.print('\n');



  //2.要不要  /  \
        for (int i = 0; i < line.length(); i++) {
            if (i >0 && line.charAt(i) == '-' && line.charAt(i-1) != '-' && line.charAt(i-1)!= '^' ) {
                System.out.print('/');
            } else if (i <line.length() && line.charAt(i)=='-' &&line.charAt(i+1)!='-' && line.charAt(i+1)!= '^') {
                System.out.print('\\');
            } else {
                System.out.print(' ');
            }
        }
        System.out.print('\n');



        //最后一行，比较简约
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '-' || line.charAt(i) == '^') {
                System.out.print(' ');
            } else {
                System.out.print(line.charAt(i));
            }
        }
        System.out.print('\n');
    }
        if(print_tree_mode==1) {

            //要不要那根竖起来的棍子
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '^') {//---------------------与上面对应
                    System.out.print('|');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.print('\n');

            System.out.println(line.toString());//还是就觉得这个舒服
        }

        ArrayList<AST> next_asts=new ArrayList<>();
        boolean to_continue=false;
        for(AST i:ast_list){
            if(i instanceof Application){
                to_continue=true;
                next_asts.add(((Application) i).lhs);
                next_asts.add(((Application) i).rhs);
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
            print_lines(next_asts,print_tree_mode);
        }

    }

    private static boolean is_space(AST ast){//指一个Identifier,里面的name是一串空格
        if(ast instanceof Identifier){
            if(((Identifier) ast).name.trim().equals("")){
                return true;
            }
        }
        return false;
    }

    abstract int calculate_node_distance();

    protected abstract AST clone();//不知怎么的突然想写这个，感觉比之前的快多了

}