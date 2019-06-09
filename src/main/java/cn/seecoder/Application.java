package cn.seecoder;

import java.util.Map;

public class Application extends AST{
    AST son;//左树
    AST daughter;//右树

    Application(AST left, AST right){
        son = left;
        daughter = right;
    }

    public boolean B_change(){

        boolean have_changed=false;

        if(son.can_be_replace()){
            have_changed=true;
            son=((Application)son).B_replace();
        }

        if((son instanceof Abstraction || son instanceof Application )&&!have_changed){
            if( son.B_change()){
                have_changed=true;
            }
        }

        if(daughter.can_be_replace()&&!have_changed){
            have_changed=true;
            daughter=((Application)daughter).B_replace();
        }

        if ((daughter instanceof Abstraction || daughter instanceof Application)&&!have_changed ){
            if( daughter.B_change()){
                have_changed=true;
            }
        }

        return have_changed;
    }

    public AST B_replace(){
        if(son instanceof Abstraction){
            String label =((Abstraction) son).param.name;

            if(((Abstraction) son).body instanceof Identifier){
                if(((Identifier) ((Abstraction) son).body).name.equals(label)){
                    return daughter;
                }
                else {
                    return ((Abstraction) son).body;
                }
            }

            else {
                Lexer_Of_HMB lexer = daughter.toLexer();
                Parser p=new Parser(lexer);
                Lexer_Of_HMB bodyLexer=((Abstraction) son).body.toLexer();
                ((Abstraction) son).body.B_replace1(lexer,bodyLexer,p,label);
                return ((Abstraction) son).body;
            }
        }
        else {
            System.out.println("bug of application");
            return null;
        }
    }

    protected void a_change(String oldString , String newString, boolean should_replace){
        son.a_change(oldString,newString,should_replace);
        daughter.a_change(oldString,newString,should_replace);
    }

    public String toString(){
        return "("+ son.toString()+" "+ daughter.toString()+")";
    }

    public String toString(int mode){
        switch (mode){
            case 1:
                StringBuilder temp1=new StringBuilder(son.toString(mode));
                StringBuilder temp2=new StringBuilder(daughter.toString(mode));
                if(daughter instanceof Application || daughter instanceof Abstraction){
                    temp2.insert(0,"(");
                    temp2.append(")");
                }
                if(son instanceof Abstraction){
                    temp1.insert(0,"(");
                    temp1.append(")");
                }
                return temp1.toString()+" "+temp2.toString();

            case 2:
            case 3:
            default:
                return "("+ son.toString(mode)+" "+ daughter.toString(mode)+")";
        }
    }

    public Lexer_Of_HMB toLexer(){
        Lexer_Of_HMB a= son.toLexer();
        Lexer_Of_HMB b= daughter.toLexer();
        a.connect(b);
        a.add_a_parenthesis();
        return a;
    }

    public void changeToSeecoder(Map<String,Integer> map){
        son.changeToSeecoder(map);
        daughter.changeToSeecoder(map);
    }
}