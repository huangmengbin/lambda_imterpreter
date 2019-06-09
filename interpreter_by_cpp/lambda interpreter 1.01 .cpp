#include<vector>
#include<iostream>
#include<string>
#include<algorithm>
using namespace std;
#define elif else if

const string left_parenthesis = "(left parenthesis)";
const string right_parenthesis = "(right parenthesis)";
const string lambda = "--lambda";
const string abstraction = "[abs]";
const string application = "[app]";
const string isend = "isend";
const string help = "\n"\
"一共有3种模式,默认为getline,可通过输入set来调节\n"\
"\n"\
"	getline模式：\t每次执行一步B规约\n"\
"	process模式：\t执行所有的B规约,并显示运算过程\n"\
"	final模式:  \t执行所有的B规约,不显示运算过程\n"\
"\n"\
"getline模式中，输入tree,token,lambda可分别打印之\n"\
"	输入a：\t对整个句子进行a规约,可选择是否保存\n"\
"	输入step回车,再输入正整数 k：\t立刻进行k步B规约\n"\
"	输入total：\t查看已经运行了多少步\n"\
"	输入cal：\t查看括号的数量\n"\
"	输入process或final：\t可临时调整至对应模式\n"\
"	输入out：\t直接返回到主控制台\n"\
"\n"\
"所有模式算出答案后,输入tree,token,lambda,total,cal,out都可执行相应功能\n"\
"	或者输入2次回车,也可以返回至主控制台\n"\
"\n"\
"主控制台中输入back或history,可查看最近一次历史\n"\
"	输入set,切换不同的模式\n"\
"	输入func,可以check内置函数,或者define自己的函数,或者delete掉最新的那个函数\n"\
"	输入quit或exit,退出程序,离开bug\n"\
"\n"\
"\n"\
"//以上单词均不区分大小写";

