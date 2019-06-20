package cn.seecoder;

import java.util.Scanner;

class global {

    static boolean to_seecoder =true;

    static String to_space(String string){
        StringBuilder result = new StringBuilder();
        for (int i=0;i<string.length();i++){
            result.append(' ');
        }
        return result.toString();
    }



    






    static final String help= "\n" +
            "set：修改或查看你的模式\n" +
            "   表达式打印模式：seecoder , simplified , medium , full\n" +
            "   规约模式：getline , process , final\n" +
            "   AST树打印模式：oblique , vertical\n" +
            "   check:查看当前模式\n" +
            "function：修改或查看你的函数\n" +
            "   add：添加一个新的至指定位置\n" +
            "   delete：删除某个函数\n" +
            "   initial：还原成默认\n" +
            "   check：查看\n" +
            "tip：查看使用技巧\n" +
            "quit：退出程序\n" +
            "\n" +
            "规约时：\n" +
            "   tree：打印AST树\n" +
            "   step：输入正整数k并立刻进行k步B规约\n" +
            "   total：查看已经进行B规约的步数\n" +
            "   seecoder：临时改变打印模式至 seecoder\n" +
            "   simplified：临时改变打印模式至 simplified\n" +
            "   medium：临时改变打印模式至 medium\n" +
            "   full：临时改变打印模式至 full\n" +
            "   out：返回上一级\n" +
            "\n" +
            "\n";


    static void print_tip(){
        Scanner scanner=new Scanner(System.in);
        for(String i:tip){
            while (!scanner.nextLine().equals("")){
                System.out.println("wrong input!");
            }
            System.out.print(i);
        }
    }

    static private final String[] tip={
            "",
            "",
            "",
            "help中所有单词都不分大小写\n",
            "seecoder有那么长，所以可以尝试一下输入 67\n",
            "另外三种表达式打印模式可以用它们的首字母来简写\n",
            "oblique , vertical也是如此\n",
            "此外还有，t=tree,func=function,del=delete,init=initial\n",
            "",
            "第一次使用时可能还没有mode文件以及function文件,但是不必担心\n",
            "只要你去设置模式或修改函数,理论上它是会自动生成的\n",
            "比如试下输入 func \\n init \\n \n",
            "",
            "set，func，help,tip 以及lambda表达式 最好要看到 “Lambda-> ”时再输入\n",
            "不然会报错\n",
            "",
            "",
            "",
            "Lambda ：",
            "Lambda ：",
            "Lambda ：",
            "Lambda ：",
            "Lambda ：",
            "Lambda ：",
            "\n",
    };

    static final String left_parenthesis ="(";
    static final String right_parenthesis =")";
    static final String lambda ="\\";
    static final String dot = ".";

