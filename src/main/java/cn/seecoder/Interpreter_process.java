package cn.seecoder;

class Interpreter_process extends Interpreter_getline{
    Interpreter_process(Parser p,int print_mode){
        myast = p.parse();
        step=999999999;
        this.print_mode=print_mode;
    }
}