const vector<string>alphabet = {
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


//一些有用没用的函数
bool   inline is_in(string str, vector<string>stringlist) {
	bool has_it = false;
	for (string temp : stringlist) {
		if (temp == str) {
			has_it = true;
			break;
		}
	}
	return has_it;
}
string inline string_replace(string&s1, const string&s2, const string&s3)
{
	string::size_type pos = 0;
	string::size_type a = s2.size();
	string::size_type b = s3.size();
	while ((pos = s1.find(s2, pos)) != string::npos)
	{
		s1.replace(pos, a, s3);
		pos += b;
	}
	return s1;
}
string inline to_lower_string(string s) {
	s = string_replace(s, "Q", "q");
	s = string_replace(s, "W", "w");
	s = string_replace(s, "E", "e");
	s = string_replace(s, "R", "r");
	s = string_replace(s, "T", "t");
	s = string_replace(s, "Y", "y");
	s = string_replace(s, "U", "u");
	s = string_replace(s, "I", "i");
	s = string_replace(s, "O", "o");
	s = string_replace(s, "P", "p");
	s = string_replace(s, "A", "a");
	s = string_replace(s, "S", "s");
	s = string_replace(s, "D", "d");
	s = string_replace(s, "F", "f");
	s = string_replace(s, "G", "g");
	s = string_replace(s, "H", "h");
	s = string_replace(s, "J", "j");
	s = string_replace(s, "K", "k");
	s = string_replace(s, "L", "l");
	s = string_replace(s, "Z", "z");
	s = string_replace(s, "X", "x");
	s = string_replace(s, "C", "c");
	s = string_replace(s, "V", "v");
	s = string_replace(s, "B", "b");
	s = string_replace(s, "N", "n");
	s = string_replace(s, "M", "m");

	return s;
}
//
//

class mydictionary {
private:
	static vector<string>key_value; 
public:
	static void function_define(string key,string value) {
		key_value.push_back(key);
		key_value.push_back(value);
	}
	static void remove() {
		if (key_value.size() > 86) {//protected number
			key_value.pop_back();
			key_value.pop_back();
			cout << "Success!" << endl << endl;
		}
		else {
			cout << "It can not be deleted." << endl << endl;
		}
	}
	static void check() {
		for (unsigned i = 0; i < key_value.size(); i += 2) {
			if (key_value[i] == "SIXSEVEN") {
				cout<<key_value[i]<< "  <-->\t" << key_value[i + 1] << endl;
			}
			else {
				cout << key_value[i] << "\t  <-->\t" << key_value[i + 1] << endl;
			}
		}
		cout << endl;
	}
	static string replace(string s) {
		for (int i = key_value.size() - 2; i >= 0; i -= 2) {
			s = string_replace(s,key_value[i], key_value[i + 1]);
		}
		return s;
	}
};
//
//<-------------------------------------------------------------------------------AST_Tree
class Tree {//<-------------------------------------------------------------------AST_Tree
private:

	static vector<string>token;					//这由输入的字符串转化而来，记载了所有信息，所有的 new Tree 都会 new 成它的形式
	static unsigned Token_ptr;//=0
	void to_token(bool should_clear = true);		//根据当前的树，把 token 转成此树对应的完全形式


	static void input_token(string s);			//
	static void calculate();
	static void add_parenthesis();//对token加一个括号,仅此而已

	
	Tree* left_son;// = 0;
	Tree* right_son;// = 0;
	Tree* father;// = 0;
	string attribute;// = "";
	string left_attribute;// = "";
	string right_attribute;// = "";

	


	//下面均为复杂的B规约-----------------------------------------------------B----------------------------------------
	static vector<string>temp_string_list;
	static vector<string>conflict_symbol_list;//右边潜在冲突的可能的自由元，只是可能冲突而已
	static vector<string>searched_symbol_list;//所有的不该变成新的变量名的东西

	bool find_conflict_before_B(bool init = true);
	void a_solve_conflict_before_B(string symbol_old, string symbol_new_new, bool shoule_solve_conflict = false);
	void B_replace_to_subtree(string symbol_old, bool init = true);
	void B_replace_to_string(string symbol_old, string symbol_new, bool init = true);
	void point_to_left_and_right_before_B();
	void goto_left_and_delete_right_before_B();
	
	//B规约如下：
	bool B_change(bool has_changed = false);//返回has changed,表示是否改变了东东-------------------------------------------



public:
	static void output_token();//打印token? 好吧，好像是parser?
	static void output_lambda();//打印那个lambda表达式
	static void a_change_token();//a规约


	//根据当前的 token (仅支持完全形式) 的情况 , 来构造一个与他对应的 语法树
	Tree(Tree* father = NULL, bool init = true);

	//他会把所有的子树都清空，请务必小心！！！
	~Tree();


	void print_Tree();			//左深度优先遍历.打印树
	static bool check_token_right(string s);//看看用户输入的是否合法
	//<---------------------------------------------------------------------------------------完了--------------------------------




	

	//<----------------------------------------------------------------------------------------初始化-------------------
	static bool initial(string s) {
		bool _67nb;
		s=mydictionary::replace(s);
		_67nb = check_token_right(s);
		if (!_67nb) { return _67nb; }

		token.clear();
		input_token(s);
		add_parenthesis();

		return _67nb;
	}
	
	//<-----------------------------------------------------------------------------------------工作区----------------------------
	static void work(Tree*hmb,string mode) {//<-------------------------------------------------work----------------------------

		string want_to_do = "";
		bool should_continue = false;
		int step = 0;
		unsigned total_step = 0;

		do {
			if (mode == "process" || mode == "getline") {
				hmb->to_token();
				cout << "lambda-> ";
				output_lambda();
				cout << endl;
			}

			step--;//<----------------------------------------------------------------------不可能溢出的

			while (mode == "getline"&&step<=0){//是个新的while,----getline
				hmb->to_token();
				cout << "What's your next thought?" << endl;
				getline(cin,want_to_do);
				want_to_do = to_lower_string(want_to_do);
				if (want_to_do == "tree") {
					cout << endl << "Tree: "<< endl << endl;
					hmb->print_Tree();
					continue;
				}
				elif(want_to_do == "token") {
					cout << endl << "token:" << endl << endl;
					hmb->output_token();
					continue;
				}
				elif(want_to_do == "lambda") {
					cout << endl << "lambda:" << endl << endl;
					cout << "lambda-> ";
					hmb->output_lambda();
					continue;
				}
				elif(want_to_do == "total") {
					cout << "total step=" << total_step << endl << endl;
				}
				elif(want_to_do == "cal") {
					calculate();
				}
				elif(want_to_do == "step") {
					cout << "step=";
					int tempstep;
					cin >> tempstep;
					string unuseful;
					getline(cin, unuseful);//<-----------------------------------------为了把后面流里头那个""截去？？？？？？？？？？
					if (tempstep > 0) {
						step = tempstep;
					}
					else {
						cout << "wrong number" << endl<<endl;
						step = 0;
						continue;
					}
				}
				elif(want_to_do == "a") {
					cout << "a change:" << endl;
					a_change_token();
					cout << "lambda-> ";
					output_lambda();
					cout <<endl<< "Save it or not?" << endl;
					string temp;
					getline(cin, temp);
					temp = to_lower_string(temp);
					if (temp == "yes"||temp=="y") {
						hmb->~Tree();
						hmb = new Tree();
						cout << "Saved！" << endl<<endl;
					}
					elif(temp == "no"||temp=="n") {
						hmb->to_token();
						cout << "Unsaved！" << endl<<endl;
					}
					else {	//让它是yes
						hmb->~Tree();
						hmb = new Tree();
						cout << "Saved！" << endl<<endl;
					}
				}
				elif(want_to_do == "process") {
					mode = "process";
					cout << endl << "Success!" << endl<<endl;
					continue;
				}
				elif(want_to_do == "final") {
					mode = "final";
					cout << endl << "Success!" << endl << endl;
					continue;
				}
				elif(want_to_do == "out") {
					cout <<endl<< "Success!" << endl << endl;
					return;
				}
				elif(want_to_do == "lambda") {
					cout << endl << "lambda:" << endl << endl;
					cout << "lambda-> ";
					hmb->output_lambda();
					continue;
				}
				elif(want_to_do == "") {//<-------------------------------------break了
					break;
				}
				else {
					cout << "Unrecognized" <<endl<< endl;
					continue;
				}
			} //end getline


			//a_change_token();//-----------------------------暴力，B规约之前进行a规约

			//hmb->~Tree();//
			//hmb = new Tree();//

			should_continue=hmb->B_change();//<-------------------------调用B规约

			//hmb->to_token();//-----------------------------暴力，B规约之前进行a规约
			

			if (hmb->left_son != 0 && hmb->right_attribute == isend) {//仅仅是为了删除一个括号而已
				hmb->left_son->to_token();
				hmb->~Tree();
				hmb = new Tree();
			}

			total_step++;
		} while (should_continue);

		cout << "lambda->:";
		hmb->to_token();
		output_lambda();
		cout << "\nThat's the answer.\n"<<endl;
//<-------------------------------------------------------------------------------------进入....状态了
		int stop_time = 2;
		do {
			cout << "....";
			getline(cin, want_to_do);
			want_to_do = to_lower_string(want_to_do);
			if (want_to_do == "tree") {
				cout << endl << "Tree: " <<endl<< endl;
				hmb->print_Tree();
				continue;
			}
			elif(want_to_do == "token") {
				cout << endl << "token:" << endl<<endl;
				hmb->output_token();
				continue;
			}
			elif(want_to_do == "cal") {
				calculate();
			}
			elif(want_to_do == "out") {
				break;
			}
			elif(want_to_do=="total") {
				cout << "total step=" << total_step-1 << endl << endl;
			}
			elif(want_to_do == "lambda") {
				cout << "lambda-> " << endl << endl;
				hmb->output_lambda();
				continue;
			}
			elif(want_to_do == "") {
				cout << "unrecognized" << endl << endl;
				--stop_time;
				continue;
			}
			else {
				cout << "unrecognized" << endl << endl;
				continue;
			}
		} while (stop_time);

		cout << endl << endl;
	}




};//end class Tree-----------------------------------------------------------------------end class Tree--------------------------------




//global???
//
Tree*hmb;//为了占一个内存？
unsigned		Tree::Token_ptr = 0;
vector<string>	Tree::token;//要在全局声明一下，不然报错（不知道为啥??

vector<string> mydictionary::key_value= {												//函数定义
	"ZERO", "(\\f.(\\x.(x)))"	,
	"ONE", "(\\f.(\\x.(f(x))))"	,
	"TWO", "(\\f.(\\x.(f(f(x)))))"	,
	"THREE", "(\\f.(\\x.(f(f(f(x))))))"	,
	"FOUR", "(\\f.(\\x.(f(f(f(f(x)))))))"	,
	"FIVE", "(\\f.(\\x.(f(f(f(f(f(x))))))))"	,
	"SIX", "(\\f.(\\x.(f(f(f(f(f(f(x)))))))))"	,
	"SIXSEVEN","(67_NB)",
	"SEVEN", "(\\f.(\\x.(f(f(f(f(f(f(f(x))))))))))"	,
	"EIGHT", "(\\f.(\\x.(f(f(f(f(f(f(f(f(x)))))))))))"	,
	"NINE", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(x))))))))))))"	,
	"TEN", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f(x)))))))))))))"	,
	"ELEVEN", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f(f(x))))))))))))))"	,
	"TWELVE", "(\\f.(\\x.(f(f(f(f(f(f(f(f(f(f(f(f(x)))))))))))))))"	,
	"SUCC", "(\\n.(\\f.(\\x.(f((n(f))(x))))))"	,
	"PLUS", "(\\m.(\\n.((m SUCC)(n))))"	,
	"MULT", "(\\m.(\\n.(\\f.(m(n(f))))))"	,
	"POW", "(\\b.(\\e.(e(b))))"	,
	"PRED", "(\\n.(\\f.(\\x.(((n(\\g.(\\h.(h(g(f))))))(\\u.(x)))(\\v.(v))))))"	,
	"SUB", "(\\m.(\\n.((n PRED)(m))))"	,

	"TRUE", "(\\u.(\\v.(u)))"	,
	"FALSE", "(\\u.(\\v.(v)))"	,
	"AND", "(\\p.(\\q.((p(q))(p))))"	,
	"OR", "(\\p.(\\q.((p(p))(q))))"	,
	"NOT", "(\\p.(\\a.(\\b.((p(b))(a)))))"	,
	"IF", "(\\p.(\\a.(\\b.((p(a))(b)))))"	,
	"ISZERO", "(\\h.((h(\\t.FALSE))TRUE))"	,//?????????????????????

	"LEQ", "(\\m.(\\n.(ISZERO((SUB(m))(n)))))"	,
	"EQ", "(\\m.(\\n.((AND((LEQ(m))(n)))((LEQ(n))(m)))))"	,
	"LEQ", "(\\m.(\\n.(ISZERO((SUB(m))(n)))))"	,
	//(\\f.(\\n.(((IF(ISZERO(n)))ONE)((MULT(n))((f f)(PRED(n)))))))
	//(\\f.(\\n.(((ISZERO n)ONE)((MULT(n))((f f)(PRED n))))))
	"FACT1","(\\a.(\\b.((((b(\\t.(\\d.(\\e.e))))(\\i.(\\j.i)))(\\k.(\\l.(k l))))(\\p.(b(((a a)(\\r.(\\s.(((b(\\g.(\\w.(w(g r)))))(\\y.s))(\\z.z)))))p))))))",
	"FACT","(FACT1 FACT1)",
	"FACT1","(\\f.(\\n.(((ISZERO n)ONE)((MULT(n))((f f)(PRED n))))))",
	//(\\f.(\\n.(((IF(ISZERO(n)))ONE)((MULT(n))((f f)(PRED(n)))))))
