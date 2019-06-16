package cn.seecoder;

import java.lang.*;

class CheckLegal {

    static void checklegal(String source) throws Exception{
        int number = 0;

            for (int i = 0; i < source.length(); i++) {
                if(number<0){throw new Exception("。。。");}
                switch (source.charAt(i)) {
                    case '(':
                        number++;
                        break;
                    case ')':
                        number--;
                        break;
                    case '（':
                    case '）':
                        throw new Exception("怎么会有中文的括号？？？");
                }
            }
        if(number!=0){
            throw new Exception("括号未匹配！");
        }
    }
}