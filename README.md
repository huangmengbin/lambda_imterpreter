这是一个lambda解释器.

在程序内输入 help 可查看使用说明.

由于hmb又菜又懒，所以不会搞缩略括号模式.



输入格式：

((AND TRUE)FALSE)
((MULT (TWO THREE))((PLUS TEN)FOUR))
((EQ((MULT ((PLUS TWO)TWO))THREE))    ((SUB ((POW TWO)FOUR) )   FOUR))
( (\f.(\x.(f  ONE )))  (f)) 
或 
( (\f.(\x.(f  ONE )))   f )