//\f.\n.IF(ISZERO n) ONE(MULT n(f f(PRED n)))
//		(\f.(\n.((( IF(ISZERO (n))) ONE)((MULT (n))(f (f(PRED n))))   )))

	"FACT2","(\\f.(\\n.(((IF(ISZERO(n)))ONE)((MULT(n))(f(PRED(n)))))))",
	"YYY","(\\g. (  (\\x.( g(x x))) (\\x.(g(x x)))))",
	"FACTY","(YYY FACT2)",

	"MAX", "(\\x.(\\y.(((IF((LEQ(x))(y)))(y))(x))))",
	"MIN", "(\\x.(\\y.(((IF((LEQ(x))(y)))(x))(y))))",


	"CDR", "(\\p.(p FALSE))",
	"CAR", "(\\p.(p TRUE))",
	"CONS", "(\\x.(\\y.(\\f.((f(x))(y)))))",
	"NULL", "(\\p.(p(\\x.(\\y.FALSE))))",
	"NIL", "(\\x.TRUE)",
};
//<--------------------------------------------------------------------------------------------------main()-------------
int main() {//<---------------------------------------------------------------------------------------main()-----------------

	string mode="getline";
	string inner;

	while (true) {
		bool token_is_correct = true;

		cout << "Hello World!" << endl;
		getline(cin, inner);
		inner.erase(0, inner.find_first_not_of(" "));

		if (to_lower_string(inner) == "set") {
			string mode_history = mode;
			cout << "mode=";
			getline(cin, mode);
			mode = to_lower_string(mode);//
			if ((mode!=mode_history)&&(mode == "process" || mode == "final" || mode == "getline")) {
				cout <<endl<< "Success!" <<endl<< endl;
			}
			continue;
		}
		elif(to_lower_string(inner) == "func") {
			mydictionary* nb67 = new mydictionary();
			getline(cin, inner);
			if (to_lower_string(inner) == "define") {
				string s1, s2;
				cout << "Your function name is:";
				getline( cin,s1);
				cout << "Your function define is:";
				getline(cin, s2);
				mydictionary::function_define(s1, s2);
				cout<< "Success!" << endl << endl;
			}
			elif(to_lower_string(inner) == "check") {
				mydictionary::check();
			}
			elif(to_lower_string(inner) == "delete") {
				mydictionary::remove();
			}
			else {
				cout << "Unrecognized " << endl << endl;
			}
			continue;
		}
		elif(inner == "") {
			continue;
		}
		elif(to_lower_string(inner) =="quit"|| to_lower_string(inner) =="exit") {
			break;
		}
		elif(to_lower_string(inner) == "history"|| to_lower_string(inner) =="back") {
			inner = "history";
		}
		elif(to_lower_string(inner) == "help") {
			cout << help << endl<<endl<<endl;
			continue;
		}
		else {
			//pass
		}
		if (to_lower_string(inner) != "history") {
			token_is_correct= Tree::initial(inner); 
		}//别传入保留字
		if (token_is_correct) {
			hmb = new Tree();
			Tree::work(hmb, mode);
		}
		else {
			cout << "wrong input" << endl << endl;
		}
	}
	//cout << "Tree" << endl;
	//hmb->print_Tree();
	//cout << "sentence" << endl;
	//Tree::output_sentence();
	//Tree::output_lambda_of_sentence();
	return 0;
}
//--------------------------------------------------------------------------------------------------end main();