    static final String[] alphabet={//用于 a替换
        "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
        "aa","ab","ac","ad","ae","af","ag","ah","ai","aj","ak","al","am","an","ao","ap","aq","ar","as","at","au","av","aw","ax","ay","az",
        "ba","bb","bc","bd","be","bf","bg","bh","bi","bj","bk","bl","bm","bn","bo","bp","bq","br","bs","bt","bu","bv","bw","bx","by","bz",
        "ca","cb","cc","cd","ce","cf","cg","ch","ci","cj","ck","cl","cm","cn","co","cp","cq","cr","cs","ct","cu","cv","cw","cx","cy","cz",
        "da","db","dc","dd","de","df","dg","dh","di","dj","dk","dl","dm","dn","do","dp","dq","dr","ds","dt","du","dv","dw","dx","dy","dz",
        "ea","eb","ec","ed","ee","ef","eg","eh","ei","ej","ek","el","em","en","eo","ep","eq","er","es","et","eu","ev","ew","ex","ey","ez",
        "fa","fb","fc","fd","fe","ff","fg","fh","fi","fj","fk","fl","fm","fn","fo","fp","fq","fr","fs","ft","fu","fv","fw","fx","fy","fz",
        "ga","gb","gc","gd","ge","gf","gg","gh","gi","gj","gk","gl","gm","gn","go","gp","gq","gr","gs","gt","gu","gv","gw","gx","gy","gz",
        "ha","hb","hc","hd","he","hf","hg","hh","hi","hj","hk","hl","hm","hn","ho","hp","hq","hr","hs","ht","hu","hv","hw","hx","hy","hz",
        "ia","ib","ic","id","ie","if","ig","ih","ii","ij","ik","il","im","in","io","ip","iq","ir","is","it","iu","iv","iw","ix","iy","iz",
        "ja","jb","jc","jd","je","jf","jg","jh","ji","jj","jk","jl","jm","jn","jo","jp","jq","jr","js","jt","ju","jv","jw","jx","jy","jz",
        "ka","kb","kc","kd","ke","kf","kg","kh","ki","kj","kk","kl","km","kn","ko","kp","kq","kr","ks","kt","ku","kv","kw","kx","ky","kz",
        "la","lb","lc","ld","le","lf","lg","lh","li","lj","lk","ll","lm","ln","lo","lp","lq","lr","ls","lt","lu","lv","lw","lx","ly","lz",
        "ma","mb","mc","md","me","mf","mg","mh","mi","mj","mk","ml","mm","mn","mo","mp","mq","mr","ms","mt","mu","mv","mw","mx","my","mz",
        "na","nb","nc","nd","ne","nf","ng","nh","ni","nj","nk","nl","nm","nn","no","np","nq","nr","ns","nt","nu","nv","nw","nx","ny","nz",
        "oa","ob","oc","od","oe","of","og","oh","oi","oj","ok","ol","om","on","oo","op","oq","or","os","ot","ou","ov","ow","ox","oy","oz",
        "pa","pb","pc","pd","pe","pf","pg","ph","pi","pj","pk","pl","pm","pn","po","pp","pq","pr","ps","pt","pu","pv","pw","px","py","pz",
        "qa","qb","qc","qd","qe","qf","qg","qh","qi","qj","qk","ql","qm","qn","qo","qp","qq","qr","qs","qt","qu","qv","qw","qx","qy","qz",
        "ra","rb","rc","rd","re","rf","rg","rh","ri","rj","rk","rl","rm","rn","ro","rp","rq","rr","rs","rt","ru","rv","rw","rx","ry","rz",
        "sa","sb","sc","sd","se","sf","sg","sh","si","sj","sk","sl","sm","sn","so","sp","sq","sr","ss","st","su","sv","sw","sx","sy","sz",
        "ta","tb","tc","td","te","tf","tg","th","ti","tj","tk","tl","tm","tn","to","tp","tq","tr","ts","tt","tu","tv","tw","tx","ty","tz",
        "ua","ub","uc","ud","ue","uf","ug","uh","ui","uj","uk","ul","um","un","uo","up","uq","ur","us","ut","uu","uv","uw","ux","uy","uz",
        "va","vb","vc","vd","ve","vf","vg","vh","vi","vj","vk","vl","vm","vn","vo","vp","vq","vr","vs","vt","vu","vv","vw","vx","vy","vz",
        "wa","wb","wc","wd","we","wf","wg","wh","wi","wj","wk","wl","wm","wn","wo","wp","wq","wr","ws","wt","wu","wv","ww","wx","wy","wz",
        "xa","xb","xc","xd","xe","xf","xg","xh","xi","xj","xk","xl","xm","xn","xo","xp","xq","xr","xs","xt","xu","xv","xw","xx","xy","xz",
        "ya","yb","yc","yd","ye","yf","yg","yh","yi","yj","yk","yl","ym","yn","yo","yp","yq","yr","ys","yt","yu","yv","yw","yx","yy","yz",
        "za","zb","zc","zd","ze","zf","zg","zh","zi","zj","zk","zl","zm","zn","zo","zp","zq","zr","zs","zt","zu","zv","zw","zx","zy","zz",
    };
}

 //\y.((\x.\y.x x y) y)
   //      (\y.((\x.(\a.(x x) a)) y))
     //    (\y.(\a.y y a))



   //\y.(\y.y y y)