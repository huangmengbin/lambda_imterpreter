package cn.seecoder;

class CheckLegal {


    static void checklegal(String source) {
        int left_num = 0, right_num = 0;

            for (int i = 0; i < source.length(); i++) {
                switch (source.charAt(i)) {
                    case '(':
                        left_num++;
                        break;
                    case ')':
                        right_num++;
                        break;
                }
            }
        if(left_num!=right_num){
            System.out.println("括号不匹配");
        }
    }
}