//<--------------------------------------------------------------------函数的定义--------------------------------
Tree::Tree(Tree* father, bool init) {
	if (init) { Tree::Token_ptr = 0; }

	this->father = father;

	while (Token_ptr < token.size()) {
		if (token[Token_ptr] == left_parenthesis)//左括号
		{
			if (token[Token_ptr + 1] == lambda)			//  (lambda
			{
				if (father != 0) {
					if (father->left_attribute == "") {
						father->left_attribute = abstraction;
					}
					elif(father->right_attribute == "") {
						father->right_attribute = abstraction;
					}
				}

				attribute = abstraction;

				left_attribute = token[Token_ptr + 2];
				Token_ptr += 3;
				right_son = new Tree(this, false);


			}

			elif(token[Token_ptr + 1] == left_parenthesis)					//  ((
			{
				if (father != 0) {
					if (father->left_attribute == "") {
						father->left_attribute = application;
					}
					elif(father->right_attribute == "") {
						father->right_attribute = application;
					}
				}
				attribute = application;

				Token_ptr++;
				left_son = new Tree(this, false);
				/*...
				改变了i
				此时token[i-1]一定是 右括号
				token[i]一定是 左括 右括 变量 之一,一下分别处理之
				...*/
				if (token[Token_ptr] == left_parenthesis) {
					right_son = new Tree(this, false);
				}
				elif(token[Token_ptr] == right_parenthesis) {
				}
				else {
					right_attribute = token[Token_ptr];
					Token_ptr += 1;
				}

			}

			else								// ‘(’ 后 紧跟着是 n f x y 之类，还可能是  (f (x))
			{
				if (father != 0) {
					if (father->left_attribute == "") {
						father->left_attribute = application;
					}
					elif(father->right_attribute == "") {
						father->right_attribute = application;
					}
				}
				attribute = application;

				left_attribute = token[Token_ptr + 1];
				if (token[Token_ptr + 2] == right_parenthesis)			// 结束,因为  (x)
				{
					right_attribute = isend;
					Token_ptr += 2;											//////why>?......dont return
				}
				else {
					Token_ptr += 2;
					right_son = new Tree(this, false);
				}

			}
		}

		elif(token[Token_ptr] == right_parenthesis)
		{
			Token_ptr++;
			return;
		}

		else
		{
			throw string(token[Token_ptr] + "  error");
		}

	}
}
Tree::~Tree() {
	Tree::Token_ptr = 0;
	if (left_son != 0) {
		left_son->~Tree();
	}
	if (right_son != 0) {
		right_son->~Tree();
	}
}


