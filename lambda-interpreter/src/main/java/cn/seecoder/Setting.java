package cn.seecoder;

import java.io.FileWriter;
import java.util.Scanner;

abstract class Setting {
    //mode用了一堆位运算符。。。
    //具体含义可以看看print
    static int set(int oldmode,String mode_file_place){
        System.out.print("Setting: ");
        int new_mode;
        Scanner in=new Scanner(System.in);
        String string=in.nextLine().toLowerCase().trim();
        if(string.equals("final")){
            new_mode = oldmode & (-13);
        }
        else if(string.equals("process")){
            new_mode = oldmode & (-13);
            new_mode = new_mode | (+4);
        }
        else if(string.equals("getline")){
            new_mode = oldmode & (-13);
            new_mode = new_mode | (+8);
        }
        else if(string.equals("seecoder")||string.equals("67")){
            new_mode=oldmode & (-4);
        }
        else if(string.equals("simplified")||string.equalsIgnoreCase("simplify")||string.equals("s")){
            new_mode=oldmode & (-4);
            new_mode=new_mode | (+1);
        }
        else if(string.equals("medium")||string.equals("m")){
            new_mode=oldmode & (-4);
            new_mode=new_mode | (+2);
        }
        else if(string.equals("full")|| string.equals("f")){
            new_mode=oldmode | (+3);
        }
        else if(string.equals("oblique")||string.equals("o")){
            new_mode=oldmode & (-17) ;
        }
        else if(string.equals("vertical")||string.equals("v")){
            new_mode = oldmode | 16;
        }
        else if(string.equals("check")){
            switch (oldmode&3){
                case 0:
                    System.out.println("The printing-mode is : "+"seecoder");
                    break;
                case 1:
                    System.out.println("The printing-mode is : "+"simplified");
                    break;
                case 2:
                    System.out.println("The printing-mode is : "+"medium");
                    break;
                case 3:
                    System.out.println("The printing-mode is : "+"full");
                    break;
            }
            switch ((oldmode>>2)&3){
                case 0:
                    System.out.println("The  running-mode is : "+"final");
                    break;
                case 1:
                    System.out.println("The  running-mode is : "+"process");
                    break;
                case 2:
                case 3:
                    System.out.println("The  running-mode is : "+"getline");
                    break;
            }
            switch ((oldmode>>4)&1){
                case 0:
                    System.out.println("The tree-printing-mode is : "+"oblique");
                    break;
                case 1:
                    System.out.println("The tree-printing-mode is : "+"vertical");
                    break;
            }
            return oldmode;
        }
        else {
            System.out.println("Wrong input!");
            new_mode=oldmode;
        }

        if(new_mode!=oldmode){
            System.out.println("Success!");
            try {
                FileWriter writer=new FileWriter(mode_file_place);
                writer.write(Integer.valueOf(new_mode).toString());
                writer.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("尝试写mode文件失败");
            }
        }
        else {
            System.out.println("No modification！！！");
        }
        return new_mode;
    }
}
