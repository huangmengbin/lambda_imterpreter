package cn.seecoder;

import java.util.HashMap;
import java.util.Map;

class Application extends AST{
    AST lhs;//左树
    AST rhs;//右树

    Application(AST left, AST right){
        lhs = left;
        rhs = right;
    }



    public String toString(){
        return "("+ lhs.toString()+" "+ rhs.toString()+")";
    }

    public String toString(int mode){
        switch (mode){
            case 1:
                StringBuilder temp1=new StringBuilder(lhs.toString(mode));
                StringBuilder temp2=new StringBuilder(rhs.toString(mode));
                if(lhs instanceof Abstraction){
                    temp1.insert(0,"(");
                    temp1.append(")");
                }
                if( ! (rhs instanceof Identifier) ){
                    temp2.insert(0,"(");
                    temp2.append(")");
                }
                return temp1.toString()+" "+temp2.toString();

            case 2:
                StringBuilder temp3=new StringBuilder(rhs.toString(mode));
                    if(rhs instanceof Abstraction){
                        temp3.insert(0,"(");
                        temp3.append(")");
                    }
                return "("+ lhs.toString(mode)+" "+ temp3.toString()+")";
            case 3:
                return "("+ lhs.toString(mode)+" "+ rhs.toString(mode)+")";
            default:
                this.change_to_seecoder(new HashMap<>());
                return this.toString();
        }
    }

    public Lexer_Of_HMB toLexer(){
        Lexer_Of_HMB a= lhs.toLexer();
        Lexer_Of_HMB b= rhs.toLexer();
        a.connect(b);
        a.add_a_parenthesis();
        return a;
    }

    protected AST clone(){
        return new Application(lhs.clone(), rhs.clone());
    }

    public void change_to_seecoder(Map<String, Integer> map){
        lhs.change_to_seecoder(map);
        rhs.change_to_seecoder(map);
    }

    //以下为规约------------------------------------------
    public boolean find_and_B_change(){

        boolean have_found=false;

        //顺序是可调的，因为真的不懂怎么搞

        if(!have_found && lhs.can_be_replace()) {
            have_found = true;//find         //and
            //if (!((Abstraction) (((Application) lhs).lhs)).body.find_and_B_change()) {
                lhs = ((Application) lhs).B_replace();
            //}
        }

        if(!have_found && !(lhs instanceof Identifier)){
            have_found= lhs.find_and_B_change();
        }


        if(!have_found && rhs.can_be_replace()){
            have_found=true;
            //if (!((Abstraction) (((Application) rhs).lhs)).body.find_and_B_change()) {
                rhs = ((Application) rhs).B_replace();
            //}
        }

        if (!have_found && !(rhs instanceof Identifier)){
            have_found= rhs.find_and_B_change();
        }

        return have_found;
    }

    AST B_replace(){//B替换第0步，即自己是APP，左边是Abs 的时候，开始替代
        if(! (lhs instanceof Abstraction) ) {
            System.out.println("B规约出现bug");
            return this;
        }

        String label =((Abstraction) lhs).param.name;

        if(((Abstraction) lhs).body instanceof Identifier){
            if(((Identifier) ((Abstraction) lhs).body).name.equals(label)){
                return rhs;
            }
            else {
                return ((Abstraction) lhs).body;
            }
        }

        else {
            Lexer_Of_HMB lexer = rhs.toLexer();
            Lexer_Of_HMB bodyLexer=((Abstraction) lhs).body.toLexer();
            ((Abstraction) lhs).body.B_replace1(lexer,bodyLexer, rhs.clone(),label);//进入了B替换第1步
            return ((Abstraction) lhs).body;
        }

    }

    protected void a_change(String oldString , String newString, boolean should_replace){
        lhs.a_change(oldString,newString,should_replace);
        rhs.a_change(oldString,newString,should_replace);
    }

    //以下打印树,可移植性。。。不太好
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
        left_distance= lhs.calculate_node_distance();
        right_distance= rhs.calculate_node_distance();
        return left_distance+right_distance+symbol.length();//长度
    }


}