//
//static
void   Tree:: input_token(string s) {
	bool spilit = false;
	for (unsigned i = 0; i < s.length(); i++) {
		switch (s.at(i))
		{
		case('('):
			token.push_back(left_parenthesis);
			break;
		case(')'):
			token.push_back(right_parenthesis);
			break;
		case('\\'):
			token.push_back(lambda);
			break;
		case('.'):
			spilit = true;
			break;
		case(' '):
			spilit = true;
			break;
		case('\n'):
			break;
		default:
			if (token.back() == left_parenthesis || token.back() == right_parenthesis || token.back() == lambda || spilit) {
				token.push_back(s.substr(i, 1));
				spilit = false;
			}
			else {
				token.back() = token.back() + s.substr(i, 1);
			}
			break;
		}
	}
	return;
}
void   Tree:: add_parenthesis() {//仅仅加一个()给token，真正的不会写（太菜了
	vector<string>temp = token;
	token.clear();
	for (unsigned i = 0; i < temp.size();i++) {
		if (	((temp[i    ] != lambda) && (temp[i  ] != left_parenthesis) && (temp[i  ] != right_parenthesis))
			&&	((temp[i - 1] != lambda) && (temp[i-1] != left_parenthesis)    )) {
			token.push_back(left_parenthesis);
			token.push_back(temp[i]);
			token.push_back(right_parenthesis);
		}
		else {
			token.push_back(temp[i]);
		}
	}
}
bool   Tree:: check_token_right(string s) {

	if (s.at(0) != '(') { return false; }

	unsigned left_num = 1, right_num = 0;

	for (unsigned i = 1; i < s.size(); i++) {
		if (s.at(i) == '(') {
			left_num++;
		}
		if (s.at(i) == ')') {
			right_num++;
			if (s.at(i - 1) == '(') {
				return false;
			}
		}

	}
	return left_num == right_num;

}
void   Tree:: calculate() {//看看括号的个数
	int num = 0;
	for (unsigned i = 0; i < token.size(); i++) {
		if (token[i] == right_parenthesis && token[i - 2] != left_parenthesis) {
			num++;
		}
	}
	cout << "number=" << num << endl << endl;
}
//
//
//根据当前的树，把 token 转成此树对应的完全形式
void   Tree::to_token(bool should_clear) {//初始化 默认should—clear 为true，清空旧的
	if (should_clear) { token.clear(); }

	if (attribute == application)
	{
		token.push_back(left_parenthesis);

		if (left_attribute == abstraction || left_attribute == application) {
			left_son->to_token(false);
		}
		else {
			token.push_back(left_attribute);
		}

		if (right_attribute == abstraction || right_attribute == application) {
			right_son->to_token(false);
		}
		else {						//isend
			//pass
		}
	}

	elif(attribute == abstraction) {
		token.push_back(left_parenthesis);
		token.push_back(lambda);
		token.push_back(left_attribute);
		right_son->to_token(false);
	}

	token.push_back(right_parenthesis);//return 前搞个 右括号
}
//
//
//
//
//
void Tree::output_token() {

	for (string i : token) 
	{
		cout << i << endl;
	}

	cout << endl << endl;
}
void Tree::output_lambda() {
		string out = "";
		bool has_dot = false;
		bool has_space = false;

		for (unsigned i = 0; i < token.size();i++) {

			if (token[i]  == left_parenthesis) {
				if (token[i + 2] != right_parenthesis) {//正常情况
					out = out + "(";
					has_space = false;
				}
				else {//两个括号只括一个东西，这样不好看，就微调一下
					if (has_space) { out = out + " ";}
					//没有 else
					out = out + token[i+1];
					has_space = false;
					i+=2;//跳跃一下
				}
			}
			elif(token[i] == right_parenthesis) {
				out = out + ")";
				has_space = false;
			}
			elif(token[i] == lambda) {
				out = out + "\\";
				has_dot = true;
				has_space = false;
			}
			else {

				if (has_dot) {
					out = out + token[i] + ".";
					has_dot = false;
				}
				elif(has_space) {
					//out = out + " " + i;
					out = out + token[i];
					has_space = false;
				}
				else {
					out = out + token[i];
					has_space = true;
				}
			}
		}
		cout << out << endl;
	}
