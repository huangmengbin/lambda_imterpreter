package cn.seecoder;

class Interpreter_process extends Interpreter_getline{
    Interpreter_process(Parser p,int print_mode,int tree_mode){
        myast = p.parse();
        step=999999999;
        this.print_mode=print_mode;
        this.tree_mode=tree_mode;
    }
}