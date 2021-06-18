## Contents
### `11.2020`<br/>
└─» [Postfix_Calculator](#Postfix_Calculator) `Math`<br/>
### `10.2020`<br/>
├─» [Remove_Cpp_Comments](#Remove_Cpp_Comments) `Syntax`<br/>
├─» [Remove_Python_Comments](#Remove_Python_Comments) `Syntax`<br/>
└─» [Remove_White_Chars](#Remove_White_Chars) `Syntax`<br/>

## Topics

### Postfix_Calculator
Create a simple calculator using Reversed Polish Notation. Allowed operations are addition (`+`), substraction (`-`), multiplication (`*`), complete division (`/`), modulo (`%`) and power (`^`).<br/>
**Compilation**<br/>
```
flex Postfix_Calculator.l
g++ lex.yy.c -o Postfix_Calculator.out -lfl
```
**Example**<br/>
```
./Postfix_Calculator.out
>>> 2 3 + 4 *
<<< 20
>>> 1 2 3 4 + * -
<<< -13
>>> -1 2 -3 4 + * -
<<< -3
>>> 8 -7 6 -5 4 * -3 % / - +
<<< 4
>>> 2 3 2 ^ ^
<<< 512
```

### Remove_Cpp_Comments
Remove comments from source code in C++. When running with `--doc`, program leaves documentation comments (`/**`, `/*!`, `///`, `//!`), and removes only regular ones.<br/>
**Compilation**<br/>
```
flex Remove_Cpp_Comments.l
g++ lex.yy.c -o Remove_Cpp_Comments.out -lfl
```
**Usage**<br/>
```
./Remove_Cpp_Comments.out path/to/file.cpp [--doc]
```

### Remove_Python_Comments
Remove comments from source code in Python.<br/>
**Compilation**<br/>
```
flex Remove_Python_Comments.l
g++ lex.yy.c -o Remove_Python_Comments.out -lfl
```
**Usage**<br/>
```
./Remove_Python_Comments.out path/to/file.cpp
```

### Remove_White_Chars
Remove spaces from beginning and end of each line, change series of tabs or spaces into one space, delete empty lines. At the end attaches number of lines and words.<br/>
**Compilation**<br/>
```
flex Remove_White_Chars.l
g++ lex.yy.c -o Remove_White_Chars.out -lfl
```
**Usage**<br/>
```
./Remove_White_Chars.out path/to/file.txt
```