void Tree::a_change_token() {//仅仅支持完整形式,作用于token,不作用于树

		vector<string>first_searched_symbollist;
		vector<string>more_than_once_symbollist;
		unsigned alphabet_ptr = 0;
		string symbol_old, symbol_new;

		for (unsigned i = 0; i < token.size(); i++) {
			if (token[i] == lambda) {
				if (!is_in(token[i + 1], first_searched_symbollist)) {
					first_searched_symbollist.push_back(token[i + 1]);
				}
				else {
					if (!is_in(token[i + 1], more_than_once_symbollist)) {
						more_than_once_symbollist.push_back(token[i + 1]);
					}
				}
			}
		}

		for (unsigned i = 0; i < token.size(); i++) {
			if ((token[i] == lambda) && is_in(token[i + 1], more_than_once_symbollist)) {//should replace

				while (is_in(alphabet[alphabet_ptr], first_searched_symbollist)) {//find new symbol
					alphabet_ptr++;
					if (alphabet_ptr == alphabet.size() - 1) {
						cout << "woc不会有那么多吧" << endl;
						alphabet_ptr = 0;
					}
				}
				symbol_old = token[i + 1];
				symbol_new = alphabet[alphabet_ptr];
				token[i + 1] = symbol_new;
				first_searched_symbollist.push_back(symbol_new);
				more_than_once_symbollist.push_back(symbol_new);

				int j = i + 1;
				int num = 1;
				while (num > 0) {			//replace
					if (token[j] == left_parenthesis) { num++; }
					elif(token[j] == right_parenthesis) { num--; }
					elif(token[j] == symbol_old) { token[j] = symbol_new; }
					j++;
				}
			}
		}

		//for (string i : symbollist) { cout << i << " "; }
		//cout << endl;
	}
//a规约 可能有bug,如三重以上定义造成混乱，也可能没有bug,不过效率低是真的
//
//
void Tree::print_Tree() {
	cout << this->attribute << endl;
	cout << left_attribute << endl;
	cout << right_attribute << endl;
	cout << endl;
	if (left_son != 0) {
		left_son->print_Tree();
	}
	if (right_son != 0) {
		right_son->print_Tree();
	}
}










//B规约的之前的处理
vector<string>  Tree::temp_string_list;
vector<string>	Tree::searched_symbol_list;//用于B规约之前的冲突处理
vector<string>  Tree::conflict_symbol_list;//用于B规约之前的冲突处理


