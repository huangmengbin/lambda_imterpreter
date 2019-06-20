package cn.seecoder;

class Lexer extends Lexer_Of_HMB {

    Lexer(String source) {//没用用过的类，仅仅是为了打印一个东西给seecoder，所有的lexer都写在它的父类里头
        source = source.trim();
        this.init(source);
        print_to_seecoder();//<-----------------提交前不能有注释
        this.my_token=full_parenthesis().my_token;
        index = 0;
    }
    private void print_to_seecoder() {
        for (String tokenType:my_token) {
            switch (tokenType){
                case lambda:
                    System.out.println("LAMBDA");
                    break;
                case left_parenthesis:
                    System.out.println("LPAREN");
                    break;
                case right_parenthesis:
                    System.out.println("RPAREN");
                    break;
                case dot:
                    System.out.println("DOT");
                    break;
                default:
                    System.out.println("LCID");
                    break;
            }
        }
        System.out.println("EOF");
    }
}