package cn.seecoder;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    //static int mode=global.mode;

    static final private String left_parenthesis=global.left_parenthesis;
    static final private String right_parenthesis=global.right_parenthesis;
    static final private String lambda=global.lambda;
    static final private String dot=global.dot;

    Lexer_Of_HMB lexer;

    public Parser(Lexer_Of_HMB lexer){
        this.lexer = lexer;
    }

    AST parse(){//来构建树了

        AST ast = term(lexer);
        try {
            Lexer_Of_HMB templexer = ast.toLexer();
            ast = term(templexer);

            if (global.to_seecoder) {
                Map<String, Integer> map = new HashMap<>();
                ast.changeToSeecoder(map);
            }

        }catch (Exception e){
            System.out.println("构建树时出错");
            e.printStackTrace();
        }
        return ast;
    }

    private AST term(Lexer_Of_HMB lexer){

        switch (lexer.getValue(0)) {
            case left_parenthesis:

//-------------------------------------------------------------------------------------
/**/                        switch (lexer.getNext(1)) {
                            case lambda:
                                return new Abstraction
                                        (new Identifier(lexer.getNext(2)),
                                                term(lexer.subLexer(4, lexer.match(4) + 1)));
                            case left_parenthesis:
                                int temp1 = lexer.match(1) + 1;
                                int temp2 = lexer.match(temp1) + 1;
                                return new Application(
                                        term(lexer.subLexer(1, temp1)),
                                        term(lexer.subLexer(temp1, temp2)));
                            default:
                                return new Application(
                                        new Identifier(lexer.getNext(1)),
                                        term(lexer.subLexer(2, lexer.match(2) + 1)));
                            }
//------------------------------------------------------------------------------------
            case right_parenthesis://理论上是不会遇到的
                System.out.println("wr");
                return null;
            case lambda://理论上是不会遇到的
                System.out.println("wl");
                return null;
            case dot://理论上是不会遇到的
                System.out.println("wd");
                return null;
            default://普通的string
                return new Identifier(lexer.getValue());
            }
    }
}