bool Tree:: find_conflict_before_B(bool init) {//= true
	
	if (init) {//默认为true
		temp_string_list.clear();
	}

	bool have_found_conflict = false;		//	相当于init


	if ((this->attribute == abstraction) && (is_in(left_attribute, conflict_symbol_list))) {
		temp_string_list.push_back(left_attribute);
		have_found_conflict = true;
	}

	//
	if (left_son == NULL) {
		if (!is_in(left_attribute, searched_symbol_list)) {
			searched_symbol_list.push_back(left_attribute);
		}
	}
	else {
		if (left_son->find_conflict_before_B(false) == true) {
			have_found_conflict = true;
		}
	}
	//
	if ((right_son == NULL) && (right_attribute != isend)) {
		if (!is_in(right_attribute, searched_symbol_list)) {
			searched_symbol_list.push_back(right_attribute);
		}
	}
	elif(right_son != NULL) {
		if (right_son->find_conflict_before_B(false) == true) {
			have_found_conflict = true;
		}
	}

	conflict_symbol_list = temp_string_list;//这时候他就是真正的冲突点了

	return have_found_conflict;
}
void Tree:: a_solve_conflict_before_B(string symbol_old, string symbol_new_new, bool shoule_solve_conflict) {// = false

	if (!shoule_solve_conflict) {//默认 = false
		if (this->attribute == abstraction) {
			shoule_solve_conflict = true;
		}
	}

	if (shoule_solve_conflict) {//

		if (this->left_attribute == symbol_old) {
			left_attribute = symbol_new_new;
		}
		elif(left_son != NULL) {
			left_son->a_solve_conflict_before_B(symbol_old, symbol_new_new, shoule_solve_conflict);
		}

		if (this->right_attribute == symbol_old) {
			right_attribute = symbol_new_new;
		}
		elif(right_son != NULL) {
			right_son->a_solve_conflict_before_B(symbol_old, symbol_new_new, shoule_solve_conflict);
		}

		//
	}

	return;
}
//<----------------------------------------------------------------------------------------------
void Tree:: B_replace_to_subtree(string symbol_old, bool init) {//= true

	if (init) {												//初始化 应该解决冲突,我觉得如果之前的右边是abs不会有冲突？？？？
		conflict_symbol_list.clear();
		searched_symbol_list.clear();
		for (string i : token) {									//搜索token所有的值，还能优化，因为有的不是自由元
			if ((i != left_parenthesis) && (i != right_parenthesis) && (i != isend) && (i != lambda)) {
				conflict_symbol_list.push_back(i);
				searched_symbol_list.push_back(i);
			}
		}
		//////conflict_symbol_list.push_back(symbol_new);从 token 里头找吧，因为树被析构了，token里包含了所有信息  (看上面的for
		//////searched_symbol_list.push_back(symbol_new);
		bool have_found_conflict = this->find_conflict_before_B();//执行完后，conflict_symbol_list 变成了值重复的点（仅仅比所求的多了一个symbol_old ） ，searched_symbol_list变得更丰富

		//<----------------------------------------------------------------------垃圾优化，但没有它会全局gg
		{//<----------------------------------------------------------------------然后在conflict_symbol_list去除那个symbol_old,并进行垃圾优化
			sort(conflict_symbol_list.begin(), conflict_symbol_list.end());
			conflict_symbol_list.erase(unique(conflict_symbol_list.begin(), conflict_symbol_list.end()), conflict_symbol_list.end());
			vector<string>::iterator result = find(conflict_symbol_list.begin(), conflict_symbol_list.end(), symbol_old);
			if (result != conflict_symbol_list.end()) {
				conflict_symbol_list.erase(result);
			}


			sort(searched_symbol_list.begin(), searched_symbol_list.end());
			searched_symbol_list.erase(unique(searched_symbol_list.begin(), searched_symbol_list.end()), searched_symbol_list.end());
		}


		if (have_found_conflict) {
			for (string symbol_new : conflict_symbol_list) {
				string symbol_new_new;

				for (string i : alphabet) {
					if (!is_in(i, searched_symbol_list)) {
						symbol_new_new = i;
						break;
					}
				}

				searched_symbol_list.push_back(symbol_new_new);//<-------------------------------------------记得push进去
				this->a_solve_conflict_before_B(symbol_new, symbol_new_new);
			}
		}
	}//end init

	if (this->left_attribute == symbol_old) {//1.左：//<----------------------------------------------------并不是全部都new的吧

		if (attribute == abstraction) {//<------------------------------------------------------------------这个很关键
			return;
		}

		if ((right_attribute == isend) && (this->father != NULL)) {		//特判：为了把点缩减一下，否则括号过多
			if (this == father->left_son) {
				father->left_son = new Tree(father);/////////////////////////////////////////////
				father->left_attribute = father->left_son->attribute;
			}
			elif(this == father->right_son) {
				father->right_son = new Tree(father);
				father->right_attribute = father->right_son->attribute;
			}
			return;
		}//end if
		elif((right_attribute == isend) && (this->father == NULL)) {		//特判：同上,想要删除最外层的那个括号
			//this = this->left_son();//<++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=可能会占据过多内存
			left_son = new Tree(this);
			left_attribute = left_son->attribute;////不知道怎么写了
			//new Tree();

			return;
		}
		else {									//正常的情况
			left_son = new Tree(this);
			left_attribute = left_son->attribute;
		}

	}
	elif(left_attribute == application || left_attribute == abstraction) {//1.左
		left_son->B_replace_to_subtree(symbol_old, false);
	}

	if (this->right_attribute == symbol_old) {//2.右
		right_son = new Tree(this);
		right_attribute = right_son->attribute;
	}
	elif(right_attribute == application || right_attribute == abstraction) {//2.右
		right_son->B_replace_to_subtree(symbol_old, false);
	}
	//else
	return;
}

void Tree:: B_replace_to_string(string symbol_old, string symbol_new, bool init) {// = true

	if (init &&  symbol_old != symbol_new) {												//初始化 应该解决冲突
		conflict_symbol_list.clear();
		searched_symbol_list.clear();
		conflict_symbol_list.push_back(symbol_new);
		searched_symbol_list.push_back(symbol_new);
		bool have_found_conflict = this->find_conflict_before_B();//这里，symbol_new 直接就是冲突点

		string symbol_new_new;
		if (have_found_conflict) {
			for (string i : alphabet) {
				if (!is_in(i, searched_symbol_list)) {
					symbol_new_new = i;
					//cout << "new new:  "<<i << endl << endl;//------------------------------------------
					break;
				}
			}
			this->a_solve_conflict_before_B(symbol_new, symbol_new_new);
		}
	}//end init

	if (this->left_attribute == symbol_old) {//<----------------------------------------------------并不是全部都换的吧

		if (attribute == abstraction) {//<---------------------------------------------------------这个很关键，并不是之前解决冲突的范畴之内
			return;
		}

		left_attribute = symbol_new;
	}
	elif(left_son != NULL) {
		left_son->B_replace_to_string(symbol_old, symbol_new, false);
	}



	if (this->right_attribute == symbol_old) {
		right_attribute = symbol_new;
	}
	elif(right_son != NULL) {
		right_son->B_replace_to_string(symbol_old, symbol_new, false);
	}
	return;
}

void Tree:: point_to_left_and_right_before_B() {

	Tree*temp1 = this->left_son;
	Tree*temp3 = this->right_son;

	this->left_son = this->left_son->right_son;
	this->left_attribute = left_son->attribute;
	this->left_son->father = this;

	this->right_son = this->right_son->right_son;
	this->right_attribute = right_son->attribute;
	this->right_son->father = this;


	temp1->right_son = NULL;
	temp1->~Tree();
	temp3->left_son = 0;///////////
	temp3->right_son = 0;
	temp3->~Tree();
}
void Tree:: goto_left_and_delete_right_before_B() {

	this->right_son->~Tree();
	Tree*temp1 = this->left_son;
	Tree*temp2 = this->left_son->right_son;

	this->attribute = temp2->attribute;
	this->left_attribute = temp2->left_attribute;
	this->right_attribute = temp2->right_attribute;
	this->left_son = temp2->left_son;
	this->right_son = temp2->right_son;

	if (this->left_son != NULL) {		//这个很重要，否则它的子树不认得自己，无法指向这里
		left_son->father = this;
	}
	if (this->right_son != NULL) {
		right_son->father = this;
	}

	if (this->father != NULL) {			//这个很重要，否则它的根节点不知道自己的属性
		if (father->left_son == this) {
			father->left_attribute = this->attribute;
		}
		elif(father->right_son == this) {
			father->right_attribute = this->attribute;
		}
	}

	temp1->right_son = NULL;
	temp1->~Tree();
	temp2->left_son = 0;
	temp2->right_son = 0;
	temp2->~Tree();
}

//B规约
bool Tree:: B_change(bool has_changed) {//-------------------------------------------------------B 规约 到底是什么顺序？？？
	//已经默认has changed false



	   	  


	if (this->attribute == application && (!has_changed)) {



		if (left_attribute == abstraction ) {/////////////////////////////
			string symbol_old = left_son->left_attribute;//记录旧的symbol,进入B规约,分2种情况


			if (right_attribute == abstraction ) {						//1.右边为抽象，整体替换

				right_son->to_token();//修改了token,小心
				this->goto_left_and_delete_right_before_B();
				this->B_replace_to_subtree(symbol_old);//我觉得不需要约，因为没有自由元？？？？？？？？？？？？？？？？？？？？？
				has_changed = true;									//表示已经约过了
			}




			elif(right_attribute == application) {		//2. 右边为应用，分2种情况

				if (right_son->right_attribute == isend) {//2.1 右边的右边是end，所以右边的左边是字母，本身就是【】

					string symbol_new = right_son->left_attribute;
					this->goto_left_and_delete_right_before_B();
					this->B_replace_to_string(symbol_old, symbol_new);
					has_changed = true;
				}
				//


				//
				else {									//2.2 右边的右边是一大串
					
					//if (!has_changed && (left_son != 0)) { has_changed = left_son->B_change(has_changed); }//???????/----------
					//if (!has_changed) { has_changed = right_son->B_change(has_changed); }//据说这个没用？？？(---------------


					//&&right_son->have_a_app()==false//????????????????????
					if (!has_changed) {		//2.2 有点难，实际上递归判断是否可约，[未改变]意思是 子层没找到可约的地方，即将其视为【变量】---//
						right_son->to_token();//修改了token,小心
						this->goto_left_and_delete_right_before_B();//树没了
						this->B_replace_to_subtree(symbol_old);
						has_changed = true;
					}
				}

			}
		}
	}




	if (left_son != NULL) {//左先----------------------------------------------------1111
		if (has_changed) {//已经约过了
			return true;//说明此处已经约啦，或者写has changed=true
		}
		else {
			has_changed = left_son->B_change(has_changed);
		}
	}//END LEFT




	
	if (right_son != NULL) {//右后---------------------------------------------------------------------222
		if (has_changed) {
			return true;
		}
		else {
			has_changed = right_son->B_change(has_changed);
		}
	}//END RIGHT


	// (   (  (\\x.(\\y.(x))) (a)  ) (((\#.((# #)$))(\#.((# #)$)))$)       )

	
	return has_changed;
}